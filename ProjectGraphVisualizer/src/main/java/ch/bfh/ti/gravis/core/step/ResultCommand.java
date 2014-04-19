package ch.bfh.ti.gravis.core.step;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class ResultCommand extends EmptyStep {

	private final IGraphItem item;

	private final double newResult, oldResult;

	/**
	 * @param currentItem
	 * @param oldResult
	 * @param newResult
	 */
	protected ResultCommand(IGraphItem currentItem, double oldResult,
			double newResult) {
		this.item = currentItem;
		this.oldResult = oldResult;
		this.newResult = newResult;
	}

	@Override
	public IStepResult execute() {
		this.item.setCurrentResult(this.newResult);
		return new StepResult();
	}

	@Override
	public IStepResult unExecute() {
		this.item.setCurrentResult(this.oldResult);
		return new StepResult();
	}

}
