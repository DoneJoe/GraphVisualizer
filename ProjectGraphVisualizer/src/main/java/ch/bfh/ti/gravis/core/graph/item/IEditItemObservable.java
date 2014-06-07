package ch.bfh.ti.gravis.core.graph.item;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;

/**
 * This interface provides support for adding and removing {@link IEditGraphEventListener}
 * listeners to graph items.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IEditItemObservable {

	/**
	 * Adds the listeners to this edit item observable.
	 * 
	 * @param listeners
	 * @throws NullPointerException
	 *             if listeners is null
	 */
	public abstract void addEditGraphEventListeners(
			IEditGraphEventListener... listeners);

	/**
	 * Removes the listeners (if added before) from this edit item observable.
	 * 
	 * @param listeners
	 * @throws NullPointerException
	 *             if listeners is null
	 */
	public abstract void removeEditGraphEventListeners(
			IEditGraphEventListener... listeners);

}
