package ch.bfh.ti.gravis.core.step;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class TaggedCommand extends EmptyStep {

	private final IGraphItem item;

	private final boolean oldTagged, newTagged;
	
	/**
	 * 
	 * @param currentItem
	 * @param oldTagged
	 * @param newTagged
	 */
	protected TaggedCommand(IGraphItem currentItem, boolean oldTagged, 
			boolean newTagged) {
		this.item = currentItem;
		this.oldTagged = oldTagged;
		this.newTagged = newTagged;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#execute()
	 */
	@Override
	public IStepResult execute() {
		this.item.setCurrentTagged(this.newTagged);
		return new StepResult();
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#unExecute()
	 */
	@Override
	public IStepResult unExecute() {
		this.item.setCurrentTagged(this.oldTagged);
		return new StepResult();
	}
}
