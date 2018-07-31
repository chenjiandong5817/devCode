package com.plugs.utils.pay.tenpay;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.plugs.utils.StringUtils;
import com.plugs.utils.pay.tenpay.util.CommonUtil;
import com.plugs.utils.pay.tenpay.util.TenpayConfig;
import com.plugs.utils.pay.tenpay.util.WXUtil;
import com.util.MD5Util;
import net.sf.json.JSONObject;

public class TenPay {
    private TenpayConfig tenpayConfig;

    public TenPay(TenpayConfig tenpayConfig) {
        this.tenpayConfig = tenpayConfig;
    }

    private JSONObject sendRequest(String orderId, Double actPaid, String spbillCreateIp,
                                   HttpServletRequest request, HttpServletResponse response, String openId) throws Exception {

        JSONObject jsonObject = new JSONObject();
        // 获取prepayid的请求类
        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(
                request, response, tenpayConfig);

        // 获取token
        AccessTokenRequestHandler accessTokenRequestHandler = new AccessTokenRequestHandler(null, null, tenpayConfig);
        String token = accessTokenRequestHandler.getAccessToken();

        if (!"".equals(token)) {
            // 生成随机字符串
            String noncestr = WXUtil.getNonceStr();

            // 设置获取prepayid支付参数
            // 公众账号ID
            prepayReqHandler.setParameter("appid", tenpayConfig.APP_ID);
            // 商户号
            prepayReqHandler.setParameter("mch_id", tenpayConfig.PARTNER);
            // 随机字符串
            prepayReqHandler.setParameter("nonce_str", noncestr);
            // 商品描述
            prepayReqHandler.setParameter("body", tenpayConfig.goodsName);
            // 商家订单号 = orderId + "_" + 4位随机数
            prepayReqHandler.setParameter("out_trade_no", orderId);
            // 商品金额,以分为单位
            int total_fee = (int) (actPaid * 100);
            prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
            // 终端IP
            prepayReqHandler.setParameter("spbill_create_ip", spbillCreateIp);
            // 通知地址
            prepayReqHandler.setParameter("notify_url", tenpayConfig.CALLBACK_SERVER + tenpayConfig.notifyUrl);
            // 交易类型
            String tradeType = openId == null ? "APP" : "JSAPI";
            prepayReqHandler.setParameter("trade_type", tradeType);
            // 用户标识
            if (openId != null) {
                prepayReqHandler.setParameter("openid", openId);
            }
            // 生成签名
            String sign = prepayReqHandler.createMD5Sign().toUpperCase();
            // 签名
            prepayReqHandler.setParameter("sign", sign);

            // 获取prepayId
            String prepayid = prepayReqHandler.sendPrepay();
            // 吐回给客户端的参数
            if (null != prepayid && !"".equals(prepayid)) {

                Map<String, String> returnWxMap = new HashMap<String, String>();
                returnWxMap.put("appId", tenpayConfig.APP_ID);     //公众号名称，由商户传入
                returnWxMap.put("timeStamp",String.valueOf(System.currentTimeMillis() / 1000));        //时间戳，自1970年以来的秒数
                returnWxMap.put("nonceStr", WXUtil.getNonceStr()); //随机串
                returnWxMap.put("package", "prepay_id=" + prepayid);
                returnWxMap.put("signType", "MD5");         //微信签名方式
                returnWxMap.put("paySign", this.signMD5(returnWxMap, tenpayConfig.APP_KEY));//微信签名


                jsonObject.put("appid", tenpayConfig.APP_ID);
                jsonObject.put("package", "prepay_id=" + prepayid);
                jsonObject.put("noncestr", returnWxMap.get("nonceStr"));
                jsonObject.put("partnerid", tenpayConfig.PARTNER);
                jsonObject.put("prepayid", prepayid);
                jsonObject.put("timestamp", returnWxMap.get("timeStamp"));

                // 签名
                jsonObject.put("sign", returnWxMap.get("paySign"));

                jsonObject.remove("package");
                jsonObject.put("pkg", "Sign=WXPay");
                jsonObject.put("retcode", "0");
                jsonObject.put("retmsg", "ok");
            } else {
                jsonObject.put("retcode", "-2");
                jsonObject.put("retmsg", "错误：获取prepayId失败");
            }
        } else {
            jsonObject.put("retcode", "-1");
            jsonObject.put("retmsg", "错误：获取不到Token");
        }

        return jsonObject;
    }

    /**APP*/
    public JSONObject payRequest(String orderId, Double actPaid, String spbillCreateIp,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        return this.sendRequest(orderId, actPaid, spbillCreateIp, request, response, null);
    }


    /**js**/
    public JSONObject yxjsPayRequest(String openId, String orderId, Double actPaid, String spbillCreateIp,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {

        return this.sendRequest(orderId, actPaid, spbillCreateIp, request, response, openId);
    }


    private String signMD5(Map<String, String> map, String key){
        String url = this.buildUrl(map, false);
        return MD5Util.MD5Encode(url + "&key=" + key).toUpperCase();
    }

    private String buildUrl(Map<String, String> map, boolean needWrapValue) {
        StringBuilder sb = new StringBuilder();
        List<String> keyList = new ArrayList<String>(map.keySet());
        Collections.sort(keyList);
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            String value = map.get(key);
            if (needWrapValue) {
                sb.append(i == 0 ? "" : "&").append(key).append("=\"").append(value).append("\"");
            } else {
                if (StringUtils.isNotEmpty(value)) sb.append(i == 0 ? "" : "&").append(key).append("=").append(value);
            }
        }
        return sb.toString();
    }

}
