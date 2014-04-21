package ch.bfh.ti.gravis.gui.model;

import javax.swing.BoundedRangeModel;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;

import ch.bfh.ti.gravis.core.graph.IEditGraphObservable;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IAppModel {

	public static enum CalculationState { 
		NOT_CALCULABLE, CALCULABLE, CALCULATED
	}
	
	public static final String DEFAULT_ALGO_ENTRY = "Algorithmus wählen:";
	
	/**
	 * 
	 * @return ToggleComboModel
	 */
	public ToggleComboModel getToggleComboModel();
	
	/**
	 * @return StepModel
	 */
	public abstract StepModel createStepModel();

	/**
	 * 
	 * @return ToolBarModel
	 */
	public abstract ToolBarModel createToolBarModel();

	/**
	 * @return ComboBoxModel<String>
	 */
	public abstract ComboBoxModel<String> getAlgorithmComboModel();
	
	/**
	 * 
	 * @return ButtonModel
	 */
	public abstract ButtonModel getBackButtonModel();
	
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
	public abstract ButtonModel getForwardButtonModel();
	
	/**
	 * @return IEditGraphObservable
	 */
	public abstract IEditGraphObservable getGraph();
	
	/**
	 * 
	 * @return BoundedRangeModel
	 */
	public abstract BoundedRangeModel getProgressBarModel();
	
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
	 * @return boolean
	 */
	public abstract boolean isGraphUnsaved();
	
	public abstract void resetStepEnabledState();
	
	/**
	 * 
	 * @param algoNames
	 */
	public abstract void setAlgorithmComboModel(String[] algoNames);

	/**
	 * @param model
	 */
	public abstract void setBackButtonModel(ButtonModel model);

	/**
	 * @param model
	 */
	public abstract void setBeginningButtonModel(ButtonModel model);

	/**
	 * @param model
	 */
	public abstract void setDeleteEdgeButtonModel(ButtonModel model);
	
	/**
	 * @param model
	 */
	public abstract void setDeleteVertexButtonModel(ButtonModel model);

	/**
	 * @param model
	 */
	public abstract void setEndButtonModel(ButtonModel model);
	
	/**
	 * @param model
	 */
	public abstract void setForwardButtonModel(ButtonModel model);
	
	/**
	 * 
	 * @param graphChanged
	 */
	public abstract void setGraphChanged(boolean graphChanged);
	
	/**
	 * 
	 * @param graphUnsaved
	 */
	public abstract void setGraphUnsaved(boolean graphUnsaved);
	
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
	 * @param mode
	 */
	public void setPopupEditMode(Mode mode);
	
	/**
	 * @param model
	 */
	public abstract void setProgressBarModel(BoundedRangeModel model);
	
	/**
	 * 
	 * @param stepIterator
	 */
	public abstract void setStepEnabledState(IGravisListIterator<String> stepIterator);
	
	/**
	 * @param model
	 */
	public abstract void setVertexCreateButtonModel(ButtonModel model);
	
	/**
	 * @param beginning
	 * @param back
	 * @param forward
	 * @param end
	 */
	public abstract void updateStepButtonModels(boolean beginning, boolean back,
			boolean forward, boolean end);

	
}
