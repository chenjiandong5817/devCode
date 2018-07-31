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

package com.plugs.module_user.mappers;

import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.plugs.module_user.dtos.UserElectronInvoiceDto;

import java.util.Map;

public interface UserElectronInvoiceMapper<T extends UserElectronInvoiceDto> extends IMapper<T> {

    double sum(Map<String, Object> params);

    PageList pageList(Map params, PageBounds pageBounds);
}
