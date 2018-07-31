package com.exception;

/**
 * 业务异常
 */
public class BizException extends RuntimeException {

    private Integer errCode;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BizException(String msg) {
        super(msg);
    }

    public BizException(String msg, Integer errCode) {
        super(msg);
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return errCode;
    }
}
