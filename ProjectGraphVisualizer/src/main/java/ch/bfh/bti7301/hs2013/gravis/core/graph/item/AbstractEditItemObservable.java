package ch.bfh.bti7301.hs2013.gravis.core.graph.item;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphEventListener.Type;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public abstract class AbstractEditItemObservable implements IEditItemObservable {

	private final List<IEditGraphEventListener> listeners;

	protected AbstractEditItemObservable() {
		this.listeners = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IEditItemObservable#
	 * addEditGraphEventListeners
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphEventListener[])
	 */
	@Override
	public void addEditGraphEventListeners(IEditGraphEventListener... listeners) {
		for (IEditGraphEventListener li : listeners) {
			this.listeners.add(li);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IEditItemObservable#
	 * removeEditGraphEventListeners()
	 */
	@Override
	public void removeEditGraphEventListeners() {
		this.listeners.clear();
	}

	/**
	 * 
	 * @param source
	 */
	protected void fireGraphItemsChangedEvent(IGraphItem source) {
		for (IEditGraphEventListener listener : this.listeners) {
			listener.handleGraphItemsChangedEvent(source, Type.EDITED);
		}
	}

}
