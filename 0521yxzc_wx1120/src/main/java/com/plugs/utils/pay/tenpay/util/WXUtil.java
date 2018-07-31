package com.plugs.utils.pay.tenpay.util;



import com.util.MD5Util;

import java.util.Random;


public class WXUtil {

    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)));
    }

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
