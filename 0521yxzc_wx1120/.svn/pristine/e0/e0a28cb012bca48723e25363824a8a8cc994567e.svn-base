package com.plugs.module_wechat.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.base.AjaxList;
import com.plugs.base.WsMsg;
import com.plugs.constants.ConfigConstants;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_common.dtos.CommonPayLogDto;
import com.plugs.module_common.mappers.CommonPayLogMapper;
import com.plugs.module_driver.dtos.DriverAccountDetailDto;
import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_driver.mappers.DriverAccountDetailMapper;
import com.plugs.module_driver.mappers.DriverMapper;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.mappers.OrderMapper;
import com.plugs.module_order.services.OrderService;
import com.plugs.module_order.services.OrderVipHelper;
import com.plugs.module_system.services.SysSeqService;
import com.plugs.module_user.dtos.UserAccountDetailDto;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserAccountDetailMapper;
import com.plugs.module_user.mappers.UserPassengerMapper;
import com.plugs.utils.BizValidate;
import com.plugs.utils.CommonUtils;
import com.plugs.utils.GuoDouSmsUtils;
import com.plugs.utils.WebSocketUtil;
import com.plugs.utils.pay.tenpay.TenPay;
import com.plugs.utils.pay.tenpay.util.TenpayConfig;
import com.util.MapUtil;
import com.util.StringUtil;
import com.utils.log4j.QxLog;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/31.
 */
@Service
public class WechatOrderPayHelper extends BaseServiceSupport<OrderDto> {
    private static final Logger logger = Logger.getLogger(WechatOrderPayHelper.class);

    @Autowired
    private OrderMapper<OrderDto> orderMapper;
    @Autowired
    private SysSeqService sysSeqService;//查询序列
    @Autowired
    private UserPassengerMapper<UserPassengerDto> userPassengerMapper;
    @Autowired
    private UserAccountDetailMapper<UserAccountDetailDto> userAccountDetailMapper;//企业账号明细
    @Autowired
    private DriverAccountDetailMapper<DriverAccountDetailDto> driverAccountDetailMapper;//司机账户明细
    @Autowired
    private CommonPayLogMapper<CommonPayLogDto> commonPayLogMapper;//通用支付日志
    @Autowired
    private DriverMapper<DriverDto> driverMapper;

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


    @Transactional
    public AjaxList orderPayByBalance(OrderDto order, UserPassengerDto userPassengerDto, double money, DriverDto driver) throws Exception {

        double driverIncomeFee = (order.getWaitPayFee() == null ? 0 : order.getWaitPayFee());//司机收入

        //创建账户明细
        UserAccountDetailDto userAccountDetail = this.createUserAcount(userPassengerDto, money, order, 3);

        //更新订单
        this.updateOrder(order, money);

        //扣除乘客余额
        UserPassengerDto updPassenger = new UserPassengerDto();
        updPassenger.setUuid(userPassengerDto.getUuid());
        updPassenger.setDecBalance(money);
        this.userPassengerMapper.edit(updPassenger);//更新余额

        //添加司机明细
        DriverAccountDetailDto driverAccountDetail = this.addDriverAccount(userAccountDetail, order, driver, driverIncomeFee, 3);

        //更新司机余额
        if (driverAccountDetail.getMoney() > 0) {
            DriverDto updDriver = new DriverDto();
            updDriver.setUuid(driver.getUuid());
            updDriver.setInsBalance(driverAccountDetail.getMoney());
            this.driverMapper.edit(updDriver);
        }

        //推送给司机
        order.setPayFare(money);
        order.setMainStatus(OrderDto.ORDER_MAIN_STATUS_FINISH);
        order.setSubStatus(OrderDto.ORDER_SUB_STATUS_FINISH_UN_EVA);//未评价
        order.setPayType(OrderDto.PAY_TYPE_BALANCE);
        WebSocketUtil.sendObjMessageByToken(driver.getUuid(),
                WsMsg.createSuccess4Order("乘客已通过个人余额支付", order.toMap4WebSocketWithPayFee(order.getTotalFee()), WsMsg.OPERATE_CODE_ORDER_PASSENGER_ORDER_PAYED, WsMsg.To.DRIVER));

        return AjaxList.createSuccess("支付成功");
    }


