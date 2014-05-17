package ch.bfh.ti.gravis.gui.model;

import java.io.File;
import java.util.Observer;

import javax.swing.BoundedRangeModel;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.SpinnerModel;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import ch.bfh.ti.gravis.core.graph.IEditGraphObservable;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.picking.PickedState;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IAppModel {

	public static enum CalculationState {
		NOT_CALCULABLE, CALCULABLE, CALCULATED, EDITED_CALCULABLE
	}

	public static enum PlayerState {
		PLAYING, PAUSED, STOPPED
	}

	public static final String DEFAULT_ALGO_ENTRY = "Algorithmus wählen:";

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
	 * 
	 * @return playerState
	 */
	public PlayerState getPlayerState();

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
	 * @return timer
	 */
	public abstract Timer getTimer();

	/**
	 * 
	 * @return ToggleComboGroup
	 */
	public abstract ToggleComboGroup getToggleComboGroup();

	/**
	 * 
	 * @return Graph document
	 */
	public abstract Document getGraphDocument();

	/**
	 * 
	 * @return Algorithm document
	 */
	public abstract Document getAlgorithmDocument();

	/**
	 * 
	 * @return protocol document
	 */
	public abstract Document getProtocolDocument();

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
	public abstract boolean hasGraphFile();

	/**
	 * @return boolean
	 */
	public abstract boolean hasStepIterator();

	/**
	 * @return boolean
	 */
	public abstract boolean isGraphUnsaved();

	/**
	 * @return boolean
	 */
	public abstract boolean isPlaying();

	/**
	 * @return boolean
	 */
	public abstract boolean isPaused();

	/**
	 * @return boolean
	 */
	public abstract boolean isStopped();

	/**
	 * @return boolean
	 */
	public abstract boolean isWorking();

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
	 */
	public abstract void notifyObservers(boolean graphChanged);

	/**
	 * 
	 * @param arg
	 */
	public abstract void notifyObservers(Object arg);

	/**
	 * 
	 * @param visualEditied
	 * @throws BadLocationException
	 */
	public abstract void setEditGraphState(boolean visualEditied)
			throws BadLocationException;

	/**
	 * 
	 * @param mode
	 * @throws BadLocationException
	 */
	public void setEditMode(Mode mode) throws BadLocationException;

	public abstract void setEndAnimationState();

	/**
	 * @param edgeType
	 * @throws BadLocationException
	 */
	public abstract void setNewGraphState(EdgeType edgeType)
			throws BadLocationException;

	/**
	 * No valid algorithm has been selected in the combo box.
	 * 
	 * @throws BadLocationException
	 */
	public abstract void setNoAlgoSelectedState() throws BadLocationException;

	/**
	 * 
	 * @param graph
	 * @param file
	 * @throws BadLocationException
	 */
	public abstract void setOpenGraphState(IGravisGraph graph, File file)
			throws BadLocationException;

	/**
	 * 
	 * @throws BadLocationException
	 */
	public abstract void setPausedState() throws BadLocationException;

	/**
	 * 
	 * @throws BadLocationException
	 */
	public abstract void setPlayingState() throws BadLocationException;

	/**
	 * 
	 * @throws BadLocationException
	 */
	public abstract void setSaveGraphState() throws BadLocationException;

	/**
	 * @param graphFile
	 * @throws BadLocationException 
	 */
	public abstract void setSaveGraphState(File graphFile) throws BadLocationException;

	/**
	 * 
	 * @param stepIterator
	 * @param algoName 
	 * @throws BadLocationException
	 */
	public abstract void setCalcDoneState(
			IGravisListIterator<String> stepIterator, String algoName)
			throws BadLocationException;

	public abstract void setStoppedState() throws BadLocationException;

	/**
	 * Two states can be set by parameter "enabled": <br />
	 * true: sets step panel to initial state and sets step iterator to first
	 * element (if step iterator is not null) <br />
	 * false: disables step panel (all button models) and clears step iterator
	 * 
	 * @param enabled
	 * @throws BadLocationException
	 */
	public abstract void setStepPanelState(boolean enabled)
			throws BadLocationException;

	/**
	 * Precondition: stepIterator not null <br />
	 * Updates the step panel models with the current stepIterator values.
	 */
	public abstract void updateStepPanelModels();

	/**
	 * @param enabled
	 * @throws BadLocationException
	 */
	public abstract void setWorkingState(boolean enabled)
			throws BadLocationException;

	/**
	 * @param pickedVertexState
	 */
	public abstract void setPickedVertexState(
			PickedState<IVertex> pickedVertexState);

	public abstract void clearPickedVertexState();

}
