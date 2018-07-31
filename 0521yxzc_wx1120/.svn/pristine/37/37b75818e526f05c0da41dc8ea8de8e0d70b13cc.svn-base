package com.plugs.module_user.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.plugs.base.AjaxList;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_system.dtos.SysCouponDto;
import com.plugs.module_user.dtos.UserCouponDto;
import com.plugs.module_user.mappers.UserCouponMapper;
import com.plugs.utils.BizValidate;
import com.plugs.utils.DateUtils;
import com.plugs.utils.MathUtils;
import com.plugs.utils.NumberFormatUtil;
import com.util.MapUtil;
import com.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class UserCouponService extends BaseServiceSupport<UserCouponDto> {
    @Autowired
    private UserCouponMapper<UserCouponDto> userCouponMapper;

    @Override
    public IMapper<UserCouponDto> getMapper() {
        return userCouponMapper;
    }


    @Override
    public String getPK() {
        return "uuid";
    }


    /**
     * 根据用车类型查找可用电子券
     */
    public List<Map<String, Object>> findEnableCouponWithUseCarType(Map<String, Object> params) {
        return this.userCouponMapper.findEnableCouponWithUseCarType(params);
    }

    /**
     * 预估时获取可用电子券
     */
    public List<Map<String, Object>> findEnableCouponForEstimate(Map<String, Object> params) {
        return this.userCouponMapper.findEnableCouponForEstimate(params);
    }

    /**
     * 我的电子券
     */
    public List<Map<String, Object>> listMyCouponMap(Map<String, Object> params, int pageNum, int pageSize) {
        return this.userCouponMapper.listMyCouponMap(params, new PageBounds(pageNum, pageSize));
    }



    /** 释放优惠卷
     */
    public AjaxList release(UserCouponDto userCoupon) {
        UserCouponDto updUserCoupon = new UserCouponDto();
        updUserCoupon.setUuid(userCoupon.getUuid());
        updUserCoupon.setOrderUuid("");
        updUserCoupon.setStatus(UserCouponDto.STATUS_VALID);
        int result = this.userCouponMapper.edit(updUserCoupon);
        return result > 0 ? AjaxList.createSuccess("操作成功") : AjaxList.createError("释放电子券失败");
    }


    /**
     * 查询最优惠电子券的id和金额
     */
    public Map<String, Object> findMinCouponUuidAndMoney(String passengerUuid, Integer orderType, Double tripTotalFare, Map<String, Object> result, String areaUuid) {
        Map<String, Object> params = MapUtil.buildMap();
        //查询电子券
        params.put("userUuid", passengerUuid);
        params.put("orderType", orderType);
        params.put("toalFare", tripTotalFare);
        params.put("nowDate", DateUtils.format(new Date(), "yyyy-MM-dd") + " 00:00:00");
        params.put("endDate", DateUtils.format(new Date(), "yyyy-MM-dd") + " 23:59:59");
        params.put("areaUuid", StringUtil.isEmpty(areaUuid) ? "-1" : areaUuid);
        List<Map<String, Object>> list = this.userCouponMapper.findMinCoupon(params, new PageBounds(1, 1));

        if (list != null && list.size() > 0) {
            Map<String, Object> map = list.get(0);
            double couponMoney = Double.parseDouble(map.get("couponMoney").toString());
            if (couponMoney > tripTotalFare) {
                couponMoney = tripTotalFare;
            }
            result.put("userCouponUuid", map.get("uuid"));
            result.put("couponName", map.get("couponName"));//电子券名称
            result.put("couponMoney", NumberFormatUtil.roundAndFormat2TwoDecimal(couponMoney));
        } else {
            result.put("userCouponUuid", "");
            result.put("couponName", "");//电子券名称
            result.put("couponMoney", 0);
        }
        return result;
    }


    public Map<String, Object> setCouponAndMoney(String userCouponUuid, Integer orderType, double totalFee, Map<String, Object> result, String areaUuid) throws Exception {
        Map<String, Object> params = MapUtil.buildMap();
        //查询优惠券信息
        UserCouponDto userCoupon = null;//用户优惠券
        SysCouponDto sysCoupon = null;//具体优惠券信息
        if (StringUtil.isNotEmpty(userCouponUuid)) {
            //20180308 前端所传的用户优惠卷所区域不一定准确，添加此判断
            if (areaUuid != null) {
                params.clear();
                params.put("userCouponUuid", userCouponUuid);
                params.put("areaUuid", areaUuid);
                int count = this.userCouponMapper.getCountByUuidAndArea(params);
                if (count == 0) {
                    result.put("userCouponUuid", "");
                    result.put("couponMoney", 0);
                    result.put("couponName", "");
                    return result;
                }
            }

            params.clear();
            params.put("uuid", userCouponUuid);
            userCoupon = this.userCouponMapper.selInfoWithSysCoupon(params);
            if (userCoupon != null && userCoupon.getCouponDetail() != null) {
                sysCoupon = userCoupon.getCouponDetail();
            }
        }
        Double couponMoney = 0d;
        //判断优惠券的是否有效
        if (userCoupon != null && sysCoupon != null) {
            int days;
            if (sysCoupon.getTermType() == 1) {
                days = DateUtils.daysBetween(new Date(), sysCoupon.getUseEndTime());
            } else {
                days = sysCoupon.getUseExpireTime() - DateUtils.daysBetween(new Date(), userCoupon.getCreateTime());
            }
            //优惠劵状态、过期
            if (userCoupon.getStatus() != UserCouponDto.STATUS_VALID || days < 0) {
                BizValidate.bizErr("此优惠劵不可用", ReturnCodeConstants.ERR_ORDER_COUPON_UNABLE);
            }
            //订单类型与优惠劵类型
            int useCarType = sysCoupon.getUseCarType();
            //该优惠卷不为此用车类型，或者不是无限制用车类型优惠卷抛出异常
            if (!((""+useCarType).contains(""+orderType)) && useCarType != SysCouponDto.USE_CAR_TYPE_NOT_LIMIT) {
                BizValidate.bizErr("此优惠劵不适用于该订单", ReturnCodeConstants.ERR_ORDER_COUPON_NOT_SUITE);
            }
            //折扣优惠
            if (sysCoupon.getType() == SysCouponDto.COUPON_TYPE_DISCOUNT) {
                double miniMoney = MathUtils.mul(totalFee, (sysCoupon.getDiscount() / 10));
                couponMoney = totalFee - miniMoney;
                if (couponMoney > sysCoupon.getHighestMoney() && sysCoupon.getHighestMoney() > 0) {
                    couponMoney = sysCoupon.getHighestMoney();
                }
            } else {
                //金额优惠
                if (totalFee >= sysCoupon.getAstrict()) {
                    couponMoney = sysCoupon.getMoney();
                }
            }
        }
        //判断能优惠的金额是否超过本次行程金额
        if (couponMoney >= totalFee) {
            couponMoney = totalFee;//只能优惠该金额
        }
        if (userCoupon != null) {
            result.put("userCouponUuid", userCoupon.getUuid());
            result.put("couponMoney", NumberFormatUtil.roundAndFormat2TwoDecimal(couponMoney));
            result.put("couponName", StringUtil.isEmpty(sysCoupon.getName()) ? "用车优惠卷" : sysCoupon.getName());
        }
        return result;
    }

    public UserCouponDto selInfoWithSysCoupon(Map<String, Object> params) {
        return this.userCouponMapper.selInfoWithSysCoupon(params);
    }
}
