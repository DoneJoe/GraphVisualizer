package ch.bfh.ti.gravis.gui.visualization;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.gui.controller.ErrorHandler;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

/**
 * PickingGraphMousePlugin supports the picking of graph elements with the
 * mouse. If a vertex is selected and the mouse is dragged while on the selected
 * vertex, then that vertex will be repositioned to follow the mouse until the
 * button is released.
 * 
 * 
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * @author Tom Nelson (JUNG-Framework)
 * 
 */
public class GravisPickingGraphMousePlugin extends
		PickingGraphMousePlugin<IVertex, IEdge> {

	private ErrorHandler errHandler = null;

	public GravisPickingGraphMousePlugin() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin#mouseDragged
	 * (java.awt.event.MouseEvent)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void mouseDragged(final MouseEvent e) {
		try {
			if (this.locked == false) {
				VisualizationViewer<IVertex, IEdge> vv = (VisualizationViewer<IVertex, IEdge>) e
						.getSource();
				if (this.vertex != null) {
					Point p = e.getPoint();
					Point2D graphPoint = vv.getRenderContext()
							.getMultiLayerTransformer().inverseTransform(p);
					Point2D graphDown = vv.getRenderContext()
							.getMultiLayerTransformer()
							.inverseTransform(this.down);
					Layout<IVertex, IEdge> layout = vv.getGraphLayout();
					double dx = graphPoint.getX() - graphDown.getX();
					double dy = graphPoint.getY() - graphDown.getY();
					PickedState<IVertex> ps = vv.getPickedVertexState();

					for (IVertex v : ps.getPicked()) {
						Point2D vp = layout.transform(v);
						vp.setLocation(vp.getX() + dx, vp.getY() + dy);
						layout.setLocation(v, vp);

						// update vertex location
						v.setLocation(vp);
					}
					this.down = p;

				} else {
					Point2D out = e.getPoint();
					if (e.getModifiers() == this.addToSelectionModifiers
							|| e.getModifiers() == this.modifiers) {
						this.rect.setFrameFromDiagonal(this.down, out);
					}
				}
				if (this.vertex != null)
					e.consume();
				vv.repaint();
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
