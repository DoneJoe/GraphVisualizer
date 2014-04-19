package ch.bfh.ti.gravis.core.graph.transformer;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VertexStrokeTransformer implements Transformer<IVertex, Stroke> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public Stroke transform(IVertex vertex) {
		float dashValue = GravisConstants.DASH_DEFAULT;
		float currentStrokeWidth = vertex.isCurrentTagged() ? GravisConstants.V_TAGGED_STROKE
				: GravisConstants.STROKE_WIDTH_DEFAULT;

		if (vertex.isStart()) {
			dashValue = GravisConstants.V_START_DASH;
		} else if (vertex.isEnd()) {
			dashValue = GravisConstants.V_END_DASH;
		}

		return vertex.isCurrentDashed() || vertex.isStart() || vertex.isEnd() ? new BasicStroke(
				currentStrokeWidth, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, GravisConstants.MITER_LIMIT_DEFAULT,
				new float[] { dashValue }, GravisConstants.DASH_PHASE_DEFAULT)
				: new BasicStroke(currentStrokeWidth);
	}

}
