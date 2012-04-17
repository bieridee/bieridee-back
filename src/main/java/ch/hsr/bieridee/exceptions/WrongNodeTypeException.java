package ch.hsr.bieridee.exceptions;

/**
 * Exception to be thrown when a node hasn't the expected type.
 *
 */
public class WrongNodeTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public WrongNodeTypeException() {
	}
	
	
	/**
	 * Constructor with message.
	 * @param msg The message
	 */
	public WrongNodeTypeException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with cause.
	 * @param cause The cause
	 */
	public WrongNodeTypeException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with message and cause.
	 * @param msg The message
	 * @param cause The cause
	 */
	public WrongNodeTypeException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
