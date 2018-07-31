package com.plugs.module_system.dtos;

import java.util.Date;


/**
 * 后台用户
 */
public class SysUserDto{
	private String uuid;		//
	private String userName;		//用户名称
	private String userAccount;		//用户账号
	private String password;		//用户密码
	private String face;		//用户头像
	private String userType;		//用户类型1:超级管理员 2：管理员
	private int userUnit;		//所属单位
	private int status;		//用户状态(1:正常、2：删除)
	private int isFirst;		//是否首次登陆、后台重置密码
	private int creator;		//创建人
	private Date createTime;		//创建时间

	//20170414新增
	private Integer errCount;		//登录错误次数
	private Date errLoginTime;		//封锁时间

	/**非表字段**/
	private String roleName;	//角色名称

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setUuid(String uuid){
		this.uuid = uuid;
	}
	
	public String getUuid(){
		return uuid;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserAccount(String userAccount){
		this.userAccount = userAccount;
	}
	
	public String getUserAccount(){
		return userAccount;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setFace(String face){
		this.face = face;
	}
	
	public String getFace(){
		return face;
	}
	
	public void setUserType(String userType){
		this.userType = userType;
	}
	
	public String getUserType(){
		return userType;
	}
	
	public void setUserUnit(int userUnit){
		this.userUnit = userUnit;
	}
	
	public int getUserUnit(){
		return userUnit;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void setIsFirst(int isFirst){
		this.isFirst = isFirst;
	}
	
	public int getIsFirst(){
		return isFirst;
	}

	public Integer getErrCount() {
		return errCount;
	}

	public void setErrCount(Integer errCount) {
		this.errCount = errCount;
	}

	public Date getErrLoginTime() {
		return errLoginTime;
	}

	public void setErrLoginTime(Date errLoginTime) {
		this.errLoginTime = errLoginTime;
	}

	public void setCreator(int creator){
		this.creator = creator;
	}
	
	public int getCreator(){
		return creator;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
}
