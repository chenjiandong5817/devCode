package com.plugs.utils.gaodemap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plugs.constants.DriverConstants;
import com.plugs.module_driver.dtos.DriverElectronicFenceDto;
import com.plugs.module_order.dtos.OrderLocationDto;
import com.plugs.utils.CalcDistance;
import com.plugs.utils.FileUtils;
import com.plugs.utils.HttpKit;
import com.plugs.utils.StringUtils;
import com.util.PropertiesUtil;
import com.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;


/**
 * 高德地图Web服务工具类.
 * <p>
 * Created by Xiaoyu on 2016/10/18.
 */
public class GaodeMapsUtils {
    private static final Logger logger = Logger.getLogger(GaodeMapsUtils.class);
    /*
     * 静态地图API
     */
    private static final String AMAP_STATICMAP_API = "http://restapi.amap.com/v3/staticmap?";

    /*
     * 静态地图API
     */
    private static final String AMAP_DRIVING_DIRECTION_API = "http://restapi.amap.com/v3/direction/driving?";

    /*
     * 距离测量API服务地址
     */
    private static final String AMAP_DRIVING_DISTANCE_API = "http://restapi.amap.com/v3/distance?";

    /*
     * 创建地理围栏
     */
    private static final String AMAP_ADD_GEO_FENCE = "http://restapi.amap.com/v4/geofence/meta?key=";


    /*
     * 围栏设备监控
     */
    private static final String AMAP_STATUS_GEO_FENCE = "http://restapi.amap.com/v4/geofence/status";

    /*
     * 高德地图KEY
     */
    private static final String AMAP_AK = "bf65d989ecc88f0ac628cd24df101ec2";

