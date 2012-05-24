package ch.hsr.bieridee.test.http.requestprocessors;

import ch.hsr.bieridee.test.http.IRequestProcessor;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * A request processor that takes a content type in the constructor
 * and adds it to the accept header of the request.
 */
public class AcceptRequestProcessor implements IRequestProcessor {

	private String contentType;

	/**
	 * Content type enum. Item values can be retrieved using <code>.value()</code>.
	 */
	public static enum ContentType {
		JSON("application/json"),
		XML("application/xml"),
		HTML("text/html"),
		PNG("image/png"),
		JPEG("image/jpeg"),
		TEXT("text/plain");

		private String value;

		ContentType(String value) {
			this.value = value;
		}

		/**
		 * Return the value for this content type.
		 * @return ContentType value
		 */
		public String value() {
			return this.value;
		}
	}

	/**
	 * Create a new <code>AcceptRequestProcessor</code> instance that adds
	 * the specified <code>ContentType</code> to the accept header of the request.
	 *
	 * @param contentType An instance of <code>AcceptRequestProcessor.ContentType</code>
	 */
	public AcceptRequestProcessor(ContentType contentType) {
		this.contentType = contentType.value();
	}

	/**
	 * Process the request.
	 *
	 * @param request HTTP request object
	 * @return HTTP request object
	 */
	public HttpRequestBase processRequest(HttpRequestBase request) {
		request.setHeader("Accept", this.contentType);
		return request;
	}
}
