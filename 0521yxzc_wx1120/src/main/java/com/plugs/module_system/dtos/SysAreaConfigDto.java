/*
 * SummerSoft  YueYue-Travel Platform
 * <p>
 * Copyright (c) 2017-2018  SummerSoft Technology (Xiamen) Co.,LTD
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of SummerSoft
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SummerSoft.
 * <p>
 */

package com.plugs.module_system.dtos;

import java.util.Date;

public class SysAreaConfigDto {

    private String uuid;//主键ID
    private String areaUuid;//区域主键ID
    private Integer bannerStatus;//横幅状态
    private String bannerTitle;//横幅标题
    private String bannerUrl;//横幅链接
    private Integer flightStatus;//接送机状态
    private Integer flightVipStatus;//接送机特权状态
    private String flightVipName;//接送机特权名称
    private String flightVipExplain;//接送机特权说明
    private Integer walkStatus;//立即/预约用车状态
    private Integer rentStatus;//日租/半日租状态
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    //20171204
    private int prepaidStatus; //预支付状态
    private int prepaidPercent; //预支付比例
    private int prepaidTime; //预支付时长

    //20171206预设计参数
    private int levelType1Status; //时尚型是否启用
    private int levelType2Status; //经济七座型是否启用
    private int levelType3Status; //豪华型是否启用

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAreaUuid() {
        return areaUuid;
    }

    public void setAreaUuid(String areaUuid) {
        this.areaUuid = areaUuid;
    }

    public Integer getBannerStatus() {
        return bannerStatus;
    }

    public void setBannerStatus(Integer bannerStatus) {
        this.bannerStatus = bannerStatus;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Integer getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(Integer flightStatus) {
        this.flightStatus = flightStatus;
    }

    public Integer getFlightVipStatus() {
        return flightVipStatus;
    }

    public void setFlightVipStatus(Integer flightVipStatus) {
        this.flightVipStatus = flightVipStatus;
    }

    public String getFlightVipName() {
        return flightVipName;
    }

    public void setFlightVipName(String flightVipName) {
        this.flightVipName = flightVipName;
    }

    public String getFlightVipExplain() {
        return flightVipExplain;
    }

    public void setFlightVipExplain(String flightVipExplain) {
        this.flightVipExplain = flightVipExplain;
    }

    public Integer getWalkStatus() {
        return walkStatus;
    }

    public void setWalkStatus(Integer walkStatus) {
        this.walkStatus = walkStatus;
    }

    public Integer getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(Integer rentStatus) {
        this.rentStatus = rentStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getPrepaidStatus() {
        return prepaidStatus;
    }

    public void setPrepaidStatus(int prepaidStatus) {
        this.prepaidStatus = prepaidStatus;
    }

    public int getPrepaidPercent() {
        return prepaidPercent;
    }

    public void setPrepaidPercent(int prepaidPercent) {
        this.prepaidPercent = prepaidPercent;
    }

    public int getPrepaidTime() {
        return prepaidTime;
    }

    public void setPrepaidTime(int prepaidTime) {
        this.prepaidTime = prepaidTime;
    }

    public int getLevelType1Status() {
        return levelType1Status;
    }

    public void setLevelType1Status(int levelType1Status) {
        this.levelType1Status = levelType1Status;
    }

    public int getLevelType2Status() {
        return levelType2Status;
    }

    public void setLevelType2Status(int levelType2Status) {
        this.levelType2Status = levelType2Status;
    }

    public int getLevelType3Status() {
        return levelType3Status;
    }

    public void setLevelType3Status(int levelType3Status) {
        this.levelType3Status = levelType3Status;
    }
}
