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

	private void createHeaderParMap(String header) {
		headerMap = new HashMap<>();
		final String[] split = header.split("\r\n");
		for (String s : split)
			headerMap.put(s.substring(0, s.indexOf(':')), s.substring(s.indexOf(' ') + 1));
	}

	private void splitReqLine(String reqLine) throws HTTPProtocolException {
		StringBuilder sb = new StringBuilder(reqLine);
		try {
			method = sb.substring(0, sb.indexOf(" "));
			sb.replace(0, sb.indexOf(" ") + 1, "");

			url = sb.substring(0, sb.indexOf(" "));
			sb.replace(0, sb.indexOf(" ") + 1, "");

			version = sb.substring(0, sb.indexOf("\r\n"));
		} catch (StringIndexOutOfBoundsException e) {
			throw new HTTPProtocolException("bad reqLine syntax");
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
