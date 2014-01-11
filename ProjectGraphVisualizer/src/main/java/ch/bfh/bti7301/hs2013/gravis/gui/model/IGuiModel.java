package ch.bfh.bti7301.hs2013.gravis.gui.model;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

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
	 * @param edgeType
	 */
	public abstract void setNewGraphState(EdgeType edgeType);

	/**
	 * @param graph
	 */
	public abstract void setOpenGraphState(IGravisGraph graph);

	/**
	 * @return boolean
	 */
	public abstract boolean hasGraphChanged();


}
