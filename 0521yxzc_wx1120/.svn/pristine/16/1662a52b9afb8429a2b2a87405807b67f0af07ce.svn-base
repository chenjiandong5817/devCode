package com.plugs.module_order.dtos;

import com.plugs.module_order.pojo.OrderCostItem;
import com.plugs.utils.NumberFormatUtil;
import com.util.MapUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderCostDetailDto {

    private String uuid;        //主键
    private String orderUuid;        //订单ID
    private double startMileage;        //起步里程
    private int startTimeLength;        //起步时长
    private double startFee;        //起步价
    private double beyondMileage;        //超出里程
    private double mileageFee;        //里程费用
    private int beyondTimeLength;        //超出时长
    private double timeFee;        //时长费用
    private double nightMileage;        //夜间补贴公里数
    private double nightSubsidyFee;        //夜间补贴费
    private double haulBackMileage;        //回空里程数
    private double haulBackSubsidyFee;        //回空补贴费
    private double extraServiceFee;        //额外服务费
    private int freeWaitTime;//免费等待时长
    private int beyondWaitTime;//超出等待时长
    private double beyondWaitFee;        //超时等待费
    private String couponName;        //电子券名称
    private double couponFee;        //电子券抵扣费用
    private double adjusteFee;        //费用调整
    private String adjustRemark;    //调整说明
    private Date createTime;        //

    //非数据库字段
    private double totalFee;//总价
    private double beyondWaitTimePrice;//超时等待单价

    public OrderCostDetailDto() {

    }


    public String getAdjustRemark() {
        return adjustRemark;
    }

    public void setAdjustRemark(String adjustRemark) {
        this.adjustRemark = adjustRemark;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setStartMileage(double startMileage) {
        this.startMileage = startMileage;
    }

    public double getStartMileage() {
        return startMileage;
    }

    public void setStartTimeLength(int startTimeLength) {
        this.startTimeLength = startTimeLength;
    }

    public int getStartTimeLength() {
        return startTimeLength;
    }

    public void setStartFee(double startFee) {
        this.startFee = startFee;
    }

    public double getStartFee() {
        return startFee;
    }

    public void setBeyondMileage(double beyondMileage) {
        this.beyondMileage = beyondMileage;
    }

    public double getBeyondMileage() {
        return beyondMileage;
    }

    public void setMileageFee(double mileageFee) {
        this.mileageFee = mileageFee;
    }

    public double getMileageFee() {
        return mileageFee;
    }

    public void setBeyondTimeLength(int beyondTimeLength) {
        this.beyondTimeLength = beyondTimeLength;
    }

    public int getBeyondTimeLength() {
        return beyondTimeLength;
    }

    public void setTimeFee(double timeFee) {
        this.timeFee = timeFee;
    }

    public double getTimeFee() {
        return timeFee;
    }

    public void setNightMileage(double nightMileage) {
        this.nightMileage = nightMileage;
    }

    public double getNightMileage() {
        return nightMileage;
    }

    public void setNightSubsidyFee(double nightSubsidyFee) {
        this.nightSubsidyFee = nightSubsidyFee;
    }

    public double getNightSubsidyFee() {
        return nightSubsidyFee;
    }

    public void setHaulBackMileage(double haulBackMileage) {
        this.haulBackMileage = haulBackMileage;
    }

    public double getHaulBackMileage() {
        return haulBackMileage;
    }

    public void setHaulBackSubsidyFee(double haulBackSubsidyFee) {
        this.haulBackSubsidyFee = haulBackSubsidyFee;
    }

    public double getHaulBackSubsidyFee() {
        return haulBackSubsidyFee;
    }

    public void setExtraServiceFee(double extraServiceFee) {
        this.extraServiceFee = extraServiceFee;
    }

    public double getExtraServiceFee() {
        return extraServiceFee;
    }

    public double getBeyondWaitFee() {
        return beyondWaitFee;
    }

    public void setBeyondWaitFee(double beyondWaitFee) {
        this.beyondWaitFee = beyondWaitFee;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponFee(double couponFee) {
        this.couponFee = couponFee;
    }

    public double getCouponFee() {
        return couponFee;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getFreeWaitTime() {
        return freeWaitTime;
    }

    public void setFreeWaitTime(int freeWaitTime) {
        this.freeWaitTime = freeWaitTime;
    }

    public int getBeyondWaitTime() {
        return beyondWaitTime;
    }

    public void setBeyondWaitTime(int beyondWaitTime) {
        this.beyondWaitTime = beyondWaitTime;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public double getBeyondWaitTimePrice() {
        return beyondWaitTimePrice;
    }

    public void setBeyondWaitTimePrice(double beyondWaitTimePrice) {
        this.beyondWaitTimePrice = beyondWaitTimePrice;
    }

    public double getAdjusteFee() {
        return adjusteFee;
    }

    public void setAdjusteFee(double adjusteFee) {
        this.adjusteFee = adjusteFee;
    }


    /**
     * 转换成map,并进行格式化
     *
     * @param isEntPayed
     * @return
     */
    public Map<String, Object> toMap4Socket(boolean isEntPayed) {
        Map<String, Object> map = MapUtil.buildMap();

        map.put("uuid", uuid);
        map.put("orderUuid", orderUuid);
        map.put("startFee", NumberFormatUtil.roundAndFormat2TwoDecimal(startFee));//起步价
        map.put("startMileage", NumberFormatUtil.roundAndFormat2TwoDecimal(startMileage));//起步里程
        map.put("startTimeLength", NumberFormatUtil.formatToInt(startTimeLength));//起步时长
        map.put("beyondMileage", NumberFormatUtil.roundAndFormat2TwoDecimal(beyondMileage));//超出里程
        map.put("mileageFee", NumberFormatUtil.roundAndFormat2TwoDecimal(mileageFee));//里程费
        map.put("beyondTimeLength", NumberFormatUtil.formatToInt(beyondTimeLength));//超出时长
        map.put("timeFee", NumberFormatUtil.roundAndFormat2TwoDecimal(timeFee));//时长费用
        map.put("nightMileage", NumberFormatUtil.roundAndFormat2TwoDecimal(nightMileage));
        map.put("nightSubsidyFee", NumberFormatUtil.roundAndFormat2TwoDecimal(nightSubsidyFee));
        map.put("haulBackMileage", NumberFormatUtil.roundAndFormat2TwoDecimal(haulBackMileage));
        map.put("haulBackSubsidyFee", NumberFormatUtil.roundAndFormat2TwoDecimal(haulBackSubsidyFee));
        map.put("extraServiceFee", NumberFormatUtil.roundAndFormat2TwoDecimal(extraServiceFee));
        map.put("freeWaitTime", NumberFormatUtil.formatToInt(freeWaitTime));
        map.put("beyondWaitTime", NumberFormatUtil.formatToInt(beyondWaitTime));
        map.put("beyondWaitFee", NumberFormatUtil.roundAndFormat2TwoDecimal(beyondWaitFee));
        map.put("adjusteFee", NumberFormatUtil.roundAndFormat2TwoDecimal(adjusteFee));//调整费用
        map.put("couponFee", NumberFormatUtil.roundAndFormat2TwoDecimal(couponFee));
        map.put("totalFee", NumberFormatUtil.roundAndFormat2TwoDecimal(totalFee));
        map.put("couponName", couponName);
        map.put("createTime", createTime);
        map.put("adjustRemark", adjustRemark);//调整费用
        map.put("beyondWaitTimePrice", NumberFormatUtil.roundAndFormat2TwoDecimal(beyondWaitTimePrice));
        map.put("isEntPayed", isEntPayed);

        return map;
    }

    /**
     * 转换成列表
     *
     * @return
     */
    public List<OrderCostItem> transfToItems(Double assignDriverFee, Double tip, Double cancelFee) {
        if (cancelFee == null) {
            cancelFee = -1d;
        }
        List<OrderCostItem> list = new ArrayList<OrderCostItem>();
        int sort = 1;
        if (startFee > 0d && cancelFee < 0d) {
            list.add(new OrderCostItem("起步费(" + (int) startMileage + "公里," + startTimeLength + "分钟)", NumberFormatUtil.roundAndFormat2TwoDecimal(startFee), sort++));
        }
        if (mileageFee > 0d && cancelFee < 0d) {
            list.add(new OrderCostItem("超出里程费(" + (int) beyondMileage + "公里)", NumberFormatUtil.roundAndFormat2TwoDecimal(mileageFee), sort++));
        }
        if (timeFee > 0d && cancelFee < 0d) {
            list.add(new OrderCostItem("超出时长费(" + beyondTimeLength + "分钟)", NumberFormatUtil.roundAndFormat2TwoDecimal(timeFee), sort++));
        }
        if (haulBackSubsidyFee > 0d && cancelFee < 0d) {
            list.add(new OrderCostItem("回空补贴(" + (int) haulBackMileage + "公里)", NumberFormatUtil.roundAndFormat2TwoDecimal(haulBackSubsidyFee), sort++));
        }
        if (nightSubsidyFee > 0d && cancelFee < 0d) {
            list.add(new OrderCostItem("夜间补贴(" + (int) nightMileage + "公里)", NumberFormatUtil.roundAndFormat2TwoDecimal(nightSubsidyFee), sort++));
        }
        if (beyondWaitFee > 0d && cancelFee < 0d) {
            list.add(new OrderCostItem("超时等待费(" + beyondWaitTime + "分钟)", NumberFormatUtil.roundAndFormat2TwoDecimal(beyondWaitFee), sort++));
        }
        if (cancelFee != null && cancelFee > 0d) {
            list.add(new OrderCostItem("取消费", NumberFormatUtil.roundAndFormat2TwoDecimal(cancelFee), sort++));
        }
        if (tip != null && tip > 0d && cancelFee < 0d) {
            list.add(new OrderCostItem("小费", NumberFormatUtil.roundAndFormat2TwoDecimal(tip), sort++));
        }
        if (extraServiceFee > 0d && cancelFee < 0d) {
            list.add(new OrderCostItem("附加服务费", NumberFormatUtil.roundAndFormat2TwoDecimal(extraServiceFee), sort++));
        }
        if (assignDriverFee != null && assignDriverFee > 0d && cancelFee < 0d) {
            list.add(new OrderCostItem("指定司机费", NumberFormatUtil.roundAndFormat2TwoDecimal(assignDriverFee), sort++));
        }
        //if (couponFee > 0d) {
        //    list.add(new OrderCostItem("电子券(" + couponName + ")", "-" + NumberFormatUtil.roundAndFormat2TwoDecimal(couponFee), sort++));
        //}
        if (adjusteFee > 0d) {
            list.add(new OrderCostItem("费用调整", "-" + NumberFormatUtil.roundAndFormat2TwoDecimal(adjusteFee), sort++));
        }
        return list;
    }

    public List<OrderCostItem> transfToItems(Double assignDriverFee, Double tip, Double cancelFee, int payType, double payFee) {
        List<OrderCostItem> list = transfToItems(assignDriverFee, tip, cancelFee);
        //支付方式（1.支付宝 2.微信 3.余额 4.企业支付 5.快钱支付）
        String item = getItemByCode(payType);
        if (payFee > 0d) {
            list.add(new OrderCostItem(item, NumberFormatUtil.roundAndFormat2TwoDecimal(payFee), 100));
        }
        return list;
    }

    public static String getItemByCode(int payType) {
        String item = "支付";
        if (payType == 1) {
            item = "支付宝支付";
        } else if (payType == 2) {
            item = "微信支付";
        } else if (payType == 3) {
            item = "余额支付";
        } else if (payType == 4) {
            item = "企业支付";
        } else if (payType == 5) {
            item = "信用卡支付";
        } else if (payType == 6) {
            item = "苹果支付";
        }
        return item;
    }


    public static void main(String[] args) {

    }
}
