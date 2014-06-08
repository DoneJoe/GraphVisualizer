package ch.bfh.ti.gravis.core.graph.item;

/**
 * This interface gives restricted access to common methods on vertices and
 * edges. A restricted graph item is either a vertex or an edge with restricted
 * access to setter methods. Only setter methods on {@code new} variables or
 * setter methods on helper variables ({@code done}, {@code value}) are
 * accessable with this interface. <br />
 * The {@code new} variables are temporary and the {@code current} variables are
 * permanent. Both type of variables are used for graph algorithm visualisation.
 * Temporary variables are reset after each step in algorithm execution. <br />
 * The {@code done} variable can store an arbitrary boolean value. The
 * {@code value} variable can store an arbitrary object reference.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IRestrictedGraphItem {

	/**
	 * Appends a comment to an existing comment.
	 * 
	 * @param comment
	 */
	public abstract void appendComment(String comment);

	/**
	 * Returns the current calculation result of this item.
	 * 
	 * @return current result
	 */
	public abstract double getCurrentResult();

	/**
	 * Returns the current state of this item.
	 * 
	 * @return current state
	 */
	public abstract ItemState getCurrentState();

	/**
	 * Returns the name of this item (unique id in the graph).
	 * 
	 * @return name
	 */
	public abstract String getName();

	/**
	 * Returns the new comment of this item (includes default state comment if
	 * {@link #isStateCommentEnabled} is true).
	 * 
	 * @return new comment
	 */
	public abstract String getNewComment();

	/**
	 * Returns the new calculation result of this item. The current result is
	 * returned if the new result is not set.
	 * 
	 * @return new calculation result
	 */
	public abstract double getNewResult();

	/**
	 * Returns the new state of this item. The current state is returned if the
	 * new state is not set.
	 * 
	 * @return new state
	 */
	public abstract ItemState getNewState();

	/**
	 * Returns the value object helper variable of this item.
	 * 
	 * @return value object
	 */
	public abstract Object getValue();

	/**
	 * Returns the boolean value helper variable of this item.
	 * 
	 * @return boolean value
	 */
	public abstract boolean isDone();

	/**
	 * Returns true if this item is labeled with new dashed. The current dashed
	 * value is returned if the new dashed value is not set.
	 * 
	 * @return boolean
	 */
	public abstract boolean isNewDashed();

	/**
	 * Returns true if this item is labeled with new tagged. The current tagged
	 * value is returned if the new tagged value is not set.
	 * 
	 * @return boolean
	 */
	public abstract boolean isNewTagged();

	/**
	 * Returns true if this item is labeled with new visible. The current
	 * visible value is returned if the new visible value is not set.
	 * 
	 * @return boolean
	 */
	public abstract boolean isNewVisible();

	/**
	 * Returns true if this item is in INITIAL state.
	 * 
	 * @return boolean
	 */
	public abstract boolean isInitial();

	/**
	 * Returns true if this item is in ACTIVATED state.
	 * 
	 * @return boolean
	 */
	public abstract boolean isActivated();

	/**
	 * Returns true if this item is in VISITED state.
	 * 
	 * @return boolean
	 */
	public abstract boolean isVisited();

	/**
	 * Returns true if this item is in SOLVED state.
	 * 
	 * @return boolean
	 */
	public abstract boolean isSolved();

	/**
	 * Returns true if this item is in DISCARDED state.
	 * 
	 * @return boolean
	 */
	public abstract boolean isDiscarded();

	/**
	 * Returns true if the comment includes the default state comment.
	 * 
	 * @return boolean
	 */
	public abstract boolean isStateCommentEnabled();

	/**
	 * Resets the item helper variables to the initial values. This method
	 * calls: <br />
	 * {@code this.setDone(false);} <br />
	 * {@code this.setValue(null);}
	 */
	public abstract void resetHelperVariables();

	/**
	 * Sets the boolean helper variable.
	 * 
	 * @param value
	 */
	public abstract void setDone(boolean value);

	/**
	 * Sets a new comment.
	 * 
	 * @param newComment
	 */
	public abstract void setNewComment(String newComment);

	/**
	 * Sets the new dashed value.
	 * 
	 * @param value
	 */
	public abstract void setNewDashed(Boolean value);

	/**
	 * Sets the new calculation result value.
	 * 
	 * @param value
	 */
	public abstract void setNewResult(double value);

	/**
	 * Sets the new state value.
	 * 
	 * @param newState
	 */
	public abstract void setNewState(ItemState newState);

	/**
	 * Sets the new tagged value.
	 * 
	 * @param tagged
	 */
	public abstract void setNewTagged(Boolean tagged);

	/**
	 * Sets the new visible value.
	 * 
	 * @param visible
	 */
	public abstract void setNewVisible(Boolean visible);

	/**
	 * Sets the boolean value if the comment includes the default state comment.
	 * 
	 * @param value
	 */
	public abstract void setStateCommentEnabled(boolean value);

	/**
	 * Sets the value object helper variable.
	 * 
	 * @param value
	 */
	public abstract void setValue(Object value);

}
