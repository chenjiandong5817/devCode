package com.plugs.module_common.dtos;

import com.plugs.constants.ConfigConstants;
import com.plugs.module_user.dtos.UserAccountDetailDto;
import com.util.StringUtil;

import java.util.Date;
import java.util.Map;

/**
 * 支付记录实体
 *
 * @author Zoro
 * @since 2016/9/21
 */
public class CommonPayLogDto {

    public static final int FROM_TYPE_USER = 1;
    public static final int FROM_TYPE_DRIVER = 2;
    public static final int FROM_TYPE_ENT = 3;

    private String uuid;        //ID
    private int fromType; //来自（1：用户2司机3企业）
    private String tradeNumber;        //订单交易号
    private String payPlatformSerialNum;        //流水单号 由支付平台返回
    private String useType;        //支付用途
    private int type;        //类别 0：线下支付，1：线上支付
    private int payType;        //付款类型 0：线下支付，1：支付宝，2：微信，3：快钱支付
    private String payAccount;        //付款账号
    private String payName;        //付款人
    private String collectAccount;        //收款账号
    private String collectName;        //收款人
    private int collectType;        //收款类型 0：线下支付，1：支付宝，2：微信，3：快钱
    private double money;        //金额
    private int status;        //状态 支付平台返回值
    private Date paidTime;        //支付日期
    private Date callbackTime;        //回调时间
    private Date createTime;        //创建时间
    private Date updateTime;        //更新时间
    private String creator;        //创建者

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public int getFromType() {
        return fromType;
    }

