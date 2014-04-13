package ch.bfh.bti7301.hs2013.gravis.gui.visualization;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.GuiFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.popup.EdgeMenu;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.popup.VertexCreateMenu;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.popup.VertexMenu;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

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
	 * 
	 * @param model
	 * @param owner
	 */
	public VisualizationPanel(IAppModel model, JFrame owner) {
		// create components
		this.viewer = new GravisVisualizationViewer(
				GuiFactory.createLayout(model.getGraph()));
		VertexMenu vertexMenu = new VertexMenu(this.viewer, owner, model);
		VertexCreateMenu vertexCreateMenu = new VertexCreateMenu(this.viewer,
				model);
		EdgeMenu edgeMenu = new EdgeMenu(this.viewer, owner, model);
		GraphZoomScrollPane pane = new GraphZoomScrollPane(this.viewer);
		EditingModalGraphMouse<IVertex, IEdge> graphMouse = new GravisModalGraphMouse(
				this.viewer.getRenderContext(), new VertexFactory(),
				new EdgeFactory(), edgeMenu, vertexMenu, vertexCreateMenu);

		this.setBorder(BorderFactory.createTitledBorder(model.getGraph()
				.getName()));
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		this.viewer.setGraphMouse(graphMouse);
		this.viewer.addKeyListener(graphMouse.getModeKeyListener());
		graphMouse.setMode(Mode.PICKING);
		model.setPopupEditMode(Mode.PICKING);
		model.setEditModeComboModel(graphMouse.getModeComboBox().getModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof IGravisGraph) {
			this.setBorder(BorderFactory
					.createTitledBorder(((IGravisGraph) arg).getName()));
		}
		this.viewer.update(o, arg);
	}

}
