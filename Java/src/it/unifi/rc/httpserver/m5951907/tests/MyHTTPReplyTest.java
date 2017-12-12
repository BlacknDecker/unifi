package it.unifi.rc.httpserver.m5951907.tests;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.m5951907.messages.MyHTTPReply;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyHTTPReplyTest {

	@Test
	public void getVersionTest1() {
		HTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 200 OK\r\n", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("HTTP/1.1", res.getVersion());
	}

	@Test
	public void getVersionTest2() {
		try {
			new MyHTTPReply("HTTP/1.1200 OK\r\n", null, null);
		} catch (HTTPProtocolException e) {
			assertTrue(e.getMessage().toUpperCase().contains("MISSING SPACE"));
			return;
		}
		fail();
	}

	@Test
	public void getStatusCodeTest1() {
		HTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 500 server error\r\n", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("500", res.getStatusCode());
	}

	@Test
	public void getStatusCodeTest2() {
		try {
			new MyHTTPReply("HTTP/1.1 200 OK\r", null, null);
		} catch (HTTPProtocolException e) {
			assertTrue(e.getMessage().toUpperCase().contains("END CHAR"));
			return;
		}
		fail();
	}

	@Test
	public void getStatusMessage() {
		HTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 200 OK aaa\r\n", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("OK aaa", res.getStatusMessage());
	}

	@Test
	public void getData() {
		HTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 200 OK\r\n", null, "<div>some html</div>");
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("<div>some html</div>", res.getData());
	}

	@Test
	public void getParametersTest1() {
		MyHTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 200 OK\r\n", "Connection: Keep-Alive\r\nUser-Agent: myBrowser", null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("Keep-Alive", res.getParameters().get("Connection"));
		assertEquals("myBrowser", res.getParameters().get("User-Agent"));
	}

	@Test
	public void getParametersTest2() {
		try {
			new MyHTTPReply("HTTP/1.1 200 OK\r\n", "Connection: Keep-Alive\n\n User-Agent: myBrowser", null);
		} catch (Exception e) {
			assertTrue(e.getMessage().toUpperCase().contains("END CHAR"));
			return;
		}
		fail();
	}

	@Test
	public void getParametersTest3() {
		try {
			new MyHTTPReply("HTTP/1.1 200 OK\r\n", "Connection Keep-Alive\r\n User-Agent: myBrowser", null);
		} catch (Exception e) {
			assertTrue(e.getMessage().toUpperCase().contains("PROPERLY FORMATTED"));
			return;
		}
		fail();
	}
}