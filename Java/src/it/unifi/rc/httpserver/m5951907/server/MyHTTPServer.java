package it.unifi.rc.httpserver.m5951907.server;

import it.unifi.rc.httpserver.*;
import it.unifi.rc.httpserver.m5951907.handler.MyHTTPHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Vector;

/**
 * Serves one client at time
 */
public class MyHTTPServer implements HTTPServer {

	private boolean running = false;

	private List<HTTPHandler> otherHandlers = new Vector<>();
	private MyHTTPHandler cmdChainHead;

	private ServerSocket socket;
	private Client client;

	private int port, backlog;
	private InetAddress address;

	public MyHTTPServer(int port, int backlog, InetAddress address) {
		this.address = address;
		this.port = port;
		this.backlog = backlog;
	}

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
		socket = new ServerSocket(port, backlog, address);
		socket.setReuseAddress(true);
		socket.setSoTimeout(50);

		running = true;
		while (running) {
			accept();
			serve();
		}
	}

	private void accept() throws IOException {
		try {
			client = new Client(socket.accept());
		} catch (SocketTimeoutException ignored) {
			// respawn
		}
	}

	private void serve() {
		if (client == null || client.isClosed())
			return;

		HTTPRequest req;
		HTTPReply res = null;

		try {
			req = client.getInputStream().readHttpRequest();
		} catch (HTTPProtocolException e) {
			req = null;
		}

		if (req != null) {
			res = cmdChainHead.handle(req);
			if (res == null)
				for (HTTPHandler h : otherHandlers) {
					res = h.handle(req);
					if (res != null)
						break;
				}
		}

		if (res != null) {
			client.getOutputStream().writeHttpReply(res);
			//client.close(); DO NOT CLOSE THISSSSSSSSSS
		}
	}

	@Override
	public void stop() {
		running = false;
		try {
			socket.close();
			if (client != null && !client.isClosed())
				client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		client = null;
		socket = null;
	}
}
