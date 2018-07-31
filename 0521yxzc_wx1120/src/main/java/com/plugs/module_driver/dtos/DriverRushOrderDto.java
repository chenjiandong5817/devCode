package com.plugs.module_driver.dtos;

import java.util.Date;

public class DriverRushOrderDto{
	private String uuid;		//主键
	private String orderUuid;		//订单UUID
	private String driverUuid;		//司机UUID
	private Date rushTime;		//抢单时间

	public void setUuid(String uuid){
		this.uuid = uuid;
	}
	
	public String getUuid(){
		return uuid;
	}
	
	public void setOrderUuid(String orderUuid){
		this.orderUuid = orderUuid;
	}
	
	public String getOrderUuid(){
		return orderUuid;
	}
	
	public void setDriverUuid(String driverUuid){
		this.driverUuid = driverUuid;
	}
	
	public String getDriverUuid(){
		return driverUuid;
	}
	
	public void setRushTime(Date rushTime){
		this.rushTime = rushTime;
	}
	
	public Date getRushTime(){
		return rushTime;
	}
	
}
