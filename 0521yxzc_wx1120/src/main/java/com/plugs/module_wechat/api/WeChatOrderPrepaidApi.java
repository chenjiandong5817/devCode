package com.plugs.module_wechat.api;

import com.exception.ValidateException;
import com.plugs.base.AjaxList;
import com.plugs.base.BaseExtendApi;
import com.plugs.constants.ConfigConstants;
import com.plugs.module_order.dtos.OrderPrepaidDto;
import com.plugs.module_order.services.OrderPrepaidService;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.util.MD5Util;
import com.util.MapUtil;
import com.util.Validate;
import com.utils.log4j.QxLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 描述:
 * 预支付订单
 *
 * @outhor qfHan
 * @create 2017-12-08 14:39
 */
@Controller
@RequestMapping("/wechat/prepaid")
@Api(value = "预支付订单", description = "预支付订单相关接口")
public class WeChatOrderPrepaidApi extends BaseExtendApi {
    private static final Logger qxLogger = Logger.getLogger(WeChatOrderPrepaidApi.class);

    @Autowired
    private OrderPrepaidService orderPrepaidService;


    @RequestMapping(value = "/prepaidOrderList", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "预支付订单查询", notes = "预支付订单查询接口", httpMethod = "POST")
    public AjaxList tradeUrl(@ApiParam(name = "openId", required = true, value = "openId") @RequestParam String openId,
                             @ApiParam(name = "orderUuid", required = true, value = "订单Uuid") @RequestParam String orderUuid) throws Exception {

        Map<String, Object> params = MapUtil.buildMap();
        params.put("orderUuid", orderUuid);
        OrderPrepaidDto dto = orderPrepaidService.selInfo(params);
        if (dto.getEffectTime() != null) {
            dto.setTimeDifference(System.currentTimeMillis() - dto.getEffectTime().getTime());
        }
        return AjaxList.createSuccess("获取预支付订单", dto);
    }


    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单预支付", notes = "订单预支付接口", httpMethod = "POST")
    public AjaxList tradeUrl(@ApiParam(name = "openId", required = true, value = "openId") @RequestParam String openId,
                             @ApiParam(name = "money", required = true, value = "金额") @RequestParam String money,
                             @ApiParam(name = "orderUuid", required = true, value = "订单Uuid") @RequestParam String orderUuid,
                             @ApiParam(name = "userCouponUuid", value = "优惠券UUID[方便前端复用支付接口]") @RequestParam(required = false) String userCouponUuid,
                             @ApiParam(name = "type", required = true, value = "类型（2：账户余额，3：支付宝，4：微信）") @RequestParam Integer type,
                             @ApiParam(name = "sign", required = true, value = "签名[将参数按字母表顺序排序拼接成key=value&key2=value2,最后加上&key=secretKey，进行MD5加密，空参数不参与]") @RequestParam String sign,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        Validate.notEmpty(openId, "参数错误[登录令牌]");
        Validate.notNull(money, "参数错误[金额]");
        Validate.notNull(orderUuid, "参数错误[订单UUID]");
        Validate.notNull(type, "参数错误[类型]");
        Validate.notEmpty(sign, "参数错误[签名]");
        if (!type.equals(1) && !type.equals(2) && !type.equals(3) && !type.equals(4)) {
            throw new ValidateException("参数错误[类型]");
        }

        String signParams = "money=" + money + "&orderUuid=" + orderUuid + "&tokenId=" + openId + "&type=" + type + "&key=" + ConfigConstants.SECRET_KEY;
        qxLogger.log(QxLog.LEVEL, "签名参数[" + signParams + "]");
        signParams = MD5Util.MD5Encode(signParams);
        if (!sign.equals(signParams)) {
//            return AjaxList.createError("签名错误", ReturnCodeConstants.ERR_10014_SIGN_ERR);//签名错误
        }

        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);
        return orderPrepaidService.orderPrepaidPay(userPassengerDto, Double.parseDouble(money), orderUuid, type, request, response);

    }


    @RequestMapping(value = "/closeOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "预支付订单关闭", notes = "预支付订单关闭接口", httpMethod = "POST")
    public AjaxList closeOrder(@ApiParam(name = "openId", required = true, value = "openId") @RequestParam String openId,
                               @ApiParam(name = "orderUuid", required = true, value = "订单Uuid") @RequestParam String orderUuid) throws Exception {
        Validate.notEmpty(orderUuid, "参数错误[订单UUID]");

        return orderPrepaidService.closeOrder(orderUuid);
    }

}
