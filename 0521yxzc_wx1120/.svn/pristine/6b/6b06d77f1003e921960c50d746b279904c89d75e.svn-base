package com.plugs.module_wechat.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.base.WsMsg;
import com.plugs.constants.ConfigConstants;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_common.dtos.CommonPayLogDto;
import com.plugs.module_common.mappers.CommonPayLogMapper;
import com.plugs.module_driver.dtos.DriverAccountDetailDto;
import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_driver.mappers.DriverAccountDetailMapper;
import com.plugs.module_driver.mappers.DriverMapper;
import com.plugs.module_order.dtos.OrderCostDetailDto;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.mappers.OrderCostDetailMapper;
import com.plugs.module_order.mappers.OrderMapper;
import com.plugs.module_order.services.OrderVipHelper;
import com.plugs.module_user.dtos.UserAccountDetailDto;
import com.plugs.module_user.dtos.UserCouponDto;
import com.plugs.module_user.mappers.UserAccountDetailMapper;
import com.plugs.module_user.mappers.UserCouponMapper;
import com.plugs.utils.BizValidate;
import com.plugs.utils.CommonUtils;
import com.plugs.utils.GuoDouSmsUtils;
import com.plugs.utils.WebSocketUtil;
import com.util.MapUtil;
import com.util.StringUtil;
import com.utils.log4j.QxLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/7.
 */
@Service
public class WechatOrderPayCallbackService extends BaseServiceSupport<OrderDto> {
    private static final Logger logger = Logger.getLogger(WechatOrderPayCallbackService.class);
    @Autowired
    private OrderMapper<OrderDto> orderMapper;
    @Autowired
    private UserAccountDetailMapper<UserAccountDetailDto> userAccountDetailMapper;//企业账号明细
    @Autowired
    private CommonPayLogMapper<CommonPayLogDto> commonPayLogMapper;//通用支付日志
    @Autowired
    private DriverMapper<DriverDto> driverMapper;//司机
    @Autowired
    private DriverAccountDetailMapper<DriverAccountDetailDto> driverAccountDetailMapper;//司机账户明细
    @Autowired
    private UserCouponMapper<UserCouponDto> userCouponMapper;//用户优惠券
    @Autowired
    private OrderCostDetailMapper<OrderCostDetailDto> orderCostDetailMapper;//订单明细

    @Value("${fz.vip.phones}")
    private String fzVipPhones;

    @Override
    public IMapper<OrderDto> getMapper() {
        return orderMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }


