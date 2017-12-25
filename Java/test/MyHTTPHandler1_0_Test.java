import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.handler.MyHTTPHandler1_0;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPRequest;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class MyHTTPHandler1_0_Test {

	private HTTPRequest getHttpRequest(String s) {
		HTTPRequest r = null;
		try {
			r = new MyHTTPRequest(s, null, null);
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
			// should and will not happen
		}
		return r;
	}

	@Test
	public void handleGoodRequest1() {
		HTTPHandler h = new MyHTTPHandler1_0(new File("test/res_root"));
		HTTPRequest r = getHttpRequest("GET /stuff.html HTTP/1.0");
		HTTPReply res = h.handle(r);
		assertEquals("200", res.getStatusCode());
		assertEquals("OK", res.getStatusMessage());
		assertEquals(true, res.getData().contains("too lazy to type actual html"));
	}

	@Test
	public void handleGoodRequest2() {
		HTTPHandler h = new MyHTTPHandler1_0(new File("test/res_root"));
		HTTPRequest r = getHttpRequest("GET /folder/nested_stuff.html HTTP/1.0");
		HTTPReply res = h.handle(r);
		assertEquals("200", res.getStatusCode());
		assertEquals("OK", res.getStatusMessage());
		assertEquals(true, res.getData().contains("Lorem Ipsus"));
	}

	//POST, HEAD

	@Test
	public void handleBadRequest1() {
		HTTPRequest r = getHttpRequest("GET /url HTTP/1.1");
		HTTPReply rep = new MyHTTPHandler1_0(null).handle(r);
		assertEquals("505", rep.getStatusCode());
		assertEquals("HTTP Version Not Supported", rep.getStatusMessage());
	}

	@Test
	public void handleBadRequest2() {
		HTTPRequest r = getHttpRequest("DELETE /url HTTP/1.0");
		HTTPReply rep = new MyHTTPHandler1_0(null).handle(r);
		assertEquals("501", rep.getStatusCode());
		assertEquals("Not Implemented", rep.getStatusMessage());
	}

	@Test
	public void handleBadRequest3() {
		HTTPHandler h = new MyHTTPHandler1_0(new File("test/res_root"));
		HTTPRequest r = getHttpRequest("GET /stuff_not_there.html HTTP/1.0");
		HTTPReply res = h.handle(r);
		assertEquals("404", res.getStatusCode());
		assertEquals("Not Found", res.getStatusMessage());
	}
}