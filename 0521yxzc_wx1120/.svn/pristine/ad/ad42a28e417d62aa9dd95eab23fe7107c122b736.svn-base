package com.plugs.module_user.mappers;

import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.plugs.module_user.dtos.UserCouponDto;

import java.util.List;
import java.util.Map;

public interface UserCouponMapper<T extends UserCouponDto> extends IMapper<T> {


    List<Map<String,Object>> findEnableCouponWithUseCarType(Map<String, Object> params);

    List<Map<String,Object>> listMyCouponMap(Map<String, Object> params,PageBounds pageBounds);

    List<Map<String,Object>> findEnableCouponForEstimate(Map<String, Object> params);

    List<Map<String,Object>> findMinCoupon(Map<String, Object> params,PageBounds pageBounds);

    UserCouponDto selInfoWithSysCoupon(Map<String, Object> params);

    List<UserCouponDto> listMap(Map<String, Object> params);

    int getCountByUuidAndArea(Map<String, Object> params);
}
