package ch.bfh.ti.gravis.core.graph.item.vertex;

import java.awt.geom.Point2D;

import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;


/**
 * This interface gives restricted access to vertex methods. Only getter methods are accessable.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IRestrictedVertex extends IRestrictedGraphItem {

	/**
	 * Returns the height of this vertex.
	 * 
	 * @return height
	 */
	public abstract double getHeight();

	/**
	 * Returns the location of this vertex.
	 * 
	 * @return location
	 */
	public abstract Point2D getLocation();

	/**
	 * Returns the width of this vertex.
	 * 
	 * @return width
	 */
	public abstract double getWidth();
	
	/**
	 * Returns <code>true</code> if this is the end vertex.
	 * 
	 * @return <code>true</code> if this is the end vertex
	 */
	public abstract boolean isEnd();
	
	/**
	 * Returns <code>true</code> if this is the start vertex.
	 * 
	 * @return <code>true</code> if this is the start vertex
	 */
	public abstract boolean isStart();

	
}
