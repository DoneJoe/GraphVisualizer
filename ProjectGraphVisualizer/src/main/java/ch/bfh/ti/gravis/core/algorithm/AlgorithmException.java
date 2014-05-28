package ch.bfh.ti.gravis.core.algorithm;

/**
 * This Exception class can be used if the algorithm is not able to calculate a
 * correct result with the given graph.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class AlgorithmException extends Exception {

	private static final long serialVersionUID = 3774886120793515985L;

	private final String algorithmName, userDialogMsg;

	/**
	 * 
	 * @param userDialogMsg
	 * @param algorithm
	 */
	public AlgorithmException(String userDialogMsg, String algorithm) {
		this("", userDialogMsg, null, algorithm);
	}

	/**
	 * 
	 * @param message
	 * @param userDialogMsg
	 * @param algorithm
	 */
	public AlgorithmException(String message, String userDialogMsg,
			String algorithm) {

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
			Throwable cause, String algorithm) {

		super(message, cause);

		this.userDialogMsg = userDialogMsg;
		this.algorithmName = algorithm;
	}

	/**
	 * @return the algorithm name
	 */
	public String getAlgorithmName() {
		return this.algorithmName;
	}

	/**
	 * @return the userDialogMsg
	 */
	public String getUserDialogMsg() {
		return this.userDialogMsg;
	}

}
