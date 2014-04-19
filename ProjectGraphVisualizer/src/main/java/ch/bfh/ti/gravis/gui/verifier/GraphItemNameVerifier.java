package ch.bfh.ti.gravis.gui.verifier;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.Graph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GraphItemNameVerifier extends AbstractGravisVerifier {

	private final IGraphItem graphItem;

	private final Graph<IVertex, IEdge> graph;

	/**
	 * 
	 * @param lastGood
	 * @param graphItem
	 * @param graph
	 */
	public GraphItemNameVerifier(String lastGood, IGraphItem graphItem,
			Graph<IVertex, IEdge> graph) {
		super(lastGood);
		this.graphItem = graphItem;
		this.graph = graph;
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
			JTextComponent textField = (JTextComponent) input;
			IGravisGraph gravisGraph = (IGravisGraph) this.graph;
			
			return !textField.getText().trim().isEmpty()
					&& (textField.getText()
							.equals(this.graphItem.getName()) || !gravisGraph
							.containsItemName(textField.getText().trim()));
		}

		return false;
	}

}