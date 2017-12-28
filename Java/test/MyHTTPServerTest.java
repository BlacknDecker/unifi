import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPInputStream;
import it.unifi.rc.httpserver.HTTPOutputStream;
import it.unifi.rc.httpserver.HTTPServer;
import it.unifi.rc.httpserver.m5951907.handler.MyHTTPHandler1_0;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPRequest;
import it.unifi.rc.httpserver.m5951907.server.MyHTTPServer;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPInputStream;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPOutputStream;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyHTTPServerTest {

	private HTTPServer server;
	private int port = 8080;

	@Before
	public void setUp() {
		try {
			server = new MyHTTPServer(port, 5, InetAddress.getLocalHost());
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		HTTPHandler base = new MyHTTPHandler1_0(new File("test/res_root"));
		((MyHTTPHandler1_0) base).setHandlerID("BASE");
		server.addHandler(base);
	}

	@Test
	public void shakeDown() {
		Runnable r = () -> {
			Socket connection;
			try {
				Thread.sleep(50); // delay request
				connection = new Socket(InetAddress.getLocalHost(), port);

				HTTPOutputStream out = new MyHTTPOutputStream(connection.getOutputStream());
				//HTTPInputStream is = new MyHTTPInputStream(connection.getInputStream());
				out.writeHttpRequest(new MyHTTPRequest("GET /something.html HTTP/1.0", "Connection: Keep-Alive\r\nUser-Agent: myBrowser", "body body body"));
				//out.close(); NOOOO
				Thread.sleep(50);
				System.out.println(connection.getInputStream().read());
				//assertEquals("404", is.readHttpReply().getStatusCode());
				//is.close(); NOOOOO
				connection.close();

				Thread.sleep(50);
				server.stop();
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		};
		new Thread(r).start();

		try {
			server.start();
		} catch (IOException ignored) {
			// nothing good comes from this
		}

	}
}