package com.tietong.web.conf;

public class Constants {

	public static final String SESSION_USER_NAME = "user_name";
	public static final String SESSION_USER_ID = "user_id";
	
	/**
	 * 默认授权有效时间1年
	 */
	public static final long APPROVAL_DEFAULT_TIME = 86400 * 365 * 1;
	
	public static final int APPROVAL_AUDIT_PASS = 1;
	public static final int APPROVAL_AUDIT_REFUSE = 2;
	
	public static final String LOGIN_FAILED_MSG = "请确认用户名和密码正确";
	public static final String PRIVILEGE_APPLY_MSG = "申请已提交，请等待审批";
	public static final String PRIVILEGE_APPLY_ERROR_MSG = "请确认资源是否存在";
	
}
