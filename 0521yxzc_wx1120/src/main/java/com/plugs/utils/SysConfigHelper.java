package com.plugs.utils;

import com.plugs.module_system.dtos.SysConfigDto;
import com.plugs.module_system.mappers.SysConfigMapper;
import com.util.MapUtil;
import com.util.SpringContextUtil;

import java.util.Map;

/**
 * 系统配置读取工具
 *
 * @author Zoro
 * @since 2016/10/20
 */
public class SysConfigHelper {

    //存储在数据库中的KEY
    private static final String PREPAID_ORDER_PAY_TIME_LIMIT = "prepaidOrderPayTimeLimit";
    private static final String TIME_DIFFERENCE_LIMIT = "timeDifferenceLimit";
    private static final String CYCLE_ORDER_PUSH_DISTANCE = "cycleOrderPushDistance";
    private static final String ASSIGN_DRIVER_PUSH_TIME_KEY = "assignDriverPushTime";
    private static final String SUITE_DRIVER_PUSH_TIME_KEY = "suiteDriverPushTime";
    private static final String ORDER_START_TIME_FOR_NEARBY_DRIVER_KEY = "orderStartTimeForNearbyDriver";
    private static final String ORDER_RUSH_LIVE_TIME_KEY = "orderRushLiveTime";
    private static final String ORDER_CONFLICT_TIME_KEY = "orderConflictTime";
    private static final String REG_USER_BALANCE_KEY = "regUserBalance";
    private static final String APNS_PRODUCTION_KEY = "apnsProduction";
    private static final String APP_TEST_ACCOUNT_KEY = "appTestAccount";
    private static final String SECOND_PUSH_TIME_KEY = "secondPushTime";
    private static final String SECOND_PUSH_INTERVAL_KEY = "secondPushInterval";
    private static final String NO_RUSH_HOUR_DISTANCE_KEY = "noRushHourDistance";
    private static final String RUSH_HOUR_DISTANCE_KEY = "rushHourDistance";
    private static final String ASSIGN_DRIVER_FEE_KEY = "assignDriverFee";
    private static final String OFF_LINE_LIMIT_TIME_KEY = "offLineLimitTime";
    private static final String RECHARGE_ENABLE_KEY = "rechargeEnable";
    private static final String IMME_CAR_LEVELUP_KEY = "immeCarLevelUp";
    private static final String APPOINT_CAR_LEVELUP_KEY = "appointCarLevelUp";
    private static final String AIR_PLANE_CAR_LEVELUP_KEY = "airPlaneCarLevelUp";
    private static final String RENT_CAR_LEVELUP_KEY = "rentCarLevelUp";
    private static final String FORWARD_TO_SQYC_KEY = "forwardToSq";
    private static final String IMME_ORDER_MATCH_TIMES_KEY = "immeOrderMatchTimes";
    private static final String IMME_ORDER_MATCH_INTERVAL_KEY = "immeOrderMatchInterval";
    private static final String IMME_ORDER_CAR_LEVELUP_TIME_KEY = "immeOrderCarLevelUpTime";
    private static final String IMME_ORDER_FORWARD_TO_SQ_TIME_KEY = "immeOrderForwardToSqTime";
    private static final String CALLED_CAR_BALANCE_LIMIT = "calledCarBalanceLimit";




