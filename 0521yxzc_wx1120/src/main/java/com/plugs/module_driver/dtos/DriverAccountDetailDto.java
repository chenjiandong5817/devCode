package com.plugs.module_driver.dtos;

import com.plugs.constants.ConfigConstants;
import com.plugs.module_user.dtos.UserAccountDetailDto;
import com.util.StringUtil;

import java.util.Date;

public class DriverAccountDetailDto {

    public static final int TYPE_3RD_PAY = 100;
    public static final int TYPE_INCOME_FROM_ORDER = 200;

    public static final int STATUS_FAIL = -1;
    public static final int STATUS_ING = 2;
    public static final int STATUS_SUCCESS = 1;


    private String uuid;        //ID
    private String serialNumber;        //流水号
    private String payOrderUuid;//订单UUID
    private int type;        //类型（100通过第三方支付),200通过订单收入,300司机收入变更
    private String driverUuid;        //司机ID
    private String driverMobile;        //司机手机
    private double money;        //金额
    private double balance;        //账户余额
    private int payType;        //支付方式（1支付宝，2微信，3信用卡，4苹果，5后台管理）
    private int payed;        //是否已付款（1是，0否）
    private int status;        //-1失败，1成功，2进行中
    private String remark;
    private Date createTime;        //创建时间
    private String creator;        //创建者
    private Date updateTime;        //更新时间

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

    public String getPayOrderUuid() {
        return payOrderUuid;
    }

    public void setPayOrderUuid(String payOrderUuid) {
        this.payOrderUuid = payOrderUuid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setDriverUuid(String driverUuid) {
        this.driverUuid = driverUuid;
    }

    public String getDriverUuid() {
        return driverUuid;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
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

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public static DriverAccountDetailDto create(String serialNumber, String payOrderUuid, String driverUuid, String driverMobile, int type, double money, int payTpe, String creator, String remark) {
        DriverAccountDetailDto driverAccount = new DriverAccountDetailDto();
        driverAccount.setUuid(StringUtil.buildUUID());
        driverAccount.setSerialNumber(serialNumber);
        driverAccount.setPayOrderUuid(payOrderUuid);
        driverAccount.setDriverUuid(driverUuid);//用户ID
        driverAccount.setDriverMobile(driverMobile);//电话
        driverAccount.setType(type);
        driverAccount.setMoney(money);//总金额
        driverAccount.setPayType(payTpe);//支付宝
        driverAccount.setPayed(ConfigConstants.PAYD_NOT);//未支付
        driverAccount.setStatus(ConfigConstants.ACCOUNT_DETAIL_STATUS_ING);//进行中
        driverAccount.setCreateTime(new Date());
        driverAccount.setCreator(creator);//创建者
        driverAccount.setRemark(remark);
        return driverAccount;
    }

}
