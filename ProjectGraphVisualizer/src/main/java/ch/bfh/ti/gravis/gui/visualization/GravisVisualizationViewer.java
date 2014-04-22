package ch.bfh.ti.gravis.gui.visualization;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeArrowStrokeTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeColorTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeLabelTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.EdgeStrokeTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.ShapeTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexDrawColorTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexFillColorTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexLabelTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexStrokeTransformer;
import ch.bfh.ti.gravis.core.graph.transformer.VertexToolTipTransformer;
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

	/**
	 * 
	 * @param layout
	 */
	public GravisVisualizationViewer(final Layout<IVertex, IEdge> layout) {
		super(layout);
		this.setBackground(Color.white);

		// vertex visualization:
		// TODO config Visualisation
		
		this.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		this.getRenderContext().setVertexFillPaintTransformer(
				new VertexFillColorTransformer());
		
		// TODO setVertexDrawPaintTransformer 
		this.getRenderContext().setVertexDrawPaintTransformer(
				new VertexDrawColorTransformer());
//		this.getRenderContext().setVertexDrawPaintTransformer(
//				new Transformer<IVertex, Paint>() {					
//					@Override
//					public Paint transform(IVertex input) {
//						return Color.BLUE;
//					}
//				});
		
		this.getRenderContext().setVertexShapeTransformer(
				new ShapeTransformer());
		this.getRenderContext().setVertexLabelTransformer(
				new VertexLabelTransformer());
		this.getRenderContext().setVertexStrokeTransformer(
				new VertexStrokeTransformer());
		this.setVertexToolTipTransformer(new VertexToolTipTransformer());

		// edge visualization:
		
		this.getRenderContext().setEdgeShapeTransformer(
				new EdgeShape.Line<IVertex, IEdge>());
		
		this.getRenderContext().setEdgeDrawPaintTransformer(
				new EdgeColorTransformer());
//		this.getRenderContext().setEdgeDrawPaintTransformer(
//				new Transformer<IEdge, Paint>() {					
//					@Override
//					public Paint transform(IEdge input) {
//						return Color.BLUE;
//					}
//				});
			
		// TODO warum wird fill color nicht angezeigt?
//		this.getRenderContext().setEdgeFillPaintTransformer(
//				new Transformer<IEdge, Paint>() {					
//					@Override
//					public Paint transform(IEdge input) {
//						return Color.RED;
//					}
//				});
		
		this.getRenderContext().setEdgeStrokeTransformer(
		new EdgeStrokeTransformer());
//		this.getRenderContext().setEdgeStrokeTransformer(
//		new Transformer<IEdge, Stroke>() {
//				@Override
//				public Stroke transform(IEdge input) {
//					return new BasicStroke(0.0f);
//				}								
//			});				
		
		this.getRenderContext().setArrowDrawPaintTransformer(
				new EdgeColorTransformer());
//		this.getRenderContext().setArrowDrawPaintTransformer(
//				new Transformer<IEdge, Paint>() {					
//					@Override
//					public Paint transform(IEdge input) {
//						return Color.PINK;
//					}
//				});

		this.getRenderContext().setArrowFillPaintTransformer(
				new EdgeColorTransformer());
//		this.getRenderContext().setArrowFillPaintTransformer(
//				new Transformer<IEdge, Paint>() {					
//					@Override
//					public Paint transform(IEdge input) {
//						return Color.PINK;
//					}
//				});
		
		this.getRenderContext().setEdgeArrowStrokeTransformer(
		new EdgeArrowStrokeTransformer());
//		this.getRenderContext().setEdgeArrowStrokeTransformer(
//			new Transformer<IEdge, Stroke>() {
//				@Override
//				public Stroke transform(IEdge input) {
//					return new BasicStroke(2.0f);
//				}								
//			});		
		
		// centers edge label
		this.getRenderContext().setEdgeLabelClosenessTransformer(
				new ConstantDirectionalEdgeValueTransformer<IVertex, IEdge>(
						0.5, 0.5));
		this.getRenderContext().setEdgeLabelTransformer(
				new EdgeLabelTransformer());
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(final Observable o, final Object arg) {
		
		// TODO VisualizationModel verwenden
		
		if (arg instanceof IGravisGraph) {
			this.getGraphLayout().setGraph((IGravisGraph) arg);
			this.repaint();
		} else if (arg == null) {
			this.repaint();
		}
	}

}
