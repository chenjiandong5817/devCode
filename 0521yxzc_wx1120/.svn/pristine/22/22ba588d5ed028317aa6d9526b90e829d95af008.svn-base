package com.plugs.module_system.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_system.dtos.SysAreaDto;
import com.plugs.module_system.mappers.SysAreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysAreaService extends BaseServiceSupport<SysAreaDto>{
	@Autowired
	private SysAreaMapper<SysAreaDto> sysAreaMapper;

	@Override
	public IMapper<SysAreaDto> getMapper() {
		return sysAreaMapper;
	}

	@Override
	public String getPK() {
		return "uuid";
	}

	public String getAreaByCityName(Map<String, Object> params) {
		return this.sysAreaMapper.getAreaByCityName(params);
	}

}
