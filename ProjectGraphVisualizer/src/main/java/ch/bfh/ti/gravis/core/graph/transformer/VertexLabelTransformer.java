package ch.bfh.ti.gravis.core.graph.transformer;

import java.text.DecimalFormat;

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
public class VertexLabelTransformer implements Transformer<IVertex, String> {

	private DecimalFormat doubleFormat;

	public VertexLabelTransformer() {
		this.doubleFormat = new DecimalFormat();
		this.doubleFormat.setMinimumFractionDigits(0);
		this.doubleFormat.setMaximumFractionDigits(2);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public String transform(IVertex vertex) {
		return vertex != null && vertex.isCurrentVisible() ? (vertex.getName()
				+ (Double.isNaN(vertex.getCurrentResult()) ? "" : ": "
						+ this.doubleFormat.format(vertex
								.getCurrentResult()))) : "";
	}

}
