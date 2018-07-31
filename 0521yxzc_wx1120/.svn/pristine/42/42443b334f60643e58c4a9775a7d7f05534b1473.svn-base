package com.plugs.module_wechat.util;

import com.util.PropertiesUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Zhouhy on 2016/7/29.
 */
public class WeChatUtil {
    private static Logger logger = LoggerFactory.getLogger(WeChatUtil.class);
    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /***
     * 获取用户访问权限
     */
    private static String user_access_token_url = " https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
    public static JSONObject getUserAccessToken(String code){
        // 第三方用户唯一凭证
        String appId = PropertiesUtil.getConfigInfo("weChatAppId");
        // 第三方用户唯一凭证密钥
        String appSecret = PropertiesUtil.getConfigInfo("weChatAppSecret");
        String requestUrl = user_access_token_url.replace("APPID", appId).replace("APPSECRET", appSecret).replace("CODE",code);
        JSONObject jsonObject = WeChatUtil.sendHttpRequest(requestUrl, "GET", null);
        return jsonObject;
    }
    /***
     *
     * @param urlStr
     * @param requestType GET POST
     * @param params
     * @return
     */
    public static JSONObject sendHttpRequest(String urlStr, String requestType, String params){
        JSONObject jsonObject = new JSONObject();
        StringBuffer buffer = null;

        try {
            // 建立连接
            URL url = new URL(urlStr);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestType);
            if ("GET".equalsIgnoreCase(requestType))
                httpUrlConn.connect();
            // 当有数据需要提交时
            if (null != params) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(params.getBytes("UTF-8"));
                outputStream.close();
            }
            // 获取输入流
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // 读取返回结果
            buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpUrlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject = JSONObject.fromObject(buffer.toString());
        return jsonObject;
    }
    /**
     * 获取access_token
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;

        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = WeChatUtil.sendHttpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }
}
