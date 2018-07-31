package com.plugs.module_order.dtos;

import java.util.Date;


/**
 * 订单配置表
 *
 * @author Zoro
 * @since 2016/11/15
 */
public class OrderConfigDto {

    public static final int FORWARD_TO_SQ_YES = 1;
    public static final int FORWARD_TO_SQ_NO = -1;

    public static final int CAR_LEVEL_UP_YES = 1;
    public static final int CAR_LEVEL_UP_NO = -1;

    private String uuid;        //主键
    private Integer orderType;        //订单类型
    private Integer conflictTimeBefore;        //订单冲突时间_前(S)
    private Integer conflictTimeAfter;        //订单冲突时间_后(S)
    private Double autonomyCancelFee;        //司机到达目的地后，自主取消费用
    private Double adminCancelFee;        //司机到达目的地后，客服取消费用
    private Double driverAssignFee;        //司机指定费用
    private Date createTime;        //创建时间

    //车型升级
    private Integer carLevelUp;//车型升级（1是，-1不是）
    private Integer forwardToSq;//转发给首汽（1是，-1不是）

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getConflictTimeBefore() {
        return conflictTimeBefore;
    }

    public void setConflictTimeBefore(Integer conflictTimeBefore) {
        this.conflictTimeBefore = conflictTimeBefore;
    }

    public Integer getConflictTimeAfter() {
        return conflictTimeAfter;
    }

    public void setConflictTimeAfter(Integer conflictTimeAfter) {
        this.conflictTimeAfter = conflictTimeAfter;
    }

    public Double getAutonomyCancelFee() {
        return autonomyCancelFee;
    }

    public void setAutonomyCancelFee(Double autonomyCancelFee) {
        this.autonomyCancelFee = autonomyCancelFee;
    }

    public Double getAdminCancelFee() {
        return adminCancelFee;
    }

    public void setAdminCancelFee(Double adminCancelFee) {
        this.adminCancelFee = adminCancelFee;
    }

    public Double getDriverAssignFee() {
        return driverAssignFee;
    }

    public void setDriverAssignFee(Double driverAssignFee) {
        this.driverAssignFee = driverAssignFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCarLevelUp() { return carLevelUp; }

    public void setCarLevelUp(Integer carLevelUp) { this.carLevelUp = carLevelUp; }

    public Integer getForwardToSq() {
        return forwardToSq;
    }

    public void setForwardToSq(Integer forwardToSq) {
        this.forwardToSq = forwardToSq;
    }
}
