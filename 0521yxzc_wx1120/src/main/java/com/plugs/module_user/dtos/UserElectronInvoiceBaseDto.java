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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UserElectronInvoiceBaseDto {

    private String orderno;//订单号
    private String saletaxnum;//销方税号
    private String saleaddress;//销方地址
    private String salephone;//销方电话
    private String saleaccount;//销方银行账号
    private String clerk;//开票员
    private String payee;//收款人
    private String checker;//复核人
    private String invoicedate;//开票时间
    private Double ordertotal;//发票总金额
    private Integer kptype;//开票类型:1,正票;2,红 票
    private String address;//购方地址
    private String phone;//购方手机(开票成功 会短信提醒购方)
    private String taxnum;//购方税号
    private String buyername;//购方名称
    private String account;//购方银行账号
    private String message;//备注
    private Integer tsfs;//推 送 方 式 :-1, 不 推 送;0,邮箱;1,手机(默 认);2,邮箱、手机
    private String email;//推送邮箱（tsfs 为 0 或 2 时，此项为必填）
    private Integer qdbz;//清单标志:0,根据项目 名称数，自动产生清 单;1,将项目信息打印 至清单
    private String qdxmmc;//清单项目名称:打印 清单时对应发票票面 项目名称，注意：税 总要求清单项目名称 为（详见销货清单）
    private String telephone;//购方电话
    private String fpdm;//对应蓝票发票代码
    private String fphm;//对应蓝票发票号码
    @JsonProperty("detail")
    private List<UserElectronInvoiceDetailDto> detailList = new ArrayList<UserElectronInvoiceDetailDto>();

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getSaletaxnum() {
        return saletaxnum;
    }

    public void setSaletaxnum(String saletaxnum) {
        this.saletaxnum = saletaxnum;
    }

    public String getSaleaddress() {
        return saleaddress;
    }

    public void setSaleaddress(String saleaddress) {
        this.saleaddress = saleaddress;
    }

    public String getSalephone() {
        return salephone;
    }

    public void setSalephone(String salephone) {
        this.salephone = salephone;
    }

    public String getSaleaccount() {
        return saleaccount;
    }

    public void setSaleaccount(String saleaccount) {
        this.saleaccount = saleaccount;
    }

    public String getClerk() {
        return clerk;
    }

    public void setClerk(String clerk) {
        this.clerk = clerk;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public Double getOrdertotal() {
        return ordertotal;
    }

    public void setOrdertotal(Double ordertotal) {
        this.ordertotal = ordertotal;
    }

    public Integer getKptype() {
        return kptype;
    }

    public void setKptype(Integer kptype) {
        this.kptype = kptype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTaxnum() {
        return taxnum;
    }

    public void setTaxnum(String taxnum) {
        this.taxnum = taxnum;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTsfs() {
        return tsfs;
    }

    public void setTsfs(Integer tsfs) {
        this.tsfs = tsfs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getQdbz() {
        return qdbz;
    }

    public void setQdbz(Integer qdbz) {
        this.qdbz = qdbz;
    }

    public String getQdxmmc() {
        return qdxmmc;
    }

    public void setQdxmmc(String qdxmmc) {
        this.qdxmmc = qdxmmc;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFpdm() {
        return fpdm;
    }

    public void setFpdm(String fpdm) {
        this.fpdm = fpdm;
    }

    public String getFphm() {
        return fphm;
    }

    public void setFphm(String fphm) {
        this.fphm = fphm;
    }

    public List<UserElectronInvoiceDetailDto> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<UserElectronInvoiceDetailDto> detailList) {
        this.detailList = detailList;
    }
}
