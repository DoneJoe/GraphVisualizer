package ch.bfh.ti.gravis.gui.popup;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * A vertex popup menu.
 * 
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

		// create menu items:
		
		StartVertexCheckBox startVertexMenuItem = new StartVertexCheckBox(vViewer);
		EndVertexCheckBox endVertexMenuItem = new EndVertexCheckBox(vViewer);
		VertexPropertyMenuItem propertyMenuItem = new VertexPropertyMenuItem(vViewer, owner);		
		DeleteVertexMenuItem deleteVertexMenuItem = new DeleteVertexMenuItem(
				vViewer);
		
		// set models:
		
		startVertexMenuItem.setModel(model.getStartVertexButtonModel());
		endVertexMenuItem.setModel(model.getEndVertexButtonModel());
		propertyMenuItem.setModel(model.getVertexPropertiesButtonModel());
		deleteVertexMenuItem.setModel(model.getDeleteVertexButtonModel());

		// add menu items:
		
		this.add(startVertexMenuItem);
		this.add(endVertexMenuItem);
		this.addSeparator();
		this.add(propertyMenuItem);
		this.addSeparator();
		this.add(deleteVertexMenuItem);
	}
}
