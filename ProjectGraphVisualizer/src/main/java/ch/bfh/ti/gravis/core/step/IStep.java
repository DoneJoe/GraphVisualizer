package ch.bfh.ti.gravis.core.step;

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
