package com.plugs.module_system.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_system.dtos.SysCityDto;
import com.plugs.module_system.mappers.SysCityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysCityService extends BaseServiceSupport<SysCityDto>{
	@Autowired
	private SysCityMapper<SysCityDto> sysCityMapper;

	@Override
	public IMapper<SysCityDto> getMapper() {
		return sysCityMapper;
	}

	@Override
	public String getPK() {
		return "uuid";
	}

	public List<SysCityDto> findList() {
		return this.sysCityMapper.findList();
	}
}

