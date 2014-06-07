package ch.bfh.ti.gravis.core.graph.item;

import java.awt.Color;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.util.GravisConstants;

import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;

/**
 * This enum class defines different states for vertices and edges.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public enum ItemState {

	// state definition:
	
	INITIAL, ACTIVATED, VISITED, SOLVED, DISCARDED;

	// default state messages:
	
	private final static String V_INITIAL_MSG = "Der Knoten %s befindet sich im Anfangszustand.%s";
	private final static String E_INITIAL_MSG = "Die Kante %s befindet sich im Anfangszustand.%s";
	private final static String V_ACTIVATED_MSG = "Der Knoten %s wurde ausgewählt.%s";
	private final static String E_ACTIVATED_MSG = "Die Kante %s wurde ausgewählt.%s";
	private final static String V_VISITED_MSG = "Der Knoten %s wurde besucht.%s";
	private final static String E_VISITED_MSG = "Die Kante %s wurde besucht.%s";
	private final static String V_SOLVED_MSG = "Der Knoten %s wurde zur Lösung hinzugefügt.%s";
	private final static String E_SOLVED_MSG = "Die Kante %s wurde zur Lösung hinzugefügt.%s";
	private final static String V_DISCARDED_MSG = "Der Knoten %s wurde aus der Lösung ausgeschlossen.%s";
	private final static String E_DISCARDED_MSG = "Die Kante %s wurde aus der Lösung ausgeschlossen.%s";
	
	/**
	 * 
	 * @param item
	 * @return default state message with new line at the end
	 */
	public String getMessage(final IGraphItem item) {
		if (item instanceof IVertex) {
			switch (this) {
			case INITIAL:
				return String.format(V_INITIAL_MSG, item.getName(), LN);
			case ACTIVATED:
				return String.format(V_ACTIVATED_MSG, item.getName(), LN);
			case VISITED:
				return String.format(V_VISITED_MSG, item.getName(), LN);
			case SOLVED:
				return String.format(V_SOLVED_MSG, item.getName(), LN);
			case DISCARDED:
				return String.format(V_DISCARDED_MSG, item.getName(), LN);
			}
		} else if (item instanceof IEdge) {
			switch (this) {
			case INITIAL:
				return String.format(E_INITIAL_MSG, item.getName(), LN);
			case ACTIVATED:
				return String.format(E_ACTIVATED_MSG, item.getName(), LN);
			case VISITED:
				return String.format(E_VISITED_MSG, item.getName(), LN);
			case SOLVED:
				return String.format(E_SOLVED_MSG, item.getName(), LN);
			case DISCARDED:
				return String.format(E_DISCARDED_MSG, item.getName(), LN);
			}
		}

		return "";
	}

	/**
	 * 
	 * @param item
	 * @return draw color
	 */
	public Color getDrawColor(final IGraphItem item) {
		if (item instanceof IVertex) {
			switch (this) {
			case INITIAL:
				return GravisConstants.V_DRAW_INITIAL_COLOR;
			case ACTIVATED:
				return GravisConstants.V_DRAW_ACTIVATION_COLOR;
			case VISITED:
				return GravisConstants.V_DRAW_VISIT_COLOR;
			case SOLVED:
				return GravisConstants.V_DRAW_SOLUTION_COLOR;
			case DISCARDED:
				return GravisConstants.V_DRAW_ELIMINATION_COLOR;
			}
		} else if (item instanceof IEdge) {
			return this.getEdgeColor();
		}

		return GravisConstants.V_DRAW_COLOR_DEFAULT;
	}

	/**
	 * 
	 * @param item
	 * @return fill Color
	 */
	public Color getFillColor(final IGraphItem item) {
		if (item instanceof IVertex) {
			switch (this) {
			case INITIAL:
				return GravisConstants.V_FILL_INITIAL_COLOR;
			case ACTIVATED:
				return GravisConstants.V_FILL_ACTIVATION_COLOR;
			case VISITED:
				return GravisConstants.V_FILL_VISIT_COLOR;
			case SOLVED:
				return GravisConstants.V_FILL_SOLUTION_COLOR;
			case DISCARDED:
				return GravisConstants.V_FILL_ELIMINATION_COLOR;
			}
		} else if (item instanceof IEdge) {
			return this.getEdgeColor();
		}

		return GravisConstants.V_FILL_COLOR_DEFAULT;
	}

	/**
	 * 
	 * @return edge color
	 */
	private Color getEdgeColor() {
		switch (this) {
		case INITIAL:
			return GravisConstants.E_INITIAL_COLOR;
		case ACTIVATED:
			return GravisConstants.E_ACTIVATION_COLOR;
		case VISITED:
			return GravisConstants.E_VISIT_COLOR;
		case SOLVED:
			return GravisConstants.E_SOLUTION_COLOR;
		case DISCARDED:
			return GravisConstants.E_ELIMINATION_COLOR;
		default:
			return GravisConstants.E_COLOR_DEFAULT;
		}
	}

}
