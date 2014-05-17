package ch.bfh.ti.gravis.core.algorithm;

/**
 * Graph not valid for that algorithm calc, error while algo calc
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class AlgorithmException extends Exception {

	private static final long serialVersionUID = 3774886120793515985L;

	private final IAlgorithm algorithm;

	private final String userDialogMsg;

	/**
	 * 
	 * @param message
	 * @param algorithm
	 */
	public AlgorithmException(String message, IAlgorithm algorithm) {
		this(message, "", null, algorithm);
	}

	/**
	 * 
	 * @param message
	 * @param userDialogMsg
	 * @param algorithm
	 */
	public AlgorithmException(String message, String userDialogMsg,
			IAlgorithm algorithm) {
		
		this(message, userDialogMsg, null, algorithm);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AlgorithmException(String message, Throwable cause) {
		this(message, "", cause, null);
	}

	/**
	 * 
	 * @param message
	 * @param userDialogMsg
	 * @param cause
	 * @param algorithm
	 */
	public AlgorithmException(String message, String userDialogMsg,
			Throwable cause, IAlgorithm algorithm) {
		
		super(message, cause);

		this.userDialogMsg = userDialogMsg;
		this.algorithm = algorithm;
	}

	/**
	 * @return the algorithm
	 */
	public IAlgorithm getAlgorithm() {
		return this.algorithm;
	}

	/**
	 * @return the userDialogMsg
	 */
	public String getUserDialogMsg() {
		return this.userDialogMsg;
	}

	
}
