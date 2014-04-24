package ch.bfh.ti.gravis.gui.model;

import java.io.File;
import java.util.Observer;

import javax.swing.BoundedRangeModel;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.SpinnerModel;

import ch.bfh.ti.gravis.core.CoreException;
import ch.bfh.ti.gravis.core.graph.GravisGraphIOException;
import ch.bfh.ti.gravis.core.graph.IEditGraphObservable;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IAppModel {

	public static enum CalculationState {
		NOT_CALCULABLE, CALCULABLE, CALCULATED, EDITED_CALCULABLE
	}

	/**
	 * 
	 * @param observer
	 */
	public abstract void addObserver(Observer observer);

	/**
	 * 
	 * @return the number of observers of this object.
	 */
	public abstract int countObservers();

	/**
	 * 
	 * @param o
	 */
	public abstract void deleteObserver(Observer o);

	/**
	 * Clears the observer list so that this object no longer has any observers.
	 */
	public abstract void deleteObservers();

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
	 * @return CalculationState
	 */
	public CalculationState getCalculationState();

	/**
	 * @return delay spinner model
	 */
	public abstract SpinnerModel getDelaySpinnerModel();

	/**
	 * @return delete edge button model
	 */
	public abstract ButtonModel getDeleteEdgeButtonModel();

	/**
	 * @return vertex delete vertex button model
	 */
	public abstract ButtonModel getDeleteVertexButtonModel();

	/**
	 * @return edge properties button model
	 */
	public abstract ButtonModel getEdgePropertiesButtonModel();

	/**
	 * 
	 * @return ButtonModel
	 */
	public abstract ButtonModel getEndButtonModel();

	/**
	 * @return end vertex button model
	 */
	public abstract ButtonModel getEndVertexButtonModel();

	/**
	 * @return file menu model
	 */
	public abstract ButtonModel getFileMenuModel();

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
	 * @return IEditGraphObservable
	 */
	public abstract File getGraphFile();

	/**
	 * @return graph properties button model
	 */
	public abstract ButtonModel getGraphPropertiesButtonModel();

	/**
	 * @return help menu model
	 */
	public abstract ButtonModel getHelpMenuModel();

	/**
	 * @return new calc button model
	 */
	public abstract ButtonModel getNewCalcButtonModel();

	/**
	 * @return new directed graph button model
	 */
	public abstract ButtonModel getNewDirGraphButtonModel();

	/**
	 * @return new undirected graph button model
	 */
	public abstract ButtonModel getNewUndirGraphButtonModel();

	/**
	 * @return new vertex button model
	 */
	public abstract ButtonModel getNewVertexButtonModel();

	/**
	 * @return open graph button model
	 */
	public abstract ButtonModel getOpenGraphButtonModel();

	/**
	 * @return pause button model
	 */
	public abstract ButtonModel getPauseButtonModel();

	/**
	 * @return play button model
	 */
	public abstract ButtonModel getPlayButtonModel();

	/**
	 * 
	 * @return BoundedRangeModel
	 */
	public abstract BoundedRangeModel getProgressBarModel();

	/**
	 * @return save graph as button model
	 */
	public abstract ButtonModel getSaveGraphAsButtonModel();

	/**
	 * @return save graph button model
	 */
	public abstract ButtonModel getSaveGraphButtonModel();

	/**
	 * @return start vertex button model
	 */
	public abstract ButtonModel getStartVertexButtonModel();

	/**
	 * Step iterator is null, if no calculation is done before.
	 * 
	 * @return IGravisListIterator<String>
	 */
	public abstract IGravisListIterator<String> getStepIterator();

	/**
	 * @return stop button model
	 */
	public abstract ButtonModel getStopButtonModel();

	/**
	 * 
	 * @return ToggleComboModel
	 */
	public ToggleComboModel getToggleComboModel();

	/**
	 * @return vertex properties button model
	 */
	public abstract ButtonModel getVertexPropertiesButtonModel();

	/**
	 * 
	 * @return true if and only if the setChanged method has been called more
	 *         recently than the clearChanged method on this object; false
	 *         otherwise.
	 */
	public abstract boolean hasChanged();

	/**
	 * @return boolean
	 */
	public abstract boolean isGraphUnsaved();
	
	/**
	 * @return boolean
	 */
	public abstract boolean hasGraphFile();

	/**
	 * @return boolean
	 */
	public abstract boolean isPlaying();

	/**
	 * If this object has changed, as indicated by the hasChanged method, then
	 * notify all of its observers and then call the clearChanged method to
	 * indicate that this object has no longer changed. Each observer has its
	 * update method called with two arguments: this observable object and null.
	 * In other words, this method is equivalent to: notifyObservers(null)
	 */
	public abstract void notifyObservers();

	/**
	 * @param graphChanged
	 * @param protocolCleared
	 */
	public abstract void notifyObservers(boolean graphChanged,
			boolean protocolCleared);

	/**
	 * 
	 * @param graphChanged
	 * @param protocolCleared
	 * @param protocolMessage
	 */
	public abstract void notifyObservers(boolean graphChanged,
			boolean protocolCleared, String protocolMessage);

	/**
	 * 
	 * @param arg
	 */
	public abstract void notifyObservers(Object arg);

	/**
	 * 
	 * @param visualEditied
	 */
	public abstract void setEditGraphState(boolean visualEditied);

	/**
	 * 
	 * @param mode
	 */
	public void setEditMode(Mode mode);

	/**
	 * @param edgeType
	 * @throws CoreException
	 */
	public abstract void setNewGraphState(EdgeType edgeType)
			throws CoreException;

	/**
	 * 
	 * @param graphFile
	 * @throws GravisGraphIOException
	 * @throws CoreException
	 */
	public abstract void setOpenGraphState(File graphFile)
			throws GravisGraphIOException, CoreException;

	public abstract void setPausedState();

	public abstract void setPlayingState();

	/**
	 * 
	 * @throws GravisGraphIOException
	 */
	public abstract void setSaveGraphState() throws GravisGraphIOException;

	/**
	 * @param graphFile
	 * @throws GravisGraphIOException
	 */
	public abstract void setSaveGraphState(File graphFile)
			throws GravisGraphIOException;

	/**
	 * 
	 * @param stepIterator
	 */
	public abstract void setStepEnabledState(
			IGravisListIterator<String> stepIterator);

	public abstract void setStoppedState();

	/**
	 * 
	 * @param beginning
	 * @param back
	 * @param forward
	 * @param end
	 */
	public abstract void updateStepButtonModels(boolean beginning,
			boolean back, boolean forward, boolean end);

}
