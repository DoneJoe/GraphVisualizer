package ch.bfh.ti.gravis.core.graph.transformer;

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.util.GravisConstants;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;

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
	public Paint transform(final IEdge edge) {
		Color color = edge == null ? GravisConstants.E_COLOR_DEFAULT :
			edge.getCurrentColor();
		this.edgeLabelRenderer.setForeground(color);
		return color;
	}

}
