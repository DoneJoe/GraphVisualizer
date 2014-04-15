package ch.bfh.bti7301.hs2013.gravis.gui.visualization.popup;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
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

		DeleteEdgeMenuItem deleteEdgeMenuItem = new DeleteEdgeMenuItem(vViewer);
		model.setDeleteEdgeButtonModel(deleteEdgeMenuItem.getModel());

		this.add(new EdgePropertyMenuItem(vViewer, owner));
		this.addSeparator();
		this.add(deleteEdgeMenuItem);
	}

}
