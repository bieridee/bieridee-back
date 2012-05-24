package ch.hsr.bieridee.exceptions;

/**
 * Exception to be thrown when a node hasn't the expected type.
 * 
 */
public class WrongNodeTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message.
	 * 
	 * @param msg
	 *            The message
	 */
	public WrongNodeTypeException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with cause.
	 * 
	 * @param cause
	 *            The cause
	 */
	public WrongNodeTypeException(Throwable cause) {
		super(cause);
	}

}
