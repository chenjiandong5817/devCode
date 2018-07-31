package com.plugs.module_user.dtos;

import com.plugs.module_system.dtos.SysCouponDto;

import java.util.Date;

/**
 * 用户电子券实体
 *
 * @author Zoro
 * @since 2016/9/24
 */
public class UserCouponDto {

    public static final int STATUS_INVALID = 0;//无效
    public static final int STATUS_VALID = 1;//有效
    public static final int STATUS_USED = 2;//已使用
    public static final int STATUS_OCCUPY = 3;//被占用

    private String uuid;        //用户优惠券id
    private String sysCouponUuid;        //优惠券id
    private String userUuid;        //用户id
    private String serialNum;        //优惠券子序号（8位，从1开始顺序）
    private double money;        //金额
    private int status;        //状态 0：无效，1：有效，2：已使用，3：被占用，待支付成功后变为[2:已使用]
    private String orderUuid;        //订单id
    private Date useTime;        //使用时间
    private Date createTime;        //创建时间
    private String creator;        //
    private Date updateTime;        //更新时间
    private String updator;        //更新者

    //以下字段不在数据库中体现
    private SysCouponDto couponDetail;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUuid() {
        return uuid;
    }

    public String getSysCouponUuid() { return sysCouponUuid; }

    public void setSysCouponUuid(String sysCouponUuid) { this.sysCouponUuid = sysCouponUuid; }

    public String getUserUuid() { return userUuid; }

    public void setUserUuid(String userUuid) { this.userUuid = userUuid; }

    public String getSerialNum() { return serialNum; }

    public void setSerialNum(String serialNum) { this.serialNum = serialNum; }

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

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Date getUseTime() {
        return useTime;
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

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getUpdator() {
        return updator;
    }

    public SysCouponDto getCouponDetail() { return couponDetail; }

    public void setCouponDetail(SysCouponDto couponDetail) { this.couponDetail = couponDetail; }
}
