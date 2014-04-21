package ch.bfh.ti.gravis.gui.popup;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VertexMenu extends JPopupMenu {

	private static final long serialVersionUID = 3273304014704565148L;

	private final static String TITLE = "Knoten";

	/**
	 * 
	 * @param vViewer
	 * @param owner
	 * @param model
	 */
	public VertexMenu(final VisualizationViewer<IVertex, IEdge> vViewer,
			final JFrame owner, final IAppModel model) {
		super(TITLE);

		DeleteVertexMenuItem deleteVertexMenuItem = new DeleteVertexMenuItem(
				vViewer);
		model.setDeleteVertexButtonModel(deleteVertexMenuItem.getModel());

		this.add(new StartVertexCheckBox(vViewer));
		this.add(new EndVertexCheckBox(vViewer));
		this.addSeparator();
		this.add(new VertexPropertyMenuItem(vViewer, owner));
		this.addSeparator();
		this.add(deleteVertexMenuItem);
	}
}