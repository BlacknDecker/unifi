package it.unifi.rc.httpserver.m5951907.tests;

import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.MyHttpRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyHttpRequestTest {

	@Test
	public void getVersionTest1() {
		HTTPRequest req = null;
		try {
			req = new MyHttpRequest("GET /4848 HTTP/1.0\r\n", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("HTTP/1.0", req.getVersion());
	}

	@Test
	public void getVersionTest2() {
		// bad end of line
		try {
			new MyHttpRequest("GET /myurl HTTP/1.0\n", null, null);
		} catch (HTTPProtocolException e) {
			return;
		}
		fail();
	}

	@Test
	public void getVersionTest3() {
		// missing space
		try {
			new MyHttpRequest("GET /urlHTTP/1.0\r\n", null, null);
		} catch (HTTPProtocolException e) {
			return;
		}
		fail();
	}


	@Test
	public void getMethodTest1() {
		HTTPRequest req = null;
		try {
			req = new MyHttpRequest("GET /4848/banana HTTP/1.0\r\n", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("GET", req.getMethod());
	}

	@Test
	public void getMethodTest2() {
		//unnecessary, but who knows...
		HTTPRequest req = null;
		try {
			req = new MyHttpRequest("POST /banana HTTP/1.0\r\n", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("POST", req.getMethod());
	}

	@Test
	public void getPathTest1() {
		HTTPRequest req = null;
		try {
			req = new MyHttpRequest("POST /banana HTTP/1.0\r\n", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("/banana", req.getPath());
	}

	@Test
	public void getPathTest2() {
		HTTPRequest req = null;
		try {
			req = new MyHttpRequest("POST /banana/reallyLongPath/reallyReallyLongPath HTTP/1.0\r\n", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("/banana/reallyLongPath/reallyReallyLongPath", req.getPath());
	}

	@Test
	public void getEntityBodyTest() {
		HTTPRequest req = null;
		try {
			req = new MyHttpRequest("POST /banana HTTP/1.1\r\n", null, "{ I'm sort of a JSON }");
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("{ I'm sort of a JSON }", req.getEntityBody());
	}

	@Test
	public void getParameters() {
		HTTPRequest req = null;
		try {
			req = new MyHttpRequest("POST /banana HTTP/1.1\r\n", "Connection: Keep-Alive\r\nUser-Agent: myBrowser", null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("Keep-Alive", req.getParameters().get("Connection"));
		assertEquals("myBrowser", req.getParameters().get("User-Agent"));
	}
}