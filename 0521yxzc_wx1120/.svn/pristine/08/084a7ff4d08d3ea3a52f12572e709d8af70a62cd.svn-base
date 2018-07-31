package com.plugs.module_system.dtos;

import java.util.Date;

public class SysCarBillingWayDto{
	private String uuid;		//主键
	private int levelType;		//级别（1：经济七座、2：商务七座、3：豪华商务）
	private Integer orderType;//订单类型（1.预约  2.接机 3送机 4.机场叫车,5立即用车，6,半日租 ,7日租 ）
	private double startFee;		//起步费
	private double startTrip;		//起步公里
	private double startDuration;		//起步时长(分钟)
	private double beyondTripFee;		//超出里程价格(元/公里)
	private double beyondTimeFee;		//超出时长费用（元/分钟）
	private double freeWaitTime;		//免费等待时间（超过这个时间才生效）
	private double beyondWaitTime;		//超出等待时长费用（元/分钟）
	private String nightTimeStr;		//夜间开始时段(22:00-6:00)
	private double nightTripFee;		//夜间里程费（元/公里）
	private double haulBackTrip;		//回空里程（超过这个里程才生效）
	private double haulBackTripFee;		//回空里程费用（元/公里）

	private Double autonomyCancelFee;        //司机到达目的地后，自主取消费用
	private Double adminCancelFee;        //司机到达目的地后，客服取消费用
	private Double driverAssignFee;        //司机指定费用
	private int status;		//状态 （1.启用  2.未启用）
	private Date createTime;		//创建时间
	private String creator;		//创建人
	private Date updateTime;		//更新时间

	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	public String getUuid(){
		return uuid;
	}

	public int getLevelType() {
		return levelType;
	}

	public void setLevelType(int levelType) {
		this.levelType = levelType;
	}

	public Integer getOrderType() { return orderType; }

	public void setOrderType(Integer orderType) { this.orderType = orderType; }

	public double getStartFee() {
		return startFee;
	}

	public void setStartFee(double startFee) {
		this.startFee = startFee;
	}

	public void setStartTrip(double startTrip){
		this.startTrip = startTrip;
	}

	public double getStartTrip(){
		return startTrip;
	}

	public void setStartDuration(double startDuration){
		this.startDuration = startDuration;
	}

	public double getStartDuration(){
		return startDuration;
	}

	public void setBeyondTripFee(double beyondTripFee){
		this.beyondTripFee = beyondTripFee;
	}

	public double getBeyondTripFee(){
		return beyondTripFee;
	}

	public void setBeyondTimeFee(double beyondTimeFee){
		this.beyondTimeFee = beyondTimeFee;
	}

	public double getBeyondTimeFee(){
		return beyondTimeFee;
	}

	public void setFreeWaitTime(double freeWaitTime){
		this.freeWaitTime = freeWaitTime;
	}

	public double getFreeWaitTime(){
		return freeWaitTime;
	}

	public void setBeyondWaitTime(double beyondWaitTime){
		this.beyondWaitTime = beyondWaitTime;
	}

	public double getBeyondWaitTime(){
		return beyondWaitTime;
	}

	public void setNightTimeStr(String nightTimeStr){
		this.nightTimeStr = nightTimeStr;
	}

	public String getNightTimeStr(){
		return nightTimeStr;
	}

	public void setNightTripFee(double nightTripFee){
		this.nightTripFee = nightTripFee;
	}

	public double getNightTripFee(){
		return nightTripFee;
	}

	public void setHaulBackTrip(double haulBackTrip){
		this.haulBackTrip = haulBackTrip;
	}

	public double getHaulBackTrip(){ return haulBackTrip; }

	public void setHaulBackTripFee(double haulBackTripFee){
		this.haulBackTripFee = haulBackTripFee;
	}

	public double getHaulBackTripFee(){
		return haulBackTripFee;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreator(String creator){
		this.creator = creator;
	}

	public String getCreator(){
		return creator;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Date getUpdateTime(){
		return updateTime;
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
}
