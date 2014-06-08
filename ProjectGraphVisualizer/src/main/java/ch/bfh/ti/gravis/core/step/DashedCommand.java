package ch.bfh.ti.gravis.core.step;

import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * Performs a DO or UNDO operation at the graph item method
 * {@link IGraphItem#setCurrentDashed(boolean)}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class DashedCommand extends EmptyStep {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "DashedCommand.%s(): %s == %s";

	private final IGraphItem item;

	private final boolean oldDashed, newDashed;

	/**
	 * 
	 * @param currentItem
	 * @param oldDashed
	 * @param newDashed
	 * @throws NullPointerException
	 *             if currentItem is null
	 */
	protected DashedCommand(IGraphItem currentItem, boolean oldDashed,
			boolean newDashed) {

		this.item = Objects.requireNonNull(currentItem, String.format(
				NULL_POINTER_MSG, "DashedCommand", "currentItem", currentItem));
		this.oldDashed = oldDashed;
		this.newDashed = newDashed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#execute()
	 */
	@Override
	public IStepResult execute() {
		this.item.setCurrentDashed(this.newDashed);
		return new StepResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#unExecute()
	 */
	@Override
	public IStepResult unExecute() {
		this.item.setCurrentDashed(this.oldDashed);
		return new StepResult();
	}

}
