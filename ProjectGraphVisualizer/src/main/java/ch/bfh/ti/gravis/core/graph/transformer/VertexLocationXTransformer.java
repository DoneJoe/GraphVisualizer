package ch.bfh.ti.gravis.core.graph.transformer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.util.ValueTransformer;

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
public class VertexLocationXTransformer implements Transformer<IVertex, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public String transform(IVertex vertex) {
		return vertex == null ? "" : String.valueOf(ValueTransformer.round2Decimals(vertex
				.getLocation().getX()));
	}

}
