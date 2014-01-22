package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraphEvent;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class VisualizationController implements IEditingGraphEventListener {

	/**
	 * @param core
	 * @param model
	 */
	protected VisualizationController(ICore core, IGuiModel model) {
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		// TODO bei graph-name änderung -> update von border-label
	}

}
