import it.unifi.rc.httpserver.*;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPInputStream;
import it.unifi.rc.httpserver.m5951907.stream.MyHTTPOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StreamIntegrationTest {

	private ByteArrayOutputStream os;
	private HTTPOutputStream httpOut;
	private HTTPInputStream httpIn;

	@Before
	public void setUp() {
		os = new ByteArrayOutputStream();
		httpOut = new MyHTTPOutputStream(os);
	}

	@After
	public void tearDown() throws IOException {
		httpIn.close();
		httpOut.close();
	}

	@Test
	public void reqIntegration() {
		String reqString = "GET /wiki/Pagina_principale HTTP/1.1\r\nHost: it.wikipedia.org\r\nConnection: Keep-Alive\r\nAccept-Language: it\r\n\r\nbodybody";
		httpIn = new MyHTTPInputStream(reqString);
		HTTPRequest req = null;
		try {
			req = httpIn.readHttpRequest();
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
			fail();
		}
		httpOut.writeHttpRequest(req);
		assertEquals(reqString, os.toString());
	}

	@Test
	public void resIntegration() {
		String resString = "HTTP/1.1 200 OK\r\nDate: Wed, 19 Apr 2017 16:34:20 GMT\r\nServer: mw1215.eqiad.wmnet\r\n\r\n<div> some html </div>";
		httpIn = new MyHTTPInputStream(resString);
		HTTPReply res = null;
		try {
			res = httpIn.readHttpReply();
		} catch (HTTPProtocolException e) {
			e.printStackTrace();
			fail();
		}
		httpOut.writeHttpReply(res);
		assertEquals(resString, os.toString());
	}


}