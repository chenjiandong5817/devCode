package com.plugs.module_enterprise.dtos;

import java.util.Date;

/**
 * 企业账号部门实体
 *
 * @author Zoro
 */
public class EntAccountDepartmentDto {
    public final static int MONTHLYLIMIT_YES =1;
    public final static int MONTHLYLIMIT_NO =0;

    private String uuid;        //主键
    private String entAccountUuid;        //企业账号ID
    private String name;        //部门名称
    private int monthlyLimit;        //是否月限额（1：是，0否）
    private double limitMoney;        //月限额金额
    private String remark;        //备注
    private String creator;        //创建者
    private Date createTime;        //创建时间
    private String updator;        //更新者
    private Date updateTime;        //更新时间

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setEntAccountUuid(String entAccountUuid) {
        this.entAccountUuid = entAccountUuid;
    }

    public String getEntAccountUuid() {
        return entAccountUuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMonthlyLimit(int monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public int getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setLimitMoney(double limitMoney) {
        this.limitMoney = limitMoney;
    }

    public double getLimitMoney() {
        return limitMoney;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

}
