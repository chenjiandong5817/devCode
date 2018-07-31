package com.plugs.module_member.dtos;

import com.util.PropertiesUtil;

import java.io.Serializable;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Administrator on 2017/7/24.
 */
public class RequestBaseInfo implements Serializable {
    private String ServiceCode;
    private String ChannelId;
    private String ExternalReference;
    private String OriginalChannelId;
    private String OriginalReference;
    private String RequestTime;
    private String RequestBranchCode;
    private String TermType;
    private String TermNo;

    public RequestBaseInfo() {
        //配置一些默认信息
        Properties propt = PropertiesUtil.getFileProperties();
        try {
            ChannelId= URLEncoder.encode(propt.getProperty("ChannelId"), "utf-8");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsssss");
            String date = sdf.format(new Date());
            RequestTime=date;
        } catch (Exception e) {
            ChannelId= "XY60101";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsssss");
            String date = sdf.format(new Date());
            RequestTime=date;
        }
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String serviceCode) {
        ServiceCode = serviceCode;
    }

    public String getChannelId() {
        return ChannelId;
    }

    public void setChannelId(String channelId) {
        ChannelId = channelId;
    }

    public String getExternalReference() {
        return ExternalReference;
    }

    public void setExternalReference(String externalReference) {
        ExternalReference = externalReference;
    }

    public String getOriginalChannelId() {
        return OriginalChannelId;
    }

    public void setOriginalChannelId(String originalChannelId) {
        OriginalChannelId = originalChannelId;
    }

    public String getOriginalReference() {
        return OriginalReference;
    }

    public void setOriginalReference(String originalReference) {
        OriginalReference = originalReference;
    }

    public String getRequestTime() {
        return RequestTime;
    }

    public void setRequestTime(String requestTime) {
        RequestTime = requestTime;
    }

    public String getRequestBranchCode() {
        return RequestBranchCode;
    }

    public void setRequestBranchCode(String requestBranchCode) {
        RequestBranchCode = requestBranchCode;
    }

    public String getTermType() {
        return TermType;
    }

    public void setTermType(String termType) {
        TermType = termType;
    }

    public String getTermNo() {
        return TermNo;
    }

    public void setTermNo(String termNo) {
        TermNo = termNo;
    }
}
