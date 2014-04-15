package ch.bfh.bti7301.hs2013.gravis.core.algorithm;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class AlgorithmException extends Exception {

	private static final long serialVersionUID = 3774886120793515985L;

	private IAlgorithm algorithm = null;
	
	public AlgorithmException() {
		super();
	}

	/**
	 * @param message
	 */
	public AlgorithmException(String message) {
		super(message);
	}
	
	/**
	 * 
	 * @param message
	 * @param algorithm
	 */
	public AlgorithmException(String message, IAlgorithm algorithm) {
		super(message);
		
		this.algorithm = algorithm;
	}

	/**
	 * @param cause
	 */
	public AlgorithmException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 
	 * @param cause
	 * @param algorithm
	 */
	public AlgorithmException(Throwable cause, IAlgorithm algorithm) {
		super(cause);
		
		this.algorithm = algorithm;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AlgorithmException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * 
	 * @param message
	 * @param cause
	 * @param algorithm
	 */
	public AlgorithmException(String message, Throwable cause, IAlgorithm algorithm) {
		super(message, cause);
		
		this.algorithm = algorithm;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public AlgorithmException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/**
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 * @param algorithm
	 */
	public AlgorithmException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace, IAlgorithm algorithm) {
		super(message, cause, enableSuppression, writableStackTrace);
		
		this.algorithm = algorithm;
	}

	/**
	 * @return the algorithm
	 */
	public IAlgorithm getAlgorithm() {
		return this.algorithm;
	}

	
}
