package it.unifi.rc.httpserver.m5951907.handler;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MyHTTPHandler1_0 extends AbstractHTTPHandler {

	public MyHTTPHandler1_0(File root) {
		super(root);
	}

	@Override
	protected List<String> getProtocolSupportedMethods() {
		return Arrays.asList("GET", "HEAD", "POST");
	}

	@Override
	protected String getProtocolVersion() {
		return "HTTP/1.0";
	}


}
