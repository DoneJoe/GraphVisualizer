package ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex;

import java.awt.geom.Point2D;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem;


/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IRestrictedVertex extends IRestrictedGraphItem {

	/**
	 * 
	 * @return double
	 */
	public abstract double getHeight();

	/**
	 * @return Point2D
	 */
	public abstract Point2D getLocation();

	/**
	 * 
	 * @return double
	 */
	public abstract double getWidth();
	
	/**
	 * Returns <code>true</code> if this is an end vertex.
	 * 
	 * @return Returns <code>true</code> if this is an end vertex
	 */
	public abstract boolean isEnd();
	
	/**
	 * Returns <code>true</code> if this is a start vertex.
	 * 
	 * @return <code>true</code> if this is a start vertex
	 */
	public abstract boolean isStart();

	
}
