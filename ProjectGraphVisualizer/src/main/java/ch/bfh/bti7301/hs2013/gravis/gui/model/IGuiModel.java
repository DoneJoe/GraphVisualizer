package ch.bfh.bti7301.hs2013.gravis.gui.model;

import javax.swing.ButtonModel;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IGuiModel {

	/**
	 * @return IGravisGraph
	 */
	public abstract IGravisGraph getGraph();
	
	/**
	 * @param graph
	 */
	public abstract void setGraph(IGravisGraph graph);

	/**
	 * @param model
	 */
	public abstract void setNewDirGraphModel(ButtonModel newDirGraphModel);

}
