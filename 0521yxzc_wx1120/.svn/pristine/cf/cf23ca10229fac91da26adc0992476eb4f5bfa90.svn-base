package com.plugs.module_order.dtos;

import com.plugs.utils.NumberFormatUtil;
import com.plugs.utils.StringUtils;
import com.util.MapUtil;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.Map;

/**
 * 订单实体
 *
 * @author Zoro
 * @since 2016/9/28
 */
public class OrderDto {

    /**
     * 订单主状态常量
     **/
    public final static int ORDER_MAIN_STATUS_DEFAULT = 1;  //订单默认主状态 (初始订单，等待应答)0
    public final static int ORDER_MAIN_STATUS_WAIT_MEET = 2;  //订单主状态 (等待接驾)1
    public final static int ORDER_MAIN_STATUS_ING = 3;  //订单主状态 (进行中，行程开始)2
    public final static int ORDER_MAIN_STATUS_NOT_PAY = 4;  //订单主状态 (待支付)4
    public final static int ORDER_MAIN_STATUS_FINISH = 5;  //订单主状态 (已完成)5
    public final static int ORDER_MAIN_STATUS_CANCEL = 6;  //订单主状态 (已取消)0

    /**
     * 订单子状态
     **/
    public final static int ORDER_SUB_STATUS_DEFAULT = 100;   //订单默认子状态 (初始订单)
    public final static int ORDER_SUB_STATUS_WAIT_MEET_ORDER = 200;   //订单子状态 (等待接驾-预约)
    public final static int ORDER_SUB_STATUS_WAIT_MEET_NOT_ARRIVE = 201;   //订单子状态 (等待接驾-已出发未到达)
    public final static int ORDER_SUB_STATUS_WAIT_MEET_ARRIVE = 202;   //订单子状态 (等待接驾-已到达)
    public final static int ORDER_SUB_STATUS_ING = 300;   //订单子状态 (行程开始)
    public final static int ORDER_SUB_STATUS_DRIVER_ARRIVE = 301;   //订单子状态 (行程结束)
    public final static int ORDER_SUB_STATUS_NOT_PAY = 400;   //订单子状态 (待支付)
    public final static int ORDER_SUB_STATUS_CANCEL_NOT_PAY = 401;   //订单子状态 (待支付,取消订单但产生了费用)
    public final static int ORDER_SUB_STATUS_PAYED_NOT_YET = 402;   //支付未完成
    public final static int ORDER_SUB_STATUS_FINISH_UN_EVA = 500;   //订单子状态 (已完成-待评价)
    public final static int ORDER_SUB_STATUS_FINISH_EVA = 501;   //订单子状态 (已完成-已评价)
    public final static int ORDER_SUB_STATUS_CANCEL_OWN = 600;   //订单子状态 (取消-自主取消)
    public final static int ORDER_SUB_STATUS_CANCEL_ADMIN = 601;   //订单子状态 (取消-后台取消)
    public final static int ORDER_SUB_STATUS_CANCEL_BEFORE_BEFORE_ANSWER = 602;   //订单子状态 (取消-应答前取消)
    public final static int ORDER_SUB_STATUS_CANCEL_AUTO = 603;   //订单子状态 (取消-程序自动取消)

    /**
     * 开票状态
     **/
    public final static Integer ORDER_BILL_DEFAULT_UNBILL = 0;   //订单开票默认标识 (未开票)
    public final static Integer ORDER_BILL_REVIEW = 1;   //订单开票-审核中
    public final static Integer ORDER_BILL_FINISH = 2;   //订单开票-已开票

    /**
     * 订单类型
     **/
    public final static int ORDER_TYPE_APPOINTMENT = 1;   //订单类型（预约）
    public final static int ORDER_TYPE_PICKUP = 2;    //订单类型（接机）
    public final static int ORDER_TYPE_SEND = 3;    //订单类型（送机）
    public final static int ORDER_TYPE_ARPORT = 4;    //订单类型（机场叫车）
    public final static int ORDER_TYPE_IMMED = 5;    //订单类型（立即用车）
    public final static int ORDER_TYPE_RENT_HALF_DAY = 6;    //订单类型（半日租）
    public final static int ORDER_TYPE_RENT_DAY = 7;    //订单类型（日租）

    /**
     * 接单类型
     **/
    public final static int ACCEPT_TYPE_DIS = 1;    //接收类型（派单）
    public final static int ACCEPT_TYPE_RUSH = 2;   //接收类型（抢单）

