package ch.bfh.ti.gravis.core.step;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A complex step contains an arbitrary number of nested steps (composite design
 * pattern).
 * 
 * @see IStep
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class ComplexStep extends EmptyStep {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "ComplexStep.%s(): %s == %s";

	private final List<IStep> nestedSteps;

	protected final String comment;

	/**
	 * Creates a complex step containing the given step.
	 * 
	 * @param step
	 * @throws NullPointerException
	 *             if step is null
	 */
	protected ComplexStep(IStep step) {
		this("");
		this.nestedSteps
				.add(Objects.requireNonNull(step, String.format(
						NULL_POINTER_MSG, "ComplexStep", "step", step)));
	}

	/**
	 * Creates an empty complex step.
	 */
	public ComplexStep() {
		this("");
	}

	/**
	 * Creates a complex step containing the given comment.
	 * 
	 * @param comment
	 */
	public ComplexStep(String comment) {
		this.nestedSteps = new ArrayList<>();
		this.comment = comment == null ? "" : comment;
	}

	@Override
	public IStepResult execute() {
		StringBuilder totalComment = new StringBuilder();
		IStepResult stepRes;

		// Performs a DO operation with all nested steps
		for (IStep command : this.nestedSteps) {
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

		// Performs an UNDO operation with all nested steps
		for (int i = this.nestedSteps.size() - 1; i >= 0; i--) {
			stepRes = this.nestedSteps.get(i).unExecute();

			if (stepRes.hasComment()) {
				totalComment.insert(0, stepRes.getComment());
			}
		}

		return new StepResult(totalComment.toString() + this.comment);
	}

	/**
	 * Adds a new step to this complex step.
	 * 
	 * @param step
	 * @throws NullPointerException
	 *             if step is null
	 */
	public void add(IStep step) {
		this.nestedSteps.add(Objects.requireNonNull(step,
				String.format(NULL_POINTER_MSG, "add", "step", step)));
	}

}
