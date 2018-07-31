package com.plugs.constants;

/**
 * //Created by Zhouhy on 2016/9/19.
 */
public class ReturnCodeConstants {

    // 1：访问成功 [0~999]
    //访问成功
    public final static int ERR_0_SUCCESS = 0;

    // 2：请求参数非法 [1000~1999]
    //参数错误
    public final static int ERR_1000_PARAMS_ERR = 1000;

    // 3：权限校验失败 [2000~2999]
    //TOKEN效验错误
    public final static int ERR_2000_TOKEN_ERR = 2000;
    //无效用用户
    public final static int ERR_2001_INVALID_USER = 2001;
    //登录超时
    public final static int ERR_2002_SESSION_TIMEOUT = 2002;
    //未注册
    public final static int ERR_2003_UN_REGISTER = 2003;
    //封号
    public final static int ERR_2004_ABORT = 2004;
    //未绑定车
    public final static int ERR_2005_UNBING_CAR = 2005;

    // 4：服务器系统异常 [3000~9999]
    //服务器异常
    public final static int ERR_3000_SERVER_ERR = 3000;
    //SMS服务器错误
    public final static int ERR_3001_SMS_API_ERR = 3001;
    //百度地图Api服务异常
    public final static int ERR_3002_BAIDUMAP_API_ERR = 3002;
    //环信api服务异常
    public final static int ERR_3003_EASEMOD_API_ERR = 3002;


    // 5：服务器业务异常 [10000~999999]
    //手机号与验证码不匹配
    public final static int ERR_10001_IDENTITY_CODE_ERR = 10001;
    //验证码过期
    public final static int ERR_10002_IDENTITY_CODE_OVERDUE = 10002;
    //手机号已注册
    public final static int ERR_10003_MOBILE_REPEAT = 10003;
    //用户重复提交请求
    public final static int ERR_10004_ACTION_REPEAT = 10004;
    //新增超过数量限制
    public final static int ERR_10005_LIMIT_OVER = 10005;
    //逻辑错误
    public final static int ERR_10006_LOGIC_ERR = 10006;
    //数据已经被删除
    public final static int ERR_10007_DELETE_ERR = 10007;
    //文件大小超过限制
    public final static int ERR_10008_FILE_LIMIT = 10008;
    //文件类型不符合要求
    public final static int ERR_10009_FILE_TYPE_ERR = 10009;
    //文件上传错误
    public final static int ERR_10010_FILE_UPLOAD_ERR = 10010;
    //超过可开发票总额
    public final static int ERR_10011_INVOICE_LIMIT_ERR = 10011;

    //生成交易地址失败
    public final static int ERR_10012_ALIPAY_TRADE_URL_ERR = 10012;
    public final static int ERR_10013_WX_PREPAYMENT_ERR = 10013;
    public final static int ERR_10014_SIGN_ERR = 10014;


    //未检索到满足条件的航班号
    public final static int ERR_11001_SRH_FLIGHT_FAIL = 11001;

    //6：订单相关错误码
    public final static int ERR_ORDER_NOT_EXIST = 20000;//订单不存在
    public final static int ERR_ORDER_STAUTS_NOT_WAIT_PAY = 20001;//订单不是待支付状态
    public final static int ERR_ORDER_NOT_DRIVER = 20002;//订单没有接单司机
    public final static int ERR_ORDER_PAY_MONEY_ERR = 20003;//订支付金额错误
    public final static int ERR_ORDER_PASSENGER_BALANCE_NOT_ENOUGH = 20004;//个人余额不足
    public final static int ERR_ORDER_STATUS_ERROR = 20005;//订单状态错误
    public final static int ERR_ORDER_NO_SUITE_DRIVER = 20006;//没有合适的司机
    public final static int ERR_ORDER_PAY_OBJECT_ERROR = 20007; //叫车人无权限支付乘车人付款订单

    public final static int ERR_ORDER_PRE_PAY_MONEY_ERROR = 20100; //订单预支付金额异常

    //订单优惠券
    public final static int ERR_ORDER_COUPON_UNABLE = 21000;//优惠券不可用
    public final static int ERR_ORDER_COUPON_NOT_SUITE = 21001;//优惠券不适用



    public final static int ERR_DOCKING_SIGN = 30000;//对接签名错误





}
