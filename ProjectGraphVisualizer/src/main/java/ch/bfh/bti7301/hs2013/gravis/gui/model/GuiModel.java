package ch.bfh.bti7301.hs2013.gravis.gui.model;

import javax.swing.ButtonModel;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GuiModel implements IGuiModel {

	private IGravisGraph graph;
	
	private ButtonModel newDirGraphModel = null;

	protected GuiModel() {
		// TODO implement

		this.graph = GraphFactory.createGravisGraph();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getGraph()
	 */
	@Override
	public IGravisGraph getGraph() {
		return this.graph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setGraph(ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.IGravisGraph)
	 */
	@Override
	public void setGraph(IGravisGraph graph) {
		this.graph = graph;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setNewDirGraphModel(
	 * javax.swing.ButtonModel)
	 */
	@Override
	public void setNewDirGraphModel(ButtonModel newDirGraphModel) {
		this.newDirGraphModel = newDirGraphModel;
	}

}
