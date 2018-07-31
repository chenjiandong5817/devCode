package com.plugs.module_enterprise.response;

/**
 * <p>
 * WebService請求返回結果共通Object.
 * </p>
 */
public class WsResponse {

	public WsResponse() {
		super();
	}

	public WsResponse(String status, Integer errCode, String message) {
		super();
		this.status = status;
		this.errCode = errCode;
		this.message = message;
	}

	/** 响应代码 **/
	protected String status;
	/** 错误代码 **/
	protected Integer errCode;
	/** 提示信息 **/
	protected String message;
	/** 访问成功后，获得的相应信息 **/
	protected Object data;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the errCode
	 */
	public Integer getErrCode() {
		return errCode;
	}

	/**
	 * @param errCode
	 *            the errCode to set
	 */
	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