    @Transactional(rollbackFor = Exception.class)
    public String orderPayTenpayCallBack(HttpServletRequest request) throws Exception {
        logger.log(QxLog.LEVEL, "-----------进入微信公众号订单支付回调service---------");
        // xml请求解析
        Map<String, String> requestMap = CommonUtils.parseXmlFromRequest(request);
        String result_code = requestMap.get("result_code");// SUCCESS/FAIL
        String return_code = requestMap.get("return_code");// SUCCESS/FAIL此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        logger.log(QxLog.LEVEL, "-----------result_code---------" + result_code);
        logger.log(QxLog.LEVEL, "-----------return_code---------" + return_code);

        // 微信支付返回结果
        String resFailXml = "<xml><return_code>FAIL</return_code></xml>";
        String resSuccessXml = "<xml><return_code>SUCCESS</return_code></xml>";

        String out_trade_no = requestMap.get("out_trade_no");// 商户订单号
        Date sysTime = new Date();//系统时间
        String tradeNumber = out_trade_no;// 订单ID
        // 验证该订单是否存在
        UserAccountDetailDto userAccountDetailDto = userAccountDetailMapper.selInfoBySerialNumber(tradeNumber);
        if (userAccountDetailDto == null || userAccountDetailDto.getUuid() == null) {
            // 向微信返回失败信息
            return resFailXml;
        } else {
            if (userAccountDetailDto.getPayed() == UserAccountDetailDto.PAYED_OK) {
                return resSuccessXml;
            }
        }

        //判断微信返回值
        if ("SUCCESS".equals(result_code) && "SUCCESS".equals(return_code)) {
            logger.log(QxLog.LEVEL, "-----------进入微信公众号订单支付回调service微信返回值正确---------");

            // todo:待验证签名
//            if (CommonUtil.validSign(requestMap, tenpayConfig.getAPP_KEY())) {
            if (true) {
                // 修改订单状态为付款完成
                updUserAccountDetailPayed(userAccountDetailDto.getUuid(), 1);

                // 检索支付信息
                Map<String, Object> srhPhParams = MapUtil.buildMap();
                srhPhParams.put("tradeNumber", userAccountDetailDto.getSerialNumber());
                List<CommonPayLogDto> payLogs = commonPayLogMapper.list(srhPhParams);
                // 新增前检查是否有该订单号的记录 没有则新增
                if (payLogs.isEmpty()) {
                    CommonPayLogDto payLog = CommonPayLogDto.createUserTenpayRechargePayLog(userAccountDetailDto, requestMap);
                    commonPayLogMapper.add(payLog);
                } else {
                    CommonPayLogDto payLog = payLogs.get(0);// 修改支付表支付状态
                    payLog.setStatus(ConfigConstants.PAY_STATUS_OK);
                    payLog.setPayPlatformSerialNum(requestMap.get("transaction_id"));// 微信交易号
                    payLog.setCollectAccount(requestMap.get("mch_id"));// 收款账号（微信商户号ID）
                    payLog.setPayAccount(requestMap.get("appid"));// 付款账号（公众账号ID）
                    payLog.setCallbackTime(sysTime);// 回调日期
                    payLog.setPaidTime(sysTime);// 支付日期
                    commonPayLogMapper.edit(payLog);
                }
                //获取用户信息
                orderPayCallbackSuccess(userAccountDetailDto, OrderDto.PAY_TYPE_WX);
                return resSuccessXml;
            }
            return resFailXml;
        }
        return resFailXml;
    }

    /**
     * 更新用户账户明细的支付状态
     */
    private void updUserAccountDetailPayed(String uuid, int payed) {
        UserAccountDetailDto updUserAccountDetail = new UserAccountDetailDto();
        updUserAccountDetail.setUuid(uuid);
        updUserAccountDetail.setStatus(UserAccountDetailDto.STATUS_SUCCESS);
        updUserAccountDetail.setPayed(payed);//已支付
        updUserAccountDetail.setUpdateTime(new Date());
        userAccountDetailMapper.edit(updUserAccountDetail);
    }


