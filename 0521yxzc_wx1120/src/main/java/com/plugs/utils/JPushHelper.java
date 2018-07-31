package com.plugs.utils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.IosNotification.Builder;
import cn.jpush.api.push.model.notification.Notification;
import com.plugs.constants.ConfigConstants;
import org.apache.log4j.Logger;
import springfox.documentation.service.Tags;

import java.util.HashMap;
import java.util.Map;


/**
 * 极光推送推送工具类
 *
 * @update Zoro
 * @update 2016/10/27
 */
public class JPushHelper {

    //推送类型
    public enum TargetTo {
        DRIVER, PASSENGER
    }

    //平台
    public enum Os {
        ANDROID, IOS, ALL
    }

    private static Logger log = Logger.getLogger(JPushHelper.class);

    //乘客的配置
    private static final String PASSENGER_J_PUSH_MASTER_SECRET = "4a0ff998d6a5e58ff6263eff";// Master Secret
    private static final String PASSENGER_J_PUSH_APP_KEY = "6ea35bf34278df9fc8a53a60";// appkey

    //司机的配置
    private static final String DRIVER_J_PUSH_MASTER_SECRET = "c82a5ffe7f986fe204af85ed";// Master Secret
    private static final String DRIVER_J_PUSH_APP_KEY = "1fc8864b013ea2e054e15081";// appkey

    private TargetTo target = TargetTo.DRIVER;

    public JPushHelper(TargetTo target) {
        this.target = target;
    }

