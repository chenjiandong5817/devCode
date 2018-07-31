package com.plugs.module_system.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_system.mappers.SysSeqMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysSeqService extends BaseServiceSupport<Long> {
    @Autowired
    private SysSeqMapper<Long> sysSeqMapper;

    @Override
    public IMapper<Long> getMapper() {
        return sysSeqMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

    public long getSerialSeqNextVal() {
        return sysSeqMapper.getSerialSeqNextVal();

    }

    public long getOrderSeqNextVal() {
        return sysSeqMapper.getOrderSeqNextVal();

    }

}
