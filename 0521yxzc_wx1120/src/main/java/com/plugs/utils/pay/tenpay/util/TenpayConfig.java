package com.plugs.utils.pay.tenpay.util;


import com.plugs.utils.pay.alipay.AlipayConfig;

import java.io.InputStream;
import java.util.PropertyResourceBundle;


public class TenpayConfig {

    public enum Type {
        PASSENGER, DRIVER, WXUSER
    }

    // package常量值
    public static String packageValue = "bank_type=WX&body=%B2%E2%CA%D4&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F127.0.0.1%3A8180%2Ftenpay_api_b2c%2FpayNotifyUrl.jsp&out_trade_no=2051571832&partner=1900000109&sign=10DA99BCB3F63EF23E4981B331B0A3EF&spbill_create_ip=127.0.0.1&time_expire=20131222091010&total_fee=1";
    public static String traceid = "testtraceid001";// 测试用户id
    private static String CONF_FILE_NAME = "pay.properties";

    public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";// 获取access_token对应的url
    public static String GRANT_TYPE = "client_credential";// 常量固定值
    public static String EXPIRE_ERRCODE = "42001";// access_token失效后请求返回的errcode
    public static String FAIL_ERRCODE = "40001";// 重复获取导致上一次获取的access_token失效,返回错误码
    public static String GATEURL = "https://api.weixin.qq.com/pay/genprepay?access_token=";// 获取预支付id的接口url
    public static String ACCESS_TOKEN = "access_token";// access_token常量值
    public static String ERRORCODE = "errcode";// 用来判断access_token是否失效的值
    public static String SIGN_METHOD = "sha1";// 签名算法常量值
    public static String GATE_URL = "https://gw.tenpay.com/gateway/verifynotifyid.xml";

    // 初始化
    // 应用对应的密钥
    public String PARTNER = "";// 财付通商户号----
    public String PARTNER_KEY = "";// 商户号对应的密钥
    public String APP_ID = "";// 微信开发平台应用id----
    public String APP_SECRET = "";// 应用对应的凭证
    public String APP_KEY = "";
    public String CALLBACK_SERVER = "";// 回调服务器


    //实例变量
    public String goodsName = "";// 商品名称
    public String goodInfo = "";// 商品详细说明
    public String notifyUrl = "";// 回调通知服务地址


    /**
     * 构造函数
     *
     * @param notifyUrl
     * @param goodsName
     * @param goodInfo
     */
    public TenpayConfig(String notifyUrl, String goodsName, String goodInfo, Type type) {
        this.notifyUrl = notifyUrl;
        this.goodsName = goodsName;
        this.goodInfo = goodInfo;

        try {
            InputStream fis = AlipayConfig.class.getClassLoader().getResourceAsStream(CONF_FILE_NAME);
            PropertyResourceBundle props = new PropertyResourceBundle(fis);
            if (type == Type.PASSENGER) {
                PARTNER = props.getString("wxpay.passenger.partner");// 合作商户ID
                PARTNER_KEY = props.getString("wxpay.passenger.partnerkey");
                APP_ID = props.getString("wxpay.passenger.appid");
                APP_SECRET = props.getString("wxpay.passenger.appsecret");
                APP_KEY = props.getString("wxpay.passenger.appkey");
                CALLBACK_SERVER = props.getString("wxpay.callback.server");
            } else if (type == Type.DRIVER) {
                PARTNER = props.getString("wxpay.driver.partner");// 合作商户ID
                PARTNER_KEY = props.getString("wxpay.driver.partnerkey");
                APP_ID = props.getString("wxpay.driver.appid");
                APP_SECRET = props.getString("wxpay.driver.appsecret");
                APP_KEY = props.getString("wxpay.driver.appkey");
                CALLBACK_SERVER = props.getString("wxpay.callback.server");
            } else if (type == Type.WXUSER){
                PARTNER = props.getString("wxpay.wxuser.partner");// 合作商户ID
                PARTNER_KEY = props.getString("wxpay.wxuser.partnerkey");
                APP_ID = props.getString("wxpay.wxuser.appid");
                APP_SECRET = props.getString("wxpay.wxuser.appsecret");
                APP_KEY = props.getString("wxpay.wxuser.appkey");
                CALLBACK_SERVER = props.getString("wxpay.wxuser.callback");
            }else {
                throw new IllegalArgumentException("init tenpay config err");
            }
            fis.close();
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException("init tenpay config err");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAPP_KEY() {
        return APP_KEY;
    }
}
