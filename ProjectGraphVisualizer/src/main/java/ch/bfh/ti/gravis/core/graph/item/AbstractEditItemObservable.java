package ch.bfh.ti.gravis.core.graph.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type;

/**
 * This basic implementation of the {@link IEditItemObservable} interface provides
 * support for adding and removing {@link IEditGraphEventListener} listeners to
 * graph items.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public abstract class AbstractEditItemObservable implements IEditItemObservable {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "AbstractEditItemObservable.%s(): %s == %s";

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
		Objects.requireNonNull(listeners, String.format(NULL_POINTER_MSG,
				"addEditGraphEventListeners", "listeners", listeners));

		for (IEditGraphEventListener li : listeners) {
			this.listeners.add(Objects.requireNonNull(li, String.format(
					NULL_POINTER_MSG, "addEditGraphEventListeners", "li", li)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.IEditItemObservable#
	 * removeEditGraphEventListener
	 * (ch.bfh.ti.gravis.core.graph.IEditGraphEventListener[])
	 */
	@Override
	public void removeEditGraphEventListeners(
			IEditGraphEventListener... listeners) {
		for (IEditGraphEventListener lst : listeners) {
			while (this.listeners.contains(lst)) {
				this.listeners.remove(lst);
			}
		}
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
