package ch.bfh.bti7301.hs2013.gravis.core.graph.item;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphStepEvent;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditableGraphEventListener;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public abstract class AbstractEditingGraphItem {

	private final List<IEditableGraphEventListener> listeners;
	
	protected AbstractEditingGraphItem() {
		this.listeners = new ArrayList<>();
	}

	/**
	 * 
	 * @param listeners
	 */
	public void addEditingGraphEventListener(IEditableGraphEventListener ... listeners) {
		for (IEditableGraphEventListener listener : listeners) {
			this.listeners.add(listener);
		}
	}
	
	public void removeEditingGraphEventListeners() {
		this.listeners.clear();
	}
	
	/**
	 * 
	 * @param event
	 */
	protected void fireEditingGraphEvent(GraphStepEvent event) {
		for (IEditableGraphEventListener listener : this.listeners) {
			listener.graphItemsChangedEvent(event);
		}
	}

	/**
	 * 
	 * @param event
	 */
	protected void fireNameChangedEvent(GraphStepEvent event) {
		for (IEditableGraphEventListener listener : this.listeners) {
			listener.graphPropertiesChangedEvent(event);
		}
	}
}
