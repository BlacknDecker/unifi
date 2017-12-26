package it.unifi.rc.httpserver.m5951907.handler;

import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;
import it.unifi.rc.httpserver.m5951907.message.HTTPMessage;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPReply;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

abstract class AbstractHTTPHandler implements HTTPHandler {

	private final File root;
	private HTTPHandler next;

	AbstractHTTPHandler(File root) {
		this.root = root;
	}

	public void setNextHandler(HTTPHandler handler) {
		this.next = handler;
	}

	@Override
	public HTTPReply handle(HTTPRequest request) {
		HTTPReply res;
		try {
			testMethod(request.getMethod());
			testVersion(request.getVersion());

			res = handlingImplementation(request);
		} catch (MyHTTPProtocolException e) {
			return new MyHTTPReply(e, getProtocolVersion());
		}

		if (res != null)
			return res;

		if (next != null)
			return next.handle(request);
		else
			return null;
	}

	private void testVersion(String version) throws MyHTTPProtocolException {
		if (!getProtocolVersion().equals(version))
			throw new MyHTTPProtocolException(505, "HTTP Version Not Supported", "PROTOCOL VERSION" + version + " SPECIFIED IN REQUEST IS NOT SUPPORTED");
	}

	private void testMethod(String method) throws MyHTTPProtocolException {
		if (!getProtocolSupportedMethods().contains(method))
			throw new MyHTTPProtocolException(501, "Not Implemented", "METHOD " + method + " SPECIFIED IN REQUEST IS NOT IMPLEMENTED");
	}

	/**
	 * Can be called by subclasses to fetch for a physical resource (I.E. a file).
	 *
	 * @param url of the requested resource
	 * @return a string container of the resource data
	 * @throws MyHTTPProtocolException when resource cannot be found
	 */
	String fetchResource(String url) throws MyHTTPProtocolException {
		String content;
		try {
			FileReader fr = new FileReader(root.getAbsolutePath() + url);
			BufferedReader br = new BufferedReader(fr);

			char[] buff = new char[1024]; // just threw in a number, did not try to optimize performances
			int read;
			StringBuilder sb = new StringBuilder();
			while ((read = br.read(buff)) != -1)
				sb.append(buff, 0, read);
			content = sb.toString();

			fr.close();
			br.close();
		} catch (IOException e) {
			throw new MyHTTPProtocolException(404, "Not Found", "REQUESTED RESOURCE WAS NOT FOUND: " + url + "\n" + Arrays.toString(e.getStackTrace()));
		} catch (NullPointerException e) {
			throw new MyHTTPProtocolException(500, "Internal Server Error", url + "\n" + Arrays.toString(e.getStackTrace()));
		}
		return content;
	}

	String fetchResourceHeader(String url) {
		StringBuilder head = HTTPMessage.getStdHeaderFields();
		head.append("Last-Modified: ");
		File file = new File(root.getAbsolutePath() + url);
		head.append(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(file.lastModified()));
		return head.toString();
	}

	protected abstract List<String> getProtocolSupportedMethods();

	protected abstract String getProtocolVersion();

	protected abstract HTTPReply handlingImplementation(HTTPRequest req) throws MyHTTPProtocolException;
}
