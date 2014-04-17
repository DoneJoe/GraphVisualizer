package ch.bfh.bti7301.hs2013.gravis.core.step;

import java.util.Objects;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class EmptyStep implements IStep {

	protected final String comment;
	
	protected EmptyStep(String comment) {
		// TODO Exception handling bei null values
		Objects.requireNonNull(comment);
		this.comment = comment.trim();
	}
	
	protected EmptyStep() {
		this("");
	}

	@Override
	public IStepResult execute() {
		// no operation
		return new StepResult(this.comment);
	}

	@Override
	public IStepResult unExecute() {
		// no operation
		return new StepResult(this.comment);
	}

}
