package it.unifi.rc.httpserver.m5951907.message;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;

import java.util.Map;

public class MyHTTPRequest extends HTTPMessage implements HTTPRequest {

	private String method, url, version;

	public MyHTTPRequest(String reqLine, String header, String body) throws HTTPProtocolException {
		super(reqLine, header, body);
	}

	@Override
	MyHTTPProtocolException getCustomException(String verboseMsg) {
		return new MyHTTPProtocolException(400, "Bad Request", verboseMsg);
	}

	@Override
	void setFirstLineParameter1(String par) {
		this.method = par;
	}

	@Override
	void setFirstLineParameter2(String par) {
		this.url = par;
	}

	@Override
	void setFirstLineParameter3(String par) {
		this.version = par;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String getMethod() {
		return method;
	}

	@Override
	public String getPath() {
		return url;
	}

	@Override
	public String getEntityBody() {
		return super.getBody();
	}

	@Override
	public Map<String, String> getParameters() {
		return super.getParameters();
	}
}
