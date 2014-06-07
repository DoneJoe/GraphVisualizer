package ch.bfh.ti.gravis.core.graph.transformer;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.util.GravisConstants;

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
public class EdgeArrowStrokeTransformer implements Transformer<IEdge, Stroke> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public Stroke transform(IEdge edge) {
		return new BasicStroke(
				edge != null && edge.isCurrentTagged() ? GravisConstants.E_TAGGED_STROKE
						: GravisConstants.STROKE_WIDTH_DEFAULT);
	}

}
