package ch.bfh.ti.gravis.gui.component;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.ti.gravis.gui.GuiFactory;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import ch.bfh.ti.gravis.gui.popup.EdgeMenu;
import ch.bfh.ti.gravis.gui.popup.VertexCreateMenu;
import ch.bfh.ti.gravis.gui.popup.VertexMenu;
import ch.bfh.ti.gravis.gui.visualization.GravisModalGraphMouse;
import ch.bfh.ti.gravis.gui.visualization.GravisVisualizationViewer;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;

/**
 * A visualization panel.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VisualizationPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 177109739873034494L;

	/**
	 * A field for a visualization viewer.
	 */
	private GravisVisualizationViewer viewer;

	/**
	 * mode combo box listener is registered
	 * 
	 * @param mainWindow
	 * @param model
	 */
	@SuppressWarnings("unchecked")
	public VisualizationPanel(final JFrame mainWindow, final IAppModel model) {
		
		// create components:
		
		this.viewer = new GravisVisualizationViewer(
				GuiFactory.createLayout(model.getGraph()));
		VertexMenu vertexMenu = new VertexMenu(this.viewer, mainWindow, model);
		VertexCreateMenu vertexCreateMenu = new VertexCreateMenu(this.viewer,
				model);
		EdgeMenu edgeMenu = new EdgeMenu(this.viewer, mainWindow, model);
		GraphZoomScrollPane pane = new GraphZoomScrollPane(this.viewer);
		EditingModalGraphMouse<IVertex, IEdge> graphMouse = new GravisModalGraphMouse(
				this.viewer.getRenderContext(), new VertexFactory(),
				new EdgeFactory(), edgeMenu, vertexMenu, vertexCreateMenu);

		// prepare VisualizationPanel:
		
		graphMouse.getModeComboBox().setModel(model.getToggleComboModel().getModeModel());
		graphMouse.setMode(model.getToggleComboModel().getDefaultMode());
		this.viewer.setGraphMouse(graphMouse);
		this.viewer.addKeyListener(graphMouse.getModeKeyListener());
		this.setBorder(BorderFactory.createTitledBorder(model.getGraph()
				.getName()));
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		// TODO VisualizationModel verwenden
		
		if (arg instanceof IGravisGraph) {
			this.setBorder(BorderFactory
					.createTitledBorder(((IGravisGraph) arg).getName()));
		}
		this.viewer.update(o, arg);
	}

}
