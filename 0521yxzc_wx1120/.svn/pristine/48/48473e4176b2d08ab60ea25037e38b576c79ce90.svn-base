package com.plugs.module_driver.mappers;


import com.base.IMapper;
import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_order.pojo.NotServiceDriverInfo;

import java.util.List;
import java.util.Map;

public interface DriverMapper<T extends DriverDto> extends IMapper<T> {


    DriverDto findDriver(Map<String, Object> params);


    List<String> suiteConditionUuidList(Map<String, Object> params);

    List<String> priorityPushUuidList(Map<String, Object> params);


    List<NotServiceDriverInfo> notServiceDriverUuidByTimeAndDistanceLog(Map<String, Object> stringObjectMap);
}
