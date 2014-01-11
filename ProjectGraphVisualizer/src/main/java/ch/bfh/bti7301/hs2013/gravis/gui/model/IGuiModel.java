package ch.bfh.bti7301.hs2013.gravis.gui.model;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IGuiModel {

	public static final String DEFAULT_ALGO_ENTRY = "Algorithmus wählen:";
	
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

	/**
	 * @param algoNames 
	 * @return ToolBarModel
	 */
	public abstract IToolBarModel getToolBarModel(String[] algoNames);

	/**
	 * @param calculateSteps
	 */
	public abstract void setStepIterator(
			IGravisListIterator<String> stepIterator);

	/**
	 * 
	 * @return IGravisListIterator<String>
	 */
	public abstract IGravisListIterator<String> getStepIterator();


}
