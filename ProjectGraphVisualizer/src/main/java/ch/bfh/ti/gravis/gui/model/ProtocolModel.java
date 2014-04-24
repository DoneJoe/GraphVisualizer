package ch.bfh.ti.gravis.gui.model;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class ProtocolModel {

	private final String message;
	
	private final boolean protocolCleared;
	
	/**
	 * @param message
	 */
	protected ProtocolModel(String message) {
		this(message, false);
	}
	
	/**
	 * 
	 * @param protocolCleared
	 */
	protected ProtocolModel(boolean protocolCleared) {
		this("", protocolCleared);
	}

	/**
	 * 
	 * @param message
	 * @param protocolCleared
	 */
	public ProtocolModel(String message, boolean protocolCleared) {
		this.message = message;
		this.protocolCleared = protocolCleared;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @return protocolCleared
	 */
	public boolean isProtocolCleared() {
		return this.protocolCleared;
	}

}
