package ch.bfh.bti7301.hs2013.gravis.core.graph;

import ch.bfh.bti7301.hs2013.gravis.core.IGravisObservable;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IEditableGraphEventListener extends IGravisObservable {

	/**
	 * 
	 * @param evt
	 */
	public abstract void graphItemsChangedEvent(GraphStepEvent evt);
	
	/**
	 * 
	 * @param evt
	 */
	public abstract void graphPropertiesChangedEvent(GraphStepEvent evt);
}
