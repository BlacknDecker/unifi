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
import java.util.Map;

/**
 * Implements an handler that serves HTTP/1.1 requests.
 * N.B. not all methods specified by the 1.1 protocol has been implemented:
 * TRACE and CONNECT returns a 501 Not Implemented response.
 *
 * @author Simone Cipriani, 5951907
 */
public class MyHTTPHandler1_1 extends MyHTTPHandler1_0 {

	/**
	 * Basic constructor, initializes the handler to serve independently of the requesting host.
	 *
	 * @param root the {@link File} object abstracting the root path
	 */
	public MyHTTPHandler1_1(File root) {
		super(root);
	}

	/**
	 * Construct the handler to serve only a specific host.
	 *
	 * @param host the host to serve
	 * @param root the {@link File} object abstracting the root path
	 */
	public MyHTTPHandler1_1(String host, File root) {
		super(host, root);
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
				case "PUT":
					return implementPUT(req.getPath(), req.getEntityBody());
				case "DELETE":
					return implementDELETE(req.getPath(), req.getParameters());
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
	 * Basic implementation of the PUT method.
	 *
	 * @param path       in which the entity will be created
	 * @param entityBody to be written in the entity
	 * @return a {@link HTTPReply}, as specified by the protocol
	 * @throws HTTPProtocolException if something goes nuts
	 */
	private HTTPReply implementPUT(String path, String entityBody) throws HTTPProtocolException {
		StringBuilder head = HTTPMessage.getStdHeaderFields();
		head.append("Content-Location");
		head.append(": ");
		head.append(path);

		HTTPReply res;
		if (fetchResource(path).exists())
			res = new MyHTTPReply("HTTP/1.1 204 No Content", head.toString(), "");
		else
			res = new MyHTTPReply("HTTP/1.1 201 Created", head.toString(), "");

		try {
			OutputStream os = new FileOutputStream(fetchResource(path));
			os.write(entityBody.getBytes());
			return res;
		} catch (IOException e) {
			throw new MyHTTPProtocolException(500, "Internal Server Error", "SOMETHING REALLY STRANGE HAPPENED: " + Arrays.toString(e.getStackTrace()));
		}
	}

	/**
	 * Basic implementation of the DELETE method.
	 * It mimics an authentication system by checking a certain header parameter.
	 * Nothing really usable in the real world, but it serves to show the concept.
	 *
	 * @param path of resource to be deleted
	 * @return a {@link HTTPReply}
	 * @throws HTTPProtocolException if something does not work
	 */
	private HTTPReply implementDELETE(String path, Map<String, String> pars) throws HTTPProtocolException {
		String password = pars.get("Authentication");
		if (password == null || !password.equals("slowpoke_tail"))
			return new MyHTTPReply("HTTP/1.1 401 Unauthorized", HTTPMessage.getStdHeaderFields().toString(), "");

		if (fetchResource(path).delete())
			return new MyHTTPReply("HTTP/1.1 202 Accepted", HTTPMessage.getStdHeaderFields().toString(), "");
		else
			return new MyHTTPReply("HTTP/1.1 204 No Content", HTTPMessage.getStdHeaderFields().toString(), "");
	}

	@Override
	protected List<String> getProtocolSupportedMethods() {
		return Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "CONNECT");
	}

	@Override
	protected String getProtocolVersion() {
		return "HTTP/1.1";
	}
}