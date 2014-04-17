package ch.bfh.bti7301.hs2013.gravis.core.graph;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.ItemState;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGraphUpdateHandler {

	/**
	 * 
	 * @param graphItem
	 */
	public abstract void add(IRestrictedGraphItem graphItem);

	/**
	 * 
	 * @param graphItem
	 * @param isVisible
	 */
	public abstract void add(IRestrictedGraphItem graphItem, Boolean isVisible);

	/**
	 * 
	 * @param graphItem
	 * @param isStateCommentEnabled
	 * @param isTagged
	 */
	public abstract void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, Boolean isTagged);

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
	public abstract void add(IRestrictedGraphItem graphItem, Boolean isVisible,
			ItemState state, boolean isStateCommentEnabled, String newComment,
			double newResult, Boolean isTagged, Boolean isDashed, Object value,
			boolean isDone);

	/**
	 * 
	 * @param graphItem
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 */
	public abstract void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 * @param isDashed
	 */
	public abstract void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged,
			Boolean isDashed);

	/**
	 * 
	 * @param graphItem
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param newResult
	 * @param isTagged
	 */
	public abstract void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, double newResult,
			Boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param isTagged
	 */
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, Boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param isTagged
	 * @param isDashed
	 */
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, Boolean isTagged, Boolean isDashed);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param isTagged
	 * @param isDashed
	 * @param isDone
	 */
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, Boolean isTagged, Boolean isDashed,
			boolean isDone);

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
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, Boolean isTagged, Boolean isDashed,
			Object value, boolean isDone);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param isTagged
	 * @param value
	 */
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, Boolean isTagged, Object value);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newResult
	 * @param isTagged
	 */
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, double newResult, Boolean isTagged);

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
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, double newResult, Boolean isTagged,
			Boolean isDashed, boolean isDone);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 */
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 * @param isDashed
	 */
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged,
			Boolean isDashed);

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
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged,
			Boolean isDashed, boolean isDone);

	/**
	 * 
	 * @param graphItem
	 * @param state
	 * @param isStateCommentEnabled
	 * @param newComment
	 * @param isTagged
	 * @param value
	 */
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged,
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
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			Boolean isTagged);

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
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			Boolean isTagged, Boolean isDashed);

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
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			Boolean isTagged, Boolean isDashed, boolean isDone);
	
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
	public abstract void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			Boolean isTagged, Object value);

	/**
	 * 
	 * @param graphItem
	 * @param newComment
	 * @param newResult
	 */
	public abstract void add(IRestrictedGraphItem graphItem, String newComment,
			double newResult);

	/**
	 * Creates a new step including all changes on graph items since last
	 * update.
	 */
	public abstract void update();

}
