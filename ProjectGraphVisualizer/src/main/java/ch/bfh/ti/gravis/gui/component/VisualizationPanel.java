package ch.bfh.ti.gravis.gui.component;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import ch.bfh.ti.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.ti.gravis.gui.GuiFactory;
import ch.bfh.ti.gravis.gui.controller.ErrorHandler;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import ch.bfh.ti.gravis.gui.model.VisualizationViewModel;
import ch.bfh.ti.gravis.gui.popup.EdgeMenu;
import ch.bfh.ti.gravis.gui.popup.CreateVertexMenu;
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
	private final GravisVisualizationViewer viewer;

	private final TitledBorder border;

	/**
	 * 
	 * @param mainWindow
	 * @param model
	 */
	@SuppressWarnings("unchecked")
	public VisualizationPanel(final JFrame mainWindow, final IAppModel model) {
		// create components:

		this.border = BorderFactory.createTitledBorder(model.getGraph()
				.getName());
		this.viewer = new GravisVisualizationViewer(
				GuiFactory.createLayout(model.getGraph()));
		VertexMenu vertexMenu = new VertexMenu(this.viewer, mainWindow, model);
		CreateVertexMenu vertexCreateMenu = new CreateVertexMenu(this.viewer,
				model);
		EdgeMenu edgeMenu = new EdgeMenu(this.viewer, mainWindow, model);
		GraphZoomScrollPane pane = new GraphZoomScrollPane(this.viewer);
		EditingModalGraphMouse<IVertex, IEdge> graphMouse = new GravisModalGraphMouse(
				this.viewer.getRenderContext(), new VertexFactory(),
				new EdgeFactory(), edgeMenu, vertexMenu, vertexCreateMenu,
				new ErrorHandler(this));

		// prepare VisualizationPanel:

		model.setPickedVertexState(this.viewer.getPickedVertexState());
		graphMouse.getModeComboBox().setModel(
				model.getToggleComboGroup().getModeComboBox().getModel());
		graphMouse.setMode(model.getToggleComboGroup().getMode());
		this.viewer.setGraphMouse(graphMouse);
		this.viewer.addKeyListener(graphMouse.getModeKeyListener());
		this.setBorder(this.border);
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		this.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof VisualizationViewModel) {
			this.border.setTitle(((VisualizationViewModel) arg).getGraph()
					.getName());
			this.setBorder(this.border);
			this.repaint();
		}

		this.viewer.update(o, arg);
	}

}
