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
public abstract class HTTPMessage {

	private Map<String, String> headerMap;
	private String body;

	/**
	 * Construct an HTTP message without specifying any overhead information.
	 *
	 * @param body of the message, the payload
	 */
	HTTPMessage(String body) {
		this(getStdHeaderFields().toString(), body);
	}

	/**
	 * Construct an HTTP message which encapsulate all the overhead information,
	 * depending on the message subtype.
	 *
	 * @param firstLine of the full message
	 * @param header    a {@link String} object containing the whole lot of header lines
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
	 * Construct an HTTP message specifying the header fields in a string container.
	 *
	 * @param header ensemble of header fields in message
	 * @param body   of the message, the payload
	 */
	private HTTPMessage(String header, String body) {
		this.body = body;
		try {
			createHeaderParMap(header);
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
			try {
				createHeaderParMap(getStdHeaderFields().toString());
			} catch (HTTPProtocolException ignored) {
				// should and will NOT happen, proper format is hard coded
			}
		}
	}

	/**
	 * Get a {@link StringBuilder} initialized with some standard header fields.
	 *
	 * @return the {@link StringBuilder}
	 */
	public static StringBuilder getStdHeaderFields() {
		StringBuilder sb = new StringBuilder();
		sb.append("Date: ");
		sb.append(DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
		sb.append("\r\n");

		sb.append("Host: ");
		try {
			sb.append(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			sb.append("unknown host");
		}
		sb.append("\r\n");

		return sb;
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
			throw getCustomException("BAD SYNTAX ON FIRST LINE: MISSING SPACE " + firstLine);
		}
	}

	/**
	 * Parse the string container of the message header to initialize the {@link Map} encapsulating
	 * the header fields.
	 *
	 * @param header lines {@link String} container
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
	 * @return the header {@link Map} object
	 */
	Map<String, String> getParameters() {
		return headerMap;
	}

	/**
	 * Self explanatory.
	 *
	 * @return the {@link String} body
	 */
	String getBody() {
		return body;
	}

	/**
	 * Should be implemented by subclasses to specify the parameters they wants during
	 * instantiation of {@link MyHTTPProtocolException}.
	 *
	 * @param verboseMsg as a verbose description of the condition causing the exception
	 * @return the {@link MyHTTPProtocolException} object, ready to be thrown
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
	 * Utility method for the toString override implementation.
	 *
	 * @return the recomposed first line of the message
	 */
	abstract String recomposeFirstLine();

	/**
	 * Overridden toString method, to get the original message back together.
	 *
	 * @return the entire message as a {@link String}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
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
		} catch (NullPointerException e) {
			return null;
		}
		return sb.toString();
	}
}
