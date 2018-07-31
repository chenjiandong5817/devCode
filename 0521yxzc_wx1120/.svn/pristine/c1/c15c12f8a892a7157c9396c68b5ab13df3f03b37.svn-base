package com.plugs.module_wechat.api;

import com.plugs.module_wechat.services.WechatOrderPayCallbackService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信公众号支付回调
 *
 * Created by Administrator on 2017/6/7.
 */
@Controller
@RequestMapping("/wechat/wxpay")
@ApiIgnore
@Api(value = "乘客支付回调接口", description = "乘客支付回调接口")
public class WechatOrderPayCallbackApi {

    @Autowired
    private WechatOrderPayCallbackService wechatOrderPayCallbackService;

    @RequestMapping(value = "/tradeCallback", method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public String clientCallback(HttpServletRequest request) throws Exception {
        System.out.println("----------------------------------------");
        System.out.println("-------------微信公众号支付回调---------");
        System.out.println("----------------------------------------");
        return wechatOrderPayCallbackService.orderPayTenpayCallBack(request);
    }

}
