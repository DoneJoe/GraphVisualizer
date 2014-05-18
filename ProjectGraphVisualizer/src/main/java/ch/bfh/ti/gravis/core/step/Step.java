package ch.bfh.ti.gravis.core.step;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class Step extends EmptyStep {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "Step.%s(): %s == %s";
	
	private final List<IStep> nestedCommands;

	protected final String comment;

	/**
	 * @param command
	 */
	protected Step(IStep command) {
		this("");
		this.nestedCommands.add(Objects.requireNonNull(command, String.format(
				NULL_POINTER_MSG, "Step", "command",
				command)));
	}

	public Step() {
		this("");
	}

	/**
	 * @param newComment
	 */
	public Step(String newComment) {
		this.nestedCommands = new ArrayList<>();
		this.comment = Objects.requireNonNull(newComment, String.format(
				NULL_POINTER_MSG, "Step", "newComment",
				newComment));
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

			if (stepRes.hasComment()) {
				totalComment.insert(0, stepRes.getComment());
			}
		}

		return new StepResult(totalComment.toString() + this.comment);
	}

	/**
	 * @param command
	 */
	public void add(IStep command) {
		this.nestedCommands.add(Objects.requireNonNull(command, String.format(
				NULL_POINTER_MSG, "add", "command",
				command)));
	}

}
