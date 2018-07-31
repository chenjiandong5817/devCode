package com.plugs.module_user.mappers;

import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.plugs.module_user.dtos.UserHistoryInvoiceDto;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 开票历史
 *
 * @outhor qfHan
 * @create 2018-01-31 11:00
 */
public interface UserHistoryInvoiceMapper<T extends UserHistoryInvoiceDto> extends IMapper<T> {

    List<String> getInvoiceUuids(Map<String, Object> params, PageBounds pageBounds);

    List<UserHistoryInvoiceDto> historyInvoiceDtos(Map<String, Object> params);

    double sum(Map<String, Object> params);
}