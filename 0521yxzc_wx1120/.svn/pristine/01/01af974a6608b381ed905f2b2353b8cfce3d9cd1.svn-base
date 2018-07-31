package com.plugs.utils;

import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.services.UserPassengerService;
import com.util.MapUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

/**
 * 国都短信
 *   00	批量短信提交成功
     01	个性化短信提交成功
     02	IP限制
     03	单条短信提交成功
     04	用户名错误
     05	 密码错误
     06	 个性化短信格式错误
     07	 发送时间有误
     08	内容为空
     09	手机号码为空
     10	 扩展号格式错误
     11	 余额不足
     -1	服务器内部错误
 * Created by Zhouhy on 2016/10/11.
 */
@Component
public class GuoDouSmsUtils {

    private static final Logger logger = Logger.getLogger(GuoDouSmsUtils.class);

    @Autowired
    private UserPassengerService userPassengerService;

    private static GuoDouSmsUtils guoDouSmsUtils;

    /**
     * PostConstruct修饰的方法会在服务器加载Servle的时候运行，
     * 并且只会被服务器执行一次。
     * PostConstruct在构造函数之后执行,init()方法之前执行
     */
    @PostConstruct
    public void init() {
        guoDouSmsUtils = this;
        guoDouSmsUtils.userPassengerService = this.userPassengerService;
    }

    //短信内容定义
    private static String ORDER_BE_TAKE_SMS_CONTENT = "【元翔专车】您deparTime从originAddress出发的订单已有司机接单：driverNamecarNo，联系电话driverMobile。orderPrepaid询0592-96363";
    private static String ORDER_RUSH_SUCCESS_SMS_CONTENT = "【元翔专车】您已接单：乘客尾号passengerMobileSuffix，deparTime从originAddress到destAddress，payToObject准时接驾。详情请到元翔专车应用内查看。";

    private static String ORDER_ADMIN_CANCEL_SMS_CONTENT = "【元翔专车】抱歉，您的订单已被取消，原因是cancelReason。详询0592-96363";

    private static String ORDER_PASSENGER_ARRIVE_SMS_CONTENT = "【元翔专车】您的行程于serverEndTime结束，成功支付payFare元。感谢使用！详询0592-96363. 退订回T";

    private static String CLOSE_PREPAID_ORDER_NOTICE ="【元翔专车】因您未完成订单预支付，您deparTime从originAddress出发的订单已被取消，您可重新下单，尽快完成预支付。详询0592-96363";


    private static final String MSN_URL_NEW = "http://qxtsms.guodulink.net:8000/HttpQuickProcess/submitMessageAll";
    //账号
    private static final String OPER_ID = "iport181";
    //密码
    private static final String OPER_PASS = "iportyxkx765";

    // 返回码
    public static final String RET_CODE = "03";


    public static String sendIdentifySmsUtf8(String mobile, String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("OperID", OPER_ID);
        params.put("OperPass", OPER_PASS);
        params.put("SendTime", "");//发送时间  空立即发送
        params.put("DesMobile", mobile);
        code = "【元翔专车】您好，您的验证码是" + code + "，如非本人操作，请忽略本短信。";
        String content = URLEncoder.encode(code, "GBK");
        String newContent = URLEncoder.encode(content, "gbk");
        params.put("Content", newContent);
        String retStr = HttpKit.post(MSN_URL_NEW, params, false);
        return retStr;
    }

