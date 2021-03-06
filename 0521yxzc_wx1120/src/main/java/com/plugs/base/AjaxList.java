package com.plugs.base;

import com.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * To change this template use File | Settings | File Templates.
 */
public class AjaxList {

    private int counts;
    private String msg;
    private boolean success;
    private Object data;
    private int errCode;


    public AjaxList() {

    }

    public AjaxList(int counts, Object data) {
        this.counts = counts;
        this.data = data;
    }

    public AjaxList(int counts, String msg, int errCode, boolean success, Object data) {
        this.counts = counts;
        this.msg = msg;
        this.success = success;
        this.data = data;
        this.errCode = errCode;
    }

    public AjaxList(int counts, String msg, boolean success, Object data) {
        this.counts = counts;
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public static AjaxList createSuccess(int counts) {
        return new AjaxList(counts, "", true, null);
    }

    public static AjaxList createSuccess(String msg) {
        return new AjaxList(1, msg, true, null);
    }

    public static AjaxList createSuccess(String msg, Object data) {
        return new AjaxList(0, msg, true, data);
    }

    public static AjaxList createSuccess(int count, String msg, Object data) {
        return new AjaxList(count, msg, true, data);
    }

    public static AjaxList createError(String msg, int errCode) {
        return new AjaxList(0, msg, errCode, false, null);
    }

    public static AjaxList createError(String msg) {
        return new AjaxList(0, msg, false, null);
    }

    public static AjaxList createError(String msg, Object data) {
        return new AjaxList(0, msg, false, data);
    }

    public static AjaxList createError(String msg, Object data,int errCode) {
        return new AjaxList(0, msg, errCode,false, data);
    }

    public int getCounts() {
        return counts;
    }

    public Object getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

}
