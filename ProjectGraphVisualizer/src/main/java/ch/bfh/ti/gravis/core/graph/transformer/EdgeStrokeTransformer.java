package ch.bfh.ti.gravis.core.graph.transformer;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class EdgeStrokeTransformer implements Transformer<IEdge, Stroke> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public Stroke transform(IEdge edge) {
		float currentStrokeWidth = edge != null && edge.isCurrentTagged() ? GravisConstants.E_TAGGED_STROKE
				: GravisConstants.STROKE_WIDTH_DEFAULT;

		return edge != null && edge.isCurrentDashed() ? new BasicStroke(currentStrokeWidth,
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
				GravisConstants.MITER_LIMIT_DEFAULT,
				new float[] { GravisConstants.DASH_DEFAULT },
				GravisConstants.DASH_PHASE_DEFAULT) : new BasicStroke(
				currentStrokeWidth);
	}
}
