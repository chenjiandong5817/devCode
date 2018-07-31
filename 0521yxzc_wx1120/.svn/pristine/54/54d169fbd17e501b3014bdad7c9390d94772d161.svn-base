package com.plugs.module_order.services;

import com.plugs.module_order.dtos.OrderDto;
import com.plugs.utils.DateUtils;
import com.plugs.utils.GuoDouSmsUtils;
import com.plugs.utils.RequestUtil;
import com.util.MD5Util;
import com.utils.log4j.QxLog;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/26.
 */
public class OrderVipHelper {
    private static final Logger qxLogger = Logger.getLogger(OrderVipHelper.class);

//    private static final String POST_URL = "http://yuanxiang.sjdzp.com/Api/Seller/api.json?g_cid=21452";
    //private static final String POST_URL = "http://demo.demo1.sjdzp.com/Api/Seller/api.json?g_cid=1";

    private static final String POST_URL = "http://yuanxiang.sjdzp.com/Api/Seller/api.json?g_cid=21452";
    private static final String CHARSET = "UTF-8";

    public static void sendVipCountMsgByOrder(OrderDto order,String fzVipPhones) {
        qxLogger.log(QxLog.LEVEL,"VIP短信");

        if(StringUtils.isNotBlank(order.getDestCity())){
            if(-1 != order.getDestCity().indexOf("厦门市")){
                qxLogger.log(QxLog.LEVEL, "[订单[" + order.getUuid() + "],调取接口创建vip订单接口");
                //调取接口
                Map<String, String> map = new HashMap<String, String>();
                map.put("method", "item_orders");
                map.put("_pid", "1142484");
                map.put("item_id", "1810758");
                map.put("is_pay", "1");
                map.put("orders_id", order.getOrderNumber());
                map.put("name", null != order.getActualPassengeName() ? order.getActualPassengeName() : "乘客" + StringUtils.substring(order.getActualPassengeMobile(), 7, 11));
                map.put("mobile", order.getActualPassengeMobile());
                map.put("size", Integer.toString(order.getVipCounts()));
                map.put("_sig", signMD5(map, "b69e77cf91392dc07b1b18b8cad92445"));
                RequestUtil.doPost(POST_URL, map, CHARSET);
            }else if (-1 != order.getDestCity().indexOf("福州市")){
                String str = "【元翔专车】订单："+order.getOrderNumber()+"，贵宾乘客"+order.getActualPassengeName()+"选择"+order.getVipCounts()+"人享受贵宾服务，" +
                        "于"+ DateUtils.format(order.getDeparTime())+"从"+order.getOriginDetailAddress()+"出发。请及时接待";
                if(StringUtils.isNotBlank(fzVipPhones)){
                    qxLogger.info("福州区工作人员接收服务短信手机号:" + fzVipPhones);
                    for (String phone : fzVipPhones.split(",")) {
                        try {
                            GuoDouSmsUtils.sendContentSmsUtf8(phone, str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (KeyManagementException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (NoSuchProviderException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    private static String signMD5(Map<String, String> map, String key) {
        String url = RequestUtil.buildUrl(map, false);
        String signUrl = key + "&" + url + "&" + key;
        return MD5Util.MD5Encode(signUrl);
    }
}
