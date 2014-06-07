package ch.bfh.ti.gravis.core.graph;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * The listener interface for receiving edit events on a
 * {@link IEditGraphObservable} instance. Implementing classes can react to
 * different types of edit graph events.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IEditGraphEventListener {

	/**
	 * The different types of edit events. <br />
	 * {@code ADDED} - a new vertex or edge is added to the graph. <br />
	 * {@code REMOVED} - a vertex or edge is removed from the graph. <br />
	 * {@code VISUAL_EDITED} - edited visual representation of graph or elements
	 * (size, location, graph name, graph description). <br />
	 * {@code START_EDITED} - edited start vertex. <br />
	 * {@code END_EDITED} - edited end vertex. <br />
	 * {@code EDITED} - other edit events (edge type, element name, edge
	 * weight). <br />
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 * 
	 */
	public static enum Type {
		/**
		 * A new vertex or edge is added to the graph.
		 */
		ADDED,

		/**
		 * A vertex or edge is removed from the graph.
		 */
		REMOVED,

		/**
		 * Other edit events (edge type, element name, edge weight).
		 */
		EDITED,

		/**
		 * Edited visual representation of graph or elements (size, location,
		 * graph name, graph description).
		 */
		VISUAL_EDITED, 
		
		/**
		 * Edited start vertex.
		 */
		START_EDITED, 
		
		/**
		 * Edited end vertex.
		 */
		END_EDITED
	}

	/**
	 * Invoked when a vertex or edge property has changed.
	 * 
	 * @param source
	 * @param type
	 */
	public abstract void handleGraphItemsChangedEvent(IGraphItem source,
			Type type);

	/**
	 * Invoked when a graph property has changed.
	 * 
	 * @param source
	 * @param type
	 */
	public abstract void handleGraphPropertiesChangedEvent(IGravisGraph source,
			Type type);
}
