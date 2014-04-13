package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.util.Observable;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphStepEvent;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditableGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class VisualizationController extends Observable implements
		IEditableGraphEventListener {

	private final IAppModel model;

	/**
	 * 
	 * @param model
	 */
	protected VisualizationController(IAppModel model) {
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditableGraphEventListener#
	 * raphItemsChangedEvent
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.GraphStepEvent)
	 */
	@Override
	public void graphItemsChangedEvent(GraphStepEvent evt) {
		// update model
		this.model.resetStepEnabledState();
		this.model.setGraphChanged(true);
		this.model.setGraphUnsaved(true);
		
		// update view
		this.setChanged();
		this.notifyObservers(this.model.createToolBarModel());
		this.setChanged();
		this.notifyObservers(this.model.createStepModel());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditableGraphEventListener#
	 * graphPropertiesChangedEvent
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.GraphStepEvent)
	 */
	@Override
	public void graphPropertiesChangedEvent(GraphStepEvent evt) {
		// update model
		this.model.setGraphUnsaved(true);
		
		// update view
		this.setChanged();
		this.notifyObservers(this.model.getGraph());
	}

}
