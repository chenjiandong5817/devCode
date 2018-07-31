package com.plugs.module_user.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.plugs.module_driver.dtos.DriverAccountDetailDto;
import com.plugs.module_driver.mappers.DriverAccountDetailMapper;
import com.plugs.module_user.dtos.UserAccountDetailDto;
import com.plugs.module_user.dtos.UserHistoryInvoiceDto;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.mappers.UserAccountDetailMapper;
import com.plugs.module_user.mappers.UserHistoryInvoiceMapper;
import com.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 描述:
 * 开票历史
 *
 * @outhor qfHan
 * @create 2018-01-31 14:13
 */
@Service
public class UserHistoryInvoiceService extends BaseServiceSupport<UserHistoryInvoiceDto> {

    @Autowired
    private UserAccountDetailMapper<UserAccountDetailDto> userAccountDetailMapper;
    @Autowired
    private DriverAccountDetailMapper<DriverAccountDetailDto> driverAccountDetailMapper;
    @Autowired
    private UserHistoryInvoiceMapper<UserHistoryInvoiceDto> userHistoryInvoiceMapper;

    @Override
    public IMapper<UserHistoryInvoiceDto> getMapper() {
        return userHistoryInvoiceMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

    public List<String> getInvoiceUuids(Map<String, Object> params, int pageNum, int pageSize) {
        PageBounds pageBounds = new PageBounds(pageNum, pageSize);
        return this.userHistoryInvoiceMapper.getInvoiceUuids(params, pageBounds);
    }

    public List<UserHistoryInvoiceDto> historyInvoiceDtos(Map<String, Object> params) {
        return this.userHistoryInvoiceMapper.historyInvoiceDtos(params);
    }

    /**
     * 获取可用额度
     *
     * @param userPassengerDto
     * @return
     */
    public Double available(UserPassengerDto userPassengerDto) {
        //申请开具发票时，显示可开票的总金额；
        // 所有通过充值、第三方支付、现金支付（司机待支付）进行付款的，都属于可开票金额；
        // 电子券抵扣金额、充值赠送的金额不属于可开票金额

        //根据用户在平台的消费情况和已经开票情况进行查询统计，计算出用户可开发票额度
        //消费记录为开票+充值记录=总的可开发票金额

        Double value = 0D;
        //先查询充值实付总额
        Map<String, Object> params = MapUtil.buildMap();
        params.put("sumField", "money-giftMoney");//統計字段：总金额-赠送金额
        params.put("type", UserAccountDetailDto.TYPE_RECHARGE);//第三方充值
        params.put("payed", UserAccountDetailDto.PAYED_OK);
        params.put("status", UserAccountDetailDto.STATUS_SUCCESS);
        params.put("userUuid", userPassengerDto.getUuid());//用户
        Double rechargePayAmount = userAccountDetailMapper.sum(params);

        //赠送的金额
        /**
         params.clear();
         params.put("sumField", "giftMoney");//統計字段：赠送金额
         params.put("type", UserAccountDetailDto.TYPE_RECHARGE);//第三方充值
         params.put("payed", UserAccountDetailDto.PAYED_OK);
         params.put("status", UserAccountDetailDto.STATUS_SUCCESS);
         params.put("userUuid", userPassengerDto.getUuid());//用户
         Double giftMoney = userAccountDetailService.sum(params);
         **/

        //第三方直接消費支付的金額
        params.clear();
        params.put("sumField", "money");//統計字段：总金额
        params.put("type", UserAccountDetailDto.TYPE_SEPND_3RD);//第三方消费
        params.put("payed", UserAccountDetailDto.PAYED_OK);
        params.put("status", UserAccountDetailDto.STATUS_SUCCESS);
        params.put("userUuid", userPassengerDto.getUuid());//用户
        Double spend3RdAmount = userAccountDetailMapper.sum(params);


        //司机代支付金额
        Double driverGiveMoney = driverAccountDetailMapper.sumDriverPayWithPassenger(userPassengerDto.getUuid());

        //已开发票总额
        params.clear();
        params.put("userUuid", userPassengerDto.getUuid());//用户
        double billAmount = userHistoryInvoiceMapper.sum(params);

        value = rechargePayAmount + spend3RdAmount + driverGiveMoney - billAmount;
        return value;
    }

}
