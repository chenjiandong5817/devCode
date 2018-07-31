package com.plugs.utils.pay.tenpay.util;

import com.util.MD5Util;

import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

public class CommonUtil {

    public static String CreateNoncestr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            res += chars.indexOf(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * 生成包含大小写字母和数字的16位随机数
     *
     * @return String
     */
    public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    public static String FormatQueryParaMap(HashMap<String, String> parameters) {

        String buff = "";
        try {
            List<Entry<String, String>> infoIds = new ArrayList<Entry<String, String>>(
                    parameters.entrySet());

            Collections.sort(infoIds,
                    new Comparator<Entry<String, String>>() {
                        public int compare(Entry<String, String> o1,
                                           Entry<String, String> o2) {
                            return (o1.getKey()).toString().compareTo(
                                    o2.getKey());
                        }
                    });

            for (int i = 0; i < infoIds.size(); i++) {
                Entry<String, String> item = infoIds.get(i);
                if (item.getKey() != "") {
                    buff += item.getKey() + "="
                            + URLEncoder.encode(item.getValue(), "utf-8") + "&";
                }
            }
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buff;
    }

    /**
     * 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）后，使用 URL 键值 对的格式（即
     * key1=value1&key2=value2…）拼接成字符串 string1
     *
     * @param paraMap
     * @param urlencode
     * @return
     * @throws SDKRuntimeException
     */
    public static String FormatBizQueryParaMap(HashMap<String, String> paraMap,
                                               boolean urlencode) {

        String buff = "";
        try {
            List<Entry<String, String>> infoIds = new ArrayList<Entry<String, String>>(
                    paraMap.entrySet());

            Collections.sort(infoIds,
                    new Comparator<Entry<String, String>>() {
                        public int compare(Entry<String, String> o1,
                                           Entry<String, String> o2) {
                            return (o1.getKey()).toString().compareTo(
                                    o2.getKey());
                        }
                    });

            for (int i = 0; i < infoIds.size(); i++) {
                Entry<String, String> item = infoIds.get(i);
                // System.out.println(item.getKey());
                if (item.getKey() != "") {

                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlencode) {
                        val = URLEncoder.encode(val, "utf-8");// urlencode 转码

                    }
                    buff += key.toLowerCase() + "=" + val + "&";

                }
            }

            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buff;
    }

    public static boolean IsNumeric(String str) {
        if (str.matches("\\d *")) {
            return true;
        } else {
            return false;
        }
    }

    public static String ArrayToXml(HashMap<String, String> arr) {
        String xml = "<xml>";

        Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            String key = entry.getKey();
            String val = entry.getValue();
            if (IsNumeric(val)) {
                xml += "<" + key + ">" + val + "</" + key + ">";

            } else
                xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
        }

        xml += "</xml>";
        return xml;
    }

    public static boolean validSign(Map<String, String> requestMap, String appKey) {
        // sign
        String sign = requestMap.get("sign");
        // 验签
        HashMap<String, String> paraMap = new HashMap<String, String>();

        Set es = requestMap.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                paraMap.put(k, v);
            }
        }

        String params = CommonUtil.FormatBizQueryParaMap(paraMap, false);

        // key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
        params = params + "&key=" + appKey;

        String clientSign = MD5Util.MD5Encode(params);

        return clientSign.equals(sign);
    }

}
