package com.plugs.utils.pay.tenpay;


import com.plugs.utils.pay.tenpay.client.TenpayHttpClient;
import com.plugs.utils.pay.tenpay.util.TenpayConfig;
import com.plugs.utils.pay.tenpay.util.JsonUtil;
import com.plugs.utils.pay.tenpay.util.WXUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AccessTokenRequestHandler extends RequestHandler {

    private TenpayConfig tenpayConfig;

    public AccessTokenRequestHandler(HttpServletRequest request,
                                     HttpServletResponse response, TenpayConfig tenpayConfig) {
        super(request, response);
        this.tenpayConfig = tenpayConfig;
    }

    private static String access_token = "";

    /**
     * 获取凭证access_token
     *
     * @return
     */
    public String getAccessToken() {
        if ("".equals(access_token)) {// 如果为空直接获取
            return getTokenReal();
        }

        if (tokenIsExpire(access_token)) {// 如果过期重新获取
            return getTokenReal();
        }
        return access_token;
    }

    /**
     * 实际获取access_token的方法
     *
     * @return
     */
    protected String getTokenReal() {
        String requestUrl = TenpayConfig.TOKENURL + "?grant_type="
                + TenpayConfig.GRANT_TYPE + "&appid=" + tenpayConfig.APP_ID
                + "&secret=" + tenpayConfig.APP_SECRET;
        String resContent = "";
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setMethod("GET");
        httpClient.setReqContent(requestUrl);
        if (httpClient.call()) {
            resContent = httpClient.getResContent();
            if (resContent.indexOf(TenpayConfig.ACCESS_TOKEN) > 0) {
                access_token = JsonUtil.getJsonValue(resContent,
                        TenpayConfig.ACCESS_TOKEN);
            } else {
                System.out.println("获取access_token值返回错误！！！");
            }
        } else {
            System.out.println("后台调用通信失败");
            System.out.println(httpClient.getResponseCode());
            System.out.println(httpClient.getErrInfo());
            // 有可能因为网络原因，请求已经处理，但未收到应答。
        }

        return access_token;
    }

    /**
     * 判断传递过来的参数access_token是否过期
     *
     * @param access_token
     * @return
     */
    private boolean tokenIsExpire(String access_token) {
        boolean flag = false;
        PrepayIdRequestHandler wxReqHandler = new PrepayIdRequestHandler(null,
                null, tenpayConfig);
        wxReqHandler.setParameter("appid", tenpayConfig.APP_ID);
        wxReqHandler.setParameter("appkey", tenpayConfig.APP_KEY);
        wxReqHandler.setParameter("noncestr", WXUtil.getNonceStr());
        wxReqHandler.setParameter("package", TenpayConfig.packageValue);
        wxReqHandler.setParameter("timestamp", WXUtil.getTimeStamp());
        wxReqHandler.setParameter("traceid", TenpayConfig.traceid);

        // 生成支付签名
        String sign = wxReqHandler.createSHA1Sign();
        wxReqHandler.setParameter("app_signature", sign);
        wxReqHandler.setParameter("sign_method", TenpayConfig.SIGN_METHOD);
        String gateUrl = TenpayConfig.GATEURL + access_token;
        wxReqHandler.setGateUrl(gateUrl);

        // 发送请求
        String accesstoken = wxReqHandler.sendAccessToken();
        if (TenpayConfig.EXPIRE_ERRCODE.equals(accesstoken)
                || TenpayConfig.FAIL_ERRCODE.equals(accesstoken))
            flag = true;
        return flag;
    }

}
