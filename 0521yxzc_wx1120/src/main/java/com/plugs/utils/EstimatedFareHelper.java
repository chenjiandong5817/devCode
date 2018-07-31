package com.plugs.utils;

import com.plugs.module_order.dtos.OrderCostDetailDto;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_system.dtos.SysCarBillingWayDto;
import com.util.StringUtil;

import java.util.Date;

/**
 * 预估费用计算类
 *
 * @author Zoro
 * @since 2016/10/15
 */
public class EstimatedFareHelper {

    private SysCarBillingWayDto billingWay;//计费方式

    private Double serviceFare;//附加服务费用
    private Double planMileage;//预估里程
    private Integer planDuration;//预估时长
    private Date deparTime;

    private double totalFee = 0;//总费用

    private OrderCostDetailDto costDetail = new OrderCostDetailDto();//费用明细

    private double planDurationParam = 1d;//时间按规划的1倍
    private double planMileageParam = 1d;//里程按规划的1倍


    public EstimatedFareHelper(SysCarBillingWayDto billingWay, Double serviceFare, Double planMileage, Integer planDuration, Date deparTime) {
        this.billingWay = billingWay;
        this.serviceFare = serviceFare;
        this.planMileage = planMileage * planMileageParam;
        this.planDuration = (int) Math.round(planDuration * planDurationParam);
        this.deparTime = deparTime;
        //关于起步参数
        costDetail.setStartMileage(billingWay.getStartTrip());
        costDetail.setStartTimeLength((int) billingWay.getStartDuration());
    }


    /**
     * 获取明细
     *
     * @return
     */
    public OrderCostDetailDto getCostDetail() {
        return costDetail;
    }

    /**
     * 获取总费用
     *
     * @return
     */
    public double getTotalFee() {
        if (totalFee != 0d)
            return MathUtils.round(totalFee, 2);
        return totalFee;
    }


    /**
     * 添加起步费
     *
     * @return
     */
    public EstimatedFareHelper addStartFee() {
        isInit();
        totalFee += billingWay.getStartFee();//起步费

        //添加明细
        costDetail.setStartFee(billingWay.getStartFee());
        costDetail.setStartMileage(billingWay.getStartTrip());
        costDetail.setStartTimeLength((int) billingWay.getStartDuration());
        return this;
    }

    /**
     * 添加时长费
     *
     * @return
     * @throws IllegalStateException
     */
    public EstimatedFareHelper addTimeLengthFee() throws IllegalStateException {
        //参数异常
        isInit();
        int beyondTime = planDuration - (int) billingWay.getStartDuration();//减去免费时长/分钟
        //没超过不处理
        if (beyondTime <= 0) {
            return this;
        }
        double timeFee = beyondTime * billingWay.getBeyondTimeFee();//时长费用
        totalFee += timeFee;//时长费

        //添加明细
        costDetail.setBeyondTimeLength((int) beyondTime);
        costDetail.setTimeFee(timeFee);
        return this;
    }


    /**
     * 添加里程费
     *
     * @return
     */
    public EstimatedFareHelper addMileageFee() {
        isInit();
        double mileages = planMileage;//里程
        double beyondMileages = mileages - billingWay.getStartTrip();//减去起步里程
        if (beyondMileages <= 0d)
            return this;
        double beyondMileageFee = beyondMileages * billingWay.getBeyondTripFee();
        totalFee += beyondMileageFee;

        costDetail.setBeyondMileage(beyondMileages);
        costDetail.setMileageFee(beyondMileageFee);
        return this;
    }

    /**
     * 添加夜间补贴
     *
     * @return
     */
    public EstimatedFareHelper addNightSubsidyFee() {
        isInit();
        String nightTimeStr = billingWay.getNightTimeStr();
        //在夜间时段
        if (DateUtils.isInNightTime(nightTimeStr, deparTime)) {

            double nightMileages = planMileage;//全部里程都算
            double nightFee = nightMileages * billingWay.getNightTripFee();
            totalFee += nightFee;

            costDetail.setNightMileage(nightMileages);
            costDetail.setNightSubsidyFee(nightFee);
        }

        return this;
    }

    /**
     * 添加回空费用(有级别的，暂时不用)
     *
     * @return
     */
    public EstimatedFareHelper addHaulBackFeeWithLevel() {
        isInit();
        double mileages = planMileage;//实际总里程
        double haulBackTrip = billingWay.getHaulBackTrip();//超过这个里程就算回空

        if (mileages <= haulBackTrip)
            return this;

        double haulBackMileages = mileages - haulBackTrip;//回空总里程
        double temp = haulBackMileages;

        int haulTimes = (int) Math.ceil(haulBackMileages / haulBackTrip);//计算分几个等级，进1
        double haulPrice = billingWay.getHaulBackTripFee();//回空初始价格
        double haulTotal = 0d;//回空总费用
        for (int i = 0; i < haulTimes; i++) {
            if (i != haulTimes - 1) {
                haulTotal += (haulBackTrip * (haulPrice * (i + 1)));
                temp -= haulBackTrip;
            } else {
                haulTotal += (temp * (haulPrice * (i + 1)));
            }
        }
        totalFee += haulTotal;

        costDetail.setHaulBackMileage(haulBackMileages);
        costDetail.setHaulBackSubsidyFee(haulTotal);
        return this;

    }


    /**
     * 添加回空费用
     *
     * @return
     */
    public EstimatedFareHelper addHaulBackFee() {
        isInit();
        double mileages = planMileage;//实际总里程
        double haulBackTrip = billingWay.getHaulBackTrip();//超过这个里程就算回空
        double haulPrice = billingWay.getHaulBackTripFee();//回空单价价格
        double haulTotal = 0d;//回空总费用

        if (mileages <= haulBackTrip)
            return this;

        double haulBackMileages = mileages - haulBackTrip;//回空总里程

        haulTotal = haulBackMileages * haulPrice;

        totalFee += haulTotal;

        costDetail.setHaulBackMileage(haulBackMileages);
        costDetail.setHaulBackSubsidyFee(haulTotal);
        return this;

    }


    /**
     * 添加超时等待费
     *
     * @return
     */
    public EstimatedFareHelper addBeyondWaitFee() {
        isInit();
        //预估的不管
        return this;
    }

    /**
     * 添加额外服务费
     *
     * @return
     */
    public EstimatedFareHelper addExtraServiceFee() {
        isInit();
        if (serviceFare != null) {
            totalFee += serviceFare;
            costDetail.setExtraServiceFee(serviceFare);
        }
        return this;
    }


    /**
     * 判断参数状态
     *
     * @throws IllegalStateException
     */
    public void isInit() throws IllegalStateException {
        if (billingWay == null) {
            throw new IllegalStateException("未设置计费方式");
        }
    }

    /**
     * 参数不能为空
     *
     * @param obj
     * @param msg
     */
    public void notNull(Object obj, String msg) {
        if (obj == null) {
            throw new IllegalStateException(msg);
        }
    }


}
