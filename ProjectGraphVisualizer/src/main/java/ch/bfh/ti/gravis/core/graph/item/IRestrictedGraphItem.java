package ch.bfh.ti.gravis.core.graph.item;


/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IRestrictedGraphItem {

	/**
	 * @param comment
	 */
	public abstract void appendComment(String comment);

	/**
	 * The current result of this item.
	 * 
	 * @return double
	 */
	public abstract double getCurrentResult();
	
	/**
	 * 
	 * @return State
	 */
	public abstract ItemState getCurrentState();

	/**
	 * 
	 * @return name
	 */
	public abstract String getName();

	/**
	 * 
	 * @return newComment
	 */
	public abstract String getNewComment();

	/**
	 * 
	 * @return double value
	 */
	public abstract double getNewResult();

	/**
	 * 
	 * @return State
	 */
	public abstract ItemState getNewState();
	
	/**
	 * 
	 * @return State
	 */
	public abstract Object getValue();

	/**
	 * @return boolean
	 */
	public abstract boolean isDone();

	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isNewDashed();
	
	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isNewTagged();
	
	/**
	 * 
	 * 
	 * @return boolean
	 */
	public abstract boolean isNewVisible();
	
	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isInitial();
	
	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isActivated();
	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isVisited();
	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isSolved();
	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isDiscarded();
	
	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isStateCommentEnabled();
	
	public abstract void resetHelperVariables();

	/**
	 * 
	 * @param value
	 */
	public abstract void setDone(boolean value);
	
	/**
	 * 
	 * @param newComment
	 */
	public abstract void setNewComment(String newComment);
	
	/**
	 * @param value
	 */
	public abstract void setNewDashed(Boolean value);
	
	/**
	 * 
	 * @param value
	 */
	public abstract void setNewResult(double value);

	/**
	 * 
	 * @param newState
	 */
	public abstract void setNewState(ItemState newState);
	
	/**
	 * 
	 * @param tagged
	 */
	public abstract void setNewTagged(Boolean tagged);
	
	/**
	 * 
	 * @param visible
	 */
	public abstract void setNewVisible(Boolean visible);
	
	/**
	 * @param value
	 */
	public abstract void setStateCommentEnabled(boolean value);
	
	/**
	 * 
	 * @param value
	 */
	public abstract void setValue(Object value);
	
}
