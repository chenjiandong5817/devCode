package com.plugs.module_enterprise.mappers;

import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.plugs.module_enterprise.dtos.EntAccountDetailDto;

import java.util.Map;

public interface EntAccountDetailMapper<T extends EntAccountDetailDto> extends IMapper<T> {

    double sum(Map<String, Object> params);

    double sumDepartmentThisMonth(Map<String, Object> params);


}
