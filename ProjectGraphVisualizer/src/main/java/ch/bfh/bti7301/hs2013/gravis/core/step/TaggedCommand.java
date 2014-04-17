package ch.bfh.bti7301.hs2013.gravis.core.step;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class TaggedCommand extends EmptyStep {

	private final IGraphItem item;

	private final boolean oldTagged, newTagged;
	
	/**
	 * 
	 * @param currentItem
	 * @param oldTagged
	 * @param newTagged
	 * @param comment
	 */
	public TaggedCommand(IGraphItem currentItem, boolean oldTagged, 
			boolean newTagged, String comment) {
		super(comment);
		
		this.item = currentItem;
		this.oldTagged = oldTagged;
		this.newTagged = newTagged;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.EmptyStep#execute()
	 */
	@Override
	public IStepResult execute() {
		this.item.setCurrentTagged(this.newTagged);
		return new StepResult(this.comment);
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.EmptyStep#unExecute()
	 */
	@Override
	public IStepResult unExecute() {
		this.item.setCurrentTagged(this.oldTagged);
		return new StepResult(this.comment);
	}
}
