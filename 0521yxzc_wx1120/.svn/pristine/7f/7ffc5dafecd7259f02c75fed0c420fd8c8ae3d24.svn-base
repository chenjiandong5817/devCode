package com.exception;

/**
 * 后台业务异常
 */
public class AdminBizException extends RuntimeException {

    private Integer errCode;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AdminBizException(String msg) {
        super(msg);
    }

    public AdminBizException(String msg, Integer errCode) {
        super(msg);
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return errCode;
    }
}
