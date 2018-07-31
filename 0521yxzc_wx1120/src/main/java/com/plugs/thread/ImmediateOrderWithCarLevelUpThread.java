package com.plugs.thread;

import com.plugs.base.WsMsg;
import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_driver.dtos.DriverQueueDto;
import com.plugs.module_driver.mappers.DriverMapper;
import com.plugs.module_driver.services.DriverElectronicFenceService;
import com.plugs.module_driver.services.DriverQueueService;
import com.plugs.module_order.dtos.OrderConfigDto;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.dtos.OrderPrepaidDto;
import com.plugs.module_order.mappers.OrderConfigMapper;
import com.plugs.module_order.mappers.OrderMapper;
import com.plugs.module_order.pojo.NotServiceDriverInfo;
import com.plugs.module_order.services.OrderExpandService;
import com.plugs.module_order.services.OrderPrepaidService;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserPassengerMapper;
import com.plugs.utils.DateUtils;
import com.plugs.utils.GuoDouSmsUtils;
import com.plugs.utils.SysConfigHelper;
import com.plugs.utils.WebSocketUtil;
import com.util.MapUtil;
import com.util.SpringContextUtil;
import com.utils.log4j.QxLog;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 立即用车推单线程
 *
 * @author Zoro
 * @since 2016/11/17
 */
public class ImmediateOrderWithCarLevelUpThread extends Thread {

    private static final Logger logger = Logger.getLogger(ImmediateOrderWithCarLevelUpThread.class);
    private static final Logger qxLogger = Logger.getLogger(ImmediateOrderWithCarLevelUpThread.class);

    //从spring容器中获取mapper
    private OrderMapper<OrderDto> orderMapper;
    private DriverMapper<DriverDto> driverMapper;
    private UserPassengerMapper<UserPassengerDto> userPassengerMapper;
    private OrderConfigMapper<OrderConfigDto> orderConfigMapper;

    private DriverQueueService driverQueueService;
    private DriverElectronicFenceService driverElectronicFenceService;

    private String orderUuid;//订单UUID
    private OrderDto orderDto = null;//订单实体
    private OrderConfigDto orderConfig = null;//订单配置
    private Map<String, Object> params = MapUtil.buildMap();//查询参数

    private OrderExpandService orderExpandService;//订单扩展业务处理
    private OrderPrepaidService orderPrepaidService;//订单扩展业务处理

    /**
     * 构造函数
     *
     * @param orderUuid
     */
    public ImmediateOrderWithCarLevelUpThread(String orderUuid) {
        orderMapper = (OrderMapper<OrderDto>) SpringContextUtil.getBean("orderMapper");
        driverMapper = (DriverMapper<DriverDto>) SpringContextUtil.getBean("driverMapper");
        userPassengerMapper = (UserPassengerMapper<UserPassengerDto>) SpringContextUtil.getBean("userPassengerMapper");
        orderConfigMapper = (OrderConfigMapper<OrderConfigDto>) SpringContextUtil.getBean("orderConfigMapper");
        driverQueueService = (DriverQueueService) SpringContextUtil.getBean("driverQueueService");
        orderExpandService = (OrderExpandService) SpringContextUtil.getBean("orderExpandService");
        driverElectronicFenceService = (DriverElectronicFenceService) SpringContextUtil.getBean("driverElectronicFenceService");
        orderPrepaidService = (OrderPrepaidService) SpringContextUtil.getBean("orderPrepaidService");
        this.orderUuid = orderUuid;
    }


