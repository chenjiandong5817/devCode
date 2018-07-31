package com.plugs.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zoro on 2016/10/17.
 */
public class RushOrderHelper {

    private static Map<String, Boolean> orderRushThreadExist = new HashMap<String, Boolean>();

    public static boolean threadExist(String orderUuid) {
        return orderRushThreadExist.get(orderUuid) == null ? false : orderRushThreadExist.get(orderUuid);
    }

    public static void put(String orderUuid) {
        orderRushThreadExist.put(orderUuid, true);
    }

    public static void remove(String orderUuid) {
        orderRushThreadExist.remove(orderUuid);
    }
}
