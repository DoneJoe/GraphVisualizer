package ch.bfh.bti7301.hs2013.gravis.core.step;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class Step extends EmptyStep {

	private final List<IStep> nestedCommands;

	/**
	 * @param command
	 */
	public Step(IStep command) {
		this();

		this.nestedCommands.add(command);
	}

	public Step() {
		this.nestedCommands = new ArrayList<>();
	}

	@Override
	public IStepResult execute() {
		StringBuilder totalComment = new StringBuilder();
		IStepResult stepResult;

		for (IStep command : this.nestedCommands) {
			stepResult = command.execute();
			totalComment.append(stepResult.getComment());
		}

		return new StepResult(totalComment.toString().trim());
	}

	@Override
	public IStepResult unExecute() {
		StringBuilder totalComment = new StringBuilder();
		IStepResult stepResult;

		for (int i = this.nestedCommands.size() - 1; i >= 0; i--) {
			stepResult = this.nestedCommands.get(i).unExecute();
			totalComment.append(stepResult.getComment());
		}

		return new StepResult(totalComment.toString().trim());
	}

	/**
	 * @param command
	 */
	public void add(IStep command) {
		this.nestedCommands.add(command);
	}

}
