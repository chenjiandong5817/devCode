package com.plugs.module_wechat.api;

import com.plugs.base.AjaxList;
import com.plugs.base.BaseExtendApi;
import com.plugs.module_order.dtos.OrderEvaluateDto;
import com.plugs.module_order.services.OrderEvaluateService;
import com.plugs.module_system.dtos.SysTagDto;
import com.plugs.module_system.services.SysTagService;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.util.MapUtil;
import com.util.StringUtil;
import com.util.Validate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/31.
 */
@Controller
@RequestMapping("/wechat/orderEvaluate")
@Api(value = "微信订单评价接口", description = "微信订单评价接口")
public class WechatOrderEvaluateApi extends BaseExtendApi {

    @Autowired
    private OrderEvaluateService orderEvaluateService;
    @Autowired
    private SysTagService sysTagService;


    /**
     * 评价标签列表
     */
    @RequestMapping(value = "/tagList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取系统标签列表", notes = "获取系统标签列表（star（1-5）代表星级，客户端可以根据这个做展示）", httpMethod = "GET")
    public AjaxList list() {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("tagStatus",1); //启用
        params.put("areaUuid","-1"); //todo:以默认为主
        List<SysTagDto> tags = this.sysTagService.list(params);
        return AjaxList.createSuccess(tags.size(), "获取成功", tags);
    }


    @RequestMapping(value = "/addEvaluate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加订单评价", notes = "添加订单评价的接口信息", httpMethod = "POST")
    public AjaxList add(HttpServletRequest request,
            @ApiParam(name = "openId", required = true, value = "用户登录令牌") @RequestParam String openId,
            @ApiParam(name = "orderUuid", required = true, value = "订单(行程)UUID") @RequestParam String orderUuid,
            @ApiParam(name = "driverUuid", required = true, value = "司机UUID") @RequestParam String driverUuid,
            @ApiParam(name = "evaluateScore", required = true, value = "评分") @RequestParam Double evaluateScore,
            @ApiParam(name = "evaluateContent", value = "评价内容") @RequestParam(required = false) String evaluateContent,
            @ApiParam(name = "evaluateTag", value = "评价标签，用逗号分隔") @RequestParam(required = false) String evaluateTag) throws Exception {

        Validate.notEmpty("openId", "登录令牌不能为空");
        Validate.notEmpty("orderUuid", "订单ID不能为空");
        Validate.notEmpty("driverUuid", "司机ID不能为空");
        Validate.notNull("evaluateScore", "评价分数不能为空");

        UserPassengerDto userPassengerDto = super.getUserByOpenId(request,openId);

        OrderEvaluateDto orderEvaluate = new OrderEvaluateDto();
        orderEvaluate.setUuid(StringUtil.buildUUID());
        orderEvaluate.setOrderUuid(orderUuid);
        orderEvaluate.setDriverUuid(driverUuid);
        orderEvaluate.setEvaluateScore(evaluateScore);
        orderEvaluate.setEvaluateContent(evaluateContent);
        orderEvaluate.setEvaluateTag(evaluateTag);
        orderEvaluate.setPassengerUuid(userPassengerDto.getUuid());
        orderEvaluate.setPassengerMobile(userPassengerDto.getMobile());
        orderEvaluate.setPassengerName(userPassengerDto.getNickname());
        orderEvaluate.setCreator(userPassengerDto.getUuid());
        orderEvaluate.setCreateTime(new Date());
        return this.orderEvaluateService.saveEvaluate(orderEvaluate);
    }

}
