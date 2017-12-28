package it.unifi.rc.httpserver.m5951907.server;

import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.HTTPServer;
import it.unifi.rc.httpserver.m5951907.handler.MyHTTPHandler;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPInputStream;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPOutputStream;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Vector;

/**
 * Serves one client at time
 * <p>
 * READ WELL SPECS
 */
public class MyHTTPServer implements HTTPServer {

	private boolean running = false;
	private boolean log = false;
	private PrintStream logPs;

	private List<HTTPHandler> otherHandlers = new Vector<>();
	private MyHTTPHandler cmdChainHead;

	private ServerSocket socket;
	private Socket client;

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

		if (log)
			logPs.println("SERVER: I am this one: " + socket);

		running = true;
		while (running) {
			accept();
			serve();
		}
	}

	private void accept() throws IOException {
		try {
			client = this.socket.accept();
		} catch (SocketTimeoutException ignored) {
			// respawn
		}
		if (log)
			logPs.println("SERVER: Accepted this client: " + client);
	}

	private void serve() {
		if (client == null)
			return;

		HTTPRequest req;
		HTTPReply res = null;

		// read the request
		try {
			req = new MyHTTPInputStream(client.getInputStream()).readHttpRequest();
		} catch (IOException e) {
			e.printStackTrace();
			req = null;
		}
		if (log)
			logPs.println("SERVER: read request: " + req);

		// forward req to handlers
		if (req != null) {
			res = cmdChainHead.handle(req);
			if (res == null)
				for (HTTPHandler h : otherHandlers) {
					res = h.handle(req);
					if (res != null)
						break;
				}
		}

		// write reply into client stream and close it
		if (res != null) {
			try {
				new MyHTTPOutputStream(client.getOutputStream()).writeHttpReply(res);
				client.shutdownOutput();
			} catch (IOException e) {
				e.printStackTrace();
			}
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


	/**
	 * Enable log prints about server activities.
	 *
	 * @param enable self-explanatory
	 * @param f      the {@link OutputStream} in which to print the logs (if null, System.out will be used)
	 */
	public void enableLogging(boolean enable, FileOutputStream f) {
		this.log = enable;
		if (f != null) {
			logPs = new PrintStream(new BufferedOutputStream(f), true);
		} else
			logPs = System.out;
	}
}