    //具体值
    private static Integer prepaidOrderPayTimeLimit; //预支付限制时间[分钟]
    private static Integer timeDifferenceLimit;//订单预约时间与当前时间差【分钟】
    private static Integer cycleOrderPushDistance; //预约用车 下单距离范围km
    private static Integer assignDriverPushTime;//后台指定司机--推单时间(s)
    private static Integer suiteDriverPushTime;//符合条件的司机--推单时间(s)
    private static Integer orderStartTimeForNearbyDriver;//订单预约时间距离现在多久--需要推送给最近的司机(S)
    private static Integer orderRushLiveTime;//订单存活时间(s)
    private static Integer orderConflictTime;//订单冲突时间(s)
    private static Integer secondPushTime;//第二阶段推单时间(s)
    private static Integer secondPushInterval;//第二阶段推单时间(s)
    private static Double regUserBalance;
    private static Boolean apnsProduction;//是否生产环境，false代表开发环境
    private static String appTestAccount;//苹果测试账号
    private static Integer noRushHourDistance;//非高峰时段立即用车推单距离
    private static Integer rushHourDistance;//高峰时段立即用车推单距离
    private static Double assignDriverFee;//指定司机费用
    private static Integer offLineLimitTime;//判断司机离线阈值（s）
    private static Boolean rechargeEnable;//充值开关
    private static Boolean immeCarLevelUp;//立即用车车型升级开关
    private static Boolean appointCarLevelUp;//预约用车车型升级开关
    private static Boolean airPlaneCarLevelUp;//接送机车型升级开关
    private static Boolean rentCarLevelUp;//日租半日租车型升级开关
    private static Boolean forwardToSq;//是否转发给首汽的开关
    private static Integer immeOrderMatchTimes;//即时单匹配次数
    private static Integer immeOrderMatchInterval;//即时单匹配间隔(S)
    private static Integer immeOrderCarLevelUpTime;//即时单第几轮进行车型升级
    private static Integer immeOrderForwardToSqTime;//即时单第几轮转发给首汽

    private static Integer immeOrderFirstIntervalTime;//即时单首轮休眠时间[5秒为宜]
    private static Float immeOrderFirstPushDistance;//即时单首轮推送距离[500米为宜]
    private static Integer immeOrderSecondIntervalTime;//即时单第二轮休眠时间[10秒为宜]
    private static Float immeOrderSecondPushDistance;//即时单第二轮推送距离[1000米为宜]
    private static Integer immeOrderThreeIntervalTime;//即时单第三轮休眠时间[10秒为宜]
    private static Float immeOrderThreePushDistance;//即时单第三轮推送距离[2000米为宜]
    private static Integer immeOrderOtherIntervalTime;//即时单第四轮休眠时间[10秒为宜]
    private static Float immeOrderOtherPushDistance;//即时单第四轮推送距离[3000米为宜]

    private static Integer calledCarBalanceLimit;//乘客代客叫车余额限制

    private static final String IMME_ORDER_FIRST_INTERVAL_TIME = "immeOrderFirstIntervalTime";
    private static final String IMME_ORDER_FIRST_PUSH_DISTANCE = "immeOrderFirstPushDistance";
    private static final String IMME_ORDER_SECOND_INTERVAL_TIME = "immeOrderSecondIntervalTime";
    private static final String IMME_ORDER_SECOND_PUSH_DISTANCE = "immeOrderSecondPushDistance";
    private static final String IMME_ORDER_THREE_INTERVAL_TIME = "immeOrderThreeIntervalTime";
    private static final String IMME_ORDER_THREE_PUSH_DISTANCE = "immeOrderThreePushDistance";
    private static final String IMME_ORDER_OTHER_INTERVAL_TIME = "immeOrderOtherIntervalTime";
    private static final String IMME_ORDER_OTHER_PUSH_DISTANCE = "immeOrderOtherPushDistance";


    /**
     * 代客叫车余额限制
     */
    public static int getCalledCarBalanceLimit() {
        if (calledCarBalanceLimit != null) {
            return calledCarBalanceLimit;
        }
        if ((calledCarBalanceLimit = getIntValueByMapper(CALLED_CAR_BALANCE_LIMIT)) != -1) {
            return calledCarBalanceLimit;
        }
        return 200;
    }

