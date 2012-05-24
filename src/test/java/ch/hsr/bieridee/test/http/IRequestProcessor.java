package ch.hsr.bieridee.test.http;

import org.apache.http.client.methods.HttpRequestBase;

/**
 * Interface for a HTTP request processor.
 */
public interface IRequestProcessor {
	/**
	 * Process a request before it gets sent.
	 *
	 * @param request HTTP request object
	 * @return Processed HTTP request object
	 */
	public HttpRequestBase processRequest(HttpRequestBase request);
}
