package com.plugs.module_enterprise.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_enterprise.dtos.EntAccountDepartmentDto;
import com.plugs.module_enterprise.mappers.EntAccountDepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EntAccountDepartmentService extends BaseServiceSupport<EntAccountDepartmentDto>{
	@Autowired
	private EntAccountDepartmentMapper<EntAccountDepartmentDto> userEntAccountDepartmentMapper;

	@Override
	public IMapper<EntAccountDepartmentDto> getMapper() {
		return userEntAccountDepartmentMapper;
	}

	@Override
	public String getPK() {
		return "uuid";
	}


}
