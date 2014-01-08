package ch.bfh.bti7301.hs2013.gravis.gui.model;

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

}
