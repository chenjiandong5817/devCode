package com.plugs.module_user.dtos;

import com.plugs.module_order.dtos.OrderDto;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 开票历史
 *
 * @outhor qfHan
 * @create 2018-02-23 11:24
 */
public class UserHistoryInvoiceDto {

    private String uuid;
    private int type; //1纸质发票,2.电子发票
    private String invoiceUuid; //发票UUID
    private String userUuid; //用户UUID
    private Date createTime; //开票时间 [申请时间]

    //非表字段
    private int ticketType; //开票类型（1：行程，2：金额）
    private int status; //状态(1：开票中，2：已开票，3：退票中，4：已退票)
    private String invoicedate;//开票时间
    private int accountType;//账号类型（1：企业，2：个人）
    private String buyername; //发票抬头
    private String taxnum; //纳税号
    private double ordertotal;//发票总金额
    private String qdxmmc; //发票内容
    private String message; //备注
    private String address; //地址
    private String telephone; //电话
    private String account; //开户行及账号

    private String recipient; //收件人
    private String mobile; //联系电话
    private String area; //所在地区
    private String detailAddress; //收件地址
    private int freightType; //运费方式 1：货到付款，2：免运费 ，3：提前付款 4.自取[可以不要]

    private String email; //邮箱地址
    private String pdfUrl; //发票PDF下载地址

    private List<OrderDto> orderList; //订单列表
    private String orderUuids;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getTicketType() {
        return ticketType;
    }

    public void setTicketType(int ticketType) {
        this.ticketType = ticketType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInvoiceUuid() {
        return invoiceUuid;
    }

    public void setInvoiceUuid(String invoiceUuid) {
        this.invoiceUuid = invoiceUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    public String getTaxnum() {
        return taxnum;
    }

    public void setTaxnum(String taxnum) {
        this.taxnum = taxnum;
    }

    public double getOrdertotal() {
        return ordertotal;
    }

    public void setOrdertotal(double ordertotal) {
        this.ordertotal = ordertotal;
    }

    public String getQdxmmc() {
        return qdxmmc;
    }

    public void setQdxmmc(String qdxmmc) {
        this.qdxmmc = qdxmmc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public int getFreightType() {
        return freightType;
    }

    public void setFreightType(int freightType) {
        this.freightType = freightType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public List<OrderDto> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDto> orderList) {
        this.orderList = orderList;
    }

    public String getOrderUuids() {
        return orderUuids;
    }

    public void setOrderUuids(String orderUuids) {
        this.orderUuids = orderUuids;
    }
}
