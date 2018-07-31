package com.plugs.module_wechat.services;

import com.plugs.base.AjaxList;
import com.plugs.constants.ConfigConstants;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_system.dtos.SysCouponDto;
import com.plugs.module_system.dtos.SysIdentifyDto;
import com.plugs.module_system.mappers.SysCouponMapper;
import com.plugs.module_system.mappers.SysIdentifyMapper;
import com.plugs.module_user.dtos.UserCouponDto;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserCouponMapper;
import com.plugs.module_user.mappers.UserPassengerMapper;
import com.plugs.utils.CommonUtils;
import com.plugs.utils.GuoDouSmsUtils;
import com.util.MapUtil;
import com.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/22.
 */
@Service
public class WechatPassengerService {


    @Autowired
    private UserPassengerMapper<UserPassengerDto> userPassengerMapper;
    @Autowired
    private SysIdentifyMapper<SysIdentifyDto> sysIdentifyMapper;
    @Autowired
    private SysCouponMapper<SysCouponDto> sysCouponMapper;
    @Autowired
    private UserCouponMapper<UserCouponDto> userCouponMapper;


    public AjaxList sendWxCode(String mobile) throws Exception {
        Map params = MapUtil.buildMap();

        params.put("mobile", mobile);
        params.put("status", 3);
        params.put("codeType", 1);
        this.sysIdentifyMapper.update(params);

        String code = CommonUtils.getIdentifyCode();
        SysIdentifyDto identifyDto = new SysIdentifyDto();
        identifyDto.setIdentifyCode(code);
        identifyDto.setMobile(mobile);
        identifyDto.setCodeType(1);
        identifyDto.setUuid(StringUtil.buildUUID());
        identifyDto.setStatus(1);
        identifyDto.setSendType(1);
        identifyDto.setCreateTime(new Date());
        int resultCode = this.sysIdentifyMapper.add(identifyDto);
        if (resultCode > 0) {
            String result = GuoDouSmsUtils.sendIdentifySmsUtf8(mobile, code);
            if (result.startsWith(GuoDouSmsUtils.RET_CODE)) {
                return AjaxList.createSuccess("发送成功");
            } else {
                this.sysIdentifyMapper.del(identifyDto);
            }
        }
        return AjaxList.createError("发送失败");
    }


    /***
     * @param mobile       手机号
     * @param identifyCode 验证码
     * @param openId       openId
     * @return
     */
    public AjaxList wxLogin(HttpServletRequest request, String mobile, String identifyCode, String openId, String nickName, int sex) throws ParseException {

        Map<String, Object> params = MapUtil.buildMap();
        params.put("mobile", mobile);
        params.put("identifyCode", identifyCode);
        params.put("sendType", 1);
        params.put("codeType", 1);
        params.put("status", 1);
        List<SysIdentifyDto> identifyDtos = this.sysIdentifyMapper.list(params);
        if (null == identifyDtos || identifyDtos.size() == 0) {
            return AjaxList.createError("验证码错误");
        }
        long idenLongTime = identifyDtos.get(0).getCreateTime().getTime();
        if ((System.currentTimeMillis() - idenLongTime) > 1000 * 60 * 5) {
            return AjaxList.createError("验证码已过期");
        }

        //openId,手机号验证唯一性
        params.clear();
        params.put("openId", openId);
        List<UserPassengerDto> users = this.userPassengerMapper.list(params);
        if (null != users && users.size() > 0) {
            if (!mobile.equals(users.get(0).getMobile())) {
                return AjaxList.createError("该微信号已绑定过手机号");
            }
        }

        params.clear();
        params.put("mobile", mobile);
        UserPassengerDto user = this.userPassengerMapper.login(params);
        if (null != user) {
            if (null != user.getOpenId() && !openId.equals(user.getOpenId())) {
                return AjaxList.createError("该手机号已绑定过该公众号");
            }

            params.clear();
            params.put("openId", openId);
            params.put("uuid", user.getUuid());
            params.put("lastLogin", new Date());
            params.put("updateTime", new Date());
            int count = this.userPassengerMapper.update(params);
            if (count <= 0){
                return AjaxList.createError("登录失败");
            }
            user.setOpenId(openId);
        } else {
            user = new UserPassengerDto();
            Date nowDate = new Date();
            user.setNickname(nickName);
            user.setUuid(StringUtil.buildUUID());
            user.setMobile(mobile);
            user.setSex(sex);
            user.setStatus(1);
            user.setLastLogin(nowDate);
            user.setCreateTime(nowDate);
            user.setUpdateTime(nowDate);
            user.setOpenId(openId);
            this.userPassengerMapper.add(user);

            //判断优惠券
            params.clear();
            params.put("regTime", user.getCreateTime());//注册时间
            List<SysCouponDto> sysCouponDtos = this.sysCouponMapper.list(params);
            if (sysCouponDtos != null && sysCouponDtos.size() > 0) {
                for (SysCouponDto sysCouponDto : sysCouponDtos) {
                    if (sysCouponDto.getStatus() == SysCouponDto.STATUS_ENABLE && sysCouponDto.getRemainNumber() > 0) {
                        //发放给用户
                        UserCouponDto userCoupon = new UserCouponDto();
                        userCoupon.setCreateTime(nowDate);
                        userCoupon.setStatus(UserCouponDto.STATUS_VALID);
                        userCoupon.setUuid(StringUtil.buildUUID());
                        userCoupon.setSysCouponUuid(sysCouponDto.getUuid());
                        userCoupon.setUserUuid(user.getUuid());
                        this.userCouponMapper.add(userCoupon);

                        //减少优惠券剩余
                        SysCouponDto updSyscoCoupon = new SysCouponDto();
                        updSyscoCoupon.setUuid(sysCouponDto.getUuid());
                        updSyscoCoupon.setDecRemainNumber(1);//剩余数量减去1
                        this.sysCouponMapper.edit(updSyscoCoupon);
                    }
                }
            }
        }
        //更新验证码
        updateStatus(mobile, identifyCode, 1, 1, 2);
        //放微信用户信息至session
        request.getSession().setAttribute(ConfigConstants.SESSION_KEY_PASSENGER_USER_WX, user);
        return AjaxList.createSuccess("登录成功", user.toMap());
    }



    private int updateStatus(String mobile, String identifyCode, int codeType, int sendType, int status) {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("mobile", mobile);
        params.put("identifyCode", identifyCode);
        params.put("sendType", sendType);
        params.put("codeType", codeType);
        params.put("status", status);
        params.put("oldStatus", 1);
        return this.sysIdentifyMapper.update(params);
    }


}
