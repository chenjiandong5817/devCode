package com.plugs.module_driver.dtos;

import java.util.Date;

/*
*电子围栏区域司机队列表
*
* */
public class DriverQueueDto {

    private String uuid;
    private String fenceUuid; // 所属商圈
    private String airportName; // 机场名称
    private Integer airportType; // 机场类型 0.T4；1.T3
    private Integer carType; // 车类型 级别（1：经济七座、2：商务七座、3：豪华商务）
    private String carTypeName;//车名称　时尚型或者经济７座
    private String driverUuid; // 司机id
    private String driverName; // 司机名称
    private String plateNumber; // 车牌号

    /*
    * 1、司机只有在isQueue=1 && isTakeOrder=1时，才满足接单条件，需要在队列中显示
    * 2、状态isQueue=1 ，isTakeOrder=0时，只满足排队条件，需要在队列中显示
    * 3、状态isQueue=0时，isTakeOrder都为0
    */
    private int isQueue; // 是否在队列：0、不在队列；1、在队列;
    private int isTakeOrder; // 是否可接单：0、不可以；1、可以； 默认为0，不可接单

    private Integer queueNum; // 司机当前排队序号
    private Date createTime;        // 创建时间
    private String creator;        //创建人
    private Date updateTime;        //更新时间
    private Date checkLineUpTime;        //检查司机排队时间，处理司机在排队情况出现位置漂移的问题，即司机在排队状态，却不在商圈范围内，处理方式：5分钟内上传点都不在商圈内，则剔除队列

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFenceUuid() {
        return fenceUuid;
    }

    public void setFenceUuid(String fenceUuid) {
        this.fenceUuid = fenceUuid;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }


    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getDriverUuid() {
        return driverUuid;
    }

    public void setDriverUuid(String driverUuid) {
        this.driverUuid = driverUuid;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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

    public Integer getAirportType() {
        return airportType;
    }

    public void setAirportType(Integer airportType) {
        this.airportType = airportType;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public Integer getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(Integer queueNum) {
        this.queueNum = queueNum;
    }

    public int getIsQueue() {
        return isQueue;
    }

    public void setIsQueue(int isQueue) {
        this.isQueue = isQueue;
    }

    public int getIsTakeOrder() {
        return isTakeOrder;
    }

    public void setIsTakeOrder(int isTakeOrder) {
        this.isTakeOrder = isTakeOrder;
    }

    public Date getCheckLineUpTime() {
        return checkLineUpTime;
    }

    public void setCheckLineUpTime(Date checkLineUpTime) {
        this.checkLineUpTime = checkLineUpTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DriverQueueDto) {
            if (this.getUuid().equals(((DriverQueueDto) obj).getUuid())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
