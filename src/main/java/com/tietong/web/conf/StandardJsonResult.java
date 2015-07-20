package com.tietong.web.conf;

/**
 * 标准响应bean. 这个Bean在序列化为json后可以生成标准的HTTP响应格式。
 * 
 *
 * @author 周玎  2015-7-20 11:40:06
 * @version 1.0
 */
public class StandardJsonResult {

	/**
	 * 错误代码，非0值为错误，0为正常数据
	 * 
	 * @see ErrorCode
	 */
	private Integer errCode;
	/** 错误名称 */
	private String errName;
	/** 错误具体信息 */
	private String errMsg;
	/** 具体业务数据 */
	private Object data;

	public StandardJsonResult() {
		this.errCode = 0;
		this.errMsg = "success";
	}

	public StandardJsonResult(Object data) {
		this.errCode = 0;
		this.errMsg = "success";
		this.data = data;
	}

	public StandardJsonResult(ErrorCode errorCode, String errMsg, Object data) {
		this.errCode = Integer.valueOf(errorCode.getCode());
		this.errName = errorCode.name();//errorCode.getMsg();
		this.errMsg = errMsg;
		this.data = data;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrName() {
		return errName;
	}

	public void setErrName(String errName) {
		this.errName = errName;
	}

}
