package com.plugs.thread;

import com.plugs.base.WsMsg;
import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_driver.dtos.DriverRushOrderDto;
import com.plugs.module_driver.mappers.DriverMapper;
import com.plugs.module_driver.mappers.DriverRushOrderMapper;
import com.plugs.module_order.dtos.OrderConfigDto;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.mappers.OrderConfigMapper;
import com.plugs.module_order.mappers.OrderMapper;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserPassengerMapper;
import com.plugs.utils.DateUtils;
import com.plugs.utils.GuoDouSmsUtils;
import com.plugs.utils.SysConfigHelper;
import com.plugs.utils.WebSocketUtil;
import com.util.MapUtil;
import com.util.SpringContextUtil;
import com.util.StringUtil;
import com.utils.log4j.QxLog;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 循环订单推送线程(根据配置进行选择性车型升级)
 *
 * @author Zoro
 * @since 2016/10/17
 */
public class CyclePushOrderWithCarLevelUpThread extends Thread {


    private static final Logger logger = Logger.getLogger(CyclePushOrderWithCarLevelUpThread.class);
    private static final Logger qxLogger = Logger.getLogger(CyclePushOrderWithCarLevelUpThread.class);

    //从spring容器中获取mapper
    private OrderMapper<OrderDto> orderMapper;
    private DriverMapper<DriverDto> driverMapper;
    private UserPassengerMapper<UserPassengerDto> userPassengerMapper;
    private OrderConfigMapper<OrderConfigDto> orderConfigMapper;
    private DriverRushOrderMapper<DriverRushOrderDto> driverRushOrderMapper;

    //订单相关
    private String orderUuid;//订单UUID
    private OrderDto orderDto = null;//订单实体
    private OrderConfigDto orderConfig = null;//订单配置

    private int step = 1;//阶段（初始阶段-（给后台指定司机），第二阶段（符合条件的司机，持续30分钟，间隔30s））

    private Date beginTime;//开始时间

    private int totalNum = 1;//租单子订单总数
    private int curNum = 1;//租单当前订单

    private Map<String, Object> params = MapUtil.buildMap();//查询参数

    /**
     * 构造函数 预约用车使用
     * @param orderUuid 订单uuid
     */
    public CyclePushOrderWithCarLevelUpThread(String orderUuid) {
        orderMapper = (OrderMapper<OrderDto>) SpringContextUtil.getBean("orderMapper");
        driverMapper = (DriverMapper<DriverDto>) SpringContextUtil.getBean("driverMapper");
        userPassengerMapper = (UserPassengerMapper<UserPassengerDto>) SpringContextUtil.getBean("userPassengerMapper");
        orderConfigMapper = (OrderConfigMapper<OrderConfigDto>) SpringContextUtil.getBean("orderConfigMapper");
        driverRushOrderMapper = (DriverRushOrderMapper<DriverRushOrderDto>) SpringContextUtil.getBean("driverRushOrderMapper");
        this.orderUuid = orderUuid;
        this.beginTime = new Date();
    }

    /**
     * 构造函数 日租半日租使用
     * @param orderUuid 订单uuid
     * @param totalNum 租单子订单总数
     * @param curNum 租单当前订单
     */
    public CyclePushOrderWithCarLevelUpThread(String orderUuid, int totalNum, int curNum) {
        orderMapper = (OrderMapper<OrderDto>) SpringContextUtil.getBean("orderMapper");
        driverMapper = (DriverMapper<DriverDto>) SpringContextUtil.getBean("driverMapper");
        userPassengerMapper = (UserPassengerMapper<UserPassengerDto>) SpringContextUtil.getBean("userPassengerMapper");
        orderConfigMapper = (OrderConfigMapper<OrderConfigDto>) SpringContextUtil.getBean("orderConfigMapper");
        driverRushOrderMapper = (DriverRushOrderMapper<DriverRushOrderDto>) SpringContextUtil.getBean("driverRushOrderMapper");
        this.orderUuid = orderUuid;
        this.beginTime = new Date();
        this.totalNum = totalNum;
        this.curNum = curNum;
    }


