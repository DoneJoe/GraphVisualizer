package ch.bfh.bti7301.hs2013.gravis.core.graph;

import ch.bfh.bti7301.hs2013.gravis.core.IGravisObservable;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IEditingGraphEventListener extends IGravisObservable {

	/**
	 * 
	 * @param evt
	 */
	public abstract void handleEditingGraphEvent(GraphStepEvent evt);
	
	/**
	 * 
	 * @param evt
	 */
	public abstract void handleNameChangedEvent(GraphStepEvent evt);
}
