package ch.bfh.ti.gravis.core.graph.item;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type;

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
	 * @see ch.bfh.ti.gravis.core.graph.item.IEditItemObservable#
	 * addEditGraphEventListeners
	 * (ch.bfh.ti.gravis.core.graph.IEditGraphEventListener[])
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
	 * @see ch.bfh.ti.gravis.core.graph.item.IEditItemObservable#
	 * removeEditGraphEventListeners()
	 */
	@Override
	public void removeEditGraphEventListeners() {
		this.listeners.clear();
	}

	/**
	 * 
	 * @param source
	 * @param type
	 */
	protected void fireGraphItemsChangedEvent(IGraphItem source, Type type) {
		for (IEditGraphEventListener listener : this.listeners) {
			listener.handleGraphItemsChangedEvent(source, type);
		}
	}

}
