package ch.bfh.bti7301.hs2013.gravis.core.step;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class VisibleCommand extends EmptyStep {

	private final IGraphItem item;

	private final boolean oldVisible, newVisible;
	
	/**
	 * 
	 * @param currentItem
	 * @param oldVisible
	 * @param newVisible
	 * @param comment
	 */
	public VisibleCommand(IGraphItem currentItem, boolean oldVisible, 
			boolean newVisible, String comment) {
		super(comment);
		
		this.item = currentItem;
		this.oldVisible = oldVisible;
		this.newVisible = newVisible;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.EmptyStep#execute()
	 */
	@Override
	public IStepResult execute() {
		this.item.setCurrentVisible(this.newVisible);
		return new StepResult(this.comment);
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.EmptyStep#unExecute()
	 */
	@Override
	public IStepResult unExecute() {
		this.item.setCurrentVisible(this.oldVisible);
		return new StepResult(this.comment);
	}
}
