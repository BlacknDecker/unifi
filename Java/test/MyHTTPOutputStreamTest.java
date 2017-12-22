import it.unifi.rc.httpserver.HTTPOutputStream;
import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPReply;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPRequest;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyHTTPOutputStreamTest {

	private ByteArrayOutputStream os;
	private HTTPOutputStream httpOut;

	@Before
	public void setUp() {
		os = new ByteArrayOutputStream();
		httpOut = new MyHTTPOutputStream(os);
	}

	@After
	public void tearDown() throws IOException {
		httpOut.close();
	}

	@Test
	public void writeHttpReply() {
		HTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 200 OK", "Connection: Keep-Alive\r\nUser-Agent: myBrowser", "body body body");
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
			fail();
		}
		httpOut.writeHttpReply(res);

		assertEquals("HTTP/1.1 200 OK\r\nConnection: Keep-Alive\r\nUser-Agent: myBrowser\r\n\r\nbody body body", os.toString());
	}

	@Test
	public void writeHttpRequest() {
		HTTPRequest req = null;
		try {
			req = new MyHTTPRequest("POST /banana HTTP/1.0", "Connection: Keep-Alive\r\nUser-Agent: myBrowser", "body body body");
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
			fail();
		}
		httpOut.writeHttpRequest(req);

		assertEquals("POST /banana HTTP/1.0\r\nConnection: Keep-Alive\r\nUser-Agent: myBrowser\r\n\r\nbody body body", os.toString());
	}
}