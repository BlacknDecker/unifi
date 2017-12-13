package it.unifi.rc.httpserver.m5951907.message;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;

import java.util.LinkedHashMap;
import java.util.Map;

abstract class HTTPMessage {

	private Map<String, String> headerMap;
	private String body;

	//protected static final String[] PROTOCOL_VERSIONS = {"HTTP/1.0", "HTTP/1.1"};

	HTTPMessage(String firstLine, String header, String body) throws HTTPProtocolException {
		splitFirstLine(firstLine);
		if (header != null)
			createHeaderParMap(header);
		this.body = body;
	}

	private void splitFirstLine(String firstLine) throws HTTPProtocolException {
		final String[] split = firstLine.split(" ", 3);
		try {
			setFirstLineParameter1(split[0]);
			setFirstLineParameter2(split[1]);
			setFirstLineParameter3(split[2].replace("\r\n", ""));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw getCustomException("BAD SYNTAX ON FIRST LINE: MISSING SPACE");
		}
	}

	private void createHeaderParMap(String header) throws HTTPProtocolException {
		if (!header.contains("\r\n"))
			throw getCustomException("HEADER LINES NOT SEPARATED BY PROPER CHARACTER");

		headerMap = new LinkedHashMap<>(); // has to be linked to preserve order
		final String[] split = header.split("\r\n");
		for (String s : split) {
			if (!s.contains(":"))
				throw getCustomException("HEADER LINE" + s + " NOT PROPERLY FORMATTED: MISSING CHAR :");
			headerMap.put(s.substring(0, s.indexOf(':')), s.substring(s.indexOf(' ') + 1));
		}
	}

	abstract MyHTTPProtocolException getCustomException(String verboseMsg);

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
