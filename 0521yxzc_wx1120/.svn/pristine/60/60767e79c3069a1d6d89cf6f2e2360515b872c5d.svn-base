package com.plugs.utils.pay.alipay;


import com.plugs.utils.RSASignature;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 调用支付宝的开放平台创建、支付交易步骤
 * <p>
 * 1.将业务参数：外部交易号、商品名称、商品总价、卖家帐户、卖家帐户、notify_url这些东西按照xml
 * 的格式放入<req_data></req_data>中 2.将通用参数也放入请求参数中 3.对以上的参数进行签名，签名结果也放入请求参数中
 * 4.请求支付宝开放平台的alipay.wap.trade.create.direct服务
 * 5.从开放平台返回的内容中取出request_token（对返回的内容要先用私钥解密，再用支付宝的公钥验签名）
 * 6.使用拿到的request_token组装alipay.wap.auth.authAndExecute服务的跳转url
 * 7.根据组装出来的url跳转到支付宝的开放平台页面，交易创建和支付在支付宝的页面上完成
 */
public class AlipayTrade {

    private AlipayConfig alipayConfig;

    public AlipayTrade(AlipayConfig alipayConfig) {
        this.alipayConfig = alipayConfig;
    }


    /**
     * 获取交易信息
     *
     * @param orderNumber
     * @param actPaid
     * @return
     */
    public String generateTradeUrl(String orderNumber, Double actPaid) {
        // 订单
        String tradeUrl = generateBaseTradeUrl(orderNumber, actPaid);// 付款
        // 对订单做RSA签名
        String sign = RSASignature.sign(tradeUrl, AlipayConfig.RSA_PRIVATE_KEY);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        return tradeUrl + "&sign=\"" + sign + "\"&sign_type=\"RSA\"";
    }


    /**
     * 创建基础订单信息
     *
     * @param orderNumber
     * @param actPaid
     * @return
     */
    private String generateBaseTradeUrl(String orderNumber, Double actPaid) {
        StringBuffer tradeUrl = new StringBuffer();//组装参数

        tradeUrl.append("partner=\"" + AlipayConfig.PARTNER + "\"");// 签约合作者身份ID
        tradeUrl.append("&seller_id=\"" + AlipayConfig.SELLER + "\"");// 签约卖家支付宝账号
        tradeUrl.append("&out_trade_no=\"" + orderNumber + "\"");// 商户网站唯一订单号
        tradeUrl.append("&subject=\"" + alipayConfig.goodsName + "\"");// 商品名称
        tradeUrl.append("&body=\"" + alipayConfig.goodsInfo + "\"");// 商品详情
        tradeUrl.append("&total_fee=\"" + actPaid.toString() + "\"");// 商品金额
        tradeUrl.append("&notify_url=\"" + AlipayConfig.CALLBACK_SERVER + alipayConfig.notifyUrl + "\"");// 服务器异步通知页面路径
        tradeUrl.append("&service=\"mobile.securitypay.pay\"");// 服务接口名称， 固定值
        tradeUrl.append("&payment_type=\"1\"");// 支付类型， 固定值
        tradeUrl.append("&_input_charset=\"utf-8\"");// 参数编码， 固定值
        // 设置未付款交易的超时时间，默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。 m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        tradeUrl.append("&it_b_pay=\"30m\"");
        //tradeUrl.append("&extern_token=\"\+extern_token+"");//extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        tradeUrl.append("&show_url=\"m.alipay.com\"");// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        //tradeUrl.append("&paymethod=\"expressGateway\"");//调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        return tradeUrl.toString();
    }
}