    public static String sendContentSmsUtf8(String mobile, String content) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("OperID", OPER_ID);
        params.put("OperPass", OPER_PASS);
        params.put("SendTime", "");//发送时间  空立即发送
        params.put("DesMobile", mobile);
        String GBKcontent = URLEncoder.encode(content, "GBK");
        String gbkContent = URLEncoder.encode(GBKcontent, "gbk");
        params.put("Content", gbkContent);
        String retStr = HttpKit.post(MSN_URL_NEW, params, false);
        return retStr;
    }


    /**
     * 发送后台自动取消订单通知(给乘客)
     *
     * @param orderDto
     * @return
     */
    public static void sendAdminAutoCancelNotice(OrderDto orderDto, String cancelReason) {
        //后台取消订单，短信通知乘客
        try {
            String msgContent = ORDER_ADMIN_CANCEL_SMS_CONTENT
                    .replaceAll("cancelReason", cancelReason);
            GuoDouSmsUtils.sendContentSmsUtf8(orderDto.getActualPassengeMobile(), msgContent);
        } catch (Exception e) {
            logger.error("sendAdminAutoCancelNotice err", e);
        }
    }


    /**
     * 发送订单成单通知（司机和乘客）
     *
     * @param orderDto
     */
    public static void sendOrderBeTakeNotice(OrderDto orderDto, DriverDto driver) {
        try {
            //给司机
            String passengerMobile = orderDto.getActualPassengeMobile();
            String passengerMobileSuffix = passengerMobile.substring(passengerMobile.length() - 4, passengerMobile.length());
            //20171201待客叫车乘车人支付 额外添加由 费用由乘车人承担
            String msgContent4Driver;
            if (orderDto.getValetCall() == 1 && orderDto.getPayToObject() != null && orderDto.getPayToObject() == 1 && orderDto.getPrepayType() == 2) {
                msgContent4Driver = ORDER_RUSH_SUCCESS_SMS_CONTENT
                        .replaceAll("passengerMobileSuffix", passengerMobileSuffix)
                        .replaceAll("deparTime", DateUtils.format(orderDto.getDeparTime()))
                        .replaceAll("originAddress", orderDto.getOriginAddress())
                        .replaceAll("destAddress", orderDto.getDestAddress())
                        .replaceAll("payToObject", "费用由乘车人承担，请");
            } else {
                msgContent4Driver = ORDER_RUSH_SUCCESS_SMS_CONTENT
                        .replaceAll("passengerMobileSuffix", passengerMobileSuffix)
                        .replaceAll("deparTime", DateUtils.format(orderDto.getDeparTime()))
                        .replaceAll("originAddress", orderDto.getOriginAddress())
                        .replaceAll("destAddress", orderDto.getDestAddress())
                        .replaceAll("payToObject", "请");
            }
            GuoDouSmsUtils.sendContentSmsUtf8(driver.getMobile(), msgContent4Driver);

            //给乘客
            String msgContent4Passenger;
            if (orderDto.getPrepaidFee() > 0) {
                msgContent4Passenger = ORDER_BE_TAKE_SMS_CONTENT
                        .replaceAll("deparTime", DateUtils.format(orderDto.getDeparTime()))
                        .replaceAll("originAddress", orderDto.getOriginAddress())
                        .replaceAll("driverName", driver.getUserName())
                        .replaceAll("carNo", driver.getCarNo())
                        .replaceAll("driverMobile", driver.getMobile())
                        .replaceAll("orderPrepaid", "请尽快预支付" + orderDto.getPrepaidFee() + "元，确保行程正常进行，详");
            } else {
                msgContent4Passenger = ORDER_BE_TAKE_SMS_CONTENT
                        .replaceAll("deparTime", DateUtils.format(orderDto.getDeparTime()))
                        .replaceAll("originAddress", orderDto.getOriginAddress())
                        .replaceAll("driverName", driver.getUserName())
                        .replaceAll("carNo", driver.getCarNo())
                        .replaceAll("driverMobile", driver.getMobile())
                        .replaceAll("orderPrepaid", "详");
            }
            GuoDouSmsUtils.sendContentSmsUtf8(orderDto.getActualPassengeMobile(), msgContent4Passenger);
        } catch (Exception e) {
            logger.error("sendOrderBeTakeNotice err", e);
        }
    }



    /**
     * 司机到达目地点短信通知乘客费用
     * todo:后期参数优化
     *
     * @param orderDto
     */
    public static void sendOrderPayedNotice2Passenger(OrderDto orderDto) {
        try {
            String msgContent4Driver = ORDER_PASSENGER_ARRIVE_SMS_CONTENT
                    .replaceAll("serverEndTime", DateUtils.format(orderDto.getServiceTimeEnd()))
                    .replaceAll("payFare", Double.toString(Math.floor(orderDto.getPayFare())));
            if (orderDto.getValetCall() == 1) { //待客叫车发送给叫车人
                Map<String, Object> params = MapUtil.buildMap();
                params.put("uuid", orderDto.getPassengeUuid());
                UserPassengerDto user = guoDouSmsUtils.userPassengerService.selInfo(params);
                GuoDouSmsUtils.sendContentSmsUtf8(user.getMobile(), msgContent4Driver);
            } else {
                GuoDouSmsUtils.sendContentSmsUtf8(orderDto.getActualPassengeMobile(), msgContent4Driver);
            }
        } catch (Exception e) {
            logger.error("sendDriverArriveNotice err", e);
        }
    }

    /**
     * 预支付超时未支付关闭订单
     */
    public static void closePrepaidOrder2Passenger(OrderDto orderDto, UserPassengerDto userPassengerDto) {
        try {
            String content4Passenger = CLOSE_PREPAID_ORDER_NOTICE
                    .replaceAll("deparTime", DateUtils.format(orderDto.getDeparTime()))
                    .replaceAll("originAddress", orderDto.getOriginAddress());

            if (orderDto.getValetCall() != -1) {
                GuoDouSmsUtils.sendContentSmsUtf8(userPassengerDto.getMobile(), content4Passenger);
            } else {
                GuoDouSmsUtils.sendContentSmsUtf8(orderDto.getActualPassengeMobile(), content4Passenger);
            }
        } catch (Exception e) {
            logger.error("closePrepaidOrderToUserNotice err", e);
        }
    }

}
