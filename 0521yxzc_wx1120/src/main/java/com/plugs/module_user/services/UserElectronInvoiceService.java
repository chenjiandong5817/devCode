/*
 * SummerSoft  YueYue-Travel Platform
 * <p>
 * Copyright (c) 2017-2018  SummerSoft Technology (Xiamen) Co.,LTD
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of SummerSoft
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SummerSoft.
 * <p>
 */

package com.plugs.module_user.services;

import com.base.BaseServiceSupport;
import com.base.IMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plugs.module_order.dtos.OrderDto;
import com.plugs.module_order.mappers.OrderMapper;
import com.plugs.module_user.dtos.UserElectronInvoiceBaseDto;
import com.plugs.module_user.dtos.UserElectronInvoiceDetailDto;
import com.plugs.module_user.dtos.UserElectronInvoiceDto;
import com.plugs.module_user.dtos.UserHistoryInvoiceDto;
import com.plugs.module_user.mappers.UserElectronInvoiceMapper;
import com.plugs.thread.InvoiceQueryThread;
import com.plugs.utils.DESDZFP;
import com.plugs.utils.GuoDouSmsUtils;
import com.util.DateUtil;
import com.util.MapUtil;
import com.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.catalina.connector.Response;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserElectronInvoiceService extends BaseServiceSupport<UserElectronInvoiceDto> {

    private Logger logger = Logger.getLogger(UserElectronInvoiceService.class);

    @Autowired
    private UserElectronInvoiceMapper<UserElectronInvoiceDto> userElectronInvoiceMapper;
    @Autowired
    private OrderMapper<OrderDto> orderMapper;
    @Autowired
    private UserHistoryInvoiceService userHistoryInvoiceService;

    @Override
    public IMapper<UserElectronInvoiceDto> getMapper() {
        return userElectronInvoiceMapper;
    }

    @Override
    public String getPK() {
        return "uuid";
    }

    public Double sum(Map<String, Object> params) {
        return userElectronInvoiceMapper.sum(params);
    }

    @Value("${nn.taxnum}")
    private String taxnum;

    @Value("${nn.identity}")
    private String identity;

    @Value("${nn.generateUrl}")
    private String generateUrl;

    @Value("${nn.queryUrl}")
    private String queryUrl;




    /**
     * 第三方电子发票生成
     * @param orderJsonStr
     * @return
     */
    private String generate(String orderJsonStr){
        String fpqqlsh = null;
        try {
            String result = sendRq(generateUrl,DESDZFP.encrypt(orderJsonStr));
            if(StringUtils.isNotBlank(result)){
                JSONObject jsonResult = JSONObject.fromObject(result);
                if("0000".equals(jsonResult.getString("status"))){
                    fpqqlsh = jsonResult.getString("fpqqlsh");
                }
                logger.info(orderJsonStr + " 请求生成响应结果：" + result);
            }
        } catch (Exception e) {
            logger.error("电子发票生成请求异常：" + e.getMessage());
            e.printStackTrace();
        }
        return fpqqlsh;
    }

    /**
     * 第三方电子发票查询（流水号）
     * @param orderJsonStr
     * @return
     */
    public JSONObject queryByFpqqlsh(String orderJsonStr){
        JSONObject json = new JSONObject();
        json.put("status",false);
        try {
            String result = sendRq(queryUrl,DESDZFP.encrypt(orderJsonStr));
            if(StringUtils.isNotBlank(result)){
                JSONObject jsonResult = JSONObject.fromObject(result);
                if("success".equals(jsonResult.getString("result"))){
                    JSONArray list = jsonResult.getJSONArray("list");
                    if(CollectionUtils.isNotEmpty(list)){
                        if("2".equals(list.getJSONObject(0).getString("c_status"))){
                            json = list.getJSONObject(0);
                            json.put("status",true);
                        }
                    }
                }
                logger.info(orderJsonStr + " 请求查询响应结果：" + result);
            }
            json.put("responseJson",result);
        } catch (Exception e) {
            logger.error("电子发票查询请求异常：" + e.getMessage());
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 电子发票生成
     * @param invoiceDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject electronGenerate(UserElectronInvoiceDto invoiceDto){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",false);
        try {
            invoiceDto.setUuid(StringUtil.buildUUID());

            invoiceDto.setOrderno("NO" + System.currentTimeMillis()); //订单号
            invoiceDto.setSaletaxnum(taxnum); //销方税号
            invoiceDto.setSaleaddress("中国（福建）自由贸易试验区厦门片区高崎南三路9号厦门机场T4候机楼到达层东侧"); //销方地址
            invoiceDto.setSalephone("0592-5700207"); //销方电话
            invoiceDto.setSaleaccount("4100020309200016859"); //销方银行账号

            invoiceDto.setClerk("林艳蕊"); //开票员
            invoiceDto.setPayee("");//收款人 林艳蕊
            invoiceDto.setChecker("");//复核人 林婷婷
            invoiceDto.setInvoicedate(DateUtil.format(new Date())); //开票时间
            invoiceDto.setKptype(1); //开票类型:1,正票;2,红 票
            invoiceDto.setFpdm(""); //对应蓝票发票代码
            invoiceDto.setFphm(""); //对应蓝票发票号码
            invoiceDto.setQdbz(1); //清单标志:0,根据项目 名称数，自动产生清 单;1,将项目信息打印 至清单
            invoiceDto.setQdxmmc("客运服务费"); //清单项目名称:打印 清单时对应发票票面 项目名称，注意：税 总要求清单项目名称 为（详见销货清单）
            invoiceDto.setTsfs(0); //推 送 方 式 :-1, 不 推 送;0,邮箱;1,手机(默 认);2,邮箱、手机
            invoiceDto.setDetailList(initDetail(invoiceDto.getOrdertotal(), "1"));

            UserElectronInvoiceBaseDto baseDto = new UserElectronInvoiceBaseDto();
            BeanUtils.copyProperties(baseDto,invoiceDto); //拷贝一份 用于生产电子发票
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = objectMapper.writeValueAsString(baseDto);
            JSONObject json = new JSONObject();
            json.put("identity",identity);
            json.put("order",jsonStr);
            String fpqqlsh = generate(json.toString()); //生成电子发票流水号

            if(StringUtils.isNotBlank(fpqqlsh)){
                invoiceDto.setStatus(1); //状态(1：开票中，2：已开票，3：退票中，4：已退票)
                invoiceDto.setFpqqlsh(fpqqlsh); //发票流水号
                invoiceDto.setRequestJson(json.toString()); //生成发票请求JSON
                invoiceDto.setCreateTime(new Date());
                logger.info("");
                userElectronInvoiceMapper.add(invoiceDto);

                //添加发票记录
                UserHistoryInvoiceDto historyInvoiceDto = new UserHistoryInvoiceDto();
                historyInvoiceDto.setUuid(StringUtil.buildUUID());
                historyInvoiceDto.setInvoiceUuid(invoiceDto.getUuid());
                historyInvoiceDto.setUserUuid(invoiceDto.getCreator());
                historyInvoiceDto.setType(1);
                historyInvoiceDto.setCreateTime(new Date());
                userHistoryInvoiceService.add(historyInvoiceDto);

                if(StringUtils.isNotBlank(invoiceDto.getOrderUuids())){
                    Map<String, Object> params = MapUtil.buildMap();
                    params.put("isBilled", 1);//已开票
                    params.put("orderUuids", invoiceDto.getOrderUuids().split(","));
                    orderMapper.batchEdit(params);//更新 TODO:
                }
                jsonObject.put("status",true);
//                jsonObject.put("uuid",invoiceDto.getUuid());
                InvoiceQueryThread invoiceQueryThread = new InvoiceQueryThread(this, invoiceDto.getUuid(), 1);
                Thread thread = new Thread(invoiceQueryThread);
                thread.start();
            }
        } catch (Exception e) {
            logger.error("电子发票开票异常：" + e.getMessage());
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 电子发票生成查询
     * @param uuid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject electronGenerateQuery(String uuid, Integer type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",false);
        Map<String, Object> params = MapUtil.buildMap();
        params.put("uuid", uuid);
        UserElectronInvoiceDto invoiceDto = userElectronInvoiceMapper.selInfo(params); //电子发票实体
        if(null != invoiceDto){
            UserElectronInvoiceDto updInvoiceDto = new UserElectronInvoiceDto();
            updInvoiceDto.setUuid(invoiceDto.getUuid());

            JSONObject jsonQy = new JSONObject();
            jsonQy.put("identity",identity);
            List<String> lshList = new ArrayList<String>();
            if(type.equals(1)){
                lshList.add(invoiceDto.getFpqqlsh());
            }else{
                lshList.add(invoiceDto.getCancelFpqqlsh()); //退票发票流水号
            }
            jsonQy.put("fpqqlsh",lshList);
            JSONObject resultJson = queryByFpqqlsh(jsonQy.toString());
            if(resultJson.getBoolean("status")){
                if(type.equals(1)){
                    updInvoiceDto.setFpdm(resultJson.getString("c_fpdm"));
                    updInvoiceDto.setFphm(resultJson.getString("c_fphm"));
                    updInvoiceDto.setPdfUrl(resultJson.getString("c_url")); //发票PDF下载地址
                    updInvoiceDto.setResponseJson(resultJson.getString("responseJson")); //生成发票响应JSON
                    updInvoiceDto.setStatus(2);
                    //TODO:待修改
                    String INVOICE_SEND_PASSENGER_NOTICE = "【元翔专车】您于email申请的电子发票已开出，您可前往发票申请页，或者登陆您的邮箱，查看发票信息，感谢使用！";
                    try {
                        String msgContent = INVOICE_SEND_PASSENGER_NOTICE
                                .replaceAll("email", invoiceDto.getEmail());
                        GuoDouSmsUtils.sendContentSmsUtf8(invoiceDto.getPhone(), msgContent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    updInvoiceDto.setCancelPdfUrl(resultJson.getString("c_url")); //退票发票PDF下载地址
                    updInvoiceDto.setCancelResponseJson(resultJson.getString("responseJson")); //退票生成发票响应JSON
                    updInvoiceDto.setStatus(4); //4：已退票
                }

                updInvoiceDto.setUpdateTime(new Date());
                userElectronInvoiceMapper.edit(updInvoiceDto);
                jsonObject.put("status",true);
            }
        }
        return jsonObject;
    }


    /**
     * 子项目初始化
     * @param price
     * @return
     */
    private List<UserElectronInvoiceDetailDto> initDetail(double price, String type){
        List<UserElectronInvoiceDetailDto> detailDtoList = new ArrayList<UserElectronInvoiceDetailDto>();
        UserElectronInvoiceDetailDto detailDto = new UserElectronInvoiceDetailDto();
        detailDto.setGoodsname("客运服务费");
        detailDto.setSpec("1");
        detailDto.setUnit("1");
        detailDto.setHsbz("1");
        detailDto.setNum(type); //生票为1，退票为-1
        detailDto.setPrice(price);
        detailDto.setSpbm("3010101020299");
        detailDto.setFphxz(0);
        detailDto.setYhzcbs(0);
        detailDto.setZzstsgl("222222");
        detailDto.setLslbs("");
        detailDto.setTaxrate("0.03");
        detailDtoList.add(detailDto);
        return detailDtoList;
    }

    private static String sendRq(String sendUrl, String orderDES) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        //POST的URL
        HttpPost httppost = new HttpPost(sendUrl);
        //建立HttpPost对象
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        //建立一个NameValuePair数组，用于存储欲传送的参数
        params.add(new BasicNameValuePair("order",orderDES));
        //添加参数
        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        //设置编码
        HttpResponse response = client.execute(httppost);
        //发送Post,并返回一个HttpResponse对象
        if(Response.SC_OK == response.getStatusLine().getStatusCode()) {//如果状态码为200,就是正常返回
            return EntityUtils.toString(response.getEntity());
        }
        return null;
    }
}