    @Transactional
    public AjaxList orderPayByWx(OrderDto order, UserPassengerDto userPassengerDto, double money, DriverDto driver,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        double driverIncomeFee = (order.getWaitPayFee() == null ? 0 : order.getWaitPayFee());//司机收入

        if (money <= 0d) {
            //预先创建账户明细
            UserAccountDetailDto userAccountDetail = this.createUserAcount(userPassengerDto, money, order, 2);

            //更新订单
            this.updateOrder(order, money);

            //添加司机明细
            DriverAccountDetailDto driverAccountDetail = this.addDriverAccount(userAccountDetail, order, driver, driverIncomeFee, 2);

            //更新司机余额
            if (driverAccountDetail.getMoney() > 0) {
                DriverDto updDriver = new DriverDto();
                updDriver.setUuid(driver.getUuid());
                updDriver.setInsBalance(driverAccountDetail.getMoney());
                this.driverMapper.edit(updDriver);
            }

            //推送
            order.setPayFare(money);
            order.setMainStatus(OrderDto.ORDER_MAIN_STATUS_FINISH);
            order.setSubStatus(OrderDto.ORDER_SUB_STATUS_FINISH_UN_EVA);//未评价
            order.setPayType(OrderDto.PAY_TYPE_BALANCE);
            WebSocketUtil.sendObjMessageByToken(driver.getUuid(),
                    WsMsg.createSuccess4Order("乘客已通过微信支付", order.toMap4WebSocketWithPayFee(order.getTotalFee()), WsMsg.OPERATE_CODE_ORDER_PASSENGER_ORDER_PAYED, WsMsg.To.DRIVER));

            return AjaxList.createSuccess("支付成功");
        }

        //预先创建账户明细
        UserAccountDetailDto userAccountDetail = UserAccountDetailDto.create(
                CommonUtils.generateSerialNumber(sysSeqService.getSerialSeqNextVal(), "ZF_WX"),//流水号
                userPassengerDto.getUuid(),//用户ID
                userPassengerDto.getMobile(),//用户电话
                UserAccountDetailDto.PAY_TYPE_WX,//第三方消费
                money,//总金额
                0,//赠送金额
                ConfigConstants.PAY_TYPE_TENPAY,//支付类型
                userPassengerDto.getUuid(),//创建者
                "微信支付车费",//备注
                order.getUuid()
        );
        //创建预支付订单
        this.userAccountDetailMapper.add(userAccountDetail);

        // 生成微信支付请求URL
        JSONObject tradeJson = new JSONObject();//返回结果
        TenpayConfig tenpayConfig = new TenpayConfig(
                ConfigConstants.PASSENGER_ORDER_JS_TEN_PAY_NOTIFY_URL, //回调地址
                ConfigConstants.PASSENGER_PAY_GOOD_NAME, //商品名称
                ConfigConstants.PASSENGER_PAY_GOOD_INFO,
                TenpayConfig.Type.WXUSER);//商品详情
        TenPay tenPay = new TenPay(tenpayConfig);
        try {
            tradeJson = tenPay.yxjsPayRequest(userPassengerDto.getOpenId(), userAccountDetail.getSerialNumber(), money,
                    CommonUtils.getRemoteHost(request), request, response);
            logger.info("tradeJson:"+tradeJson.toString());
        } catch (Exception e) {
            logger.error("生成微信预支付错误", e);
        }
        if (!"0".equals(tradeJson.get("retcode"))) {
            // 生成微信支付订单失败
            BizValidate.bizErr("生成微信支付订单失败", ReturnCodeConstants.ERR_10013_WX_PREPAYMENT_ERR);
        }

        Map<String, Object> params = MapUtil.buildMap();
        params.put("tradeNumber", userAccountDetail.getSerialNumber());
        List<CommonPayLogDto> payLogs = commonPayLogMapper.list(params);
        // 新增前检查是否有该订单号的记录 没有则新增
        if (null == payLogs || payLogs.size() < 1) {
            CommonPayLogDto payLog = CommonPayLogDto.create(
                    CommonPayLogDto.FROM_TYPE_USER,
                    userAccountDetail.getSerialNumber(),
                    "微信支付",
                    1,
                    ConfigConstants.PAY_TYPE_TENPAY,
                    userPassengerDto.getNickname(),
                    ConfigConstants.PAY_TYPE_TENPAY,
                    userAccountDetail.getMoney(),
                    ConfigConstants.PAY_STATUS_NO,
                    userPassengerDto.getUuid()
            );
            //保存到数据库
            this.commonPayLogMapper.add(payLog);
        }
        return AjaxList.createSuccess("微信预支付ID生成成功", tradeJson);
    }


