package ch.bfh.bti7301.hs2013.gravis.core.step;

import java.awt.Color;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class RefuseState extends AbstractVisualizationState {

	private final static String V_DO_MSG = "Der Knoten %s wurde aus der Lösung ausgeschlossen.";
	private final static String E_DO_MSG = "Die Kante %s wurde aus der Lösung ausgeschlossen.";
	private final static String V_UNDO_MSG = "Der Knoten %s wird nicht mehr aus der Lösung "
			+ "ausgeschlossen.";
	private final static String E_UNDO_MSG = "Die Kante %s wird nicht mehr aus der Lösung "
			+ "ausgeschlossen.";

	public RefuseState() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.step.IVisualizationState#getState()
	 */
	@Override
	public State getState() {
		return State.REFUSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.AbstractVisualizationState#
	 * getStateColor()
	 */
	@Override
	protected Color getStateColor() {
		return GravisConstants.REFUSE_COLOR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.AbstractVisualizationState#
	 * getStateDoMessage
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem)
	 */
	@Override
	protected String getStateDoMessage(IGraphItem currentItem) {
		if (currentItem instanceof IVertex) {
			return String.format(V_DO_MSG, currentItem.getName());
		}

		return String.format(E_DO_MSG, currentItem.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.step.AbstractVisualizationState#
	 * getStateUndoMessage
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem)
	 */
	@Override
	protected String getStateUndoMessage(IGraphItem currentItem) {
		if (currentItem instanceof IVertex) {
			return String.format(V_UNDO_MSG, currentItem.getName());
		}

		return String.format(E_UNDO_MSG, currentItem.getName());
	}

}
