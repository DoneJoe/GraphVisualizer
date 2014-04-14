package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.util.Observable;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class VisualizationController extends Observable implements
		IEditGraphEventListener {

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
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphEventListener#
	 * handleGraphItemsChangedEvent
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphEventListener.Type)
	 */
	@Override
	public void handleGraphItemsChangedEvent(IGraphItem source, Type type) {
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
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphEventListener#
	 * handleGraphPropertiesChangedEvent
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph)
	 */
	@Override
	public void handleGraphPropertiesChangedEvent(IGravisGraph source) {
		// update model
		this.model.setGraphUnsaved(true);

		// update view
		this.setChanged();
		this.notifyObservers(this.model.getGraph());
	}

}