    /**
     * 创建乘客账户明细
     */
    private UserAccountDetailDto createUserAcount(UserPassengerDto userPassengerDto, double money, OrderDto order, int flag){
        int type = 2;
        int payTpe = 2;
        String remark = "微信支付车费";
        if (flag == 3) { //个人余额
            type = 3;
            payTpe = 5;
            remark = "";
        }
        //创建账户明细
        UserAccountDetailDto userAccountDetail = UserAccountDetailDto.create(
                CommonUtils.generateSerialNumber(sysSeqService.getSerialSeqNextVal(), "ZF_YE"),//流水单号
                userPassengerDto.getUuid(),//用户ID
                userPassengerDto.getMobile(),//用户手机号
                type,//明细类型
                (money),//总金额
                0,//赠送金额
                payTpe,//支付方式
                userPassengerDto.getUuid(),//创建者
                remark,
                order.getUuid());
        //创建预支付订单
        userAccountDetail.setStatus(UserAccountDetailDto.STATUS_SUCCESS);
        userAccountDetail.setPayed(UserAccountDetailDto.PAYED_OK);
        this.userAccountDetailMapper.add(userAccountDetail);
        return userAccountDetail;
    }

    /**
     * 更新订单
     */
    private void updateOrder (OrderDto order, double money) {
        OrderDto updOrder = new OrderDto();
        updOrder.setUuid(order.getUuid());
        updOrder.setInsPayFare(money); //实际支付费用
        updOrder.setWaitPayFee(0d);
        updOrder.setMainStatus(OrderDto.ORDER_MAIN_STATUS_FINISH);
        updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_FINISH_UN_EVA);//未评价
        updOrder.setPayType(OrderDto.PAY_TYPE_BALANCE);
        //todo：vip快速通道
        if (null != order.getVipCounts() && order.getVipCounts() > 0 && order.getSubStatus() != 401 ) { //排除取消支付的
            updOrder.setVipFlag(1);
            updOrder.setVipEffTime(new Date());
            OrderVipHelper.sendVipCountMsgByOrder(order,fzVipPhones);
        }
        //20170628加短信通知
        order.setPayFare(order.getPayFare() + money);
        GuoDouSmsUtils.sendOrderPayedNotice2Passenger(order);
        this.orderMapper.edit(updOrder);
    }

    /**
     * 添加司机明细
     */
    private DriverAccountDetailDto addDriverAccount(UserAccountDetailDto userAccountDetail, OrderDto order,
                                                    DriverDto driver, double driverIncomeFee, int payType) {
        DriverAccountDetailDto driverAccountDetail = new DriverAccountDetailDto();
        driverAccountDetail.setUuid(StringUtil.buildUUID());
        driverAccountDetail.setSerialNumber(userAccountDetail.getSerialNumber());
        driverAccountDetail.setPayOrderUuid(order.getUuid());
        driverAccountDetail.setDriverUuid(driver.getUuid());
        driverAccountDetail.setDriverMobile(driver.getMobile());
        driverAccountDetail.setMoney(driverIncomeFee);
        driverAccountDetail.setType(DriverAccountDetailDto.TYPE_INCOME_FROM_ORDER);//订单收入
        driverAccountDetail.setBalance(driver.getBalance() + driverAccountDetail.getMoney());
        driverAccountDetail.setPayed(1);
        driverAccountDetail.setPayType(payType); //wx:2, balance:3
        driverAccountDetail.setStatus(DriverAccountDetailDto.STATUS_SUCCESS);
        driverAccountDetail.setCreateTime(new Date());
        driverAccountDetail.setRemark("车费收入");
        this.driverAccountDetailMapper.add(driverAccountDetail);
        return driverAccountDetail;
    }

}
