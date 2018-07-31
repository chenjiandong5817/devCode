package com.plugs.module_wechat.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.plugs.base.BaseExtendApi;
import com.plugs.module_wechat.entity.resp.NewsMessage;
import com.plugs.module_wechat.entity.resp.TextMessage;
import com.plugs.module_wechat.entity.resp.type.Article;
import com.plugs.module_wechat.util.MessageUtil;
import com.plugs.module_wechat.util.SignUtil;
import com.utils.log4j.QxLog;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 公众号接入和消息处理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("wechat/core") // 配置地址时，只写到类路径，例如localhost:80/wechat.do
public class WechatCoreApi extends BaseExtendApi {
    private static final Logger logger = Logger.getLogger(WechatCoreApi.class);

    private Logger log = Logger.getLogger(WechatCoreApi.class);

    /**
     * 校验信息是否是从微信服务器发过来的。
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        logger.log(QxLog.LEVEL, "校验微信服务器发过来信息[signature]" + signature);
        logger.log(QxLog.LEVEL, "校验微信服务器发过来信息[timestamp]" + timestamp);
        logger.log(QxLog.LEVEL, "校验微信服务器发过来信息[nonce]" + nonce);
        logger.log(QxLog.LEVEL, "校验微信服务器发过来信息[echostr]" + echostr);

        PrintWriter out = response.getWriter();

        // 通过检验 signature 对请求进行校验，若校验成功则原样返回 echostr，表示接入
        // 成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {

            out.print(echostr);
        }

        out.close();

    }

    /**
     * 微信消息的处理
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = {RequestMethod.POST}, produces = "application/xml;charset=UTF-8")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 将请求、响应的编码均设置为 UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 调用核心业务类接收消息、处理消息
        String respMessage = processRequest(request);
        log.info(respMessage);

        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }

    /**
     * 转换消息格式
     *
     * @param request
     * @return
     */
    public String processRequest(HttpServletRequest request) {

        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml 请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 默认回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            // 事件推送
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");

                String eventKey = requestMap.get("EventKey");// 带参二维码直接把参数值作为eventkey传入

                String msg = "";
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    System.out.printf("*************这是订阅**************");
                    respMessage = scanOpt(toUserName, fromUserName);
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    if ("serviceTel".equals(eventKey)) {
                        msg = "服务热线：96363-0";
                        respMessage = activity(fromUserName, toUserName, msg);
                    } else if ("usingCar".equals(eventKey)) {
                        System.out.printf("*************点击按钮**************");
                        respMessage = scanOpt(toUserName, fromUserName);
                    }
                }
                // 带参二维码扫描
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    // 扫码后的操作
                    System.out.printf("*************扫码**************");
                    respMessage = scanOpt(toUserName, fromUserName);
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {

                    String Latitude = requestMap.get("Latitude");
                    String Longitude = requestMap.get("Longitude");

                    System.out.printf("Latitude:" + Latitude + "Longitude" + Longitude);
                }

            } else {
                // 文本消息
                if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                    respContent = "您发送的是文本消息！";
                }
                // 图片消息
                else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                    respContent = "您发送的是图片消息！";
                }
                // 地理位置消息
                else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                    respContent = "您发送的是地理位置消息！";
                }
                // 链接消息
                else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                    respContent = "您发送的是链接消息！";
                }
                // 音频消息
                else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                    respContent = "您发送的是音频消息！";
                }
                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }

    /**
     * 点击活动专区时，推送活动内容
     *
     * @param fromUserName
     * @param toUserName
     * @return
     */
    public String activity(String fromUserName, String toUserName, String msg) {
        /*// 创建图文消息
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

        List<Article> articleList = new ArrayList<Article>();
*/
        // 没有活动时，回复文本信息
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent(msg);
        return MessageUtil.textMessageToXml(textMessage);
    }

    /**
     * 带参二维码扫描
     *
     * @param toUserName
     * @param fromUserName
     */
    public String scanOpt(String toUserName, String fromUserName) {

        List<Article> list = new ArrayList<Article>();

        Article article = new Article();
        article.setTitle("接送机一口价！真的是一口价");
        article.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7b2cd97fb9446891&redirect_uri=http%3A%2F%2F5afef62e.ngrok.io%2Fwechat%2Fauth%2FgetUserInfoAndRedirct&response_type=code&scope=snsapi_userinfo&state=YXZC_STATE&uin=NDU4MTA1MzIw&key=a1ef062e63bc1830607f548d949cef029b87447063a34f1f516037dd85a09266a64b044824905795cff86595e29c4086&pass_ticket=RHelAXNFrmKkBl0x6aDPkvp/qdxtqT8ogi7liTD2F2OF4eqhvl+VeDs2Ou/kiDx+rySP7RPeR/iwL5Ae+rZwxA==");
        article.setPicUrl("https://mmbiz.qlogo.cn/mmbiz_jpg/MUX6CwRdkOHFHia1Jx8ktwB7FWib1TZvbiazRwD2xbDdcZE18Q0fTA15my0w8BXhvyxJXKFZ4r74Z3ibvshJxfic9rA/0?wx_fmt=jpeg");
        article.setDescription("●一口价，看见多少付多少 ●全新功能让您省省省 ●机场专属车道 零距离上下车");

        NewsMessage news = new NewsMessage();
        news.setArticleCount(1);
        list.add(article);
        news.setArticles(list);
        news.setFromUserName(toUserName);
        news.setToUserName(fromUserName);
        news.setMsgType("news");
        news.setCreateTime(new Date().getTime());

        return MessageUtil.newsMessageToXml(news);
    }

}
