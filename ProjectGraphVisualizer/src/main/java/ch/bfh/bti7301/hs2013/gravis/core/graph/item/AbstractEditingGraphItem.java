package ch.bfh.bti7301.hs2013.gravis.core.graph.item;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraphEvent;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public abstract class AbstractEditingGraphItem {

	private final List<IEditingGraphEventListener> listeners;
	
	protected AbstractEditingGraphItem() {
		this.listeners = new ArrayList<>();
	}

	/**
	 * 
	 * @param listener
	 */
	public void addEditingGraphEventListener(IEditingGraphEventListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * 
	 * @param event
	 */
	protected void fireEditingGraphEvent(GravisGraphEvent event) {
		for (IEditingGraphEventListener listener : this.listeners) {
			listener.handleEditingGraphEvent(event);
		}
	}

	/**
	 * 
	 * @param event
	 */
	protected void fireNameChangedEvent(GravisGraphEvent event) {
		for (IEditingGraphEventListener listener : this.listeners) {
			listener.handleNameChangedEvent(event);
		}
	}
}
