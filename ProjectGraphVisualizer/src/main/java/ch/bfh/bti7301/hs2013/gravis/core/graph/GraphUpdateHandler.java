package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State;
import static ch.bfh.bti7301.hs2013.gravis.core.util.ValueTransformer.toArray;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GraphUpdateHandler implements IGraphUpdateHandler {

	private final List<IRestrictedGraphItem> itemUpdateList;

	private final IRestrictedGraph graph;

	protected GraphUpdateHandler(IRestrictedGraph graph) {
		this.itemUpdateList = new ArrayList<IRestrictedGraphItem>();
		this.graph = graph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem) {
		this.itemUpdateList.add(graphItem);
	}

	@Override
	public void add(IRestrictedGraphItem graphItem, boolean isVisible) {
		graphItem.setVisible(isVisible);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem, boolean,
	 * boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, boolean isTagged) {
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setTagged(isTagged);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem, boolean,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, java.lang.String, double, boolean, boolean, java.lang.Object,
	 * boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, boolean isVisible,
			State state, boolean isStateCommentEnabled, String newComment,
			double newResult, boolean isTagged, boolean isDashed, Object value,
			boolean isDone) {
		graphItem.setVisible(isVisible);
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setNewResult(newResult);
		graphItem.setTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		graphItem.setValue(value);
		graphItem.setDone(isDone);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem, boolean,
	 * java.lang.String, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, boolean isTagged) {
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setTagged(isTagged);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem, boolean,
	 * java.lang.String, boolean, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, boolean isTagged,
			boolean isDashed) {
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem, boolean,
	 * java.lang.String, double, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, double newResult,
			boolean isTagged) {
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setNewResult(newResult);
		graphItem.setTagged(isTagged);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, boolean isTagged) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setTagged(isTagged);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, boolean, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, boolean isTagged, boolean isDashed) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, boolean, boolean, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, boolean isTagged, boolean isDashed,
			boolean isDone) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		graphItem.setDone(isDone);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, boolean, boolean, java.lang.Object, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, boolean isTagged, boolean isDashed,
			Object value, boolean isDone) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		graphItem.setValue(value);
		graphItem.setDone(isDone);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, boolean, java.lang.Object)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, boolean isTagged, Object value) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setTagged(isTagged);
		graphItem.setValue(value);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, double, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, double newResult, boolean isTagged) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewResult(newResult);
		graphItem.setTagged(isTagged);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, double, boolean, boolean, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, double newResult, boolean isTagged,
			boolean isDashed, boolean isDone) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewResult(newResult);
		graphItem.setTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		graphItem.setDone(isDone);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, java.lang.String, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, boolean isTagged) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setTagged(isTagged);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, java.lang.String, boolean, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, boolean isTagged,
			boolean isDashed) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, java.lang.String, boolean, boolean, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, boolean isTagged,
			boolean isDashed, boolean isDone) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		graphItem.setDone(isDone);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, java.lang.String, boolean, java.lang.Object)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, boolean isTagged,
			Object value) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setTagged(isTagged);
		graphItem.setValue(value);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, java.lang.String, double, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			boolean isTagged) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setNewResult(newResult);
		graphItem.setTagged(isTagged);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, java.lang.String, double, boolean, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			boolean isTagged, boolean isDashed) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setNewResult(newResult);
		graphItem.setTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, java.lang.String, double, boolean, boolean, boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			boolean isTagged, boolean isDashed, boolean isDone) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setNewResult(newResult);
		graphItem.setTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		graphItem.setDone(isDone);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State,
	 * boolean, java.lang.String, double, boolean, java.lang.Object)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, State state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			boolean isTagged, Object value) {
		graphItem.setNewState(state);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setNewComment(newComment);
		graphItem.setNewResult(newResult);
		graphItem.setTagged(isTagged);
		graphItem.setValue(value);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * java.lang.String, double)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, String newComment,
			double newResult) {
		graphItem.setNewComment(newComment);
		graphItem.setNewResult(newResult);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#update()
	 */
	@Override
	public void update() {
		if (!this.itemUpdateList.isEmpty()) {
			this.graph.updateState(toArray(this.itemUpdateList));
			this.itemUpdateList.clear();
		}
	}

}
