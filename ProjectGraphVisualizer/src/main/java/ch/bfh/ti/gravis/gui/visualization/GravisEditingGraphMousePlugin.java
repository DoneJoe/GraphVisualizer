package ch.bfh.ti.gravis.gui.visualization;

import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.apache.commons.collections15.Factory;

import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.gui.controller.ErrorHandler;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;

/**
 * A plugin that can create vertices and edges using mouse gestures.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * @author Tom Nelson (JUNG-Framework)
 * 
 */
public class GravisEditingGraphMousePlugin extends
		EditingGraphMousePlugin<IVertex, IEdge> {

	private ErrorHandler errHandler = null;

	/**
	 * 
	 * @param vertexFactory
	 * @param edgeFactory
	 */
	public GravisEditingGraphMousePlugin(Factory<IVertex> vertexFactory,
			Factory<IEdge> edgeFactory) {
		super(vertexFactory, edgeFactory);
	}

	/**
	 * code lifted from PluggableRenderer to move an edge shape into an
	 * arbitrary position.
	 * 
	 * @param down
	 * @param out
	 */
	private void transformEdgeShape(final Point2D down, final Point2D out) {
		float x1 = (float) down.getX();
		float y1 = (float) down.getY();
		float x2 = (float) out.getX();
		float y2 = (float) out.getY();

		AffineTransform xform = AffineTransform.getTranslateInstance(x1, y1);

		float dx = x2 - x1;
		float dy = y2 - y1;
		float thetaRadians = (float) Math.atan2(dy, dx);
		xform.rotate(thetaRadians);
		float dist = (float) Math.sqrt(dx * dx + dy * dy);
		xform.scale(dist / this.rawEdge.getBounds().getWidth(), 1.0);
		this.edgeShape = xform.createTransformedShape(this.rawEdge);
	}

	/**
	 * 
	 * @param down
	 * @param out
	 */
	private void transformArrowShape(final Point2D down, final Point2D out) {
		float x1 = (float) down.getX();
		float y1 = (float) down.getY();
		float x2 = (float) out.getX();
		float y2 = (float) out.getY();

		AffineTransform xform = AffineTransform.getTranslateInstance(x2, y2);

		float dx = x2 - x1;
		float dy = y2 - y1;
		float thetaRadians = (float) Math.atan2(dy, dx);
		xform.rotate(thetaRadians);
		this.arrowShape = xform.createTransformedShape(this.rawArrowShape);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin#mousePressed
	 * (java.awt.event.MouseEvent)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void mousePressed(final MouseEvent e) {
		try {
			if (this.checkModifiers(e)) {
				final VisualizationViewer<IVertex, IEdge> vv = (VisualizationViewer<IVertex, IEdge>) e
						.getSource();
				final Point2D p = e.getPoint();
				GraphElementAccessor<IVertex, IEdge> pickSupport = vv
						.getPickSupport();

				if (pickSupport != null) {
					Graph<IVertex, IEdge> graph = vv.getModel()
							.getGraphLayout().getGraph();

					// set default edge type
					this.edgeIsDirected = EdgeType.DIRECTED;
					if (graph instanceof IGravisGraph) {
						IGravisGraph gravisGraph = (IGravisGraph) graph;

						if (gravisGraph.getEdgeType() == EdgeType.UNDIRECTED) {
							this.edgeIsDirected = EdgeType.UNDIRECTED;
						}
					}

					final IVertex vertex = pickSupport.getVertex(vv.getModel()
							.getGraphLayout(), p.getX(), p.getY());

					if (vertex != null) {
						// get ready to make an edge
						this.startVertex = vertex;
						this.down = e.getPoint();
						this.transformEdgeShape(this.down, this.down);
						vv.addPostRenderPaintable(this.edgePaintable);

						// shift shortcut not supported in GRAVIS application

						// if ((e.getModifiers() & MouseEvent.SHIFT_MASK) != 0
						// && vv.getModel().getGraphLayout().getGraph()
						// instanceof UndirectedGraph == false) {
						// this.edgeIsDirected = EdgeType.DIRECTED;
						// }

						if (this.edgeIsDirected == EdgeType.DIRECTED) {
							this.transformArrowShape(this.down, e.getPoint());
							vv.addPostRenderPaintable(this.arrowPaintable);
						}
					} else {
						// make a new vertex
						IVertex newVertex = this.vertexFactory.create();
						Layout<IVertex, IEdge> layout = vv.getModel()
								.getGraphLayout();
						graph.addVertex(newVertex);

						Point2D point = vv.getRenderContext()
								.getMultiLayerTransformer()
								.inverseTransform(e.getPoint());
						layout.setLocation(newVertex, point);
						newVertex.setLocation(point);
					}
				}
				vv.repaint();
			}
		} catch (Throwable ex) {
			if (this.errHandler != null) {
				this.errHandler.handleAppErrorExit(ex);
			}
		}
	}

	/**
	 * @param e
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void mouseReleased(final MouseEvent e) {
		try {
			if (this.checkModifiers(e)) {
				final VisualizationViewer<IVertex, IEdge> vv = (VisualizationViewer<IVertex, IEdge>) e
						.getSource();
				final Point2D p = e.getPoint();
				Layout<IVertex, IEdge> layout = vv.getModel().getGraphLayout();
				GraphElementAccessor<IVertex, IEdge> pickSupport = vv
						.getPickSupport();

				if (pickSupport != null) {
					final IVertex vertex = pickSupport.getVertex(layout,
							p.getX(), p.getY());
					if (vertex != null && this.startVertex != null) {
						Graph<IVertex, IEdge> graph = vv.getGraphLayout()
								.getGraph();
						graph.addEdge(this.edgeFactory.create(),
								this.startVertex, vertex, this.edgeIsDirected);
						vv.repaint();
					}
				}
				this.startVertex = null;
				this.down = null;
				// set default edge type
				this.edgeIsDirected = EdgeType.DIRECTED;
				vv.removePostRenderPaintable(this.edgePaintable);
				vv.removePostRenderPaintable(this.arrowPaintable);
			}
		} catch (Throwable ex) {
			if (this.errHandler != null) {
				this.errHandler.handleAppErrorExit(ex);
			}
		}
	}

	/**
	 * @param errHandler
	 */
	public void setErrorHandler(ErrorHandler errHandler) {
		this.errHandler = errHandler;
	}

}
