package ch.bfh.bti7301.hs2013.gravis.core.graph.item;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IEditingGraphItem {

	/**
	 * 
	 * @param listeners
	 */
	public abstract void addEditingGraphEventListener(IEditingGraphEventListener ... listeners);
	
	public abstract void removeEditingGraphEventListeners();
}
