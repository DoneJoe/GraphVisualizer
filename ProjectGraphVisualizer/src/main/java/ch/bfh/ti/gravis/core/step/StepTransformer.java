package ch.bfh.ti.gravis.core.step;


import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class StepTransformer implements Transformer<IGraphItem, IStep> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public IStep transform(IGraphItem currentItem) {		
		Step complexStep = new Step(currentItem.getNewComment());
		
		IStep command = new StateCommand(currentItem, currentItem.getCurrentState(), 
				currentItem.getNewState());		
		command.execute();
		complexStep.add(command);
		
		command = new ResultCommand(currentItem, currentItem.getCurrentResult(), 
				currentItem.getNewResult());		
		command.execute();
		complexStep.add(command);
		
		command = new VisibleCommand(currentItem, currentItem.isCurrentVisible(), 
				currentItem.isNewVisible());		
		command.execute();
		complexStep.add(command);
		
		command = new TaggedCommand(currentItem, currentItem.isCurrentTagged(), 
				currentItem.isNewTagged());		
		command.execute();
		complexStep.add(command);
		
		command = new DashCommand(currentItem, currentItem.isCurrentDashed(), 
				currentItem.isNewDashed());		
		command.execute();
		complexStep.add(command);
		
		currentItem.resetNewVariables();
		return complexStep;
	}

}