    /**
     * 预支付限制时间[分钟]
     *
     * @return 预支付限制时间
     */
    public static int getPrepaidOrderPayTimeLimit() {
        if (prepaidOrderPayTimeLimit != null) {
            return prepaidOrderPayTimeLimit;
        }
        if ((prepaidOrderPayTimeLimit = getIntValueByMapper(PREPAID_ORDER_PAY_TIME_LIMIT)) != -1) {
            return prepaidOrderPayTimeLimit;
        }
        return 5;
    }

    /**
     * 订单预约时间与当前时间差【分钟】
     */
    public static int getTimeDifferenceLimit() {
        if (timeDifferenceLimit != null) {
            return timeDifferenceLimit;
        }
        if ((timeDifferenceLimit = getIntValueByMapper(TIME_DIFFERENCE_LIMIT)) != -1) {
            return timeDifferenceLimit;
        }
        return 15;
    }

    /**
     * 立即用车 剩余其他轮睡眠时间
     * @return 剩余其他轮睡眠时间
     */
    public static int getImmeOrderOtherIntervalTime() {
        if (immeOrderOtherIntervalTime != null) {
            return immeOrderOtherIntervalTime;
        }
        if ((immeOrderOtherIntervalTime = getIntValueByMapper(IMME_ORDER_OTHER_INTERVAL_TIME)) != -1) {
            return immeOrderOtherIntervalTime;
        }
        return 10;
    }

    /**
     * 立即用车 剩余其他轮推送距离
     * @return 剩余其他轮推送距离
     */
    public static float getImmeOrderOtherPushDistance() {
        if (immeOrderOtherPushDistance != null) {
            return immeOrderOtherPushDistance;
        }
        if ((immeOrderOtherPushDistance = getFloatValueByMapper(IMME_ORDER_OTHER_PUSH_DISTANCE)) != -1) {
            return immeOrderOtherPushDistance;
        }
        return 3000f;
    }

    /**
     * 立即用车 第三轮睡眠时间
     * @return 第三轮睡眠时间
     */
    public static int getImmeOrderThreeIntervalTime() {
        if (immeOrderThreeIntervalTime != null) {
            return immeOrderThreeIntervalTime;
        }
        if ((immeOrderThreeIntervalTime = getIntValueByMapper(IMME_ORDER_THREE_INTERVAL_TIME)) != -1) {
            return immeOrderThreeIntervalTime;
        }
        return 10;
    }

    /**
     * 立即用车 第三轮推送距离
     * @return 第三轮推送距离
     */
    public static float getImmeOrderThreePushDistance() {
        if (immeOrderThreePushDistance != null) {
            return immeOrderThreePushDistance;
        }
        if ((immeOrderThreePushDistance = getFloatValueByMapper(IMME_ORDER_THREE_PUSH_DISTANCE)) != -1) {
            return immeOrderThreePushDistance;
        }
        return 2000f;
    }

    /**
     * 立即用车 次轮睡眠时间
     * @return 次轮睡眠时间
     */
    public static int getImmeOrderSecondIntervalTime() {
        if (immeOrderSecondIntervalTime != null) {
            return immeOrderSecondIntervalTime;
        }
        if ((immeOrderSecondIntervalTime = getIntValueByMapper(IMME_ORDER_SECOND_INTERVAL_TIME)) != -1) {
            return immeOrderSecondIntervalTime;
        }
        return 10;
    }

    /**
     * 立即用车 次轮推送距离
     * @return 次轮推送距离
     */
    public static float getImmeOrderSecondPushDistance() {
        if (immeOrderSecondPushDistance != null) {
            return immeOrderSecondPushDistance;
        }
        if ((immeOrderSecondPushDistance = getFloatValueByMapper(IMME_ORDER_SECOND_PUSH_DISTANCE)) != -1) {
            return immeOrderSecondPushDistance;
        }
        return 1000f;
    }

