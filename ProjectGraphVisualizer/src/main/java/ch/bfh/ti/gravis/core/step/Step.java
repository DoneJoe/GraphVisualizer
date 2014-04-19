package ch.bfh.ti.gravis.core.step;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class Step extends EmptyStep {

	private final List<IStep> nestedCommands;

	protected final String comment;

	/**
	 * @param command
	 */
	protected Step(IStep command) {
		this("");
		this.nestedCommands.add(command);
	}

	public Step() {
		this("");
	}

	/**
	 * @param newComment
	 */
	public Step(String newComment) {
		this.nestedCommands = new ArrayList<>();
		this.comment = newComment;
	}

	@Override
	public IStepResult execute() {
		StringBuilder totalComment = new StringBuilder();
		IStepResult stepRes;

		for (IStep command : this.nestedCommands) {
			stepRes = command.execute();

			if (stepRes.hasComment()) {
				totalComment.append(stepRes.getComment());
			}
		}
		
		return new StepResult(totalComment.toString() + this.comment);
	}

	@Override
	public IStepResult unExecute() {
		StringBuilder totalComment = new StringBuilder();
		IStepResult stepRes;

		for (int i = this.nestedCommands.size() - 1; i >= 0; i--) {
			stepRes = this.nestedCommands.get(i).unExecute();

			if (!stepRes.hasComment()) {
				totalComment.append(stepRes.getComment());
			}
		}

		return new StepResult(this.comment + totalComment.toString());
	}

	/**
	 * @param command
	 */
	public void add(IStep command) {
		this.nestedCommands.add(command);
	}

}
