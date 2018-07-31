package com.plugs.module_system.services;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.IMapper;
import com.base.BaseServiceSupport;
import com.plugs.module_system.dtos.SysOpLogDto;
import com.plugs.module_system.mappers.SysOpLogMapper;

import java.util.Map;

@Service
public class SysOpLogService extends BaseServiceSupport<SysOpLogDto>{
	@Autowired
	private SysOpLogMapper<SysOpLogDto> sysOpLogMapper;

	@Override
	public IMapper<SysOpLogDto> getMapper() {
		return sysOpLogMapper;
	}

	@Override
	public String getPK() {
		return "uuid";
	}


	/**
	 * 列表
	 *
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList findList(Map params, int pageNum, int pageSize) {
		PageBounds pageBounds = new PageBounds(pageNum, pageSize);
		return this.sysOpLogMapper.findList(params, pageBounds);
	}
}
