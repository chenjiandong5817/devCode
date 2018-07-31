package com.plugs.module_wechat.api;

import com.plugs.base.AjaxList;
import com.plugs.base.BaseExtendApi;
import com.plugs.constants.ConfigConstants;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.services.OrderService;
import com.plugs.module_user.dtos.UserCouponDto;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.services.UserCouponService;
import com.plugs.utils.BizValidate;
import com.plugs.utils.StringUtils;
import com.util.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 用户优惠卷API
 *
 * @outhor qfHan
 * @create 2017-10-09 10:59
 */
@Controller
@RequestMapping("/wechat/coupon")
@Api(value = "微信优惠卷", description = "微信优惠卷")
public class WeChatCouponApi extends BaseExtendApi {

    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private OrderService orderService;

    /**
     * 我的电子券（已使用和为使用，通过参数来获取）
     *
     * @param openId  微信用户openId
     * @param status  电子券状态(1:有效，2:已使用)
     * @param pageNum 页码(默认1)
     * @throws Exception
     */
    @RequestMapping(value = "/listMyCouponMap", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取电子券列表", notes = "获取电子券列表的接口信息", httpMethod = "GET")
    public AjaxList listMyCouponMap(HttpServletRequest request,
                       @ApiParam(name = "openId", required = true, value = "openId") @RequestParam String openId,
                       @ApiParam(name = "status", required = true, value = "电子券状态(1:有效，2:已使用)") @RequestParam Integer status,
                       @ApiParam(name = "pageNum", value = "页码(默认1)") @RequestParam(required = false) Integer pageNum) throws Exception {
        //根据openId获取乘客信息
        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);

        Map<String, Object> params = MapUtil.buildMap();
        params.put("userUuid", userPassengerDto.getUuid());
        params.put("status", status);
        params.put("useCarTypeLike23", 1);
        pageNum = super.dealPageNum(pageNum); //页码默认1
        List<Map<String, Object>> userCoupons = this.userCouponService.listMyCouponMap(params, pageNum, ConfigConstants.PAGESIZE_10);
        return AjaxList.createSuccess(userCoupons.size(), "获取成功", userCoupons);
    }


    /**
     * 支付选择优惠券
     * 根据订单类型获取可用电子券列表
     *
     * @param openId    微信用户openId
     * @param orderUuid 订单UUID
     * @throws Exception
     */
    @RequestMapping(value = "/findEnableCouponWithUseCarType", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "[支付选择]根据订单类型获取可用电子券列表", notes = "[支付选择]根据订单类型获取可用电子券列表", httpMethod = "GET")
    public AjaxList findEnableCouponWithUseCarType(HttpServletRequest request,
                                                   @ApiParam(name = "openId", required = true, value = "openId") @RequestParam String openId,
                                                   @ApiParam(name = "orderUuid", required = true, value = "订单UUID") @RequestParam String orderUuid) throws Exception {

        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", orderUuid);
        OrderDto order = this.orderService.selInfo(params);
        if (order == null) {
            BizValidate.bizErr("订单不存在", ReturnCodeConstants.ERR_ORDER_NOT_EXIST);
        }

        //根据openId获取乘客信息
        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);

        double totalFare = order.getTripTotalFare();

        params.clear();
        params.put("userUuid", userPassengerDto.getUuid());
        params.put("orderType", order.getOrderType());
        params.put("totalFare", totalFare);
        params.put("areaUuid", StringUtils.isEmpty(order.getAreaUuid()) ? "-1" : order.getAreaUuid());
        params.put("orderUuid", orderUuid);
        List<Map<String, Object>> userCouponDtos = this.userCouponService.findEnableCouponWithUseCarType(params);
        return AjaxList.createSuccess(userCouponDtos.size(), "获取成功", userCouponDtos);
    }

    /**
     * 预估获取可用电子券
     *
     * @param openId    微信用户openId
     * @param orderType 订单类型
     * @param areaUuid  区域UUID
     * @throws Exception
     */
    @RequestMapping(value = "/findEnableCouponForEstimate", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "预估根据订单类型获取可用电子券列表", notes = "预估根据订单类型获取可用电子券列表", httpMethod = "GET")
    public AjaxList findEnableCouponForEstimate(HttpServletRequest request,
                                                @ApiParam(name = "openId", required = true, value = "openId") @RequestParam String openId,
                                                @ApiParam(name = "orderType", required = true, value = "订单类型") @RequestParam Integer orderType,
                                                @ApiParam(name = "areaUuid", required = false, value = "区域UUID") @RequestParam(required = false) String areaUuid) throws Exception {

        //根据openId获取乘客信息
        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);

        Map<String, Object> params = MapUtil.buildMap();
        params.put("userUuid", userPassengerDto.getUuid());
        params.put("orderType", orderType);
        params.put("areaUuid", areaUuid);
        List<Map<String, Object>> userCouponDtos = this.userCouponService.findEnableCouponForEstimate(params);
        return AjaxList.createSuccess(userCouponDtos.size(), "获取成功", userCouponDtos);
    }


    /**
     * 释放电子券
     *
     * @param openId         微信用户openId
     * @param userCouponUuid 电子券UUID
     * @throws Exception
     */
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "如果用户取消支付，需要释放电子券的占用状态", notes = "如果用户取消支付，需要释放电子券的占用状态", httpMethod = "POST")
    public AjaxList release(HttpServletRequest request,
                            @ApiParam(name = "openId", required = true, value = "openId") @RequestParam String openId,
                            @ApiParam(name = "userCouponUuid", required = true, value = "电子券UUID") @RequestParam String userCouponUuid) throws Exception {


        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", userCouponUuid);
        UserCouponDto userCoupon = this.userCouponService.selInfo(params);
        if (userCoupon == null) {
            BizValidate.bizErr("电子券不存在", ReturnCodeConstants.ERR_10006_LOGIC_ERR);
        }

        //根据openId获取乘客信息
        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);

        if (StringUtils.isNotEmpty(userCoupon.getUserUuid()) && !userCoupon.getUserUuid().equals(userPassengerDto.getUuid())) {
            BizValidate.bizErr("没有操作该电子券的权限", ReturnCodeConstants.ERR_10006_LOGIC_ERR);
        }
        if (userCoupon.getStatus() != UserCouponDto.STATUS_OCCUPY) {
            BizValidate.bizErr("该电子券不是占用状态", ReturnCodeConstants.ERR_10006_LOGIC_ERR);
        }
        return this.userCouponService.release(userCoupon);
    }


}