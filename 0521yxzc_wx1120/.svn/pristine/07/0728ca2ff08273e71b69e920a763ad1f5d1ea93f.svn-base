package com.plugs.module_driver.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_driver.dtos.DriverAccountDetailDto;
import com.plugs.module_driver.mappers.DriverAccountDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverAccountDetailService extends BaseServiceSupport<DriverAccountDetailDto> {

    @Autowired
    private DriverAccountDetailMapper<DriverAccountDetailDto> driverAccountDetailMapper;

    @Override
    public IMapper<DriverAccountDetailDto> getMapper() {
        return driverAccountDetailMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }


}
