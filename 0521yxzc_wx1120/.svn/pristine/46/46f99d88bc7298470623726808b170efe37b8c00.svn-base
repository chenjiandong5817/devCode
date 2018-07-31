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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
@RequestMapping("/api/base")
@Api(value = "基础数据", description = "基础数据")
public class SysBaseDataApi {

    @Autowired
    private SysCarBillingWayService billingWayService;

    @Autowired
    private SysCityService sysCityService;
    @Autowired
    private SysAreaConfigService sysAreaConfigService;

    /**
     * 计价规则
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/priceRule")
    @ApiOperation(value = "计价规则", httpMethod = "GET", response = AjaxList.class, notes = "计价规则")
    public String priceRule(HttpServletRequest request, Model model) {
        List<SysCarBillingWayDto> list = this.billingWayService.list(null);
        model.addAttribute("data", list);
        return "/system/h5Page/priceRule";
    }

    /**
     * app下载页
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/appDownload")
    @ApiOperation(value = "app下载页", httpMethod = "GET", response = AjaxList.class, notes = "app下载页")
    public String appDownload(HttpServletRequest request, Model model) {
        return "/system/h5Page/appDownload";
    }

    /**
     * 用户协议
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/userAgreement")
    @ApiOperation(value = "用户协议", httpMethod = "GET", response = AjaxList.class, notes = "用户协议")
    public String userAgreement(HttpServletRequest request, Model model) {
        return "/system/h5Page/userAgreement";
    }


    /**
     * 微信叫车获取车型
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCarModels")
    @ResponseBody
    @ApiOperation(value = "微信获取车型列表", httpMethod = "GET", response = AjaxList.class, notes = "微信获取车型列表")
    public AjaxList getCarModels(HttpServletRequest request,
                                 @ApiParam(name = "areaUuid", required = true, value = "起点经度") @RequestParam String areaUuid) throws Exception {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("areaUuid", areaUuid);
        List<SysAreaConfigDto> areaConfigDtos = this.sysAreaConfigService.list(params);

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> model1 = new HashMap<String, Object>();
        model1.put("modelNum", 1);
        model1.put("picNum", 3);
        model1.put("modelName", "时尚型");
        model1.put("terminalPic", "http://59.60.12.62:8765/pub/images/car/car_type_1.png");
        model1.put("status", 1);

        Map<String, Object> model2 = new HashMap<String, Object>();
        model2.put("modelNum", 2);
        model2.put("picNum", 2);
        model2.put("modelName", "经济七座");
        model2.put("terminalPic", "http://59.60.12.62:8765/pub/images/car/car_type_2.png");
        model2.put("status", 1);

        Map<String, Object> model3 = new HashMap<String, Object>();

        model3.put("modelNum", 3);
        model3.put("picNum", 3);
        model3.put("terminalPic", "http://59.60.12.62:8765/pub/images/car/car_type_3.png");
        model3.put("modelName", "豪华商务");
        model3.put("status", 1);

        if (areaConfigDtos != null && areaConfigDtos.size() > 0) {
            SysAreaConfigDto dto = areaConfigDtos.get(0);
            if (dto.getLevelType1Status() == 1) { //时尚型
                result.add(model1);
            }
            if (dto.getLevelType2Status() == 1) { //经济七座
                result.add(model2);
            }
            if (dto.getLevelType3Status() == 1) { //豪华型
                result.add(model3);
            }
        }
        return AjaxList.createSuccess("获取成功", result);
    }



    @RequestMapping("/getLocations")
    @ResponseBody
    @ApiOperation(value = "微信叫车获取起终点地址", httpMethod = "GET", response = AjaxList.class, notes = "微信叫车获取起终点地址")
    public AjaxList getLocations(@ApiParam(value = "cityName", name = "areaUuid") @RequestParam(required = true) String areaUuid,
                               HttpServletRequest request) throws Exception {
        return AjaxList.createSuccess("获取成功", ConfigConstants.getLocations(areaUuid));
    }


    @RequestMapping("/creWXMenu")
    @ResponseBody
    @ApiOperation(value = "微信创建菜单", httpMethod = "GET", response = AjaxList.class, notes = "微信创建菜单")
    public AjaxList creWXMenu(HttpServletRequest request) throws Exception {
        AccessToken at = WeixinUtil.getAccessToken(Constants.APPID, Constants.APPSECRET);
        if (null != at) {
            int result = WeixinUtil.createMenu(WeChatMenuApi.initMenu(), at.getToken());
            if (0 == result) {
                System.out.println("菜单创建成功");
                return AjaxList.createSuccess("菜单创建成功");
            }
            System.out.println("菜单创建失败，错误码：" + result);
            return AjaxList.createError("菜单创建失败");
        }
        return AjaxList.createError("无法获取"+at);
    }



    @RequestMapping(value = "/openedCity", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取已开通城市", notes = "获取已开通城市的接口", httpMethod = "GET")
    public AjaxList opened() {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("status", 1);
        params.put("sortField","updateTime");//按更新时间（开通时间）
        params.put("sortOrder","DESC");//降序
        List<SysCityDto> citys = this.sysCityService.list(params);
        for (SysCityDto city : citys) {
            String regEx = "市";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(city.getName());
            city.setName(m.replaceAll("").trim());
        }
        return AjaxList.createSuccess(citys.size(), "获取成功", citys);
    }


    @RequestMapping(value = "/testMSG", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "testMSG", notes = "testMSG", httpMethod = "GET")
    public AjaxList testMSG(HttpServletRequest request) throws Exception {
        String jsonDate = TemplateData.New()
                .setTouser("od_i0wDNv9xoxbq65xj5INZjARiw")
                .setTemplate_id("GmW5YsUrED7zW6H466vLSW3z5_VU5jedNdAXitNDzeA")
                .setUrl("wxtest.yxzc01.com/wechat/view/index")
                .add("first","司机已到达","#ddd")
                .add("keyword1","厦门站","#000")
                .add("keyword2","小贱人","#000")
                .add("keyword3","1775950001","#000")
                .add("keyword4","闽D001","#000")
                .add("keyword5","保时捷","#000")
                .add("remark","测试","#000")
                .build();

        AccessToken accessToken = WeChatUtil.getAccessToken(Constants.APPID, Constants.APPSECRET);
        System.out.println(TemplateMsgHelper.send(jsonDate, accessToken.getToken()));
        return AjaxList.createSuccess("XX",TemplateMsgHelper.send(jsonDate, accessToken.getToken()));
    }




}
