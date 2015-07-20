/**
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 *
 * File generated at: 2015年1月20日下午4:57:31
 */
package com.aliyun.databiz.ads.pot.web;

/**
 * 标准响应bean. 这个Bean在序列化为json后可以生成标准的HTTP响应格式。
 * 
 *
 * @author 邬启明（激酶） 2015-4-27 11:40:06
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
