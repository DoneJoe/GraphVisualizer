package ch.bfh.bti7301.hs2013.gravis.core.step;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IStep {

	/**
	 * Executes this step.
	 * 
	 * @return message
	 */
	public abstract IStepResult execute();

	/**
	 * Performs an UNDO on this step.
	 * 
	 * @return message
	 */
	public abstract IStepResult unExecute();

}
