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
 * This interface represents the model in the MVC-pattern. The application state
 * can be changed with the methods declared in this interface.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IAppModel {

	/**
	 * The algorithm calculation state can be handled with this enum constants.
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 * 
	 */
	public static enum CalculationState {
		NOT_CALCULABLE, CALCULABLE, CALCULATED,

		/**
		 * The graph is edited after algorithm calculation and a new calculation
		 * is needed.
		 */
		EDITED_CALCULABLE
	}

	/**
	 * The visualisation playing state can be handled with this enum constants.
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 * 
	 */
	public static enum PlayerState {
		PLAYING, PAUSED,

		/**
		 * This is the default playing state.
		 */
		STOPPED
	}

	public static final String DEFAULT_ALGO_ENTRY = "Algorithmus wählen:";

	/**
	 * Adds an observer to the set of observers for this object, provided that
	 * it is not the same as some observer already in the set. The order in
	 * which notifications will be delivered to multiple observers is not
	 * specified. See the class comment.
	 * 
	 * @param o
	 *            an observer to be added.
	 * @throws NullPointerException
	 *             if the parameter o is null.
	 */
	public abstract void addObserver(Observer o);

	/**
	 * Returns the number of observers of this <tt>Observable</tt> object.
	 * 
	 * @return the number of observers of this object.
	 */
	public abstract int countObservers();

	/**
	 * Deletes an observer from the set of observers of this object. Passing
	 * <CODE>null</CODE> to this method will have no effect.
	 * 
	 * @param o
	 *            the observer to be deleted.
	 */
	public abstract void deleteObserver(Observer o);

	/**
	 * Clears the observer list so that this object no longer has any observers.
	 */
	public abstract void deleteObservers();

	/**
	 * @return algorithm combo model
	 */
	public abstract ComboBoxModel<String> getAlgorithmComboModel();

	/**
	 * 
	 * @return back button model
	 */
	public abstract ButtonModel getBackButtonModel();

	/**
	 * 
	 * @return beginning button model
	 */
	public abstract ButtonModel getBeginningButtonModel();

	/**
	 * 
	 * @return calculation state
	 */
	public CalculationState getCalculationState();

	/**
	 * 
	 * @return player state
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
	 * @return end button model
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
	 * @return forward button model
	 */
	public abstract ButtonModel getForwardButtonModel();

	/**
	 * @return edit graph observable
	 */
	public abstract IEditGraphObservable getGraph();

	/**
	 * @return graph file
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
	 * @return progress bar model
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
	 * Returns a step iterator. The step iterator is null if no calculation is
	 * done before.
	 * 
	 * @return a step iterator
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
	 * @return toggle combo group
	 */
	public abstract ToggleComboGroup getToggleComboGroup();

	/**
	 * 
	 * @return graph document
	 */
	public abstract Document getGraphDocument();

	/**
	 * 
	 * @return algorithm document
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
	 * Tests if this object has changed.
	 * 
	 * @return <code>true</code> if and only if the <code>setChanged</code>
	 *         method has been called more recently than the
	 *         <code>clearChanged</code> method on this object;
	 *         <code>false</code> otherwise.
	 */
	public abstract boolean hasChanged();

	/**
	 * @return {@code true} if a graph file exists
	 */
	public abstract boolean hasGraphFile();

	/**
	 * @return {@code true} if a step iterator exists
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
	 * If this object has changed, as indicated by the <code>hasChanged</code>
	 * method, then notify all of its observers and then call the
	 * <code>clearChanged</code> method to indicate that this object has no
	 * longer changed.
	 * <p>
	 * Each observer has its <code>update</code> method called with two
	 * arguments: this observable object and <code>null</code>.
	 */
	public abstract void notifyObservers();

	/**
	 * Notify all observers of this model.
	 * 
	 * @param graphChanged
	 *            <code>true</code> if the graph instance has changed
	 */
	public abstract void notifyObservers(boolean graphChanged);

	/**
	 * If this object has changed, as indicated by the <code>hasChanged</code>
	 * method, then notify all of its observers and then call the
	 * <code>clearChanged</code> method to indicate that this object has no
	 * longer changed.
	 * <p>
	 * Each observer has its <code>update</code> method called with two
	 * arguments: this observable object and the <code>arg</code> argument.
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
	 * The state if no valid algorithm has been selected in the combo box.
	 * 
	 * @throws BadLocationException
	 */
	public abstract void setNoAlgoSelectedState() throws BadLocationException;

	/**
	 * 
	 * @param graph
	 * @param file
	 * @throws BadLocationException
	 * @throws NullPointerException
	 *             if graph or file is null
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
	 * @throws NullPointerException
	 *             if graphFile is null
	 */
	public abstract void setSaveGraphState(File graphFile)
			throws BadLocationException;

	/**
	 * 
	 * @param stepIterator
	 * @param algoName
	 * @throws BadLocationException
	 * @throws NullPointerException
	 *             if algoName is null
	 */
	public abstract void setCalcDoneState(
			IGravisListIterator<String> stepIterator, String algoName)
			throws BadLocationException;

	public abstract void setStoppedState() throws BadLocationException;

	/**
	 * Two states can be set by parameter {@code enabled}: <br />
	 * {@code true}: sets step panel to initial state and sets step iterator to
	 * first element (if step iterator is not null). <br />
	 * {@code false}: disables step panel (all button models) and clears step
	 * iterator.
	 * 
	 * @param enabled
	 * @throws BadLocationException
	 */
	public abstract void setStepPanelState(boolean enabled)
			throws BadLocationException;

	/**
	 * Precondition: step iterator not null. <br />
	 * Updates the step panel models with the current step iterator values.
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
	 * @throws NullPointerException
	 *             if pickedVertexState is null
	 */
	public abstract void setPickedVertexState(
			PickedState<IVertex> pickedVertexState);

	public abstract void clearPickedVertexState();

}
