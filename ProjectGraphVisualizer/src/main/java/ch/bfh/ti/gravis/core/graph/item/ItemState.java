package ch.bfh.ti.gravis.core.graph.item;

import java.awt.Color;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.util.GravisConstants;

import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public enum ItemState {

	// state definition
	INITIAL, ACTIVATION, VISIT, SOLUTION, ELIMINATION;

	// DO default state messages
	private final static String V_INITIAL_MSG = "Der Knoten %s wurde in den Anfangszustand versetzt.%s";
	private final static String E_INITIAL_MSG = "Die Kante %s wurde in den Anfangszustand versetzt.%s";
	private final static String V_ACTIVATION_MSG = "Der Knoten %s wurde ausgewählt.%s";
	private final static String E_ACTIVATION_MSG = "Die Kante %s wurde ausgewählt.%s";
	private final static String V_VISIT_MSG = "Der Knoten %s wurde besucht.%s";
	private final static String E_VISIT_MSG = "Die Kante %s wurde besucht.%s";
	private final static String V_SOLUTION_MSG = "Der Knoten %s wurde zur Lösung hinzugefügt.%s";
	private final static String E_SOLUTION_MSG = "Die Kante %s wurde zur Lösung hinzugefügt.%s";
	private final static String V_ELIMINATION_MSG = "Der Knoten %s wurde aus der Lösung ausgeschlossen.%s";
	private final static String E_ELIMINATION_MSG = "Die Kante %s wurde aus der Lösung ausgeschlossen.%s";
	// TODO ev. Kommentare anpassen

	// UNDO default state messages
	private final static String V_UNDO_MSG = "Einen Schritt zurück für den Knoten %s.%s";
	private final static String E_UNDO_MSG = "Einen Schritt zurück für die Kante %s.%s";
	
	/**
	 * 
	 * @param item
	 * @return default state do message with new line at the end
	 */
	public String getDoMessage(final IGraphItem item) {
		if (item instanceof IVertex) {
			switch (this) {
			case INITIAL:
				return String.format(V_INITIAL_MSG, item.getName(), LN);
			case ACTIVATION:
				return String.format(V_ACTIVATION_MSG, item.getName(), LN);
			case VISIT:
				return String.format(V_VISIT_MSG, item.getName(), LN);
			case SOLUTION:
				return String.format(V_SOLUTION_MSG, item.getName(), LN);
			case ELIMINATION:
				return String.format(V_ELIMINATION_MSG, item.getName(), LN);
			}
		} else if (item instanceof IEdge) {
			switch (this) {
			case INITIAL:
				return String.format(E_INITIAL_MSG, item.getName(), LN);
			case ACTIVATION:
				return String.format(E_ACTIVATION_MSG, item.getName(), LN);
			case VISIT:
				return String.format(E_VISIT_MSG, item.getName(), LN);
			case SOLUTION:
				return String.format(E_SOLUTION_MSG, item.getName(), LN);
			case ELIMINATION:
				return String.format(E_ELIMINATION_MSG, item.getName(), LN);
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
			case ACTIVATION:
				return GravisConstants.V_DRAW_ACTIVATION_COLOR;
			case VISIT:
				return GravisConstants.V_DRAW_VISIT_COLOR;
			case SOLUTION:
				return GravisConstants.V_DRAW_SOLUTION_COLOR;
			case ELIMINATION:
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
			case ACTIVATION:
				return GravisConstants.V_FILL_ACTIVATION_COLOR;
			case VISIT:
				return GravisConstants.V_FILL_VISIT_COLOR;
			case SOLUTION:
				return GravisConstants.V_FILL_SOLUTION_COLOR;
			case ELIMINATION:
				return GravisConstants.V_FILL_ELIMINATION_COLOR;
			}
		} else if (item instanceof IEdge) {
			return this.getEdgeColor();
		}

		return GravisConstants.V_FILL_COLOR_DEFAULT;
	}

	/**
	 * 
	 * @param item
	 * @return default state undo message with new line at the end
	 */
	public String getUndoMessage(final IGraphItem item) {
		if (item instanceof IVertex) {
			return String.format(V_UNDO_MSG, item.getName(), LN);
		} else if (item instanceof IEdge) {
			return String.format(E_UNDO_MSG, item.getName(), LN);
		}

		return "";
	}

	/**
	 * 
	 * @return edge color
	 */
	private Color getEdgeColor() {
		switch (this) {
		case INITIAL:
			return GravisConstants.E_INITIAL_COLOR;
		case ACTIVATION:
			return GravisConstants.E_ACTIVATION_COLOR;
		case VISIT:
			return GravisConstants.E_VISIT_COLOR;
		case SOLUTION:
			return GravisConstants.E_SOLUTION_COLOR;
		case ELIMINATION:
			return GravisConstants.E_ELIMINATION_COLOR;
		default:
			return GravisConstants.E_COLOR_DEFAULT;
		}
	}

}
