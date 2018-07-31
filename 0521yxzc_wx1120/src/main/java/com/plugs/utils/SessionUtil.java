package com.plugs.utils;

import com.plugs.constants.ConfigConstants;
import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_user.dtos.UserPassengerDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Zhouhy on 2016/9/20.
 */
public class SessionUtil {
    /**
     * 乘客
     * @param request
     * @return
     */
    public static UserPassengerDto getUserPassenger(HttpServletRequest request) {
        return (UserPassengerDto) getSession(request).getAttribute(ConfigConstants.SESSION_KEY_PASSENGER_USER);
    }
    /**
     * 司机
     * @param request
     * @return
     */
    public static DriverDto getUserDriver(HttpServletRequest request) {
        return (DriverDto) getSession(request).getAttribute(ConfigConstants.SESSION_KEY_DRIVER_USER);
    }

    public static HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }
}
