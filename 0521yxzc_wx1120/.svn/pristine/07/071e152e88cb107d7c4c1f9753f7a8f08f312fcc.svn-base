package com.plugs.module_driver.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.module_driver.dtos.DriverQueueDto;
import com.plugs.module_driver.mappers.DriverQueueMapper;
import com.plugs.module_order.dtos.OrderConfigDto;
import com.plugs.module_order.mappers.OrderConfigMapper;
import com.plugs.utils.DateUtils;
import com.plugs.utils.SysConfigHelper;
import com.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DriverQueueService extends BaseServiceSupport<DriverQueueDto> {


    @Autowired
    private DriverQueueMapper<DriverQueueDto> driverQueueMapper;
    @Autowired
    private OrderConfigMapper<OrderConfigDto> orderConfigMapper;

    @Override
    public IMapper<DriverQueueDto> getMapper() {
        return driverQueueMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }


    /**
     * 根据 Gid与车型与预约时间 获取队列中司机信息
     * @param fenceGid 商圈Gid
     * @param levelType 车型
     * @param deparTime 预约时间
     * @return
     */
    public List<DriverQueueDto> getDriverFromQuequeByGid(String fenceGid, int levelType, Date deparTime, int orderType, String areaUuid) {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("fenceGid", fenceGid);
        params.put("levelType", levelType);
        params.put("deparTime", DateUtils.format(deparTime));

        OrderConfigDto orderConfig = this.orderConfigMapper.selInfoByOrderType(orderType);
        params.put("limitTimeBefore", orderConfig.getConflictTimeBefore());
        params.put("limitTimeAfter", orderConfig.getConflictTimeAfter());
        params.put("offLineLimitTime", SysConfigHelper.getOffLineLimitTime()); //s
        params.put("areaUuid", areaUuid);
        List<DriverQueueDto> driverQueues = this.driverQueueMapper.getDriverFromQuequeByGid(params);
        return driverQueues;
    }

    /*
    * 通过司机id获取对应队列信息
    * */
    public DriverQueueDto queryDriverQueueByDriverUuid(String driverUuid) {
        DriverQueueDto queueDto = null;
        Map<String, Object> queryMap = MapUtil.buildMap();
        queryMap.put("driverUuid", driverUuid);
        List<DriverQueueDto> queueDtoList = driverQueueMapper.list(queryMap);
        if (queueDtoList.size() > 0) {
            queueDto = queueDtoList.get(0);
        }
        return queueDto;
    }


}
