package com.plugs.module_user.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserPassengerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UserPassengerService extends BaseServiceSupport<UserPassengerDto> {
    @Autowired
    private UserPassengerMapper<UserPassengerDto> userPassengerMapper;


    @Override
    public IMapper<UserPassengerDto> getMapper() {
        return userPassengerMapper;
    }

    public UserPassengerDto findUser(Map<String, Object> params) {
        return userPassengerMapper.findUser(params);
    }

    @Override
    public String getPK() {
        return "uuid";
    }


}
