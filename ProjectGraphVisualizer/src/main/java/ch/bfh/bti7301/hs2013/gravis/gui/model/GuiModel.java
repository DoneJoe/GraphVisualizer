package ch.bfh.bti7301.hs2013.gravis.gui.model;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class GuiModel implements IGuiModel {

	protected GuiModel() {
		// TODO implement
		GraphFactory.createGravisGraph();
	}
	
}
