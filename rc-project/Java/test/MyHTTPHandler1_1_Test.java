import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.handler.MyHTTPHandler1_1;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPRequest;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyHTTPHandler1_1_Test {

	private HTTPRequest getHttpRequest(String s) {
		HTTPRequest r = null;
		try {
			r = new MyHTTPRequest(s, null, "I am a body!");
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
			// should and will not happen
		}
		return r;
	}

	@After
	public void cleanUp() {
		new File("test/res_root/delete_me.html").delete();
	}

	@Test
	public void handleGoodRequest1() {
		if (new File("test/res_root/put_me.html").delete()) {
			HTTPHandler h = new MyHTTPHandler1_1(new File("test/res_root"));
			HTTPRequest r = getHttpRequest("PUT /put_me.html HTTP/1.1");
			HTTPReply res = h.handle(r);
			assertEquals("201", res.getStatusCode());
			assertEquals("Created", res.getStatusMessage());
		} else fail();
	}

	@Test
	public void handleGoodRequest2() throws IOException {
		if (new File("test/res_root/delete_me.html").createNewFile()) {
			HTTPHandler h = new MyHTTPHandler1_1(new File("test/res_root"));
			HTTPRequest r = new MyHTTPRequest("DELETE /delete_me.html HTTP/1.1", "Par: am\r\nAuthentication: slowpoke_tail", null);
			HTTPReply res = h.handle(r);
			assertEquals("202", res.getStatusCode());
			assertEquals("Accepted", res.getStatusMessage());
		} else fail();
	}

	@Test
	public void handleBadReq1() {
		HTTPHandler h = new MyHTTPHandler1_1(new File("test/res_root"));
		HTTPRequest r = getHttpRequest("TRACE /delete_me.html HTTP/1.1");
		HTTPReply res = h.handle(r);
		assertEquals("501", res.getStatusCode());
		assertEquals("Not Implemented", res.getStatusMessage());
	}

	@Test
	public void handleBadReq2() throws IOException {
		if (new File("test/res_root/delete_me.html").createNewFile()) {
			HTTPHandler h = new MyHTTPHandler1_1(new File("test/res_root"));
			HTTPRequest r = new MyHTTPRequest("DELETE /delete_me.html HTTP/1.1", "Par: am\r\nAuthentication: slowpoke_nail", null);
			HTTPReply res = h.handle(r);
			assertEquals("401", res.getStatusCode());
			assertEquals("Unauthorized", res.getStatusMessage());
		} else fail();
	}
}