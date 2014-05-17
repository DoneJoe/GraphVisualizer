package ch.bfh.ti.gravis.core.graph.item.vertex;

import java.util.Objects;

import org.apache.commons.collections15.Factory;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VertexFactory implements Factory<IVertex> {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "VertexFactory.%s(): %s == %s";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.collections15.Factory#create()
	 */
	@Override
	public IVertex create() {
		return new GravisVertex();
	}

	/**
	 * @param vertex
	 * @return the restricted vertex
	 */
	public static IRestrictedVertex createRestrictedVertex(IVertex vertex) {
		return new RestrictedVertex(Objects.requireNonNull(vertex, String.format(
				NULL_POINTER_MSG, "createRestrictedVertex", "vertex",
				vertex)));
	}

}
