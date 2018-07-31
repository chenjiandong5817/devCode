package com.plugs.module_wechat.api;

import com.plugs.base.AjaxList;
import com.plugs.module_system.dtos.SysAreaDto;
import com.plugs.module_system.services.SysAreaConfigService;
import com.plugs.module_system.services.SysAreaService;
import com.util.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 系统区域获取接口
 *
 * @outhor qfHan
 * @create 2017-10-10 10:17
 */
@Controller
@RequestMapping("/wechat/sysarea")
@Api(value = "系统区域获取接口", description = "系统区域获取接口")
public class WeChatSysAreaApi {

    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SysAreaConfigService sysAreaConfigService;

    /**
     * 活动列表
     *
     * @return
     */
    @RequestMapping(value = "/opened.arport", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "区域列表", notes = "获取区域列表的接口信息", httpMethod = "GET")
    public AjaxList list() throws Exception {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("status", 1);
        List<SysAreaDto> areas = this.sysAreaService.list(params);
        for (SysAreaDto area : areas) {
            params.clear();
            params.put("areaUuid", area.getUuid());
            area.setSysAreaConfigDto(sysAreaConfigService.selInfo(params));
        }
        return AjaxList.createSuccess("获取成功", areas);
    }




}
