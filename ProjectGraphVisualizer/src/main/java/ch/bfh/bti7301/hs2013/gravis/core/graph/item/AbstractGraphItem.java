package ch.bfh.bti7301.hs2013.gravis.core.graph.item;

import java.awt.Color;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphStepEvent;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisColor;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public abstract class AbstractGraphItem extends AbstractEditItemObservable
		implements IGraphItem {

	private static int counter = 0;

	private String name, newComment;

	private double currentResult, newResult;

	private State currentState, newState = null;

	private Color currentColor, newColor, oldColor;

	private float currentStrokeWidth, newStrokeWidth, oldStrokeWidth;

	private boolean done, tagged, visible, stateCommentEnabled, newDashed,
			currentDashed;

	private Object value = null;

	/**
	 * Main constructor.
	 */
	protected AbstractGraphItem() {
		super();

		// TODO weitere Events auslösen?
		
		this.name = String.valueOf(++counter);
		this.newComment = "";
		this.currentResult = this.newResult = Double.NaN;
		this.currentState = State.INITIAL;
		this.currentColor = this.oldColor = GravisConstants.V_FILL_COLOR_DEFAULT;
		this.currentStrokeWidth = this.oldStrokeWidth = GravisConstants.STROKE_WIDTH_DEFAULT;
		this.stateCommentEnabled = this.done = this.newDashed = this.currentDashed = false;

		this.setNewColor(GravisConstants.V_FILL_COLOR_DEFAULT);
		this.setNewStrokeWidth(GravisConstants.STROKE_WIDTH_DEFAULT);
		this.setVisible(true);
		this.setTagged(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * appendComment(java.lang.String)
	 */
	@Override
	public void appendComment(String comment) {
		this.newComment += System.lineSeparator() + comment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AbstractGraphItem clone() throws CloneNotSupportedException {
		return (AbstractGraphItem) super.clone();
	}

	@Override
	public Color getCurrentColor() {
		return this.currentColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.common.IGraphItem#getPaintedResult()
	 */
	@Override
	public double getCurrentResult() {
		return this.newResult;
	}

	@Override
	public State getCurrentState() {
		return this.currentState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#getStrokeWidth()
	 */
	@Override
	public float getCurrentStrokeWidth() {
		return this.currentStrokeWidth;
	}

	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#getNewColor()
	 */
	@Override
	public Color getNewColor() {
		return this.newColor;
	}

	@Override
	public String getNewComment() {
		return this.newComment;
	}

	@Override
	public double getNewResult() {
		return this.currentResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * getTraversalState()
	 */
	@Override
	public State getNewState() {
		return this.newState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#getNewStrokeWidth
	 * ()
	 */
	@Override
	public float getNewStrokeWidth() {
		return this.newStrokeWidth;
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
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#hasNoResult()
	 */
	@Override
	public boolean hasNoResult() {
		return Double.isNaN(this.currentResult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#isCurrentDashed()
	 */
	@Override
	public boolean isCurrentDashed() {
		return this.currentDashed;
	}

	@Override
	public boolean isDone() {
		return this.done;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * isDashedStroke()
	 */
	@Override
	public boolean isNewDashed() {
		return this.newDashed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * isStateCommentEnabled()
	 */
	@Override
	public boolean isStateCommentEnabled() {
		return this.stateCommentEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#isTagged
	 * ()
	 */
	@Override
	public boolean isTagged() {
		return this.tagged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#isVisible
	 * ()
	 */
	@Override
	public boolean isVisible() {
		return this.visible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#
	 * resetVisualizationValues()
	 */
	@Override
	public void resetVisualizationValues() {
		// TODO test, empty string literal constant
		this.newComment = "";
		// gilt nur für aktuellen Step
		this.currentResult = Double.NaN;
		this.newState = null;
		this.stateCommentEnabled = false;
		// this.newDashed = false;
		// this.newStrokeWidth = Float.NaN;
	}

	@Override
	public void setCurrentColor(Color color) {
		this.currentColor = color;
		this.visible = color != GravisColor.WHITE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#setCurrentDashed
	 * (boolean)
	 */
	@Override
	public void setCurrentDashed(boolean value) {
		this.currentDashed = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.common.IGraphItem#setPaintedResult(double)
	 */
	@Override
	public void setCurrentResult(double result) {
		this.newResult = result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#setState(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State)
	 */
	@Override
	public void setCurrentState(State state) {
		this.currentState = state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#setStrokeWidth
	 * (float)
	 */
	@Override
	public void setCurrentStrokeWidth(float strokeWidth) {
		this.currentStrokeWidth = strokeWidth;
	}

	@Override
	public void setDone(boolean value) {
		this.done = value;
	}

	@Override
	public void setName(String id) {
		boolean equal = this.name.equals(id.trim());
		this.name = id.trim();

		if (!equal) {
			this.fireGraphItemsChangedEvent(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#setNewColor(java
	 * .awt.Color)
	 */
	@Override
	public void setNewColor(Color newColor) {
		if (newColor != GravisColor.WHITE) {
			this.oldColor = newColor;
		}

		this.newColor = newColor;
		this.visible = newColor != GravisColor.WHITE;
	}

	@Override
	public void setNewComment(String comment) {
		this.newComment = comment.trim();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * setDashedStroke(boolean)
	 */
	@Override
	public void setNewDashed(boolean value) {
		this.newDashed = value;
	}

	@Override
	public void setNewResult(double value) {
		this.currentResult = value;
	}

	@Override
	public void setNewState(State traversalState) {
		this.newState = traversalState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#setNewStrokeWidth
	 * (float)
	 */
	@Override
	public void setNewStrokeWidth(float width) {
		if (width != this.getTaggedStrokeWidth()) {
			this.oldStrokeWidth = width;
		}

		this.newStrokeWidth = width;
		this.tagged = width == this.getTaggedStrokeWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#
	 * setStateCommentEnabled(boolean)
	 */
	@Override
	public void setStateCommentEnabled(boolean value) {
		this.stateCommentEnabled = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#setTagged
	 * (boolean)
	 */
	@Override
	public void setTagged(boolean tagged) {
		this.tagged = tagged;
		this.newStrokeWidth = tagged ? this.getTaggedStrokeWidth()
				: this.oldStrokeWidth;
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
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#setVisible
	 * (boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
		this.newColor = visible ? this.oldColor : GravisColor.WHITE;
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

	/**
	 * @return float
	 */
	protected abstract float getTaggedStrokeWidth();

}