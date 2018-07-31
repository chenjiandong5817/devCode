package com.plugs.module_driver.mappers;

import com.base.IMapper;
import com.plugs.module_driver.dtos.DriverQueueDto;

import java.util.List;
import java.util.Map;

public interface DriverQueueMapper<T extends DriverQueueDto> extends IMapper<T> {

    /**
     * 根据 Gid与车型与预约时间 获取队列中司机信息
     */
    List<DriverQueueDto> getDriverFromQuequeByGid(Map<String, Object> params);
}