    /**
     * 立即用车 首轮睡眠时间
     * @return 首轮睡眠时间
     */
    public static int getImmeOrderFirstIntervalTime() {
        if (immeOrderFirstIntervalTime != null) {
            return immeOrderFirstIntervalTime;
        }
        if ((immeOrderFirstIntervalTime = getIntValueByMapper(IMME_ORDER_FIRST_INTERVAL_TIME)) != -1) {
            return immeOrderFirstIntervalTime;
        }
        return 5;
    }

    /**
     * 立即用车 首轮推送距离
     * @return 首轮推送距离
     */
    public static float getImmeOrderFirstPushDistance() {
        if (immeOrderFirstPushDistance != null) {
            return immeOrderFirstPushDistance;
        }
        if ((immeOrderFirstPushDistance = getFloatValueByMapper(IMME_ORDER_FIRST_PUSH_DISTANCE)) != -1) {
            return immeOrderFirstPushDistance;
        }
        return 500f;
    }



    /**
     * 预约用车 下单距离范围km
     * @return
     */
    public static int getCycleOrderPushDistance() {
        if (cycleOrderPushDistance != null) {
            return cycleOrderPushDistance;
        }
        if ((cycleOrderPushDistance = getIntValueByMapper(CYCLE_ORDER_PUSH_DISTANCE)) != -1) {
            return cycleOrderPushDistance;
        }
        return 150;
    }

    /**
     * 指定司机推送时间
     *
     * @return
     */
    public static int getAssignDriverPushTime() {
        if (assignDriverPushTime != null) {
            return assignDriverPushTime;
        }
        if ((assignDriverPushTime = getIntValueByMapper(ASSIGN_DRIVER_PUSH_TIME_KEY)) != -1) {
            return assignDriverPushTime;
        }
        return 30;
    }

    /**
     * 符合条件的司机推送时间
     *
     * @return
     */
    public static int getSuiteDriverPushTime() {
        if (suiteDriverPushTime != null) {
            return suiteDriverPushTime;
        }
        if ((suiteDriverPushTime = getIntValueByMapper(SUITE_DRIVER_PUSH_TIME_KEY)) != -1) {
            return suiteDriverPushTime;
        }
        return 180;
    }

    /**
     * 距离订单开始时间多长需要推送给最近的司机
     *
     * @return
     */
    public static int getOrderStartTimeForNearbyDriver() {
        if (orderStartTimeForNearbyDriver != null) {
            return orderStartTimeForNearbyDriver;
        }
        if ((orderStartTimeForNearbyDriver = getIntValueByMapper(ORDER_START_TIME_FOR_NEARBY_DRIVER_KEY)) != -1) {
            return orderStartTimeForNearbyDriver;
        }
        return 2400;
    }

    /**
     * 抢单存活时间
     *
     * @return
     */
    public static int getOrderRushLiveTime() {
        if (orderRushLiveTime != null) {
            return orderRushLiveTime;
        }
        if ((orderRushLiveTime = getIntValueByMapper(ORDER_RUSH_LIVE_TIME_KEY)) != -1) {
            return orderRushLiveTime;
        }
        return 3;
    }

    /**
     * 订单冲突时间
     *
     * @return
     */
    public static int getOrderConflictTime() {
        if (orderConflictTime != null) {
            return orderConflictTime;
        }
        if ((orderConflictTime = getIntValueByMapper(ORDER_CONFLICT_TIME_KEY)) != -1) {
            return orderConflictTime;
        }
        return 3600;
    }

    /**
     * 第二阶段推单时间
     *
     * @return
     */
    public static int getSecondPushTime() {
        if (secondPushTime != null) {
            return secondPushTime;
        }
        if ((secondPushTime = getIntValueByMapper(SECOND_PUSH_TIME_KEY)) != -1) {
            return secondPushTime;
        }
        return 1800;
    }

    /**
     * 第二阶段推单间隔
     *
     * @return
     */
    public static int getSecondPushSecondPushInterval() {
        if (secondPushInterval != null) {
            return secondPushInterval;
        }
        if ((secondPushInterval = getIntValueByMapper(SECOND_PUSH_INTERVAL_KEY)) != -1) {
            return secondPushInterval;
        }
        return 30;
    }

