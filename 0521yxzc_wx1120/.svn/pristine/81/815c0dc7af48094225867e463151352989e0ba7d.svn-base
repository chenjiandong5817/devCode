package com.plugs.module_wechat.api;

import com.exception.BizException;
import com.plugs.base.AjaxList;
import com.plugs.base.BaseExtendApi;
import com.plugs.constants.ConfigConstants;
import com.plugs.constants.ReturnCodeConstants;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.services.OrderService;
import com.plugs.module_user.dtos.UserElectronInvoiceDto;
import com.plugs.module_user.dtos.UserHistoryInvoiceDto;
import com.plugs.module_user.dtos.UserPaperInvoiceDto;
import com.plugs.module_user.dtos.UserPassengerDto;
import com.plugs.module_user.services.UserElectronInvoiceService;
import com.plugs.module_user.services.UserHistoryInvoiceService;
import com.plugs.module_user.services.UserPaperInvoiceService;
import com.plugs.utils.NumberFormatUtil;
import com.util.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发票API
 *
 * @author Zoro
 * @since 2016/9/24
 */
@Controller
@RequestMapping("/wechat/userInvoice")
@Api(value = "用户发票接口", description = "用户发票接口")
public class WeChatUserInvoiceApi extends BaseExtendApi {
    private Logger logger = Logger.getLogger(WeChatUserInvoiceApi.class);

    @Autowired
    private UserElectronInvoiceService userElectronInvoiceService;
    @Autowired
    private UserPaperInvoiceService userPaperInvoiceService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserHistoryInvoiceService userHistoryInvoiceService;

