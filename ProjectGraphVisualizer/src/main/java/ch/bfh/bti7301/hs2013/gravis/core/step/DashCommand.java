package ch.bfh.bti7301.hs2013.gravis.core.step;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class DashCommand extends EmptyStep {

	private final IGraphItem item;

	private final boolean oldDashed, newDashed;
	
	/**
	 * 
	 * @param currentItem
	 * @param oldDashed
	 * @param newDashed
	 * @param comment
	 */
	public DashCommand(IGraphItem currentItem, boolean oldDashed, 
			boolean newDashed, String comment) {
		super(comment);
		
		this.item = currentItem;
		this.oldDashed = oldDashed;
		this.newDashed = newDashed;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.EmptyStep#execute()
	 */
	@Override
	public IStepResult execute() {
		this.item.setCurrentDashed(this.newDashed);
		return new StepResult(this.comment);
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.EmptyStep#unExecute()
	 */
	@Override
	public IStepResult unExecute() {
		this.item.setCurrentDashed(this.oldDashed);
		return new StepResult(this.comment);
	}
	
}
