package com.plugs.module_system.dtos;

import java.util.Date;

/**
 * 区域实体类
 *
 * @author qfhan
 * @date 2016/12/14
 */
public class SysAreaDto {
    private String uuid;
    private String areaName; //区域管理全称
    private String areaSubName; //区域管理简称
    private String contactName; //联系人名称
    private String contactMobile; //联系人电话
    private Integer status;        //状态 0：无效，1：有效
    private String remark;  //备注
    private Date createTime;	//创建时间
    private String creator;		//创建人
    private Date updateTime;	//更新时间
    private Date closeTime;	//关闭时间
    private String closeReason;		//关闭原因
    //todo:简单快速做法，时间有限 业务没那么大
    private String cityUuids; //营运范围：已开发城市的uuid
    private String cityNames; //营运范围:城市名名称

    //非表字段
    private String name;

    private SysAreaConfigDto sysAreaConfigDto;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getCityUuids() {
        return cityUuids;
    }

    public void setCityUuids(String cityUuids) {
        this.cityUuids = cityUuids;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public String getCityNames() {
        return cityNames;
    }

    public void setCityNames(String cityNames) {
        this.cityNames = cityNames;
    }

    public String getAreaSubName() {
        return areaSubName;
    }

    public void setAreaSubName(String areaSubName) {
        this.areaSubName = areaSubName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public SysAreaConfigDto getSysAreaConfigDto() {
        return sysAreaConfigDto;
    }

    public void setSysAreaConfigDto(SysAreaConfigDto sysAreaConfigDto) {
        this.sysAreaConfigDto = sysAreaConfigDto;
    }
}
