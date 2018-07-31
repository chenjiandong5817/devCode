package com.plugs.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhouhy on 2016/9/20.
 */
public class ConfigConstants {
    //安全性较高的api接口秘钥
    public static final String SECRET_KEY = "ARLNOINDFPORT15101SNllsdKNONFjlsdfjl";
    //短信加密KEY
    public static final String SMS_KEY = "ARPORT912012KEY";
    //后台用户
    public static final String ADMIN_SESSION_KEY = "ADMIN_SESSION_USER";
    //乘客
    public static final String SESSION_KEY_PASSENGER_USER = "SESSION_PASSENGER_USER";
    //微信乘客
    public static final String SESSION_KEY_PASSENGER_USER_WX = "SESSION_PASSENGER_USER_WX";

    public static final String SESSION_KEY_DRIVER_MAP_SESSION_WX = "WX_DRIVER_MAP_SESSION";

    public static final String SESSION_KEY_ORDER_MAP_SESSION_WX = "WX_ORDER_MAP_SESSION";

    //司机
    public static final String SESSION_KEY_DRIVER_USER = "SESSION_DRIVER_USER";
    //官网用户SESSION KEY
    public static final String ENT_SESSION_KEY = "ENT_SESSION_USER";

    //官网用户SESSION KEY
    public static final String ADMIN_AUTHORITY_KEY = "ADMIN_AR";

    //账户明细类型
    public static final int ACCOUNT_DETAIL_TYPE_RECHARGE = 1;
    public static final int ACCOUNT_DETAIL_TYPE_3RD_SPEND = 2;
    public static final int ACCOUNT_DETAIL_TYPE_BALANCE_SPEND = 3;
    //充值类型
    public static final int PAY_TYPE_ALIPAY = 1;
    public static final int PAY_TYPE_TENPAY = 2;
    public static final int PAY_TYPE_KQPAY = 3;
    //支付状态
    public static final int PAYD_SUCCESS = 1;
    public static final int PAYD_NOT = 0;
    //账户明细状态
    public static final int ACCOUNT_DETAIL_STATUS_FAIL = -1;
    public static final int ACCOUNT_DETAIL_STATUS_SUCCESS = 1;
    public static final int ACCOUNT_DETAIL_STATUS_ING = 2;
    //
    public static final int PAY_STATUS_NO = 0;
    public static final int PAY_STATUS_OK = 1;

    //默认10条记录数
    public static final int PAGESIZE_10 = 10;

    //附近司机范围限定，单位千米
    public static final double NEARBY_DRIVER_RANGE = 0.8;

    //回调地址
    public static final String PASSENGER_ORDER_JS_TEN_PAY_NOTIFY_URL = "/wechat/wxpay/tradeCallback";
    public static final String PASSENGER_ORDER_JS_TEN_PREPAID_PAY_NOTIFY_URL = "/wechat/orderPrepaidPay/tradeCallback";


    public static final String PASSENGER_RECHARGE_GOOD_NAME = "账户充值";
    public static final String PASSENGER_RECHARGE_GOOD_INFO = "在线账户金额充值";

    public static final String PASSENGER_PAY_GOOD_NAME = "支付车费";
    public static final String PASSENGER_PAY_GOOD_INFO = "在线支付车费";


    public static final String DRIVER_PREPAID_PAY_GOOD_NAME = "预支付车费";
    public static final String DRIVER_PREPAID_PAY_GOOD_INFO = "在线预支付车费";

    //推送相关
    public static final boolean IS_APNS_PRODUCTION = true;//是否生产环境



    /**
     * OS 1.android，2.IOS
     **/
    public enum OS {
        ANDROID,
        IOS
    }

    public static final String PUSH_TAG_PASSENGER = "passenger";//乘客标签
    public static final String PUSH_TAG_DRIVER = "driver";//司机标签

    public static final String EXCLUDE_MOLILE_PREFIX = "142";//测试号码段

    public static final int PASSENGER_ONGOING_ORDER_LIMIT = 5;//一个乘客最多只有有几个未完成订单

    public static final String UPLOAD_FOLDER_ORDER_TRIP = "orderTrip";

    public static final int ORDER_DISTANCE_LIMIT_MAX = 800;//八百公里（叫车起终点距离限制）
    public static final double ORDER_DISTANCE_LIMIT_MIN = 2;//2公里（叫车起终点距离限制）

    public static final boolean NUM_142_DISABLED = false;//是否禁用142号码段

    public static final String REG_PASS_DEVICE =
            "003000f2010ca726b84c,"+//测试设备
            "000000f30108a8d2c4d7," +
                    "003000f2010ca7269eec," +
                    "000000f30108a8d2c5a3," +
                    "003000f2010ca738779b," +
                    "701000f10014a68cf0c3," +
                    "003000f30108a8d2c64a," +
                    "003000f30108a8d2c525," +
                    "003000f2010ca72678cb";//注册设备白名单

    /**
     * 获取车型列表
     * @return
     */
    public static List<Map<String, Object>> getCarModels(){
        return getCarModelsFun(1, 1, -1);
    }

    /**
     * 获取车型列表
     * @return
     */
    public static List<Map<String, Object>> getEntCarModels(){
        return getCarModelsFun(1, 1, 2);
    }


