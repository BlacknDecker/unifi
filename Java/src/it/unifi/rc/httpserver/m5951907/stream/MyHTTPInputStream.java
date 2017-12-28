package it.unifi.rc.httpserver.m5951907.stream;

import it.unifi.rc.httpserver.HTTPInputStream;
import it.unifi.rc.httpserver.HTTPProtocolException;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPReply;
import it.unifi.rc.httpserver.m5951907.message.MyHTTPRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A concrete implementation of the given abstract class {@link HTTPInputStream}.
 *
 * @author Simone Cipriani, 5951907
 */
public class MyHTTPInputStream extends HTTPInputStream {

	private InputStream is;
	private Scanner scanner;

	/**
	 * Construct the HTTP Input Stream wrapping an existent {@link InputStream}.
	 *
	 * @param is {@link InputStream}
	 */
	public MyHTTPInputStream(InputStream is) {
		super(is);
	}

	/**
	 * Construct the HTTP Input Stream generating an {@link InputStream} from a given string.
	 * This is an utility constructor, useful to quickly instantiate tests and such.
	 *
	 * @param s the string from which to read
	 */
	public MyHTTPInputStream(String s) {
		this(new ByteArrayInputStream(s.getBytes()));
	}

	/**
	 * Extract first line of the HTTP Message from the internal {@link InputStream}.
	 *
	 * @return the first line as a String
	 */
	private String readFirstLine() {
		String limit = "\r\n", line = "";
		scanner.useDelimiter(limit);
		if (scanner.hasNext())
			line = scanner.next();
		try {
			scanner.skip(limit);
		} catch (NoSuchElementException ignored) {
		}
		return line;
	}

	/**
	 * Extract the header lines of the HTTP Message from the internal {@link InputStream}.
	 *
	 * @return the header lines as a String
	 */
	private String readHeader() {
		String limit = "\r\n\r\n", header = null;
		scanner.useDelimiter(limit);
		if (scanner.hasNext())
			header = scanner.next();
		try {
			scanner.skip(limit);
		} catch (NoSuchElementException ignored) {
		}
		return header;
	}

	/**
	 * Extract the body lines of the HTTP Message from the internal {@link InputStream}.
	 *
	 * @return the body as a String
	 */
	private String readBody() {
		StringBuilder body = new StringBuilder();
		while (scanner.hasNextLine())
			body.append(scanner.nextLine());
		return body.toString();
	}

	@Override
	protected void setInputStream(InputStream is) {
		this.is = is;
		this.scanner = new Scanner(is);
	}

	@Override
	public HTTPRequest readHttpRequest() throws HTTPProtocolException {
		return new MyHTTPRequest(readFirstLine(), readHeader(), readBody());
	}

	@Override
	public HTTPReply readHttpReply() throws HTTPProtocolException {
			return new MyHTTPReply(readFirstLine(), readHeader(), readBody());
	}

	@Override
	public void close() throws IOException {
		scanner.close();
		is.close();
	}
}