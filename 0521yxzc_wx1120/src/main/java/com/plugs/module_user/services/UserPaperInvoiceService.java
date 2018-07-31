package com.plugs.module_user.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.plugs.base.AjaxList;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.mappers.OrderMapper;
import com.plugs.module_order.services.OrderService;
import com.plugs.module_user.dtos.UserHistoryInvoiceDto;
import com.plugs.module_user.dtos.UserPaperInvoiceDto;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserPaperInvoiceMapper;
import com.plugs.utils.StringUtils;
import com.util.MapUtil;
import com.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 描述:
 * 纸质发票service
 *
 * @outhor qfHan
 * @create 2018-01-31 14:13
 */
@Service
public class UserPaperInvoiceService extends BaseServiceSupport<UserPaperInvoiceDto> {
    private Logger logger = Logger.getLogger(UserElectronInvoiceService.class);

    @Autowired
    private UserPaperInvoiceMapper<UserPaperInvoiceDto> userPaperInvoiceMapper;

    @Override
    public IMapper<UserPaperInvoiceDto> getMapper() {
        return userPaperInvoiceMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

    @Autowired
    public OrderService orderService;
    @Autowired
    private OrderMapper<OrderDto> orderMapper;
    @Autowired
    private UserHistoryInvoiceService userHistoryInvoiceService;

    /**
     * 生成纸质发票
     * @param invoiceDto 发票实体
     * @param userDto 用户实体
     */
    public AjaxList paperGenerate(UserPaperInvoiceDto invoiceDto, UserPassengerDto userDto) {
        //组装数据
        this.assemblyInvoice(invoiceDto, userDto);
        if (StringUtils.isNotEmpty(invoiceDto.getOrderUuids())) {
            if(StringUtils.isNotEmpty(invoiceDto.getOrderUuids())){
                Map<String, Object> params = MapUtil.buildMap();
                params.put("isBilled", 1);//已开票
                params.put("orderUuids", invoiceDto.getOrderUuids().split(","));
                orderMapper.batchEdit(params);//更新
            }
        }
        userPaperInvoiceMapper.add(invoiceDto);
        //添加发票记录
        UserHistoryInvoiceDto historyInvoiceDto = new UserHistoryInvoiceDto();
        historyInvoiceDto.setUuid(StringUtil.buildUUID());
        historyInvoiceDto.setInvoiceUuid(invoiceDto.getUuid());
        historyInvoiceDto.setUserUuid(invoiceDto.getUserUuid());
        historyInvoiceDto.setType(1);
        historyInvoiceDto.setCreateTime(new Date());
        userHistoryInvoiceService.add(historyInvoiceDto);

        return AjaxList.createSuccess("开票成功");
    }

    /**
     * 获取纸质发票信息
     * @param invoiceUuid 发票的UUID
     */
    public AjaxList paperGenerateQuery(String invoiceUuid) {
        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", invoiceUuid);
        UserPaperInvoiceDto userPaperInvoiceDto = userPaperInvoiceMapper.selInfo(params);
        if (StringUtils.isNotEmpty(userPaperInvoiceDto.getOrderUuids())) {
            //查询订单信息
            List<OrderDto> orderDtos = new ArrayList<OrderDto>();
            for (String uuid : userPaperInvoiceDto.getOrderUuids().split(",")) {
                params.clear();
                params.put("uuid", uuid);
                orderDtos.add(orderService.selInfo(params));
            }
            userPaperInvoiceDto.setOrderDtoList(orderDtos);
        }
        return AjaxList.createSuccess("纸质发票获取成功", userPaperInvoiceDto);
    }

    private void assemblyInvoice(UserPaperInvoiceDto invoiceDto, UserPassengerDto userDto) {
        invoiceDto.setUuid(StringUtils.buildUUID());
        invoiceDto.setUserUuid(userDto.getUuid());
        invoiceDto.setInvoiceSource(1);
        invoiceDto.setUserMobile(userDto.getMobile());
        invoiceDto.setStatus(1);
        invoiceDto.setCreateTime(new Date());
        invoiceDto.setCreator(userDto.getUuid());
    }


}
