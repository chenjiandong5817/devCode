package com.plugs.module_wechat.api;

import com.plugs.base.AjaxList;
import com.plugs.base.BaseExtendApi;
import com.plugs.constants.ConfigConstants;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_enterprise.dtos.EntAccountDepartmentDto;
import com.plugs.module_enterprise.dtos.EntAccountDto;
import com.plugs.module_enterprise.dtos.EntAccountRoleDto;
import com.plugs.module_enterprise.services.EntAccountDepartmentService;
import com.plugs.module_enterprise.services.EntAccountDetailService;
import com.plugs.module_enterprise.services.EntAccountRoleService;
import com.plugs.module_enterprise.services.EntAccountService;
import com.plugs.module_order.dtos.OrderCostDetailDto;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.services.OrderService;
import com.plugs.module_system.dtos.SysCarBillingWayDto;
import com.plugs.module_system.services.SysCarBillingWayService;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.services.UserCouponService;
import com.plugs.utils.*;
import com.plugs.utils.gaodemap.GaodeMapsUtils;
import com.util.DateUtil;
import com.util.MapUtil;
import com.util.StringUtil;
import com.util.Validate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/wechat/price")
@Api(value = "微信价格", description = "微信价格")
public class WeChatPriceApi extends BaseExtendApi{

    @Autowired
    private SysCarBillingWayService billingWayService;
    @Autowired
    private EntAccountService entAccountService;
    @Autowired
    private EntAccountRoleService roleService;
    @Autowired
    private EntAccountDepartmentService entAccountDepartmentService;
    @Autowired
    private EntAccountDetailService entAccountDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserCouponService userCouponService;

    @RequestMapping(value = "/disAndDura", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "两点之间测距与时间", httpMethod = "POST", notes = "两点之间测距与时间")
    public AjaxList getDrivingDistanceAndDuration(HttpServletRequest request,
                                  @ApiParam(name = "oriLng", required = true, value = "出发点经度") @RequestParam double oriLng,
                                  @ApiParam(name = "oriLat", required = true, value = "出发点纬度") @RequestParam double oriLat,
                                  @ApiParam(name = "destLng", required = true, value = "目的地经度") @RequestParam double destLng,
                                  @ApiParam(name = "destLat", required = true, value = "目的地纬度") @RequestParam double destLat) throws Exception {
        Validate.notNull(oriLng, "缺少请求参数[oriLng]");
        Validate.notNull(oriLat, "缺少请求参数[oriLat]");
        Validate.notNull(destLng, "缺少请求参数[destLng]");
        Validate.notNull(destLat, "缺少请求参数[destLat]");

        Object obj = GaodeMapsUtils.getDrivingDistanceAndDuration(oriLng, oriLat, destLng, destLat);
        if (null == obj) {
            return AjaxList.createError("计算失败");
        }
        return AjaxList.createSuccess("计算结果", obj);
    }



    @RequestMapping(value = "/orderFare", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "订单费用", notes = "订单费用", httpMethod = "GET")
    public AjaxList orderFare(HttpServletRequest request,
                              @ApiParam(name = "openId", required = true, value = "微信openid") @RequestParam(value = "openId") String openId,
                              @ApiParam(name = "orderUuid", required = true, value = "订单(行程)UUID") @RequestParam String orderUuid) throws Exception {
        UserPassengerDto passenger = (UserPassengerDto)request.getSession().getAttribute(ConfigConstants.SESSION_KEY_PASSENGER_USER_WX);

        return this.orderService.orderFare(passenger, orderUuid);
    }


