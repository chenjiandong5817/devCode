package com.plugs.module_wechat.api;

import com.plugs.base.AjaxList;
import com.plugs.constants.ConfigConstants;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.services.UserPassengerService;
import com.plugs.module_wechat.services.WechatPassengerService;
import com.plugs.utils.BizValidate;
import com.plugs.utils.StringUtils;
import com.util.MD5Util;
import com.util.MapUtil;
import com.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/wechat/passenger")
@Api(value = "微信乘客", description = "微信乘客")
public class WechatPassengerApi {

    @Autowired
    private WechatPassengerService wechatPassengerService;
    @Autowired
    private UserPassengerService passengerService;


    /**
     * 首页根据openId验证微信用户是否存在
     * @param openId openId
     * @param request request
     * @return 存在该用户将其存入session中并放回给前端
     */
    @RequestMapping("/login/validateOpenId")
    @ResponseBody
    @ApiOperation(value = "验证微信用户OpenID", httpMethod = "POST")
    public AjaxList validateOpenId(@ApiParam(name = "openId",value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                                   HttpServletRequest request) throws Exception {
        //查询是否在微信注册过
        Map<String,Object> map = MapUtil.buildMap();
        map.put("openId",openId);
        List<UserPassengerDto> users = this.passengerService.list(map);
        //已经有绑定手机的情况，就返回用户信息，并且更新token
        if(null != users && users.size() > 0){
            UserPassengerDto passengerDto = users.get(0);
            passengerDto.setTokenId(StringUtil.buildUUID());
            this.passengerService.edit(passengerDto);
            //将微信用户放入session中
            request.getSession().setAttribute(ConfigConstants.SESSION_KEY_PASSENGER_USER_WX, passengerDto);
            return AjaxList.createSuccess("存在绑定用户",passengerDto);
        }
        return AjaxList.createError("查无用户信息");
    }

    /**
     * 发送验证码
     * @param request request
     * @param mobile 手机号码
     * @param openId openId
     * @param sign 签名
     * @return 验证码
     * @throws Exception
     */
    @RequestMapping("/sendCode")
    @ResponseBody
    @ApiOperation(value = "发送验证码", httpMethod = "POST")
    public AjaxList sendWxCode(@ApiParam(name = "mobile", required = true, value = "手机号") @RequestParam String mobile,
                               @ApiParam(name = "openId", required = true, value = "openId") @RequestParam String openId,
                               @ApiParam(name = "sign", required = true, value = "签名") @RequestParam String sign,
                               HttpServletRequest request) throws Exception {

        if (!StringUtils.checkMobile(mobile)) {
            return AjaxList.createError("手机号码格式不正确");
        }
        //md5验签
        String paramsStr = "openId=" + openId + "&key=" + ConfigConstants.SMS_KEY;
        String clientSign = MD5Util.MD5Encode(paramsStr).toUpperCase();

        if (!clientSign.equals(sign)) {
            return AjaxList.createError("参数不正确");
        }
        return this.wechatPassengerService.sendWxCode(mobile);
    }


    /**
     *
     * @param mobile
     * @param nickName
     * @param sex
     * @param identifyCode
     * @param openId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    @ResponseBody
    @ApiOperation(value = "微信用户登录", httpMethod = "POST")
    public AjaxList login(@ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam(value = "mobile") String mobile,
                          @ApiParam(name = "nickName", required = false, value = "昵称") @RequestParam(required = false) String nickName,
                          @ApiParam(name = "sex", required = false, value = "备注") @RequestParam(required = false) Integer sex,
                          @ApiParam(name = "identifyCode", value = "验证码", required = true) @RequestParam(value = "identifyCode") String identifyCode,
                          @ApiParam(name = "openId", value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                          HttpServletRequest request) throws Exception {

        BizValidate.notEmpty(mobile, "请输入手机号");

        if (!StringUtils.checkMobile(mobile))
            return AjaxList.createError("请输入正确的手机号");
        BizValidate.notEmpty(identifyCode, "验证码不正确");
        return this.wechatPassengerService.wxLogin(request, mobile, identifyCode,openId,nickName,sex);
    }

    @RequestMapping("/getBalance")
    @ResponseBody
    @ApiOperation(value = "获取用户余额", httpMethod = "POST")
    public AjaxList getBalance(@ApiParam(name = "openId",value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                               HttpServletRequest request) throws Exception {
        //查询是否在微信注册过
        Map<String,Object> map = MapUtil.buildMap();
        map.put("openId",openId);
        List<UserPassengerDto> users = this.passengerService.list(map);
        //已经有绑定手机的情况，就返回用户信息，并且更新token
        if(null != users && users.size() > 0){
            return AjaxList.createSuccess("获取用户余额",users.get(0).getBalance());
        }
        return AjaxList.createError("查无用户信息");
    }


}