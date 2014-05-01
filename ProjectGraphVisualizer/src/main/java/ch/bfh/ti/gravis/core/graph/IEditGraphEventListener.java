package ch.bfh.ti.gravis.core.graph;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IEditGraphEventListener {

	/**
	 * VISUAL_EDITED: edited visual representation of Graph and elements (size, location, graph
	 * name, graph description).
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 *
	 */
	public static enum Type {
		ADDED, REMOVED, EDITED, VISUAL_EDITED, START_EDITED, END_EDITED
	}
	
	/**
	 * 
	 * @param source
	 * @param type
	 */
	public abstract void handleGraphItemsChangedEvent(IGraphItem source, Type type);
	
	/**
	 * 
	 * @param source
	 * @param type
	 */
	public abstract void handleGraphPropertiesChangedEvent(IGravisGraph source, Type type);
}
