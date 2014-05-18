package ch.bfh.ti.gravis.core.graph.transformer;

import java.text.DecimalFormat;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VertexLocationXTransformer implements Transformer<IVertex, String> {

	private DecimalFormat doubleFormat;

	public VertexLocationXTransformer() {
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
		return vertex == null ? "" : this.doubleFormat.format(vertex
				.getLocation().getX());
	}

}
