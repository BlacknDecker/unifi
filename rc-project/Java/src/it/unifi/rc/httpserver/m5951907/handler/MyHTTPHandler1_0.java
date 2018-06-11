package it.unifi.rc.httpserver.m5951907.handler;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;
import it.unifi.rc.httpserver.m5951907.message.HTTPMessage;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Implements an handler that serves HTTP/1.0 requests.
 *
 * @author Simone Cipriani, 5951907
 */
public class MyHTTPHandler1_0 extends AbstractHTTPHandler {

	private final String host;

	/**
	 * Basic constructor, initializes the handler to serve independently of the requesting host.
	 *
	 * @param root the {@link File} object abstracting the root path
	 */
	public MyHTTPHandler1_0(File root) {
		super(root);
		this.host = null;
	}

	/**
	 * Construct the handler to serve only a specific host.
	 *
	 * @param host the host to serve
	 * @param root the {@link File} object abstracting the root path
	 */
	public MyHTTPHandler1_0(String host, File root) {
		super(root);
		this.host = host;
	}

	@Override
	protected List<String> getProtocolSupportedMethods() {
		return Arrays.asList("GET", "HEAD", "POST");
	}

	@Override
	protected String getProtocolVersion() {
		return "HTTP/1.0";
	}

	@Override
	protected HTTPReply handlingImplementation(HTTPRequest req) throws MyHTTPProtocolException {
		if (checkHost(req))
			return null; // I am not the one serving you
		try {
			switch (req.getMethod()) {
				case "GET":
					return implementGET(req.getPath());
				case "HEAD":
					return implementHEAD(req.getPath());
				case "POST":
					return implementPOST(req.getPath(), req.getEntityBody());
				default:
					throw new MyHTTPProtocolException(501, "Not Implemented", "METHOD " + req.getMethod() + " SPECIFIED IN REQUEST IS NOT IMPLEMENTED");
			}
		} catch (HTTPProtocolException ex) {
			if (ex instanceof MyHTTPProtocolException)
				throw (MyHTTPProtocolException) ex;
			else
				throw new MyHTTPProtocolException(500, "Internal Server Error", "SOMETHING REALLY STRANGE HAPPENED: " + Arrays.toString(ex.getStackTrace()));
		}
	}

	/**
	 * Check if the host to serve matches the one indicated in the {@link HTTPRequest}.
	 *
	 * @param req the request
	 * @return true if host should not be served, false otherwise
	 */
	boolean checkHost(HTTPRequest req) {
		if (host != null) {
			String reqHost = req.getParameters().get("Host");
			return reqHost == null || !reqHost.equals(this.host);
		}
		return false;
	}

	/**
	 * A basic implementation of the HTTP/1.0 POST method.
	 * It does not do anything useful, just append to the resource indicated in path the body of the request.
	 * More "realistic" implementations can be done, but I think it is out of the scope of this project.
	 *
	 * @param path of the request
	 * @param body of the request
	 * @return an {@link HTTPReply} indicating success or failure
	 * @throws HTTPProtocolException if it is not possible to construct the {@link HTTPReply}
	 */
	HTTPReply implementPOST(String path, String body) throws HTTPProtocolException {
		try {
			OutputStream os = new FileOutputStream(fetchResource(path), true);
			os.write(body.getBytes());
			return new MyHTTPReply("HTTP/1.0 204 No Content", HTTPMessage.getStdHeaderFields().toString(), "");
		} catch (IOException e) {
			return new MyHTTPReply("HTTP/1.0 404 Not Found", HTTPMessage.getStdHeaderFields().toString(), Arrays.toString(e.getStackTrace()));
		}
	}

	/**
	 * A basic implementation of the HTTP/1.0 HEAD method.
	 *
	 * @param url of the request
	 * @return an {@link HTTPReply} indicating success or failure
	 * @throws HTTPProtocolException if it is not possible to construct the {@link HTTPReply}
	 */
	HTTPReply implementHEAD(String url) throws HTTPProtocolException {
		return new MyHTTPReply("HTTP/1.0 200 OK", super.readResourceHeader(url), "");
	}

	/**
	 * A basic implementation of the HTTP/1.0 GET method.
	 *
	 * @param url of the requested resource
	 * @return a reply with the resource fetched as a body
	 * @throws MyHTTPProtocolException if anything bad with resource fetching (I.E. Not Found)
	 */
	HTTPReply implementGET(String url) throws HTTPProtocolException {
		return new MyHTTPReply("HTTP/1.0 200 OK", super.readResourceHeader(url), super.readResource(url));
	}
}
