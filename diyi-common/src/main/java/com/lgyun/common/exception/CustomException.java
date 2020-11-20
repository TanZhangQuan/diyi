package com.lgyun.common.exception;

import lombok.Getter;
import com.lgyun.common.api.IResultCode;
import com.lgyun.common.api.ResultCode;

/**
 * Secure异常
 *
 * @author tzq
 */
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 2359767895161832954L;

	@Getter
	private final IResultCode resultCode;

	public CustomException(String message) {
		super(message);
		this.resultCode = ResultCode.INTERNAL_SERVER_ERROR;
	}

	public CustomException(IResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public CustomException(IResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
