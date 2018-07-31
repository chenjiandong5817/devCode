package com.plugs.module_order.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.plugs.base.AjaxList;
import com.plugs.module_enterprise.dtos.EntAccountDetailDto;
import com.plugs.module_enterprise.dtos.EntAccountDto;
import com.plugs.module_enterprise.dtos.EntAccountRoleDto;
import com.plugs.module_enterprise.mappers.EntAccountDetailMapper;
import com.plugs.module_enterprise.services.EntAccountRoleService;
import com.plugs.module_enterprise.services.EntAccountService;
import com.plugs.module_order.dtos.*;
import com.plugs.module_order.mappers.OrderMapper;
import com.plugs.module_system.dtos.SysCarBillingWayDto;
import com.plugs.module_system.dtos.SysCouponDto;
import com.plugs.module_system.services.SysCarBillingWayService;
import com.plugs.module_user.dtos.UserCouponDto;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserCouponMapper;
import com.plugs.utils.DateUtils;
import com.plugs.utils.MathUtils;
import com.plugs.utils.NumberFormatUtil;
import com.plugs.utils.StringUtils;
import com.util.MapUtil;
import com.util.StringUtil;
import com.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单业务逻辑
 */
@Service
public class OrderService extends BaseServiceSupport<OrderDto> {

    @Autowired
    private OrderMapper<OrderDto> orderMapper;
    @Autowired
    private OrderCostDetailService orderCostDetailService;
    @Autowired
    private SysCarBillingWayService billingWayService;
    @Autowired
    private UserCouponMapper<UserCouponDto> userCouponMapper;
    @Autowired
    private EntAccountRoleService entAccountRoleService;
    @Autowired
    private EntAccountService entAccountService;
    @Autowired
    private EntAccountDetailMapper<EntAccountDetailDto> entAccountDetailMapper;

