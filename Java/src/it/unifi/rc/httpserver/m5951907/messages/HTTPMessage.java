package it.unifi.rc.httpserver.m5951907.messages;

import it.unifi.rc.httpserver.HTTPProtocolException;

import java.util.HashMap;
import java.util.Map;

abstract class HTTPMessage {

	private Map<String, String> headerMap;
	private String body;

	HTTPMessage(String firstLine, String header, String body) throws HTTPProtocolException {
		splitFirstLine(firstLine);
		if (header != null)
			createHeaderParMap(header);
		this.body = body;
	}

	private void splitFirstLine(String firstLine) throws HTTPProtocolException {
		if (!firstLine.endsWith("\r\n"))
			throw new HTTPProtocolException(getMessageType() + " Line does not have the proper End Characters");

		final String[] split = firstLine.split(" ", 3);
		try {
			setFirstLineParameter1(split[0]);
			setFirstLineParameter2(split[1]);
			setFirstLineParameter3(split[2].replace("\r\n", ""));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new HTTPProtocolException("Bad Syntax on " + getMessageType() + " Line: missing spaces");
		}
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

	abstract String getMessageType();

	abstract void setFirstLineParameter1(String par);

	abstract void setFirstLineParameter2(String par);

	abstract void setFirstLineParameter3(String par);

	Map<String, String> getParameters() {
		return headerMap;
	}

	String getBody() {
		return body;
	}
}