    @Override
    public void run() {
        try {
            qxLogger.log(QxLog.LEVEL, "微信订单【循环推单】订单[" + orderUuid + "]进入推单线程-------");
            //循环
            while (true) {
                Thread.sleep(100L);//延迟100毫秒
                if (step == 1) {//第一阶段【推送给后台指定司机】
                    step++;//步骤加1
                    if (!orderIsInDefault()) {
                        break;//如果订单不在默认状态，则退出推单线程
                    }
                    if (this.step1Push()) {
                        //如果优先列表有司机，那么进行推送等待
                        Thread.sleep(DateUtils.transferToMillisecond(SysConfigHelper.getAssignDriverPushTime()));
                    }
                } else if (step == 2) {// 第二阶段【推送给符合条件的司机】
                    step++;
                    //推单开始距离现在的时间间隔
                    int cycle = 1;
                    long timeBetween = new Date().getTime() - beginTime.getTime();
                    qxLogger.log(QxLog.LEVEL, "微信订单 订单建立时间距离现在:" + timeBetween + "毫秒");
                    //推单时间还未超过设定，则继续推单，否则退出循环
                    while (timeBetween <= DateUtils.transferToMillisecond(SysConfigHelper.getSecondPushTime())) { //180
                        if (!orderIsInDefault()) {
                            break;//如果订单不在默认状态，则退出推单线程
                        }
                        if (cycle >= 2) {
                            this.step2Push(true);//第二阶段推送 升级
                        } else {
                            this.step2Push(false); //不升级
                        }
                        cycle++;
                        //休眠推送间隔
                        Thread.sleep(DateUtils.transferToMillisecond(SysConfigHelper.getSecondPushSecondPushInterval()));//符合条件的司机-毫秒 36
                        timeBetween = new Date().getTime() - beginTime.getTime();//从新计算推单时间
                        qxLogger.log(QxLog.LEVEL, "微信订单 订单建立时间距离现在:" + timeBetween + "毫秒");
                    }

                    //还在默认状态且不转发给首汽，就取消
                    if (orderConfig.getForwardToSq() == OrderConfigDto.FORWARD_TO_SQ_NO) {
                        if (orderIsInDefault()) {
                            this.adminAutoCancelOrder();//后台取消订单
                        }
                    }
                    break;
                } else {
                    break;
                }//非法步骤
            }//退出循环
        } catch (Exception e) {
            logger.error("push order[step] thread err", e);
        }
    }

    /**
     * 订单是否在初始状态
     *
     * @return true 订单不为空，且不处于默认状态
     */
    private boolean orderIsInDefault() {
        qxLogger.log(QxLog.LEVEL, "微信订单 【循环推单】判断订单[" + orderUuid + "]是否存在，并且是否是默认状态------");
        orderDto = this.queryOrder();
        //订单不在默认初始化状态，则退出推单线程,订单为空则查询两次，防止创建时还未提交事务，这边查询不到
        if (null == orderDto) {
            qxLogger.log(QxLog.LEVEL, "微信订单 【循环推单】首次查询[" + orderUuid + "]为空，进行二次查询------");
            orderDto = this.queryOrder();
            if (null == orderDto) {
                qxLogger.log(QxLog.LEVEL, "微信订单【循环推单】二次查询[" + orderUuid + "]为空，退出推单线程-------");
                return false;
            } else if (orderDto.getMainStatus() != OrderDto.ORDER_MAIN_STATUS_DEFAULT) {
                qxLogger.log(QxLog.LEVEL, "微信订单【循环推单】二次查询[" + orderUuid + "]不为空，但是订单不在默认状态，退出推单线程-----");
                return false;
            }
        } else if (orderDto.getMainStatus() != OrderDto.ORDER_MAIN_STATUS_DEFAULT) {
            qxLogger.log(QxLog.LEVEL, "微信订单【循环推单】首次查询[" + orderUuid + "]不为空，但是订单不在默认状态，退出推单线程-----");
            return false;
        }
        //查询订单配置
        orderConfig = this.orderConfigMapper.selInfoByOrderType(orderDto.getOrderType());
        qxLogger.log(QxLog.LEVEL, "微信订单【循环推单】订单查询[" + orderUuid + "]不为空，且订单处于默认状态，继续下一步-----");
        return true;
    }

