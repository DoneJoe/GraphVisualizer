package ch.bfh.ti.gravis.core.graph.item;

import java.awt.Color;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type;
import ch.bfh.ti.gravis.core.util.GravisColor;

/**
 * This interface gives access to common methods on vertices and edges.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGraphItem extends IRestrictedGraphItem, IEditItemObservable {

	/**
	 * Returns the current color of this graph item if this item is visible.
	 * This method return {@link GravisColor#WHITE} if this item is not visible.
	 * 
	 * @return current color or white
	 */
	public abstract Color getCurrentColor();

	/**
	 * 
	 * @return current dashed value
	 */
	public abstract boolean isCurrentDashed();

	/**
	 * 
	 * @return current tagged value
	 */
	public abstract boolean isCurrentTagged();

	/**
	 * 
	 * @return current visible value
	 */
	public abstract boolean isCurrentVisible();

	/**
	 * Resets the item {@code new} variables to the initial values. This method
	 * calls : <br />
	 * {@code this.setNewComment("");} <br />
	 * {@code this.setStateCommentEnabled(false);} <br />
	 * {@code this.setNewState(null);} <br />
	 * {@code this.setNewResult(Double.NaN);} <br />
	 * {@code this.setNewVisible(null);} <br />
	 * {@code this.setNewTagged(null);} <br />
	 * {@code this.setNewDashed(null);} <br />
	 */
	public abstract void resetNewVariables();

	/**
	 * 
	 * @param currentColor
	 * @throws NullPointerException
	 *             if currentColor is null
	 */
	public abstract void setCurrentColor(Color currentColor);

	/**
	 * @param value
	 */
	public abstract void setCurrentDashed(boolean value);

	/**
	 * Sets the current calculation result.
	 * 
	 * @param result
	 */
	public abstract void setCurrentResult(double result);

	/**
	 * 
	 * @param currentState
	 * @throws NullPointerException
	 *             if currentState is null
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
	 * Sets the unique name of this graph item. An edit event of type
	 * {@link Type#EDITED} is fired if {@code name} is different from the
	 * existing value.
	 * 
	 * @param name
	 * @throws NullPointerException
	 *             if name is null
	 */
	public abstract void setName(String name);
}