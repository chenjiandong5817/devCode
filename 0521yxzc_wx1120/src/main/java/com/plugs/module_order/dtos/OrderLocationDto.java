package com.plugs.module_order.dtos;

import java.util.Date;

public class OrderLocationDto {

    public static final int ORDER_STATUS_BEFORE_START = -1;//行程开始前
    public static final int ORDER_STATUS_START = 1;//行程开始
    public static final int ORDER_STATUS_END = 2;//行程结束
    public static final int ORDER_STATUS_ING = 3;//行程中
    // 非订单中路径记录
    public static final int ORDER_STATUS_NOT = 9;


    private String uuid;        //
    private String orderUuid;        //
    private String driverUuid;        //
    private double lat;        //纬度
    private double lng;        //经度
    private double mileage;        //当前里程
    private double distance;        //与上一点偏移量
    private double fee;//当前费用
    private int orderStatus;//订单状态（-1:行程开始前,1：开始行程，2到达目的地，3行程中）
    private Date uploadTime;        //
    private Date createTime;

	public OrderLocationDto(){}

	public OrderLocationDto(double lng, double lat, double mileage){
		this.lng = lng;
		this.lat = lat;
		this.mileage = mileage;
	}


	public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setDriverUuid(String driverUuid) {
        this.driverUuid = driverUuid;
    }

    public String getDriverUuid() {
        return driverUuid;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLng() {
        return lng;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getMileage() {
        return mileage;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Date getCreateTime() { return createTime; }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}
