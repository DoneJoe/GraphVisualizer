package ch.bfh.bti7301.hs2013.gravis.core.util.transformer;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;

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
		return vertex.isCurrentDashed() ? new BasicStroke(
				vertex.getCurrentStrokeWidth(), BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, GravisConstants.MITER_LIMIT_DEFAULT,
				new float[] { GravisConstants.DASH_DEFAULT },
				GravisConstants.DASH_PHASE_DEFAULT) : new BasicStroke(
				vertex.getCurrentStrokeWidth());
	}

}