    /**
     * 非高峰时段，立即用车派送距离
     *
     * @return
     */
    public static int getNoRushHourDistance() {
        if (noRushHourDistance != null) {
            return noRushHourDistance;
        }
        if ((noRushHourDistance = getIntValueByMapper(NO_RUSH_HOUR_DISTANCE_KEY)) != -1) {
            return noRushHourDistance;
        }
        return 1000;
    }

    /**
     * 高峰时段，立即用车派送距离
     *
     * @return
     */
    public static int getRushHourDistance() {
        if (rushHourDistance != null) {
            return rushHourDistance;
        }
        if ((rushHourDistance = getIntValueByMapper(RUSH_HOUR_DISTANCE_KEY)) != -1) {
            return rushHourDistance;
        }
        return 1000;
    }

    /**
     * 司机离线判断阈值
     *
     * @return
     */
    public static int getOffLineLimitTime() {
        if (offLineLimitTime != null) {
            return offLineLimitTime;
        }
        if ((offLineLimitTime = getIntValueByMapper(OFF_LINE_LIMIT_TIME_KEY)) != -1) {
            return offLineLimitTime;
        }
        return 60;
    }

    /**
     * 获取即时订单匹配次数
     *
     * @return
     */
    public static int getImmeOrderMatchTimes() {
        if (immeOrderMatchTimes != null) {
            return immeOrderMatchTimes;
        }
        if ((immeOrderMatchTimes = getIntValueByMapper(IMME_ORDER_MATCH_TIMES_KEY)) != -1) {
            return immeOrderMatchTimes;
        }
        return 3;
    }

    /**
     * 即时单匹配间隔
     *
     * @return
     */
    public static int getImmeOrderMatchInterval() {
        if (immeOrderMatchInterval != null) {
            return immeOrderMatchInterval;
        }
        if ((immeOrderMatchInterval = getIntValueByMapper(IMME_ORDER_MATCH_INTERVAL_KEY)) != -1) {
            return immeOrderMatchInterval;
        }
        return 10;
    }

    /**
     * 即时单第几轮进行车型升级
     *
     * @return
     */
    public static int getImmeOrderCarLevelUpTime() {
        if (immeOrderCarLevelUpTime != null) {
            return immeOrderCarLevelUpTime;
        }
        if ((immeOrderCarLevelUpTime = getIntValueByMapper(IMME_ORDER_CAR_LEVELUP_TIME_KEY)) != -1) {
            return immeOrderCarLevelUpTime;
        }
        return 2;
    }

    /**
     * 即时单第几轮转发给首汽
     *
     * @return
     */
    public static int getImmeOrderForwardToSqTime() {
        if (immeOrderForwardToSqTime != null) {
            return immeOrderForwardToSqTime;
        }
        if ((immeOrderForwardToSqTime = getIntValueByMapper(IMME_ORDER_FORWARD_TO_SQ_TIME_KEY)) != -1) {
            return immeOrderForwardToSqTime;
        }
        return 2;
    }

    /**
     * 注册用户余额
     *
     * @return
     */
    public static Double getRegUserBalance() {
        if (regUserBalance != null) {
            return regUserBalance;
        }
        if (!(regUserBalance = getDoubleValueByMapper(REG_USER_BALANCE_KEY)).equals(-1d)) {
            return regUserBalance;
        }
        return 0d;
    }

    /**
     * 指定司机费用
     *
     * @return
     */
    public static Double getAssignDriverFee() {
        if (assignDriverFee != null) {
            return assignDriverFee;
        }
        if (!(assignDriverFee = getDoubleValueByMapper(ASSIGN_DRIVER_FEE_KEY)).equals(-1d)) {
            return assignDriverFee;
        }
        return 0d;
    }

