package ch.bfh.bti7301.hs2013.gravis.gui.model;

import javax.swing.ButtonModel;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.util.IGravisListIterator;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IVisualizationController;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

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

	/**
	 * @param algoName
	 */
	public abstract void setCurrentAlgorithmName(String algoName);

	/**
	 * 
	 * @return String
	 */
	public abstract String getCurrentAlgorithmName();

	/**
	 * @param visualizationController
	 */
	public abstract void setVisualizationController(
			IVisualizationController visualizationController);

	/**
	 * @param model
	 */
	public abstract void setDeleteEdgeButtonModel(ButtonModel model);

	/**
	 * @param mode
	 */
	public abstract void setEditMode(Mode mode);

	/**
	 * @param model
	 */
	public abstract void setVertexCreateButtonModel(ButtonModel model);

	/**
	 * @param model
	 */
	public abstract void setDeleteVertexButtonModel(ButtonModel model);


}
