package it.unifi.rc.httpserver.m5951907.stream;

import it.unifi.rc.httpserver.HTTPOutputStream;
import it.unifi.rc.httpserver.HTTPReply;
import it.unifi.rc.httpserver.HTTPRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

/**
 * A concrete implementation of the given abstract class {@link HTTPOutputStream}.
 *
 * @author Simone Cipriani, 5951907
 */
public class MyHTTPOutputStream extends HTTPOutputStream {

	private final OutputStream os;

	/**
	 * Construct the HTTP Output Stream wrapping an existent {@link OutputStream}.
	 *
	 * @param os {@link OutputStream}
	 */
	public MyHTTPOutputStream(OutputStream os) {
		super(os); // why is this private in superclass? I need reference to it
		this.os = os;
	}

	/**
	 * Build the header lines extracting keys and values from a {@link Map}.
	 *
	 * @param msg     the interface abstracting an object with a {@link Map} that can be get
	 * @param message as the StringBuilder in which append the generated lines
	 */
	private void buildHeader(MsgWithHeadParam msg, StringBuilder message) {
		Set<String> headerKeys = msg.par().keySet();
		StringBuilder sb = new StringBuilder();
		for (String key : headerKeys) {
			sb.append(key);
			sb.append(": ");
			sb.append(msg.par().get(key));
			sb.append("\r\n");
		}
		message.append(sb.toString());
		message.append("\r\n");
	}

	@Override
	public void writeHttpReply(HTTPReply reply) {
		StringBuilder message = new StringBuilder();
		message.append(reply.getVersion());
		message.append(" ");
		message.append(reply.getStatusCode());
		message.append(" ");
		message.append(reply.getStatusMessage());
		message.append("\r\n");

		buildHeader(reply::getParameters, message);
		message.append(reply.getData());

		try {
			os.write(message.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeHttpRequest(HTTPRequest request) {
		StringBuilder message = new StringBuilder();
		message.append(request.getMethod());
		message.append(" ");
		message.append(request.getPath());
		message.append(" ");
		message.append(request.getVersion());
		message.append("\r\n");

		buildHeader(request::getParameters, message);
		message.append(request.getEntityBody());

		try {
			os.write(message.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Utility interface to do fancy lambda/methods reference tricks in the {@link #buildHeader(MsgWithHeadParam, StringBuilder)},
	 * {@link #writeHttpReply(HTTPReply)} and {@link #writeHttpRequest(HTTPRequest)} methods.
	 */
	private interface MsgWithHeadParam {
		Map<String, String> par();
	}
}
