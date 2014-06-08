package ch.bfh.ti.gravis.core.step;

import java.util.Objects;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * A step transformer transforms a single graph item into a step.
 * 
 * @see IStep
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class StepTransformer implements Transformer<IGraphItem, IStep> {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "StepTransformer.%s(): %s == %s";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public IStep transform(final IGraphItem currentItem) {
		Objects.requireNonNull(currentItem, String.format(NULL_POINTER_MSG,
				"transform", "currentItem", currentItem));
		ComplexStep complexStep = new ComplexStep(currentItem.getNewComment());

		// add state, result, visible, tagged and dashed command to the step

		IStep command = new StateCommand(currentItem,
				currentItem.getCurrentState(), currentItem.getNewState());
		command.execute();
		complexStep.add(command);

		command = new ResultCommand(currentItem,
				currentItem.getCurrentResult(), currentItem.getNewResult());
		command.execute();
		complexStep.add(command);

		command = new VisibleCommand(currentItem,
				currentItem.isCurrentVisible(), currentItem.isNewVisible());
		command.execute();
		complexStep.add(command);

		command = new TaggedCommand(currentItem, currentItem.isCurrentTagged(),
				currentItem.isNewTagged());
		command.execute();
		complexStep.add(command);

		command = new DashedCommand(currentItem, currentItem.isCurrentDashed(),
				currentItem.isNewDashed());
		command.execute();
		complexStep.add(command);

		currentItem.resetNewVariables();
		return complexStep;
	}

}