    @RequestMapping(value = "/available", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取可开发票额度", notes = "绑获取可开发票额度接口信息", httpMethod = "GET")
    public AjaxList available(@ApiParam(name = "openId",value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                              HttpServletRequest request) throws Exception {
        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);//获取登录的用户
        double value = userHistoryInvoiceService.available(userPassengerDto);
        return AjaxList.createSuccess("获取成功", NumberFormatUtil.roundAndFormatToInt(value));
    }

    @RequestMapping(value = "/unBillingTrip", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取未开票行程", notes = "获取未开票行程的接口信息", httpMethod = "GET")
    public AjaxList unBillingTrip(@ApiParam(name = "openId",value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                                  @ApiParam(name = "pageNum", value = "页码") @RequestParam(required = false) Integer pageNum,
                                  HttpServletRequest request) throws Exception {
        //根据用户去获取该用户的未开票行程
        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);//获取登录的用户
        Map<String, Object> params = MapUtil.buildMap();
        params.put("passengeUuid", userPassengerDto.getUuid());//用户id
        params.put("mainStatus", OrderDto.ORDER_MAIN_STATUS_FINISH);//订单状态已完成
        params.put("isBilled", OrderDto.ORDER_BILL_DEFAULT_UNBILL);//未开票
        params.put("orderSource", 3); // TODO：是否只需要来源为微信订单
        params.put("sortField", "deparTime");
        params.put("sortOrder", "DESC");
        params.put("payFareNotNull", 1); //实际支付> 0
        List<OrderDto> orders = orderService.queryPage(params, super.dealPageNum(pageNum), ConfigConstants.PAGESIZE_10);
        return AjaxList.createSuccess(orders.size(), "获取成功", orders);
    }


    @RequestMapping(value = "/electron/generate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "电子发票开票", notes = "电子发票开票开票的接口信息", httpMethod = "POST")
    public AjaxList electronGenerate(@ApiParam(name = "openId",value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                                     @ApiParam(name = "buyername", required = true, value = "购方名称[发票抬头]") @RequestParam String buyername,
                                     @ApiParam(name = "taxnum", required = true, value = "购方税号") @RequestParam String taxnum,
                                     @ApiParam(name = "ordertotal", required = true, value = "发票总金额") @RequestParam double ordertotal,
                                     @ApiParam(name = "email", required = true, value = "电子邮箱") @RequestParam String email,
                                     @ApiParam(name = "accountType", required = true, value = "账号类型（1：企业，2：个人）") @RequestParam Integer accountType,
                                     @ApiParam(name = "account", required = false, value = "购方银行账号【更多信息中】") @RequestParam(required = false) String account,
                                     @ApiParam(name = "address", required = false, value = "购方地址【更多信息中】") @RequestParam(required = false) String address,
                                     @ApiParam(name = "telephone", required = false, value = "购方电话【更多信息中】") @RequestParam(required = false) String telephone,
                                     @ApiParam(name = "message", required = false, value = "备注【更多信息中】") @RequestParam(required = false) String message,
                                     @ApiParam(name = "orderUuids", required = false, value = "订单UUID[英文,号隔开]") @RequestParam(required = false) String orderUuids,
                                     @ApiParam(name = "ticketType", required = true, value = "开票类型（1：行程，2：金额）") @RequestParam Integer ticketType,
                                     HttpServletRequest request) throws Exception {
        logger.info("电子发票开票接口调用" + System.currentTimeMillis());
        //根据用户所选行程,判断开票总额是否超过可开票额度
        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);
        UserElectronInvoiceDto invoiceDto = super.getModel(request, UserElectronInvoiceDto.class); //反射获取

        invoiceDto.setPhone(userPassengerDto.getMobile());
        invoiceDto.setCreator(userPassengerDto.getUuid());

        double available = userHistoryInvoiceService.available(userPassengerDto);//可开票额度
        //判断金额是否超过可开票金额
        if (invoiceDto.getOrdertotal() > Double.parseDouble(NumberFormatUtil.roundAndFormatToInt(available))) {
            throw new BizException("超过可开发票总额,请重新选择", ReturnCodeConstants.ERR_10011_INVOICE_LIMIT_ERR);
        }
        JSONObject jsonObject = userElectronInvoiceService.electronGenerate(invoiceDto);
        if (!jsonObject.getBoolean("status")) {
            return AjaxList.createError("开票失败");//返回电子发票ID
        }
        return AjaxList.createSuccess("开票成功");//返回可开发票金额
    }


    @RequestMapping(value = "/electron/generateQuery", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "电子发票开票查询", notes = "电子发票开票开票的接口信息", httpMethod = "POST")
    public AjaxList electronGenerateQuery(@ApiParam(name = "openId",value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                                          @ApiParam(name = "invoiceUuid", required = true, value = "电子发票UUID") @RequestParam String invoiceUuid
                                        ) throws Exception {
        logger.info("电子发票开票查询接口调用" + System.currentTimeMillis() + ":" + invoiceUuid);
        JSONObject jsonObject = userElectronInvoiceService.electronGenerateQuery(invoiceUuid, 1);
        if (!jsonObject.getBoolean("status")) {
            return AjaxList.createError("开票查询失败");
        }
        return AjaxList.createSuccess("开票查询成功");
    }


    @RequestMapping(value = "/paper/generate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "纸质发票开票", notes = "纸质发票开票的接口信息", httpMethod = "POST")
    public AjaxList paperGenerate(@ApiParam(name = "openId",value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                                  @ApiParam(name = "type", required = true, value = "发票类型（1按行程2按金额）") @RequestParam int type,
                                  @ApiParam(name = "headerType", required = true, value = "抬头类型 1:公司，2：个人") @RequestParam int headerType,
                                  @ApiParam(name = "header", required = true, value = "发票抬头") @RequestParam String header,
                                  @ApiParam(name = "taxNumber", required = false, value = "购方税号") @RequestParam(required = false) String taxNumber,
                                  @ApiParam(name = "money", required = true, value = "发票金额") @RequestParam double money,
                                  @ApiParam(name = "content", required = true, value = "发票内容") @RequestParam String content,
                                  @ApiParam(name = "remark", required = false, value = "备注") @RequestParam(required = false) String remark,
                                  @ApiParam(name = "regAddress", required = false, value = "注册地址") @RequestParam(required = false) String regAddress,
                                  @ApiParam(name = "regMobile", required = false, value = "注册电话") @RequestParam(required = false) String regMobile,
                                  @ApiParam(name = "bankAccount", required = false, value = "开户行及账号") @RequestParam(required = false) String bankAccount,
                                  @ApiParam(name = "recipient", required = false, value = "收件人") @RequestParam(required = false) String recipient,
                                  @ApiParam(name = "mobile", required = false, value = "联系电话") @RequestParam(required = false) String mobile,
                                  @ApiParam(name = "area", required = false, value = "所在地区") @RequestParam(required = false) String area,
                                  @ApiParam(name = "detailAddress", required = false, value = "详细地址") @RequestParam(required = false) String detailAddress,
                                  @ApiParam(name = "freightType", required = false, value = "运费支付方式 1：货到付款，2：免运费 ，3：提前付款 4.自取[可以不要]") @RequestParam(required = false) int freightType,
                                  @ApiParam(name = "orderUuids", required = false, value = "行程开票 关联的订单UUID 用逗号隔开") @RequestParam(required = false) String orderUuids,
                                  HttpServletRequest request) throws Exception {

        UserPaperInvoiceDto invoiceDto = super.getModel(request, UserPaperInvoiceDto.class);
        logger.info("纸质发票开票接口调用" + System.currentTimeMillis());
        //根据用户所选行程,判断开票总额是否超过可开票额度
        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);

        double available = userHistoryInvoiceService.available(userPassengerDto);//可开票额度
        //判断金额是否超过可开票金额
        if (invoiceDto.getMoney() > Double.parseDouble(NumberFormatUtil.roundAndFormatToInt(available))) {
            throw new BizException("超过可开发票总额,请重新选择", ReturnCodeConstants.ERR_10011_INVOICE_LIMIT_ERR);
        }
        return userPaperInvoiceService.paperGenerate(invoiceDto, userPassengerDto);
    }


    @RequestMapping(value = "/paper/generateQuery", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "纸质发票开票查询", notes = "纸质发票开票查询的接口信息", httpMethod = "POST")
    public AjaxList paperGenerateQuery(@ApiParam(name = "openId",value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                                       @ApiParam(name = "invoiceUuid", required = true, value = "纸质发票UUID") @RequestParam String invoiceUuid
                                       ) throws Exception {
        logger.info("电子发票开票查询接口调用" + System.currentTimeMillis() + ":" + invoiceUuid);
        return userPaperInvoiceService.paperGenerateQuery(invoiceUuid);
    }


    @RequestMapping(value = "/electron/history", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "电子发票开票历史", notes = "电子发票开票历史的接口信息", httpMethod = "POST")
    public AjaxList electronHistory(@ApiParam(name = "openId",value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                                    @ApiParam(name = "pageNum", value = "页码") @RequestParam(required = false) Integer pageNum,
                                    HttpServletRequest request) throws Exception {
        logger.info("电子发票开票历史接口调用" + pageNum + openId);
        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);//获取登录的用户
        Map<String, Object> params = MapUtil.buildMap();
        params.put("creator", userPassengerDto.getUuid());
        pageNum = dealPageNum(pageNum);

        List<UserElectronInvoiceDto> userInvoiceDtos = userElectronInvoiceService.queryPage(params, pageNum, ConfigConstants.PAGESIZE_10);
        for (UserElectronInvoiceDto userInvoiceDto : userInvoiceDtos) {
            if (StringUtils.isNotBlank(userInvoiceDto.getOrderUuids())) {
                List<OrderDto> orderDtos = new ArrayList<OrderDto>();
                for (String orderId : userInvoiceDto.getOrderUuids().split(",")) {
                    params.clear();
                    params.put("uuid", orderId);
                    OrderDto orderDto = orderService.selInfo(params);
                    if (null != orderDto) {
                        orderDtos.add(orderDto);
                    }
                }
                userInvoiceDto.setOrderList(orderDtos);
            }
        }
        return AjaxList.createSuccess(userInvoiceDtos.size(), "获取成功", userInvoiceDtos);
    }


    @RequestMapping(value = "/invoice/history", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "发票开票历史", notes = "发票开票历史的接口信息[新版]", httpMethod = "POST")
    public AjaxList invoiceHistory(@ApiParam(name = "openId",value = "微信openid", required = true) @RequestParam(value = "openId") String openId,
                                   @ApiParam(name = "pageNum", value = "页码") @RequestParam(required = false) Integer pageNum,
                                   HttpServletRequest request) throws Exception {
        logger.info("发票开票历史接口调用" + pageNum + openId);
        UserPassengerDto userPassengerDto = super.getUserByOpenId(request, openId);//获取登录的用户
        Map<String, Object> params = MapUtil.buildMap();
        params.put("creator", userPassengerDto.getUuid());
        pageNum = dealPageNum(pageNum);

        params.clear();
        params.put("userUuid", userPassengerDto.getUuid());
        List<String> invoiceUuids = userHistoryInvoiceService.getInvoiceUuids(params, pageNum, ConfigConstants.PAGESIZE_10);
        if (invoiceUuids != null && invoiceUuids.size() > 0) {
            String[] uuids = invoiceUuids.toArray(new String[invoiceUuids.size()]);
            params.clear();
            params.put("uuids", uuids);
            List<UserHistoryInvoiceDto> historyInvoiceDtos = userHistoryInvoiceService.historyInvoiceDtos(params);
            for (UserHistoryInvoiceDto historyInvoiceDto : historyInvoiceDtos) {
                if (StringUtils.isNotBlank(historyInvoiceDto.getOrderUuids())) {
                    List<OrderDto> orderDtos = new ArrayList<OrderDto>();
                    for (String orderId : historyInvoiceDto.getOrderUuids().split(",")) {
                        params.clear();
                        params.put("uuid", orderId);
                        orderDtos.add(orderService.selInfo(params));
                    }
                    historyInvoiceDto.setOrderList(orderDtos);
                }
            }
            return AjaxList.createSuccess(historyInvoiceDtos.size(), "获取成功", historyInvoiceDtos);
        }
        return AjaxList.createSuccess(0, "获取成功", null);
    }

}
