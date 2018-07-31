package com.plugs.utils;

import com.plugs.base.WsMsg;
import com.plugs.thread.JPushThread;
import com.plugs.utils.websocket.ChatMessageHandler;
import com.util.StringUtil;
import com.utils.log4j.QxLog;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述： <br>
 * Created on 2016/4/8.
 *
 * @author linweihao
 */
public class WebSocketUtil {

    private static final Logger logger = Logger.getLogger(WebSocketUtil.class);
    private static final Logger qxLogger = Logger.getLogger(WebSocketUtil.class);

    @Bean
    public static ChatMessageHandler infoHandler() {
        return new ChatMessageHandler();
    }

    /**
     * 推送消息到指定用户
     *
     * @param userUuid
     * @param msg
     */
    public static void sendMessageByToken(String userUuid, String msg) {
        logger.debug("【长连接推送】----[" + userUuid + "][" + msg + "]");
        infoHandler().sendMessageToUser(userUuid, new TextMessage(msg));
    }

    /**
     * 推送消息到所有在线用户
     *
     * @param msg
     */
    public static void sendMessageByAll(String msg) {
        infoHandler().sendMessageToUsers(new TextMessage(msg));
    }

    /**
     * 给指定用户发送消息（obj）
     *
     * @param userUuid
     * @param obj
     */
    public static void sendObjMessageByToken(String userUuid, Object obj) {
//        infoHandler().sendMessageToUser(userUuid, new TextMessage(StringUtil.toJsonStr(obj)));
        try {
            //判断实体是不是长连接的
            if (obj instanceof WsMsg) {
                WsMsg wsMsg = (WsMsg) obj;
                if (wsMsg.getOperateCode() != WsMsg.OPERATE_CODE_ORDER_DRIVER_UPD_LOCATION) {
//                    qxLogger.log(QxLog.LEVEL, "【长连接推送】----[" + userUuid + "][" + StringUtil.toJsonStr(obj) + "]");
                    qxLogger.log(QxLog.LEVEL, "极光推送[" + userUuid + "]-----");
                    Map<String, String> extras = new HashMap<String, String>();
                    extras.put("type", "order");
                    extras.put("data", StringUtil.toJsonStr(obj));
                    String content = wsMsg.getMsg();
                    String[] alias = new String[]{userUuid};

                    //推给司机
                    if (WsMsg.To.DRIVER == wsMsg.getTo()) {
                        //JPushHelper jPushHelper = new JPushHelper(JPushHelper.TargetTo.DRIVER);//给司机
                        //jPushHelper.pushMessage4AndroidWithAlias(alias, "订单变动", content, extras);

                        new JPushThread("订单变动", content, 0, extras)
                                .init(JPushHelper.TargetTo.DRIVER, JPushThread.SendType.ALIAS, JPushHelper.Os.ANDROID)
                                .setTargets(null, alias)
                                .start();
                    }
                    //推给乘客
                    if (WsMsg.To.PASSENGER == wsMsg.getTo()) {
                        //JPushHelper jPushHelper = new JPushHelper(JPushHelper.TargetTo.PASSENGER);//给乘客
                        //jPushHelper.pushMessage4AndroidWithAlias(alias, "订单变动", content, extras);//安卓
                        //jPushHelper.pushNotification4IosWithAlias(alias, content, 0, extras);//IOS

                        new JPushThread("订单变动", content, 0, extras)
                                .init(JPushHelper.TargetTo.PASSENGER, JPushThread.SendType.ALIAS, JPushHelper.Os.ALL)
                                .setTargets(null, alias)
                                .start();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("sendObjMessageByToken [Jpush] err", e);
            qxLogger.log(QxLog.LEVEL, "sendObjMessageByToken [Jpush] err[" + e.getMessage() + "]");
        }
    }


    /**
     * 关闭websocket连接
     *
     * @return
     */
    public static void closeWebsocketConnect(String socketToken) throws IOException {
        infoHandler().closeConnection(socketToken);
    }

    /**
     * 判断用户是否在线
     *
     * @param userUuid
     * @return
     */
    public static boolean userIsOnline(String userUuid) {
        return infoHandler().userIsOnline(userUuid);
    }
}