    /**
     * 查找订单
     * @return 订单实体类
     */
    private OrderDto queryOrder() {
        //查询订单
        params.clear();
        params.put("uuid", orderUuid);
        return this.orderMapper.selInfo(params);//查询订单
    }

    /**
     * 符合条件的筛选参数
     *
     * @return params
     */
    private Map<String, Object> suiteConditionParams() {
        params.clear();
        params.put("levelType", orderDto.getLevelType());//车型
        params.put("isWork", DriverDto.IS_WORK_YES);//上班
        params.put("deparTime", DateUtils.format(orderDto.getDeparTime(), "yyyy-MM-dd HH:mm:ss"));//预约时间
        //冲突时间，前后时间不一致
        params.put("limitTimeBefore", orderConfig.getConflictTimeBefore());//订单冲突时间_前--秒
        params.put("limitTimeAfter", orderConfig.getConflictTimeAfter());//订单冲突时间_后--秒
        params.put("offLineLimitTime", SysConfigHelper.getOffLineLimitTime());//司机离线阈值
        //指定范围内
        params.put("lat", orderDto.getOriginLat());
        params.put("lng", orderDto.getOriginLng());
        params.put("distance",SysConfigHelper.getCycleOrderPushDistance() );//范围 km
        //todo:兼容旧版本
        params.put("areaUuid", orderDto.getAreaUuid());
        return params;
    }

    /**
     * 第一阶段推送,是否查询到有符合条件的司机
     */
    private boolean step1Push() {
        qxLogger.log(QxLog.LEVEL, "微信订单【循环推单】第一阶段【" + orderUuid + "】----------------");

        //查找后台指定司机里列表
        List<String> priorityPushUuids = this.driverMapper.priorityPushUuidList(suiteConditionParams());//优先推送的司机UUID列表(已判断订单冲突)
        if (priorityPushUuids != null && priorityPushUuids.size() > 0) {
            //进行推送
            WsMsg wsMsg;
            if (orderDto.getOrderType() == OrderDto.ORDER_TYPE_RENT_DAY || orderDto.getOrderType() == OrderDto.ORDER_TYPE_RENT_HALF_DAY) {
                wsMsg = WsMsg.createSuccess4Order("可抢订单", orderDto.toMap4WebSocket(totalNum, curNum), WsMsg.OPERATE_CODE_ORDER_PASSENGER_ORDER_PUSH, WsMsg.To.DRIVER);
            } else {
                wsMsg = WsMsg.createSuccess4Order("可抢订单", orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_ORDER_PASSENGER_ORDER_PUSH, WsMsg.To.DRIVER);
            }
            new SocketPushThread(priorityPushUuids, wsMsg).start();//开始推送优先的司机
            return true;
        }
        return false;
    }

    /**
     * 第二阶段推送
     * @param carLevelUp 是否升级
     */
    private void step2Push(boolean carLevelUp) {
        qxLogger.log(QxLog.LEVEL, "微信订单【循环推单】第2阶段【" + orderUuid + "】----------------");
        params = this.suiteConditionParams(); //

        //预约单，且车型升级开关开启的情况
        if (orderConfig.getCarLevelUp() == OrderConfigDto.CAR_LEVEL_UP_YES) {
            if (carLevelUp) {
                qxLogger.log(QxLog.LEVEL, "微信订单【循环推单】预约用车，进行车型升级.....");

                params.remove("levelType");
                params.put("carLevelUp", orderDto.getLevelType());//进行车型升级
            }
        }
        List<String> suiteConditionUuids = this.driverMapper.suiteConditionUuidList(params);//上班的司机UUID列表
        if (suiteConditionUuids != null && suiteConditionUuids.size() > 0) {
            //进行推送
            WsMsg wsMsg;
            //todo:20171010目前只对时尚型进行升级提示
            if (orderDto.getLevelType() == 1 && orderConfig.getCarLevelUp() == OrderConfigDto.CAR_LEVEL_UP_YES && carLevelUp) {
                orderDto.setIsCarLevelUp(1); //推送显示升级标签
            } else {
                orderDto.setIsCarLevelUp(-1); //推送不显示升级标签
            }
            if (orderDto.getOrderType() == OrderDto.ORDER_TYPE_RENT_DAY || orderDto.getOrderType() == OrderDto.ORDER_TYPE_RENT_HALF_DAY) {
                wsMsg = WsMsg.createSuccess4Order("可抢订单", orderDto.toMap4WebSocket(totalNum, curNum), WsMsg.OPERATE_CODE_ORDER_PASSENGER_ORDER_PUSH, WsMsg.To.DRIVER);
            } else {
                wsMsg = WsMsg.createSuccess4Order("可抢订单", orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_ORDER_PASSENGER_ORDER_PUSH, WsMsg.To.DRIVER);
            }
            new SocketPushThread(suiteConditionUuids, wsMsg).start();//开始推送给上班的司机
        }
    }




