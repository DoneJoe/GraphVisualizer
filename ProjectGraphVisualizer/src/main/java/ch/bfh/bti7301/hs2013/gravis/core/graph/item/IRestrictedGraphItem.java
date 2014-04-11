package ch.bfh.bti7301.hs2013.gravis.core.graph.item;

import java.awt.Color;


/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IRestrictedGraphItem {

	/**
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 * 
	 */
	public static enum State {
		INITIAL, ACTIVATION, VISIT, SOLUTION, REFUSE;
	}

	/**
	 * @param comment
	 */
	public abstract void appendToNewComment(String comment);

	/**
	 * 
	 * @return current color
	 */
	public abstract Color getCurrentColor();

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
	public abstract State getCurrentState();

	/**
	 * 
	 * @return String id
	 */
	public abstract String getId();

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
	public abstract State getNewState();
	
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
	public abstract boolean isStateCommentEnabled();
	
	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isTagged();
	
	/**
	 * 
	 * @return boolean
	 */
	public abstract boolean isVisible();
	
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
	public abstract void setNewDashed(boolean value);
	
	/**
	 * 
	 * @param value
	 */
	public abstract void setNewResult(double value);
	
	/**
	 * 
	 * @param newState
	 */
	public abstract void setNewState(State newState);

	/**
	 * @param value
	 */
	public abstract void setStateCommentEnabled(boolean value);
	
	/**
	 * 
	 * @param tagged
	 */
	public abstract void setTagged(boolean tagged);
	
	/**
	 * 
	 * @param value
	 */
	public abstract void setValue(Object value);
	
	/**
	 * 
	 * @param visible
	 */
	public abstract void setVisible(boolean visible);
}
