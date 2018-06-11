import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyHTTPRequestTest {

	@Test
	public void getVersionTest1() {
		HTTPRequest req = null;
		try {
			req = new MyHTTPRequest("GET /4848 HTTP/1.0", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("HTTP/1.0", req.getVersion());
	}

	@Test
	public void getVersionTest2() {
		// missing space
		try {
			new MyHTTPRequest("GET /urlHTTP/1.0", null, null);
		} catch (HTTPProtocolException e) {
			assertEquals(((MyHTTPProtocolException) e).getCode(), 400);
			return;
		}
		fail();
	}

	@Test
	public void getMethodTest1() {
		HTTPRequest req = null;
		try {
			req = new MyHTTPRequest("GET /4848/banana HTTP/1.0", null, null);
		} catch (Exception e) {
			fail();
		}
		assertEquals("GET", req.getMethod());
	}

	@Test
	public void getMethodTest2() {
		//unnecessary, but who knows...
		HTTPRequest req = null;
		try {
			req = new MyHTTPRequest("POST /banana HTTP/1.0", null, null);
		} catch (Exception e) {
			fail();
		}
		assertEquals("POST", req.getMethod());
	}

	@Test
	public void getPathTest1() {
		HTTPRequest req = null;
		try {
			req = new MyHTTPRequest("POST /banana HTTP/1.0", null, null);
		} catch (Exception e) {
			fail();
		}
		assertEquals("/banana", req.getPath());
	}

	@Test
	public void getPathTest2() {
		// a long path, unnecessary, but again I never know...
		HTTPRequest req = null;
		try {
			req = new MyHTTPRequest("POST /banana/reallyLongPath/reallyReallyLongPath HTTP/1.0", null, null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("/banana/reallyLongPath/reallyReallyLongPath", req.getPath());
	}

	@Test
	public void getEntityBodyTest() {
		HTTPRequest req = null;
		try {
			req = new MyHTTPRequest("POST /banana HTTP/1.1", null, "{ I'm sort of a JSON }");
		} catch (Exception e) {
			fail();
		}
		assertEquals("{ I'm sort of a JSON }", req.getEntityBody());
	}

	@Test
	public void getParametersTest1() {
		HTTPRequest req = null;
		try {
			req = new MyHTTPRequest("POST /banana HTTP/1.1", "Connection: Keep-Alive\r\nUser-Agent: myBrowser", null);
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("Keep-Alive", req.getParameters().get("Connection"));
		assertEquals("myBrowser", req.getParameters().get("User-Agent"));
	}

	@Test
	public void getParametersTest2() {
		try {
			new MyHTTPRequest("POST /banana HTTP/1.1", "Connection: Keep-Alive\n\n User-Agent: myBrowser", null);
		} catch (HTTPProtocolException e) {
			assertEquals(((MyHTTPProtocolException) e).getCode(), 400);
			return;
		}
		fail();
	}

	@Test
	public void getParametersTest3() {
		try {
			new MyHTTPRequest("POST /banana HTTP/1.1", "Connection Keep-Alive\r\n User-Agent: myBrowser", null);
		} catch (HTTPProtocolException e) {
			assertEquals(((MyHTTPProtocolException) e).getCode(), 400);
			return;
		}
		fail();
	}

	@Test
	public void toStringTest() {
		HTTPRequest req = null;
		try {
			req = new MyHTTPRequest("POST /banana HTTP/1.1", "Connection: Keep-Alive\r\nUser-Agent: myBrowser", "I'm a banana");
		} catch (Exception e) {
			fail(); // if exception thrown, test should fail
		}
		assertEquals("POST /banana HTTP/1.1\r\nConnection: Keep-Alive\r\nUser-Agent: myBrowser\r\n\r\nI'm a banana", req.toString());
	}
}