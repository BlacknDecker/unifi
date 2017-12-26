package it.unifi.rc.httpserver.m5951907.handler;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;
import it.unifi.rc.httpserver.m5951907.message.HTTPMessage;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPReply;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MyHTTPHandler1_0 extends AbstractHTTPHandler {

	private final String host;

	public MyHTTPHandler1_0(File root) {
		super(root);
		this.host = null;
	}

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
		if (host != null) {
			String reqHost = req.getParameters().get("Host");
			if (!reqHost.equals(this.host))
				return null; // I am not the one serving you
		}
		try {
			switch (req.getMethod()) {
				case "GET":
					return implementGET(req.getPath());
				case "HEAD":
					return implementHEAD(req.getPath());
				case "POST":
					return implementPOST(req.getPath(), req.getEntityBody());
				default:
					throw new MyHTTPProtocolException(404, "Not Found", "REQUESTED RESOURCE WAS NOT FOUND: " + req.getPath());
			}
		} catch (HTTPProtocolException ex) {
			if (ex instanceof MyHTTPProtocolException)
				throw (MyHTTPProtocolException) ex;
			else
				throw new MyHTTPProtocolException(500, "Internal Server Error", "SOMETHING REALLY STRANGE HAPPENED: " + Arrays.toString(ex.getStackTrace()));
		}
	}

	/**
	 * A basic (well, almost fake) implementation of the HTTP/1.0 POST method.
	 *
	 * @return a reply with no body
	 */
	private HTTPReply implementPOST(String path, String body) throws HTTPProtocolException {
		// do something meaningful with post body
		System.out.println("PATH TO POST TO:\n" + path);
		System.out.println("POSTED BODY:\n" + body);
		return new MyHTTPReply("HTTP/1.0 204 No Content", HTTPMessage.getStdHeaderFields().toString(), "");
	}

	/**
	 * A basic implementation of the HTTP/1.0 HEAD method.
	 *
	 * @return a reply with no body
	 */
	private HTTPReply implementHEAD(String url) throws HTTPProtocolException {
		return new MyHTTPReply("HTTP/1.0 200 OK", super.fetchResourceHeader(url), "");
	}

	/**
	 * A basic implementation of the HTTP/1.0 GET method.
	 *
	 * @param url of the requested resource
	 * @return a reply with the resource fetched as a body
	 * @throws MyHTTPProtocolException if anything bad with resource fetching (I.E. Not Found)
	 */
	private HTTPReply implementGET(String url) throws HTTPProtocolException {
		return new MyHTTPReply("HTTP/1.0 200 OK", super.fetchResourceHeader(url), super.fetchResource(url));
	}
}