    /**
     * 获取车型列表
     * @return
     */
    public static List<Map<String, Object>> getCarModelsFun(int status1, int status2, int status3){
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        Map<String, Object> model1 = new HashMap<String, Object>();
        model1.put("modelNum", 1);
        model1.put("picNum", 3);
        model1.put("modelName", "时尚型");
        model1.put("terminalPic", "http://59.60.12.62:8765/pub/images/car/car_type_1.png");
        model1.put("status", status1);

        Map<String, Object> model2 = new HashMap<String, Object>();
        model2.put("modelNum", 2);
        model2.put("picNum", 2);
        model2.put("modelName", "经济七座");
        model2.put("terminalPic", "http://59.60.12.62:8765/pub/images/car/car_type_2.png");
        model2.put("status", status2);

        Map<String, Object> model3 = new HashMap<String, Object>();

        model3.put("modelNum", 3);
        model3.put("picNum", 3);
        model3.put("terminalPic", "http://59.60.12.62:8765/pub/images/car/car_type_3.png");
        model3.put("modelName", "豪华商务");
        model3.put("status", status3);


        result.add(model1);
        result.add(model2);
        result.add(model3);

        return result;
    }


    /**
     * 微信叫车获取起终点地址
     * @param areaUuid
     * @return
     */
    public static List<Map<String, Object>> getLocations(String areaUuid) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        String fz_areaUuid="cb33bbcc0e8c400f992c4d1acff274cb";//福州全区域UUID
        if (fz_areaUuid.equals(areaUuid)) {//默认为厦门全区域ID

            Map<String, Object> t3 = new HashMap<String, Object>();
            t3.put("uuid", "e577130fcc1c0df03b1a75d561576967");
            t3.put("lat", 25.929030);
            t3.put("lng", 119.672900);
            t3.put("city", "福建省福州市");
            t3.put("address", "福州长乐机场");
            t3.put("status", 1);
            t3.put("startingEnd", 3);

            result.add(t3);
        } else {
            Map<String, Object> t9 = new HashMap<String, Object>();
            t9.put("uuid", "e577130fcc1c0df03b1a75d561036992");
            t9.put("lat", 24.4549433550);
            t9.put("lng", 118.0739736557);
            t9.put("city", "福建省厦门市");
            t9.put("address", "轮渡");
            t9.put("status", 1);
            t9.put("startingEnd", 1);

            Map<String, Object> t8 = new HashMap<String, Object>();
            t8.put("uuid", "e577130fcc1c0df03b1a75d5612e6992");
            t8.put("lat", 24.4823104959);
            t8.put("lng", 118.0752557516);
            t8.put("city", "福建省厦门市");
            t8.put("address", "东渡邮轮码头");
            t8.put("status", 1);
            t8.put("startingEnd", 1);

            Map<String, Object> t7 = new HashMap<String, Object>();
            t7.put("uuid", "e577130fcc1c0df03b1a75d5612e6992");
            t7.put("lat", 24.4918900000);
            t7.put("lng", 118.111920);
            t7.put("city", "福建省厦门市");
            t7.put("address", "威斯汀酒店");
            t7.put("status", 1);
            t7.put("startingEnd", 1);

            Map<String, Object> t6 = new HashMap<String, Object>();
            t6.put("uuid", "e577130fcc1c0df03b1a75d5612e6991");
            t6.put("lat", 24.435230);
            t6.put("lng", 118.089250);
            t6.put("city", "福建省厦门市");
            t6.put("address", "康莱德酒店");
            t6.put("status", 1);
            t6.put("startingEnd", 1);

            Map<String, Object> t5 = new HashMap<String, Object>();
            t5.put("uuid", "e577130fcc1c0df03b1a75d5612e6999");
            t5.put("lat", 24.490580);
            t5.put("lng", 118.176120);
            t5.put("city", "福建省厦门市");
            t5.put("address", "佰翔软件园酒店");
            t5.put("status", 1);
            t5.put("startingEnd", 1);

            Map<String, Object> t4 = new HashMap<String, Object>();
            t4.put("uuid", "e577130fcc1c0df03b1a75d5612e6955");
            t4.put("lat", 24.544104);
            t4.put("lng", 118.147831);
            t4.put("city", "福建省厦门市");
            t4.put("address", "厦门高崎机场T4航站楼");
            t4.put("status", 1);
            t4.put("startingEnd", 3);//起始地（1：起点，2：终点，3：是起点也是终点）

            Map<String, Object> t3 = new HashMap<String, Object>();
            t3.put("uuid", "e577130fcc1c0df03b1a75d5612e6966");
            t3.put("lat", 24.534085);
            t3.put("lng", 118.131887);
            t3.put("city", "福建省厦门市");
            t3.put("address", "厦门高崎机场T3航站楼");
            t3.put("status", 1);
            t3.put("startingEnd", 3);

            Map<String, Object> t2 = new HashMap<String, Object>();
            t2.put("uuid", "e577130fcc1c0df03b1a75d5612e6977");
            t2.put("lat", 24.52748);
            t2.put("lng", 118.194100);
            t2.put("city", "福建省厦门市");
            t2.put("address", "佰翔五通酒店（码头）");
            t2.put("status", 1);
            t2.put("startingEnd", 1);

            Map<String, Object> t1 = new HashMap<String, Object>();
            t1.put("uuid", "e577130fcc1c0df03b1a75d5612e6988");
            t1.put("lat", 24.491110);
            t1.put("lng", 118.195430);
            t1.put("city", "福建省厦门市");
            t1.put("address", "香格里拉酒店");
            t1.put("status", 1);
            t1.put("startingEnd", 1);

            result.add(t4);
            result.add(t3);
            result.add(t8);
            result.add(t9);
            result.add(t7);
            result.add(t2);
            result.add(t5);
            result.add(t1);
            result.add(t6);
        }
        return result;
    }



}
