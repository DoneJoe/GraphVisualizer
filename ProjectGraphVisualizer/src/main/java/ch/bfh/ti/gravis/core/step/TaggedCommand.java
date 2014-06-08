package ch.bfh.ti.gravis.core.step;

import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * Performs a DO or UNDO operation at the graph item method
 * {@link IGraphItem#setCurrentTagged(boolean)}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class TaggedCommand extends EmptyStep {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "TaggedCommand.%s(): %s == %s";

	private final IGraphItem item;

	private final boolean oldTagged, newTagged;

	/**
	 * 
	 * @param currentItem
	 * @param oldTagged
	 * @param newTagged
	 * @throws NullPointerException
	 *             if currentItem is null
	 */
	protected TaggedCommand(IGraphItem currentItem, boolean oldTagged,
			boolean newTagged) {

		this.item = Objects.requireNonNull(currentItem, String.format(
				NULL_POINTER_MSG, "TaggedCommand", "currentItem", currentItem));
		this.oldTagged = oldTagged;
		this.newTagged = newTagged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#execute()
	 */
	@Override
	public IStepResult execute() {
		this.item.setCurrentTagged(this.newTagged);
		return new StepResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#unExecute()
	 */
	@Override
	public IStepResult unExecute() {
		this.item.setCurrentTagged(this.oldTagged);
		return new StepResult();
	}
}
