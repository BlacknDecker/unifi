package it.unifi.rc.httpserver.m5951907.message;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;

import java.util.Map;

/**
 * A concrete implementation of an HTTP Request Message.
 *
 * @author Simone Cipriani, 5951907
 */
public class MyHTTPRequest extends HTTPMessage implements HTTPRequest {

	private String method, url, version;

	/**
	 * Construct an HTTP Request object using the superclass constructor.
	 *
	 * @param reqLine the first line of the message
	 * @param header  a string object containing the whole lot of header lines
	 * @param body    the body of the message
	 * @throws HTTPProtocolException if input parameters could not be parsed
	 */
	public MyHTTPRequest(String reqLine, String header, String body) throws HTTPProtocolException {
		super(reqLine, header, body);
	}

	/**
	 * Implementation of superclass method: exception generated while instantiating
	 * the request object are due to the string form of the request.
	 *
	 * @param verboseMsg as a verbose description of the condition causing the exception
	 * @return the constructed exception
	 */
	@Override
	MyHTTPProtocolException getCustomException(String verboseMsg) {
		return new MyHTTPProtocolException(400, "Bad Request", verboseMsg);
	}

	/**
	 * Implementation of superclass method: first parameter for request is method.
	 *
	 * @param par the parameter, I.E. "GET"
	 */
	@Override
	void setFirstLineParameter1(String par) {
		this.method = par;
	}

	/**
	 * Implementation of superclass method: second parameter for request is the url.
	 *
	 * @param par the parameter, I.E. "/path/to/somewhere"
	 */
	@Override
	void setFirstLineParameter2(String par) {
		this.url = par;
	}

	/**
	 * Implementation of superclass method: third parameter for request is the HTTP protocol version.
	 *
	 * @param par the parameter, I.E. "HTTP/1.0"
	 */
	@Override
	void setFirstLineParameter3(String par) {
		this.version = par;
	}

	/**
	 * Implementation of superclass method: just jam together the fields in the right order.
	 *
	 * @return recomposed status line
	 */
	@Override
	String recomposeFirstLine() {
		return method + " " + url + " " + version;
	}

	/**
	 * Implementation of interface method.
	 *
	 * @return the HTTP protocol version
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * Implementation of interface method.
	 *
	 * @return the HTTP method
	 */
	@Override
	public String getMethod() {
		return method;
	}

	/**
	 * Implementation of interface method.
	 *
	 * @return the url of the resource requested
	 */
	@Override
	public String getPath() {
		return url;
	}

	/**
	 * Implementation of interface method.
	 *
	 * @return the body of the HTTP message
	 */
	@Override
	public String getEntityBody() {
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
