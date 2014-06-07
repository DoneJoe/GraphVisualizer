package ch.bfh.ti.gravis.core.step;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.util.GravisListIterator;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import ch.bfh.ti.gravis.core.util.StepIterator;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class StepBuilder {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "StepBuilder.%s(): %s == %s";

	/**
	 * @param restrictedGraph
	 * @return IStepRecorder
	 * @throws NullPointerException
	 *             if restrictedGraph is null
	 */
	public static IStepRecorder createStepRecorder(
			IRestrictedGraph restrictedGraph) {

		return new StepRecorder(Objects.requireNonNull(restrictedGraph, String
				.format(NULL_POINTER_MSG, "createStepRecorder",
						"restrictedGraph", restrictedGraph)));
	}

	private final List<IStep> stepList;

	private final Transformer<IGraphItem, IStep> stepTransformer;

	public StepBuilder() {
		this.stepList = new ArrayList<>();
		this.stepTransformer = new StepTransformer();
	}

	/**
	 * @param currentItems
	 */
	public void addStep(IGraphItem... currentItems) {
		Objects.requireNonNull(currentItems, String.format(NULL_POINTER_MSG,
				"addStep", "currentItems", currentItems));
		List<IGraphItem> tempList = new ArrayList<>();
		Set<IGraphItem> lookup = new HashSet<>();
		Step step = new Step();

		// eliminates duplicates and preserves order
		for (IGraphItem item : currentItems) {
			if (item != null && lookup.add(item)) {
				// lookup.add() returns false if item is already in the set
				tempList.add(item);
			}
		}

		for (IGraphItem item : tempList) {
			IStep command = this.stepTransformer.transform(item);
			step.add(command);
		}

		this.stepList.add(step);
	}

	/**
	 * @return step iterator
	 */
	public IGravisListIterator<String> createStepIterator() {
		// undo for all commands in the list in reverse order
		for (int i = this.stepList.size() - 1; i >= 0; i--) {
			this.stepList.get(i).unExecute();
		}

		return new StepIterator(new GravisListIterator<IStep>(this.stepList));
	}
}
