package ch.bfh.ti.gravis.core.graph.comparator;

import java.util.Comparator;

import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;

/**
 * This Comparator compares graph items names in lexicographical order.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ItemNameComparator implements Comparator<IRestrictedGraphItem> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(IRestrictedGraphItem i1, IRestrictedGraphItem i2) {
		return i1 == null || i2 == null ? 0 : i1.getName().compareTo(
				i2.getName());
	}

}
