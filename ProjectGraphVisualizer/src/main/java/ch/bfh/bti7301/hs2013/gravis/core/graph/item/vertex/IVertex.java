package ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex;

import java.awt.Color;
import java.awt.geom.Point2D;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IVertex extends IGraphItem, IRestrictedVertex {

	/**
	 * 
	 * @return draw color
	 */
	public abstract Color getCurrentDrawColor();
	
	/**
	 * 
	 * @param color
	 */
	public abstract void setCurrentDrawColor(Color color);
	
	/**
	 * Sets as end vertex.
	 * 
	 * @param end
	 */
	public abstract void setEnd(boolean end);

	/**
	 * 
	 * @param height
	 */
	public abstract void setHeight(double height);

	/**
	 * 
	 * @param location
	 */
	public abstract void setLocation(Point2D location);

	/**
	 * Sets as start vertex.
	 * 
	 * @param start
	 */
	public abstract void setStart(boolean start);
	
	/**
	 * 
	 * @param width
	 */
	public abstract void setWidth(double width);

}
