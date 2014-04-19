package ch.bfh.ti.gravis.core.step;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class VisibleCommand extends EmptyStep {

	private final IGraphItem item;

	private final boolean oldVisible, newVisible;
	
	/**
	 * 
	 * @param currentItem
	 * @param oldVisible
	 * @param newVisible
	 */
	protected VisibleCommand(IGraphItem currentItem, boolean oldVisible, 
			boolean newVisible) {
		this.item = currentItem;
		this.oldVisible = oldVisible;
		this.newVisible = newVisible;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#execute()
	 */
	@Override
	public IStepResult execute() {
		this.item.setCurrentVisible(this.newVisible);
		return new StepResult();
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#unExecute()
	 */
	@Override
	public IStepResult unExecute() {
		this.item.setCurrentVisible(this.oldVisible);
		return new StepResult();
	}
}
