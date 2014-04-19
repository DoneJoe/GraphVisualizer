package ch.bfh.ti.gravis.core.step;

import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.ti.gravis.core.graph.item.ItemState;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IStepRecorder {

	/**
	 * Adds the current graph item to the recorder.
	 */
	public abstract void add();
	
	/**
	 * Appends a comment to the existing newComment.
	 * 
	 * @param comment
	 * 
	 * @return IStepRecorder
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
	 * 	 Sets StateCommentEnabled visualization variable to true.
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder cmtOk();
	
	/**
	 * Sets dashed visualization variable to true.
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder dash();
	
	/**
	 * Sets is done helper variable to true.
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder done();
	
	/**
	 * Sets a graph item for recording.
	 * 
	 * @param item
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder item(IRestrictedGraphItem item);
	
	/**
	 * Sets StateCommentEnabled visualization variable to false.
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder notCmt();
	
	/**
	 * Sets dashed visualization variable to false.
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder notDash();
	
	/**
	 * 	 Sets is done helper variable to false.
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder notDone();
	
	/**
	 * Sets tagged visualization variable to false.
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder notTag();
	
	/**
	 * Sets visible visualization variable to false.
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder notVisib();
	
	
	/**
	 * Sets result visualization variable to the new value.
	 * 
	 * @param result
	 * 
	 * @return IStepRecorder
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
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder state(ItemState state);
	
	/**
	 * Sets tagged visualization variable to true.
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder tag();
	
	/**
	 * Sets value helper variable to the new value.
	 * 	 
	 * @param value
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder value(Object value);
	
	/**
	 * Sets visible visualization variable to true.
	 * 
	 * @return IStepRecorder
	 */
	public abstract IStepRecorder visib();
	
	
}
