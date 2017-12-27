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

public class MyHTTPHandler1_1 extends MyHTTPHandler1_0 {

	public MyHTTPHandler1_1(File root) {
		super(root);
	}

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
					return implementDELETE(req.getPath());
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

	private HTTPReply implementDELETE(String path) throws HTTPProtocolException {
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
