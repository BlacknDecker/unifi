package it.unifi.rc.httpserver.m5951907.message;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;

import java.util.Map;

/**
 * A concrete implementation of an {@link HTTPReply}.
 *
 * @author Simone Cipriani, 5951907
 */
public class MyHTTPReply extends HTTPMessage implements HTTPReply {

	private String version, statusCode, statusMsg;

	/**
	 * Construct an {@link HTTPReply} object bouncing parameters to the superclass constructor.
	 *
	 * @param statusLine of the response
	 * @param header     a {@link String} object containing the whole lot of header lines
	 * @param data       the body of the message
	 * @throws HTTPProtocolException if input parameters could not be parsed
	 */
	public MyHTTPReply(String statusLine, String header, String data) throws HTTPProtocolException {
		super(statusLine, header, data);
	}

	/**
	 * Construct a basic {@link HTTPReply} based on an exception generated
	 * by an incoming request (I.E. bad syntax).
	 *
	 * @param e       the {@link MyHTTPProtocolException} object that was thrown
	 * @param version of the HTTP protocol in use
	 */
	public MyHTTPReply(MyHTTPProtocolException e, String version) {
		super(e.getVerboseMsg());
		this.version = version;
		this.statusCode = String.valueOf(e.getCode());
		this.statusMsg = e.getMsg();
	}

	/**
	 * Implementation of superclass method: exception generated while generating
	 * the reply object are Internal Server Error.
	 *
	 * @param verboseMsg as a verbose description of the condition causing the exception
	 * @return the constructed exception
	 */
	@Override
	protected MyHTTPProtocolException getCustomException(String verboseMsg) {
		return new MyHTTPProtocolException(500, "Internal Server Error", verboseMsg);
	}

	/**
	 * Implementation of superclass method: first parameter for reply is version.
	 *
	 * @param par the parameter, I.E. "HTTP/1.0"
	 */
	@Override
	void setFirstLineParameter1(String par) {
		this.version = par;
	}

	/**
	 * Implementation of superclass method: second parameter for reply is status code.
	 *
	 * @param par the parameter, I.E. "200"
	 */
	@Override
	void setFirstLineParameter2(String par) {
		this.statusCode = par;
	}

	/**
	 * Implementation of superclass method: third parameter for reply is status message.
	 *
	 * @param par the parameter, I.E. "OK"
	 */
	@Override
	void setFirstLineParameter3(String par) {
		this.statusMsg = par;
	}

	/**
	 * Implementation of superclass method: just jam together the fields in the right order.
	 *
	 * @return recomposed status line
	 */
	@Override
	String recomposeFirstLine() {
		return version + " " + statusCode + " " + statusMsg;
	}

	/**
	 * Implementation of interface method.
	 *
	 * @return the HTTP version
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * Implementation of interface method.
	 *
	 * @return the HTTP status code
	 */
	@Override
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * Implementation of interface method.
	 *
	 * @return the HTTP status message
	 */
	@Override
	public String getStatusMessage() {
		return statusMsg;
	}

	/**
	 * Implementation of interface method.
	 *
	 * @return the body of the HTTP message
	 */
	@Override
	public String getData() {
		return super.getBody();
	}

	/**
	 * Implementation of interface method: bounce the superclass method getParameters().
	 *
	 * @return the header parameters map
	 */
	@Override
	public Map<String, String> getParameters() {
		return super.getParameters();
	}
}
