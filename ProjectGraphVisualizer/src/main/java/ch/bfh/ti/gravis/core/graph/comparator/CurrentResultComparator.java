package ch.bfh.ti.gravis.core.graph.comparator;

import java.util.Comparator;

import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;

/**
 * This Comparator compares the current result of two vertices.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class CurrentResultComparator implements Comparator<IRestrictedVertex> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(IRestrictedVertex v1, IRestrictedVertex v2) {
		return v1 == null || v2 == null ? 0 : Double.compare(
				v1.getCurrentResult(), v2.getCurrentResult());
	}

}
