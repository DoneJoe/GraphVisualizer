package ch.bfh.ti.gravis.core.graph;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type;

/**
 * This interface provides support for adding {@link IEditGraphEventListener}
 * listeners to an instance of {@link IGravisGraph}. <br />
 * 
 * An edit event of type {@link Type#EDITED} is fired if the call of method
 * {@link IGravisGraph#setEdgeType(edu.uci.ics.jung.graph.util.EdgeType)}
 * changes the existing edge type. <br />
 * 
 * An edit event of type {@link Type#VISUAL_EDITED} is fired if the call of
 * method {@link IGravisGraph#setName(String)} or
 * {@link IGravisGraph#setDescription(String)} changes the existing value.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IEditGraphObservable extends IGravisGraph {

	/**
	 * Adds a new {@code IEditGraphEventListener} to this graph.
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