    public void setFromType(int fromType) {
        this.fromType = fromType;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getPayPlatformSerialNum() {
        return payPlatformSerialNum;
    }

    public void setPayPlatformSerialNum(String payPlatformSerialNum) {
        this.payPlatformSerialNum = payPlatformSerialNum;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getUseType() {
        return useType;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayName(String payName) {
        if (payName==null){
            payName="";
        }
        if (payName.length()>17){
            payName=payName.substring(0,16);
        }
        this.payName = payName;
    }

    public String getPayName() {
        return payName;
    }

    public void setCollectAccount(String collectAccount) {
        this.collectAccount = collectAccount;
    }

    public String getCollectAccount() {
        return collectAccount;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public String getCollectName() {
        return collectName;
    }

    public void setCollectType(int collectType) {
        this.collectType = collectType;
    }

    public int getCollectType() {
        return collectType;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setPaidTime(Date paidTime) {
        this.paidTime = paidTime;
    }

    public Date getPaidTime() {
        return paidTime;
    }

    public void setCallbackTime(Date callbackTime) {
        this.callbackTime = callbackTime;
    }

    public Date getCallbackTime() {
        return callbackTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    /**
     * 创建支付记录
     *
     * @param serialNumber
     * @param useType
     * @param type
     * @param payType
     * @param payName
     * @param collectType
     * @param money
     * @param status
     * @param creator
     * @return
     */
    public static CommonPayLogDto create(int fromType, String serialNumber, String useType, int type, int payType, String payName, int collectType, double money, int status, String creator) {
        CommonPayLogDto payLog = new CommonPayLogDto();
        payLog.setFromType(fromType);
        payLog.setUuid(StringUtil.buildUUID());
        payLog.setTradeNumber(serialNumber);//订单交易号
        payLog.setUseType(useType);// 支付用途
        payLog.setType(type);// 类别 0：线下支付，1：线上支付
        payLog.setPayType(payType);// 付款类型 0：线下支付，1：支付宝，2：微信，3：快钱支付
        payLog.setPayName(payName);//存储付款人手机号// 付款人
        //payLog.setCollectName(CommonConstants.COLLECT_NAME);// 收款人
        payLog.setCollectType(collectType);// 收款类型 0：线下支付，1：支付宝，2：微信
        payLog.setMoney(money);// 金额
        payLog.setStatus(status);// 状态(支付平台返回的值)
        payLog.setCreateTime(new Date());// 生成日期
        payLog.setCreator(creator);
        return payLog;
    }

    /**
     * 创建用户支付宝充值支付记录
     *
     * @param userAccountDetailDto
     * @param reqMap
     * @return
     */
    public static CommonPayLogDto createUserAliRechargePayLog(UserAccountDetailDto userAccountDetailDto, Map<String, String> reqMap) {
        CommonPayLogDto payLog = new CommonPayLogDto();
        Date sysTime = new Date();
        payLog.setFromType(CommonPayLogDto.FROM_TYPE_USER);
        payLog.setUuid(StringUtil.buildUUID());
        payLog.setTradeNumber(userAccountDetailDto.getSerialNumber());// 订单号
        payLog.setPayPlatformSerialNum(reqMap.get("trade_no"));// 支付宝交易号
        payLog.setUseType("支付宝充值");// 支付用途
        payLog.setType(1);// 类别 0：线下支付，1：线上支付
        payLog.setPayType(ConfigConstants.PAY_TYPE_ALIPAY);// 付款类型 0：线下支付，1：支付宝，2：微信
        //payLog.setPayName(rechargeLog.getUserMobile());// 付款人
        //payLog.setCollectName(CommonConstants.COLLECT_NAME);// 收款人
        payLog.setCollectType(ConfigConstants.PAY_TYPE_ALIPAY);// 收款类型 0：线下支付，1：支付宝，2：微信
        payLog.setMoney(Double.valueOf(reqMap.get("total_fee")));// 金额
        payLog.setStatus(ConfigConstants.PAY_STATUS_OK);// 已付款
        payLog.setCreateTime(sysTime);// 生成日期
        payLog.setPaidTime(sysTime);// 支付日期
        payLog.setCallbackTime(sysTime);// 回调时间
        payLog.setPayAccount(reqMap.get("buyer_id"));// 付款支付宝账号
        payLog.setCollectAccount(reqMap.get("seller_id"));// 收款支付宝账号
        return payLog;
    }

    /**
     * 创建用户支付宝充值支付记录
     *
     * @param userAccountDetailDto
     * @param reqMap
     * @return
     */
    public static CommonPayLogDto createUserAliOrderPayLog(UserAccountDetailDto userAccountDetailDto, Map<String, String> reqMap) {
        CommonPayLogDto payLog = new CommonPayLogDto();
        Date sysTime = new Date();
        payLog.setFromType(CommonPayLogDto.FROM_TYPE_USER);
        payLog.setUuid(StringUtil.buildUUID());
        payLog.setTradeNumber(userAccountDetailDto.getSerialNumber());// 订单号
        payLog.setPayPlatformSerialNum(reqMap.get("trade_no"));// 支付宝交易号
        payLog.setUseType("在线支付车费");// 支付用途
        payLog.setType(1);// 类别 0：线下支付，1：线上支付
        payLog.setPayType(ConfigConstants.PAY_TYPE_ALIPAY);// 付款类型 0：线下支付，1：支付宝，2：微信
        //payLog.setPayName(rechargeLog.getUserMobile());// 付款人
        //payLog.setCollectName(CommonConstants.COLLECT_NAME);// 收款人
        payLog.setCollectType(ConfigConstants.PAY_TYPE_ALIPAY);// 收款类型 0：线下支付，1：支付宝，2：微信
        payLog.setMoney(Double.valueOf(reqMap.get("total_fee")));// 金额
        payLog.setStatus(ConfigConstants.PAY_STATUS_OK);// 已付款
        payLog.setCreateTime(sysTime);// 生成日期
        payLog.setPaidTime(sysTime);// 支付日期
        payLog.setCallbackTime(sysTime);// 回调时间
        payLog.setPayAccount(reqMap.get("buyer_id"));// 付款支付宝账号
        payLog.setCollectAccount(reqMap.get("seller_id"));// 收款支付宝账号
        return payLog;
    }

    /**
     * 创建用户微信充值支付记录
     *
     * @param userAccountDetailDto
     * @param requestMap
     * @return
     */
    public static CommonPayLogDto createUserTenpayRechargePayLog(UserAccountDetailDto userAccountDetailDto, Map<String, String> requestMap) {
        CommonPayLogDto payLog = new CommonPayLogDto();
        Date sysTime = new Date();
        payLog.setFromType(CommonPayLogDto.FROM_TYPE_USER);
        payLog.setUuid(StringUtil.buildUUID());
        payLog.setTradeNumber(userAccountDetailDto.getSerialNumber());// 订单号
        payLog.setPayPlatformSerialNum(requestMap.get("transaction_id"));// 支付宝交易号
        payLog.setUseType("微信充值");// 支付用途
        payLog.setType(1);// 类别 0：线下支付，1：线上支付
        payLog.setPayType(ConfigConstants.PAY_TYPE_ALIPAY);// 付款类型 0：线下支付，1：支付宝，2：微信
        payLog.setCollectAccount(requestMap.get("mch_id"));
        payLog.setPayAccount(requestMap.get("appid"));
        //payLog.setPayName(rechargeLog.getUserMobile());// 付款人
        //payLog.setCollectName(CommonConstants.COLLECT_NAME);// 收款人
        payLog.setCollectType(ConfigConstants.PAY_TYPE_TENPAY);// 收款类型 0：线下支付，1：支付宝，2：微信
        payLog.setMoney(Double.valueOf(requestMap.get("total_fee")));// 金额
        payLog.setStatus(ConfigConstants.PAY_STATUS_OK);// 已付款
        payLog.setCreateTime(sysTime);// 生成日期
        payLog.setPaidTime(sysTime);// 支付日期
        payLog.setCallbackTime(sysTime);// 回调时间
        return payLog;
    }

}
