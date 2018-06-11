package it.unifi.rc.httpserver.m5951907;

import it.unifi.rc.httpserver.HTTPProtocolException;

public class MyHTTPProtocolException extends HTTPProtocolException {

	private final int code;
	private final String msg;
	private final String verboseMsg;

	public MyHTTPProtocolException(int errorCode, String errorMsg, String verboseErrMsg) {
		super(errorCode + " " + errorMsg);
		this.code = errorCode;
		this.msg = errorMsg;
		this.verboseMsg = verboseErrMsg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public String getVerboseMsg() {
		return verboseMsg;
	}
}
