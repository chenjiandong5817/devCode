package com.plugs.module_user.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_user.dtos.UserAccountDetailDto;
import com.plugs.module_user.mappers.UserAccountDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountDetailService extends BaseServiceSupport<UserAccountDetailDto> {

    @Autowired
    private UserAccountDetailMapper<UserAccountDetailDto> userAccountDetailMapper;//企业账号明细

    @Override
    public IMapper<UserAccountDetailDto> getMapper() {
        return userAccountDetailMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }


}
