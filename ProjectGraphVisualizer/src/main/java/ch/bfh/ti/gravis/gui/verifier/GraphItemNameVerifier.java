package ch.bfh.ti.gravis.gui.verifier;

import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.Graph;

/**
 * A graph item name verifier.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GraphItemNameVerifier extends AbstractVerifier {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "GraphItemNameVerifier.%s(): %s == %s";
	
	private static final int MAX_LENGTH = 20;

	private final String currentName;

	private final Graph<IVertex, IEdge> graph;

	/**
	 * 
	 * @param currentName
	 * @param graph
	 */
	public GraphItemNameVerifier(String currentName,
			Graph<IVertex, IEdge> graph) {

		super(currentName);

		this.currentName = currentName == null ? "" : currentName;
		this.graph = Objects.requireNonNull(graph, String.format(
				NULL_POINTER_MSG, "GraphItemNameVerifier", "graph",
				graph));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
	 */
	@Override
	public boolean verify(final JComponent input) {
		if (input instanceof JTextComponent
				&& this.graph instanceof IGravisGraph) {

			IGravisGraph gravisGraph = (IGravisGraph) this.graph;
			String newName = ((JTextComponent) input).getText().trim();

			return !newName.isEmpty() && newName.length() <= MAX_LENGTH
					&& (newName.equals(this.currentName) || !gravisGraph
							.containsItemName(newName));
		}

		return false;
	}

}