package ch.bfh.ti.gravis.core.graph.item.vertex;

import java.awt.Color;
import java.awt.geom.Point2D;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * This interface gives access to mutable vertex methods.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IVertex extends IGraphItem, IRestrictedVertex {

	/**
	 * Returns the current draw color of this vertex.
	 * 
	 * @return draw color
	 */
	public abstract Color getCurrentDrawColor();
	
	/**
	 * Sets the current draw color of this vertex.
	 * 
	 * @param color
	 * @throws NullPointerException
	 *             if color is null
	 */
	public abstract void setCurrentDrawColor(Color color);
	
	/**
	 * Sets as end vertex. An edit event of type {@link Type#END_EDITED} is
	 * fired if {@code end} is different from the existing value.
	 * 
	 * @param end
	 */
	public abstract void setEnd(boolean end);

	/**
	 * Sets the height of this vertex. An edit event of type {@link Type#VISUAL_EDITED} is
	 * fired if {@code height} is different from the existing value.
	 * 
	 * @param height
	 */
	public abstract void setHeight(double height);

	/**
	 * Sets the location of this vertex. An edit event of type {@link Type#VISUAL_EDITED} is
	 * fired if {@code location} is different from the existing value.
	 * 
	 * @param location
	 * @throws NullPointerException
	 *             if location is null
	 */
	public abstract void setLocation(Point2D location);

	/**
	 * Sets as start vertex. An edit event of type {@link Type#START_EDITED} is
	 * fired if {@code start} is different from the existing value.
	 * 
	 * @param start
	 */
	public abstract void setStart(boolean start);
	
	/**
	 * Sets the width of this vertex. An edit event of type {@link Type#VISUAL_EDITED} is
	 * fired if {@code width} is different from the existing value.
	 * 
	 * @param width
	 */
	public abstract void setWidth(double width);

}
