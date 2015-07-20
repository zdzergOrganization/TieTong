/**
 * Copyright 2012 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.aliyun.databiz.ads.pot.web;


/**
 * 错误代码枚举类. <br>
 * 错误代码为0时代表正常，非0时代表异常，一般错误代码为负数。
 *
 * @author 邬启明（激酶） 2015-4-27 11:40:06
 * @version 1.0
 */
public enum ErrorCode {

	COMMON_UNDEFINED_ERROR(-1000, "未定义的错误"), //
	COMMON_UNSUPPORTED_PLUGIN_TYPE(-1001, "不支持的数据库插件类型"), //
	COMMON_NUMBER_FORMAT_ERROR(-1002, "非法的数字格式"),
	COMMON_DATE_FORMAT_ERROR(-1003, "非法的日期格式"),
	ODPS_SQL_ERROR(-3000, "ODPS SQL Error"), //
	ODPS_SQL_RUNNING(-3001, "Instance is running"), //
	ODPS_TABLE_LIST_ERROR(-3200, "Cannot list project tables"), //
	ODPS_TABLE_NOT_FOUND(-3201, "ODPS table not found"), //
	COMMON_MISSING_HTTP_REQ_PARAM(-1003, "HTTP请求参数缺失"), //
	COMMON_SQL_SYNTAX_ERROR(-1004, "SQL格式错误"), //
	COMMON_TRANSCATION_ROLLBACK(-1010, "事务回滚");

	/** 错误代码 */
	private int code;
	/** 错误描述 */
	private String msg;

	ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
