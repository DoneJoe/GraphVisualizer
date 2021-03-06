package ch.bfh.ti.gravis.core.step;

import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * Performs a DO or UNDO operation at the graph item method
 * {@link IGraphItem#setCurrentVisible(boolean)}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class VisibleCommand extends EmptyStep {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "VisibleCommand.%s(): %s == %s";

	private final IGraphItem item;

	private final boolean oldVisible, newVisible;

	/**
	 * 
	 * @param currentItem
	 * @param oldVisible
	 * @param newVisible
	 * @throws NullPointerException
	 *             if currentItem is null
	 */
	protected VisibleCommand(IGraphItem currentItem, boolean oldVisible,
			boolean newVisible) {

		this.item = Objects
				.requireNonNull(currentItem, String.format(NULL_POINTER_MSG,
						"VisibleCommand", "currentItem", currentItem));
		this.oldVisible = oldVisible;
		this.newVisible = newVisible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#execute()
	 */
	@Override
	public IStepResult execute() {
		this.item.setCurrentVisible(this.newVisible);
		return new StepResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#unExecute()
	 */
	@Override
	public IStepResult unExecute() {
		this.item.setCurrentVisible(this.oldVisible);
		return new StepResult();
	}
}
