package it.unifi.rc.httpserver.m5951907;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPRequest;

import java.util.HashMap;
import java.util.Map;

public class MyHttpRequest implements HTTPRequest {

	private String method, url, version, body;
	private Map<String, String> headerMap;

	public MyHttpRequest(String reqLine, String header, String body) throws HTTPProtocolException {
		splitReqLine(reqLine);
		if (header != null)
			createHeaderParMap(header);
		this.body = body;
	}

	private void createHeaderParMap(String header) throws HTTPProtocolException {
		if (!header.contains("\r\n"))
			throw new HTTPProtocolException("Header Lines do not have the proper End Characters");

		headerMap = new HashMap<>();
		final String[] split = header.split("\r\n");
		for (String s : split) {
			if (!s.contains(":"))
				throw new HTTPProtocolException("Header Line \"" + s + "\" non properly Formatted: missing char :");
			headerMap.put(s.substring(0, s.indexOf(':')), s.substring(s.indexOf(' ') + 1));
		}

	}

	private void splitReqLine(String reqLine) throws HTTPProtocolException {
		if (!reqLine.endsWith("\r\n"))
			throw new HTTPProtocolException("Req Line does not have the proper End Characters");
		final String[] split = reqLine.split(" ");
		try {
			method = split[0];
			url = split[1];
			version = split[2].replace("\r\n", "");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new HTTPProtocolException("Bad Syntax on Req Line: missing spaces");
		}
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
		return body;
	}

	@Override
	public Map<String, String> getParameters() {
		return headerMap;
	}
}
