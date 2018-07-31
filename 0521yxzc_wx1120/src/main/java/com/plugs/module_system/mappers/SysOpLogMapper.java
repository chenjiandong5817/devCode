package com.plugs.module_system.mappers;

import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.plugs.module_system.dtos.SysOpLogDto;

import java.util.Map;

public interface SysOpLogMapper <T extends SysOpLogDto> extends IMapper<T>{

    PageList findList(Map params, PageBounds pageBounds);
}
