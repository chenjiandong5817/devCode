package com.plugs.base;

import com.util.StringUtil;

import java.util.*;

/**
 * 长连接推送实体
 *
 * @author Zoro
 */
public class WsMsg {


    public static final String TYPE_ORDER = "order";

    public static final String TYPE_MSG = "msg";

    //司机操作需要退给乘客的操作码
    public static final int OPERATE_CODE_ORDER_DRIVER_TAKE = 1000;//司机已接单
    public static final int OPERATE_CODE_ORDER_DRIVER_START_TO_TAKE_PASSENGER = 1001;//司机已出发
    public static final int OPERATE_CODE_ORDER_DRIVER_UPD_LOCATION = 1005;//司机变更位置

    public static final int OPERATE_CODE_RENT_ORDER_DRIVER_TAKE = 1009;//司机已接单_租车类型

    //乘客操作需要推给司机的操作码
    public static final int OPERATE_CODE_ORDER_PASSENGER_ORDER_DRIVER = 2000;//乘客下单，并指定了司机
    public static final int OPERATE_CODE_ORDER_PASSENGER_ORDER_CANCEL = 2001;//乘客取消订单
    public static final int OPERATE_CODE_ORDER_PASSENGER_ORDER_PUSH = 2002;//可抢订单推送
    public static final int OPERATE_CODE_ORDER_PASSENGER_ORDER_DISTRIBUTE = 2003;//订单派送
    public static final int OPERATE_CODE_ORDER_PASSENGER_ORDER_PAYED = 2004;//用户已支付

    //企业自动扣款支付的操作码
    public static final int OPERATE_CODE_ORDER_CANCELFEE_PAYED = 2009;//取消费支付成功
    public static final int OPERATE_CODE_PASSENGER_BALANCE_PAY_SUCCESS = 2010;//个人余额扣款成功
    public static final int OPERATE_CODE_PASSENGER_BALANCE_PAY_FAIL = 2011;//个人余额扣款失败

    //后台自动流程的操作码
    public static final int OPERATE_CODE_ORDER_ADMIN_CANCEL = 3000;//后台取消订单

    public static final int OPERATE_CODE_PASSENGER_PREPAID_LATE_TO_ORDER = 6011; //支付超时，已支付的费用将存入您的个人账户
    public static final int OPERATE_CODE_PASSENGER_PREPAID_CLOSE_TO_ORDER = 6012; //未及时预支付费用，取消订单通知乘客

    public static final int OPERATE_CODE_DRIVER_PREPAID_PAY_TO_ORDER = 6100; //乘客预支付成功通知司机
    public static final int OPERATE_CODE_DRIVER_PREPAID_CLOSE_TO_ORDER = 6101; //预支付订单关闭订单

    /**
     * 成员变量
     **/
    private String msg;//提示消息
    private boolean success;//是否成功
    private Object data;//附加数据
    private int errCode;//错误码
    private String type;//推送类型
    private int operateCode;//操作码（用以验证并判断本次推送具体的操作内容）
    private Date createTime = new Date();
    private To to;

    private String pushUuid = StringUtil.buildUUID();

    public enum To {
        DRIVER, PASSENGER
    }

    /**
     * 构造函数
     *
     * @param msg
     * @param success
     * @param data
     * @param errCode
     * @param type
     * @param operateCode
     */
    public WsMsg(String msg, boolean success, Object data, int errCode, String type, int operateCode, To to) {
        this.msg = msg;
        this.success = success;
        this.data = data;
        this.errCode = errCode;
        this.type = type;
        this.operateCode = operateCode;
        this.to = to;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(int operateCode) {
        this.operateCode = operateCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public String getPushUuid() {
        return pushUuid;
    }

    public void setPushUuid(String pushUuid) {
        this.pushUuid = pushUuid;
    }

    public static WsMsg createSuccess4Order(String msg, Object data, int operateCode, To to) {
        return new WsMsg(msg, true, data, 0, TYPE_ORDER, operateCode, to);
    }

    public static WsMsg createSuccess4Msg(String msg, Object data, To to) {
        return new WsMsg(msg, true, data, 0, TYPE_MSG, 0, to);
    }

}
