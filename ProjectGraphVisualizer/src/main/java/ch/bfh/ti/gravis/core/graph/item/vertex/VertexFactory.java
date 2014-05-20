package ch.bfh.ti.gravis.core.graph.item.vertex;

import java.util.Objects;

import org.apache.commons.collections15.Factory;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VertexFactory implements Factory<IVertex> {

	// constants:

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "VertexFactory.%s(): %s == %s";

	private static final char FIRST_CHAR = 'A';

	private static final char LAST_CHAR = 'Z';

	// static counters:

	private static int intCounter = 0;

	private static char charCounter = FIRST_CHAR;

	/**
	 * @return default name
	 */
	public static String createVertexName() {
		String newChar = String.valueOf(charCounter);
		String newName = intCounter == 0 ? newChar : newChar
				+ String.valueOf(intCounter);

		if (LAST_CHAR - charCounter == 0) {
			charCounter = FIRST_CHAR;
			intCounter++;
		} else {
			charCounter++;
		}

		return newName;
	}

	/**
	 * @param vertex
	 * @return the restricted vertex
	 */
	public static IRestrictedVertex createRestrictedVertex(IVertex vertex) {
		return new RestrictedVertex(Objects.requireNonNull(vertex, String
				.format(NULL_POINTER_MSG, "createRestrictedVertex", "vertex",
						vertex)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.collections15.Factory#create()
	 */
	@Override
	public IVertex create() {
		return new GravisVertex();
	}

}
