package com.plugs.utils.pay.tenpay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.plugs.utils.Sha1Util;
import com.plugs.utils.pay.tenpay.client.TenpayHttpClient;
import com.plugs.utils.pay.tenpay.util.CommonUtil;
import com.plugs.utils.pay.tenpay.util.TenpayConfig;
import com.plugs.utils.pay.tenpay.util.XMLUtil;
import com.util.MD5Util;
import net.sf.json.JSONException;

import static jxl.biff.FormatRecord.logger;


public class PrepayIdRequestHandler extends RequestHandler {

    private TenpayConfig tenpayConfig;


    public PrepayIdRequestHandler(HttpServletRequest request,
                                  HttpServletResponse response, TenpayConfig tenpayConfig) {
        super(request, response);
        this.tenpayConfig = tenpayConfig;
    }

    /**
     * 创建签名SHA1
     *
     * @return
     * @throws Exception
     */
    public String createSHA1Sign() {
        StringBuffer sb = new StringBuffer();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        String appsign = Sha1Util.getSha1(params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "sha1 sb:" + params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
        return appsign;
    }

    /**
     * 创建签名MD5
     *
     * @return
     * @throws Exception
     */
    public String createMD5Sign() {
        StringBuffer sb = new StringBuffer();

        HashMap<String, String> paraMap = new HashMap<String, String>();

        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
            paraMap.put(k, v);
        }

        String params = CommonUtil.FormatBizQueryParaMap(paraMap, false);

        // key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
        params = params + "&key=" + tenpayConfig.APP_KEY;

        String appsign = MD5Util.MD5Encode(params);

        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "sha1 sb:" + params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
        return appsign;

    }

    // 提交预支付
    public String sendPrepay() throws JSONException {
        String prepayid = "";

        // StringBuffer sb = new StringBuffer("{");
        // Set es = super.getAllParameters().entrySet();
        // Iterator it = es.iterator();
        // while (it.hasNext()) {
        // Map.Entry entry = (Map.Entry) it.next();
        // String k = (String) entry.getKey();
        // String v = (String) entry.getValue();
        // if (null != v && !"".equals(v) && !"appkey".equals(k)) {
        // sb.append("\"" + k + "\":\"" + v + "\",");
        // }
        // }
        // String params = sb.substring(0, sb.lastIndexOf(","));
        // params += "}";

        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");

        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("<" + k + ">");
                sb.append(v);
                sb.append("</" + k + ">");
            }
        }
        sb.append("</xml>");
        logger.info("请求数据："+sb.toString());

        String requestUrl = super.getGateUrl();

        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(requestUrl);
        String resContent = "";

        if (httpClient.callHttpPost(requestUrl, sb.toString())) {
            resContent = httpClient.getResContent();
            logger.info("支付返回数据："+resContent);
            Map<String, String> resContentMap = null;
            try {
                resContentMap = XMLUtil.doXMLParse(resContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if ("SUCCESS".equals(resContentMap.get("return_code"))) {
                prepayid = resContentMap.get("prepay_id");
            }
            this.setDebugInfo(this.getDebugInfo() + "\r\n" + "resContent:"
                    + resContent);
        }
        return prepayid;
    }

    // 判断access_token是否失效
    public String sendAccessToken() {
        String accesstoken = "";
        StringBuffer sb = new StringBuffer("{");
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("\"" + k + "\":\"" + v + "\",");
            }
        }
        String params = sb.substring(0, sb.lastIndexOf(","));
        params += "}";

        String requestUrl = super.getGateUrl();
        // this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:"
        // + requestUrl);
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(requestUrl);
        String resContent = "";
        // this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" +
        // params);
        if (httpClient.callHttpPost(requestUrl, params)) {
            resContent = httpClient.getResContent();
            if (2 == resContent.indexOf(TenpayConfig.ERRORCODE)) {
                accesstoken = resContent.substring(11, 16);// 获取对应的errcode的值
            }
        }
        return accesstoken;
    }
}
