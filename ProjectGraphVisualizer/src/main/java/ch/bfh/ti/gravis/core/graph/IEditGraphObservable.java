package ch.bfh.ti.gravis.core.graph;

/**
 * This interface provides support for adding {@link IEditGraphEventListener}
 * listeners to an instance of {@link IGravisGraph}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IEditGraphObservable extends IGravisGraph {

	/**
	 * Adds an new {@code IEditGraphEventListener} to this graph.
	 * 
	 * @param listener
	 * @throws NullPointerException
	 *             if listener is null
	 */
	public abstract void addEditGraphEventListener(
			IEditGraphEventListener listener);

	/**
	 * Returns all added {@code IEditGraphEventListener} listeners.
	 * 
	 * @return all added {@code IEditGraphEventListener} listeners
	 */
	public abstract IEditGraphEventListener[] getEditGraphEventListeners();

}
