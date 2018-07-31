package com.plugs.utils.pay.alipay;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;

/**
 * 支付宝支付配置
 *
 * @author Zoro update
 */
public class AlipayConfig {
    //配置文件名
    private static final String CONF_FILE_NAME = "pay.properties";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALvCUE1mxapY6ww0" +
            "Gjsyajktj4hv6JKasqOumptx7QGbZwstcXbelsaiaTT702AIdcP7vPLalKJd7etb" +
            "PR6UxaEKI8/xE0ha8yw13veD3lfNX3mwU7vDixqtsNVYV2RwJeZeLdvZqOx6+WUi" +
            "V00R1xueFPFLiKxW/mYBqvMnUmBBAgMBAAECgYBkCC6ITkzZaO4xXkDrcPyu4dsq" +
            "sZSTJuAt3IR6dMP0qzrG9bsRvZ1OzpCGrfP7ogY8GA6exF2T878iOUXi2RhAvmi2" +
            "pTknU9+dDLPony5PJ2ZFKQCUBbbJwOMV9+iZFg8rUOBwDxhKxaBY1eyDFaxBJUSF" +
            "Hil22VPlcO6ZQ7tewQJBAN4PVfzWI+Ve9CR59r+RFeZ2FGgDdwBsIEsCFA6MtGo3" +
            "+1noCRwE+DpgDK/IzrqmSswFrJDfVaokjExWk4mSaa0CQQDYdN5E8x2eUptH2Ubl" +
            "RhWa2t/Y+TeMSYlFhmEhcpSVGr/vKKKtJ+KNxCVIm73+oFthyBo2wbPMiK5MKShv" +
            "HUtlAkBY0IFv0qYgw02amT1beUftGSUoRNp/zL9NwNMMzrIKGcHqP6gFEOfHUUtB" +
            "s2sbtA8OcuaGOw0kM+H5VldEGs8xAkApL6OTTwcVtvYg57Dbs0gtHPR23oS5Rsy9" +
            "0+HrcVfgPyTCJuM9O1eaTfYiKLAwIOcCZgbG4HiLtH4ls7cj3VkZAkAPu2o9CqD1" +
            "b47Ojq0pV+uEmFwJwLk9B9YMpIN7BAMU5EgYGKNc4GImsV4TauzzxSG916pKKNQU" +
            "rYHBtGR2gGWs";
    // 支付宝公钥
    public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";


    //配置常量
    public static String PARTNER = "";// 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取
    public static String SELLER = "";// 商户收款的支付宝账号
    public static String KEY = "";// 商户（MD5）KEY
    public static String REQ_URL = "";// 支付宝URL
    public static String CALLBACK_SERVER = "";// 回调接口API服务器

    //实例变量
    public String notifyUrl = "";// 回调通知具体地址
    public String goodsName = "";// 商品名称
    public String goodsInfo = "";// 商品详细说明


    static {
        InputStream fis = null;
        try {
            fis = AlipayConfig.class.getClassLoader().getResourceAsStream(CONF_FILE_NAME);
            PropertyResourceBundle props = new PropertyResourceBundle(fis);
            PARTNER = props.getString("alipay.partner");// 合作商户ID
            SELLER = props.getString("alipay.seller");// 商户收款的支付宝账号
            KEY = props.getString("alipay.key");// 商户（MD5）KEY
            REQ_URL = props.getString("alipay.req.url");// 支付宝URL
            CALLBACK_SERVER = props.getString("alipay.callback.server");// 回调接口API服务器
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fis)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 构造函数
     *
     * @param notifyUrl
     * @param goodsName
     * @param goodsInfo
     */
    public AlipayConfig(String notifyUrl, String goodsName, String goodsInfo) {
        this.notifyUrl = notifyUrl;
        this.goodsName = goodsName;
        this.goodsInfo = goodsInfo;
    }

}
