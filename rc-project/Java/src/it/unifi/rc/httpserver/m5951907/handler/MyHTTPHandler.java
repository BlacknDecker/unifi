package it.unifi.rc.httpserver.m5951907.handler;

import it.unifi.rc.httpserver.HTTPHandler;

public interface MyHTTPHandler extends HTTPHandler {

	void setNextHandler(HTTPHandler handler);
}
