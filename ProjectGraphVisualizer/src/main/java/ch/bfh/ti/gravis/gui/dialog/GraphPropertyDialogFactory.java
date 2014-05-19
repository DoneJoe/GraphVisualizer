package ch.bfh.ti.gravis.gui.dialog;

import java.util.Objects;

import javax.swing.JDialog;
import javax.swing.JFrame;

import ch.bfh.ti.gravis.core.graph.IGravisGraph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class GraphPropertyDialogFactory {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "GraphPropertyDialogFactory.%s(): %s == %s";
	
	private JFrame owner;
	
	/**
	 * @param owner
	 */
	public GraphPropertyDialogFactory(JFrame owner) {
		this.owner =  Objects.requireNonNull(owner, String.format(
				NULL_POINTER_MSG, "GraphPropertyDialogFactory", "owner",
				owner));
	}

	/**
	 * 
	 * @param graph
	 * @return JDialog
	 */
	public JDialog createGraphPropertyDialog(IGravisGraph graph) {
		return new GraphPropertyDialog(graph, this.owner);
	}

}
