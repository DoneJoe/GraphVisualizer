package ch.bfh.ti.gravis.core.step;

import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * Performs a DO or UNDO operation at the graph item method
 * {@link IGraphItem#setCurrentResult(double)}.
 *  
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class ResultCommand extends EmptyStep {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "ResultCommand.%s(): %s == %s";

	private final IGraphItem item;

	private final double newResult, oldResult;

	/**
	 * @param currentItem
	 * @param oldResult
	 * @param newResult
	 * @throws NullPointerException
	 *             if currentItem is null
	 */
	protected ResultCommand(IGraphItem currentItem, double oldResult,
			double newResult) {

		this.item = Objects.requireNonNull(currentItem, String.format(
				NULL_POINTER_MSG, "ResultCommand", "currentItem", currentItem));
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
