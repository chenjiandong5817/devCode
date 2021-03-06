package com.plugs.module_system.api;

import com.plugs.base.AjaxList;
import com.plugs.constants.ConfigConstants;
import com.plugs.module_system.dtos.SysAreaConfigDto;
import com.plugs.module_system.dtos.SysCarBillingWayDto;
import com.plugs.module_system.dtos.SysCityDto;
import com.plugs.module_system.services.SysAreaConfigService;
import com.plugs.module_system.services.SysCarBillingWayService;
import com.plugs.module_system.services.SysCityService;
import com.plugs.module_wechat.api.WeChatMenuApi;
import com.plugs.module_wechat.constants.Constants;
import com.plugs.module_wechat.util.AccessToken;
import com.plugs.module_wechat.util.WeChatUtil;
import com.plugs.module_wechat.util.WeixinUtil;
import com.plugs.utils.templatemsg.TemplateData;
import com.plugs.utils.templatemsg.TemplateMsgHelper;
import com.util.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhouhy on 2016/9/19.
 */
@RestController
@RequestMapping("/")
@Api(value = "微信域名认证", description = "微信域名认证")
public class WechatVerifyApi {


    @RequestMapping("MP_verify_qwAJYbJG7eO3Z6mv.txt")
    @ApiOperation(value = "计价规则", httpMethod = "GET", response = AjaxList.class, notes = "计价规则")
    public String priceRule(HttpServletRequest request, Model model) {
        return "qwAJYbJG7eO3Z6mv";
    }



}
