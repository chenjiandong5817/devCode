package com.plugs.module_system.dtos;

import java.util.Date;

public class SysIdentifyDto {
	private String uuid;		//主键
	private String mobile;		//手机号码
	private String identifyCode;		//验证码
	private int codeType;		//验证码类型 （1:登陆、注册、2：修改密码）
	private int status;		//状态(1:未使用、2:已使用、3::废弃)
	private int sendType;		//发送类别(1：乘客、2：司机)
	private Date createTime;		//创建时间

	public void setUuid(String uuid){
		this.uuid = uuid;
	}
	
	public String getUuid(){
		return uuid;
	}
	
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	
	public String getMobile(){
		return mobile;
	}
	
	public void setIdentifyCode(String identifyCode){
		this.identifyCode = identifyCode;
	}
	
	public String getIdentifyCode(){
		return identifyCode;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return createTime;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSendType() {
		return sendType;
	}

	public void setSendType(int sendType) {
		this.sendType = sendType;
	}
}
