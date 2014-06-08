package ch.bfh.ti.gravis.gui.visualization;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.gui.popup.IGraphItemMenuListener;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin;

/**
 * A plugin that uses popup menus to edit the vertices and edges.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GravisPopupGraphMousePlugin extends
		EditingPopupGraphMousePlugin<IVertex, IEdge> {

	private JPopupMenu edgePopup = null;

	private JPopupMenu vertexPopup = null;

	private JPopupMenu vertexCreatePopup = null;

	/**
	 * 
	 * @param vertexFactory
	 * @param edgeFactory
	 */
	public GravisPopupGraphMousePlugin(Factory<IVertex> vertexFactory,
			Factory<IEdge> edgeFactory) {
		super(vertexFactory, edgeFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin#
	 * handlePopup(java.awt.event.MouseEvent)
	 */
	@Override
	protected void handlePopup(final MouseEvent e) {
		if (e.getSource() instanceof VisualizationViewer<?, ?>) {
			@SuppressWarnings("unchecked")
			VisualizationViewer<IVertex, IEdge> vViewer = (VisualizationViewer<IVertex, IEdge>) e
					.getSource();
			Point2D point = e.getPoint();
			GraphElementAccessor<IVertex, IEdge> pickSupport = vViewer
					.getPickSupport();

			if (pickSupport != null) {
				IVertex vertex = pickSupport.getVertex(
						vViewer.getGraphLayout(), point.getX(), point.getY());
				IEdge edge = pickSupport.getEdge(vViewer.getGraphLayout(),
						point.getX(), point.getY());

				if (vertex != null && this.vertexPopup != null) {
					// show vertex popup menu
					this.updateItemMenu(vertex, point, this.vertexPopup);
					this.vertexPopup.show(vViewer, e.getX(), e.getY());
				} else if (edge != null && this.edgePopup != null) {
					// show edge popup menu
					this.updateItemMenu(edge, point, this.edgePopup);
					this.edgePopup.show(vViewer, e.getX(), e.getY());
				} else if (edge == null && vertex == null
						&& this.vertexCreatePopup != null) {
					if (this.vertexCreatePopup instanceof IGraphItemMenuListener) {
						((IGraphItemMenuListener) this.vertexCreatePopup)
								.setGraphItemLocation(point);
					}
					this.vertexCreatePopup.show(vViewer, e.getX(), e.getY());
				}
			}
			vViewer.repaint();
		}
	}

	/**
	 * 
	 * @param item
	 * @param point
	 * @param popUp
	 */
	private void updateItemMenu(final IGraphItem item, final Point2D point,
			final JPopupMenu popUp) {
		for (Component comp : popUp.getComponents()) {
			if (comp instanceof IGraphItemMenuListener) {
				((IGraphItemMenuListener) comp).setGraphItem(item);
				((IGraphItemMenuListener) comp).setGraphItemLocation(point);
			}
		}
	}

	/**
	 * Setter for the edge popup.
	 * 
	 * @param edgePopup
	 */
	public void setEdgePopup(JPopupMenu edgePopup) {
		this.edgePopup = edgePopup;
	}

	/**
	 * Setter for the vertex popup.
	 * 
	 * @param vertexPopup
	 */
	public void setVertexPopup(JPopupMenu vertexPopup) {
		this.vertexPopup = vertexPopup;
	}

	/**
	 * Setter for the vertex create popup.
	 * 
	 * @param vertexCreatePopup
	 */
	public void setVertexCreatePopup(JPopupMenu vertexCreatePopup) {
		this.vertexCreatePopup = vertexCreatePopup;
	}

}
