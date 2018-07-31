package com.plugs.module_wechat.api;

import com.plugs.base.AjaxList;
import com.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/wechat/change")
@Api(value = "切换区域重置session", description = "切换区域重置session")
public class WeChatChangeSessionApi {

    private static final String SESSION_KEY = "areaUuid";


    /**
     * 切换区域重置session
     */
    //通用界面跳转
    @RequestMapping("/session/{sessionVal}")
    @ResponseBody
    @ApiOperation(value = "切换区域重置session", response = AjaxList.class, httpMethod = "GET", notes = "切换区域重置session")
    public AjaxList pageView(@PathVariable("sessionVal") String sessionVal,
                             HttpServletRequest request) {
        if (StringUtil.isNotEmpty(sessionVal)) {
            request.getSession().setAttribute(SESSION_KEY, sessionVal);
            return AjaxList.createSuccess("重置session成功", sessionVal);
        }
        return AjaxList.createError("重置session失败");
    }


}
