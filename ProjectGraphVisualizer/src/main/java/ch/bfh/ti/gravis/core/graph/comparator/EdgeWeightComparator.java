package ch.bfh.ti.gravis.core.graph.comparator;

import java.util.Comparator;

import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;

/**
 * This Comparator compares the weight of two edges.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class EdgeWeightComparator implements Comparator<IRestrictedEdge> {

	public EdgeWeightComparator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.IRestrictedEdge,
	 * java.lang.IRestrictedEdge)
	 */
	@Override
	public int compare(IRestrictedEdge e1, IRestrictedEdge e2) {
		return e1 == null || e2 == null ? 0 : Double.compare(e1.getWeight(),
				e2.getWeight());
	}

}