    @Override
    public void run() {
        try {
            qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】订单[" + orderUuid + "]进入推单线程-------");
            int immeOrderMatchTimes = SysConfigHelper.getImmeOrderMatchTimes(); //匹配次数
            int immeOrderMatchInterval = SysConfigHelper.getImmeOrderMatchInterval(); //匹配间隔
            int immeOrderCarLevelUpTime = SysConfigHelper.getImmeOrderCarLevelUpTime(); //第几轮进行车型升级
            int time = 1;//第一轮
            qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】订单[" + orderUuid + "][匹配次数:" + immeOrderMatchTimes + "]-" +
                    "[匹配间隔:" + immeOrderMatchInterval + "]-[第几轮进行车型升级:" + immeOrderCarLevelUpTime + "]----");
            //相关变量进行初始化
            init();
            Thread.sleep(100L);//延迟100毫秒

            boolean pushFlag = pushOrder2DriverInQueue(orderDto);

            if(!pushFlag){
                //循环推送
                while (time < immeOrderMatchTimes) {
                    //订单不在默认状态则退出
                    if (!this.orderIsInDefault()) {
                        break;
                    }

                    //车型升级单
                    if (time >= immeOrderCarLevelUpTime) {
                        if (orderConfig.getCarLevelUp() == OrderConfigDto.CAR_LEVEL_UP_YES) { //订单是否允许升级
                            qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】订单[" + orderUuid + "][升级订单][轮询次数："+time+"]-------");
                            //车型升级
                            if(this.findSuiteDriver(true, time)) {
                                break;
                            }
                        } else {
                            if(this.findSuiteDriver(false, time)) {
                                break;
                            }
                        }
                    } else {
                        qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】订单[" + orderUuid + "][不升级订单][轮询次数："+time+"]-------");
                        if(this.findSuiteDriver(false, time)) {
                            break;
                        }
                    }
                    //todo:根据不同轮询次数设置休眠时间
                    int intervalTime;
                    switch (time) {
                        case 1:
                            intervalTime = SysConfigHelper.getImmeOrderFirstIntervalTime();
                            break;
                        case 2:
                            intervalTime = SysConfigHelper.getImmeOrderSecondIntervalTime();
                            break;
                        case 3:
                            intervalTime = SysConfigHelper.getImmeOrderThreeIntervalTime();
                            break;
                        default:
                            intervalTime = SysConfigHelper.getImmeOrderOtherIntervalTime();
                            break;
                    }
                    Thread.sleep(DateUtils.transferToMillisecond(intervalTime));//休眠匹配间隔
                    time++;
                }//退出循环
            }

            //订单还在默认状态且订单不转换发给首汽的话，取消订单(如果有转发给首汽的话，就等首汽回调)
            if (this.orderIsInDefault() && orderConfig.getForwardToSq() == OrderConfigDto.FORWARD_TO_SQ_NO) {
                this.adminCancelOrder();
            }
        } catch (Exception e) {
            logger.error("ImmediateOrderWithCarLevelUpThread order[step] thread err", e);
        }
    }

    private boolean pushOrder2DriverInQueue(OrderDto orderDto) {
        String fenceGid = this.driverElectronicFenceService.getFenceGid(orderDto.getOriginLng(), orderDto.getOriginLat());
        if (fenceGid != null) {
            //根据 Gid与车型 获取队列中司机信息
            //todo:增加区域
            List<DriverQueueDto> driverList = driverQueueService.getDriverFromQuequeByGid(fenceGid, orderDto.getLevelType(),orderDto.getDeparTime(), orderDto.getOrderType(),orderDto.getAreaUuid()); //
            for (DriverQueueDto driverQueue : driverList) {
                //队列升级单
                boolean flag = false;
                if (orderDto.getLevelType() < driverQueue.getCarType()) {
                    logger.info("订单升级为经济型：已经推送给司机" + driverQueue.getDriverName() + "师傅,uuid=" + driverQueue.getDriverUuid());
                    flag = true;
                }
                pushOrder2Driver(driverQueue.getDriverUuid(), flag);
                return true;
            }
            logger.info("该商圈没有满足条件的司机，准备进行就近订单推送");
            return false;
        }
        logger.info("订单["+orderDto.getOriginLng()+","+orderDto.getOriginLat()+"]不在商圈内，准备进行就近订单推送");
        return false;
    }

    /**
     * 判断定是否在默认状态
     *
     * @return
     */
    private boolean orderIsInDefault() {
        qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】判断订单[" + orderUuid + "]是否存在，并且是否是默认状态------");
        this.queryOrder();
        //订单为null 或者 不在默认初始化状态，则退出推单线程
        if (null == orderDto) {
            qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】查询[" + orderUuid + "]为空，退出推单线程------");
            return false;
        } else if (orderDto.getMainStatus() != OrderDto.ORDER_MAIN_STATUS_DEFAULT) {
            qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】查询[" + orderUuid + "]不为空，但是订单不在默认状态，退出推单线程-----");
            return false;
        }
        qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】查询[" + orderUuid + "]不为空，且订单在默认状态-----");
        return true;
    }

    /**
     * 符合条件的筛选参数
     *
     * @return
     */
    private Map<String, Object> suiteConditionParams(boolean carLevelUp, int time, String areaUuid) {
        float distance;
        switch (time) {
            case 1:
                distance = SysConfigHelper.getImmeOrderFirstPushDistance();
                break;
            case 2:
                distance = SysConfigHelper.getImmeOrderSecondPushDistance();
                break;
            case 3:
                distance = SysConfigHelper.getImmeOrderThreePushDistance();
                break;
            default:
                distance = SysConfigHelper.getImmeOrderOtherPushDistance();
                break;
        }
        params.clear();
        params.put("lat", orderDto.getOriginLat());
        params.put("lng", orderDto.getOriginLng());
        if (!carLevelUp) {
            qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】[不升级],[符合条件的筛选参数]-----");
            params.put("levelType", orderDto.getLevelType());
        }else {
            qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】[升级],[符合条件的筛选参数]-----");
            params.put("carLevelUp", orderDto.getLevelType());//进行车型升级
        }
        params.put("deparTime", DateUtils.format(orderDto.getDeparTime()));
        params.put("limitTimeBefore", orderConfig.getConflictTimeBefore());
        params.put("limitTimeAfter", orderConfig.getConflictTimeAfter());
        params.put("distance", distance / 1000);//公里
        params.put("offLineLimitTime", SysConfigHelper.getOffLineLimitTime());//s
        params.put("areaUuid", areaUuid);//s
        return params;
    }

    /**
     * 查询符合条件的司机
     */
    private boolean findSuiteDriver(boolean carLevelUp, int time) {
        qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】【查询符合条件的司机】【" + orderUuid + "】-【轮询次数"+time+"】---------------");
        //查找后台指定司机里列表
        List<NotServiceDriverInfo> notServiceDriverInfos = this.driverMapper.notServiceDriverUuidByTimeAndDistanceLog(this.suiteConditionParams(carLevelUp, time, orderDto.getAreaUuid()));

        //第一轮没有找到
        if((null == notServiceDriverInfos || notServiceDriverInfos.size() == 0) && !carLevelUp && time == 1) {
            try {
                Thread.sleep(1000L); //首轮未找到睡眠1秒
                qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】【不升级车型，发起第二次查询】【" + this.orderUuid + "】----------------");
                notServiceDriverInfos = this.driverMapper.notServiceDriverUuidByTimeAndDistanceLog(this.suiteConditionParams(carLevelUp, time, orderDto.getAreaUuid()));
            } catch (InterruptedException e) {
                qxLogger.log(QxLog.LEVEL, "订单【" + this.orderDto.getUuid() + "】[第一轮重复查询发生异常退出本次查询]" + e.getMessage());
                return false;
            }
        }

        if (notServiceDriverInfos != null && notServiceDriverInfos.size() > 0) {
            //日志记录使用
            for (NotServiceDriverInfo driverInfo : notServiceDriverInfos) {
                qxLogger.log(QxLog.LEVEL, "微信订单【循环查找立即用车司机】[订单："+orderUuid+"]【已找到符合条件司机】" +
                        "[司机UUID："+driverInfo.getSuitDriverUuid()+"]" +
                        "[司机名称："+driverInfo.getDriverName()+"]" +
                        "[车牌号："+driverInfo.getCarNo()+"]" +
                        "[距离订单："+driverInfo.getDistance()+"]" +
                        "[司机当前经纬度："+driverInfo.getCurrentLng()+","+driverInfo.getCurrentLat()+"]】-----------");
            }

            NotServiceDriverInfo firstDriver = notServiceDriverInfos.get(0);
            if (carLevelUp) {
                for (int i = 0; i < notServiceDriverInfos.size(); i++) {
                    //成绩单 与订单相同的车型优先派送
                    if (notServiceDriverInfos.get(i).getLevelType() == orderDto.getLevelType()) {
                        firstDriver = notServiceDriverInfos.get(i);
                        qxLogger.log(QxLog.LEVEL, "微信订单 即时订单【" + orderDto.getUuid() + "】升级，但有小车司机[" + firstDriver.getDriverName() + "]");
                        break;
                    }
                }
            }

            pushOrder2Driver(firstDriver.getSuitDriverUuid(), carLevelUp);

            return true;

        }
        return false;
    }

    private void pushOrder2Driver(String driverUuid, boolean carLevelUp) {
        qxLogger.log(QxLog.LEVEL, "微信订单 即时订单【" + orderDto.getUuid() + "】成立，推送给乘客["+orderDto.getPassengeUuid()+"]和司机[" + driverUuid + "]");
        //查询司机信息
        params.clear();
        params.put("uuid", driverUuid);
        DriverDto suitDriver = this.driverMapper.findDriver(params);

        // 查询当前接单司机位置是否仍在商圈中，并更新对应队列状态
        driverElectronicFenceService.checkDriverHasOrderInFence(suitDriver);

        //修改订单成立
        OrderDto updOrder = new OrderDto();
        updOrder.setUuid(orderDto.getUuid());
        if (carLevelUp) {
            if (suitDriver.getLevelType() > orderDto.getLevelType()) {
                updOrder.setIsCarLevelUp(1);//车型升级
            } else {
                updOrder.setIsCarLevelUp(-1);
            }
        }
        updOrder.setActualDriverUuid(driverUuid);//司机接单司机
        updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_WAIT_MEET);
        updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_WAIT_MEET_NOT_ARRIVE);
        //20170806新增订单成立时间
        updOrder.setDistributeTime(new Date());
        updOrder.setPrepaidStatus(orderDto.getPrepaidFee() > 0D ? OrderPrepaidDto.PAY_STATUS_WAIT : OrderPrepaidDto.PAY_STATUS_INIT);
        this.orderMapper.edit(updOrder);//订单成立

        //同步更新变量 orderDto
        orderDto.setIsCarLevelUp(updOrder.getIsCarLevelUp());
        orderDto.setActualDriverUuid(driverUuid);
        orderDto.setMainStatus(updOrder.getMainStatus());
        orderDto.setSubStatus(updOrder.getSubStatus());
        orderDto.setDistributeTime(new Date());
        orderDto.setPrepaidStatus(updOrder.getPrepaidStatus());

        //20171228订单静态数据
        orderExpandService.setOrderStaticData(driverUuid, orderDto.getUuid());

        //若需预支付 加入预支付订单中及定时器中【构造方式】
        if (orderDto.getPrepaidFee() > 0) {
            orderPrepaidService.addPrepaidOrder(orderDto);
        }

        GuoDouSmsUtils.sendOrderBeTakeNotice(orderDto, suitDriver);

        //司机
        WsMsg wsMsg4Driver = WsMsg.createSuccess4Order("订单派送", orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_ORDER_PASSENGER_ORDER_DISTRIBUTE, WsMsg.To.DRIVER);
        WebSocketUtil.sendObjMessageByToken(driverUuid, wsMsg4Driver);

        //将司机接单消息推送给乘客
        WsMsg wsMsg = WsMsg.createSuccess4Order("司机已接单", suitDriver.toMap4WebSocket(orderUuid), WsMsg.OPERATE_CODE_ORDER_DRIVER_START_TO_TAKE_PASSENGER, WsMsg.To.PASSENGER);
        WebSocketUtil.sendObjMessageByToken(orderDto.getPassengeUuid(), wsMsg);
    }


    /**
     * 后台取消订单并推送给乘客
     */
    private void adminCancelOrder() {

        qxLogger.log(QxLog.LEVEL, "=======================================================================");
        qxLogger.log(QxLog.LEVEL, "【循环查找立即用车司机】未找到符合条件的司机，后台取消订单");
        qxLogger.log(QxLog.LEVEL, "=======================================================================");

        DataSourceTransactionManager springTransactionManager = (DataSourceTransactionManager) SpringContextUtil.getBean("springTransactionManager");
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus status = springTransactionManager.getTransaction(def); // 获得事务状态
        try {
            //未找到符合条件的司机---取消订单
            OrderDto updOrder = new OrderDto();
            updOrder.setUuid(orderDto.getUuid());
            updOrder.setActualDriverUuid("-1");//司机接单司机uuid清空
            updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_CANCEL);
            updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_CANCEL_AUTO);
            //20170806订单更新时间作为取消时间
            updOrder.setUpdateTime(new Date());
            orderMapper.edit(updOrder);//后台取消订单

            springTransactionManager.commit(status);
            //后台取消订单，短信通知乘客
            GuoDouSmsUtils.sendAdminAutoCancelNotice(orderDto, "未找到合适司机");

            WsMsg wsMsg = WsMsg.createSuccess4Order("未找到符合条件的司机，后台为您取消订单", updOrder.getUuid(), WsMsg.OPERATE_CODE_ORDER_ADMIN_CANCEL, WsMsg.To.PASSENGER);
            WebSocketUtil.sendObjMessageByToken(orderDto.getPassengeUuid(), wsMsg);
        } catch (Exception e) {
            springTransactionManager.rollback(status);
        }
    }


    /**
     * 初始化查找订单及其配置
     */
    private void init() {
        this.queryOrder();
        //查询订单配置
        orderConfig = this.orderConfigMapper.selInfoByOrderType(orderDto.getOrderType());
    }

    /**
     * 根据订单uuid查询订单
     */
    private void queryOrder() {
        //查询订单
        params.clear();
        params.put("uuid", orderUuid);
        orderDto = this.orderMapper.selInfo(params);//查询订单
    }

}
