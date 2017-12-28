package it.unifi.rc.httpserver.m5951907.server;

import it.unifi.rc.httpserver.HTTPInputStream;
import it.unifi.rc.httpserver.HTTPOutputStream;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPInputStream;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPOutputStream;

import java.io.IOException;
import java.net.Socket;

/**
 * this class makes no sense at all
 */
public class Client {

	private Socket s;
	private HTTPInputStream is;
	private HTTPOutputStream os;

	private boolean closed = false;

	Client(Socket client) throws IOException {
		this.s = client;
		this.is = new MyHTTPInputStream(client.getInputStream());
		this.os = new MyHTTPOutputStream(client.getOutputStream());
	}

	public HTTPInputStream getInputStream() {
		return is;
	}

	public HTTPOutputStream getOutputStream() {
		return os;
	}

	public void close() {
		try {
			closed = true;
			s.close();
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isClosed() {
		return closed;
	}
}
