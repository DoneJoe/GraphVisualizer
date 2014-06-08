package ch.bfh.ti.gravis.core.step;

import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.ItemState;

/**
 * Performs a DO or UNDO operation at the graph item method
 * {@link IGraphItem#setCurrentState(ItemState)}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class StateCommand extends EmptyStep {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "StateCommand.%s(): %s == %s";

	private final IGraphItem item;

	private final ItemState newState, oldState;

	/**
	 * 
	 * @param currentItem
	 * @param oldState
	 * @param newState
	 * @throws NullPointerException
	 *             if currentItem or oldState or newState is null
	 */
	protected StateCommand(IGraphItem currentItem, ItemState oldState,
			ItemState newState) {
		this.item = Objects.requireNonNull(currentItem, String.format(
				NULL_POINTER_MSG, "StateCommand", "currentItem", currentItem));
		this.oldState = Objects.requireNonNull(oldState, String.format(
				NULL_POINTER_MSG, "StateCommand", "oldState", oldState));
		this.newState = Objects.requireNonNull(newState, String.format(
				NULL_POINTER_MSG, "StateCommand", "newState", newState));
	}

	@Override
	public IStepResult execute() {
		this.item.setCurrentState(this.newState);
		return new StepResult();
	}

	@Override
	public IStepResult unExecute() {
		this.item.setCurrentState(this.oldState);
		return new StepResult();
	}

}
