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

package com.plugs.module_user.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.plugs.module_order.dtos.OrderDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserElectronInvoiceDto extends UserElectronInvoiceBaseDto {

    @JsonIgnore
    private String uuid;//电子发票主键
    @JsonIgnore
    private String fpqqlsh;//发票流水号

    private String pdfUrl;//发票PDF下载地址
    @JsonIgnore
    private String requestJson;//生成发票请求JSON
    @JsonIgnore
    private String responseJson;//生成发票响应JSON
    @JsonIgnore
    private String cancelFpqqlsh;//退票发票流水号
    @JsonIgnore
    private String cancelPdfUrl;//退票发票PDF下载地址
    @JsonIgnore
    private String cancelRequestJson;//退票生成发票请求JSON
    @JsonIgnore
    private String cancelResponseJson;//退票生成发票响应JSON

    private Integer ticketType;//开票类型（1：行程，2：金额）

    private Integer accountType;//账号类型（1：企业，2：个人）
    @JsonIgnore
    private String cancelRemark;//退票备注

    private Integer status;//状态(1：开票中，2：已开票，3：退票中，4：已退票)
    @JsonIgnore
    private Date createTime;//创建时间
    @JsonIgnore
    private Date updateTime;//更新时间
    @JsonIgnore
    private String creator;//创建者
    @JsonIgnore
    private String updator;//更新者

    private String orderUuids;//订单主键集合

    private List<OrderDto> orderList = new ArrayList<OrderDto>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFpqqlsh() {
        return fpqqlsh;
    }

    public void setFpqqlsh(String fpqqlsh) {
        this.fpqqlsh = fpqqlsh;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    public String getCancelFpqqlsh() {
        return cancelFpqqlsh;
    }

    public void setCancelFpqqlsh(String cancelFpqqlsh) {
        this.cancelFpqqlsh = cancelFpqqlsh;
    }

    public String getCancelPdfUrl() {
        return cancelPdfUrl;
    }

    public void setCancelPdfUrl(String cancelPdfUrl) {
        this.cancelPdfUrl = cancelPdfUrl;
    }

    public String getCancelRequestJson() {
        return cancelRequestJson;
    }

    public void setCancelRequestJson(String cancelRequestJson) {
        this.cancelRequestJson = cancelRequestJson;
    }

    public String getCancelResponseJson() {
        return cancelResponseJson;
    }

    public void setCancelResponseJson(String cancelResponseJson) {
        this.cancelResponseJson = cancelResponseJson;
    }

    public Integer getTicketType() {
        return ticketType;
    }

    public void setTicketType(Integer ticketType) {
        this.ticketType = ticketType;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getOrderUuids() {
        return orderUuids;
    }

    public void setOrderUuids(String orderUuids) {
        this.orderUuids = orderUuids;
    }

    public List<OrderDto> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDto> orderList) {
        this.orderList = orderList;
    }

}
