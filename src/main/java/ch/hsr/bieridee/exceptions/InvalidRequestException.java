package ch.hsr.bieridee.exceptions;

/**
 * @author chrigi
 * 
 */
public class InvalidRequestException extends Exception {
	/**
	 * Exception to be thrown when a request is malformed.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message.
	 * 
	 * @param message
	 *            The message
	 */
	public InvalidRequestException(String message) {
		super(message);
	}

}
