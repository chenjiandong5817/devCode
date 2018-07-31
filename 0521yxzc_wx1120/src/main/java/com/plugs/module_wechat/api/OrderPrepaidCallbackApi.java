package com.plugs.module_wechat.api;

import com.plugs.base.BaseExtendApi;
import com.plugs.module_order.services.OrderPrepaidService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 预支付回调
 *
 * @outhor qfHan
 * @create 2017-12-11 11:20
 */
@Controller
@RequestMapping("/wechat/orderPrepaidPay")
@ApiIgnore
@Api(value = "乘客支付回调接口", description = "乘客支付回调接口")
public class OrderPrepaidCallbackApi extends BaseExtendApi {
    @Autowired
    private OrderPrepaidService orderPrepaidService;


    /**
     * 客户端微信帐号支付回调 微信付款
     * <p>
     * 公众账号ID appid 微信分配的公众账号ID（企业号corpid即为此appId） 商户号 mch_id 微信支付分配的商户号 设备号
     * device_info 微信支付分配的终端设备号， 随机字符串 nonce_str 随机字符串，不长于32位 签名 sign 签名，详见签名算法
     * 业务结果 result_code SUCCESS/FAIL 错误代码 err_code 错误返回的信息描述 错误代码描述 err_code_des
     * 错误返回的信息描述 用户标识 openid 用户在商户appid下的唯一标识 是否关注公众账号 is_subscribe
     * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效 交易类型 trade_type JSAPI、NATIVE、APP 付款银行
     * bank_type 银行类型，采用字符串类型的银行标识，银行类型见银行列表 总金额 total_fee 订单总金额，单位为分 货币种类
     * fee_type 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型 现金支付金额 cash_fee
     * 现金支付金额订单现金支付金额，详见支付金额 现金支付货币类型 cash_fee_type
     * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型 代金券或立减优惠金额 coupon_fee
     * 代金券或立减优惠金额<=订单总金额，订单总金额-代金券或立减优惠金额=现金支付金额，详见支付金额 代金券或立减优惠使用数量
     * coupon_count 代金券或立减优惠使用数量 代金券或立减优惠ID coupon_id_$n 代金券或立减优惠ID,$n为下标，从0开始编号
     * 单个代金券或立减优惠支付金额 coupon_fee_$n 单个代金券或立减优惠支付金额,$n为下标，从0开始编号 微信支付订单号
     * transaction_id 微信支付订单号 商户订单号 out_trade_no 商户系统的订单号，与请求一致。 商家数据包 attach
     * 商家数据包，原样返回 支付完成时间 time_end
     * 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     *
     * @return
     */
    @RequestMapping(value = "/tradeCallback", method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public String clientCallback(HttpServletRequest request) throws Exception {
        return orderPrepaidService.orderPrepaidTenPayCallBack(request);
    }
}
