package it.unifi.rc.httpserver.m5951907.handler;

import java.io.File;
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
	protected List<String> getProtocolSupportedMethods() {
		return Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "CONNECT");
	}

	@Override
	protected String getProtocolVersion() {
		return "HTTP/1.1";
	}
}
