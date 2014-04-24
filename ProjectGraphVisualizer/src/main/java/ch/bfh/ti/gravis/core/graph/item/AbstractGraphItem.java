package ch.bfh.ti.gravis.core.graph.item;

import java.awt.Color;
import java.util.Objects;

import ch.bfh.ti.gravis.core.util.GravisColor;
import ch.bfh.ti.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public abstract class AbstractGraphItem extends AbstractEditItemObservable
		implements IGraphItem {

	// new value variables:

	private String newComment;

	private double newResult;

	private boolean stateCommentEnabled;

	private Boolean newVisible = null, newTagged = null, newDashed = null;

	private ItemState newState = null;

	// current value variables:

	private double currentResult;

	private Color currentColor;

	private boolean currentVisible, currentTagged, currentDashed;

	private ItemState currentState;

	// helper variables:

	private boolean done;

	private Object value = null;

	/**
	 * Main constructor.
	 */
	protected AbstractGraphItem() {
		this.resetNewVariables();

		this.setCurrentResult(Double.NaN);
		this.setCurrentColor(GravisConstants.V_FILL_COLOR_DEFAULT);
		this.setCurrentVisible(true);
		this.setCurrentTagged(false);
		this.setCurrentDashed(false);
		this.setCurrentState(ItemState.INITIAL);

		this.resetHelperVariables();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * appendComment(java.lang.String)
	 */
	@Override
	public void appendComment(String comment) {
		// TODO Exception handling bei null values
		Objects.requireNonNull(comment);
		this.newComment += comment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IGraphItem#getCurrentColor()
	 */
	@Override
	public Color getCurrentColor() {
		return this.isCurrentVisible() ? this.currentColor : GravisColor.WHITE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * getCurrentResult()
	 */
	@Override
	public double getCurrentResult() {
		return this.currentResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * getCurrentState()
	 */
	@Override
	public ItemState getCurrentState() {
		return this.currentState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * getNewComment()
	 */
	@Override
	public String getNewComment() {
		return (this.stateCommentEnabled ? this.getNewState().getDoMessage(this) : "")
				+ this.newComment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * getNewResult()
	 */
	@Override
	public double getNewResult() {
		return Double.compare(this.newResult, Double.NaN) == 0 ? this.getCurrentResult()
				: this.newResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#getNewState
	 * ()
	 */
	@Override
	public ItemState getNewState() {
		return this.newState == null ? this.getCurrentState() : this.newState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#getValue
	 * ()
	 */
	@Override
	public Object getValue() {
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IGraphItem#isCurrentDashed()
	 */
	@Override
	public boolean isCurrentDashed() {
		return this.currentDashed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IGraphItem#isCurrentTagged()
	 */
	@Override
	public boolean isCurrentTagged() {
		return this.currentTagged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IGraphItem#isCurrentVisible
	 * ()
	 */
	@Override
	public boolean isCurrentVisible() {
		return this.currentVisible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#isDone
	 * ()
	 */
	@Override
	public boolean isDone() {
		return this.done;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#isNewDashed
	 * ()
	 */
	@Override
	public boolean isNewDashed() {
		return this.newDashed == null ? this.isCurrentDashed() : this.newDashed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#isNewTagged
	 * ()
	 */
	@Override
	public boolean isNewTagged() {
		return this.newTagged == null ? this.isCurrentTagged() : this.newTagged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * isNewVisible()
	 */
	@Override
	public boolean isNewVisible() {
		return this.newVisible == null ? this.isCurrentVisible()
				: this.newVisible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * isStateCommentEnabled()
	 */
	@Override
	public boolean isStateCommentEnabled() {
		return this.stateCommentEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * resetHelperVariables()
	 */
	@Override
	public void resetHelperVariables() {
		this.setDone(false);
		this.setValue(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IGraphItem#
	 * resetNewVariables()
	 */
	@Override
	public void resetNewVariables() {
		this.setNewComment("");
		this.setStateCommentEnabled(false);
		this.setNewState(null);
		this.setNewResult(Double.NaN);
		this.setNewVisible(null);
		this.setNewTagged(null);
		this.setNewDashed(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IGraphItem#setCurrentColor
	 * (java.awt.Color)
	 */
	@Override
	public void setCurrentColor(Color currentColor) {
		// TODO Exception handling bei null values
		this.currentColor = Objects.requireNonNull(currentColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IGraphItem#setCurrentDashed
	 * (boolean)
	 */
	@Override
	public void setCurrentDashed(boolean dashed) {
		this.currentDashed = dashed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IGraphItem#setCurrentResult
	 * (double)
	 */
	@Override
	public void setCurrentResult(double result) {
		this.currentResult = result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IGraphItem#setCurrentState
	 * (ch.bfh.ti.gravis.core.graph.item.ItemState)
	 */
	@Override
	public void setCurrentState(ItemState currentState) {
		// TODO Exception handling bei null values
		
		this.currentState = Objects.requireNonNull(currentState);
		this.setCurrentColor(currentState.getFillColor(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IGraphItem#setCurrentTagged
	 * (boolean)
	 */
	@Override
	public void setCurrentTagged(boolean tagged) {
		this.currentTagged = tagged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IGraphItem#setCurrentVisible
	 * (boolean)
	 */
	@Override
	public void setCurrentVisible(boolean visible) {
		this.currentVisible = visible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#setDone
	 * (boolean)
	 */
	@Override
	public void setDone(boolean done) {
		this.done = done;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * setNewComment(java.lang.String)
	 */
	@Override
	public void setNewComment(String newComment) {
		// TODO Exception handling bei null values
		Objects.requireNonNull(newComment);
		this.newComment = newComment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * setNewDashed(Boolean)
	 */
	@Override
	public void setNewDashed(Boolean dashed) {
		this.newDashed = dashed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * setNewResult(double)
	 */
	@Override
	public void setNewResult(double result) {
		this.newResult = result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#setNewState
	 * (ch.bfh.ti.gravis.core.graph.item.ItemState)
	 */
	@Override
	public void setNewState(ItemState newState) {
		this.newState = newState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * setNewTagged(Boolean)
	 */
	@Override
	public void setNewTagged(Boolean tagged) {
		this.newTagged = tagged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * setNewVisible(Boolean)
	 */
	@Override
	public void setNewVisible(Boolean visible) {
		this.newVisible = visible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#
	 * setStateCommentEnabled(boolean)
	 */
	@Override
	public void setStateCommentEnabled(boolean enabled) {
		this.stateCommentEnabled = enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#setValue
	 * (java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}