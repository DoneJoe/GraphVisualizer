package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.ItemState;
import static ch.bfh.bti7301.hs2013.gravis.core.util.ValueTransformer.toArray;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GraphUpdateHandler implements IGraphUpdateHandler {

	private final List<IRestrictedGraphItem> itemUpdateList;

	private final IRestrictedGraph graph;

	/**
	 * 
	 * @param graph
	 */
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
		
		this.add(graphItem, null, null, graphItem.isStateCommentEnabled(),
				"", graphItem.getCurrentResult(), null, null, graphItem.getValue(), 
				graphItem.isDone());
	}

	@Override
	public void add(IRestrictedGraphItem graphItem, Boolean isVisible) {
		
		this.add(graphItem, isVisible, null, graphItem.isStateCommentEnabled(),
				"", graphItem.getCurrentResult(), null, null, graphItem.getValue(), 
				graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem, Boolean,
	 * Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, Boolean isTagged) {
		
		this.add(graphItem, null, null, graphItem.isStateCommentEnabled(),
				"", graphItem.getCurrentResult(), isTagged, null, graphItem.getValue(), 
				graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem, Boolean,
	 * ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState,
	 * Boolean, java.lang.String, double, Boolean, Boolean, java.lang.Object,
	 * boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, Boolean isVisible,
			ItemState state, boolean isStateCommentEnabled, String newComment,
			double newResult, Boolean isTagged, Boolean isDashed, Object value,
			boolean isDone) {

		graphItem.setNewVisible(isVisible);
		graphItem.setNewState(state);
		graphItem.setNewComment(newComment);
		graphItem.setNewResult(newResult);
		graphItem.setNewTagged(isTagged);
		graphItem.setNewDashed(isDashed);
		graphItem.setStateCommentEnabled(isStateCommentEnabled);
		graphItem.setValue(value);
		graphItem.setDone(isDone);
		this.itemUpdateList.add(graphItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem, Boolean,
	 * java.lang.String, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged) {
		
		this.add(graphItem, null, null, isStateCommentEnabled,
				newComment, graphItem.getCurrentResult(), isTagged, null, 
				graphItem.getValue(), graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem, Boolean,
	 * java.lang.String, Boolean, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged,
			Boolean isDashed) {
		
		this.add(graphItem, null, null, isStateCommentEnabled,
				newComment, graphItem.getCurrentResult(), isTagged, isDashed, 
				graphItem.getValue(), graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem, Boolean,
	 * java.lang.String, double, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem,
			boolean isStateCommentEnabled, String newComment, double newResult,
			Boolean isTagged) {
		
		this.add(graphItem, null, null, isStateCommentEnabled,
				newComment, newResult, isTagged, null, graphItem.getValue(), 
				graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, Boolean isTagged) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				"", graphItem.getCurrentResult(), isTagged, null, 
				graphItem.getValue(), graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * Boolean, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, Boolean isTagged, Boolean isDashed) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				"", graphItem.getCurrentResult(), isTagged, isDashed, 
				graphItem.getValue(), graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * Boolean, Boolean, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, Boolean isTagged, Boolean isDashed,
			boolean isDone) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				"", graphItem.getCurrentResult(), isTagged, isDashed, 
				graphItem.getValue(), isDone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * Boolean, Boolean, java.lang.Object, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, Boolean isTagged, Boolean isDashed,
			Object value, boolean isDone) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				"", graphItem.getCurrentResult(), isTagged, isDashed, value, isDone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * Boolean, java.lang.Object)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, Boolean isTagged, Object value) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				"", graphItem.getCurrentResult(), isTagged, null, value, 
				graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * double, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, double newResult, Boolean isTagged) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				"", newResult, isTagged, null, graphItem.getValue(), 
				graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * double, Boolean, Boolean, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, double newResult, Boolean isTagged,
			Boolean isDashed, boolean isDone) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				"", newResult, isTagged, isDashed, graphItem.getValue(), isDone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * java.lang.String, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				newComment, graphItem.getCurrentResult(), isTagged, null, 
				graphItem.getValue(), graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * java.lang.String, Boolean, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged,
			Boolean isDashed) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				newComment, graphItem.getCurrentResult(), isTagged, isDashed, 
				graphItem.getValue(), graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * java.lang.String, Boolean, Boolean, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged,
			Boolean isDashed, boolean isDone) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				newComment, graphItem.getCurrentResult(), isTagged, isDashed, 
				graphItem.getValue(), isDone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * java.lang.String, Boolean, java.lang.Object)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, Boolean isTagged,
			Object value) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				newComment, graphItem.getCurrentResult(), isTagged, null, value, 
				graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * java.lang.String, double, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			Boolean isTagged) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				newComment, newResult, isTagged, null, graphItem.getValue(), 
				graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * java.lang.String, double, Boolean, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			Boolean isTagged, Boolean isDashed) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				newComment, newResult, isTagged, isDashed, graphItem.getValue(), 
				graphItem.isDone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * java.lang.String, double, Boolean, Boolean, Boolean)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			Boolean isTagged, Boolean isDashed, boolean isDone) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				newComment, newResult, isTagged, isDashed, graphItem.getValue(), isDone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler#set(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem,
	 * ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem.ItemState, Boolean,
	 * java.lang.String, double, Boolean, java.lang.Object)
	 */
	@Override
	public void add(IRestrictedGraphItem graphItem, ItemState state,
			boolean isStateCommentEnabled, String newComment, double newResult,
			Boolean isTagged, Object value) {
		
		this.add(graphItem, null, state, isStateCommentEnabled,
				newComment, newResult, isTagged, null, value, graphItem.isDone());
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
		
		this.add(graphItem, null, null, graphItem.isStateCommentEnabled(),
				newComment, newResult, null, null, graphItem.getValue(), 
				graphItem.isDone());
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
