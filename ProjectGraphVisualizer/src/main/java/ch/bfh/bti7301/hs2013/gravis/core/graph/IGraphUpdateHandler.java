package ch.bfh.bti7301.hs2013.gravis.core.graph;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGraphUpdateHandler {

	/**
	 * 
	 * @param graphItem
	 */
	public abstract void set(IRestrictedGraphItem graphItem);

	/**
	 * 
	 * @param graphItem
	 * @param isStateCommentEnabled
	 * @param isTagged
	 */
	public abstract void set(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param isVisible
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param newResult
	 * @param isTagged
	 * @param isDashed
	 * @param value
	 * @param isDone
	 */
	public abstract void set(IRestrictedGraphItem graphItem, boolean isVisible,
			State state, boolean isStateCommentEnabled, String newComment,
			double newResult, boolean isTagged, boolean isDashed, Object value,
			boolean isDone);

	/**
	 * 
	 * @param graphItem
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 */
	public abstract void set(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param newResult
	 * @param isTagged
	 */
	public abstract void set(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, double newResult,
			boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param isTagged
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param isTagged
	 * @param isDashed
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, boolean isTagged, boolean isDashed);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param isTagged
	 * @param isDashed
	 * @param value
	 * @param isDone
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, boolean isTagged, boolean isDashed,
			Object value, boolean isDone);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param isTagged
	 * @param value
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, boolean isTagged, Object value);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 * @param value
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, boolean isTagged,
			Object value);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param newResult
	 * @param isTagged
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param newResult
	 * @param isTagged
	 * @param isDashed
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			boolean isTagged, boolean isDashed);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param newResult
	 * @param isTagged
	 * @param value
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			boolean isTagged, Object value);

	/**
	 * 
	 * @param graphItem
	 * @param newComment
	 * @param newResult
	 */
	public abstract void set(IRestrictedGraphItem graphItem, String newComment,
			double newResult);

	/**
	 * Creates a new step including all changes on graph items since last
	 * update.
	 */
	public abstract void update();

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newResult
	 * @param isTagged
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, double newResult, boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param newResult
	 * @param isTagged
	 * @param isDashed
	 * @param isDone
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			boolean isTagged, boolean isDashed, boolean isDone);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newResult
	 * @param isTagged
	 * @param isDashed
	 * @param isDone
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, double newResult, boolean isTagged,
			boolean isDashed, boolean isDone);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param isTagged
	 * @param isDashed
	 * @param isDone
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, boolean isTagged, boolean isDashed,
			boolean isDone);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 * @param isDashed
	 * @param isDone
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, boolean isTagged,
			boolean isDashed, boolean isDone);
	
	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 * @param isDashed
	 */
	public abstract void set(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, boolean isTagged,
			boolean isDashed);

	/**
	 * 
	 * @param graphItem
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 * @param isDashed
	 */
	public abstract void set(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, boolean isTagged,
			boolean isDashed);

	/**
	 * 
	 * @param graphItem
	 * @param isVisible
	 */
	public abstract void set(IRestrictedGraphItem graphItem, boolean isVisible);

}
