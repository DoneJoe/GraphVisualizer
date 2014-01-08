package ch.bfh.bti7301.hs2013.gravis.gui.model;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class GuiModel implements IGuiModel {

	private IGravisGraph graph;
	
	protected GuiModel() {
		// TODO implement
		
		this.graph = GraphFactory.createGravisGraph();
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getGraph()
	 */
	@Override
	public IGravisGraph getGraph() {
		return this.graph;
	}
	
}
