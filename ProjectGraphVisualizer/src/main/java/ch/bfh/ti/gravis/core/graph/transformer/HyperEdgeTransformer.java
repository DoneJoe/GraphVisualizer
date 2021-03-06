package ch.bfh.ti.gravis.core.graph.transformer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.util.GravisConstants;
import ch.bfh.ti.gravis.core.util.ValueTransformer;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;

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
public class HyperEdgeTransformer implements
		Transformer<HyperEdgeMetadata, IEdge> {

	private final EdgeFactory edgeFactory;

	public HyperEdgeTransformer() {
		this.edgeFactory = new EdgeFactory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public IEdge transform(HyperEdgeMetadata hEdgeMeta) {
		IEdge edge = this.edgeFactory.create();
		
		if (hEdgeMeta != null) {
			String color = hEdgeMeta.getProperty(GravisConstants.E_COLOR);
			
			edge.setName(hEdgeMeta.getId());
			edge.setCurrentColor(color == null ? GravisConstants.E_COLOR_DEFAULT
					: ValueTransformer.toColor(hEdgeMeta
							.getProperty(GravisConstants.E_COLOR)));
			edge.setWeight(ValueTransformer.toDouble(hEdgeMeta
					.getProperty(GravisConstants.E_WEIGHT)));
		}
		
		return edge;
	}

}
