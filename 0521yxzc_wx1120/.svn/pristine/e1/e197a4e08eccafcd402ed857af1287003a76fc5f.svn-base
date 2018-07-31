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

package com.plugs.module_order.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_order.dtos.OrderExpandDto;
import com.plugs.module_order.mappers.OrderExpandMapper;
import com.util.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service
public class OrderExpandService extends BaseServiceSupport<OrderExpandDto>{
	@Autowired
	private OrderExpandMapper<OrderExpandDto> orderExpandMapper;

	@Override
	public IMapper<OrderExpandDto> getMapper() {
		return orderExpandMapper;
	}

	@Override
	public String getPK() {
		return "uuid";
	}

	/**
	 * 设置订单静态数据
	 */
	public void setOrderStaticData(String driverUuid, String orderUuid) {
		OrderExpandDto orderExpandDto = new OrderExpandDto();
		if(StringUtils.isNotBlank(orderUuid)){
			orderExpandDto.setOrderUuid(orderUuid);
			orderExpandDto.setStatus(-1);
			this.edit(orderExpandDto);
			Map<String, Object> map = null;
			if(StringUtils.isNotBlank(driverUuid)){
				map = orderExpandMapper.queryDriverInfo(driverUuid);
				if(null != map) {
					orderExpandDto.setDriverUuid(driverUuid);
					orderExpandDto.setDriverName(MapUtils.getString(map, "DRIVERNAME"));
					orderExpandDto.setDriverPhone(MapUtils.getString(map, "DRIVERPHONE"));
					orderExpandDto.setAreaUuid(MapUtils.getString(map, "AREAUUID"));
					orderExpandDto.setAreaName(MapUtils.getString(map, "AREANAME"));
					orderExpandDto.setCarTeamUuid(MapUtils.getString(map, "CARTEAMUUID"));
					orderExpandDto.setCarTeamName(MapUtils.getString(map, "CARTEAMNAME"));
					orderExpandDto.setVehicleNo(MapUtils.getString(map, "VEHICLENO"));
					orderExpandDto.setVehicleType(MapUtils.getInteger(map, "VEHICLETYPE"));
				}
			}else {
				map = orderExpandMapper.queryAreaInfo(orderUuid);
				if(null != map) {
					orderExpandDto.setAreaUuid(MapUtils.getString(map, "AREAUUID"));
					orderExpandDto.setAreaName(MapUtils.getString(map, "AREANAME"));
				}
			}
			orderExpandDto.setUuid(StringUtil.buildUUID());
			orderExpandDto.setCreateTime(new Date());
			orderExpandDto.setStatus(0);
			this.add(orderExpandDto);
		}
	}
}
