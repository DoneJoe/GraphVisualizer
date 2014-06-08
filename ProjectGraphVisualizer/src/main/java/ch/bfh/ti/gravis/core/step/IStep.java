package ch.bfh.ti.gravis.core.step;

/**
 * A step in the graph algorithm visualisation. A single step contains a
 * sequence of commands. This commands operate on a graph item and perform DO
 * and UNDO operations on the graph item properties (command design pattern). <br />
 * A step is able to contain an arbitrary number of nested steps (composite design
 * pattern).
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IStep {

	/**
	 * Performs a DO operation at this step.
	 * 
	 * @return step result
	 */
	public abstract IStepResult execute();

	/**
	 * Performs an UNDO operation at this step.
	 * 
	 * @return step result
	 */
	public abstract IStepResult unExecute();

}
