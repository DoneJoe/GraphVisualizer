package ch.bfh.bti7301.hs2013.gravis.core.util.transformer;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;

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
		return edge.isCurrentDashed() ? new BasicStroke(
				edge.getCurrentStrokeWidth(), BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, GravisConstants.MITER_LIMIT_DEFAULT,
				new float[] { GravisConstants.DASH_DEFAULT },
				GravisConstants.DASH_PHASE_DEFAULT) : new BasicStroke(
				edge.getCurrentStrokeWidth());
	}
}
