package com.plugs.module_enterprise.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.plugs.module_enterprise.dtos.EntAccountRoleDto;
import com.plugs.module_enterprise.mappers.EntAccountRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EntAccountRoleService extends BaseServiceSupport<EntAccountRoleDto>{
	@Autowired
	private EntAccountRoleMapper<EntAccountRoleDto> entAccountRoleMapper;

	@Override
	public IMapper<EntAccountRoleDto> getMapper() {
		return entAccountRoleMapper;
	}

	@Override
	public String getPK() {
		return "uuid";
	}

	/**
	 * 是否企业叫车负责人
	 *
	 * @param userPassengerUuid
	 * @return
	 */
	public String isEntResponsiblePerson(String userPassengerUuid) {
		return entAccountRoleMapper.isEntResponsiblePerson(userPassengerUuid);
	}

}
