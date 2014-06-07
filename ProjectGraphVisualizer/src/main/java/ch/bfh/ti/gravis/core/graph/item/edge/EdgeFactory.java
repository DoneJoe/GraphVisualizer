package ch.bfh.ti.gravis.core.graph.item.edge;

import java.util.Objects;

import org.apache.commons.collections15.Factory;

/**
 * Edge factory class with static and instance factory methods.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class EdgeFactory implements Factory<IEdge> {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "EdgeFactory.%s(): %s == %s";

	private static final String LABEL = "e";

	private static int counter = 0;

	/**
	 * Creates a new default edge name.
	 * 
	 * @return a new default edge name
	 */
	public static String createEdgeName() {
		return LABEL + String.valueOf(++counter);
	}

	/**
	 * Decorates an instance of {@code IEdge} and creates a restricted edge {@code IRestrictedEdge}.
	 * 
	 * @param edge
	 * @return a restricted edge
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
