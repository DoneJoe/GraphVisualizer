package ch.bfh.ti.gravis.core.graph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class GraphException extends Exception {

	private static final long serialVersionUID = -6462515714563342030L;

	final private IGravisGraph graph;
	
	public GraphException(IGravisGraph graph) {
		super();
				
		this.graph = graph;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public GraphException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace, IGravisGraph graph) {
		super(message, cause, enableSuppression, writableStackTrace);
		
		this.graph = graph;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GraphException(String message, Throwable cause, IGravisGraph graph) {
		super(message, cause);
		
		this.graph = graph;
	}

	/**
	 * @param message
	 */
	public GraphException(String message, IGravisGraph graph) {
		super(message);
		
		this.graph = graph;
	}

	/**
	 * @param cause
	 */
	public GraphException(Throwable cause, IGravisGraph graph) {
		super(cause);
		
		this.graph = graph;
	}

	/**
	 * @return the graph
	 */
	public IGravisGraph getGraph() {
		return this.graph;
	}

	
}
