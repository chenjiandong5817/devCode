package com.plugs.module_order.dtos;

import com.plugs.utils.StringUtils;
import com.plugs.utils.SysConfigHelper;

import java.util.Date;

/**
 * 描述:
 * 预支付订单
 *
 * @outhor qfHan
 * @create 2017-12-04 14:55
 */
public class OrderPrepaidDto {

    /**
     * 预支付状态
     */
    public final static int PAY_STATUS_INIT = 0; //1：初始化
    public final static int PAY_STATUS_WAIT = 1; //1：待支付
    public final static int PAY_STATUS_ING = 2; //2：支付中
    public final static int PAY_STATUS_SUCCESS = 3; //3：已支付
    public final static int PAY_STATUS_CANCEL = 4; //4：取消已支付
    public final static int PAY_STATUS_ERR = 5; //5:支付异常[预留]

    /**
     * 支付方式
     */
    public final static int PAY_TYPE_ENT = 1; //1：企业余额
    public final static int PAY_TYPE_OWNE = 2; //2：账户余额
    public final static int PAY_TYPE_ALI = 3; //3：支付宝
    public final static int PAY_TYPE_WX = 4; //4：微信

    private String uuid; //主键ID
    private String orderUuid; //订单主键UUID
    private String passengerUuid; //预留 乘客UUID
    private String driverUuid; //预留 司机UUID
    private String serialNumber; //流水号

    private int orderType; //订单类型【由于目前订单生产处不同使用类型字段】
    private int orderSource; //订单来源【由于目前订单生产处不同使用来源字段】
    private int acceptType; //接收类型（1.派单  2.抢单）【由于目前订单生产处不同使用来源字段】
    private double money; //预支付金额
    private double refundMoney; //可退款金额
    private int refundMoneyStatus; //可退金额是否已退款 1:可退款 todo:未初始化
    private int payType;    //支付方式（1：企业余额，2：账户余额，3：支付宝，4：微信）
    private int payStatus; //支付状态 0：初始化 1：待支付 2：支付中 3：已支付 4：支付异常 5:支付异常[预留]
    private int status; //状态：1，有效 2：失效
    private Date effectTime; //开始计算时间
    private int payTimeLimit; //预支付时效
    private Date createTime; //创建时间
    private Date updateTime; //更新时间

    //非表字段
    private long timeDifference; //系统时间与开始计算时间 时间差 毫秒

    public OrderPrepaidDto() {}

    public OrderPrepaidDto(OrderDto orderDto) {
        this.uuid = StringUtils.buildUUID();
        this.orderUuid = orderDto.getUuid();
        this.passengerUuid = orderDto.getPassengeUuid();
        this.driverUuid = orderDto.getActualDriverUuid();
        this.orderType = orderDto.getOrderType();
        this.orderSource = orderDto.getOrderSource();
        this.acceptType = orderDto.getAcceptType();
        this.money = orderDto.getPrepaidFee();
        this.payStatus = orderDto.getPrepaidStatus();
        this.status = 1;
        this.effectTime = orderDto.getDistributeTime();
        this.createTime = orderDto.getDistributeTime();
        this.payTimeLimit = SysConfigHelper.getPrepaidOrderPayTimeLimit();
    }



    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(int orderSource) {
        this.orderSource = orderSource;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPassengerUuid() {
        return passengerUuid;
    }

    public void setPassengerUuid(String passengerUuid) {
        this.passengerUuid = passengerUuid;
    }

    public String getDriverUuid() {
        return driverUuid;
    }

    public void setDriverUuid(String driverUuid) {
        this.driverUuid = driverUuid;
    }

    public int getPayTimeLimit() {
        return payTimeLimit;
    }

    public void setPayTimeLimit(int payTimeLimit) {
        this.payTimeLimit = payTimeLimit;
    }

    public long getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(long timeDifference) {
        this.timeDifference = timeDifference;
    }

    public int getAcceptType() {
        return acceptType;
    }

    public void setAcceptType(int acceptType) {
        this.acceptType = acceptType;
    }

    public int getRefundMoneyStatus() {
        return refundMoneyStatus;
    }

    public void setRefundMoneyStatus(int refundMoneyStatus) {
        this.refundMoneyStatus = refundMoneyStatus;
    }
}