    /**
     * 获取极光推送ios是否生产环境
     *
     * @return
     */
    public static Boolean getApnsProduction() {
        if (apnsProduction != null) {
            return apnsProduction;
        }
        if (!(apnsProduction = getBooleanValueByMapper(APNS_PRODUCTION_KEY))) {
            return apnsProduction;
        }
        return true;
    }

    /**
     * 获取充值开关
     *
     * @return
     */
    public static Boolean getRechargeEnable() {
        if (rechargeEnable != null) {
            return rechargeEnable;
        }
        if (!(rechargeEnable = getBooleanValueByMapper(RECHARGE_ENABLE_KEY))) {
            return rechargeEnable;
        }
        return true;
    }

    /**
     * 立即用车是否车型升级
     *
     * @return
     */
    public static Boolean getImmeCarLevelUp() {
        if (immeCarLevelUp != null) {
            return immeCarLevelUp;
        }
        if (!(immeCarLevelUp = getBooleanValueByMapper(IMME_CAR_LEVELUP_KEY))) {
            return immeCarLevelUp;
        }
        return false;
    }

    /**
     * 预约用车是否车型升级
     *
     * @return
     */
    public static Boolean getAppointCarLevelUp() {
        if (appointCarLevelUp != null) {
            return appointCarLevelUp;
        }
        if (!(appointCarLevelUp = getBooleanValueByMapper(APPOINT_CAR_LEVELUP_KEY))) {
            return appointCarLevelUp;
        }
        return false;
    }

    /**
     * 接送机是否车型升级
     *
     * @return
     */
    public static Boolean getAirPlaneCarLevelUp() {
        if (airPlaneCarLevelUp != null) {
            return airPlaneCarLevelUp;
        }
        if (!(airPlaneCarLevelUp = getBooleanValueByMapper(AIR_PLANE_CAR_LEVELUP_KEY))) {
            return airPlaneCarLevelUp;
        }
        return false;
    }


    /**
     * 日租半日租车型升级
     *
     * @return
     */
    public static Boolean getRentCarLevelUp() {
        if (rentCarLevelUp != null) {
            return rentCarLevelUp;
        }
        if (!(rentCarLevelUp = getBooleanValueByMapper(RENT_CAR_LEVELUP_KEY))) {
            return rentCarLevelUp;
        }
        return false;
    }

    /**
     * 是否转发订单给首汽
     *
     * @return
     */
    public static Boolean getForwardToSq() {
        if (forwardToSq != null) {
            return forwardToSq;
        }
        if (!(forwardToSq = getBooleanValueByMapper(FORWARD_TO_SQYC_KEY))) {
            return forwardToSq;
        }
        return false;
    }


    /**
     * 获取苹果测试账号
     *
     * @return
     */
    public static String getAppTestAccount() {
        if (appTestAccount != null) {
            return appTestAccount;
        }
        if ((appTestAccount = getStringValueByMapper(APP_TEST_ACCOUNT_KEY)) != null) {
            return appTestAccount;
        }
        return "14266668888";
    }


    /**
     * 根据mapper获取float值
     *
     * @param key
     * @return
     */
    private static float getFloatValueByMapper(String key) {
        SysConfigMapper<SysConfigDto> mapper = (SysConfigMapper<SysConfigDto>) SpringContextUtil.getBean("sysConfigMapper");
        if (null != mapper) {
            try {
                Map<String, Object> params = MapUtil.buildMap();
                params.put("key", key);
                return Float.parseFloat(mapper.selInfo(params).getValue());
            } catch (Exception e) {
                return -1f;
            }
        }
        return -1f;
    }

