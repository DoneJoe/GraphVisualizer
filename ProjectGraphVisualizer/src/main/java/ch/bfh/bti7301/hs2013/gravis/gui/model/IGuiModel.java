package ch.bfh.bti7301.hs2013.gravis.gui.model;

import javax.swing.BoundedRangeModel;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IGuiModel {

	public static final String DEFAULT_ALGO_ENTRY = "Algorithmus wählen:";
	
	/**
	 * @param visualizationController
	 */
	public abstract void setEditingGraphEventListener(
			IEditingGraphEventListener visualizationController);
	
	/**
	 * @param edgeType
	 */
	public abstract void setNewGraphState(EdgeType edgeType);

	/**
	 * @param graph
	 */
	public abstract void setOpenGraphState(IGravisGraph graph);

	/**
	 * 
	 * @param stepIterator
	 */
	public abstract void setStepEnabledState(IGravisListIterator<String> stepIterator);
	
	public abstract void resetStepEnabledState();
	
	/**
	 * @return IGravisGraph
	 */
	public abstract IGravisGraph getGraph();

	/**
	 * 
	 * @return IGravisListIterator<String>
	 */
	public abstract IGravisListIterator<String> getStepIterator();
	
	/**
	 * @return boolean
	 */
	public abstract boolean hasGraphChanged();
	
	/**
	 * 
	 * @param graphChanged
	 */
	public abstract void setGraphChanged(boolean graphChanged);

	/**
	 * 
	 * @return IToolBarModel
	 */
	public abstract IToolBarModel createToolBarModel();

	/**
	 * 
	 * @param mode
	 */
	public void setPopupEditMode(Mode mode);
	
	/**
	 * @param model
	 */
	public abstract void setDeleteEdgeButtonModel(ButtonModel model);

	/**
	 * @param model
	 */
	public abstract void setVertexCreateButtonModel(ButtonModel model);

	/**
	 * @param model
	 */
	public abstract void setDeleteVertexButtonModel(ButtonModel model);

	/**
	 * @return ComboBoxModel<String>
	 */
	public abstract ComboBoxModel<String> getAlgorithmComboModel();
	
	/**
	 * 
	 * @param algoNames
	 */
	public abstract void setAlgorithmComboModel(String[] algoNames);

	/**
	 * 
	 * @param comboModel
	 */
	public abstract void setEditModeComboModel(ComboBoxModel<?> comboModel);
	
	/**
	 * 
	 * @return ComboBoxModel<?>
	 */
	public abstract ComboBoxModel<?> getEditModeComboModel();
	
	/**
	 * @param model
	 */
	public abstract void setBeginningButtonModel(ButtonModel model);
	
	/**
	 * @param model
	 */
	public abstract void setEndButtonModel(ButtonModel model);
	
	/**
	 * @param model
	 */
	public abstract void setBackButtonModel(ButtonModel model);
	
	/**
	 * @param model
	 */
	public abstract void setForwardButtonModel(ButtonModel model);
	
	/**
	 * @param model
	 */
	public abstract void setProgressBarModel(BoundedRangeModel model);
	
	/**
	 * 
	 * @return ButtonModel
	 */
	public abstract ButtonModel getBeginningButtonModel();
	
	/**
	 * 
	 * @return ButtonModel
	 */
	public abstract ButtonModel getEndButtonModel();
	
	/**
	 * 
	 * @return ButtonModel
	 */
	public abstract ButtonModel getBackButtonModel();
	
	/**
	 * 
	 * @return ButtonModel
	 */
	public abstract ButtonModel getForwardButtonModel();
	
	/**
	 * 
	 * @return BoundedRangeModel
	 */
	public abstract BoundedRangeModel getProgressBarModel();
	
}
