package com.plugs.module_system.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_system.dtos.SysCouponDto;
import com.plugs.module_system.mappers.SysCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysCouponService extends BaseServiceSupport<SysCouponDto>{
	@Autowired
	private SysCouponMapper<SysCouponDto> sysCouponMapper;

	@Override
	public IMapper<SysCouponDto> getMapper() {
		return sysCouponMapper;
	}

	@Override
	public String getPK() {
		return "uuid";
	}

}
