package com.plugs.module_order.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.mappers.OrderMapper;
import com.plugs.module_system.services.SysAreaService;
import com.plugs.utils.*;
import com.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * OrderHandle
 *
 * @author Zoro
 * @date 2016/11/15
 */
@Service
public class OrderHandler extends BaseServiceSupport<OrderDto> {

    @Autowired
    private OrderMapper<OrderDto> orderMapper;
    @Autowired
    private SysAreaService sysAreaService;

    @Override
    public IMapper<OrderDto> getMapper() {
        return orderMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }


    /**
     * 区域订单检测
     * @param areaUuid 区域UUID
     */
    public boolean checkOrgCity(String areaUuid) {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", areaUuid);
        params.put("status", 1);
        int count = this.sysAreaService.count(params);
        return count > 0 ? false : true;
    }

}
