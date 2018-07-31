package com.plugs.module_enterprise.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.plugs.module_enterprise.dtos.EntAccountDto;
import com.plugs.module_enterprise.mappers.EntAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EntAccountService extends BaseServiceSupport<EntAccountDto> {
    @Autowired
    private EntAccountMapper<EntAccountDto> userEntAccountMapper;


    @Override
    public IMapper<EntAccountDto> getMapper() {
        return userEntAccountMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

}

