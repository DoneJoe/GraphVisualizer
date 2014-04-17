package ch.bfh.bti7301.hs2013.gravis.core.step;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.ItemState;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class StateCommand extends EmptyStep {

	private final IGraphItem item;

	private final ItemState newState, oldState;
	
	/**
	 * 
	 * @param currentItem
	 * @param oldState
	 * @param newState
	 * @param comment
	 */
	public StateCommand(IGraphItem currentItem, ItemState oldState, ItemState newState,
			String comment) {
		super(comment);
		
		this.item = currentItem;
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public IStepResult execute() {
		this.item.setCurrentState(this.newState);
		return new StepResult(this.comment);
	}

	@Override
	public IStepResult unExecute() {
		this.item.setCurrentState(this.oldState);
		return new StepResult(this.comment);
	}

	
}
