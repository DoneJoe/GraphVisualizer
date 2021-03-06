package ch.bfh.ti.gravis.core.graph.transformer;

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.util.GravisConstants;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;

/**
 * Defines a functor class and transforms one object into another.
 * <p/>
 * A <code>Transformer</code> converts the input object to the output object.
 * The input object should be left unchanged. Transformers are typically used
 * for type conversions, or extracting data from an object.
 * <p/>
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class VertexDrawColorTransformer implements Transformer<IVertex, Paint> {

	private final DefaultVertexLabelRenderer vertexLabelRenderer;
	
	/**
	 * @param vertexLabelRenderer
	 */
	public VertexDrawColorTransformer(
			DefaultVertexLabelRenderer vertexLabelRenderer) {
		
		this.vertexLabelRenderer = vertexLabelRenderer;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public Paint transform(IVertex vertex) {
		Color color = vertex == null ? GravisConstants.V_DRAW_COLOR_DEFAULT :
			vertex.getCurrentDrawColor();
		this.vertexLabelRenderer.setForeground(color);
		return color;
	}

}
