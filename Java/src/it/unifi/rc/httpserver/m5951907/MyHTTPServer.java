package it.unifi.rc.httpserver.m5951907;

import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPServer;
import it.unifi.rc.httpserver.m5951907.handler.MyHTTPHandler;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class MyHTTPServer implements HTTPServer {

	private List<HTTPHandler> otherHandlers = new Vector<>();
	private MyHTTPHandler cmdChainHead;

	@Override
	public void addHandler(HTTPHandler handler) {
		if (handler instanceof MyHTTPHandler) {
			((MyHTTPHandler) handler).setNextHandler(cmdChainHead);
			cmdChainHead = (MyHTTPHandler) handler;
		} else
			otherHandlers.add(handler);
	}

	@Override
	public void start() throws IOException {

	}

	@Override
	public void stop() {

	}
}
