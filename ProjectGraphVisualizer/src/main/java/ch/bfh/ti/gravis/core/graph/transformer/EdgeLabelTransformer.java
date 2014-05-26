package ch.bfh.ti.gravis.core.graph.transformer;

import java.text.DecimalFormat;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class EdgeLabelTransformer implements Transformer<IEdge, String> {

	private final DecimalFormat doubleFormat;

	public EdgeLabelTransformer() {
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
	public String transform(IEdge edge) {
		return edge != null && edge.isCurrentVisible() ? (this.doubleFormat.format((edge
				.getWeight())) + (Double.isNaN(edge.getCurrentResult()) ? ""
				: " - " + this.doubleFormat.format(edge.getCurrentResult())))
				: "";
	}
}