    /**
     * 预估费用
     */
    @RequestMapping(value = "/estimatedFare", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信叫车预估费用", httpMethod = "POST", notes = "微信叫车预估费用")
    public AjaxList estimatedFare(HttpServletRequest request,
                                  @ApiParam(name = "openId", required = true, value = "微信openid") @RequestParam(value = "openId") String openId,
                                  @ApiParam(name = "orderType", required = true, value = "订单类型（1.预约,5立即用车）") @RequestParam Integer orderType,
                                  @ApiParam(name = "levelType", required = true, value = "车型级别") @RequestParam Integer levelType,
                                  @ApiParam(name = "planMileage", required = true, value = "预估里程") @RequestParam Double planMileage,
                                  @ApiParam(name = "planDuration", required = true, value = "预估时长(分钟)") @RequestParam Integer planDuration,
                                  @ApiParam(name = "userCouponUuid", required = false, value = "用户优惠券UUID") @RequestParam(required = false) String userCouponUuid,
                                  @ApiParam(name = "areaUuid", required = false, value = "areaUuid") @RequestParam(required = false) String areaUuid,
                                  @ApiParam(name = "deparTimeStr", required = false, value = "预约时间（例如：yyyy-MM-dd HH:mm）") @RequestParam(required = false) String deparTimeStr) throws Exception {
        //判断参数
        Validate.notNull(levelType, "缺少请求参数[levelType]");
        Validate.notNull(planMileage, "缺少请求参数[planMileage]");
        Validate.notNull(planDuration, "缺少请求参数[planDuration]");
        //预约时间
        Date deparTime;
        if (orderType != OrderDto.ORDER_TYPE_IMMED) {
            Validate.notEmpty(deparTimeStr, "缺少请求参数[deparTime]");
            deparTime = DateUtil.getDateFromStr(deparTimeStr, "yyyy-MM-dd HH:mm");
        } else {
            deparTime = new Date();
        }

        UserPassengerDto passenger = (UserPassengerDto) request.getSession().getAttribute(ConfigConstants.SESSION_KEY_PASSENGER_USER_WX);

        //获取计费方式
        SysCarBillingWayDto billingWay = this.billingWayService.findByLevelTypeAndOrderType(levelType, orderType, areaUuid);
        if (billingWay == null) {
            BizValidate.bizErr("获取不到车型计费方式", ReturnCodeConstants.ERR_10006_LOGIC_ERR);
        }

        EstimatedFareHelper estimatedFareHelper = new EstimatedFareHelper(billingWay, 0D, planMileage, planDuration, deparTime);

        estimatedFareHelper.addStartFee()//起步费
                .addExtraServiceFee()//额外服务费
                .addTimeLengthFee()//时长费
                .addMileageFee()//里程费
                .addNightSubsidyFee()//夜间补贴
                .addHaulBackFee();//回空

        double totalFee = estimatedFareHelper.getTotalFee();
        OrderCostDetailDto costDetailDto = estimatedFareHelper.getCostDetail();
        //返回给客户端的结果
        Map<String, Object> result = MapUtil.buildMap();

        result.put("balance", passenger.getBalance());

        if (StringUtil.isEmpty(userCouponUuid)) {
            //优惠券金额放入result
            result = this.userCouponService.findMinCouponUuidAndMoney(passenger.getUuid(), orderType, totalFee, result, areaUuid);
        } else {
            if (!"-1".equals(userCouponUuid)) {
                result = this.userCouponService.setCouponAndMoney(userCouponUuid, orderType, totalFee, result, areaUuid);
            } else {
                result.put("userCouponUuid", "");
                result.put("couponMoney", 0);
                result.put("couponName", "");
            }
        }

        //totalFee -= couponMoney;
        if (totalFee < 0) {
            totalFee = 0;
        }
        costDetailDto.setTotalFee(totalFee);//总金额
        result.put("totalFee", NumberFormatUtil.roundAndFormatToInt(totalFee));//总金额-电子券，明细
        //result.put("costDetail", costDetailDto.toMap4Socket(false));
        result.put("costItems", costDetailDto.transfToItems(0d, 0d, -1d));

        //用户不是企业员工
        if (StringUtil.isEmpty(passenger.getEntAccountUuid()))
            return noEnt(result);

        //查不到该企业
        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", passenger.getEntAccountUuid());
        EntAccountDto ent = this.entAccountService.selInfo(params);
        if (ent == null)
            return noEnt(result);

        //是否小于保证金，小于则返回企业余额不足
        if (ent.getBalance() < ent.getSecurityMoney()) {
            result.put("isShowEnt", 1);
            result.put("entAvailableBalance", NumberFormatUtil.roundAndFormatToInt(ent.getBalance()));
            result.put("limitIsEnough", false);
            result.put("entBalanceIsEnough", false);
            return AjaxList.createSuccess("获取成功", result);
        }

        //没有角色信息
        params.clear();
        params.put("entAccountUuid", passenger.getEntAccountUuid());
        params.put("userUuid", passenger.getUuid());
        EntAccountRoleDto role = roleService.selInfo(params);
        if (role == null)
            return noEnt(result);

        //没有部门
        params.clear();
        params.put("uuid", passenger.getDepartmentUuid());
        EntAccountDepartmentDto department = entAccountDepartmentService.selInfo(params);
        if (department == null)
            return noEnt(result);

        //没有限额的情况
        if (role.getMonthlyLimit() == EntAccountRoleDto.MONTHLYLIMIT_NO) {

            if (ent.getBalance() < totalFee) {
                result.put("isShowEnt", 1);
                result.put("entAvailableBalance", NumberFormatUtil.roundAndFormatToInt(ent.getBalance()));//可用余额
                result.put("limitIsEnough", false);
                result.put("entBalanceIsEnough", false);
                return AjaxList.createSuccess("获取成功", result);
            } else {
                result.put("isShowEnt", 1);
                result.put("entBalanceIsEnough", true);
                result.put("entAvailableBalance", NumberFormatUtil.roundAndFormatToInt(ent.getBalance()));//可用余额
                result.put("limitIsEnough", true);
                return AjaxList.createSuccess("获取成功", result);
            }
        }

        Double miniLimit = null;//表示不限
        //个人剩余限额
        if (role.getMonthlyLimit() == EntAccountRoleDto.MONTHLYLIMIT_YES) {
            params.clear();
            params.put("sumField", "money");
            params.put("thisMonth", 1);
            params.put("userUuid", passenger.getUuid());
            miniLimit = role.getLimitMoney() - entAccountDetailService.sum(params);
        }


        //部门剩余限额
        if (department.getMonthlyLimit() == EntAccountDepartmentDto.MONTHLYLIMIT_YES) {
            params.clear();
            params.put("departmentUuid", passenger.getDepartmentUuid());
            double limitDepartment = department.getLimitMoney() - entAccountDetailService.sumDepartmentThisMonth(params);

            if ((miniLimit == null) || (limitDepartment < miniLimit)) {
                miniLimit = limitDepartment;
            }
        }

        if (miniLimit < totalFee) {
            result.put("isShowEnt", 1);
            result.put("entAvailableBalance", NumberFormatUtil.roundAndFormatToInt(miniLimit));//可用余额
            result.put("limitIsEnough", false);
            result.put("entBalanceIsEnough", true);
            return AjaxList.createSuccess("获取成功", result);
        } else {
            result.put("isShowEnt", 1);
            result.put("entBalanceIsEnough", true);
            result.put("entAvailableBalance", NumberFormatUtil.roundAndFormatToInt(miniLimit));//可用余额
            result.put("limitIsEnough", true);
            return AjaxList.createSuccess("获取成功", result);

        }
    }


    /**
     * 预估费用，判断企业余额--无企业的返回
     *
     * @param result
     * @return
     */
    private AjaxList noEnt(Map<String, Object> result) {
        //不显示企业余额
        result.put("isShowEnt", 0);
        result.put("entAvailableBalance", 0);//可用余额
        result.put("entBalanceIsEnough", false);
        result.put("limitIsEnough", false);
        return AjaxList.createSuccess("获取成功", result);
    }


}