    /**
     * 允许指派其他司机
     **/
    public final static int ALLOW_ASSIGN_YES = 1;    //允许指派其他司机-是
    public final static int ALLOW_ASSIGN_NO = 0;   //允许指派其他司机-否

    /**
     * 支付方式
     */
    public final static int PAY_TYPE_ALI = 1;    //支付宝支付
    public final static int PAY_TYPE_WX = 2;   //微信支付
    public final static int PAY_TYPE_BALANCE = 3;   //余额支付
    public final static int PAY_TYPE_ENT = 4;   //企业支付
    public final static int PAY_TYPE_KQ = 5;   //快钱信用卡支付


    /**
     * 预支付方式
     */
    public final static int PREPAY_TYPE_ENT = 1;
    public final static int PREPAY_TYPE_OWN = 2;

    /**
     * 代客叫车
     */
    public final static int VALETCALL_YES = 1;
    public final static int VALETCALL_NO = -1;

    /**
     * 数据库字段
     **/
    private String uuid;        //主键
    private String passengeUuid;        //乘客ID
    private String driverUuid;        //司机ID
    private int orderType;        //订单类型（1.预约  2.接机 3送机 4.机场叫车,5立即用车，6,半日租 ,7日租 ）
    private int acceptType;        //接收类型（1.派单  2.抢单）
    private int orderSource;        //订单来源（1.APP  2.WEB  3.微信 4.自助终端 5.首汽约车）
    private String actualPassengeName;  //实际乘车人（代人叫车）
    private String actualPassengeMobile;    //实际乘车人电话（代人叫车）
    private int levelType;        //车型
    private String serviceNames;        //附加服务名
    private String servicePrice;        //附加服务费用
    private int mainStatus;        //订单主状态（1.初始（等待应答）2.等待接驾  3.进行中（行程开始）  4.待支付，5.已完成  6.已取消）
    private int subStatus;        //订单子状态（100.等待应答 200.等待接驾-预约 201.等待接驾-已出发未到达 202.等待接驾-已到达 300.行程开始 301.行程结束【目前针对微信】 400.待支付 401.待支付,取消订单但产生了费用 402自动扣款（部分） 500.已完成(待评价) 501.已完成-已评价 600.取消-自主取消 601.取消-后台取消 602.取消-应答前取消,603程序自动取消）
    private double planTrip;        //预估里程
    private double actualTrip;        //实际里程
    private double planFare;        //预估车费
    private double payFare;        //实际支付费用（加上超时等待费-优惠劵）
    private double tripTotalFare;    //行程实际费用
    private Date deparTime;        //预约时间
    private int deparTimeDiff;//预约时间差（接送机用，比如接机时间是XXX，在航班到达之后30分钟接机，这里的30就是deparTimeDiff）
    private double originLng;        //起点经度
    private double originLat;        //起点纬度
    private String originCity;      //起点城市
    private String originAddress;      //起点地址
    private double destLng;        //终点经度
    private double destLat;        //终点纬度
    private String destCity;        //终点城市
    private String destAddress;        //终点地址
    private String flightNum;        //航班号
    private String remark;        //备注
    private String cancelReason;        //取消原因
    private String closeReason;     //订单关闭原因 161028新增
    private int isBilled;       //是否开票（0.未开票  1.审核中  2.已开票）
    private String tripPic;     //行程图片（开始或者结束后保存，主要看客户端行程轨迹如何处理）
    private Date createTime;        //创建时间
    private String creator;        //
    private Date updateTime;        //更新时间
    private Date driverArriveTime;  //司机到达指定地点时间
    private Date serviceTimeStart;  //服务开始时间
    private Date serviceTimeEnd;     //服务结束时间
    private String originDetailAddress;     //起点详细地址
    private String destDetailAddress;     //终点详细地址
    private String actualDriverUuid;     //实际接单司机ID
    private int allowAssignOtherDriver;//允许指派其他司机（1允许，-1，不允许）
    private Double nightSubsidyMileageStart;//夜间补贴开始里程
    private Double nightSubsidyMileageEnd;//夜间补贴结束里程
    private String planTripPic;     //行程开始前规划图片
    private Date driverSetOutTime;//司机出发接乘客时间
    private Integer beyondWaitTime;//超时等待时间（分钟）
    private Double beyondWaitFee;//超时等待费
    private int payType;    //支付方式（1.支付宝 2.微信 3.余额 4.企业支付 5.快钱支付）
    private int prepayType;    //预支付方式（1企业支付，2个人支付）
    private double assignDriverFare;    //指定司机费用

