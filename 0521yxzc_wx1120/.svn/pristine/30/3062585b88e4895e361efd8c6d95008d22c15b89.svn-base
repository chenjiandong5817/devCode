package com.plugs.module_enterprise.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_enterprise.dtos.EntAccountDetailDto;
import com.plugs.module_enterprise.mappers.EntAccountDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EntAccountDetailService extends BaseServiceSupport<EntAccountDetailDto> {
    @Autowired
    private EntAccountDetailMapper<EntAccountDetailDto> entAccountDetailMapper;

    @Override
    public IMapper<EntAccountDetailDto> getMapper() {
        return entAccountDetailMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

    public double sum(Map<String, Object> params) {
        return entAccountDetailMapper.sum(params);
    }

    public double sumDepartmentThisMonth(Map<String, Object> params) {
        return entAccountDetailMapper.sumDepartmentThisMonth(params);
    }


}
