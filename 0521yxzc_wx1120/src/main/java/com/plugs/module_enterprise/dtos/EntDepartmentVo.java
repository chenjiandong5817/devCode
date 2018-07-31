package com.plugs.module_enterprise.dtos;

/**
 * Created by Administrator on 2016/10/24.
 */
public class EntDepartmentVo {
    private String name;        //部门名称
    private String remark;        //备注
    private String uuid;        //备注
    private String limitMoney;        //月限额
    private String limitType;        //限额类型

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(String limitMoney) {
        this.limitMoney = limitMoney;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
