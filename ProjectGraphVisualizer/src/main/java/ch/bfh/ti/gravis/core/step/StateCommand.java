package ch.bfh.ti.gravis.core.step;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.ItemState;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class StateCommand extends EmptyStep {

	private final IGraphItem item;

	private final ItemState newState, oldState;
	
	/**
	 * 
	 * @param currentItem
	 * @param oldState
	 * @param newState
	 */
	protected StateCommand(IGraphItem currentItem, ItemState oldState, ItemState newState) {
		this.item = currentItem;
		this.oldState = oldState;
		this.newState = newState;
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
