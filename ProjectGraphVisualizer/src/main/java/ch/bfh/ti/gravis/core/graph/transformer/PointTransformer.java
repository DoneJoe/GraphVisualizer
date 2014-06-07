package ch.bfh.ti.gravis.core.graph.transformer;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;

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
public class PointTransformer implements Transformer<IVertex, Point2D> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public Point2D transform(IVertex vertex) {
		return vertex == null ? new Point() : vertex.getLocation();
	}

}
