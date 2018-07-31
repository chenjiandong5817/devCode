package com.plugs.module_driver.dtos;

import com.plugs.utils.MathUtils;
import com.util.MapUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 司机实体
 */
public class DriverDto {
    public static final int IS_WORK_YES = 2;
    public static final int IS_WORK_NO = 1;

    private String uuid;        //
    private String userName;        //司机姓名
    private String idCard;        //身份证号
    private String password;        //密码
    private String face;        //头像
    private Integer sex;        //性别 1.男、2.女
    private String mobile;        //手机号
    private String carUuid;        //车辆Uuid
    private String teamUuid;        //所属车队
    private String companyUuid;        //所属公司
    private int isWork;        //上下班状态（1：下班、2：上班）
    private int status;        //司机状态(1:正常、2:短期封号、3:长期封号、4:删除)
    private Date abortTime;        //终止时间（针对短期封号>status）
    private String abortRemark;        //封号说明
    private int isFirstLogin;        //是否首次登录 1：是、2：否
    private String tokenId;        //app登录后生成tokenId
    private double balance;        //账号余额
    private Date lastLogin;        //最后一次登录时间
    private String pushId;        //推送唯一标识
    private int deviceType;        //设备类型（1.android，2.IOS）
    private String deviceToken;        //设备唯一标识
    private String deviceVersion;        //设备版本
    private String appVersion;        //使用的app版本
    private Date createTime;        //
    private String creator;        //创建人
    private Date updateTime;        //更新时间
    private double currentLng;//当前经度
    private double currentLat;//当前纬度
    private double currentAngle;//当前角度
    private String currentAddress;//当前位置
    private Date locationUploadTime;//位置上传时间
    private int driverType; //司机类型 1.普通司机、2.顶班司机
    private int englishService; //英文服务 1.是、2.否
    private int isValid;//是否验证通过(1是，0否)
    private int isShowGuidePage;//是否展示过引导页(1是，0否)

    private int priorityPush;//优先推送，1是，-1不是


    //非表字段
    private int levelType;
    private String carNo;
    private String carColor;
    private String brandName;
    private int orderCount;//订单数
    private double avgScore;//平均分
    private String onTripId;//行程中 订单UUID

    private double freeWaitTime;//免费等待时长
    private double beyondWaitFee;//超过等待时长费 元/分钟

    private double insBalance;
    private double decBalance;


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return face;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setCarUuid(String carUuid) {
        this.carUuid = carUuid;
    }

    public String getCarUuid() {
        return carUuid;
    }

    public void setTeamUuid(String teamUuid) {
        this.teamUuid = teamUuid;
    }

