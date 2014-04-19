package ch.bfh.ti.gravis.core.step;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class StepResult implements IStepResult {

	private String stepComment;
	
	/**
	 * 
	 * @param comment
	 */
	protected StepResult(String comment) {
		this.stepComment = comment;
	}

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
