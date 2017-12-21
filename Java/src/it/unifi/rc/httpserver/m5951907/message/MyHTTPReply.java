package it.unifi.rc.httpserver.m5951907.message;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MyHTTPReply extends HTTPMessage implements HTTPReply {

	private String version, statusCode, statusMsg;

	public MyHTTPReply(String statusLine, String header, String data) throws HTTPProtocolException {
		super(statusLine, header, data);
	}

	// to quickly instantiate replies from bad requests
	public MyHTTPReply(MyHTTPProtocolException e, String version) {
		super(e.getVerboseMsg());
		this.version = version;
		this.statusCode = String.valueOf(e.getCode());
		this.statusMsg = e.getMsg();
		try {
			super.createHeaderParMap("Date: " + DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()) + "\r\n");
			// add other standard header fields?
		} catch (HTTPProtocolException e1) {
			e1.printStackTrace();
			// will never happen, proper format is hard-coded
		}
	}

	@Override
	protected MyHTTPProtocolException getCustomException(String verboseMsg) {
		return new MyHTTPProtocolException(500, "Internal Server Error", verboseMsg);
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
	String recomposeFirstLine() {
		return version + " " + statusCode + " " + statusMsg;
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
