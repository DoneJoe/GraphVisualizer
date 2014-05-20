package ch.bfh.ti.gravis.core.graph.item.edge;

import java.util.Objects;

import org.apache.commons.collections15.Factory;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class EdgeFactory implements Factory<IEdge> {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "EdgeFactory.%s(): %s == %s";

	private static final String LABEL = "e";

	private static int counter = 0;

	/**
	 * @return default name
	 */
	public static String createEdgeName() {
		return LABEL + String.valueOf(++counter);
	}

	/**
	 * @param edge
	 * @return IEdge
	 */
	public static IRestrictedEdge createRestrictedEdge(final IEdge edge) {
		return new RestrictedEdge(Objects.requireNonNull(edge, String.format(
				NULL_POINTER_MSG, "createRestrictedEdge", "edge", edge)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.collections15.Factory#create()
	 */
	@Override
	public IEdge create() {
		return new GravisEdge();
	}

}
