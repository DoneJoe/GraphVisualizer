package ch.bfh.bti7301.hs2013.gravis.gui.dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class GraphPropertyDialogFactory {

	private JFrame owner;
	
	/**
	 * @param owner
	 */
	public GraphPropertyDialogFactory(JFrame owner) {
		this.owner = owner;
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
