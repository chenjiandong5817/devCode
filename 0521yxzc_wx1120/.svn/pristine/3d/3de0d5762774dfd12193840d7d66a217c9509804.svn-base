/*
 * SummerSoft  YueYue-Travel Platform
 * <p>
 * Copyright (c) 2017-2018  SummerSoft Technology (Xiamen) Co.,LTD
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of SummerSoft
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SummerSoft.
 * <p>
 */

package com.plugs.module_system.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_system.dtos.SysAreaConfigDto;
import com.plugs.module_system.mappers.SysAreaConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysAreaConfigService extends BaseServiceSupport<SysAreaConfigDto> {
	@Autowired
	private SysAreaConfigMapper<SysAreaConfigDto> sysAreaConfigMapper;

	@Override
	public IMapper<SysAreaConfigDto> getMapper() {
		return sysAreaConfigMapper;
	}

	@Override
	public String getPK() {
		return "uuid";
	}

}
