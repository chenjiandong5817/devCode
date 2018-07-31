package com.plugs.timer;

import com.plugs.base.WsMsg;
import com.plugs.module_driver.dtos.DriverDto;
import com.plugs.module_driver.dtos.DriverQueueDto;
import com.plugs.module_driver.services.DriverElectronicFenceService;
import com.plugs.module_driver.services.DriverQueueService;
import com.plugs.module_driver.services.DriverService;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.dtos.OrderPrepaidDto;
import com.plugs.module_order.mappers.OrderMapper;
import com.plugs.module_order.services.OrderPrepaidService;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserPassengerMapper;
import com.plugs.utils.GuoDouSmsUtils;
import com.plugs.utils.SysConfigHelper;
import com.plugs.utils.WebSocketUtil;
import com.util.MapUtil;
import com.utils.log4j.QxLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

/**
 * 描述:
 * 预支付订单定时器
 *
 * @outhor qfHan
 * @create 2017-12-04 17:17
 */
@Component
public class PrepaidOrderTimer {
    private static final Logger qxLogger = Logger.getLogger(PrepaidOrderTimer.class);

    @Autowired
    private OrderMapper<OrderDto> orderMapper;
    @Autowired
    private OrderPrepaidService orderPrepaidService;
    @Autowired
    private DriverQueueService driverQueueService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private DriverElectronicFenceService driverElectronicFenceService;
    @Autowired
    private UserPassengerMapper<UserPassengerDto> userPassengerMapper;

    private static LinkedList<OrderPrepaidDto> prepaidOrderList = null;

    private static Map<String, Object> params = MapUtil.buildMap();

    private static boolean isFirst = true;

    public static void add(OrderPrepaidDto orderPrepaidDto) {
        prepaidOrderList.add(orderPrepaidDto);
    }

    public static void remove(OrderPrepaidDto orderPrepaidDto) {
        prepaidOrderList.remove(orderPrepaidDto);
    }

    // 每秒执行一次
    @Scheduled(cron = "0/1 * * * * ? ")
    public void prepaidOrderTask() {
        //说明为第一次处理，从数据库中获取数据 防止重启后丢失部分数据
        if (isFirst) {
            params.put("orderTypeIn23", 1); //接送机
            params.put("orderSource", 3); //微信
            params.put("payStatusIn12" ,1); //待支付 支付中
            params.put("acceptType", 1); //派单模式
            params.put("status", 2); //todo:不为失效状态下
            params.put("createTime", 10); //10分钟之内
            prepaidOrderList = orderPrepaidService.orderPrepaidList(params);
            qxLogger.log(QxLog.LEVEL, "微信重启获取预支付订单数据，共：" + prepaidOrderList.size());
            isFirst = false;
        }
        for (OrderPrepaidDto orderPrepaidDto : prepaidOrderList) {

            long effectLongTime = orderPrepaidDto.getEffectTime().getTime();

             if ((System.currentTimeMillis()-effectLongTime)/1000 >= SysConfigHelper.getPrepaidOrderPayTimeLimit() * 60) {
                //判断订单相关状态 满足则取消
                params.clear();
                params.put("uuid", orderPrepaidDto.getOrderUuid());
                OrderDto orderDto = orderMapper.selInfo(params);
                if (orderDto != null && orderDto.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_WAIT_MEET
                        && orderDto.getPrepaidFee() > 0 && orderDto.getPrepaidStatus() != OrderPrepaidDto.PAY_STATUS_SUCCESS) {
                    //取消订单
                    OrderDto updOrderDto = new OrderDto();
                    updOrderDto.setUuid(orderPrepaidDto.getOrderUuid());
                    updOrderDto.setMainStatus(OrderDto.ORDER_MAIN_STATUS_CANCEL);
                    updOrderDto.setSubStatus(OrderDto.ORDER_SUB_STATUS_CANCEL_OWN);
                    updOrderDto.setUpdateTime(new Date());
                    orderMapper.edit(updOrderDto);

                    //todo:判断恢复司机队列信息
                    DriverQueueDto queueDto = driverQueueService.queryDriverQueueByDriverUuid(orderPrepaidDto.getDriverUuid());
                    if (queueDto != null) {
                        Map<String, Object> driverMap = MapUtil.buildMap();
                        driverMap.put("uuid", orderPrepaidDto.getDriverUuid());
                        DriverDto driverDto = this.driverService.findDriver(driverMap);
                        String diu = "AB" + String.valueOf(new Date().getTime());
                        try {
                            Map<String, Object> map = driverElectronicFenceService.checkInFence(driverDto.getCurrentLng(), driverDto.getCurrentLat(), diu);
                            if (map != null) {
                                String status = (String) map.get("status");
                                DriverQueueDto detailDto = this.driverQueueService.queryDriverQueueByDriverUuid(driverDto.getUuid());
                                if (detailDto != null) {
                                    if ("in".equals(status) && queueDto.getIsQueue() == 1) { // 如果司机仍然还在商圈排队中，则恢复可接单状态，继续排队
                                        queueDto.setIsTakeOrder(1);
                                    } else { // 不在商圈，则剔除排队，且在队列表中数据更新为不可接单
                                        queueDto.setIsQueue(0);
                                        queueDto.setIsTakeOrder(0);
                                    }
                                    queueDto.setUpdateTime(new Date());
                                    driverQueueService.edit(queueDto);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //极光通知乘客与司机
                    WsMsg wsMsg = WsMsg.createSuccess4Order("非常抱歉，由于未及时预支付费用，本次服务将取消。", orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_PASSENGER_PREPAID_CLOSE_TO_ORDER, WsMsg.To.PASSENGER);
                    WebSocketUtil.sendObjMessageByToken(orderDto.getPassengeUuid(), wsMsg);

                    params.clear();
                    params.put("uuid", orderDto.getPassengeUuid());
                    UserPassengerDto user = userPassengerMapper.selInfo(params);
                    GuoDouSmsUtils.closePrepaidOrder2Passenger(orderDto, user);

                    WsMsg wsMsg4Driver = WsMsg.createSuccess4Order("预支付订单关闭订单", orderDto.toMap4WebSocket(), WsMsg.OPERATE_CODE_DRIVER_PREPAID_CLOSE_TO_ORDER, WsMsg.To.DRIVER);
                    WebSocketUtil.sendObjMessageByToken(orderDto.getActualDriverUuid(), wsMsg4Driver);
                }
                OrderPrepaidDto updOrderPrepaidDto = new OrderPrepaidDto();
                updOrderPrepaidDto.setUuid(orderPrepaidDto.getUuid());
                updOrderPrepaidDto.setStatus(2); //设为失效
                updOrderPrepaidDto.setUpdateTime(new Date());
                orderPrepaidService.edit(updOrderPrepaidDto);
                prepaidOrderList.remove(orderPrepaidDto); //从定期器中移除
            } else {
                return;
            }
        }
    }


}
