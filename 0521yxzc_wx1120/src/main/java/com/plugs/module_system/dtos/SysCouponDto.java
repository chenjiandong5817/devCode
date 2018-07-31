package com.plugs.module_system.dtos;


import java.util.Date;

/**
 * 电子券实体
 *
 * @author Zoro
 * @since 2016/09/24
 */
public class SysCouponDto {

    public final static int USE_CAR_TYPE_NOT_LIMIT = 0;//不限
    public final static int USE_CAR_TYPE_ORDER = 1;//预约用车
    public final static int USE_CAR_TYPE_PICKUP = 2;//接机
    public final static int USE_CAR_TYPE_SEND = 3;//送机

    public final static int COUPON_TYPE_DISCOUNT = 1;//打折
    public final static int COUPON_TYPE_MONEY = 2;//金额

    public final static int STATUS_ENABLE = 1;//有效
    public final static int STATUS_UNABLE = 0;//无效


    private String uuid;        //优惠券ID
    private String name;        //名称
    private int type;        //类型1打折，2金额
    private double money;        //金额
    private Integer status;        //状态 0：无效，1：有效
    private String instruction;        //使用说明
    private String useCondition;        //使用条件
    private double astrict;        //条件金额
    private int grantNumber;        //发放数量
    private int remainNumber;        //剩余数量
    private int sendMode;        //发放方式 (0代表系统发放，1代表手动发放，2代表随活动发放，3:注册发放)
    private int getMode;        //获得方式 (0代表限领一张，1代表每天限领一张，2代表不限张数)
    private int useMode;        //使用方式 (0代表可以叠加，1代表不可以叠加)
    private int userRankLower;        //最低用户级别 [保留，暂不使用]
    private Date sendStartTime;        //发放开始时间
    private Date sendEndTime;        //发放结束时间
    private Date useStartTime;        //使用开始时间
    private Date useEndTime;        //使用结束时间
    private int useExpireTime;        //获取后有效天数
    private Date createTime;        //创建时间
    private String creator;        //创建人
    private Date updateTime;        //更新时间
    private String updator;        //更新者
    private int termType;      //期限类型 1.固定时间、2.天数
    /**
     * 20161014新增字段
     **/
    private double discount;     //折扣
    private int useCarType;     //用车类型（0：不限，1：预约用车，2：接机 3:送机）
    /**161028新增注册期间 为空则表示不限期限**/
    private Date regStartTime; //注册期间起始时间
    private Date regEndTime; //注册期间结束时间
    private String userMobiles; //指定对象（目前为用户手机号码）

    //非数据库字段
    private int decRemainNumber;
    private double highestMoney;// 最高抵扣金额


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public void setInstruction(String Instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setUseCondition(String useCondition) {
        this.useCondition = useCondition;
    }

    public String getUseCondition() {
        return useCondition;
    }

    public void setAstrict(double astrict) {
        this.astrict = astrict;
    }

    public double getAstrict() {
        return astrict;
    }

    public void setGrantNumber(int grantNumber) {
        this.grantNumber = grantNumber;
    }

    public int getGrantNumber() {
        return grantNumber;
    }

    public void setRemainNumber(int remainNumber) {
        this.remainNumber = remainNumber;
    }

    public int getRemainNumber() {
        return remainNumber;
    }

    public void setSendMode(int sendMode) {
        this.sendMode = sendMode;
    }

    public int getSendMode() {
        return sendMode;
    }

    public void setGetMode(int getMode) {
        this.getMode = getMode;
    }

    public int getGetMode() {
        return getMode;
    }

    public void setUseMode(int useMode) {
        this.useMode = useMode;
    }

    public int getUseMode() {
        return useMode;
    }

    public void setUserRankLower(int userRankLower) {
        this.userRankLower = userRankLower;
    }

    public int getUserRankLower() {
        return userRankLower;
    }

    public void setSendStartTime(Date sendStartTime) {
        this.sendStartTime = sendStartTime;
    }

    public Date getSendStartTime() {
        return sendStartTime;
    }

    public void setSendEndTime(Date sendEndTime) {
        this.sendEndTime = sendEndTime;
    }

    public Date getSendEndTime() {
        return sendEndTime;
    }

    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    public Date getUseStartTime() {
        return useStartTime;
    }

    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
    }

    public Date getUseEndTime() {
        return useEndTime;
    }

    public void setUseExpireTime(int useExpireTime) {
        this.useExpireTime = useExpireTime;
    }

    public int getUseExpireTime() {
        return useExpireTime;
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

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getUpdator() {
        return updator;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getUseCarType() {
        return useCarType;
    }

    public void setUseCarType(int useCarType) {
        this.useCarType = useCarType;
    }

    public Date getRegStartTime() {
        return regStartTime;
    }

    public void setRegStartTime(Date regStartTime) {
        this.regStartTime = regStartTime;
    }

    public Date getRegEndTime() {
        return regEndTime;
    }

    public void setRegEndTime(Date regEndTime) {
        this.regEndTime = regEndTime;
    }

    public String getUserMobiles() {
        return userMobiles;
    }
    public void setUserMobiles(String userMobiles) {
        this.userMobiles = userMobiles;
    }
    public int getDecRemainNumber() {
        return decRemainNumber;
    }

    public void setDecRemainNumber(int decRemainNumber) {
        this.decRemainNumber = decRemainNumber;
    }

    public double getHighestMoney() {
        return highestMoney;
    }

    public void setHighestMoney(double highestMoney) {
        this.highestMoney = highestMoney;
    }

    public int getTermType() {
        return termType;
    }

    public void setTermType(int termType) {
        this.termType = termType;
    }
}

