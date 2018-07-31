package com.plugs.module_order.mappers;

import com.base.IMapper;
import com.plugs.module_order.dtos.OrderDto;

import java.util.Map;

public interface OrderMapper<T extends OrderDto> extends IMapper<T> {


    int cancelBatchByGroupUuid(Map<String, Object> params);

    void batchEdit(Map<String, Object> params);

}
