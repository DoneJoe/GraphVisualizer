package ch.bfh.ti.gravis.core.graph.transformer;

import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 */
public class VertexFillColorTransformer implements Transformer<IVertex, Paint> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.collections15.Transformer#transform(java.lang.
	 * Object)
	 */
	@Override
	public Paint transform(IVertex vertex) {
		return vertex == null ? GravisConstants.V_FILL_COLOR_DEFAULT : vertex
				.getCurrentColor();
	}

}