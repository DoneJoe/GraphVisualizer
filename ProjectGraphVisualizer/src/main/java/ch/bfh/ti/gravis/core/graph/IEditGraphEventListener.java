package ch.bfh.ti.gravis.core.graph;

import ch.bfh.ti.gravis.core.IGravisObservable;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IEditGraphEventListener extends IGravisObservable {

	public static enum Type {
		ADDED, REMOVED, EDITED
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
	 */
	public abstract void handleGraphPropertiesChangedEvent(IGravisGraph source);
}