    /**
     * 后台取消订单并推送给乘客
     */
    private void adminAutoCancelOrder() {
        //先判断有没有抢单司机
        params.clear();
        params.put("orderUuid", orderUuid);
        int rushCount = this.driverRushOrderMapper.count(params);
        if (rushCount > 0) {
            return;
        }

        qxLogger.log(QxLog.LEVEL, "=======================================================================");
        qxLogger.log(QxLog.LEVEL, "微信订单【循环推单】未找到符合条件的司机，后台取消订单");
        qxLogger.log(QxLog.LEVEL, "=======================================================================");
        //未找到符合条件的司机---取消订单
        OrderDto updOrder = new OrderDto();
        updOrder.setUuid(orderDto.getUuid());
        updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_CANCEL);
        updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_CANCEL_AUTO);//自动取消
        updOrder.setUpdateTime(new Date());
        this.orderMapper.edit(updOrder);//后台取消订单

        //后台取消订单，短信通知乘客
        GuoDouSmsUtils.sendAdminAutoCancelNotice(orderDto, "无人接单");

        WsMsg wsMsg = WsMsg.createSuccess4Order("未找到符合条件的司机，后台为您取消订单", updOrder.getUuid(), WsMsg.OPERATE_CODE_ORDER_ADMIN_CANCEL, WsMsg.To.PASSENGER);
        WebSocketUtil.sendObjMessageByToken(orderDto.getPassengeUuid(), wsMsg);

        if (orderDto.getOrderType() == OrderDto.ORDER_TYPE_RENT_DAY || orderDto.getOrderType() == OrderDto.ORDER_TYPE_RENT_HALF_DAY) {
            if (StringUtil.isNotEmpty(orderDto.getGroupUuid())) {
                params.clear();
                params.put("groupUuid", orderDto.getGroupUuid());
                List<OrderDto> groupOrder = this.orderMapper.list(params);
                for (OrderDto subOrder : groupOrder) {
                    //订单组相关订单 状态非默认与取消的前提下，实际接单司机uuid不为空
                    if (subOrder.getMainStatus() != OrderDto.ORDER_MAIN_STATUS_CANCEL && subOrder.getMainStatus() != OrderDto.ORDER_MAIN_STATUS_DEFAULT) {
                        if (StringUtil.isNotEmpty(subOrder.getActualDriverUuid())) {
                            logger.info("半日租日租自动取消：" + subOrder.getUuid());
                            WsMsg wsMsgSuccess = WsMsg.createSuccess4Order("订单被取消", subOrder, WsMsg.OPERATE_CODE_ORDER_ADMIN_CANCEL, WsMsg.To.DRIVER);
                            WebSocketUtil.sendObjMessageByToken(subOrder.getActualDriverUuid(), wsMsgSuccess);
                        }
                    }
                }
                params.clear();
                params.put("groupUuid", orderDto.getGroupUuid());
                params.put("mainStatus", OrderDto.ORDER_MAIN_STATUS_CANCEL);
                params.put("subStatus", OrderDto.ORDER_SUB_STATUS_CANCEL_AUTO);
                params.put("updateTime", new Date());
                orderMapper.cancelBatchByGroupUuid(params);
            }
        }
    }
}
