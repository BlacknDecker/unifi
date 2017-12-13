package it.unifi.rc.httpserver.m5951907.message.test;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPReply;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyHTTPReplyTest {

	@Test
	public void getVersionTest1() {
		HTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 200 OK", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("HTTP/1.1", res.getVersion());
	}

	@Test
	public void getVersionTest2() {
		try {
			new MyHTTPReply("HTTP/1.1200 OK", null, null);
		} catch (HTTPProtocolException e) {
			assertEquals(((MyHTTPProtocolException) e).getCode(), 500);
			return;
		}
		fail();
	}

	@Test
	public void getStatusCodeTest1() {
		HTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 500 server error", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("500", res.getStatusCode());
	}

	@Test
	public void getStatusMessage() {
		HTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 200 OK aaa", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("OK aaa", res.getStatusMessage());
	}

	@Test
	public void getData() {
		HTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 200 OK", null, "<div>some html</div>");
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("<div>some html</div>", res.getData());
	}

	@Test
	public void getParametersTest1() {
		MyHTTPReply res = null;
		try {
			res = new MyHTTPReply("HTTP/1.1 200 OK", "Connection: Keep-Alive\r\nUser-Agent: myBrowser", null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("Keep-Alive", res.getParameters().get("Connection"));
		assertEquals("myBrowser", res.getParameters().get("User-Agent"));
	}

	@Test
	public void getParametersTest2() {
		try {
			new MyHTTPReply("HTTP/1.1 200 OK", "Connection: Keep-Alive\n\n User-Agent: myBrowser", null);
		} catch (HTTPProtocolException e) {
			assertEquals(((MyHTTPProtocolException) e).getCode(), 500);
			return;
		}
		fail();
	}

	@Test
	public void getParametersTest3() {
		try {
			new MyHTTPReply("HTTP/1.1 200 OK\r\n", "Connection Keep-Alive\r\n User-Agent: myBrowser", null);
		} catch (HTTPProtocolException e) {
			assertEquals(((MyHTTPProtocolException) e).getCode(), 500);
			return;
		}
		fail();
	}
}