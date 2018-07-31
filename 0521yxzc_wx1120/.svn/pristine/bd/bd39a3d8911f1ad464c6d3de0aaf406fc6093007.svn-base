package com.plugs.module_user.dtos;

import com.plugs.constants.ConfigConstants;
import com.plugs.utils.CommonUtils;
import com.util.StringUtil;

import java.util.Date;

/**
 * 用户账户明细实体
 *
 * @author Zoro
 * @since 2016/9/21
 */
public class UserAccountDetailDto {

    public static final int TYPE_RECHARGE = 1;
    public static final int TYPE_SEPND_3RD = 2;
    public static final int TYPE_SEPND_BALANCE = 3;

    public static final int PAY_TYPE_ALI = 1;
    public static final int PAY_TYPE_WX = 2;
    public static final int PAY_TYPE_CREDIT = 3;
    public static final int PAY_TYPE_APPLE = 4;
    public static final int PAY_TYPE_BALANCE = 5;

    public static final int PAYED_OK = 1;
    public static final int PAYED_NOT = 0;

    public static final int STATUS_FAIL = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_ING = 2;


    private String uuid;        //ID
    private String serialNumber;        //(流水号)
    private int type;        //类型（1充值到账户，2,通过第三方消费，3余额消费，4后台金额变更）
    private String userUuid;        //用户ID
    private String userMobile;        //用户手机
    private double money;        //金额(总金额)
    private double giftMoney;        //赠送金额
    private int payType;        //支付类型（1支付宝，2微信，3信用卡，4苹果，5余额，6后台管理）
    private int payed;        //充值是否已付款(1是0否)
    private double balance;        //账户余额
    private int status;        //2失败，1成功，3进行中
    private String remark;//备注
    private Date createTime;        //创建时间
    private String creator;        //创建者
    private Date updateTime;        //更新时间
    private String orderUuid; //订单UUid

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public double getGiftMoney() {
        return giftMoney;
    }

    public void setGiftMoney(double giftMoney) {
        this.giftMoney = giftMoney;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayed() {
        return payed;
    }

    public void setPayed(int payed) {
        this.payed = payed;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    /**
     * 创建用户明细
     *
     * @param serialNumber
     * @param userUuid
     * @param userMobile
     * @param type
     * @param money
     * @param giftMoney
     * @param payTpe
     * @param creator
     * @return
     */
    public static UserAccountDetailDto create(String serialNumber, String userUuid, String userMobile, int type, double money, double giftMoney, int payTpe, String creator, String remark, String orderUuid) {
        UserAccountDetailDto userAccountDetail = new UserAccountDetailDto();
        userAccountDetail.setUuid(StringUtil.buildUUID());
        userAccountDetail.setSerialNumber(serialNumber);
        userAccountDetail.setUserUuid(userUuid);//用户ID
        userAccountDetail.setUserMobile(userMobile);//电话
        userAccountDetail.setType(type);
        userAccountDetail.setMoney(money);//总金额
        userAccountDetail.setGiftMoney(giftMoney);
        userAccountDetail.setPayType(payTpe);//支付宝
        userAccountDetail.setPayed(ConfigConstants.PAYD_NOT);//未支付
        userAccountDetail.setStatus(ConfigConstants.ACCOUNT_DETAIL_STATUS_ING);//进行中
        userAccountDetail.setCreateTime(new Date());
        userAccountDetail.setCreator(creator);//创建者
        userAccountDetail.setRemark(remark);
        userAccountDetail.setOrderUuid(orderUuid);
        return userAccountDetail;
    }

    public static String getItemByCode(int payType) {
        String item = "支付";
        if (payType == 1) {
            item = "支付宝支付";
        } else if (payType == 2) {
            item = "微信支付";
        } else if (payType == 3) {
            item = "信用卡";
        } else if (payType == 4) {
            item = "苹果";
        } else if (payType == 5) {
            item = "余额";
        }

        return item;
    }
}
