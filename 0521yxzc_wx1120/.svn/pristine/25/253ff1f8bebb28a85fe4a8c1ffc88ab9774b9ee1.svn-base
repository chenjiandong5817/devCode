package com.plugs.module_enterprise.dtos;

import java.util.Date;

public class EntAccountRoleDto {

	public final static int MONTHLYLIMIT_YES =1;
	public final static int MONTHLYLIMIT_NO =0;


	private String uuid;		//主键
	private String entAccountUuid;		//企业账号ID
	private String userUuid;//用户UUID
	private String name;		//权限名（管理员，轿车负责人，用车人员)
	private int roleType;		//权限类型（1管理员，2轿车负责人，3用车人员)
	private int useCarPermission;		//1本人用车，2他人用车，3不限
	private int carTypePermission;		//车辆权限（1经济五座，2商务五座，3商务七座，4不限）
	private int monthlyLimit;		//是否月限额（1：是，0否）
	private double limitMoney;		//月限额金额
	private Date createTime;		//创建时间
	private String creator;		//创建人
	private Date updateTime;		//更新时间
	private String updator;		//更新者

	/**非表字段 161021新增**/
	private String nickName;	//昵称
	private String mobile;  	//手机号
	private String departName; //部门名称

	public void setUuid(String uuid){
		this.uuid = uuid;
	}
	
	public String getUuid(){
		return uuid;
	}
	
	public void setEntAccountUuid(String entAccountUuid){
		this.entAccountUuid = entAccountUuid;
	}
	
	public String getEntAccountUuid(){
		return entAccountUuid;
	}

	public String getUserUuid() { return userUuid; }

	public void setUserUuid(String userUuid) { this.userUuid = userUuid; }

	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setRoleType(int roleType){
		this.roleType = roleType;
	}
	
	public int getRoleType(){ return roleType; }
	
	public void setUseCarPermission(int useCarPermission){
		this.useCarPermission = useCarPermission;
	}
	
	public int getUseCarPermission(){
		return useCarPermission;
	}
	
	public void setCarTypePermission(int carTypePermission){
		this.carTypePermission = carTypePermission;
	}
	
	public int getCarTypePermission(){
		return carTypePermission;
	}
	
	public void setMonthlyLimit(int monthlyLimit){
		this.monthlyLimit = monthlyLimit;
	}
	
	public int getMonthlyLimit(){
		return monthlyLimit;
	}
	
	public void setLimitMoney(double limitMoney){
		this.limitMoney = limitMoney;
	}
	
	public double getLimitMoney(){
		return limitMoney;
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
	
	public void setUpdator(String updator){
		this.updator = updator;
	}
	
	public String getUpdator(){
		return updator;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
}
