package it.unifi.rc.httpserver.m5951907.handler;

import it.unifi.rc.httpserver.HTTPHandler;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.MyHTTPProtocolException;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPReply;

import java.io.*;
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

		//aaaargh those try with resources are bad: here is the error!
		try (BufferedReader br = new BufferedReader(new FileReader(root.getCanonicalPath() + url))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			content = sb.toString();
		} catch (IOException e) {
			throw new MyHTTPProtocolException(404, "Not Found", "REQUESTED RESOURCE WAS NOT FOUND: " + url + "\n" + Arrays.toString(e.getStackTrace()));
		} catch (NullPointerException e) {
			throw new MyHTTPProtocolException(500, "Internal Server Error", url + "\n" + Arrays.toString(e.getStackTrace()));
		}
		return content;
	}

	protected abstract List<String> getProtocolSupportedMethods();

	protected abstract String getProtocolVersion();

	protected abstract HTTPReply handlingImplementation(HTTPRequest req) throws MyHTTPProtocolException;
}
