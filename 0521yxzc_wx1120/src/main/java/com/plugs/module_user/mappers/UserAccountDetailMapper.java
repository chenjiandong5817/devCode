package com.plugs.module_user.mappers;

import com.base.IMapper;
import com.plugs.module_user.dtos.UserAccountDetailDto;

import java.util.Map;

public interface UserAccountDetailMapper<T extends UserAccountDetailDto> extends IMapper<T> {

    UserAccountDetailDto selInfoBySerialNumber(String serialNumber);

    double sum(Map<String, Object> params);

}
