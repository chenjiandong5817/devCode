package com.plugs.module_order.mappers;

import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.plugs.module_order.dtos.OrderEvaluateDto;

import java.util.Map;

public interface OrderEvaluateMapper<T extends OrderEvaluateDto> extends IMapper<T> {

     Double myAvgScore(Map<String, Object> params);


     PageList findList(Map params, PageBounds pageBounds);
}
