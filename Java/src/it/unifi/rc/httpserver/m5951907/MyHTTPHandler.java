package it.unifi.rc.httpserver.m5951907;

import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;

import java.util.List;

public abstract class MyHTTPHandler implements HTTPHandler {

	// TODO


	@Override
	public HTTPReply handle(HTTPRequest request) {
		HTTPReply reply;

		return null;
	}

	private void testMethod(String method) throws MyHTTPProtocolException {


	}

	protected abstract List<String> getProtocolSupportedMethods();
}
