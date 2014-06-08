package ch.bfh.ti.gravis.core.step;

import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.ti.gravis.core.graph.item.ItemState;

/**
 * A step recorder simplifies visualisation change operations at a graph item.
 * The methods in this interface return a reference to a {@code IStepRecorder}.
 * Multiple operations could be chained in a single statement.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IStepRecorder {

	/**
	 * Adds the current graph item to the step recorder sequence.
	 */
	public abstract void add();

	/**
	 * Appends a comment to the existing newComment.
	 * 
	 * @param comment
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder app(String comment);

	/**
	 * Sets comment visualization variable to the new value.
	 * 
	 * @param comment
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder cmt(String comment);

	/**
	 * Sets stateCommentEnabled visualization variable to true.
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder cmtOk();

	/**
	 * Sets dashed visualization variable to true.
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder dash();

	/**
	 * Sets done helper variable to true.
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder done();

	/**
	 * Sets a current graph item for recording.
	 * 
	 * @param item
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder item(IRestrictedGraphItem item);

	/**
	 * Sets stateCommentEnabled visualization variable to false. This is the
	 * default value.
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder notCmt();

	/**
	 * Sets dashed visualization variable to false.
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder notDash();

	/**
	 * Sets done helper variable to false.
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder notDone();

	/**
	 * Sets tagged visualization variable to false.
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder notTag();

	/**
	 * Sets visible visualization variable to false.
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder notVisib();

	/**
	 * Sets result visualization variable to the new value.
	 * 
	 * @param result
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder res(double result);

	/**
	 * Builds a new step with all added graph items since the last save and
	 * clears the list of added items.
	 */
	public abstract void save();

	/**
	 * Sets state visualization variable to the new value.
	 * 
	 * @param state
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder state(ItemState state);

	/**
	 * Sets tagged visualization variable to true.
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder tag();

	/**
	 * Sets value helper variable to the new value.
	 * 
	 * @param value
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder value(Object value);

	/**
	 * Sets visible visualization variable to true.
	 * 
	 * @return a reference to the same {@code IStepRecorder} instance
	 */
	public abstract IStepRecorder visib();

}
