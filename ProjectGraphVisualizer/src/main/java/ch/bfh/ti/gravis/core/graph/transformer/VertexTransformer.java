package ch.bfh.ti.gravis.core.graph.transformer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.ti.gravis.core.util.GravisConstants;
import ch.bfh.ti.gravis.core.util.ValueTransformer;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class VertexTransformer implements Transformer<NodeMetadata, IVertex> {

	private final VertexFactory vertexFactory;
	
	public VertexTransformer() {
		this.vertexFactory = new VertexFactory();
	}
	
	/* (non-Javadoc)
	 * @see org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public IVertex transform(NodeMetadata vertexMeta) {
		IVertex vertex = this.vertexFactory.create();
		String color = vertexMeta.getProperty(GravisConstants.V_COLOR);
		
		vertex.setName(vertexMeta.getId());
		vertex.setCurrentColor(color == null ? GravisConstants.V_FILL_COLOR_DEFAULT : 
			ValueTransformer.transformStringToColor(color));		
		
		vertex.setLocation(ValueTransformer.transformToLocation(vertexMeta
				.getProperty(GravisConstants.V_LOC_X), vertexMeta
				.getProperty(GravisConstants.V_LOC_Y)));
		vertex.setStart(ValueTransformer.transformToBoolean(vertexMeta
				.getProperty(GravisConstants.V_START)));
		vertex.setEnd(ValueTransformer.transformToBoolean(vertexMeta
				.getProperty(GravisConstants.V_END)));
		vertex.setWidth(ValueTransformer.transformToDouble(vertexMeta
				.getProperty(GravisConstants.V_WIDTH)));
		vertex.setHeight(ValueTransformer.transformToDouble(vertexMeta
				.getProperty(GravisConstants.V_HEIGHT)));
		
		return vertex;
	}

}
