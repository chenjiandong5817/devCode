package com.plugs.module_wechat.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/wechat/view")
@Api(value = "微信通用页面跳转", description = "微信通用页面跳转API")
public class WeChatViewApi {

    private static final String FZ_AREA_UUID = "cb33bbcc0e8c400f992c4d1acff274cb";//福州区域UUID

    //通用界面跳转
    @RequestMapping("{pageName}")
    @ApiOperation(value = "微信通用页面跳转", httpMethod = "GET", notes = "wechatPage为文件所在的文件夹，view问文件名称")
    public String pageView(@PathVariable("pageName") String pageName,
                           HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        String sessionAreauuid = (String) request.getSession().getAttribute("areaUuid");
        if ("index".equals(pageName)) {
            sb.append("wxPage") .append("/") .append("index.fz");
        } else {
            sb.append("wxPage") .append("/") .append(pageName);
        }
        return sb.toString();
    }

}
