package ch.bfh.bti7301.hs2013.gravis.core.graph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IEditingGraphEventListener {

	/**
	 * 
	 * @param evt
	 */
	public abstract void handleEditingGraphEvent(GravisGraphEvent evt);
	
	/**
	 * 
	 * @param evt
	 */
	public abstract void handleNameChangedEvent(GravisGraphEvent evt);
}
