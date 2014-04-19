package ch.bfh.ti.gravis.gui.controller;

import java.util.Observable;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.gui.model.IAppModel;

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
	 * @see ch.bfh.ti.gravis.core.graph.IEditGraphEventListener#
	 * handleGraphItemsChangedEvent
	 * (ch.bfh.ti.gravis.core.graph.item.IGraphItem,
	 * ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type)
	 */
	@Override
	public void handleGraphItemsChangedEvent(final IGraphItem source, final Type type) {
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
	 * @see ch.bfh.ti.gravis.core.graph.IEditGraphEventListener#
	 * handleGraphPropertiesChangedEvent
	 * (ch.bfh.ti.gravis.core.graph.IGravisGraph)
	 */
	@Override
	public void handleGraphPropertiesChangedEvent(final IGravisGraph source) {
		// update model
		this.model.setGraphUnsaved(true);

		// update view
		this.setChanged();
		this.notifyObservers(this.model.getGraph());
	}

}
