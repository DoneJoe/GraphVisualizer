package ch.bfh.bti7301.hs2013.gravis.gui.visualization;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.GuiFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
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

	private VertexMenu vertexMenu;

	private VertexCreateMenu vertexCreateMenu;

	private EdgeMenu edgeMenu;

	private JComboBox<?> comboBoxMode;

	/**
	 * 
	 * @param model
	 * @param owner
	 */
	public VisualizationPanel(IGuiModel model, JFrame owner) {
		super();

		// TODO diese Klasse muss editing events auslösen können
		// TODO Scroll-Pane anpassen 

		this.viewer = new GravisVisualizationViewer(
				GuiFactory.createLayout(model.getGraph()));
		this.vertexMenu = new VertexMenu(this.viewer, owner, model);
		this.vertexCreateMenu = new VertexCreateMenu(this.viewer, model);
		this.edgeMenu = new EdgeMenu(this.viewer, owner, model);
		GraphZoomScrollPane pane = new GraphZoomScrollPane(this.viewer);
		EditingModalGraphMouse<IVertex, IEdge> graphMouse = new GravisModalGraphMouse(
				this.viewer.getRenderContext(), new VertexFactory(),
				new EdgeFactory(), this.edgeMenu, this.vertexMenu,
				this.vertexCreateMenu);
		this.comboBoxMode = graphMouse.getModeComboBox();

		this.setBorder(BorderFactory.createTitledBorder(model.getGraph()
				.getName()));
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		this.viewer.setGraphMouse(graphMouse);
		this.viewer.addKeyListener(graphMouse.getModeKeyListener());
		graphMouse.setMode(Mode.PICKING);
		model.setEditMode(Mode.PICKING);
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

	/**
	 * 
	 * @return JComboBox<?>
	 */
	public JComboBox<?> getModeComboBox() {
		return this.comboBoxMode;
	}

}
