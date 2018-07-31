package com.plugs.module_order.mappers;

import com.base.IMapper;
import com.plugs.module_order.dtos.OrderConfigDto;

public interface OrderConfigMapper<T extends OrderConfigDto> extends IMapper<T> {

    OrderConfigDto selInfoByOrderType(int orderType);

}
