package ch.bfh.bti7301.hs2013.gravis.core.graph.item;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphEventListener;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IEditItemObservable {

	/**
	 * 
	 * @param listeners
	 */
	public abstract void addEditGraphEventListeners(
			IEditGraphEventListener ... listeners);

	public abstract void removeEditGraphEventListeners();
}
