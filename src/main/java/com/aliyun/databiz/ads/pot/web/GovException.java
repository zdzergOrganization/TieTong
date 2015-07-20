package com.aliyun.databiz.ads.pot.web;

/**
 * @author 徐冲（丁卯） 2015-06-16
 * @version 1.0
 */
public class GovException extends Exception {

	private ErrorCode code;

	public GovException(ErrorCode code) {
		this(code.getMsg());
		this.code = code;
	}

	public GovException() {
	}

	public GovException(String message) {
		super(message);
	}

	public GovException(String message, Throwable cause) {
		super(message, cause);
	}

	public GovException(Throwable cause) {
		super(cause);
	}

	public GovException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErrorCode getErrorCode() {
		return code;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName());
		String message = getLocalizedMessage();
		if (message != null) {
			sb.append(": ").append(message);
		}
		if (code != null) {
			sb.append(", ErrCode: ").append(this.code.getCode());
			sb.append('(').append(code.getMsg()).append(')');
		}
		return sb.toString();
	}
}