    private int valetCall;//是否代客叫车 1.是,-1不是
    private String reassignmentRemark;//改派备注说明
    private String entUuid; //企业ID
    private String orderNumber; //订单号
    private String groupUuid; //组Uuid
    private Double tip;//小费
    private Double cancelFee;//取消费
    private Double totalFee;//总费用（优惠券抵扣前的总费用）

    //2016/12/21 对接首汽新增字段 合作者订单号,司机名称，司机电话号码
    private String partnerOrderNumber;
    private String sqDriverId;
    private String sqDriverName;
    private String sqDriverMobile;

    private Integer isCarLevelUp;//是否车型升级(-1不是，1是)

    //新增预选优惠券uuid字段
    private String preChoiceUserCouponUuid;
    //新增未支付金额
    private Double waitPayFee;//待支付金额
    //20170626vip快速通道新增字段
    private Integer vipCounts; //送机赠送的vip通道数
    private Integer vipFlag; //赠送的vip通道数是否有效
    private Date vipEffTime; //赠送的vip通道数有效时间,等于支付完成时间
    //20170719新增
    private String areaUuid; //所属区域
    //20170806新增派单成功时间
    private Date distributeTime;
    private Date payTime; //订单结算时间

    //20171115新增
    private Integer payToObject;  //代客叫车支付对象【1:乘车人支付】

    //20171205新增
    private double prepaidFee; //预支付金额
    private int prepaidStatus; //预支付状态【0：初始化 1：待支付 2：支付中 3：已支付 4:支付异常 】

    //20180403 新增计费方式【针对福州微信一口价改实时方式 1:为实时，其他暂定】
    private int billingWay;

    //非数据库字段
    private Double decWaitPayFee;

    //非数据库字段
    private boolean passengerIsBindCredti = false;
    private boolean passengerBalanceIsEnough = false;
    private double passengerBalance;
    private double insPayFare;
    private int payTimeLimit;


    public Date getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassengeUuid() {
        return passengeUuid;
    }

    public void setPassengeUuid(String passengeUuid) {
        this.passengeUuid = passengeUuid;
    }

    public String getDriverUuid() {
        return driverUuid;
    }

