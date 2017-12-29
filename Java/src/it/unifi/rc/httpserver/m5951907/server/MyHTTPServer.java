package it.unifi.rc.httpserver.m5951907.server;

import it.unifi.rc.httpserver.*;
import it.unifi.rc.httpserver.m5951907.handler.MyHTTPHandler;
import it.unifi.rc.httpserver.m5951907.handler.MyHTTPHandler1_0;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPInputStream;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPOutputStream;

import java.io.*;
import java.net.*;
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

	private int port, backlog;
	private InetAddress address;

	public MyHTTPServer(int port, int backlog, InetAddress address) {
		this.address = address;
		this.port = port;
		this.backlog = backlog;

		enableLogging(false, null);
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
			logPs.println("--- SERVER: I am this one: " + socket);

		running = true;
		while (running)
			serve(accept());
	}

	private Socket accept() throws IOException {
		Socket client = null;
		try {
			client = this.socket.accept();
		} catch (SocketTimeoutException ignored) {
			// respawn
		}
		if (log && client != null)
			logPs.println("--- SERVER: Accepted this client: " + client);
		return client;
	}

	private void serve(Socket client) {
		if (client == null)
			return; //no one to serve

		HTTPRequest req = getHttpRequest(client);
		HTTPReply res = forwardToHandlers(req);
		writeResponse(client, res);
	}

	private HTTPRequest getHttpRequest(Socket client) {
		HTTPRequest req;
		try {
			HTTPInputStream in = new MyHTTPInputStream(client.getInputStream());
			req = in.readHttpRequest();
		} catch (IOException e) {
			req = null;
		}
		if (log && req != null)
			logPs.println("--- SERVER: read request: " + req);
		return req;
	}

	private HTTPReply forwardToHandlers(HTTPRequest req) {
		HTTPReply res = null;
		if (req != null) {
			res = cmdChainHead.handle(req);
			if (res == null)
				for (HTTPHandler h : otherHandlers) {
					res = h.handle(req);
					if (res != null)
						break;
				}
		}
		if (log)
			logPs.println("--- SERVER: produced response: " + res);
		return res;
	}

	private void writeResponse(Socket client, HTTPReply res) {
		if (res != null) {
			try {
				HTTPOutputStream os = new MyHTTPOutputStream(client.getOutputStream());
				os.writeHttpReply(res);
				client.shutdownOutput();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (log)
				logPs.println("--- SERVER: wrote response into client stream");
		}
	}

	@Override
	public void stop() {
		running = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Enable log prints about server activities.
	 *
	 * @param enable self-explanatory
	 * @param f      the {@link OutputStream} in which to print the logs (if null, System.out will be used)
	 */
	private void enableLogging(boolean enable, FileOutputStream f) {
		this.log = enable;
		if (f != null) {
			logPs = new PrintStream(new BufferedOutputStream(f), true);
		} else
			logPs = System.out;
	}

	public static void main(String[] args) throws IOException {
		HTTPServer server = null;
		try {
			server = new MyHTTPServer(8080, 10, InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		assert server != null;
		server.addHandler(new MyHTTPHandler1_0(new File("test/res_root")));
		server.start();
	}
}
