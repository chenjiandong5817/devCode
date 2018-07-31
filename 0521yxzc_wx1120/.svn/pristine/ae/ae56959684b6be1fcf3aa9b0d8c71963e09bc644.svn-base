package com.plugs.module_driver.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_driver.mappers.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DriverService extends BaseServiceSupport<DriverDto> {


    @Autowired
    private DriverMapper<DriverDto> driverMapper;

    @Override
    public IMapper<DriverDto> getMapper() {
        return driverMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

    public DriverDto findDriver(Map<String, Object> params) {
        return driverMapper.findDriver(params);
    }

}
