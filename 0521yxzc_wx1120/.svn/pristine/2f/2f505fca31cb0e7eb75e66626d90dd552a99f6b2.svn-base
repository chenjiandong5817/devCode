package com.plugs.utils;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Zhouhy on 2016/9/19.
 */
public class CommonUtils {

    private static final Logger logger = Logger.getLogger(CommonUtils.class);

    /**
     * 生成验证码.
     *
     * @return 4位数字随机验证码
     */
    public static String getIdentifyCode() {

        Random random = new Random();

        int x = random.nextInt(8999);

        x = x + 1000;

        return String.valueOf(x);
    }

    /**
     * 生成序列号（前缀+时间戳+8位序列号）
     *
     * @param seqVal
     * @param prefix
     * @return
     */
    public static String generateSerialNumber(long seqVal, String prefix) {
        String seqStr = fillBlank(seqVal, 8);
        Random r = new Random();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return prefix + sf.format(new Date()) + r.nextInt(10) + seqStr;
    }


    /**
     * 生成普通序列号（前缀+uid+时间戳+随机数）
     *
     * @param uid
     * @param prefix
     * @return
     */
    public static String generateSerialNumber(String uid, String prefix) {
        Random r = new Random();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return prefix + uid + "_" + sf.format(new Date()) + "_" + r.nextInt(10);
    }


    /**
     * 生成订单序列号（年月日+6位序列号）
     *
     * @param seqVal
     * @return
     */
    public static String generateOrderSerial(long seqVal) {
        String seqStr = fillBlank(seqVal, 6);
        Random r = new Random();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return sf.format(new Date()) + seqStr;
    }

    /**
     * 不够长度，左边补0
     *
     * @param seqVal
     * @param length
     * @return
     */
    public static String fillBlank(long seqVal, int length) {
        String seqStr = String.valueOf(seqVal);
        StringBuilder sb = new StringBuilder();
        while (sb.length() + seqStr.length() < length) {
            sb.append("0");
        }
        sb.append(seqVal);
        return sb.toString();
    }

    /**
     * 根据请求获取IP
     *
     * @param request http请求
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String actualIp = ip.split(",")[0];

        return actualIp.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : actualIp;
    }

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXmlFromRequest(HttpServletRequest request)
            throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    public static Map<String, Object> bean2Map(Object bean) {
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        try {
            return propertyUtilsBean.describe(bean);
        } catch (Exception e) {
            logger.error("bean2Map err:" + e.getMessage());
            return null;
        }
    }
}
