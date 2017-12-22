package it.unifi.rc.httpserver.m5951907.handler;

import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPReply;

import java.io.File;
import java.util.List;

abstract class AbstractHTTPHandler implements HTTPHandler {

	private final File root; //TODO
	private HTTPHandler next;

	AbstractHTTPHandler(File root) {
		this(root, null);
	}

	AbstractHTTPHandler(File root, HTTPHandler next) {
		this.root = root;
		setNextHandler(next);
	}

	protected void setNextHandler(HTTPHandler handler) {
		this.next = handler;
	}

	@Override
	public HTTPReply handle(HTTPRequest request) {
		try {
			testMethod(request.getMethod());
			testVersion(request.getVersion());
		} catch (MyHTTPProtocolException e) {
			return new MyHTTPReply(e, getProtocolVersion());
		}

		HTTPReply res = handlingImplementation(request);
		if (res != null)
			return res;

		if (next != null)
			return next.handle(request);
		else
			return null;
	}

	private void testVersion(String version) throws MyHTTPProtocolException {
		if (!getProtocolVersion().equals(version))
			throw new MyHTTPProtocolException(505, "HTTP Version Not Supported", "PROTOCOL VERSION" + version + " SPECIFIED IN REQUEST IS NOT SUPPORTED");
	}

	private void testMethod(String method) throws MyHTTPProtocolException {
		if (!getProtocolSupportedMethods().contains(method))
			throw new MyHTTPProtocolException(501, "Not Implemented", "METHOD " + method + " SPECIFIED IN REQUEST IS NOT IMPLEMENTED");
	}

	protected abstract List<String> getProtocolSupportedMethods();

	protected abstract String getProtocolVersion();

	protected abstract HTTPReply handlingImplementation(HTTPRequest req);
}
