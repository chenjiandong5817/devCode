package com.plugs.module_wechat.services;

/**
 * Created by Administrator on 2017/5/22.
 */

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.base.AjaxList;
import com.plugs.base.WsMsg;
import com.plugs.constants.ConfigConstants;
import com.plugs.constants.DriverConstants;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_driver.dtos.DriverAccountDetailDto;
import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_driver.dtos.DriverQueueDto;
import com.plugs.module_driver.mappers.DriverAccountDetailMapper;
import com.plugs.module_driver.mappers.DriverMapper;
import com.plugs.module_driver.services.DriverAccountDetailService;
import com.plugs.module_driver.services.DriverElectronicFenceService;
import com.plugs.module_driver.services.DriverQueueService;
import com.plugs.module_enterprise.dtos.EntAccountDetailDto;
import com.plugs.module_enterprise.dtos.EntAccountDto;
import com.plugs.module_enterprise.mappers.EntAccountDetailMapper;
import com.plugs.module_enterprise.mappers.EntAccountMapper;
import com.plugs.module_order.dtos.OrderCostDetailDto;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.dtos.OrderPrepaidDto;
import com.plugs.module_order.mappers.OrderCostDetailMapper;
import com.plugs.module_order.mappers.OrderMapper;
import com.plugs.module_order.services.OrderPrepaidService;
import com.plugs.module_system.dtos.SysCarBillingWayDto;
import com.plugs.module_system.dtos.SysCouponDto;
import com.plugs.module_system.services.SysCarBillingWayService;
import com.plugs.module_system.services.SysCouponService;
import com.plugs.module_system.services.SysSeqService;
import com.plugs.module_user.dtos.UserCouponDto;
import com.plugs.module_user.mappers.UserPassengerMapper;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.services.UserCouponService;
import com.plugs.utils.*;
import com.plugs.utils.gaodemap.GaodeMapsUtils;
import com.util.MapUtil;
import com.util.StringUtil;
import com.utils.log4j.QxLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WechatOrderService extends BaseServiceSupport<OrderDto> {
    private static final Logger logger = Logger.getLogger(WechatOrderService.class);
    private static final Logger qxLogger = Logger.getLogger(WechatOrderService.class);

    @Autowired
    private OrderMapper<OrderDto> orderMapper;

    @Override
    public IMapper<OrderDto> getMapper() {
        return orderMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

    @Autowired
    private DriverMapper<DriverDto> driverMapper;
    @Autowired
    private SysSeqService sysSeqService;//查询序列
    @Autowired
    private UserPassengerMapper<UserPassengerDto> userPassengerMapper;
    @Autowired
    private WechatOrderPayHelper wechatOrderPayHelper;
    @Autowired
    private SysCarBillingWayService billingWayService;
    @Autowired
    private OrderCostDetailMapper<OrderCostDetailDto> orderCostDetailMapper;
    @Autowired
    private EntAccountMapper<EntAccountDto> userEntAccountMapper;
    @Autowired
    private EntAccountDetailMapper<EntAccountDetailDto> userEntAccountDetailMapper;//企业账户明细
    @Autowired
    private DriverAccountDetailMapper<DriverAccountDetailDto> driverAccountDetailMapper;//司机账户明细
    @Autowired
    private DriverElectronicFenceService driverElectronicFenceService;
    @Autowired
    private DriverQueueService driverQueueService;
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private OrderPrepaidService orderPrepaidService;
    @Autowired
    private DriverAccountDetailService driverAccountDetailService;//司机账户明细

    /**
     * 首页订单状态
     */
    public AjaxList getStatus(String passengerUuid) {
        Map<String, Object> params = MapUtil.buildMap();
        Map<String, Object> result = MapUtil.buildMap();

        //等待应答的微信订单
        params.clear();
        params.put("passengeUuid", passengerUuid);
        params.put("orderSource", 3);
        params.put("mainStatus", OrderDto.ORDER_MAIN_STATUS_DEFAULT);//默认
        params.put("subStatus", OrderDto.ORDER_SUB_STATUS_DEFAULT);//默认
        List<OrderDto> defaultOrders = orderMapper.list(params);
        result.put("defaultOrders", defaultOrders == null || defaultOrders.size() < 1 ? null : defaultOrders.get(0));//等待应答的订单

        //进行中的微信订单
        params.clear();
        params.put("passengeUuid", passengerUuid);
        params.put("mainStatusInGoing", 1);//进行中
        params.put("subStatusInGoing", 1);//进行中
        params.put("orderSource", 3);
        List<OrderDto> ongoingOrders = orderMapper.list(params);
        if (ongoingOrders != null && ongoingOrders.size() > 0) {
            OrderDto ongoingOrder = ongoingOrders.get(0);
            ongoingOrder.setPayTimeLimit(SysConfigHelper.getPrepaidOrderPayTimeLimit());
            result.put("ongoingOrders", ongoingOrder);//进行中的订单
        } else {
            result.put("ongoingOrders", null);//进行中的订单
        }

        //待支付的微信订单
        params.clear();
        params.put("passengeUuid", passengerUuid);
        params.put("mainStatus", OrderDto.ORDER_MAIN_STATUS_NOT_PAY);//待支付
//        params.put("subStatus", OrderDto.ORDER_SUB_STATUS_PAYED_NOT_YET);//待支付
        params.put("orderSource", 3);
        List<OrderDto> toPayOrders = orderMapper.list(params);
        result.put("toPayOrders", toPayOrders == null || toPayOrders.size() < 1 ? null : toPayOrders.get(0));//待支付的订单

        return AjaxList.createSuccess("获取成功", result);
    }

    /**
     * 取消订单
     */
    public AjaxList cancel(UserPassengerDto passenger, String uuid) {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", uuid);
        OrderDto orderDto = this.orderMapper.selInfo(params);
        if (orderDto == null || !orderDto.getPassengeUuid().equals(passenger.getUuid())) {
            return AjaxList.createError("订单不存在");
        }

        //进行中，待支付,已完成
        if (orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_ING
                || orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_NOT_PAY
                || orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_FINISH) {
            return AjaxList.createError("取消失败,行程进行中...");
        }


        Map<String, Object> result = MapUtil.buildMap();
        result.put("needToPay", false);
        result.put("needToEvaluation", false);

        //如果该订单已取消，或者 未指定司机，且没有接单司机，直接返回取消成功
        //1 6
        if (StringUtil.isEmpty(orderDto.getActualDriverUuid())
                || orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_CANCEL
                || orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_DEFAULT) {
            OrderDto cancelBean = new OrderDto();
            cancelBean.setUuid(orderDto.getUuid());
            cancelBean.setMainStatus(OrderDto.ORDER_MAIN_STATUS_CANCEL);
            cancelBean.setSubStatus(OrderDto.ORDER_SUB_STATUS_CANCEL_OWN);
            this.orderMapper.edit(cancelBean);
            return AjaxList.createSuccess("取消订单成功", result);
        }

        //todo：20180413 去掉判断司机是否在商圈，根据队列状态判断，只做恢复操作
        DriverQueueDto queueDto = driverQueueService.queryDriverQueueByDriverUuid(orderDto.getActualDriverUuid());
        if (queueDto != null) {
            DriverQueueDto updQueueDto = new DriverQueueDto();
            updQueueDto.setUuid(queueDto.getUuid());
            updQueueDto.setUpdateTime(new Date());

            //当前取消的为立即用车,只要是在排队 就设置为可接单
            int orderType = orderDto.getOrderType();
            if (orderType == OrderDto.ORDER_TYPE_IMMED) {
                if (queueDto.getIsQueue() == DriverConstants.DRIVER_QUEUE_STATUS_1 && queueDto.getIsTakeOrder() != 1) {
                    updQueueDto.setIsTakeOrder(DriverConstants.DRIVER_QUEUE_TAKE_ORDER_YES);
                    driverQueueService.edit(updQueueDto);
                }
            } else {
                //判断用车时间与当前时间在一个小时之内，则进行判断是否恢复队列
                long deparTimeLong = orderDto.getDeparTime().getTime();
                if (Math.abs(System.currentTimeMillis() - deparTimeLong) <= 3600000) {
                    if (queueDto.getIsQueue() == DriverConstants.DRIVER_QUEUE_STATUS_1 && queueDto.getIsTakeOrder() != 1) {
                        updQueueDto.setIsTakeOrder(DriverConstants.DRIVER_QUEUE_TAKE_ORDER_YES);
                        driverQueueService.edit(updQueueDto);
                    }
                }
            }
        }

        params.clear();
        params.put("uuid", orderDto.getActualDriverUuid());
        DriverDto driver = driverMapper.findDriver(params);

        //长连接推送给司机
        WsMsg wsMsg = WsMsg.createSuccess4Order("乘客取消订单", orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_ORDER_PASSENGER_ORDER_CANCEL, WsMsg.To.DRIVER);//取消订单
        WebSocketUtil.sendObjMessageByToken(orderDto.getActualDriverUuid(), wsMsg);

        // 获取计费方式
        SysCarBillingWayDto billingWay = this.billingWayService.findByLevelTypeAndOrderType(orderDto.getLevelType(), orderDto.getOrderType(), orderDto.getAreaUuid());
        //(等待接驾-已到达)
        if (orderDto.getSubStatus() == OrderDto.ORDER_SUB_STATUS_WAIT_MEET_ARRIVE) {
            Date now = new Date();
            double cancelFee = billingWay.getAutonomyCancelFee();
            //订单更新实体
            OrderDto updOrder = new OrderDto();
            updOrder.setUuid(orderDto.getUuid());
            updOrder.setServiceTimeStart(now);
            updOrder.setServiceTimeEnd(now);
            updOrder.setTripTotalFare(0d);
            updOrder.setTotalFee(cancelFee);
            updOrder.setWaitPayFee(cancelFee); //20171201取整
            updOrder.setCancelFee(cancelFee);//自主取消费用
            updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_NOT_PAY);//待支付
            updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_CANCEL_NOT_PAY);//待支付

            //保存明细
            OrderCostDetailDto detailDto = new OrderCostDetailDto();
            detailDto.setUuid(StringUtil.buildUUID());
            detailDto.setOrderUuid(orderDto.getUuid());
            detailDto.setBeyondWaitTimePrice(billingWay.getBeyondWaitTime());
            detailDto.setFreeWaitTime((int) billingWay.getFreeWaitTime());
            detailDto.setBeyondTimeLength(0);
            detailDto.setBeyondWaitFee(0);
            detailDto.setBeyondMileage(0);
            detailDto.setExtraServiceFee(0);
            detailDto.setTimeFee(0);
            detailDto.setAdjusteFee(0);
            detailDto.setStartMileage(billingWay.getStartTrip());
            detailDto.setStartTimeLength((int) billingWay.getStartDuration());
            detailDto.setHaulBackMileage(0);
            detailDto.setHaulBackSubsidyFee(0);
            detailDto.setCreateTime(now);
            this.orderCostDetailMapper.add(detailDto);

            //判断是否有超时等待费
            Date waitBeginTime = orderDto.getDriverArriveTime();
            if (waitBeginTime != null) {
                //如果司机提前到达，则产生等待费的时间，从预约时间开始算起
                if (waitBeginTime.getTime() < orderDto.getDeparTime().getTime()) {
                    waitBeginTime = orderDto.getDeparTime();
                }
                //计算是否超时等待
                long waitTime = (now.getTime() - waitBeginTime.getTime()) / 1000;

                int beyondWaitTime = Math.round(waitTime / 60) - (int) billingWay.getFreeWaitTime();
                if (beyondWaitTime > 1) {
                    //需要更新订单状态
                    updOrder.setUuid(orderDto.getUuid());
                    updOrder.setServiceTimeEnd(now);
                    updOrder.setServiceTimeStart(now);

                    double beyondWaitFee = beyondWaitTime * billingWay.getBeyondWaitTime();
                    cancelFee += beyondWaitFee; //
                    updOrder.setTripTotalFare(0d);

                    updOrder.setBeyondWaitTime(beyondWaitTime);//超时等待时长
                    updOrder.setBeyondWaitFee(beyondWaitFee);//超时等待费

                    //将超时等待费与取消费 给 总费用与外支付费用
                    updOrder.setTotalFee(cancelFee);
                    updOrder.setWaitPayFee(Math.floor(cancelFee));

                }//判断是否产生超时等待费--结束
            }//判断是否有到达时间--结束

            //待支付费用
            if (cancelFee > 0) {
                //如果是企业支付，则自动扣款
                if (orderDto.getPrepayType() == OrderDto.PREPAY_TYPE_ENT) {
                    //企业余额
                    EntAccountDto ent = null;
                    if (StringUtil.isNotEmpty(passenger.getEntAccountUuid())) {
                        params.clear();
                        params.put("uuid", passenger.getEntAccountUuid());
                        ent = this.userEntAccountMapper.selInfo(params);
                    }
                    //预支付方式 企业支付
                    if (ent != null) {
                        //企业支付
                        doEntPay(passenger, ent, driver, cancelFee, orderDto.getUuid());

                        //更新订单支付信息
                        updOrder.setPayFare(Math.floor(updOrder.getWaitPayFee()));//支付金额
                        updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_FINISH);
                        updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_FINISH_UN_EVA);
                        updOrder.setPayType(OrderDto.PAY_TYPE_ENT);
                        updOrder.setEntUuid(ent.getUuid());
                        this.orderMapper.edit(updOrder);

                        //推送
                        orderDto.setTotalFee(updOrder.getTotalFee());
                        WebSocketUtil.sendObjMessageByToken(driver.getUuid(), WsMsg.createSuccess4Order("取消费已从企业账户中扣取",
                                orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_ORDER_CANCELFEE_PAYED, WsMsg.To.DRIVER));

                        result.put("needToEvaluation", true);
                        return AjaxList.createSuccess("订单取消成功，取消费已从企业账户中扣取", result);
                    }

                } else {
                    //是否成功产生过预支付费用 开始
                    if (orderDto.getPrepaidFee() > 0 && orderDto.getPrepaidStatus() == OrderPrepaidDto.PAY_STATUS_SUCCESS) {
                        double offsetFee = cancelFee - orderDto.getPrepaidFee();  //取消费用差 与 预支付费用
                        //有预支付费用 且足够支付
                        if (offsetFee < 1) { //低于1块钱都算足够支付
                            double driverIncomeMoney =  offsetFee < 0 ? orderDto.getPrepaidFee() : updOrder.getWaitPayFee(); //计算司机收入
                            doOwnPay(driver, orderDto.getUuid(), driverIncomeMoney); //个人预支付进行支付

                            //更新订单支付信息
                            updOrder.setPayFare(orderDto.getPrepaidFee());
                            updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_FINISH);
                            updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_FINISH_UN_EVA);
                            updOrder.setPayType(OrderDto.PAY_TYPE_BALANCE);
                            updOrder.setWaitPayFee(0D); //
                            orderMapper.edit(updOrder);

                            //推送
                            orderDto.setTotalFee(driverIncomeMoney);
                            WebSocketUtil.sendObjMessageByToken(orderDto.getActualDriverUuid(), WsMsg.createSuccess4Order("取消费已从个人账户中扣取",
                                    orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_ORDER_CANCELFEE_PAYED, WsMsg.To.DRIVER));

                            return AjaxList.createSuccess("订单取消成功，取消费已从预支付费用中扣取", result);
                        }

                        //更新订单支付信息
                        updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_NOT_PAY);
                        updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_CANCEL_NOT_PAY);
                        updOrder.setPayType(OrderDto.PAY_TYPE_BALANCE);
                        updOrder.setWaitPayFee(offsetFee); //取绝对值
                        orderMapper.edit(updOrder);
                        result.put("needToPay", true);
                        //记录司机收入
                        doOwnPay(driver, orderDto.getUuid(), orderDto.getPrepaidFee()); //个人预支付进行支付
                        return AjaxList.createSuccess("订单取消成功，您需支付相关费用", result);
                    }

                    //无预支付费用
                    updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_NOT_PAY);
                    updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_CANCEL_NOT_PAY);
                    updOrder.setPayType(OrderDto.PAY_TYPE_BALANCE);
                    orderMapper.edit(updOrder);
                    result.put("needToPay", true);
                    return AjaxList.createSuccess("订单取消成功，您需支付相关费用", result);
                }
            } else {
                //有预支付金额
                if (orderDto.getPrepaidFee() > 0 && orderDto.getPrepaidStatus() == OrderPrepaidDto.PAY_STATUS_SUCCESS) {
                    updOrder.setInsPayFare(orderDto.getPrepaidFee()); //实际支付
                    doOwnPay(driver, orderDto.getUuid(), orderDto.getPrepaidFee()); //将乘客预支付费用作为司机收入
                    //todo：推送司机
                    orderDto.setTotalFee(orderDto.getPrepaidFee());
                    WebSocketUtil.sendObjMessageByToken(orderDto.getActualDriverUuid(), WsMsg.createSuccess4Order("取消费已从个人账户中扣取",
                            orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_ORDER_CANCELFEE_PAYED, WsMsg.To.DRIVER));
                }
                updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_CANCEL);
                updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_CANCEL_OWN);
                this.orderMapper.edit(updOrder);
                return AjaxList.createSuccess("订单取消成功", result);
            }
        }

        //有预支付金额
        if (orderDto.getPrepaidFee() > 0 && orderDto.getPrepaidStatus() == OrderPrepaidDto.PAY_STATUS_SUCCESS) {
            doOwnPay(driver, orderDto.getUuid(), orderDto.getPrepaidFee()); //将乘客预支付费用作为司机收入
            //todo：推送司机
            orderDto.setTotalFee(orderDto.getPrepaidFee());
            WebSocketUtil.sendObjMessageByToken(orderDto.getActualDriverUuid(), WsMsg.createSuccess4Order("取消费已从个人账户中扣取",
                    orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_ORDER_CANCELFEE_PAYED, WsMsg.To.DRIVER));
        }

        OrderDto cancelBean = new OrderDto();
        cancelBean.setUuid(orderDto.getUuid());
        cancelBean.setInsPayFare(orderDto.getPrepaidFee()); //实际支付
        cancelBean.setMainStatus(OrderDto.ORDER_MAIN_STATUS_CANCEL);
        cancelBean.setSubStatus(OrderDto.ORDER_SUB_STATUS_CANCEL_OWN);
        this.orderMapper.edit(cancelBean);
        return AjaxList.createSuccess("订单取消成功", result);
    }

    private void doOwnPay(DriverDto driverInfo, String orderUuid, double money) {
        //添加司机明细
        DriverAccountDetailDto driverAccountDetail = new DriverAccountDetailDto();
        Map<String, Object> params = MapUtil.buildMap();
        params.put("orderUuid", orderUuid);
        OrderPrepaidDto orderPrepaidDto = orderPrepaidService.selInfo(params);
        if (orderPrepaidDto.getSerialNumber() != null) {
            driverAccountDetail.setSerialNumber(orderPrepaidDto.getSerialNumber());//todo:司机收入取预支付订单流水
        }

        driverAccountDetail.setUuid(StringUtil.buildUUID());
        driverAccountDetail.setPayOrderUuid(orderUuid);
        driverAccountDetail.setDriverUuid(driverInfo.getUuid());
        driverAccountDetail.setDriverMobile(driverInfo.getMobile());
        driverAccountDetail.setMoney(money);
        driverAccountDetail.setType(DriverAccountDetailDto.TYPE_INCOME_FROM_ORDER);//订单收入
        driverAccountDetail.setBalance(driverInfo.getBalance() + driverAccountDetail.getMoney());
        driverAccountDetail.setPayed(1);
        driverAccountDetail.setPayType(OrderDto.PAY_TYPE_BALANCE);
        driverAccountDetail.setCreateTime(new Date());
        driverAccountDetail.setStatus(DriverAccountDetailDto.STATUS_SUCCESS);
        driverAccountDetail.setRemark("车费收入");
        this.driverAccountDetailService.add(driverAccountDetail);

        //更新司机余额
        DriverDto updDriver = new DriverDto();
        updDriver.setUuid(driverInfo.getUuid());
        updDriver.setInsBalance(driverAccountDetail.getMoney());
        driverMapper.edit(updDriver);
    }

    /**
     * 创建订单
     */
    @Transactional(rollbackFor = Exception.class)
    public AjaxList createOrder(OrderDto orderDto) throws Exception {

        //创建订单前处理
        this.preCreateOrder(orderDto);
        orderDto.setPlanTripPic(this.planTripPicUrl(orderDto));//保存路径规划图

        //添加订单
        //订单序列号
        orderDto.setOrderNumber(CommonUtils.generateOrderSerial(this.sysSeqService.getOrderSeqNextVal()));
        int addResult = this.orderMapper.add(orderDto);
        qxLogger.log(QxLog.LEVEL, "[微信订单[" + orderDto.getUuid() + "]保存到数据库，addResult][" + addResult + "]");

        Map<String, Object> result = MapUtil.buildMap();
        result.put("orderUuid", orderDto.getUuid());
        result.put("executePushThread", true);
        return AjaxList.createSuccess("订单创建成功", result);
    }


    @Transactional
    public AjaxList orderPay(UserPassengerDto userPassengerDto, double money, String orderUuid, int type,
                             HttpServletRequest request, HttpServletResponse response, String userCouponUuid) throws Exception {

        Map<String, Object> params = MapUtil.buildMap();
        //订单信息
        params.clear();
        params.put("uuid", orderUuid);
        OrderDto order = this.orderMapper.selInfo(params);

        //首先判断订单状态
        if (order == null) {
            BizValidate.bizErr("订单异常", ReturnCodeConstants.ERR_ORDER_NOT_EXIST);
        } else {
            if (order.getMainStatus() == 5) {
                BizValidate.bizErr("该订单已支付", ReturnCodeConstants.ERR_ORDER_NOT_EXIST);
            }
            if (order.getMainStatus() != OrderDto.ORDER_MAIN_STATUS_NOT_PAY) {
                BizValidate.bizErr("订单异常", ReturnCodeConstants.ERR_ORDER_STAUTS_NOT_WAIT_PAY);
            }
        }

        //todo:20171122 叫车人无权限支付乘车人付款订单
        if (order.getSubStatus() == OrderDto.ORDER_SUB_STATUS_PAYED_NOT_YET && order.getValetCall() == 1
                && order.getPayToObject() != null && order.getPayToObject() == 1) {
            BizValidate.bizErr("该订单由乘车人线下支付", ReturnCodeConstants.ERR_ORDER_PAY_OBJECT_ERROR);
        }

        if (order.getSubStatus() == OrderDto.ORDER_SUB_STATUS_CANCEL_NOT_PAY) { //取消待支付则取消设置为不使用优惠卷
            OrderDto orderReleaseCouponBean = new OrderDto();
            orderReleaseCouponBean.setUuid(orderUuid);
            orderReleaseCouponBean.setPreChoiceUserCouponUuid("-1");
            orderMapper.edit(orderReleaseCouponBean);
            order.setPreChoiceUserCouponUuid("-1");
        }

        //司机信息
        params.clear();
        params.put("uuid", order.getActualDriverUuid());
        DriverDto driver = driverMapper.selInfo(params);
        //判断司机信息
        if (driver == null) {
            BizValidate.bizErr("订单异常", ReturnCodeConstants.ERR_ORDER_NOT_DRIVER);
        }

        //查询优惠券信息
        UserCouponDto userCoupon = null;//用户优惠券
        SysCouponDto sysCoupon = null;//具体优惠券信息

        if (StringUtil.isNotEmpty(userCouponUuid)) {
            params.clear();
            params.put("uuid", userCouponUuid);
            userCoupon = this.userCouponService.selInfoWithSysCoupon(params);
            if (userCoupon != null && userCoupon.getCouponDetail() != null) {
                sysCoupon = userCoupon.getCouponDetail();
            }
        }

        //20170915如果重新选择优惠卷与预选优惠卷不一致，若之前优惠卷占用则释放
        String preChoiceUserCouponUuid = order.getPreChoiceUserCouponUuid(); //预选优惠卷UUID
        if (StringUtils.isNotEmpty(preChoiceUserCouponUuid)) {
            params.clear();
            params.put("orderUuid", orderUuid);
            params.put("status", 3);
            List<UserCouponDto> releaseUserCoupons = this.userCouponService.list(params); //20180108查找该订单锁占用的优惠卷
            for (UserCouponDto userCouponDto : releaseUserCoupons) {
                qxLogger.log(QxLog.LEVEL, "支付重新选择优惠卷，释放原优惠券["+preChoiceUserCouponUuid+"],orderuuid[" + orderUuid + "]");
                UserCouponDto releaseBean = new UserCouponDto();
                releaseBean.setUuid(userCouponDto.getUuid());
                releaseBean.setOrderUuid("");
                releaseBean.setStatus(UserCouponDto.STATUS_VALID);//可用
                releaseBean.setMoney(0); //优惠券金额
                releaseBean.setUpdateTime(new Date());
                this.userCouponService.edit(releaseBean);

                //20171216订单重新预先优惠卷，防止支付回调将原有的优惠卷使用
                OrderDto updOrderDto = new OrderDto();
                updOrderDto.setUuid(orderUuid);
                updOrderDto.setPreChoiceUserCouponUuid(userCoupon == null ? "-1" : userCoupon.getUuid()); //20180108如果没有则设置为-1，防止回调使用异常
                orderMapper.edit(updOrderDto);
            }
        }

        //费用详情
//        params.clear();
//        params.put("orderUuid", orderUuid);
//        OrderCostDetailDto costDetail = orderCostDetailMapper.selInfo(params);

        //需要计算优惠券的金额
//        double couponCountMoney = order.getTripTotalFare() + costDetail.getAdjusteFee();//行程金额+调整金额
        double couponCountMoney = order.getWaitPayFee();//行程金额+调整金额

        Double couponMoney = 0d;//优惠券金额
        int orderType = order.getOrderType();//订单类型
        //判断优惠券的是否有效
        if (userCoupon != null && sysCoupon != null) {
            int days;
            if (sysCoupon.getTermType() == 1) {
                days = DateUtils.daysBetween(new Date(), sysCoupon.getUseEndTime());
            }else {
                days = sysCoupon.getUseExpireTime() - DateUtils.daysBetween(new Date(), userCoupon.getCreateTime());
            }
            //优惠劵状态、过期
//            if (userCoupon.getStatus() != UserCouponDto.STATUS_VALID || days < 0) { todo:只过滤过期时间
            if (days < 0) {
                BizValidate.bizErr("此优惠劵不可用", ReturnCodeConstants.ERR_ORDER_COUPON_UNABLE);
            }
            //订单类型与优惠劵类型
            int useCarType = sysCoupon.getUseCarType();
            //该优惠卷不为此用车类型，或者不是无限制用车类型优惠卷抛出异常
            if (!((""+useCarType).contains(""+orderType)) && useCarType != SysCouponDto.USE_CAR_TYPE_NOT_LIMIT) {
                BizValidate.bizErr("此优惠劵不适用于该订单", ReturnCodeConstants.ERR_ORDER_COUPON_NOT_SUITE);
            }
            //折扣优惠
            if (sysCoupon.getType() == SysCouponDto.COUPON_TYPE_DISCOUNT) {
                double miniMoney = MathUtils.mul(couponCountMoney, (sysCoupon.getDiscount() / 10));
                couponMoney = couponCountMoney - miniMoney;
                if (couponMoney > sysCoupon.getHighestMoney() && sysCoupon.getHighestMoney() > 0)
                    couponMoney = sysCoupon.getHighestMoney();
            } else {
                if (couponCountMoney >= sysCoupon.getAstrict()) {
                    couponMoney = sysCoupon.getMoney();
                }
            }

            //判断能优惠的金额是否超过本次行程金额
            if (couponMoney >= couponCountMoney) {
                couponMoney = couponCountMoney;//只能优惠该金额
            }

        }

        double driverIncomeFee = (order.getWaitPayFee() == null ? 0 : order.getWaitPayFee());//司机收入
        double needPayFee = driverIncomeFee - couponMoney;//需支付金额

        //todo:20180108 增加预支付金额后 目前根据前端所传参数目前暂用此方法简写进行校验
        if (needPayFee > 0) { //当实际服务端实际金额大于0时进行校验
            if (money >= 0) {
                if (Math.floor(money) != Math.floor(needPayFee)) {
                    qxLogger.log(QxLog.LEVEL, "微信订单[" + orderUuid + "][支付金额错误],微信金额[" + money + "]服务端金额[" + needPayFee + "]");
                    BizValidate.bizErr("支付金额错误", ReturnCodeConstants.ERR_ORDER_PAY_MONEY_ERR);
                }
                money = Double.parseDouble(NumberFormatUtil.formatToInt(money));
            } else {
                money = 0d;
            }
        }

        //判断余额
        if ((type == 2) && (userPassengerDto.getBalance() < money)) {
            BizValidate.bizErr("余额不足", ReturnCodeConstants.ERR_ORDER_PASSENGER_BALANCE_NOT_ENOUGH);
        }


        //更新优惠券
        if (userCoupon != null && sysCoupon != null) {
            int useStatus = type == 2 ? UserCouponDto.STATUS_USED : UserCouponDto.STATUS_OCCUPY; //个人支付为已使用
            this.updateUserCoupon(userCouponUuid, useStatus, orderUuid, couponMoney, sysCoupon.getName(), userPassengerDto.getUuid());
        }

        //以企业余额支付
        if (type == 1) {
            //转移到企业支付自动扣款，这边不做处理
        } else if (type == 2) { //以个人余额支付
            return this.wechatOrderPayHelper.orderPayByBalance(order, userPassengerDto, money, driver);
        } else if (type == 4) { //微信
            return this.wechatOrderPayHelper.orderPayByWx(order, userPassengerDto, money, driver, request, response);
        }
        return AjaxList.createError("服务器繁忙，请稍后支付", ReturnCodeConstants.ERR_10006_LOGIC_ERR);
    }


    /**
     * 更新优惠券
     *
     * @param userCouponUuid
     * @param status
     * @param orderUuid
     * @param couponMoney
     * @param passengerUuid
     */
    private void updateUserCoupon(String userCouponUuid, int status, String orderUuid, double couponMoney, String couponName, String passengerUuid) {
        qxLogger.log(QxLog.LEVEL, "更新用户优惠券[" + userCouponUuid + "],status[" + status + "],orderUuid[" + orderUuid + "]");
        UserCouponDto updUserCoupon = new UserCouponDto();
        updUserCoupon.setUuid(userCouponUuid);
        updUserCoupon.setStatus(status);//已占用
        updUserCoupon.setOrderUuid(orderUuid);
        updUserCoupon.setMoney(couponMoney);//优惠券金额
        updUserCoupon.setUpdateTime(new Date());
        updUserCoupon.setUseTime(new Date());
        updUserCoupon.setUpdator(passengerUuid);
        this.userCouponService.edit(updUserCoupon);

        if (status == UserCouponDto.STATUS_USED) {
            OrderCostDetailDto updCostDetail = new OrderCostDetailDto();
            updCostDetail.setOrderUuid(orderUuid);
            updCostDetail.setCouponFee(couponMoney);
            updCostDetail.setCouponName(couponName);
            this.orderCostDetailMapper.edit(updCostDetail);
        }
    }

    /**
     * 创建订单前处理
     *
     * @param order
     * @throws Exception
     */
    private void preCreateOrder(OrderDto order) throws Exception {
        Map<String, Object> params = MapUtil.buildMap();

        //未完成的订单数查询
        params.put("unFinishedOrder", 1);
        params.put("notValetCall", true);
        params.put("passengeUuid", order.getPassengeUuid());
        int count = orderMapper.count(params);
        //非代客叫车的未完成的订单超过设定的订单数，不能重新发起新订单
        if (order.getPrepayType() != OrderDto.PREPAY_TYPE_ENT) {
//            if ((order.getValetCall() != 1) && (count > ConfigConstants.PASSENGER_ONGOING_ORDER_LIMIT)) {
            //todo:20180306需求更改，限制由5笔订单修改为1笔订单
            if ((order.getValetCall() != 1) && (count > 1)) {
                BizValidate.bizErr("您有一个行程还未完成，暂时不能发起新订单", ReturnCodeConstants.ERR_10006_LOGIC_ERR);
            }
        }

        //判断乘客订单冲突
        if (order.getValetCall() != 1 && order.getPrepayType() != OrderDto.PREPAY_TYPE_ENT) {
            params.clear();
            params.put("passengeUuid", order.getPassengeUuid());
            params.put("limitTime", SysConfigHelper.getOrderConflictTime());
            params.put("deparTime", DateUtils.format(order.getDeparTime()));
            int countUnfinishedOrderByPassengerUuidAndDeparTime = userPassengerMapper.countUnfinishedOrderByPassengerUuidAndDeparTime(params);
            if (countUnfinishedOrderByPassengerUuidAndDeparTime > 0) {
                BizValidate.bizErr("您在该预约时间段有其他未完成订单，无法下单", ReturnCodeConstants.ERR_10006_LOGIC_ERR);
            }
        }
    }


    private String planTripPicUrl(OrderDto order) {
        //预先保存行程规划图
        String planTripPicUrl = null;
        try {
            String drivingDirection = GaodeMapsUtils.getDrivingDirection(order.getOriginLng(), order.getOriginLat(), order.getDestLng(), order.getDestLat());
            planTripPicUrl = GaodeMapsUtils.getStaticMap(GaodeMapsUtils.convertStringToDtoList(drivingDirection), order.getUuid());
        } catch (Exception e) {
            logger.error("create order plan trip picture err", e);
        }
        return planTripPicUrl;
    }


    /**
     * 企业余额支付
     */
    private void doEntPay(UserPassengerDto passenger, EntAccountDto ent, DriverDto driver, double money, String orderUuid) {
        //添加企业支付明细
        double needPayMoney = Math.floor(money); //20171201支付金额取整
        EntAccountDetailDto entAccountDetail = new EntAccountDetailDto();
        entAccountDetail.setUuid(StringUtil.buildUUID());
        entAccountDetail.setSerialNumber(CommonUtils.generateSerialNumber(sysSeqService.getSerialSeqNextVal(), "ZF_QY"));
        entAccountDetail.setEntUuid(ent.getUuid());
        entAccountDetail.setEntMobile(ent.getMobile());
        entAccountDetail.setUserUuid(passenger.getUuid());
        entAccountDetail.setType(EntAccountDetailDto.TYPE_EMPLOYEE_PAY);
        entAccountDetail.setMoney(needPayMoney);
        entAccountDetail.setBalance(ent.getBalance() - needPayMoney);
        entAccountDetail.setGiftMoney(0);
        entAccountDetail.setStatus(1);
        entAccountDetail.setRemark("员工叫车-自动扣款");
        entAccountDetail.setCreateTime(new Date());
        entAccountDetail.setCreator(passenger.getUuid());
        entAccountDetail.setOrderUuid(orderUuid);//订单uuid
        this.userEntAccountDetailMapper.add(entAccountDetail);

        //扣除企业余额
        EntAccountDto updEnt = new EntAccountDto();
        updEnt.setUuid(ent.getUuid());
        updEnt.setDecBalance(needPayMoney);
        this.userEntAccountMapper.edit(updEnt);


        //添加司机明细
        DriverAccountDetailDto driverAccountDetail = new DriverAccountDetailDto();
        driverAccountDetail.setSerialNumber(entAccountDetail.getSerialNumber());
        driverAccountDetail.setUuid(StringUtil.buildUUID());
        driverAccountDetail.setPayOrderUuid(orderUuid);
        driverAccountDetail.setDriverUuid(driver.getUuid());
        driverAccountDetail.setDriverMobile(driver.getMobile());
        driverAccountDetail.setMoney(money);
        driverAccountDetail.setType(DriverAccountDetailDto.TYPE_INCOME_FROM_ORDER);//订单收入
        driverAccountDetail.setBalance(driver.getBalance() + driverAccountDetail.getMoney());
        driverAccountDetail.setPayed(1);
        driverAccountDetail.setPayType(OrderDto.PAY_TYPE_ENT);
        driverAccountDetail.setCreateTime(new Date());
        driverAccountDetail.setStatus(DriverAccountDetailDto.STATUS_SUCCESS);
        driverAccountDetail.setRemark("车费收入");
        this.driverAccountDetailMapper.add(driverAccountDetail);

        //更新司机余额
        DriverDto updDriver = new DriverDto();
        updDriver.setUuid(driver.getUuid());
        updDriver.setInsBalance(driverAccountDetail.getMoney());
        this.driverMapper.edit(updDriver);
    }


    public AjaxList preCancel(UserPassengerDto passenger, String uuid) {

        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", uuid);
        OrderDto orderDto = this.orderMapper.selInfo(params);
        if (orderDto == null || !orderDto.getPassengeUuid().equals(passenger.getUuid())) {
            return AjaxList.createError("订单不存在");
        }

        //进行中，待支付,已完成
        if (orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_ING
                && orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_NOT_PAY
                && orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_FINISH) {
            return AjaxList.createError("取消失败,行程进行中...");
        }

        Map<String, Object> result = MapUtil.buildMap();

        //如果该订单已取消，或者 未指定司机，且没有接单司机，直接返回取消成功
        if (StringUtil.isEmpty(orderDto.getActualDriverUuid())
                || orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_CANCEL
                || orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_DEFAULT) {
            result.put("preCancelFee", 0.0D);
            return AjaxList.createSuccess("获取预取消订单支付费用", result);
        }

        //(等待接驾-已到达)
        if (orderDto.getSubStatus() == OrderDto.ORDER_SUB_STATUS_WAIT_MEET_ARRIVE) {
            // 获取计费方式
            SysCarBillingWayDto billingWay = this.billingWayService.findByLevelTypeAndOrderType(orderDto.getLevelType(), orderDto.getOrderType(), orderDto.getAreaUuid());

            //判断是否有超时等待费
            Date waitBeginTime = orderDto.getDriverArriveTime();
            double beyondWaitFee = 0.0D;
            if (waitBeginTime != null) {
                //如果司机提前到达，则产生等待费的时间，从预约时间开始算起
                if (waitBeginTime.getTime() < orderDto.getDeparTime().getTime()) {
                    waitBeginTime = orderDto.getDeparTime();
                }
                //计算是否超时等待
                long waitTime = (new Date().getTime() - waitBeginTime.getTime()) / 1000;

                int beyondWaitTime = Math.round(waitTime / 60) - (int) billingWay.getFreeWaitTime();
                if (beyondWaitTime > 1) {
                    beyondWaitFee = beyondWaitTime * billingWay.getBeyondWaitTime();
                }
            }
            result.put("preCancelFee", billingWay.getAutonomyCancelFee() + beyondWaitFee);
            return AjaxList.createSuccess("获取预取消订单支付费用", result);
        }
        result.put("preCancelFee", 0.0D);
        return AjaxList.createSuccess("获取预取消订单支付费用", result);
    }
}
