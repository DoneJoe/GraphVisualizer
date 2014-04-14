package ch.bfh.bti7301.hs2013.gravis.core.graph;

import edu.uci.ics.jung.io.GraphIOException;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class GravisGraphIOException extends GraphIOException {

	private static final long serialVersionUID = 2920173837833768257L;
	
	public GravisGraphIOException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GravisGraphIOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public GravisGraphIOException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public GravisGraphIOException(Throwable cause) {
		super(cause);
	}


}
