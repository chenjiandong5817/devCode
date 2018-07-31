package com.plugs.module_system.mappers;

import com.base.IMapper;
import com.plugs.module_system.dtos.SysAreaDto;

import java.util.Map;


public interface SysAreaMapper<T extends SysAreaDto> extends IMapper<T>{

    String getAreaByCityName(Map<String, Object> params);
}
