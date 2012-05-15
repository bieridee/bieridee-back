package ch.hsr.bieridee.exceptions;

/**
 * Exception to be thrown when request is not authorized.
 */
public class UnauthorizedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public UnauthorizedException() {
	}

	/**
	 * Constructor with message.
	 * @param msg The message
	 */
	public UnauthorizedException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with cause.
	 * @param cause The cause
	 */
	public UnauthorizedException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with message and cause.
	 * @param msg The message
	 * @param cause The cause
	 */
	public UnauthorizedException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
