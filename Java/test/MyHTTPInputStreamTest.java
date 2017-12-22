import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPInputStream;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyHTTPInputStreamTest {

	private MyHTTPInputStream httpIS;
	private HTTPRequest req;
	private HTTPReply res;

	@After
	public void tearDown() throws Exception {
		httpIS.close();
	}

	@Test
	public void readHttpRequestTest1() {
		String httpReq = "GET /wiki/Pagina_principale HTTP/1.1\r\nHost: it.wikipedia.org\r\nConnection: Keep-Alive\r\nAccept-Language: it\r\n\r\n";
		httpIS = new MyHTTPInputStream(httpReq);
		try {
			req = httpIS.readHttpRequest();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertEquals("GET", req.getMethod());
		Map<String, String> head = new HashMap<>();
		head.put("Host", "it.wikipedia.org");
		head.put("Connection", "Keep-Alive");
		head.put("Accept-Language", "it");
		assertEquals(head, req.getParameters());
		assertEquals("", req.getEntityBody());
	}

	@Test
	public void readHttpRequestTest2() {
		String httpReq = "POST /wiki/Pagina_principale HTTP/1.1\r\nConnection: Keep-Alive\r\nAccept-Language: it\r\n\r\n{here's a JSON}";
		httpIS = new MyHTTPInputStream(httpReq);
		try {
			req = httpIS.readHttpRequest();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertEquals("POST", req.getMethod());
		assertEquals("/wiki/Pagina_principale", req.getPath());
		assertEquals("{here's a JSON}", req.getEntityBody());
	}

	@Test
	public void readHttpReplyTest1() {
		String httpRes = "HTTP/1.1 200 OK\r\nDate: Wed, 19 Apr 2017 16:34:20 GMT\r\nServer: mw1215.eqiad.wmnet\r\n\r\n<div> some html </div>";
		httpIS = new MyHTTPInputStream(httpRes);
		try {
			res = httpIS.readHttpReply();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertEquals("HTTP/1.1", res.getVersion());
		Map<String, String> head = new HashMap<>();
		head.put("Server", "mw1215.eqiad.wmnet");
		head.put("Date", "Wed, 19 Apr 2017 16:34:20 GMT");
		assertEquals(head, res.getParameters());
		assertEquals("<div> some html </div>", res.getData());
	}

	@Test
	public void readHttpReplyTest2() {
		String httpRes = "HTTP/1.1 200 OK\r\nDate: Wed, 19 Apr 2017 16:34:20 GMT\r\nServer: mw1215.eqiad.wmnet\r\n\r\n<div> some html </div>";
		httpIS = new MyHTTPInputStream(httpRes);

		try {
			res = httpIS.readHttpReply();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
}