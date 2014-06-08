package ch.bfh.ti.gravis.gui.popup;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * An edge popup menu.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class EdgeMenu extends JPopupMenu {

	private static final long serialVersionUID = 2640685878709501654L;

	private final static String TITLE = "Kanten";

	/**
	 * 
	 * @param vViewer
	 * @param owner
	 * @param model
	 */
	public EdgeMenu(final VisualizationViewer<IVertex, IEdge> vViewer,
			final JFrame owner, final IAppModel model) {
		super(TITLE);

		EdgePropertyMenuItem edgePropertyMenuItem =new EdgePropertyMenuItem(vViewer, owner);
		DeleteEdgeMenuItem deleteEdgeMenuItem = new DeleteEdgeMenuItem(vViewer);
				
		edgePropertyMenuItem.setModel(model.getEdgePropertiesButtonModel());
		deleteEdgeMenuItem.setModel(model.getDeleteEdgeButtonModel());

		this.add(edgePropertyMenuItem);
		this.addSeparator();
		this.add(deleteEdgeMenuItem);
	}

}
