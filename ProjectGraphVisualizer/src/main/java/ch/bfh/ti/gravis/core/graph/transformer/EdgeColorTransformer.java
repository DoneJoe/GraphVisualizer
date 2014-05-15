package ch.bfh.ti.gravis.core.graph.transformer;

import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class EdgeColorTransformer implements Transformer<IEdge, Paint> {

	private final DefaultEdgeLabelRenderer edgeLabelRenderer;
	
	/**
	 * @param edgeLabelRenderer
	 */
	public EdgeColorTransformer(DefaultEdgeLabelRenderer edgeLabelRenderer) {
		this.edgeLabelRenderer = edgeLabelRenderer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.collections15.Transformer#transform(java.lang.
	 * Object)
	 */
	@Override
	public Paint transform(IEdge edge) {
		this.edgeLabelRenderer.setForeground(edge.getCurrentColor());
		return edge.getCurrentColor();
	}

}
