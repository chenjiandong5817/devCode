package com.plugs.module_user.mappers;

import com.base.IMapper;
import com.plugs.module_user.dtos.UserPassengerDto;

import java.util.Map;

public interface UserPassengerMapper<T extends UserPassengerDto> extends IMapper<T> {

    UserPassengerDto login(Map<String, Object> params);

    int update(Map<String, Object> params);

    UserPassengerDto findUser(Map<String, Object> params);

    int countUnfinishedOrderByPassengerUuidAndDeparTime(Map<String, Object> params);

}
