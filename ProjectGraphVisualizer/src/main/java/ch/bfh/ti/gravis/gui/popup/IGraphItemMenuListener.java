package ch.bfh.ti.gravis.gui.popup;

import java.awt.geom.Point2D;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * Popup menu items can implement this interface and receives information about
 * the current graph item and the graph item location.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGraphItemMenuListener {

	/**
	 * Sets the current graph item.
	 * 
	 * @param item
	 */
	public abstract void setGraphItem(IGraphItem item);

	/**
	 * Sets the current graph item location.
	 * 
	 * @param point
	 */
	public abstract void setGraphItemLocation(Point2D point);
}