    public void setDriverUuid(String driverUuid) {
        this.driverUuid = driverUuid;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getAcceptType() {
        return acceptType;
    }

    public void setAcceptType(int acceptType) {
        this.acceptType = acceptType;
    }

    public int getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(int orderSource) {
        this.orderSource = orderSource;
    }

    public String getActualPassengeName() {
        return actualPassengeName;
    }

    public void setActualPassengeName(String actualPassengeName) {
        this.actualPassengeName = actualPassengeName;
    }

    public String getActualPassengeMobile() {
        return actualPassengeMobile;
    }

    public void setActualPassengeMobile(String actualPassengeMobile) {
        this.actualPassengeMobile = actualPassengeMobile;
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public String getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(String serviceNames) {
        this.serviceNames = serviceNames;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getMainStatus() {
        return mainStatus;
    }

    public void setMainStatus(int mainStatus) {
        this.mainStatus = mainStatus;
    }

    public int getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(int subStatus) {
        this.subStatus = subStatus;
    }

    public double getPlanTrip() {
        return planTrip;
    }

    public void setPlanTrip(double planTrip) {
        this.planTrip = planTrip;
    }

    public double getActualTrip() {
        return actualTrip;
    }

    public void setActualTrip(double actualTrip) {
        this.actualTrip = actualTrip;
    }

    public double getPlanFare() {
        return planFare;
    }

    public void setPlanFare(double planFare) {
        this.planFare = planFare;
    }

    public double getPayFare() {
        return payFare;
    }

    public void setPayFare(double payFare) {
        this.payFare = payFare;
    }

    public Date getDeparTime() {
        return deparTime;
    }

    public void setDeparTime(Date deparTime) {
        this.deparTime = deparTime;
    }

    public int getDeparTimeDiff() {
        return deparTimeDiff;
    }

    public void setDeparTimeDiff(int deparTimeDiff) {
        this.deparTimeDiff = deparTimeDiff;
    }

    public double getOriginLng() {
        return originLng;
    }

    public void setOriginLng(double originLng) {
        this.originLng = originLng;
    }

    public double getOriginLat() {
        return originLat;
    }

    public void setOriginLat(double originLat) {
        this.originLat = originLat;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public double getDestLng() {
        return destLng;
    }

    public void setDestLng(double destLng) {
        this.destLng = destLng;
    }

    public double getDestLat() {
        return destLat;
    }

    public void setDestLat(double destLat) {
        this.destLat = destLat;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public int getIsBilled() {
        return isBilled;
    }

    public void setIsBilled(int isBilled) {
        this.isBilled = isBilled;
    }

    public String getTripPic() {
        return tripPic;
    }

    public void setTripPic(String tripPic) {
        this.tripPic = tripPic;
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

    public Date getServiceTimeStart() {
        return serviceTimeStart;
    }

    public void setServiceTimeStart(Date serviceTimeStart) {
        this.serviceTimeStart = serviceTimeStart;
    }

    public Date getServiceTimeEnd() {
        return serviceTimeEnd;
    }

    public void setServiceTimeEnd(Date serviceTimeEnd) {
        this.serviceTimeEnd = serviceTimeEnd;
    }

    public Date getDriverSetOutTime() {
        return driverSetOutTime;
    }

    public void setDriverSetOutTime(Date driverSetOutTime) {
        this.driverSetOutTime = driverSetOutTime;
    }

    public String getOriginDetailAddress() {
        return originDetailAddress;
    }

    public void setOriginDetailAddress(String originDetailAddress) {
        this.originDetailAddress = originDetailAddress;
    }

    public String getDestDetailAddress() {
        return destDetailAddress;
    }

    public void setDestDetailAddress(String destDetailAddress) {
        this.destDetailAddress = destDetailAddress;
    }

    public String getActualDriverUuid() {
        return actualDriverUuid;
    }

    public void setActualDriverUuid(String actualDriverUuid) {
        this.actualDriverUuid = actualDriverUuid;
    }

    public int getAllowAssignOtherDriver() {
        return allowAssignOtherDriver;
    }

    public void setAllowAssignOtherDriver(int allowAssignOtherDriver) {
        this.allowAssignOtherDriver = allowAssignOtherDriver;
    }

    public Date getDriverArriveTime() {
        return driverArriveTime;
    }

    public void setDriverArriveTime(Date driverArriveTime) {
        this.driverArriveTime = driverArriveTime;
    }

    public Double getNightSubsidyMileageStart() {
        return nightSubsidyMileageStart;
    }

    public void setNightSubsidyMileageStart(Double nightSubsidyMileageStart) {
        this.nightSubsidyMileageStart = nightSubsidyMileageStart;
    }

    public Double getNightSubsidyMileageEnd() {
        return nightSubsidyMileageEnd;
    }

    public void setNightSubsidyMileageEnd(Double nightSubsidyMileageEnd) {
        this.nightSubsidyMileageEnd = nightSubsidyMileageEnd;
    }

    public String getPlanTripPic() {
        return planTripPic;
    }

    public void setPlanTripPic(String planTripPic) {
        this.planTripPic = planTripPic;
    }

    public double getTripTotalFare() {
        return tripTotalFare;
    }

    public void setTripTotalFare(double tripTotalFare) {
        this.tripTotalFare = tripTotalFare;
    }

    public static int getOrderMainStatusDefault() {
        return ORDER_MAIN_STATUS_DEFAULT;
    }

    public Integer getBeyondWaitTime() {
        return beyondWaitTime;
    }

    public void setBeyondWaitTime(Integer beyondWaitTime) {
        this.beyondWaitTime = beyondWaitTime;
    }

    public Double getBeyondWaitFee() {
        return beyondWaitFee;
    }

    public void setBeyondWaitFee(Double beyondWaitFee) {
        this.beyondWaitFee = beyondWaitFee;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPrepayType() {
        return prepayType;
    }

    public void setPrepayType(int prepayType) {
        this.prepayType = prepayType;
    }

    public double getAssignDriverFare() {
        return assignDriverFare;
    }

    public void setAssignDriverFare(double assignDriverFare) {
        this.assignDriverFare = assignDriverFare;
    }

    public int getValetCall() {
        return valetCall;
    }

    public void setValetCall(int valetCall) {
        this.valetCall = valetCall;
    }

    public String getReassignmentRemark() {
        return reassignmentRemark;
    }

    public void setReassignmentRemark(String reassignmentRemark) {
        this.reassignmentRemark = reassignmentRemark;
    }

    public boolean isPassengerBalanceIsEnough() {
        return passengerBalanceIsEnough;
    }

    public void setPassengerBalanceIsEnough(boolean passengerBalanceIsEnough) {
        this.passengerBalanceIsEnough = passengerBalanceIsEnough;
    }

    public boolean isPassengerIsBindCredti() {
        return passengerIsBindCredti;
    }

    public void setPassengerIsBindCredti(boolean passengerIsBindCredti) {
        this.passengerIsBindCredti = passengerIsBindCredti;
    }

    public String getEntUuid() {
        return entUuid;
    }

    public void setEntUuid(String entUuid) {
        this.entUuid = entUuid;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    public Double getCancelFee() {
        return cancelFee;
    }

    public void setCancelFee(Double cancelFee) {
        this.cancelFee = cancelFee;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getPartnerOrderNumber() {
        return partnerOrderNumber;
    }

    public void setPartnerOrderNumber(String partnerOrderNumber) {
        this.partnerOrderNumber = partnerOrderNumber;
    }

    public String getSqDriverId() {
        return sqDriverId;
    }

    public void setSqDriverId(String sqDriverId) {
        this.sqDriverId = sqDriverId;
    }

    public String getSqDriverName() {
        return sqDriverName;
    }

    public void setSqDriverName(String sqDriverName) {
        this.sqDriverName = sqDriverName;
    }

    public String getSqDriverMobile() {
        return sqDriverMobile;
    }

    public void setSqDriverMobile(String sqDriverMobile) {
        this.sqDriverMobile = sqDriverMobile;
    }

    public Integer getIsCarLevelUp() {
        return isCarLevelUp;
    }

    public void setIsCarLevelUp(Integer isCarLevelUp) {
        this.isCarLevelUp = isCarLevelUp;
    }

    public double getPassengerBalance() {
        return passengerBalance;
    }

    public void setPassengerBalance(double passengerBalance) {
        this.passengerBalance = passengerBalance;
    }

    public String getPreChoiceUserCouponUuid() {
        return preChoiceUserCouponUuid;
    }

    public void setPreChoiceUserCouponUuid(String preChoiceUserCouponUuid) {
        this.preChoiceUserCouponUuid = preChoiceUserCouponUuid;
    }

    public Double getWaitPayFee() {
        return waitPayFee;
    }

    public void setWaitPayFee(Double waitPayFee) {
        this.waitPayFee = waitPayFee;
    }

    public Double getDecWaitPayFee() {
        return decWaitPayFee;
    }

    public void setDecWaitPayFee(Double decWaitPayFee) {
        this.decWaitPayFee = decWaitPayFee;
    }

    public double getInsPayFare() {
        return insPayFare;
    }

    public void setInsPayFare(double insPayFare) {
        this.insPayFare = insPayFare;
    }

    public Integer getVipCounts() {
        return vipCounts;
    }

    public void setVipCounts(Integer vipCounts) {
        this.vipCounts = vipCounts;
    }

    public Integer getVipFlag() {
        return vipFlag;
    }

    public void setVipFlag(Integer vipFlag) {
        this.vipFlag = vipFlag;
    }

    public Date getVipEffTime() {
        return vipEffTime;
    }

    public void setVipEffTime(Date vipEffTime) {
        this.vipEffTime = vipEffTime;
    }

    public String getAreaUuid() {
        return areaUuid;
    }

    public void setAreaUuid(String areaUuid) {
        this.areaUuid = areaUuid;
    }

    public int getPrepaidStatus() {
        return prepaidStatus;
    }

    public void setPrepaidStatus(int prepaidStatus) {
        this.prepaidStatus = prepaidStatus;
    }

    public double getPrepaidFee() {
        return prepaidFee;
    }

    public void setPrepaidFee(double prepaidFee) {
        this.prepaidFee = prepaidFee;
    }

    public int getPayTimeLimit() {
        return payTimeLimit;
    }

    public void setPayTimeLimit(int payTimeLimit) {
        this.payTimeLimit = payTimeLimit;
    }

    public Integer getPayToObject() {
        return payToObject;
    }

    public void setPayToObject(Integer payToObject) {
        this.payToObject = payToObject;
    }

    public int getBillingWay() {
        return billingWay;
    }

    public void setBillingWay(int billingWay) {
        this.billingWay = billingWay;
    }

    public Map<String, Object> toMap4WebSocket() {

        Map<String, Object> map = MapUtil.buildMap();
        map.put("uuid", uuid);
        //map.put("passengeUuid",passengeUuid);
        //map.put("driverUuid",driverUuid);
        //map.put("actualDriverUuid",actualDriverUuid);
        //map.put("allowAssignOtherDriver",allowAssignOtherDriver);
        map.put("orderType", orderType);
        map.put("acceptType", acceptType);
        map.put("orderSource",orderSource);
        map.put("actualPassengeName", actualPassengeName);
        map.put("actualPassengeMobile", actualPassengeMobile);
        map.put("levelType", levelType);
        map.put("serviceNames", serviceNames);
        //map.put("servicePrice",servicePrice);
        map.put("mainStatus", mainStatus);
        map.put("subStatus", subStatus);
        map.put("planTrip", NumberFormatUtil.roundAndFormat2TwoDecimal(planTrip));//预估里程
        map.put("actualTrip", NumberFormatUtil.roundAndFormat2TwoDecimal(actualTrip));//实际里程
        map.put("planFare", NumberFormatUtil.roundAndFormat2TwoDecimal(planFare));//预估金额
        map.put("tripTotalFare", NumberFormatUtil.roundAndFormat2TwoDecimal(tripTotalFare));//行程费用
        map.put("payFare", NumberFormatUtil.roundAndFormat2TwoDecimal(payFare));//支付的费用
        map.put("tip", NumberFormatUtil.roundAndFormat2TwoDecimal(tip == null ? 0 : tip));//小费
        map.put("cancelFee", NumberFormatUtil.roundAndFormat2TwoDecimal(cancelFee == null ? 0 : cancelFee));//取消费
        map.put("totalFee", NumberFormatUtil.roundAndFormat2TwoDecimal(totalFee == null ? 0 : totalFee));//总金额（优惠券抵扣前的金额）
        map.put("waitPayFee", NumberFormatUtil.roundAndFormat2TwoDecimal(waitPayFee == null ? 0 : waitPayFee));//总金额（优惠券抵扣前的金额）
        map.put("deparTime", deparTime);
        //map.put("originLng",originLng);
        //map.put("originLat",originLat);
        //map.put("originCity",originCity);
        map.put("originAddress", originAddress);
        map.put("originDetailAddress", originDetailAddress);
        //map.put("destLng",destLng);
        //map.put("destLat",destLat);
        //map.put("destCity",destCity);
        map.put("destAddress", destAddress);
        map.put("destDetailAddress", destDetailAddress);
        map.put("flightNum", flightNum);
        map.put("remark", remark);
        map.put("cancelReason", cancelReason);
        //map.put("isBilled",isBilled);
        //map.put("createTime",createTime);
        //map.put("creator",creator);
        //map.put("updateTime",updateTime);
        //map.put("driverSetOutTime",driverSetOutTime);//出发接乘客时间
        //map.put("driverArriveTime",driverArriveTime);//到达指定目的地时间
        //map.put("serviceTimeStart",serviceTimeStart);//服务开始时间
        //map.put("serviceTimeEnd",serviceTimeEnd);//服务结束时间
        //map.put("nightSubsidyMileageStart",nightSubsidyMileageStart);//夜间补贴开始公里数
        //map.put("nightSubsidyMileageEnd",nightSubsidyMileageEnd);//夜间补贴结束公里数
        //map.put("planTripPic",planTripPic);
        //map.put("tripPic",tripPic);
        //map.put("beyondWaitTime",beyondWaitTime);
        //map.put("beyondWaitFee",beyondWaitFee);
        //map.put("payType",payType);
        //map.put("prepayType",prepayType);
        //map.put("assignDriverFare",assignDriverFare);
        //map.put("valetCall",valetCall);
        //map.put("reassignmentRemark",reassignmentRemark);
        //map.put("entUuid",entUuid);
        //map.put("passengerIsBindCredti",passengerIsBindCredti);
        //map.put("entUuid",entUuid);
        //map.put("passengerBalanceIsEnough",passengerBalanceIsEnough);
        map.put("orderNumber", orderNumber);
        map.put("isCarLevelUp", isCarLevelUp);
        map.put("areaUuid", areaUuid);
        return map;
    }


    public Map<String, Object> toMap4WebSocket(int totalNum, int curNum) {
        Map<String, Object> map = toMap4WebSocket();
        map.put("totalNum", totalNum);
        map.put("curNum", curNum);
        return map;

    }

    public Map<String, Object> toMap4WebSocketWithPayFee(double payFeeThisTime) {
        Map<String, Object> map = toMap4WebSocket();
        map.put("payFeeThisTime", NumberFormatUtil.roundAndFormat2TwoDecimal(payFeeThisTime));//总金额（优惠券抵扣前的金额）
        return map;

    }

}
