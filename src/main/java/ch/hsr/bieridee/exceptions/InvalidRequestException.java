package ch.hsr.bieridee.exceptions;

/**
 * Exception to be thrown when a request is malformed.
 * 
 */
public class InvalidRequestException extends Exception {
	
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
