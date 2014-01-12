package ch.bfh.bti7301.hs2013.gravis.gui.visualization.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.GravisVisualizationViewer;
import edu.uci.ics.jung.algorithms.layout.Layout;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VertexCreateMenu extends JPopupMenu implements
		IGraphItemMenuListener {

	private static final long serialVersionUID = 6897658442329318591L;

	private final static String TITLE = "Neuer Knoten";
	private final static String NEW_VERTEX_LABEL = "Neuer Knoten";
	
	private final VertexFactory vertexFactory;

	private Point2D point = null;

	/**
	 * @param viewer
	 * @param model 
	 */
	public VertexCreateMenu(final GravisVisualizationViewer viewer, IGuiModel model) {
		super(TITLE);

		this.vertexFactory = new VertexFactory();

		JMenuItem newVertexMenuItem = new JMenuItem(NEW_VERTEX_LABEL);
		this.add(newVertexMenuItem);
		model.setVertexCreateButtonModel(newVertexMenuItem.getModel());
		
		newVertexMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VertexCreateMenu.this.createVertex(viewer);
			}
		});
	}

	/**
	 * 
	 * @param viewer
	 */
	private void createVertex(GravisVisualizationViewer viewer) {
		if (this.point != null) {
			IVertex newVertex = this.vertexFactory.create();
			Layout<IVertex, IEdge> layout = viewer.getGraphLayout();
			Point2D newPoint = viewer.getRenderContext()
			. getMultiLayerTransformer().inverseTransform(this.point);
			
			newVertex.setLocation(newPoint);
			layout.getGraph().addVertex(newVertex);
			layout.setLocation(newVertex, newPoint);
			viewer.repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.visualization.GraphItemMenuListener#
	 * setGraphItemAndView
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem)
	 */
	@Override
	public void setGraphItemAndView(IGraphItem item) {
		// // does nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.visualization.GraphItemMenuListener#
	 * setGraphItemLocation(java.awt.geom.Point2D)
	 */
	@Override
	public void setGraphItemLocation(Point2D point) {
		if (point != null) {
			this.point = point;
		}
	}

}
