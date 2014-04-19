package ch.bfh.bti7301.hs2013.gravis.core.graph.item;

/**
 * An restricted item.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public abstract class AbstractRestrictedGraphItem implements
		IRestrictedGraphItem {

	/**
	 * A field for an item.
	 */
	private final IGraphItem item;

	/**
	 * Main constructor.
	 * 
	 * @param item
	 *            the item
	 */
	protected AbstractRestrictedGraphItem(IGraphItem item) {
		this.item = item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * appendComment(java.lang.String)
	 */
	@Override
	public void appendComment(String comment) {
		this.item.appendComment(comment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * getCurrentResult()
	 */
	@Override
	public double getCurrentResult() {
		return this.item.getCurrentResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * getCurrentState()
	 */
	@Override
	public ItemState getCurrentState() {
		return this.item.getCurrentState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#getName
	 * ()
	 */
	@Override
	public String getName() {
		return this.item.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * getNewComment()
	 */
	@Override
	public String getNewComment() {
		return this.item.getNewComment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * getNewResult()
	 */
	@Override
	public double getNewResult() {
		return this.item.getNewResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#getNewState
	 * ()
	 */
	@Override
	public ItemState getNewState() {
		return this.item.getNewState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#getValue
	 * ()
	 */
	@Override
	public Object getValue() {
		return this.item.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#isDone
	 * ()
	 */
	@Override
	public boolean isDone() {
		return this.item.isDone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#isNewDashed
	 * ()
	 */
	@Override
	public boolean isNewDashed() {
		return this.item.isNewDashed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#isNewTagged
	 * ()
	 */
	@Override
	public boolean isNewTagged() {
		return this.item.isNewTagged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * isNewVisible()
	 */
	@Override
	public boolean isNewVisible() {
		return this.item.isNewVisible();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * isStateCommentEnabled()
	 */
	@Override
	public boolean isStateCommentEnabled() {
		return this.item.isStateCommentEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * resetHelperVariables()
	 */
	@Override
	public void resetHelperVariables() {
		this.item.resetHelperVariables();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#setDone
	 * (boolean)
	 */
	@Override
	public void setDone(boolean value) {
		this.item.setDone(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * setNewComment(java.lang.String)
	 */
	@Override
	public void setNewComment(String newComment) {
		this.item.setNewComment(newComment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * setNewDashed(java.lang.Boolean)
	 */
	@Override
	public void setNewDashed(Boolean value) {
		this.item.setNewDashed(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * setNewResult(double)
	 */
	@Override
	public void setNewResult(double value) {
		this.item.setNewResult(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#setNewState
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.ItemState)
	 */
	@Override
	public void setNewState(ItemState newState) {
		this.item.setNewState(newState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * setNewTagged(java.lang.Boolean)
	 */
	@Override
	public void setNewTagged(Boolean tagged) {
		this.item.setNewTagged(tagged);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * setNewVisible(java.lang.Boolean)
	 */
	@Override
	public void setNewVisible(Boolean visible) {
		this.item.setNewVisible(visible);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * setStateCommentEnabled(boolean)
	 */
	@Override
	public void setStateCommentEnabled(boolean value) {
		this.item.setStateCommentEnabled(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#setValue
	 * (java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		this.item.setValue(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.item.toString();
	}

}
