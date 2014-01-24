package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.util.Observable;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraphEvent;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class VisualizationController extends Observable implements
		IEditingGraphEventListener {

	private final IGuiModel model;

	/**
	 * 
	 * @param model
	 */
	protected VisualizationController(IGuiModel model) {
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener#
	 * handleEditingGraphEvent
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraphEvent)
	 */
	@Override
	public void handleEditingGraphEvent(GravisGraphEvent evt) {
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
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener#
	 * handleGraphNameChangedEvent
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraphEvent)
	 */
	@Override
	public void handleNameChangedEvent(GravisGraphEvent evt) {
		// update model
		this.model.setGraphUnsaved(true);
		
		// update view
		this.setChanged();
		this.notifyObservers(this.model.getGraph());
	}

}
