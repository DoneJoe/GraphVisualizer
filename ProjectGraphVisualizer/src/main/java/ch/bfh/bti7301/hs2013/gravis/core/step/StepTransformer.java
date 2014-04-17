package ch.bfh.bti7301.hs2013.gravis.core.step;


import org.apache.commons.collections15.Transformer;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;

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
		Step complexStep = new Step();
		
		IStep command = new StateCommand(currentItem, currentItem.getCurrentState(), 
				currentItem.getNewState(), currentItem.getNewComment());		
		command.execute();
		complexStep.add(command);
		
		command = new ResultCommand(currentItem, currentItem.getCurrentResult(), 
				currentItem.getNewResult(), currentItem.getNewComment());		
		command.execute();
		complexStep.add(command);
		
		command = new VisibleCommand(currentItem, currentItem.isCurrentVisible(), 
				currentItem.isNewVisible(), currentItem.getNewComment());		
		command.execute();
		complexStep.add(command);
		
		command = new TaggedCommand(currentItem, currentItem.isCurrentTagged(), 
				currentItem.isNewTagged(), currentItem.getNewComment());		
		command.execute();
		complexStep.add(command);
		
		command = new DashCommand(currentItem, currentItem.isCurrentDashed(), 
				currentItem.isNewDashed(), currentItem.getNewComment());		
		command.execute();
		complexStep.add(command);
		
		currentItem.resetNewValues();
		return complexStep;
	}

}
