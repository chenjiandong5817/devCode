package com.plugs.module_wechat.api;

import com.plugs.module_wechat.constants.Constants;
import com.plugs.module_wechat.pojo.SNSUserInfo;
import com.plugs.module_wechat.pojo.WeixinOauth2Token;
import com.plugs.module_wechat.util.AdvancedUtil;
import com.plugs.utils.StringUtils;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/wechat/fz/auth")
@Api(value = "福州微信认证", description = "福州微信认证")
public class WeChatFzAuthApi {
    private static final Logger logger = Logger.getLogger(WeChatFzAuthApi.class);

    private static final String APPID = "wx04c8887748cfd860";

    private static final String APPSECRET = "affc58c5966e0f45b7a01fe4b1a2c8cd";

    private static final String REDIRECT_URL = "http://ticket.panport-fjexpress.com/FZKX/jump-fz.jsp";

    private static final String AREA_UUID = "cb33bbcc0e8c400f992c4d1acff274cb";//福州区域UUID

    /**
     * 生成用于获取access_token的Code的Url
     *
     * @return
     */
    @RequestMapping("/auth")
    public String wxAuth() {
        return "redirect:" + this.getRequestCodeUrl(REDIRECT_URL);
    }

    /**
     * 生成用于获取access_token的Code的Url
     *
     * @param redirectUrl
     * @return
     */
    private String getRequestCodeUrl(String redirectUrl) {
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("translateFromYxFzAuth err:" + e.getMessage());
        }
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                APPID, redirectUrl, "snsapi_userinfo", "YXZC_STATE");
    }

    /**
     * 微信菜单的调用地址，获取授权信息并且返回跳转给前台
     *
     * @param request
     * @return
     */
    @RequestMapping("/getUserInfoAndRedirct")
    public String getUserInfoAndRedirct(HttpServletRequest request, Model model) {
        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String state="YXZC_STATE";//String state = request.getParameter("state");

        logger.info("code:"+code+"   state:"+state);

        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(APPID, APPSECRET, code);
            if (weixinOauth2Token.getErrorCode() != null && weixinOauth2Token.getErrorCode() == 40163) {
                //微信不允许5分钟之内相同Code重新请求，所以遇到这个错误我们就重新请求授权
                String url = getRequestCodeUrl(REDIRECT_URL);
                return "redirect:" + url;
            }
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

            // 设置要传递的参数
            request.getSession().setAttribute("snsUserInfo", snsUserInfo);
            request.getSession().setAttribute("state", state);
            //增加区域UUID设置
            String areaSession = (String) request.getSession().getAttribute("areaUuid");
            if (StringUtils.isEmpty(areaSession)) {
                request.getSession().setAttribute("areaUuid",AREA_UUID);
            }

            //跳转到主页
            return "wxPage/index.fz";
        }
        //跳转到主页
        return "redirect:" + this.getRequestCodeUrl(REDIRECT_URL);
    }


}