    /**
     * 主函数测试
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        JPushHelper jPushHelper = new JPushHelper(TargetTo.PASSENGER);
        jPushHelper.pushMessage4AndroidWithAlias(new String[]{"e280835bdbba4c6aaf4c82a8a81fa660"}, "122", "3r3w43", new HashMap<String, String>());

        //jPushHelper.pushNotification4IosWithAlias(new String[]{"86fbc2d03ada444599d24a564f9b7426"}, "了很顺利的减肥了时间到了腹肌撕裂", 0, new HashMap<String, String>());

    }

    /**
     * 通过标签推送--安卓
     *
     * @param tags       标签
     * @param title      标题
     * @param msgContent 内容
     * @param extras     附带信息
     * @return
     */
    public boolean pushMessage4AndroidWithTags(String[] tags, String title, String msgContent, Map<String, String> extras) {
        if (StringUtils.ArrayIsEmpty(tags)) {
            return true;
        }
        try {
            JPushClient jpushClient = getInstance();
            PushPayload pushPayload = PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.tag(tags))
                    .setMessage(Message.newBuilder().addExtras(extras).setMsgContent(msgContent).setTitle(title).build())
                    .build();
            PushResult result = jpushClient.sendPush(pushPayload);
            return result.isResultOK();
        } catch (Exception e) {
            log.error("JPush err:" + e.getMessage());
        }
        return false;
    }


    /**
     * 通过别名推送--安卓
     *
     * @param alias      别名
     * @param title      消息标题
     * @param msgContent 消息内容
     * @param extras     附带信息
     * @return
     */
    public boolean pushNotification4AndroidWithAlias(String alias[], String title, String msgContent, Map<String, String> extras) {
        // alias，则跳过该推送
        if (StringUtils.ArrayIsEmpty(alias)) {
            return true;
        }
        try {
            JPushClient jpushClient = getInstance();
            PushResult result = jpushClient.sendAndroidNotificationWithAlias(title, msgContent, extras, alias);
            return result.isResultOK();
        } catch (Exception e) {
            log.error("JPush err :" + e.getMessage());
        }
        return false;
    }

    /**
     * 自定义消息推送--安卓
     *
     * @param alias
     * @param title
     * @param msgContent
     * @param extras
     * @return
     */
    public boolean pushMessage4AndroidWithAlias(String alias[], String title, String msgContent, Map<String, String> extras) {
        // alias，则跳过该推送
        if (StringUtils.ArrayIsEmpty(alias)) {
            return true;
        }
        try {
            JPushClient jpushClient = getInstance();

            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.alias(alias))
                    .setMessage(Message.newBuilder().addExtras(extras).setTitle(title).setMsgContent(msgContent).build()).build();

            PushResult result = jpushClient.sendPush(payload);
            return result.isResultOK();
        } catch (Exception e) {
            log.error("JPush err :" + e.getMessage());
        }
        return false;
    }

    /**
     * IOS标签推送
     *
     * @param tags       标签
     * @param msgContent 消息内容
     * @param badge      角标
     * @param extras     附带信息
     * @return
     */
    public boolean pushNotification4IosWithTags(String[] tags, String msgContent, int badge, Map<String, String> extras) {
        // alias为空则跳过该推送
        if (StringUtils.ArrayIsEmpty(tags)) {
            return true;
        }
        try {
            JPushClient jpushClient = getInstance();

            Builder iosBuilder = IosNotification.newBuilder();
            // 弹窗内容为空
            if (StringUtils.isEmpty(msgContent)) {
                msgContent = "";
                iosBuilder.disableSound();
            } else {
                iosBuilder.setSound("default");
            }
            if (badge < 0) {
                iosBuilder.disableBadge();
            } else {
                iosBuilder.setBadge(badge);
            }
            IosNotification iosNotification = iosBuilder.setAlert(msgContent).setContentAvailable(true).addExtras(extras).build();

            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.tag(tags))
                    .setOptions(Options.newBuilder().setApnsProduction(ConfigConstants.IS_APNS_PRODUCTION).build())
                    .setNotification(Notification.newBuilder().addPlatformNotification(iosNotification).build()).build();
            PushResult result = jpushClient.sendPush(payload);
            return result.isResultOK();
        } catch (Exception e) {
            log.error("JPush err :" + e.getMessage());
        }
        return false;
    }


    /**
     * IOS别名推送
     *
     * @param alias      别名
     * @param msgContent 消息内容
     * @param badge      角标
     * @param extras     附带信息
     * @return
     */
    public boolean pushNotification4IosWithAlias(String[] alias, String msgContent, int badge, Map<String, String> extras) {
        log.info("【ios别名推送】-alias=" + alias[0]);
        // alias为空则跳过该推送
        if (StringUtils.ArrayIsEmpty(alias)) {
            return true;
        }
        try {
            JPushClient jpushClient = getInstance();

            Builder iosBuilder = IosNotification.newBuilder();
            // 弹窗内容为空
            if (StringUtils.isEmpty(msgContent)) {
                msgContent = "";
                iosBuilder.disableSound();
            } else {
                iosBuilder.setSound("default");
            }
            if (badge < 0) {
                iosBuilder.disableBadge();
            } else {
                iosBuilder.setBadge(badge);
            }
            IosNotification iosNotification = iosBuilder.setAlert(msgContent).setContentAvailable(true).addExtras(extras).build();

            log.info("【ios推送-是否生产环境】" + SysConfigHelper.getApnsProduction());
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.alias(alias))
                    .setOptions(Options.newBuilder().setApnsProduction(SysConfigHelper.getApnsProduction()).build())
                    .setNotification(Notification.newBuilder().addPlatformNotification(iosNotification).build()).build();
            PushResult result = jpushClient.sendPush(payload);
            //两个环境都推送
            log.info("【ios推送结果】" + result.isResultOK());
            return result.isResultOK();
        } catch (Exception e) {
            log.error("JPush err :" + e.getMessage());
        }
        return false;
    }

    /**
     * 推送全部
     *
     * @param title      标题
     * @param msgContent 内容
     * @param badge      角标
     * @param extras     附带信息
     * @param os         平台
     * @return
     */
    public boolean pushAll(String title, String msgContent, int badge, Map<String, String> extras, Os os) {
        try {
            JPushClient jpushClient = getInstance();
            PushPayload payload = null;
            IosNotification iosNotification = null;

            // 如果是Android平台
            if (Os.ANDROID.equals(os)) {
                payload = PushPayload.newBuilder()
                        .setPlatform(Platform.android())
                        .setAudience(Audience.all())//全部设备
                        .setMessage(Message.newBuilder().addExtras(extras).setMsgContent(msgContent).setTitle(title).build())
                        .build();
            } else {
                Builder iosBuilder = IosNotification.newBuilder();
                // 弹窗内容为空
                if (StringUtils.isEmpty(msgContent)) {
                    msgContent = "";
                    iosBuilder.disableSound();
                } else {
                    iosBuilder.setSound("default");
                }
                if (badge < 0) {
                    iosBuilder.disableBadge();
                } else {
                    iosBuilder.setBadge(badge);
                }
                iosNotification = iosBuilder.setAlert(msgContent).setContentAvailable(true).addExtras(extras).build();
                //boolean isApnsProduction = ConfigConstants.IS_APNS_PRODUCTION;// 是否是APNS生产环境
                payload = PushPayload
                        .newBuilder()
                        .setPlatform(Platform.ios())
                        .setAudience(Audience.all())
                        .setOptions(Options.newBuilder().setApnsProduction(SysConfigHelper.getApnsProduction()).build())
                        .setNotification(Notification.newBuilder().addPlatformNotification(iosNotification).build()).build();
            }

            PushResult result = jpushClient.sendPush(payload);

            return result.isResultOK();
        } catch (Exception e) {
            log.error("JPush err :" + e.getMessage());
        }
        return false;
    }

    /**
     * 获取推送实例
     *
     * @return
     */
    private JPushClient getInstance() {
        if (target == TargetTo.DRIVER) {
            return new JPushClient(DRIVER_J_PUSH_MASTER_SECRET, DRIVER_J_PUSH_APP_KEY);
        } else if (target == TargetTo.PASSENGER) {
            return new JPushClient(PASSENGER_J_PUSH_MASTER_SECRET, PASSENGER_J_PUSH_APP_KEY);
        } else {
            throw new IllegalArgumentException("JPushHelper type err");
        }
    }

}
