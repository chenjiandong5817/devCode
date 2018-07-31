package com.plugs.module_order.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.IMapper;
import com.base.BaseServiceSupport;
import com.plugs.module_order.dtos.OrderCostDetailDto;
import com.plugs.module_order.mappers.OrderCostDetailMapper;


@Service
public class OrderCostDetailService extends BaseServiceSupport<OrderCostDetailDto> {
    @Autowired
    private OrderCostDetailMapper<OrderCostDetailDto> orderCostDetailMapper;

    @Override
    public IMapper<OrderCostDetailDto> getMapper() {
        return orderCostDetailMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }


}
