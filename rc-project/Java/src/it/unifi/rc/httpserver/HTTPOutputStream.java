/**
 * 
 */
package it.unifi.rc.httpserver;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A class that writes {@link HTTPRequest}/{@link HTTPReply} into a given {@link OutputStream}.
 * 
 * @author loreti
 *
 */
public abstract class HTTPOutputStream {

	private OutputStream os;

	/**
	 * Creates an instance of {@link HTTPOutputStream} that uses the {@link OutputStream} passed
	 * as parameter.
	 * 
	 * @param os an {@link OutputStream} that is used to write data.
	 */
	public HTTPOutputStream( OutputStream os ) {
		this.os = os;
	}
	
	/**
	 * Writes a reply into the inner {@link OutputStream}.
	 * 
	 * @param reply reply to write.
	 */
	public abstract void writeHttpReply( HTTPReply reply );
	
	/**
	 * Writes a rquest into the inner {@link OutputStream}.
	 * 
	 * @param request request to write.
	 */
	public abstract void writeHttpRequest( HTTPRequest request );
	
	/**
	 * Closes the stream and releases all the used resources.
	 * 
	 * @throws IOException if an error occurs.
	 */
	public void close() throws IOException {
		this.os.close();
	}
	
}
