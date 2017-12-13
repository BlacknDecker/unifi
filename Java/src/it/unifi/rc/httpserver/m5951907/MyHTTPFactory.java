package it.unifi.rc.httpserver.m5951907;

import it.unifi.rc.httpserver.*;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPInputStream;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPOutputStream;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

@SuppressWarnings("unused")
public class MyHTTPFactory implements HTTPFactory {

	@Override
	public HTTPInputStream getHTTPInputStream(InputStream is) {
		return new MyHTTPInputStream(is);
	}

	@Override
	public HTTPOutputStream getHTTPOutputStream(OutputStream os) {
		return new MyHTTPOutputStream(os);
	}

	@Override
	public HTTPServer getHTTPServer(int port, int backlog, InetAddress address, HTTPHandler... handlers) {
		//TODO
		return null;
	}

	@Override
	public HTTPHandler getFileSystemHandler1_0(File root) {
		//TODO
		return null;
	}

	@Override
	public HTTPHandler getFileSystemHandler1_0(String host, File root) {
		//TODO

		return null;
	}

	@Override
	public HTTPHandler getFileSystemHandler1_1(File root) {
		//TODO
		return null;
	}

	@Override
	public HTTPHandler getFileSystemHandler1_1(String host, File root) {
		//TODO
		return null;
	}

	@Override
	public HTTPHandler getProxyHandler() {
		//TODO
		return null;
	}
}
