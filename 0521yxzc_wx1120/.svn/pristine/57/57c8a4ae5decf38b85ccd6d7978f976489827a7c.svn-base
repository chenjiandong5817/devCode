package com.plugs.module_wechat.api;

import com.base.BaseApi;
import com.plugs.module_wechat.util.SignUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by Zhouhy on 2016/8/1.
 */
@Controller
@RequestMapping("/wechat")
@ApiIgnore
public class WeChatAccessApi extends BaseApi {

    /***
     * 微信认证
     * @param request
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @throws Exception
     */
    @RequestMapping(value="/access/tokenValidate", method = RequestMethod.GET)
    public void accessToken(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value="signature") String signature,
                            @RequestParam(value="timestamp") String timestamp,
                            @RequestParam(value="nonce") String nonce,
                            @RequestParam(value="echostr") String echostr)throws Exception {

        System.out.println("微信认证...");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            PrintWriter out = response.getWriter();
            System.out.println("成功返回 echostr：" + echostr);
            out.write(echostr);
            out.flush();
            out.close();
        }
    }


}
