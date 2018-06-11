import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.handler.MyHTTPHandler1_0;
import it.unifi.rc.httpserver.m5951907.message.HTTPMessage;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class CommandChainTest {

	private HTTPHandler first;

	@Before
	public void setUpCommandChain() {
		MyHTTPHandler1_0 base, h1, h2, h3, first;
		base = new MyHTTPHandler1_0(new File("test/res_root"));
		base.setHandlerID("BASIC");
		h1 = new MyHTTPHandler1_0("banana", new File("test/res_root"));
		h1.setHandlerID("H1");
		h2 = new MyHTTPHandler1_0("terracotta", new File("test/res_root"));
		h2.setHandlerID("H2");
		h3 = new MyHTTPHandler1_0("pie", new File("test/res_root"));
		h3.setHandlerID("H3");
		try {
			first = new MyHTTPHandler1_0(InetAddress.getLocalHost().getHostName(), new File("test/res_root"));
			first.setNextHandler(h3);
			first.setHandlerID("FIRST");
			first.enableLogging(false, null);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			first = null;
		}
		h3.setNextHandler(h2);
		h2.setNextHandler(h1);
		h1.setNextHandler(base);

		this.first = first;
	}

	@Test
	public void commandChainTest1() {
		HTTPRequest r = null;
		try {
			r = new MyHTTPRequest("GET /stuff.html HTTP/1.0", HTTPMessage.getStdHeaderFields().toString(), "body body body");
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
		}
		HTTPReply res = first.handle(r); //first should catch it
		assertEquals("200", res.getStatusCode());
		assertEquals("OK", res.getStatusMessage());
		assertEquals(true, res.getData().contains("too lazy to type actual html"));
		assertEquals("FIRST", res.getParameters().get("Handler-ID"));
	}

	@Test
	public void commandChainTest2() {
		HTTPRequest r = null;
		try {
			r = new MyHTTPRequest("GET /stuff.html HTTP/1.0", "A-Field: something\r\nAnother: something_else", "body body body");
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
		}
		HTTPReply res = first.handle(r); // basic should catch it
		assertEquals("200", res.getStatusCode());
		assertEquals("OK", res.getStatusMessage());
		assertEquals(true, res.getData().contains("too lazy to type actual html"));
		assertEquals("BASIC", res.getParameters().get("Handler-ID"));
	}

	@Test
	public void commandChainTest3() {
		HTTPRequest r = null;
		try {
			r = new MyHTTPRequest("GET /stuff.html HTTP/1.0", "Host: banana\r\nAnother: something_else", "body body body");
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
		}
		HTTPReply res = first.handle(r); // basic should catch it
		assertEquals("200", res.getStatusCode());
		assertEquals("OK", res.getStatusMessage());
		assertEquals(true, res.getData().contains("too lazy to type actual html"));
		assertEquals("H1", res.getParameters().get("Handler-ID"));
	}

}