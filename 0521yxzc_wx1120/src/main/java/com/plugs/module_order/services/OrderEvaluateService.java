package com.plugs.module_order.services;

import com.exception.BizException;
import com.plugs.base.AjaxList;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.mappers.OrderMapper;
import com.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.IMapper;
import com.base.BaseServiceSupport;
import com.plugs.module_order.dtos.OrderEvaluateDto;
import com.plugs.module_order.mappers.OrderEvaluateMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class OrderEvaluateService extends BaseServiceSupport<OrderEvaluateDto> {
    @Autowired
    private OrderEvaluateMapper<OrderEvaluateDto> orderEvaluateMapper;
    @Autowired
    private OrderMapper<OrderDto> orderMapper;

    @Override
    public IMapper<OrderEvaluateDto> getMapper() {
        return orderEvaluateMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

    public double myAvgScore(Map<String, Object> params) {
        return orderEvaluateMapper.myAvgScore(params);
    }

    @Transactional(rollbackFor = Exception.class)
    public AjaxList saveEvaluate(OrderEvaluateDto orderEvaluate) {
        orderEvaluateMapper.add(orderEvaluate);
        //修改订单子状态
        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", orderEvaluate.getOrderUuid());
        OrderDto order = orderMapper.selInfo(params);
        if (null != order) {
            if (order.getMainStatus() == OrderDto.ORDER_MAIN_STATUS_FINISH && order.getSubStatus() == OrderDto.ORDER_SUB_STATUS_FINISH_UN_EVA) {
                OrderDto updOrder = new OrderDto();
                updOrder.setUuid(order.getUuid());
                updOrder.setSubStatus(OrderDto.ORDER_SUB_STATUS_FINISH_EVA);//已评价
                orderMapper.edit(updOrder);
            } else
                throw new BizException("订单状态错误", ReturnCodeConstants.ERR_10006_LOGIC_ERR);

        }
        return AjaxList.createSuccess("评价成功");
    }


}
