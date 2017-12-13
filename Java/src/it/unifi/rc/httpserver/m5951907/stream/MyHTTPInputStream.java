package it.unifi.rc.httpserver.m5951907.stream;

import it.unifi.rc.httpserver.HTTPInputStream;
import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPReply;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MyHTTPInputStream extends HTTPInputStream {

	private InputStream is;
	private Scanner scanner;

	@SuppressWarnings("unused")
	public MyHTTPInputStream(InputStream is) {
		super(is);
	}

	public MyHTTPInputStream(String s) {
		super(new ByteArrayInputStream(s.getBytes()));
	}

	@Override
	protected void setInputStream(InputStream is) {
		this.is = is;
		this.scanner = new Scanner(is);
	}

	@Override
	public HTTPRequest readHttpRequest() throws HTTPProtocolException {
		return new MyHTTPRequest(readFirstLine(), readHeader(), readBody());
	}

	@Override
	public HTTPReply readHttpReply() throws HTTPProtocolException {
		return new MyHTTPReply(readFirstLine(), readHeader(), readBody());
	}

	private String readFirstLine() {
		String limit = "\r\n", line = "";
		scanner.useDelimiter(limit);
		if (scanner.hasNext())
			line = scanner.next();
		scanner.skip(limit);
		return line;
	}

	private String readHeader() {
		String limit = "\r\n\r\n", header = null;
		scanner.useDelimiter(limit);
		if (scanner.hasNext())
			header = scanner.next();
		scanner.skip(limit);
		return header;
	}

	private String readBody() {
		StringBuilder body = new StringBuilder();
		while (scanner.hasNextLine())
			body.append(scanner.nextLine());
		return body.toString();
	}

	@Override
	public void close() throws IOException {
		scanner.close();
		is.close();
	}
}
