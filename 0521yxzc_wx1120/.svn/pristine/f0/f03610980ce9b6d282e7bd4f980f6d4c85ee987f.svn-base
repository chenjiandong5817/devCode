package com.plugs.utils.websocket;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * websocket握手拦截器 <br>
 * Created on 2016/4/7.
 *
 * @author linweihao
 * @update zoro
 */
public class ChatHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    private static Logger logger = Logger.getLogger(ChatHandshakeInterceptor.class);
    public static final String WEBSOCKET_TOKEN_KEY = "websocket_token";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.debug("[webSocket]————Before Handshake");
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpSession session = servletRequest.getSession(false);
            //TODO:session有时候会为空
            if (session != null) {
                //使用token区分WebSocketHandler，以便定向发送消息
                String userName = servletRequest.getHeader(WEBSOCKET_TOKEN_KEY);
                // String timeStamp = servletRequest.getHeader("timeStamp");
                if (userName == null) {
                    userName = "default-system";
                }
                attributes.put(ChatMessageHandler.WEBSOCKET_KEY, userName);
                //attributes.put(userName,timeStamp);
            } else {
                String userName = servletRequest.getHeader(WEBSOCKET_TOKEN_KEY);
                if (userName == null) {
                    userName = "default-system";
                }
                attributes.put(ChatMessageHandler.WEBSOCKET_KEY, userName);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        logger.debug("[webSocket]————After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
