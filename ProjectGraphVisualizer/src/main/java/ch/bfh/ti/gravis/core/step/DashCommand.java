package ch.bfh.ti.gravis.core.step;

import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class DashCommand extends EmptyStep {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "DashCommand.%s(): %s == %s";
	
	private final IGraphItem item;

	private final boolean oldDashed, newDashed;
	
	/**
	 * 
	 * @param currentItem
	 * @param oldDashed
	 * @param newDashed
	 */
	protected DashCommand(IGraphItem currentItem, boolean oldDashed, 
			boolean newDashed) {
		
		this.item = Objects.requireNonNull(currentItem, String.format(
				NULL_POINTER_MSG, "DashCommand", "currentItem",
				currentItem));
		this.oldDashed = oldDashed;
		this.newDashed = newDashed;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#execute()
	 */
	@Override
	public IStepResult execute() {
		this.item.setCurrentDashed(this.newDashed);
		return new StepResult();
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.step.EmptyStep#unExecute()
	 */
	@Override
	public IStepResult unExecute() {
		this.item.setCurrentDashed(this.oldDashed);
		return new StepResult();
	}
	
}
