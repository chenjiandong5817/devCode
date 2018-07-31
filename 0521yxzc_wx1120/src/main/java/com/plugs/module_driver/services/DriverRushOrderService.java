package com.plugs.module_driver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.IMapper;
import com.base.BaseServiceSupport;
import com.plugs.module_driver.dtos.DriverRushOrderDto;
import com.plugs.module_driver.mappers.DriverRushOrderMapper;

@Service
public class DriverRushOrderService extends BaseServiceSupport<DriverRushOrderDto> {
    @Autowired
    private DriverRushOrderMapper<DriverRushOrderDto> driverRushOrderMapper;

    @Override
    public IMapper<DriverRushOrderDto> getMapper() {
        return driverRushOrderMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

}
