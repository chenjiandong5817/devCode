package com.plugs.utils;

import com.plugs.module_order.services.OrderVipHelper;
import com.utils.log4j.QxLog;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Administrator on 2017/6/26.
 */
public class RequestUtil {

    private static final Logger qxLogger = Logger.getLogger(OrderVipHelper.class);

    public static String doPost(String url, Map<String, String> params, String charset) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        // 设置Http Post数据
        method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=" + charset);
        if(params != null){
            Set<String> keySet = params.keySet();
            NameValuePair[] param = new NameValuePair[keySet.size()];
            int i = 0;
            for(String key : keySet){
                param[i] = new NameValuePair(key, params.get(key));
                i++;
            }
            method.setRequestBody(param);
        }
        InputStream responseBodyStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                responseBodyStream = method.getResponseBodyAsStream();
                streamReader = new InputStreamReader(responseBodyStream, charset);
                reader = new BufferedReader(streamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (IOException e) {
            qxLogger.log(QxLog.LEVEL, "执行HTTP Post请求" + url + "时，发生异常！" + e);
        } finally {
            try {
                responseBodyStream.close();
                streamReader.close();
                reader.close();
            } catch (IOException e) {
                qxLogger.log(QxLog.LEVEL, "执行HTTP Post请求" + url + "时，发生异常，关闭流异常！", e);
                e.printStackTrace();
            }
            method.releaseConnection();
        }
        return response.toString();
    }


    public static String buildUrl(Map<String, String> map, boolean needWrapValue) {
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
