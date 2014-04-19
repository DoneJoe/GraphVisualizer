package ch.bfh.bti7301.hs2013.gravis.core.step;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.ItemState;
import static ch.bfh.bti7301.hs2013.gravis.core.util.ValueTransformer.toArray;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class StepRecorder implements IStepRecorder {

	private final IRestrictedGraph graph;

	private final List<IRestrictedGraphItem> itemList;

	private IRestrictedGraphItem currentItem = null;

	/**
	 * @param restrictedGraph
	 */
	protected StepRecorder(IRestrictedGraph restrictedGraph) {
		this.graph = restrictedGraph;
		this.itemList = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#item(ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.IRestrictedGraphItem)
	 */
	@Override
	public IStepRecorder item(IRestrictedGraphItem item) {
		this.currentItem = item;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#add()
	 */
	@Override
	public void add() {
		if (this.currentItem != null) {
			this.itemList.add(this.currentItem);
			this.currentItem = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#save()
	 */
	@Override
	public void save() {
		if (!this.itemList.isEmpty()) {
			this.graph.addStep(toArray(this.itemList));
			this.itemList.clear();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#done()
	 */
	@Override
	public IStepRecorder done() {
		if (this.currentItem != null) {
			this.currentItem.setDone(true);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#notDone()
	 */
	@Override
	public IStepRecorder notDone() {
		if (this.currentItem != null) {
			this.currentItem.setDone(false);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#visib()
	 */
	@Override
	public IStepRecorder visib() {
		if (this.currentItem != null) {
			this.currentItem.setNewVisible(true);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#notVisib()
	 */
	@Override
	public IStepRecorder notVisib() {
		if (this.currentItem != null) {
			this.currentItem.setNewVisible(false);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#tag()
	 */
	@Override
	public IStepRecorder tag() {
		if (this.currentItem != null) {
			this.currentItem.setNewTagged(true);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#notTag()
	 */
	@Override
	public IStepRecorder notTag() {
		if (this.currentItem != null) {
			this.currentItem.setNewTagged(false);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#dash()
	 */
	@Override
	public IStepRecorder dash() {
		if (this.currentItem != null) {
			this.currentItem.setNewDashed(true);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#notDash()
	 */
	@Override
	public IStepRecorder notDash() {
		if (this.currentItem != null) {
			this.currentItem.setNewDashed(false);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#cmtOk()
	 */
	@Override
	public IStepRecorder cmtOk() {
		if (this.currentItem != null) {
			this.currentItem.setStateCommentEnabled(true);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#notCmt()
	 */
	@Override
	public IStepRecorder notCmt() {
		if (this.currentItem != null) {
			this.currentItem.setStateCommentEnabled(false);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#cmt(java.lang.String
	 * )
	 */
	@Override
	public IStepRecorder cmt(String comment) {
		if (this.currentItem != null) {
			this.currentItem.setNewComment(comment);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#value(java.lang.
	 * Object)
	 */
	@Override
	public IStepRecorder value(Object value) {
		if (this.currentItem != null) {
			this.currentItem.setValue(value);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#res(double)
	 */
	@Override
	public IStepRecorder res(double result) {
		if (this.currentItem != null) {
			this.currentItem.setNewResult(result);

		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#state(ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.item.ItemState)
	 */
	@Override
	public IStepRecorder state(ItemState state) {
		if (this.currentItem != null) {
			this.currentItem.setNewState(state);
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder#app(java.lang.String)
	 */
	@Override
	public IStepRecorder app(String comment) {
		if (this.currentItem != null) {
			this.currentItem.appendComment(comment);
		}
		return this;
	}

}