    /**
     * 获取静态地图图片
     *
     * @param orderLocations 经纬度，经纬度之间使用","分隔，不同的点使用";"分隔
     * @param ordUuid        订单Uuid，用于设置图片存放路径
     */
    public static String getStaticMap(List<OrderLocationDto> orderLocations, String ordUuid) throws Exception {

        Map<String, String> params = new HashMap<String, String>();

        params.put("key", GaodeMapsUtils.AMAP_AK); // 用户唯一标识
        //params.put("location", GaodeMapsUtils.AMAP_AK); // 地图中心点(规则：经度和纬度用","分隔 经纬度小数点后不得超过6位。)
        params.put("zoom", GaodeMapsUtils.calcZoom(orderLocations)); // 地图缩放级别:[1,17]
        params.put("size", "500*500"); // 地图大小(图片宽度*图片高度。最大值为1024*1024)
        params.put("scale", "1"); // 普通/高清	1:返回普通图；2:调用高清图
        //params.put("markers", GaodeMapsUtils.AMAP_AK); // 标注
        //params.put("labels", GaodeMapsUtils.AMAP_AK); // 标签
        //params.put("traffic", GaodeMapsUtils.AMAP_AK); // 交通路况标识
        //params.put("sig", GaodeMapsUtils.AMAP_AK); // 数字签名

        StringBuffer pathSBuffer = new StringBuffer();
        // weight(线条粗细。 可选值： [2,15])
        pathSBuffer.append("4,");
        // color 折线颜色。 选值范围：[0x000000, 0xffffff]
        pathSBuffer.append("0x00b5e6,");
        // transparency 透明度。可选值[0,1]，小数后最多2位，0表示完全透明，1表示完全不透明。
        pathSBuffer.append("1,");
        // fillcolor 多边形的填充颜色，此值不为空时折线封闭成多边形。取值规则同color
        pathSBuffer.append(",");
        // location为经纬度，经纬度之间使用","分隔，不同的点使用";"分隔。
        pathSBuffer.append(":" + GaodeMapsUtils.getPaths(orderLocations));

        params.put("paths", pathSBuffer.toString()); // 折线

        params.put("markers", GaodeMapsUtils.getMarkers(orderLocations)); // 图标

        // 访问高德API获取静态图片数据
        byte[] btImg = HttpKit.getBytes(GaodeMapsUtils.AMAP_STATICMAP_API, params, false);

        // 将图片数据写入文件
        String urlPath = "";
        try {
            // TODO 图片存放路径需要重新定义
            urlPath = FileUtils.uploadImages(btImg, ordUuid, "orderTrip");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlPath;
    }

    /**
     * 根据起终点距离计算缩放比例
     *
     * @param orderLocations 订单所有路径规划点
     */
    public static String calcZoom(List<OrderLocationDto> orderLocations) throws Exception {
        // 默认地图缩放比
        String zoom = "12";

        if (orderLocations == null || orderLocations.size() == 0) {
            return zoom;
        }

        // 计算起终点之间的距离，单位米
        double distance = CalcDistance.getDistance(orderLocations.get(0).getLng(), orderLocations.get(0).getLat(),
                orderLocations.get(orderLocations.size() - 1).getLng(), orderLocations.get(orderLocations.size() - 1).getLat());

        if (distance < 500) {
            zoom = "15";
        } else if (distance < 1500) {
            zoom = "14";
        } else if (distance < 2500) {
            zoom = "13";
        } else if (distance < 5000) {
            zoom = "12";
        } else if (distance < 10000) {
            zoom = "11";
        } else if (distance < 15000) {
            zoom = "10";
        } else if (distance < 30000) {
            zoom = "9";
        } else if (distance < 50000) {
            zoom = "8";
        } else if (distance < 100000) {
            zoom = "7";
        } else if (distance < 200000) {
            zoom = "6";
        } else if (distance < 400000) {
            zoom = "5";
        } else if (distance < 800000) {
            zoom = "4";
        } else if (distance < 1600000) {
            zoom = "3";
        } else {
            zoom = "2";
        }

        return zoom;
    }

    /**
     * 获取计算静态图片所需路径规划点
     *
     * @param orderLocations 订单所有路径规划点
     */
    public static String getPaths(List<OrderLocationDto> orderLocations) throws Exception {
        if (orderLocations == null || orderLocations.size() == 0) {
            return "";
        }
        double distance = orderLocations.get(orderLocations.size() - 1).getMileage();

        // 路径点总数不超过100个
        final int TOTAL_POINTS = 100;

        StringBuffer pathSB = new StringBuffer();

        // 距离平均分布，例、总行驶里程10公里，则每100米取一个点，共取100个点
        // 起点从0开始计算
        int pCnt = 0;

        for (OrderLocationDto ol : orderLocations) {
            // 累计里程 > （总距离 * 当前点 / 总取点数）
            if (ol.getMileage() > (distance * pCnt / TOTAL_POINTS)) {
                // 记录该点
                pCnt++;
                pathSB.append(ol.getLng() + "," + ol.getLat() + ";");
            }
            if (pCnt > TOTAL_POINTS) {
                break;
            }
        }

        // 插入最后一条记录
        pathSB.append(orderLocations.get(orderLocations.size() - 1).getLng() + ","
                + orderLocations.get(orderLocations.size() - 1).getLat());

        return pathSB.toString();
    }

    /**
     * 获取起终点图标
     *
     * @param orderLocations 订单所有路径规划点
     */
    public static String getMarkers(List<OrderLocationDto> orderLocations) throws Exception {
        if (orderLocations == null || orderLocations.size() == 0) {
            return "";
        }
        // 起点图片路径
        String locationStartPng = "http://pic.yxzc01.com:11001/arport/images/pub/location_start.png";
        // 终点图片路径
        String locationEndPng = "http://pic.yxzc01.com:11001/arport/images/pub/location_end.png";

        StringBuffer markersSB = new StringBuffer();
        // markers=-1,http://114.55.115.102/pub/images/location_start.png,0:116.31604,39.96491|-1,http://114.55.115.102/pub/images/location_end.png,0:116.32361,39.966957
        // markersStyle： -1，url，0。-1表示为自定义图片，URL为图片的网址。自定义图片只支持PNG格式。
        markersSB.append("-1,");
        markersSB.append(locationStartPng);
        markersSB.append(",0:");
        // 起点
        markersSB.append(orderLocations.get(0).getLng() + "," + orderLocations.get(0).getLat());
        markersSB.append("|-1,");
        markersSB.append(locationEndPng);
        markersSB.append(",0:");
        // 终点
        markersSB.append(orderLocations.get(orderLocations.size() - 1).getLng() + "," + orderLocations.get(orderLocations.size() - 1).getLat());

        return markersSB.toString();
    }

    /**
     * 将字符串拼接的经纬度坐标转换为DTO对象List
     * 字符串拼接规则：精度,纬度;精度,纬度;精度,纬度（半角逗号及半角冒号）
     *
     * @param lngLat 字符串拼接的经纬度坐标
     */
    public static List<OrderLocationDto> convertStringToDtoList(String lngLat) {
        List<OrderLocationDto> orderLocations = new ArrayList<OrderLocationDto>();
        if (StringUtils.isEmpty(lngLat)) {
            return orderLocations;
        }

        String[] lngLats = lngLat.split(";");
        if (lngLats.length == 0) {
            return orderLocations;
        }

        try {
            String[] tempS = lngLats[0].split(",");

            double tempLng = Double.valueOf(tempS[0]);
            double tempLat = Double.valueOf(tempS[1]);
            double tempDis = 0;

            for (String s : lngLats) {
                String[] temp = s.split(",");
                if (temp.length > 1) {
                    double lng = Double.valueOf(temp[0]);
                    double lat = Double.valueOf(temp[1]);
                    tempDis += CalcDistance.getDistance(lng, lat, tempLng, tempLat);

                    orderLocations.add(new OrderLocationDto(lng, lat, tempDis));

                    tempLng = lng;
                    tempLat = lat;
                }
            }

            return orderLocations;
        } catch (Exception e) {
            return orderLocations;
        }
    }


    /**
     * 驾车路径规划
     *
     * @param oriLng  起点经度
     * @param oriLat  起点纬度
     * @param destLng 终点经度
     * @param destLat 终点纬度
     */
    public static String getDrivingDirection(double oriLng, double oriLat, double destLng, double destLat) throws Exception {
        // 返回值
        StringBuffer retSb = new StringBuffer();

        // 驾车规划路线请求参数
        Map<String, String> params = new HashMap<String, String>();

        params.put("key", GaodeMapsUtils.AMAP_AK); // 用户唯一标识
        params.put("origin", lngLat2String(oriLng, oriLat)); // 出发点 经度在前，纬度在后，经度和纬度用","分割，经纬度小数点后不得超过6位。
        params.put("destination", lngLat2String(destLng, destLat)); // 目的地 经度在前，纬度在后，经度和纬度用","分割，经纬度小数点后不得超过6位。
        params.put("strategy", "0"); // 驾车选择策略
        /*
        0速度优先（时间）
        1费用优先（不走收费路段的最快道路）
        2距离优先
        3不走快速路
        4躲避拥堵
        5多策略（同时使用速度优先、费用优先、距离优先三个策略计算路径）。
        其中必须说明，就算使用三个策略算路，会根据路况不固定的返回一~三条路径规划信息。
        6不走高速
        7不走高速且避免收费
        8躲避收费和拥堵
        9不走高速且躲避收费和拥堵
        */
        params.put("output", "JSON"); // 驾车选择策略

        // 访问高德API获取驾车路径规划数据
        String retJson = HttpKit.get(GaodeMapsUtils.AMAP_DRIVING_DIRECTION_API, params, false);

        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

        // 从返回结果中获取导航数据并进行解析
        try {
            Map<String, Object> amapData = mapper.readValue(retJson, Map.class);

            // 判断返回状态不成功（1：成功；0：失败）
            if (!"1".equals(amapData.get("status"))) {
                logger.error("GaodeMapsUtils#getDrivingDirection Exception：" + amapData.get("info"));
                return "";
            }

            //获取路线
            Map<String, Object> route = (Map<String, Object>) amapData.get("route"); // 驾车路径规划信息列表

            List<Map<String, Object>> paths = (List<Map<String, Object>>) route.get("paths"); //驾车换乘方案

            // 上一点坐标（起始为起点坐标）
            retSb.append((String) route.get("origin") + ";");

            for (Map<String, Object> path : paths) {
                // 导航路段
                List<Map<String, Object>> steps = (List<Map<String, Object>>) path.get("steps");

                for (Map<String, Object> step : steps) {
                    // 此路段坐标点串 格式为坐标串，如：116.481247,39.990704;116.481270,39.990726
                    String polyline = (String) step.get("polyline");

                    retSb.append(polyline + ";");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retSb.toString();
    }

    /**
     * 两点测距 和 时间
     *
     * @param oriLng  出发点 经度
     * @param oriLat  出发点 纬度
     * @param destLng 目的地 经度
     * @param destLat 目的地 纬度
     * @return {origin_id=1, dest_id=1, distance=3890, duration=840} OR null
     * @throws Exception
     */
    public static Object getDrivingDistanceAndDuration(double oriLng, double oriLat, double destLng, double destLat) throws Exception {
        // 返回值
        Object obj = null;

        // 驾车规划路线请求参数
        Map<String, String> params = new HashMap<String, String>();

        params.put("key", GaodeMapsUtils.AMAP_AK); // 用户唯一标识
        params.put("origins", lngLat2String(oriLng, oriLat)); // 出发点 经度在前，纬度在后，经度和纬度用","分割，经纬度小数点后不得超过6位。
        params.put("destination", lngLat2String(destLng, destLat)); // 目的地 经度在前，纬度在后，经度和纬度用","分割，经纬度小数点后不得超过6位。

        // 访问高德API获取驾车路径规划数据
        String retJson = HttpKit.get(GaodeMapsUtils.AMAP_DRIVING_DISTANCE_API, params, false);
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

        // 从返回结果中获取导航数据并进行解析
        try {
            Map<String, Object> amapData = mapper.readValue(retJson, Map.class);
            // 判断返回状态不成功（1：成功；0：失败）
            if (!"1".equals(amapData.get("status"))) {
                logger.error("GaodeMapsUtils#getDrivingDistance Exception：" + amapData.get("info"));
                return obj;
            }
            ArrayList<String> results = (ArrayList<String>) amapData.get("results");
            return results.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    /**
     * 拼接经纬度为字符串。经度在前，纬度在后，经度和纬度用","分割，经纬度小数点后不得超过6位。
     *
     * @param lng 经度
     * @param lat 纬度
     */
    private static String lngLat2String(double lng, double lat) throws Exception {
        StringBuffer retBf = new StringBuffer();

        retBf.append(new DecimalFormat("#.000000").format(lng));
        retBf.append(",");
        retBf.append(new DecimalFormat("#.000000").format(lat));

        System.out.println("lngLat2String：" + retBf.toString());

        return retBf.toString();
    }


    /**
     * 创建围栏
     *
     * @param fenceDto
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String addGeoFence(DriverElectronicFenceDto fenceDto) throws UnsupportedEncodingException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", fenceDto.getFenceName().trim());
        jsonObj.put("enable", "true");
        jsonObj.put("repeat", "Mon,Tues,Wed,Thur,Fri,Sat,Sun");
        Integer fenceType = fenceDto.getFenceType();
        if (fenceType.equals(DriverConstants.FENCE_TYPE_CIRCLE)) {// 圆形
            jsonObj.put("center", fenceDto.getFenceLng() + "," + fenceDto.getFenceLat());
            jsonObj.put("radius", fenceDto.getRadius().toString());
        } else if (fenceType.equals(DriverConstants.FENCE_TYPE_POLYGON)) { //多边形
            jsonObj.put("points", fenceDto.getPoints());
        }

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(GaodeMapsUtils.AMAP_ADD_GEO_FENCE + GaodeMapsUtils.AMAP_AK);
        try {
            StringEntity postingString = new StringEntity(jsonObj.toString(), "UTF-8");// json传递
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(post);
            String resEntityToString = EntityUtils.toString(response.getEntity(),"UTF-8");
            return resEntityToString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 更新围栏
     *
     * @param fenceDto
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String patchGeoFence(DriverElectronicFenceDto fenceDto) throws UnsupportedEncodingException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", fenceDto.getFenceName());
        Integer fenceType = fenceDto.getFenceType();
        if (fenceType.equals(DriverConstants.FENCE_TYPE_CIRCLE)) {// 圆形
            jsonObj.put("center", fenceDto.getFenceLng() + "," + fenceDto.getFenceLat());
            jsonObj.put("radius", fenceDto.getRadius().toString());
        } else if (fenceType.equals(DriverConstants.FENCE_TYPE_POLYGON)) { //多边形
            jsonObj.put("points", fenceDto.getPoints());
        }

        HttpClient httpClient = HttpClients.createDefault();
        String patchUrl = GaodeMapsUtils.AMAP_ADD_GEO_FENCE + GaodeMapsUtils.AMAP_AK + "&gid=" + fenceDto.getFenceGid();
        HttpPatch httpPatch = new HttpPatch(patchUrl);
        try {
            StringEntity postingString = new StringEntity(jsonObj.toString(), "utf-8");// json传递
            httpPatch.setEntity(postingString);
            httpPatch.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(httpPatch);
            String resEntityToString = EntityUtils.toString(response.getEntity(),"UTF-8");
            return resEntityToString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除围栏
     *
     * @param fenceGid
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String deleteGeoFence(String fenceGid) throws UnsupportedEncodingException {
        HttpClient httpClient = HttpClients.createDefault();
        String deleteUrl = GaodeMapsUtils.AMAP_ADD_GEO_FENCE + GaodeMapsUtils.AMAP_AK + "&gid=" + fenceGid;
        HttpDelete httpDelete = new HttpDelete(deleteUrl);
        try {
            httpDelete.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(httpDelete);
            String resEntityToString = EntityUtils.toString(response.getEntity(),"UTF-8");
            return resEntityToString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 围栏设备监控
     *
     * @return
     */
    public static String statusGeoFence(double oriLng, double oriLat, String diu) throws Exception {
        // 返回值
        Object obj = null;

        Map<String, String> params = new HashMap<String, String>();
        params.put("key", GaodeMapsUtils.AMAP_AK);
        params.put("diu", diu); // 设备唯一标识符。Android为imei，iOS为idfv
        params.put("locations", oriLng + "," + oriLat + "," + new Date().getTime() / 1000); //经度在前，纬度在后，经度和纬度用","分割，经纬度小数点后不得超过6位。

        // params.put("uid","imei");

        String retJson = HttpKit.get(GaodeMapsUtils.AMAP_STATUS_GEO_FENCE, params, false);

        return retJson;
    }


    public static void main(String[] args) throws Exception {
        //String path = "118.180191,24.486216;118.180069,24.486259;118.180069,24.486259;118.180443,24.487118;118.18045,24.487169;118.17524,24.490356;118.175049,24.492374;118.175316,24.494484;118.177147,24.500677;118.177094,24.502035;118.176788,24.503143;118.175323,24.505838;118.174927,24.506121;118.168121,24.502747;118.168121,24.502747;118.158417,24.499207;118.158417,24.499207;118.148071,24.498755;118.148071,24.498755;118.144066,24.49902;118.144066,24.49902;118.143837,24.499241;118.143837,24.499241;118.145157,24.500338";

        //System.out.println(GaodeMapsUtils.getStaticMap(path, "8f6d26256f844d17b2a7b29ab653ee46"));

//        List<OrderLocationDto> orderLocations = GaodeMapsUtils.addTestData();

//        System.out.println(GaodeMapsUtils.getStaticMap(orderLocations, "90156daeb2c44344b9bdf90879938a50"));
//        String lngLats = "118.180206,24.48658;118.180069,24.486259;118.179993,24.486059;118.179665,24.48526;118.179611,24.48513;118.179611,24.48513;118.178452,24.485521;118.177414,24.485886;118.177414,24.485886;118.177429,24.485733;118.17746,24.48559;118.177521,24.485004;118.177536,24.48481;118.177528,24.48457;118.177521,24.484344;118.177467,24.484085;118.177414,24.48394;118.177185,24.483347;118.177185,24.483347;118.176247,24.483667;118.175156,24.484055;118.174515,24.484276;118.173996,24.484409;118.173813,24.484453;118.173286,24.484552;118.173035,24.484592;118.172363,24.484674;118.171181,24.484814;118.171097,24.484831;118.170044,24.484943;118.16996,24.484957;118.168541,24.485147;118.167587,24.48527;118.166405,24.48543;118.166161,24.485468;118.165298,24.485577;118.16497,24.485617;118.164894,24.485624;118.164688,24.485655;118.164101,24.485743;118.163643,24.485807;118.162811,24.485899;118.162598,24.485916;118.162529,24.48592;118.161613,24.485964;118.161064,24.485994;118.160294,24.486046;118.159424,24.486073;118.158203,24.486107;118.158134,24.486107;118.157745,24.48612;118.157082,24.486172;118.156647,24.486198;118.156349,24.486237;118.156143,24.486277;118.15538,24.486464;118.155052,24.486559;118.1549,24.486614;118.154701,24.486692;118.154602,24.486553;118.154808,24.486471;118.154999,24.48641;118.155312,24.486311;118.155312,24.486311;118.155113,24.485994;118.155113,24.485994;118.154984,24.485781;118.154945,24.485712;118.1549,24.485569;118.1549,24.485569;118.154846,24.485443;118.154823,24.485226;118.154823,24.485157;118.154823,24.485157;118.154846,24.485121;118.154915,24.48509;118.155304,24.485025;118.155556,24.484987";
//        List<OrderLocationDto> orderLocations = GaodeMapsUtils.convertStringToDtoList(lngLats);
//

        double oriLng = 118.180206;
        double oriLat = 24.48658;
        double destLng = 118.155556;
        double destLat = 24.484987;
//        System.out.println(GaodeMapsUtils.getDrivingDistanceAndDuration(oriLng, oriLat, destLng, destLat));

//        System.out.println(GaodeMapsUtils.getStaticMap(GaodeMapsUtils.convertStringToDtoList(GaodeMapsUtils.getDrivingDirection(oriLng, oriLat, destLng, destLat)), "90156daeb2c44344b9bdf90879938a50"));


//        double oriLng = 118.180206;
//        double oriLat = 24.48658;
//        double destLng = 118.155556;
//        double destLat = 24.484987;
//
//        getDrivingDirection(oriLng, oriLat, destLng, destLat);
    }

    private static List<OrderLocationDto> addTestData() {
        List<OrderLocationDto> orderLocations = new ArrayList<OrderLocationDto>();
        orderLocations.add(new OrderLocationDto(118.180807, 24.486613, 0));
        orderLocations.add(new OrderLocationDto(118.1808063, 24.48661283, 0.026420955));
        orderLocations.add(new OrderLocationDto(118.1808074, 24.48661309, 0.026522148));
        orderLocations.add(new OrderLocationDto(118.1808101, 24.48661286, 0.02682573));
        orderLocations.add(new OrderLocationDto(118.1808036, 24.48661661, 0.028014898));
        orderLocations.add(new OrderLocationDto(118.1808038, 24.48661677, 0.028014898));
        orderLocations.add(new OrderLocationDto(118.1809585, 24.48655292, 0.204232857));
        orderLocations.add(new OrderLocationDto(118.1809352, 24.48653374, 0.20745185));
        orderLocations.add(new OrderLocationDto(118.1809414, 24.48654769, 0.209122792));
        orderLocations.add(new OrderLocationDto(118.1808106, 24.48655749, 0.222316012));
        orderLocations.add(new OrderLocationDto(118.1809068, 24.48659892, 0.233094901));
        orderLocations.add(new OrderLocationDto(118.1808187, 24.48659882, 0.241999954));
        orderLocations.add(new OrderLocationDto(118.180817, 24.4866371, 0.2462302));
        orderLocations.add(new OrderLocationDto(118.1807182, 24.48665039, 0.256352127));
        orderLocations.add(new OrderLocationDto(118.1805675, 24.48669666, 0.272501409));
        orderLocations.add(new OrderLocationDto(118.1806259, 24.4867201, 0.293740451));
        orderLocations.add(new OrderLocationDto(118.1806172, 24.48669916, 0.296246856));
        orderLocations.add(new OrderLocationDto(118.1806436, 24.48675656, 0.303251058));
        orderLocations.add(new OrderLocationDto(118.1806605, 24.48677371, 0.305739999));
        orderLocations.add(new OrderLocationDto(118.1807054, 24.4867494, 0.311075181));
        orderLocations.add(new OrderLocationDto(118.18074, 24.48678741, 0.31658864));
        orderLocations.add(new OrderLocationDto(118.1806272, 24.4868546, 0.330331922));
        orderLocations.add(new OrderLocationDto(118.1806282, 24.486716, 0.34578836));
        orderLocations.add(new OrderLocationDto(118.180283, 24.48662198, 0.382296652));
        orderLocations.add(new OrderLocationDto(118.180187, 24.48663258, 0.392087966));
        orderLocations.add(new OrderLocationDto(118.1801392, 24.48669585, 0.400612473));
        orderLocations.add(new OrderLocationDto(118.1800628, 24.48676766, 0.411713988));
        orderLocations.add(new OrderLocationDto(118.1799764, 24.48677775, 0.420587778));
        orderLocations.add(new OrderLocationDto(118.1799183, 24.48678659, 0.426541716));
        orderLocations.add(new OrderLocationDto(118.179919, 24.48678769, 0.426692069));
        orderLocations.add(new OrderLocationDto(118.179909, 24.48677591, 0.428366721));
        orderLocations.add(new OrderLocationDto(118.1799166, 24.48673499, 0.432997018));
        orderLocations.add(new OrderLocationDto(118.1799279, 24.48672976, 0.434241265));
        orderLocations.add(new OrderLocationDto(118.1800537, 24.48670994, 0.447184175));
        orderLocations.add(new OrderLocationDto(118.1802688, 24.48693435, 0.480255961));
        orderLocations.add(new OrderLocationDto(118.1803635, 24.48711896, 0.50296247));
        orderLocations.add(new OrderLocationDto(118.1802229, 24.48728942, 0.526646078));
        orderLocations.add(new OrderLocationDto(118.1798951, 24.48743073, 0.563401818));
        orderLocations.add(new OrderLocationDto(118.1795241, 24.4876112, 0.605946541));
        orderLocations.add(new OrderLocationDto(118.1791415, 24.4877955, 0.649682939));
        orderLocations.add(new OrderLocationDto(118.1787802, 24.48801956, 0.694043219));
        orderLocations.add(new OrderLocationDto(118.1783152, 24.48826278, 0.748304009));
        orderLocations.add(new OrderLocationDto(118.1779168, 24.48848793, 0.795716941));
        orderLocations.add(new OrderLocationDto(118.1775605, 24.48871745, 0.839832187));
        orderLocations.add(new OrderLocationDto(118.1774293, 24.48891401, 0.865488887));
        orderLocations.add(new OrderLocationDto(118.177555, 24.48919564, 0.899338961));
        orderLocations.add(new OrderLocationDto(118.1777578, 24.48941952, 0.931624591));
        orderLocations.add(new OrderLocationDto(118.1779488, 24.48972103, 0.970273972));
        orderLocations.add(new OrderLocationDto(118.1781045, 24.48986514, 0.992688119));
        orderLocations.add(new OrderLocationDto(118.1782684, 24.49000214, 1.015215278));
        orderLocations.add(new OrderLocationDto(118.1785565, 24.49018599, 1.050823212));
        orderLocations.add(new OrderLocationDto(118.1789216, 24.49034541, 1.091862679));
        orderLocations.add(new OrderLocationDto(118.1793898, 24.49045142, 1.140664697));
        orderLocations.add(new OrderLocationDto(118.1798694, 24.49046809, 1.18917191));
        orderLocations.add(new OrderLocationDto(118.1803519, 24.49041949, 1.238349795));
        orderLocations.add(new OrderLocationDto(118.1808632, 24.4902526, 1.293254137));
        orderLocations.add(new OrderLocationDto(118.18131, 24.49001447, 1.34571588));
        orderLocations.add(new OrderLocationDto(118.1817642, 24.48974077, 1.400779963));
        orderLocations.add(new OrderLocationDto(118.1823093, 24.48962619, 1.457392335));
        orderLocations.add(new OrderLocationDto(118.1828078, 24.48956717, 1.508311272));
        orderLocations.add(new OrderLocationDto(118.1832279, 24.48946854, 1.552186489));
        orderLocations.add(new OrderLocationDto(118.1836652, 24.48925652, 1.602298141));
        orderLocations.add(new OrderLocationDto(118.1840804, 24.48905427, 1.649974823));
        orderLocations.add(new OrderLocationDto(118.1843944, 24.48880045, 1.69248724));
        orderLocations.add(new OrderLocationDto(118.1847135, 24.48851478, 1.737795711));
        orderLocations.add(new OrderLocationDto(118.1850319, 24.48824874, 1.781503201));
        orderLocations.add(new OrderLocationDto(118.1852907, 24.48804302, 1.81631124));
        orderLocations.add(new OrderLocationDto(118.1856456, 24.48779755, 1.861396313));
        orderLocations.add(new OrderLocationDto(118.1860255, 24.48746698, 1.914625168));
        orderLocations.add(new OrderLocationDto(118.1862621, 24.48707787, 1.964034796));
        orderLocations.add(new OrderLocationDto(118.1863534, 24.48671554, 2.005327225));
        orderLocations.add(new OrderLocationDto(118.1863791, 24.48642123, 2.038235188));
        orderLocations.add(new OrderLocationDto(118.1862537, 24.4860578, 2.080534458));
        orderLocations.add(new OrderLocationDto(118.1861112, 24.48574912, 2.117816687));
        orderLocations.add(new OrderLocationDto(118.1860153, 24.48558492, 2.138478756));
        orderLocations.add(new OrderLocationDto(118.1856793, 24.48556525, 2.172552824));
        orderLocations.add(new OrderLocationDto(118.1851709, 24.48567223, 2.22531867));
        orderLocations.add(new OrderLocationDto(118.1846301, 24.48579021, 2.281615257));
        orderLocations.add(new OrderLocationDto(118.184109, 24.48591638, 2.336167336));
        orderLocations.add(new OrderLocationDto(118.1835721, 24.48605718, 2.392725229));
        orderLocations.add(new OrderLocationDto(118.1830137, 24.48622176, 2.452097654));
        orderLocations.add(new OrderLocationDto(118.1825053, 24.48637582, 2.506377459));
        orderLocations.add(new OrderLocationDto(118.1821715, 24.48650714, 2.543181658));
        orderLocations.add(new OrderLocationDto(118.1817868, 24.48664662, 2.585042477));
        orderLocations.add(new OrderLocationDto(118.1805493, 24.48711263, 2.720626593));
        orderLocations.add(new OrderLocationDto(118.1802754, 24.48661672, 2.791725636));
        orderLocations.add(new OrderLocationDto(118.1802831, 24.48661998, 2.792601109));
        orderLocations.add(new OrderLocationDto(118.180283, 24.48662, 2.792601109));

        return orderLocations;
    }


}
