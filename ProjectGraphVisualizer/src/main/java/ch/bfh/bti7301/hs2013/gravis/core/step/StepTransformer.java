package ch.bfh.bti7301.hs2013.gravis.core.step;

import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.map.HashedMap;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class StepTransformer implements Transformer<IGraphItem, IStep> {

	private final static String V_SUFFIX = "_VERTEX";

	private final static String E_SUFFIX = "_EDGE";

	private final Map<String, IVisualizationState> states;

	protected StepTransformer() {
		this.states = new HashedMap<>();

		this.states.put(State.INITIAL.toString() + V_SUFFIX,
				new InitialVertexState());
		this.states.put(State.INITIAL.toString() + E_SUFFIX,
				new InitialEdgeState());
		this.states.put(State.ACTIVATION.toString(), new ActivationState());
		this.states.put(State.VISIT.toString(), new VisitState());
		this.states.put(State.SOLUTION.toString(), new SolutionState());
		this.states.put(State.REFUSE.toString(), new RefuseState());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public IStep transform(IGraphItem currentItem) {
		IVisualizationState nextState = this.states.get(this
				.getKeyString(currentItem));
		IStep nextCommand = nextState.createCommand(currentItem);
		return nextCommand;
	}

	/**
	 * @param currentItem
	 * @return String
	 */
	private String getKeyString(IGraphItem currentItem) {
		// if newState is null, use state from last step
		State newState = currentItem.getNewState() == null ? currentItem
				.getCurrentState() : currentItem.getNewState();

		if (newState == State.INITIAL) {
			if (currentItem instanceof IVertex) {
				return newState.toString() + V_SUFFIX;
			} else {
				return newState.toString() + E_SUFFIX;
			}
		}

		return newState.toString();
	}

}