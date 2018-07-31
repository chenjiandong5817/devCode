package com.plugs.module_system.mappers;

import com.base.IMapper;
import com.plugs.module_system.dtos.SysIdentifyDto;

import java.util.Map;

public interface SysIdentifyMapper<T extends SysIdentifyDto> extends IMapper<T>{
    int update(Map<String,Object> params);
}