    /**
     * 订单支付回调成功
     */
    private void orderPayCallbackSuccess(UserAccountDetailDto userAccountDetailDto, int orderPayType) {
        logger.log(QxLog.LEVEL, "微信公众号订单支付回调成功...");
        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", userAccountDetailDto.getOrderUuid());
        OrderDto order = orderMapper.selInfo(params);
        if (order == null) {
            BizValidate.bizErr("订单不存在", ReturnCodeConstants.ERR_ORDER_NOT_EXIST);
        }

        //更新订单状态
        OrderDto updOrder = new OrderDto();
        updOrder.setUuid(userAccountDetailDto.getOrderUuid());
        updOrder.setInsPayFare(userAccountDetailDto.getMoney());
        updOrder.setWaitPayFee(0d);
        updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_FINISH);
        updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_FINISH_UN_EVA);//未评价
        updOrder.setPayType(orderPayType);
        //todo：vip快速通道
        if (null != order.getVipCounts() && order.getVipCounts() > 0 && order.getSubStatus() != 401 ) { //排除取消支付的
            updOrder.setVipFlag(1);
            updOrder.setVipEffTime(new Date());
            OrderVipHelper.sendVipCountMsgByOrder(order,fzVipPhones);
        }
        //20170806新增订单结算时间
        updOrder.setPayTime(new Date());
        orderMapper.edit(updOrder);//更新订单
        //20170628加短信通知
        order.setPayFare(order.getPayFare() + userAccountDetailDto.getMoney());
        GuoDouSmsUtils.sendOrderPayedNotice2Passenger(order);

        //更新优惠券状态
        params.clear();
        params.put("orderUuid", userAccountDetailDto.getOrderUuid());
        List<UserCouponDto> userCoupons = userCouponMapper.listMap(params);
        if (userCoupons != null && userCoupons.size() > 0) {
            UserCouponDto userCoupon = userCoupons.get(0);
            logger.log(QxLog.LEVEL, "回调成功，更新优惠券" + userCoupon.getUuid());
            UserCouponDto updUserCoupon = new UserCouponDto();
            updUserCoupon.setUuid(userCoupon.getUuid());
            updUserCoupon.setStatus(UserCouponDto.STATUS_USED);//已用
            updUserCoupon.setUpdateTime(new Date());
            userCouponMapper.edit(updUserCoupon);

            //更新明细（写入优惠券金额和名字）
            OrderCostDetailDto updCostDetail = new OrderCostDetailDto();
            updCostDetail.setOrderUuid(order.getUuid());
            updCostDetail.setCouponFee(userCoupon.getMoney());
            updCostDetail.setCouponName(userCoupon.getCouponDetail() == null ? "" : userCoupon.getCouponDetail().getName());
            orderCostDetailMapper.edit(updCostDetail);
        }

        //司机信息
        params.clear();
        params.put("uuid", order.getActualDriverUuid());
        DriverDto driver = driverMapper.selInfo(params);
        //添加司机明细
        DriverAccountDetailDto driverAccountDetail = new DriverAccountDetailDto();
        driverAccountDetail.setDriverUuid(driver.getUuid());
        driverAccountDetail.setSerialNumber(userAccountDetailDto.getSerialNumber());
        driverAccountDetail.setUuid(StringUtil.buildUUID());
        driverAccountDetail.setPayOrderUuid(userAccountDetailDto.getOrderUuid());
        driverAccountDetail.setDriverMobile(driver.getMobile());
        driverAccountDetail.setMoney(order.getWaitPayFee() == null ? 0 : order.getWaitPayFee()); //取待支付金额
        driverAccountDetail.setType(DriverAccountDetailDto.TYPE_INCOME_FROM_ORDER);//订单收入
        driverAccountDetail.setBalance(driver.getBalance() + driverAccountDetail.getMoney());
        driverAccountDetail.setPayed(1);
        driverAccountDetail.setPayType(orderPayType);
        driverAccountDetail.setCreateTime(new Date());
        driverAccountDetail.setRemark("车费收入");
        driverAccountDetail.setStatus(DriverAccountDetailDto.STATUS_SUCCESS);
        driverAccountDetailMapper.add(driverAccountDetail);

        //更新司机余额
        if (driverAccountDetail.getMoney() > 0) {
            DriverDto updDriver = new DriverDto();
            updDriver.setInsBalance(driverAccountDetail.getMoney());
            updDriver.setUuid(driver.getUuid());
            driverMapper.edit(updDriver);
        }


        //返回给司机详细订单
        order.setPayFare(userAccountDetailDto.getMoney());
        order.setMainStatus(OrderDto.ORDER_MAIN_STATUS_FINISH);
        order.setSubStatus(OrderDto.ORDER_SUB_STATUS_FINISH_UN_EVA);//未评价
        order.setPayType(orderPayType);
        //推送
        WebSocketUtil.sendObjMessageByToken(driver.getUuid(),
                WsMsg.createSuccess4Order("乘客已支付车费",
                        order.toMap4WebSocketWithPayFee(order.getTotalFee()),
                        WsMsg.OPERATE_CODE_ORDER_PASSENGER_ORDER_PAYED,
                        WsMsg.To.DRIVER));
    }


}
