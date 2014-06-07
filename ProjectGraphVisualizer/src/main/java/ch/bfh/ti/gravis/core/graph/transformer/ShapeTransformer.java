package ch.bfh.ti.gravis.core.graph.transformer;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
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
public class ShapeTransformer implements Transformer<IVertex, Shape> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public Shape transform(IVertex vertex) {
		return vertex == null ? new Ellipse2D.Double(-GravisConstants.V_WIDTH_DEFAULT / 2.0,
				-GravisConstants.V_HEIGHT_DEFAULT / 2.0, GravisConstants.V_WIDTH_DEFAULT,
				GravisConstants.V_HEIGHT_DEFAULT) : 
				new Ellipse2D.Double(-vertex.getWidth() / 2.0,
				-vertex.getHeight() / 2.0, vertex.getWidth(), vertex.getHeight());
	}

}