    @Override
    public IMapper<OrderDto> getMapper() {
        return orderMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

    public AjaxList orderFare(UserPassengerDto passenger, String orderUuid) {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", orderUuid);
        OrderDto order = orderMapper.selInfo(params);
        Validate.notNull(order, "查询不到该订单");
        return AjaxList.createSuccess("获取成功", getOrderFare(passenger, order));
    }



    /**
     * 获取行程费用
     */
    private Map<String, Object> getOrderFare(UserPassengerDto passenger, OrderDto order) {
        Map<String, Object> result = MapUtil.buildMap();//返回结果
        //查询订单明细
        Map<String, Object> params = MapUtil.buildMap();
        params.put("orderUuid", order.getUuid());
        OrderCostDetailDto detail = this.orderCostDetailService.selInfo(params);
        result.put("isCancelPay", false);//是否取消付费
        result.put("isDebited", false);//是否已经扣款过
        if (order.getSubStatus() != OrderDto.ORDER_SUB_STATUS_CANCEL_NOT_PAY) {
            result.put("totalFee", NumberFormatUtil.roundAndFormat2TwoDecimal(order.getWaitPayFee() == null ? 0 : order.getWaitPayFee()));//行程费用
            result.put("waitPayFee", NumberFormatUtil.roundAndFormat2TwoDecimal(order.getWaitPayFee() == null ? 0 : order.getWaitPayFee()));//行程费用

            //存在预支付及已支付过不能重新使用优惠卷
            if ((order.getWaitPayFee() != null && order.getTotalFee() != null && Math.floor(order.getWaitPayFee()) != Math.floor(order.getTotalFee())
                    && order.getPrepaidFee() == 0 && order.getPrepaidStatus() != OrderPrepaidDto.PAY_STATUS_SUCCESS)
                    || (order.getPrepaidFee() > 0 && order.getPrepaidStatus() == OrderPrepaidDto.PAY_STATUS_SUCCESS)){
                result.put("isDebited", true);//已经扣款过
            }

            //todo:20171216增加预付款判断
            String preChiceCouponUuid = order.getPreChoiceUserCouponUuid();
            if (order.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_NOT_PAY  && (Math.floor(order.getTotalFee()) == Math.floor(order.getWaitPayFee())
                    || (order.getPrepaidStatus() == OrderPrepaidDto.PAY_STATUS_SUCCESS && (Math.floor(order.getTotalFee()) == Math.floor(order.getWaitPayFee() + order.getPrepaidFee()))))
                    && preChiceCouponUuid != null && !"-1".equals(preChiceCouponUuid)) {

                //20180102优选乘客预选
                boolean flag = true;
                if (StringUtils.isNotEmpty(preChiceCouponUuid)) {
                    params.put("uuid", preChiceCouponUuid);
                    UserCouponDto userCouponDto = this.userCouponMapper.selInfoWithSysCoupon(params);
                    if (userCouponDto != null && (userCouponDto.getStatus() == UserCouponDto.STATUS_VALID)
                            || userCouponDto.getStatus() == UserCouponDto.STATUS_OCCUPY && userCouponDto.getUuid().equals(preChiceCouponUuid)) {
                        SysCouponDto sysCoupon = userCouponDto.getCouponDetail(); //系统优惠卷
                        //计算有效天数
                        int days = sysCoupon.getTermType() == 1 ? DateUtils.daysBetween(new Date(), sysCoupon.getUseEndTime())
                                : sysCoupon.getUseExpireTime() - DateUtils.daysBetween(new Date(), userCouponDto.getCreateTime());

                        double couponMoney = 0D;
                        double couponCountMoney = order.getTripTotalFare() + detail.getAdjusteFee(); //行程金额+调整金额
                        if (days > 0) {
                            result.put("userCouponUuid", userCouponDto.getUuid());//电子券UUID
                            result.put("couponName", sysCoupon.getName());//电子券名称
                            if (sysCoupon.getType() == SysCouponDto.COUPON_TYPE_DISCOUNT) {
                                couponMoney = couponCountMoney - MathUtils.mul(order.getTripTotalFare() + detail.getAdjusteFee(), (sysCoupon.getDiscount() / 10));
                                if (couponMoney > sysCoupon.getHighestMoney() && sysCoupon.getHighestMoney() > 0) {
                                    couponMoney = sysCoupon.getHighestMoney();
                                }
                            } else {
                                if (couponCountMoney >= sysCoupon.getAstrict()) {
                                    couponMoney = sysCoupon.getMoney();
                                }
                            }
                            result.put("couponMoney", NumberFormatUtil.roundAndFormat2TwoDecimal(couponMoney));//可优惠金额
                            flag = false;
                        }
                    }

                    if (flag) {
                        params.clear();
                        params.put("userUuid", passenger.getUuid());
                        params.put("useCarType", order.getOrderType());
                        params.put("toalFare", order.getTripTotalFare() + detail.getAdjusteFee());//只抵扣行程相关费用
                        params.put("nowDate", DateUtils.format(new Date(), "yyyy-MM-dd") + " 00:00:00");
                        params.put("endDate", DateUtils.format(new Date(), "yyyy-MM-dd") + " 23:59:59");
                        params.put("areaUuid", StringUtil.isEmpty(order.getAreaUuid()) ? "-1" : order.getAreaUuid());
                        List<Map<String, Object>> list = this.userCouponMapper.findMinCoupon(params, new PageBounds(1, 1));
                        if (list != null && list.size() > 0) {
                            Map<String, Object> map = list.get(0);
                            result.put("userCouponUuid", map.get("uuid"));//电子券UUID
                            result.put("couponName", map.get("couponName"));//电子券名称
                            double couponMoney = Double.parseDouble(map.get("couponMoney").toString());
                            if (couponMoney > (order.getTripTotalFare() + detail.getAdjusteFee())) {
                                couponMoney = (order.getTripTotalFare() + detail.getAdjusteFee());
                            }
                            result.put("couponMoney", NumberFormatUtil.roundAndFormat2TwoDecimal(couponMoney));//可优惠金额
                        }
                    }
                }
            }
        } else {
            //如果是取消需要支付的话
            result.put("isCancelPay", true);
            result.put("totalFee", NumberFormatUtil.roundAndFormat2TwoDecimal(order.getWaitPayFee() == null ? 0 : order.getWaitPayFee()));//取消费
            result.put("waitPayFee", NumberFormatUtil.roundAndFormat2TwoDecimal(order.getWaitPayFee() == null ? 0 : order.getWaitPayFee()));//取消费
        }
        //企业余额
        if (StringUtil.isNotEmpty(passenger.getEntAccountUuid())) {
            params.clear();
            params.put("uuid", passenger.getEntAccountUuid());
            EntAccountDto ent = this.entAccountService.selInfo(params);
            if (ent != null) {
                double availableBalance = 0d;
                params.clear();
                params.put("entAccountUuid", passenger.getEntAccountUuid());
                params.put("userUuid", passenger.getUuid());
                EntAccountRoleDto role = this.entAccountRoleService.selInfo(params);
                if (role != null) {
                    if (role.getLimitMoney() > 0) {
                        //查询本月已用
                        params.clear();
                        params.put("sumField", "money");
                        params.put("userUuid", passenger.getUuid());
                        params.put("thisMonth", 1);
                        double limitUsed = entAccountDetailMapper.sum(params);

                        if ((role.getLimitMoney() - limitUsed) < 0) {
                            availableBalance = 0;
                        } else {
                            availableBalance = role.getLimitMoney() - limitUsed;
                        }
                    }
                }
                result.put("isShowEnt", 1);
                result.put("entAvailableBalance", NumberFormatUtil.roundAndFormatToInt(availableBalance));//可用余额
            }
        } else {
            //不显示企业余额
            result.put("isShowEnt", 0);
        }
        //账户余额
        double balance = passenger.getBalance();
        result.put("balance", NumberFormatUtil.roundAndFormatToInt(balance));

        // 获取计费方式
        SysCarBillingWayDto billingWay = billingWayService.findByLevelTypeAndOrderType(order.getLevelType(), order.getOrderType(), order.getAreaUuid());

        result.put("beyondWaitTimePrice", billingWay.getBeyondWaitTime());//超时等待费单价
        result.put("costItems", detail.transfToItems(order.getAssignDriverFare(), order.getTip(), order.getCancelFee()));//费用列表
        result.put("levelType", order.getLevelType());

        if (order.getServiceTimeStart() != null && order.getDriverArriveTime() != null) {
            long waitTime = (order.getServiceTimeStart().getTime() - order.getDriverArriveTime().getTime()) / 1000;//超时等待计算的时间
            int waitTimeMinute = Math.round(waitTime / 60);//超时时间
            result.put("driverWaitTime", waitTimeMinute);
        }
        return result;
    }


}

