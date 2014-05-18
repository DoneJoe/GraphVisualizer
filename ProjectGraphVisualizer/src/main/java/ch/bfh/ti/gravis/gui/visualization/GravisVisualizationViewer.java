package ch.bfh.ti.gravis.gui.visualization;

import java.util.Observable;
import java.util.Observer;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeArrowStrokeTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeColorTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeLabelTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeStrokeTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeToolTipTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.ShapeTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexDrawColorTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexFillColorTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexLabelTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexStrokeTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexToolTipTransformer;
import ch.bfh.ti.gravis.core.util.GravisColor;
import ch.bfh.ti.gravis.core.util.GravisConstants;
import ch.bfh.ti.gravis.gui.model.VisualizationViewModel;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ConstantDirectionalEdgeValueTransformer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GravisVisualizationViewer extends
		VisualizationViewer<IVertex, IEdge> implements Observer {

	private static final long serialVersionUID = 1145648259547595925L;

	private static final int LABEL_OFFSET = 8;
	private static final double LABEL_CLOSENESS = 0.5;

	/**
	 * 
	 * @param layout
	 */
	public GravisVisualizationViewer(final Layout<IVertex, IEdge> layout) {
		super(layout);

		this.setBackground(GravisColor.WHITE);
		this.getRenderContext().setLabelOffset(LABEL_OFFSET);

		// vertex visualization:

		GravisVertexLabelRenderer vertexLabelRenderer = new GravisVertexLabelRenderer(
				GravisConstants.V_DRAW_INITIAL_COLOR,
				GravisConstants.V_PICKED_COLOR);

		this.getRenderContext().setVertexLabelRenderer(vertexLabelRenderer);
		this.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		this.getRenderContext().setVertexFillPaintTransformer(
				new VertexFillColorTransformer());
		this.getRenderContext().setVertexDrawPaintTransformer(
				new VertexDrawColorTransformer(vertexLabelRenderer));
		this.getRenderContext().setVertexShapeTransformer(
				new ShapeTransformer());
		this.getRenderContext().setVertexLabelTransformer(
				new VertexLabelTransformer());
		this.getRenderContext().setVertexStrokeTransformer(
				new VertexStrokeTransformer());
		this.setVertexToolTipTransformer(new VertexToolTipTransformer());

		// edge visualization:

		GravisEdgeLabelRenderer edgeLabelRenderer = new GravisEdgeLabelRenderer(
				GravisConstants.E_INITIAL_COLOR, GravisConstants.E_PICKED_COLOR);
		EdgeColorTransformer edgeColorTransformer = new EdgeColorTransformer(
				edgeLabelRenderer);

		this.getRenderContext().setEdgeLabelRenderer(edgeLabelRenderer);
		this.getRenderContext().setEdgeShapeTransformer(
				new EdgeShape.Line<IVertex, IEdge>());
		this.getRenderContext().setEdgeDrawPaintTransformer(
				edgeColorTransformer);
		this.getRenderContext().setEdgeFillPaintTransformer(
				edgeColorTransformer);
		this.getRenderContext().setEdgeStrokeTransformer(
				new EdgeStrokeTransformer());
		this.getRenderContext().setArrowDrawPaintTransformer(
				edgeColorTransformer);
		this.getRenderContext().setArrowFillPaintTransformer(
				edgeColorTransformer);
		this.getRenderContext().setEdgeArrowStrokeTransformer(
				new EdgeArrowStrokeTransformer());
		this.getRenderContext().setEdgeLabelTransformer(
				new EdgeLabelTransformer());
		this.setEdgeToolTipTransformer(new EdgeToolTipTransformer());
		// centers edge label
		this.getRenderContext().setEdgeLabelClosenessTransformer(
				new ConstantDirectionalEdgeValueTransformer<IVertex, IEdge>(
						LABEL_CLOSENESS, LABEL_CLOSENESS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(final Observable o, final Object arg) {
		if (arg instanceof VisualizationViewModel) {
			VisualizationViewModel model = (VisualizationViewModel) arg;

			if (model.isGraphChanged()) {
				this.getGraphLayout().setGraph(model.getGraph());
			}

			this.repaint();
		}
	}

}
