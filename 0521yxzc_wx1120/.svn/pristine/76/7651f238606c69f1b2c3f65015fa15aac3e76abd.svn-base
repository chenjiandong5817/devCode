package com.plugs.module_enterprise.dtos;

import java.util.Date;

/**
 * 企业账户明细实体
 *
 * @author Zoro
 */
public class EntAccountDetailDto {
    public static final int TYPE_RECHARGE =1;
    public static final int TYPE_ONLINE_USER_CAR_PAY = 2;
    public static final int TYPE_EMPLOYEE_PAY = 3;
    public static final int TYPE_ADMIN_PAY = 5;

    private String uuid;        //ID
    private String serialNumber;        //流水号
    private int type;        //类型（1充值到账户，2在线叫车支付，3员工支付)
    private String entUuid;        //企业ID
    private String entMobile;        //用户手机
    private String userUuid;        //用户ID
    private double money;        //金额（总金额）
    private double giftMoney;        //赠送金额
    private int payType;        //支付类型（1支付宝，2微信，3信用卡，4苹果，5后台充值）
    private int payed;        //充值是否已付款(1是0否)
    private double balance;        //账户余额
    private int status;        //-1失败，1成功，2进行中
    private String remark;        //备注
    private Date createTime;        //创建时间
    private String creator;        //创建者
    private Date updateTime;        //更新时间
    private String orderUuid;//订单uuid

    /**非表字段 161021新增**/
    private String nickName; //用户昵称
    private String entName; //企业名称


    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setEntUuid(String entUuid) {
        this.entUuid = entUuid;
    }

    public String getEntUuid() {
        return entUuid;
    }

    public void setEntMobile(String entMobile) {
        this.entMobile = entMobile;
    }

    public String getEntMobile() {
        return entMobile;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public void setGiftMoney(double giftMoney) {
        this.giftMoney = giftMoney;
    }

    public double getGiftMoney() {
        return giftMoney;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayed(int payed) {
        this.payed = payed;
    }

    public int getPayed() {
        return payed;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }
}
