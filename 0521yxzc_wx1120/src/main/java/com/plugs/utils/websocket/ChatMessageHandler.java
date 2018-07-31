package com.plugs.utils.websocket;

import com.util.StringUtil;
import com.utils.log4j.QxLog;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * websocket功能处理： <br>
 * Created on 2016/4/7.
 *
 * @author linweihao
 * @author Zoro update 2016/10/19
 */
public class ChatMessageHandler extends TextWebSocketHandler {
    public static final String WEBSOCKET_KEY = "websocket_useruuid";

    private static final Map<String, WebSocketSession> users;//存储连接用户，key为用户UUID

    private static Logger logger = Logger.getLogger(ChatMessageHandler.class);
    private static Logger qxLogger = Logger.getLogger(ChatMessageHandler.class);

    static {
        users = new ConcurrentHashMap<String, WebSocketSession>();
    }

    public ChatMessageHandler() {

    }

    /**
     * 连接成功时候，会触发UI上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("[webSocket]————Connect to the webSocket success......");
        Object userUuid = session.getAttributes().get(WEBSOCKET_KEY);
        if (users.containsKey(userUuid)) {
            WebSocketSession user = users.get(userUuid);
            if (user != null) {
                try {
                    if (user.isOpen()) {
                        user.close();
                    }
                } catch (Exception e) {
                    logger.error("[webSocket]————close err", e);
                }
            }
            users.remove(userUuid);
        }
        logger.debug("[webSocket]—————new user connect :" + userUuid.toString());
        users.put(userUuid.toString(), session);
        logger.debug("[webSocket]————current user numbers:" + users.size());
        //qxLogger.log(QxLog.LEVEL,"当前WebSocket连接数：" + users.size());

        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户（当然这里最后有推送记录等设计）
        //TextMessage returnMessage = new TextMessage("你将收到的离线");
        //session.sendMessage(returnMessage);
    }

    /**
     * 在UI在用js调用websocket.send()时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        qxLogger.log(QxLog.LEVEL, "收到用户信息：" + message.getPayload());
        Object userUuid = session.getAttributes().get(WEBSOCKET_KEY);
        sendMessageToUser(userUuid.toString(), new TextMessage("收到消息"));//回复
        super.handleTextMessage(session, message);

    }

    /**
     * 给某个用户(TOKEN标识)发送消息
     *
     * @param userUuid
     * @param message
     */
    public void sendMessageToUser(String userUuid, TextMessage message) {
        WebSocketSession user = users.get(userUuid);
        if (user != null) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                logger.error("[webSocket]————sendMessage err", e);
            }
        }

    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        Set<String> keys = users.keySet();
        for (String key : keys) {
            WebSocketSession user = users.get(key);
            if (null != user) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    logger.error("[webSocket]————closeConnection err", e);
                }
            }
        }
    }

    /**
     * 关闭连接
     *
     * @param userUuid
     * @throws IOException
     */
    public void closeConnection(String userUuid) throws IOException {
        WebSocketSession user = users.get(userUuid);
        if (user != null) {
            try {
                if (user.isOpen()) {
                    user.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断用户是否在线
     *
     * @param userUuid
     * @return
     */
    public boolean userIsOnline(String userUuid) {
        if (users.containsKey(userUuid)) {
            WebSocketSession user = users.get(userUuid);
            if (user != null) {
                return user.isOpen();
            }
        }
        return false;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.debug("[webSocket]————connection handleTransportError......");
        //qxLogger.log(QxLog.LEVEL,"长连接 handleTransportError ["+exception.toString()+"]");
        users.remove(session.getAttributes().get(WEBSOCKET_KEY));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("[webSocket]————connection closed......");
        //qxLogger.log(QxLog.LEVEL,"长连接 afterConnectionClosed ");
        try {
            //qxLogger.log(QxLog.LEVEL,"长连接 session.close()调用 ");
            session.close();
        } catch (Exception e) {
            //qxLogger.log(QxLog.LEVEL,"webSocket close err:" + e.getMessage());
            logger.error("webSocket err:" + e.getMessage());
        }
        users.remove(session.getAttributes().get(WEBSOCKET_KEY));
        //qxLogger.log(QxLog.LEVEL,"afterConnectionClosed[剩余数量:" + users.size() + "]");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 清理无效长连接
     */
    public static void clearInvalidConnection() {
        Set<Map.Entry<String, WebSocketSession>> entrySet = users.entrySet();
        for (Map.Entry<String, WebSocketSession> entry : entrySet) {
            WebSocketSession user = entry.getValue();
            if (user != null) {
                if (!user.isOpen()) {
                    try {
                        user.close();
                        users.remove(entry.getKey());
                    } catch (Exception e) {
                        //qxLogger.log(QxLog.LEVEL,"webSocket close err:" + e.getMessage());
                        logger.error("webSocket close err:" + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * 关闭全部长连接(暂时没用)
     */
    public static void clearAll() {
        qxLogger.log(QxLog.LEVEL, "清理全部长连接");
        Set<Map.Entry<String, WebSocketSession>> entrySet = users.entrySet();
        for (Map.Entry<String, WebSocketSession> entry : entrySet) {
            WebSocketSession user = entry.getValue();
            if (user != null) {
                try {
                    user.close(CloseStatus.NORMAL);
                    users.remove(entry.getKey());
                } catch (Exception e) {
                    //qxLogger.log(QxLog.LEVEL,"webSocket close err:" + e.getMessage());
                    logger.error("webSocket close err:" + e.getMessage());
                }
            }
        }
        qxLogger.log(QxLog.LEVEL, "清理完毕[剩余数量:" + users.size() + "]");
    }

}
