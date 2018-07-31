package com.plugs.utils.websocket;

import com.plugs.utils.WebSocketUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 描述： <br>
 * Created on 2016/4/7.
 *
 * @author linweihao
 */
@Controller
@ApiIgnore
public class WebsocketController {
    @Bean//这个注解会从Spring容器拿出Bean
    public ChatMessageHandler infoHandler() {
        return new ChatMessageHandler();
    }

    @RequestMapping("/websocket/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        HttpSession session = request.getSession(false);
        session.setAttribute(ChatHandshakeInterceptor.WEBSOCKET_TOKEN_KEY, username);

        response.sendRedirect("ws.jsp");
    }

    @RequestMapping("/websocket/send")
    @ResponseBody
    public String send(HttpServletRequest request) {

        String username = request.getParameter("username");
        WebSocketUtil.sendMessageByToken(username, "测试");

        return null;
    }

    @RequestMapping("/websocket/close")
    @ResponseBody
    public String close(HttpServletRequest request) throws IOException {
        String username = request.getParameter("username");
        WebSocketUtil.closeWebsocketConnect(username);
        return "";
    }
}
