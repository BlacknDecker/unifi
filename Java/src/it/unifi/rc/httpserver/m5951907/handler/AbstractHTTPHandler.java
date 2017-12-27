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

/**
 * A class that factors away common characteristics of the various concrete implementations of {@link HTTPHandler}.
 *
 * @author Simone Cipriani, 5951907
 */
abstract class AbstractHTTPHandler implements HTTPHandler {

	private final File root;
	private HTTPHandler next;

	private boolean log = false;
	private PrintStream logPs;
	private String id = "DEFAULT ID";

	/**
	 * Basic constructor, initializes handler root.
	 *
	 * @param root from which resources will be fetched
	 */
	AbstractHTTPHandler(File root) {
		this.root = root;
	}

	/**
	 * Self-explanatory.
	 *
	 * @param handler next to this in the command chain
	 */
	public void setNextHandler(HTTPHandler handler) {
		this.next = handler;
	}

	/**
	 * Set an ID useful for {@link #toString()} implementation.
	 *
	 * @param id mnemonic name of this handler
	 */
	public void setHandlerID(String id) {
		this.id = id;
	}

	/**
	 * Enable log prints about handling activities.
	 *
	 * @param enable self-explanatory
	 * @param f      the {@link OutputStream} in which to print the logs (if null, System.out will be used)
	 */
	public void enableLogging(boolean enable, FileOutputStream f) {
		this.log = enable;
		if (f != null) {
			logPs = new PrintStream(new BufferedOutputStream(f), true);
		} else
			logPs = System.out;
	}

	@Override
	public HTTPReply handle(HTTPRequest request) {
		if (log)
			logPs.println(this);

		HTTPReply res;
		try {
			testMethod(request.getMethod());
			testVersion(request.getVersion());

			res = handlingImplementation(request);
		} catch (MyHTTPProtocolException e) {
			return new MyHTTPReply(e, getProtocolVersion());
		}

		if (res != null) {
			if (log)
				logPs.println("\n-------- LOG HANDLER " + this.id + ": caught REQ\n" + request + "\n-------- RES:\n" + res + "\n-------- END LOG");
			return res;
		}

		if (next != null)
			return next.handle(request);
		else
			return null;
	}

	/**
	 * Check if the HTTP protocol version requested is supported by this handler.
	 *
	 * @param version of the HTTP protocol used
	 * @throws MyHTTPProtocolException if HTTP version requested is not supported
	 */
	private void testVersion(String version) throws MyHTTPProtocolException {
		if (!getProtocolVersion().equals(version))
			throw new MyHTTPProtocolException(505, "HTTP Version Not Supported", "PROTOCOL VERSION" + version + " SPECIFIED IN REQUEST IS NOT SUPPORTED");
	}

	/**
	 * Check if the HTTP method requested is implemented by this handler.
	 *
	 * @param method requested
	 * @throws MyHTTPProtocolException if method not implemented
	 */
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
	String readResource(String url) throws MyHTTPProtocolException {
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

	/**
	 * Build a {@link String} with some standard header fields, ready to be parsed by HTTP Reply constructor.
	 *
	 * @param url of the requested resource
	 * @return the {@link String} with header fields and values
	 */
	String readResourceHeader(String url) {
		StringBuilder head = HTTPMessage.getStdHeaderFields();

		if (url != null) {
			head.append("Last-Modified: ");
			File file = new File(root.getAbsolutePath() + url);
			head.append(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(file.lastModified()));
			head.append("\r\n");
		}

		head.append("Handler-ID");
		head.append(": ");
		head.append(id);

		return head.toString();
	}

	/**
	 * Return a {@link File} reference to the requested resource.
	 *
	 * @param url of the requested resource
	 * @return the {@link File} reference
	 */
	File fetchResource(String url) {
		return new File(root.getAbsolutePath() + url);
	}

	/**
	 * Used to check if method requested is supported by this handler.
	 *
	 * @return a {@link List} with supported methods
	 */
	protected abstract List<String> getProtocolSupportedMethods();

	/**
	 * Used ot check if the HTTP version requested is supported by this handler.
	 *
	 * @return the HTTP version
	 */
	protected abstract String getProtocolVersion();

	/**
	 * This method should implement the methods defined in the proper HTTP protocol version.
	 *
	 * @param req an incoming {@link HTTPRequest}
	 * @return the proper {@link HTTPReply}
	 * @throws MyHTTPProtocolException if something is wrong
	 */
	protected abstract HTTPReply handlingImplementation(HTTPRequest req) throws MyHTTPProtocolException;

	/**
	 * Mainly for debugging and logging purposes.
	 *
	 * @return a {@link String} representation of this handler
	 */
	@Override
	public String toString() {
		return "[" + this.id + "; " + this.root + "; " + this.next + "]";
	}
}
