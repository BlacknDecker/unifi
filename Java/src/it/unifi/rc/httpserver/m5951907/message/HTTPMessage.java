package it.unifi.rc.httpserver.m5951907.message;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A class that abstract common characteristics of HTTP messages.
 *
 * @author Simone Cipriani, 5951907
 */
abstract class HTTPMessage {

	private Map<String, String> headerMap;
	private String body;

	/**
	 * Construct an HTTP message without specifying any overhead information.
	 *
	 * @param body of the message, the payload
	 */
	HTTPMessage(String body) {
		this.body = body;
		try {
			String header = "Date: " + DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()) + "\r\n" + "Host: " + InetAddress.getLocalHost().getHostName();
			createHeaderParMap(header);
			// add other standard header fields?
		} catch (HTTPProtocolException | UnknownHostException e1) {
			e1.printStackTrace();
			// will never happen, proper format is hard-coded
		}
	}

	/**
	 * Construct an HTTP message which encapsulate all the overhead information,
	 * depending on the message subtype.
	 *
	 * @param firstLine of the full message
	 * @param header    a string object containing the whole lot of header lines
	 * @param body      as the payload of the message
	 * @throws HTTPProtocolException if the first line or the header lines container could not be parsed
	 */
	HTTPMessage(String firstLine, String header, String body) throws HTTPProtocolException {
		splitFirstLine(firstLine);
		if (header != null)
			createHeaderParMap(header);
		this.body = body;
	}

	/**
	 * Split the first line of the HTTP message and initialize the fields encapsulating those information.
	 *
	 * @param firstLine of the HTTP message
	 * @throws HTTPProtocolException if bad syntax is found in the first line
	 */
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

	/**
	 * Parse the string container of the message header to initialize the map encapsulating
	 * the header fields.
	 *
	 * @param header lines string container
	 * @throws HTTPProtocolException if poor syntax is found
	 */
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

	/**
	 * Self explanatory.
	 *
	 * @return the header map object
	 */
	Map<String, String> getParameters() {
		return headerMap;
	}

	/**
	 * Self explanatory.
	 *
	 * @return the string body
	 */
	String getBody() {
		return body;
	}

	/**
	 * Should be implemented by subclasses to specify the parameters they wants during
	 * instantiation of MyHTTPProtocolException.
	 *
	 * @param verboseMsg as a verbose description of the condition causing the exception
	 * @return the exception object, ready to be thrown
	 */
	abstract MyHTTPProtocolException getCustomException(String verboseMsg);

	/**
	 * Setter for the first parameter that appears in the first line of the message overhead.
	 *
	 * @param par the parameter, I.E. HTTP method
	 */
	abstract void setFirstLineParameter1(String par);

	/**
	 * Setter for the second parameter that appears in the first line of the message overhead.
	 *
	 * @param par the parameter, I.E. requested url
	 */
	abstract void setFirstLineParameter2(String par);

	/**
	 * Setter for the third parameter that appears in the first line of the message overhead.
	 *
	 * @param par the parameter, I.E. HTTP protocol version
	 */
	abstract void setFirstLineParameter3(String par);

	/**
	 * Merge the first line parameters, recomposing the first line as it originally was.
	 *
	 * @return the recomposed first line of the message
	 */
	abstract String recomposeFirstLine();

	/**
	 * Overridden toString method, to get the original message back together.
	 *
	 * @return the entire message as a string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(recomposeFirstLine());
		sb.append("\r\n");
		for (String k : headerMap.keySet()) {
			sb.append(k);
			sb.append(": ");
			sb.append(headerMap.get(k));
			sb.append("\r\n");
		}
		sb.append("\r\n");
		sb.append(body);

		return sb.toString();
	}
}
