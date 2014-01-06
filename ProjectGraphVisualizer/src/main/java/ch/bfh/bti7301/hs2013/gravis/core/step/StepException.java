package ch.bfh.bti7301.hs2013.gravis.core.step;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class StepException extends Exception {

	private static final long serialVersionUID = -985202167979838848L;

	public StepException() {
		super();
	}

	/**
	 * @param message
	 */
	public StepException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public StepException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public StepException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public StepException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
