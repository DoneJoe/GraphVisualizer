package ch.bfh.ti.gravis.gui.visualization.popup;

import java.awt.geom.Point2D;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IGraphItemMenuListener {

	/**
	 * 
	 * @param item
	 */
	public abstract void setGraphItemAndView(IGraphItem item);
	
	/**
	 * 
	 * @param point
	 */
	public abstract void setGraphItemLocation(Point2D point);
}
