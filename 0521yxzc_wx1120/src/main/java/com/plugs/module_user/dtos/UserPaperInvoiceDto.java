package com.plugs.module_user.dtos;

import com.plugs.module_order.dtos.OrderDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 纸质发票
 *
 * @outhor qfHan
 * @create 2018-01-30 16:12
 */
public class UserPaperInvoiceDto {

    private String uuid;        //发票ID
    private String userUuid; //客户UUID
    //发票详情
    private int type;        //发票类型（1按行程2按金额）
    private int headerType; //抬头类型 1:公司，2：个人 新增
    private String header;        //发票抬头
    private String taxNumber; //纳税人识别号 新增
    private double money;        //发票金额
    private String content;        //发票内容

    //更多信息
    private String remark; //备注
    private String regAddress; //注册地址 新增
    private String regMobile; //注册电话 新增
    private String bankAccount; //开户行及账号 新增

    //收件信息
    private String recipient;        //收件人
    private String mobile;        //联系电话
    private String area;        //所在地区
    private String detailAddress;        //详细地址

    //运费支付
    private int freightType; //运费支付方式 1：货到付款，2：免运费 ，3：提前付款 4.自取[可以不要] 新增
    private double freightMoney; //需支付运费 新增
    private Date freightTime; //寄出时间 新增
    private String courierCompany; //快递公司 新增
    private String courierNote; //快递备注 新增
    private String singleNumber;  //161028 新增物流单号信息

    private String userMobile; //(申请账号)用户手机 新增
    /**
     * 1：待开票【开票中】2：待寄出【已开票】，3：已寄出【已开票】,4:自取【已开票】5：已取消【已取消】 6：已作废【已退票】
     * 原线上状态：发票状态(0审核中，1审核通过,-1审核失败)
     */
    private int status;
    private String orderUuids; //行程开票 关联的订单UUID 用逗号隔开 新增
    //非表字段用于 按行程开票获取订单信息
    private List<OrderDto> orderDtoList = new ArrayList<OrderDto>();

    //管理员操作记录
    private String admNote; //管理员备注 新增
    private String closeNote; //取消原因 新增
    private Date closeTime; //取消时间 新增
    private String invalidNote; //作废原因 新增
    private Date invalidTime; //作废时间 新增
    private int invoiceSource; //开票者 1:乘客自主 2：后台自助 新增

    private Date invoiceTime; //开票时间 新增
    private String invoiceCode; //发票编码 新增

    private Date createTime;        //创建时间
    private String creator;        //创建者
    private Date updateTime;        //更新时间
    private String updator;        //

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHeaderType() {
        return headerType;
    }

    public void setHeaderType(int headerType) {
        this.headerType = headerType;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getRegMobile() {
        return regMobile;
    }

    public void setRegMobile(String regMobile) {
        this.regMobile = regMobile;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public double getFreightMoney() {
        return freightMoney;
    }

    public void setFreightMoney(double freightMoney) {
        this.freightMoney = freightMoney;
    }

    public Date getFreightTime() {
        return freightTime;
    }

    public void setFreightTime(Date freightTime) {
        this.freightTime = freightTime;
    }

    public String getCourierCompany() {
        return courierCompany;
    }

    public void setCourierCompany(String courierCompany) {
        this.courierCompany = courierCompany;
    }

    public String getCourierNote() {
        return courierNote;
    }

    public void setCourierNote(String courierNote) {
        this.courierNote = courierNote;
    }

    public String getSingleNumber() {
        return singleNumber;
    }

    public void setSingleNumber(String singleNumber) {
        this.singleNumber = singleNumber;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderUuids() {
        return orderUuids;
    }

    public void setOrderUuids(String orderUuids) {
        this.orderUuids = orderUuids;
    }

    public String getAdmNote() {
        return admNote;
    }

    public void setAdmNote(String admNote) {
        this.admNote = admNote;
    }

    public String getCloseNote() {
        return closeNote;
    }

    public void setCloseNote(String closeNote) {
        this.closeNote = closeNote;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getInvalidNote() {
        return invalidNote;
    }

    public void setInvalidNote(String invalidNote) {
        this.invalidNote = invalidNote;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public int getInvoiceSource() {
        return invoiceSource;
    }

    public void setInvoiceSource(int invoiceSource) {
        this.invoiceSource = invoiceSource;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Date getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public List<OrderDto> getOrderDtoList() {
        return orderDtoList;
    }

    public void setOrderDtoList(List<OrderDto> orderDtoList) {
        this.orderDtoList = orderDtoList;
    }
}
