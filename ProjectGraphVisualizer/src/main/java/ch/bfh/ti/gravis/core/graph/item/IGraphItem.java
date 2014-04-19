package ch.bfh.ti.gravis.core.graph.item;

import java.awt.Color;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGraphItem extends IRestrictedGraphItem, IEditItemObservable {

	/**
	 * 
	 * @return current color
	 */
	public abstract Color getCurrentColor();

	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isCurrentDashed();

	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isCurrentTagged();
	
	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isCurrentVisible();

	public abstract void resetNewVariables();
	
	/**
	 * 
	 * @param currentColor
	 */
	public abstract void setCurrentColor(Color currentColor);
	
	/**
	 * @param value
	 */
	public abstract void setCurrentDashed(boolean value);
	
	/**
	 * 
	 * @param result
	 */
	public abstract void setCurrentResult(double result);
	
	/**
	 * null pointer throws an Exception.
	 * 
	 * @param currentState
	 */
	public abstract void setCurrentState(ItemState currentState);
	
	/**
	 * 
	 * @param tagged
	 */
	public abstract void setCurrentTagged(boolean tagged);
	
	/**
	 * 
	 * @param visible
	 */
	public abstract void setCurrentVisible(boolean visible);
	
	/**
	 * 
	 * @param name
	 */
	public abstract void setName(String name);
}