    /**
     * 根据mapper获取int值
     *
     * @param key
     * @return
     */
    private static int getIntValueByMapper(String key) {
        SysConfigMapper<SysConfigDto> mapper = (SysConfigMapper<SysConfigDto>) SpringContextUtil.getBean("sysConfigMapper");
        if (null != mapper) {
            try {
                Map<String, Object> params = MapUtil.buildMap();
                params.put("key", key);
                return Integer.parseInt(mapper.selInfo(params).getValue());
            } catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }

    /**
     * 根据mapper获取double值
     *
     * @param key
     * @return
     */
    private static double getDoubleValueByMapper(String key) {
        SysConfigMapper<SysConfigDto> mapper = (SysConfigMapper<SysConfigDto>) SpringContextUtil.getBean("sysConfigMapper");
        if (null != mapper) {
            try {
                Map<String, Object> params = MapUtil.buildMap();
                params.put("key", key);
                return Double.parseDouble(mapper.selInfo(params).getValue());
            } catch (Exception e) {
                return -1d;
            }
        }
        return -1d;
    }

    /**
     * 根据mapper获取boolean
     *
     * @param key
     * @return
     */
    private static boolean getBooleanValueByMapper(String key) {
        SysConfigMapper<SysConfigDto> mapper = (SysConfigMapper<SysConfigDto>) SpringContextUtil.getBean("sysConfigMapper");
        if (null != mapper) {
            try {
                Map<String, Object> params = MapUtil.buildMap();
                params.put("key", key);
                return Boolean.parseBoolean(mapper.selInfo(params).getValue());
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 根据mapper获取String
     *
     * @param key
     * @return
     */
    private static String getStringValueByMapper(String key) {
        SysConfigMapper<SysConfigDto> mapper = (SysConfigMapper<SysConfigDto>) SpringContextUtil.getBean("sysConfigMapper");
        if (null != mapper) {
            try {
                Map<String, Object> params = MapUtil.buildMap();
                params.put("key", key);
                return mapper.selInfo(params).getValue();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }



    /**
     * 清空
     */
    public static void clear() {
        prepaidOrderPayTimeLimit = null; //预支付限制时间[分钟]
        timeDifferenceLimit = null; //订单预约时间与当前时间差【分钟】
        assignDriverPushTime = null;//指定司机推送时间
        suiteDriverPushTime = null;//合适的司机推送时间
        orderStartTimeForNearbyDriver = null;//订单开始多久给最近司机
        orderRushLiveTime = null;//订单存活时间
        orderConflictTime = null;//订单冲突时间
        regUserBalance = null;//注册初始化余额
        apnsProduction = null;//ios极光推送是否生产环境
        secondPushTime = null;//第二阶段推送时间
        secondPushInterval = null;//第二阶段推送间隔
        appTestAccount = null;//苹果测试账号
        noRushHourDistance = null;//非高峰时间派单距离
        rushHourDistance = null;//高峰时段派单距离
        assignDriverFee = null;//指定司机费用
        offLineLimitTime = null;//司机离线判断时间
        rechargeEnable = null;//充值开关
        immeCarLevelUp = null;//立即用车车型升级开关
        appointCarLevelUp = null;//预约用车车型升级开关
        rentCarLevelUp = null;//租车类型车型升级开关
        forwardToSq = null;//是否转发给首汽
        immeOrderMatchTimes = null;//立即用车订单匹配次数
        immeOrderMatchInterval = null;//立即用车订单匹配间隔
        immeOrderCarLevelUpTime = null;//立即用车在第几轮进行车型升级

        immeOrderFirstIntervalTime = null;//即时单首轮休眠时间
        immeOrderFirstPushDistance = null;//即时单首轮推送距离
        immeOrderSecondIntervalTime = null;//即时单第二轮休眠时间
        immeOrderSecondPushDistance = null;//即时单第二轮推送距
        immeOrderThreeIntervalTime = null;//即时单第三轮休眠时间
        immeOrderThreePushDistance = null;//即时单第三轮推送距离
        immeOrderOtherIntervalTime = null;//即时单第四轮休眠时间
        immeOrderOtherPushDistance = null;//即时单第四轮推送距离

        calledCarBalanceLimit = null;//乘客代客叫车余额限制
    }

}
