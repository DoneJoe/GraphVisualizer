package ch.bfh.ti.gravis.core.graph.item;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IEditItemObservable {

	/**
	 * Adds the listeners to this edit item observable.
	 * 
	 * @param listeners
	 */
	public abstract void addEditGraphEventListeners(
			IEditGraphEventListener... listeners);

	/**
	 * Removes the listeners if the listeners were added to this edit item
	 * observable.
	 * 
	 * @param listeners
	 */
	public abstract void removeEditGraphEventListeners(
			IEditGraphEventListener... listeners);

}
