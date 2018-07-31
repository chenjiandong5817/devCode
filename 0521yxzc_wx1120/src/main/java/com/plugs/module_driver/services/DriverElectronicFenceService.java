package com.plugs.module_driver.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.constants.DriverConstants;
import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_driver.dtos.DriverElectronicFenceDto;
import com.plugs.module_driver.dtos.DriverQueueDto;
import com.plugs.module_driver.dtos.LatLngDto;
import com.plugs.module_driver.mappers.DriverElectronicFenceMapper;
import com.plugs.utils.gaodemap.GaodeMapsUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DriverElectronicFenceService extends BaseServiceSupport<DriverElectronicFenceDto> {

    private static final Logger logger = Logger.getLogger(DriverElectronicFenceService.class);


    @Autowired
    private DriverElectronicFenceMapper<DriverElectronicFenceDto> driverElectronicFenceMapper;

    @Autowired
    private DriverQueueService driverQueueService;

    @Override
    public IMapper<DriverElectronicFenceDto> getMapper() {
        return driverElectronicFenceMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }


    /**
     * 根据经纬度获取Gid
     *
     * @param lng 经度
     * @param lat 纬度
     * @return 异常返回NULL，否则返回Gid
     */
    public String getFenceGid(double lng, double lat) {
        String fenceGid = null;
        try {
            String diu = "AB" + String.valueOf(new Date().getTime()).toUpperCase();
            String jsonStr = GaodeMapsUtils.statusGeoFence(lng, lat, diu);
            JSONObject dataJson = JSONObject.fromObject(jsonStr);
            Object data = dataJson.get("data");
            dataJson = JSONObject.fromObject(data);
            data = dataJson.get("fencing_event_list");
            JSONArray dataJsonArray = JSONArray.fromObject(data);
            if (dataJsonArray.size() > 0) {
                data = dataJsonArray.get(0);
                dataJson = JSONObject.fromObject(data);
                data = dataJson.get("fence_info");
                dataJson = JSONObject.fromObject(data);
                fenceGid = dataJson.getString("fence_gid");
            }
        } catch (Exception e) {
            logger.error("高德商圈定位异常", e);
        }
        if(StringUtils.isBlank(fenceGid)){
            fenceGid = checkFecne(lng,lat);
        }
        return fenceGid;
    }

    /*
    * 检查司机接单是是否仍然还在商圈内，在进行派单
    * */
    public void checkDriverHasOrderInFence(DriverDto driverDto) {
        String diu = "AB" + String.valueOf(new Date().getTime());
        try {
            DriverQueueDto driverQueueDto = driverQueueService.queryDriverQueueByDriverUuid(driverDto.getUuid());
            if (driverQueueDto != null) {
                Map<String, Object> map = checkInFence(driverDto.getCurrentLng(), driverDto.getCurrentLat(), diu);
                if (map != null) {
                    String status = (String) map.get("status");
                    if ("in".equals(status)) {
                        driverQueueDto.setIsTakeOrder(DriverConstants.DRIVER_QUEUE_TAKE_ORDER_NO); // 司机在商圈围栏，不可接单
                    }
                } else {
                    driverQueueDto.setIsQueue(DriverConstants.DRIVER_QUEUE_STATUS_0); // 司机不在队列
                    driverQueueDto.setIsTakeOrder(DriverConstants.DRIVER_QUEUE_TAKE_ORDER_NO);
                }
                driverQueueDto.setUpdateTime(new Date());
                driverQueueService.edit(driverQueueDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 是否在商圈
    * */
    public Map<String, Object> checkInFence(Double curLng, Double curLat, String diu) throws Exception {
        Map<String, Object> map = null;
        String jsonStr = GaodeMapsUtils.statusGeoFence(curLng, curLat, diu);
        JSONObject dataJson = JSONObject.fromObject(jsonStr);
        Object data = dataJson.get("data");
        dataJson = JSONObject.fromObject(data);
        data = dataJson.get("fencing_event_list");
        JSONArray dataJsonArray = JSONArray.fromObject(data);
        if (dataJsonArray.size() > 0) {
            map = new HashMap<String, Object>();
            data = dataJsonArray.get(0);
            dataJson = JSONObject.fromObject(data);
            String status = (String) dataJson.get("client_status");
            data = dataJson.get("fence_info");
            dataJson = JSONObject.fromObject(data);
            String fenceGid = (String) dataJson.get("fence_gid");
            String fenceName = (String) dataJson.get("fence_name");
            map.put("status", status);
            map.put("fenceGid", fenceGid);
            map.put("fenceName", fenceName);
        }
        return map;
    }

    /**
     * 圈定位异常,辅助检测
     * @param lng
     * @param lat
     * @return
     */
    public String checkFecne(double lng, double lat){
        logger.info("高德商圈定位异常，商圈辅助定位开始 " + lng +","+ lat);
        String fenceGid = null;
        LatLngDto latLngDto = new LatLngDto(lng,lat);
        List<DriverElectronicFenceDto> fenceDtos = this.driverElectronicFenceMapper.list(null);
        if(CollectionUtils.isNotEmpty(fenceDtos)){
            for (DriverElectronicFenceDto fenceDto : fenceDtos) {
                if(StringUtils.isNotBlank(fenceDto.getPoints())){
                    List<LatLngDto> APoints = new ArrayList<LatLngDto>();
                    for (String s : fenceDto.getPoints().split(";")) {
                        APoints.add(new LatLngDto(Double.parseDouble(s.split(",")[0]),Double.parseDouble(s.split(",")[1])));
                    }
                    if(PtInPolygon(latLngDto, APoints)){
                        fenceGid = fenceDto.getFenceGid();
                        break;
                    }
                }
            }
        }
        logger.info("商圈辅助定位围栏ID:" + fenceGid);
        return fenceGid;
    }

    /**
     * 功能：判断点是否在多边形内
     * 方法：求解通过该点的水平线与多边形各边的交点
     * 结论：单边交点为奇数，成立!
     * @param point 指定的某个点
     * @param APoints 多边形的各个顶点坐标（首末点可以不一致）
     * @return
     */
    public static boolean PtInPolygon(LatLngDto point, List<LatLngDto> APoints) {
        int nCross = 0;
        for (int i = 0; i < APoints.size(); i++)   {
            LatLngDto p1 = APoints.get(i);
            LatLngDto p2 = APoints.get((i + 1) % APoints.size());
            // 求解 y=p.y 与 p1p2 的交点
            if ( p1.getLongitude() == p2.getLongitude())      // p1p2 与 y=p0.y平行
                continue;
            if ( point.getLongitude() <  Math.min(p1.getLongitude(), p2.getLongitude()))   // 交点在p1p2延长线上
                continue;
            if ( point.getLongitude() >= Math.max(p1.getLongitude(), p2.getLongitude()))   // 交点在p1p2延长线上
                continue;
            // 求交点的 X 坐标 --------------------------------------------------------------
            double x = (double)(point.getLongitude() - p1.getLongitude()) * (double)(p2.getLatitude() - p1.getLatitude()) / (double)(p2.getLongitude() - p1.getLongitude()) + p1.getLatitude();
            if ( x > point.getLatitude() )
                nCross++; // 只统计单边交点
        }
        // 单边交点为偶数，点在多边形之外 ---
        return (nCross % 2 == 1);
    }
}
