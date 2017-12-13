package it.unifi.rc.httpserver.m5951907.message;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;

import java.util.Map;

public class MyHTTPReply extends HTTPMessage implements HTTPReply {

	private String version, statusCode, statusMsg;

	public MyHTTPReply(String statusLine, String header, String data) throws HTTPProtocolException {
		super(statusLine, header, data);
	}

	@Override
	String getMessageType() {
		return "Response";
	}

	@Override
	void setFirstLineParameter1(String par) {
		this.version = par;
	}

	@Override
	void setFirstLineParameter2(String par) {
		this.statusCode = par;
	}

	@Override
	void setFirstLineParameter3(String par) {
		this.statusMsg = par;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String getStatusCode() {
		return statusCode;
	}

	@Override
	public String getStatusMessage() {
		return statusMsg;
	}

	@Override
	public String getData() {
		return super.getBody();
	}

	@Override
	public Map<String, String> getParameters() {
		return super.getParameters();
	}
}
