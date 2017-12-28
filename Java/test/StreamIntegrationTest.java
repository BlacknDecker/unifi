import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPReply;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPRequest;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPInputStream;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPOutputStream;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StreamIntegrationTest {

	@Test
	public void streamIntegrationReq() {
		HTTPRequest req = null;
		try {
			req = new MyHTTPRequest("GET /something.html HTTP/1.0", "Connection: Keep-Alive\r\nUser-Agent: myBrowser", "body body body");
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
			fail();
		}

		ServerSocket server;
		Socket client;
		try {
			server = new ServerSocket(9999, 5, InetAddress.getLocalHost());
			client = new Socket(InetAddress.getLocalHost(), 9999);

			MyHTTPOutputStream to = new MyHTTPOutputStream(client.getOutputStream());
			MyHTTPInputStream from = new MyHTTPInputStream(server.accept().getInputStream());

			to.writeHttpRequest(req);
			client.shutdownOutput();
			assertEquals(req.toString(), from.readHttpRequest().toString());

			client.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void streamIntegrationRes() {
		HTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.0 200 OK", "Connection: Keep-Alive\r\nUser-Agent: myBrowser", "body body body");
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
			fail();
		}

		ServerSocket server;
		Socket client;
		try {
			server = new ServerSocket(9999, 5, InetAddress.getLocalHost());
			client = new Socket(InetAddress.getLocalHost(), 9999);
			Socket clientAccepted = server.accept();

			MyHTTPOutputStream to = new MyHTTPOutputStream(clientAccepted.getOutputStream());
			MyHTTPInputStream from = new MyHTTPInputStream(client.getInputStream());

			to.writeHttpReply(res);
			clientAccepted.shutdownOutput();
			assertEquals(res.toString(), from.readHttpRequest().toString());

			client.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

	}


}