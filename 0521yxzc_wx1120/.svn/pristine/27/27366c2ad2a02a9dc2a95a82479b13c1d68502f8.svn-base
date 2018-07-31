package com.plugs.module_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.IMapper;
import com.base.BaseServiceSupport;
import com.plugs.module_system.dtos.SysTagDto;
import com.plugs.module_system.mappers.SysTagMapper;


@Service
public class SysTagService extends BaseServiceSupport<SysTagDto>{
	@Autowired
	private SysTagMapper<SysTagDto> sysTagMapper;

	@Override
	public IMapper<SysTagDto> getMapper() {
		return sysTagMapper;
	}

	@Override
	public String getPK() {
		return "uuid";
	}

}
