package com.plugs.module_system.mappers;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import com.base.IMapper;
import com.plugs.module_system.dtos.SysCarBillingWayDto;

import java.util.Map;

public interface SysCarBillingWayMapper <T extends SysCarBillingWayDto> extends IMapper<T>{

    int update(Map<String,Object> params);

    int delete(Map<String,Object> params);

    PageList queryPageApi(Map<String, Object> params, PageBounds pageBounds);

    SysCarBillingWayDto selInfoByLevelTypeAndOrderType(Map<String,Object> params);

}
