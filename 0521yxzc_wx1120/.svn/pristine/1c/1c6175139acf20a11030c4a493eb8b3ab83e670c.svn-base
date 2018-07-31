package com.plugs.module_user.dtos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***
 * 乘客表
 */
public class UserPassengerDto {

    private String uuid;        //主键
    private String entAccountUuid;//企业ID
    private String departmentUuid;//部门ID
    private String mobile;        //手机
    private String password;        //密码
    private String nickname;        //昵称
    private String realName;        //实名认证后的姓名
    private int sex;        //性别(1男，2女)
    private String face;        //头像
    private String weibo;        //微博相关
    private String qq;        //qq相关
    private String wechat;        //微信相关
    private String openId;        //开放平台相关ID
    private String tokenId;        //app用户登录后生成tokenId
    private String email;        //邮箱
    private int point;        //积分
    private double balance;        //账户余额
    private int status;        //状态（1.正常，2.长期封号，3.短期封号）
    private Date abortTime;        //终止时间（针对短期封号>status）
    private String abortRemark;        //封号说明
    private Date lastLogin;        //最后一次登录时间
    private int successOrder;        //成功的订单数
    private String pushId;        //推送唯一标识
    private int deviceType;        //设备类型（1.android，2.IOS）
    private String deviceToken;        //设备唯一标识
    private String deviceVersion;        //设备版本
    private String appVersion;        //使用的app版本
    private Date createTime;        //创建时间
    private Date updateTime;        //更新数据的时间
    private String creator;        //创建者
    /**
     * 16/10/24 新增
     **/
    private String entAccount;      //企业账号，用于账号密码登录企业官网

    private Double historyInvoiceAmount;//历史发票额度
    private Double billedInvoiceAmount;//已开发票额度

    //以下字段不在数据库中体现
    private Double insBalance;//增加金额
    private double decBalance;//减少金额

    private  Double insHistoryInvoiceAmount;//增加历史额度
    private  Double insBilledInvoiceAmount;//增加已开发票额度

    //20170414新增
    private Integer errCount;		//登录错误次数
    private Date errLoginTime;		//封锁时间

    //20170728新增
    private String fNumber; //翔会会员编号

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getEntAccountUuid() {
        return entAccountUuid;
    }

    public void setEntAccountUuid(String entAccountUuid) {
        this.entAccountUuid = entAccountUuid;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return face;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQq() {
        return qq;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWechat() {
        return wechat;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setAbortTime(Date abortTime) {
        this.abortTime = abortTime;
    }

    public Date getAbortTime() {
        return abortTime;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setSuccessOrder(int successOrder) {
        this.successOrder = successOrder;
    }

    public int getSuccessOrder() {
        return successOrder;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public String getAbortRemark() {
        return abortRemark;
    }

    public void setAbortRemark(String abortRemark) {
        this.abortRemark = abortRemark;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Double getInsBalance() {
        return insBalance;
    }

    public void setInsBalance(Double insBalance) {
        this.insBalance = insBalance;
    }

    public double getDecBalance() {
        return decBalance;
    }

    public void setDecBalance(double decBalance) {
        this.decBalance = decBalance;
    }

    public String getEntAccount() {
        return entAccount;
    }

    public void setEntAccount(String entAccount) {
        this.entAccount = entAccount;
    }

    public Double getHistoryInvoiceAmount() {
        return historyInvoiceAmount;
    }

    public void setHistoryInvoiceAmount(Double historyInvoiceAmount) {
        this.historyInvoiceAmount = historyInvoiceAmount;
    }

    public Double getBilledInvoiceAmount() {
        return billedInvoiceAmount;
    }

    public void setBilledInvoiceAmount(Double billedInvoiceAmount) {
        this.billedInvoiceAmount = billedInvoiceAmount;
    }

    public Double getInsHistoryInvoiceAmount() {
        return insHistoryInvoiceAmount;
    }

    public void setInsHistoryInvoiceAmount(Double insHistoryInvoiceAmount) {
        this.insHistoryInvoiceAmount = insHistoryInvoiceAmount;
    }

    public Double getInsBilledInvoiceAmount() {
        return insBilledInvoiceAmount;
    }

    public void setInsBilledInvoiceAmount(Double insBilledInvoiceAmount) {
        this.insBilledInvoiceAmount = insBilledInvoiceAmount;
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

    public Map<String, Object> toMap() {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap();
        map.put("uuid", uuid);
        map.put("mobile", mobile);
        map.put("tokenId", tokenId);
        map.put("openId", openId);
        map.put("nickname", nickname);
        map.put("realName", realName);
        map.put("face", face);
        map.put("sex", sex);
        map.put("email", email);
        return map;
    }

    public String getFNumber() {
        return fNumber;
    }

    public void setFNumber(String fNumber) {
        this.fNumber = fNumber;
    }
}
