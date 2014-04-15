package ch.bfh.bti7301.hs2013.gravis.core.graph.comparator;

import java.util.Comparator;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem;

/**
 * This comparator compares graph items in lexicographical name order.
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
		return i1.getName().compareTo(i2.getName());
	}

}
