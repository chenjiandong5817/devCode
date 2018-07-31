package com.plugs.module_system.services;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import com.plugs.utils.StringUtils;
import com.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.IMapper;
import com.base.BaseServiceSupport;
import com.plugs.module_system.dtos.SysCarBillingWayDto;
import com.plugs.module_system.mappers.SysCarBillingWayMapper;

import java.util.Map;

@Service
public class SysCarBillingWayService extends BaseServiceSupport<SysCarBillingWayDto> {
    @Autowired
    private SysCarBillingWayMapper<SysCarBillingWayDto> sysCarBillingWayMapper;

    @Override
    public IMapper<SysCarBillingWayDto> getMapper() {
        return sysCarBillingWayMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

    public SysCarBillingWayDto selInfoByLevelTypeAndOrderType(Map<String, Object> params) {
        return sysCarBillingWayMapper.selInfoByLevelTypeAndOrderType(params);
    }

    public int update(Map<String, Object> params) {
        return sysCarBillingWayMapper.update(params);
    }

    public int delete(Map<String, Object> params) {
        return sysCarBillingWayMapper.delete(params);
    }

    public PageList queryPageApi(Map<String, Object> params, int pageNum, int pageSize) {
        PageBounds pageBounds = new PageBounds(pageNum, pageSize);
        return sysCarBillingWayMapper.queryPageApi(params, pageBounds);
    }


    public SysCarBillingWayDto selInfoByLevelTypeAndOrderType(Integer levelType, Integer orderType) {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("levelType", levelType);
        params.put("orderType", orderType);
        return sysCarBillingWayMapper.selInfoByLevelTypeAndOrderType(params);//计费方式
    }


    public SysCarBillingWayDto findByLevelTypeAndOrderType(int levelType, int orderType, String areaUuid) {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("levelType", levelType);
        params.put("orderType", orderType);
        params.put("areaUuid", StringUtils.isEmpty(areaUuid) ? "-1" : areaUuid); //区域UUID为空则取 -1
        return this.sysCarBillingWayMapper.selInfoByLevelTypeAndOrderType(params);//计费方式
    }


}
