package com.plugs.module_driver.dtos;

import java.util.Date;

/*
*电子围栏区域
*
* */
public class DriverElectronicFenceDto {

    private String uuid;
    private String fenceName; // 围栏名称
    private Double fenceLng; // 围栏经度
    private Double fenceLat; // 围栏纬度
    private String areaUuid; // 所属区域

    private Integer radius; // 围栏半径
    private Integer fenceType; // 围栏类型；0、圆形；1、多边形
    private String points; // 多边形坐标点
    private String fenceGid; // 围栏全局id ----- 由调取电子围栏接口成功时返回数据

    private Date createTime;        // 创建时间
    private String creator;        //创建人
    private Date updateTime;        //更新时间

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFenceName() {
        return fenceName;
    }

    public void setFenceName(String fenceName) {
        this.fenceName = fenceName;
    }

    public Double getFenceLng() {
        return fenceLng;
    }

    public void setFenceLng(Double fenceLng) {
        this.fenceLng = fenceLng;
    }

    public Double getFenceLat() {
        return fenceLat;
    }

    public void setFenceLat(Double fenceLat) {
        this.fenceLat = fenceLat;
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

    public String getAreaUuid() {
        return areaUuid;
    }

    public void setAreaUuid(String areaUuid) {
        this.areaUuid = areaUuid;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Integer getFenceType() {
        return fenceType;
    }

    public void setFenceType(Integer fenceType) {
        this.fenceType = fenceType;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getFenceGid() {
        return fenceGid;
    }

    public void setFenceGid(String fenceGid) {
        this.fenceGid = fenceGid;
    }
}
