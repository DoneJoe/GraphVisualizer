package ch.bfh.ti.gravis.core.step;

/**
 * An implementation of {@link IStepResult}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class StepResult implements IStepResult {

	private String stepComment;
	
	/**
	 * Creates a step result with comment.
	 * 
	 * @param comment
	 */
	protected StepResult(String comment) {
		this.stepComment = comment == null ? "" : comment;
	}

	/**
	 *  Creates an empty step result.
	 */
	public StepResult() {
		this("");
	}
	
	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.step.IStepResult#hasComment()
	 */
	@Override
	public boolean hasComment() {
		return !this.stepComment.trim().isEmpty();
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.step.IStepResult#getComment()
	 */
	@Override
	public String getComment() {
		return this.stepComment;
	}

}