    public String getTeamUuid() {
        return teamUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setIsWork(int isWork) {
        this.isWork = isWork;
    }

    public int getIsWork() {
        return isWork;
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

    public void setAbortRemark(String abortRemark) {
        this.abortRemark = abortRemark;
    }

    public String getAbortRemark() {
        return abortRemark;
    }

    public void setIsFirstLogin(int isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public int getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastLogin() {
        return lastLogin;
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

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public double getCurrentLng() {
        return currentLng;
    }

    public void setCurrentLng(double currentLng) {
        this.currentLng = currentLng;
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public double getCurrentAngle() {
        return currentAngle;
    }

    public void setCurrentAngle(double currentAngle) {
        this.currentAngle = currentAngle;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getDriverType() {
        return driverType;
    }

    public void setDriverType(int driverType) {
        this.driverType = driverType;
    }

    public int getEnglishService() {
        return englishService;
    }

    public void setEnglishService(int englishService) {
        this.englishService = englishService;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getAvgScore() {
        BigDecimal bd = new BigDecimal(avgScore);
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public double getFreeWaitTime() {
        return freeWaitTime;
    }

    public void setFreeWaitTime(double freeWaitTime) {
        this.freeWaitTime = freeWaitTime;
    }

    public double getBeyondWaitFee() {
        return beyondWaitFee;
    }

    public void setBeyondWaitFee(double beyondWaitFee) {
        this.beyondWaitFee = beyondWaitFee;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getIsShowGuidePage() {
        return isShowGuidePage;
    }

    public void setIsShowGuidePage(int isShowGuidePage) {
        this.isShowGuidePage = isShowGuidePage;
    }

    public double getInsBalance() {
        return insBalance;
    }

    public void setInsBalance(double insBalance) {
        this.insBalance = insBalance;
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public int getPriorityPush() {
        return priorityPush;
    }

    public void setPriorityPush(int priorityPush) {
        this.priorityPush = priorityPush;
    }

    public String getOnTripId() {
        return onTripId;
    }

    public void setOnTripId(String onTripId) {
        this.onTripId = onTripId;
    }

    public Date getLocationUploadTime() {
        return locationUploadTime;
    }

    public void setLocationUploadTime(Date locationUploadTime) {
        this.locationUploadTime = locationUploadTime;
    }

    public double getDecBalance() {
        return decBalance;
    }

    public void setDecBalance(double decBalance) {
        this.decBalance = decBalance;
    }

    /**
     * 转换成map
     *
     * @return
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap();

        map.put("uuid", uuid);
        map.put("mobile", mobile);
        map.put("tokenId", tokenId);
        map.put("face", face);
        map.put("isWork", isWork);
        map.put("userName", userName);
        map.put("isFirstLogin", isFirstLogin);
        map.put("carNo", carNo);
        map.put("brandName", brandName);
        map.put("carColor", carColor);
        map.put("avgScore", avgScore == 0 ? 0 : new DecimalFormat("#0.0").format(MathUtils.round(avgScore, 1)));
        map.put("isValid", isValid);
        map.put("isShowGuidePage", isShowGuidePage);
        map.put("levelType", levelType);
        map.put("driverType", driverType);

        map.put("currentLng", currentLng);
        map.put("currentLat", currentLat);
        map.put("currentAngle", currentAngle);
        return map;
    }

    /**
     * 推送用
     *
     * @param orderUuid
     * @return
     */
    public Map<String, Object> toMap4WebSocket(String orderUuid) {
        Map<String, Object> map = MapUtil.buildMap();

        map.put("uuid", uuid);
        map.put("mobile", mobile);
        map.put("face", face);
        map.put("isWork", isWork);
        map.put("userName", userName);
        map.put("carNo", carNo);
        map.put("brandName", brandName);
        map.put("carColor", carColor);
        map.put("avgScore", avgScore == 0 ? 0 : new DecimalFormat("#0.0").format(MathUtils.round(avgScore, 1)));
        map.put("levelType", levelType);

        map.put("currentLng", currentLng);
        map.put("currentLat", currentLat);
        map.put("currentAngle", currentAngle);

        map.put("orderUuid", orderUuid);
        map.put("freeWaitTime", freeWaitTime);
        map.put("beyondWaitFee", beyondWaitFee);
        return map;
    }

    /**
     * 扣款推送用
     *
     * @param orderUuid
     * @param totalFee
     * @param waitPayFee
     * @return
     */
    public Map<String, Object> toMap4WebSocketWithDebit(String orderUuid, Double totalFee, Double waitPayFee) {
        Map<String, Object> map = toMap4WebSocket(orderUuid);

        map.put("totalFee", totalFee);
        map.put("waitPayFee", waitPayFee);
        return map;
    }

    /**
     * 租单推送用
     *
     * @param orderUuid
     * @param totalNum
     * @param successNum
     * @return
     */
    public Map<String, Object> toMap4WebSocketWithRentOrder(String orderUuid, int totalNum, int successNum) {
        Map<String, Object> map = toMap4WebSocket(orderUuid);
        map.put("totalNum", totalNum);
        map.put("successNum", successNum);
        return map;
    }

}
