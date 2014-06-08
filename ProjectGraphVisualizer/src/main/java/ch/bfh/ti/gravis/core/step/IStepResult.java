package ch.bfh.ti.gravis.core.step;

/**
 * A step result contains the comment of a step DO or UNDO operation.
 * 
 * @see IStep
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IStepResult {

	/**
	 * 
	 * @return {@code true} if this step comment is not empty
	 */
	public abstract boolean hasComment();

	/**
	 * 
	 * @return the step comment
	 */
	public abstract String getComment();
}
