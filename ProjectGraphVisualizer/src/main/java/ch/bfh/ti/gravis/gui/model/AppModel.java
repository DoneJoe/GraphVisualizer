package ch.bfh.ti.gravis.gui.model;

import java.io.File;
import java.util.Observable;

import javax.swing.BoundedRangeModel;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

import ch.bfh.ti.gravis.core.CoreException;
import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.IEditGraphObservable;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import static ch.bfh.ti.gravis.gui.model.IAppModel.CalculationState.*;
import static ch.bfh.ti.gravis.gui.model.IAppModel.PlayerState.*;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class AppModel extends Observable implements IAppModel {

	// static final fields:
	
	private static final EdgeType DEFAULT_EDGE_TYPE = EdgeType.UNDIRECTED;
	
	// double values in seconds 
	private static final double INIT = 1.0, MIN = 0.25, MAX = 10.0, STEP_SIZE = 0.25;
	private static final double FACTOR = 1000.0;
			
	// non final fields:

	private IEditGraphObservable graph;

	private boolean graphUnsaved;

	private CalculationState calcState;

	private PlayerState playerState;

	// final fields:

	private final ICore core;

	private final ToggleComboModel toggleComboModel;

	private final DefaultComboBoxModel<String> algoComboModel;

	private final SpinnerModel delaySpinnerModel;

	private final BoundedRangeModel progressBarModel;

	private final ButtonModel deleteEdgeButtonModel, edgePropertiesButtonModel,
			newVertexButtonModel, deleteVertexButtonModel,
			vertexPropertiesButtonModel, playButtonModel, pauseButtonModel,
			stopButtonModel, beginningButtonModel, endButtonModel,
			backButtonModel, forwardButtonModel, newDirGraphButtonModel,
			newUndirGraphButtonModel, openGraphButtonModel,
			saveGraphButtonModel, saveGraphAsButtonModel,
			graphPropertiesButtonModel, fileMenuModel, helpMenuModel,
			newCalcButtonModel;

	private final JToggleButton.ToggleButtonModel startVertexButtonModel,
			endVertexButtonModel;

	private final Timer timer;

	// null pointer fields:

	private IGravisListIterator<String> stepIterator = null;

	private File graphFile = null;

	/**
	 * 
	 * @param core
	 * @throws CoreException
	 */
	protected AppModel(final ICore core) throws CoreException {
		this.core = core;
		this.graphUnsaved = false;
		this.calcState = NOT_CALCULABLE;
		this.playerState = STOPPED;

		// create default button models:

		this.deleteEdgeButtonModel = new DefaultButtonModel();
		this.edgePropertiesButtonModel = new DefaultButtonModel();
		this.newVertexButtonModel = new DefaultButtonModel();
		this.deleteVertexButtonModel = new DefaultButtonModel();
		this.vertexPropertiesButtonModel = new DefaultButtonModel();
		this.playButtonModel = new DefaultButtonModel();
		this.pauseButtonModel = new DefaultButtonModel();
		this.stopButtonModel = new DefaultButtonModel();
		this.beginningButtonModel = new DefaultButtonModel();
		this.endButtonModel = new DefaultButtonModel();
		this.backButtonModel = new DefaultButtonModel();
		this.forwardButtonModel = new DefaultButtonModel();
		this.newDirGraphButtonModel = new DefaultButtonModel();
		this.newUndirGraphButtonModel = new DefaultButtonModel();
		this.openGraphButtonModel = new DefaultButtonModel();
		this.saveGraphButtonModel = new DefaultButtonModel();
		this.saveGraphAsButtonModel = new DefaultButtonModel();
		this.graphPropertiesButtonModel = new DefaultButtonModel();
		this.fileMenuModel = new DefaultButtonModel();
		this.helpMenuModel = new DefaultButtonModel();
		this.newCalcButtonModel = new DefaultButtonModel();

		// create toggle button models:

		this.startVertexButtonModel = new JToggleButton.ToggleButtonModel();
		this.endVertexButtonModel = new JToggleButton.ToggleButtonModel();

		// create other component models:

		this.toggleComboModel = new ToggleComboModel();
		this.algoComboModel = new DefaultComboBoxModel<>();
		this.delaySpinnerModel = new SpinnerNumberModel(INIT, MIN, MAX, STEP_SIZE);
		this.progressBarModel = new DefaultBoundedRangeModel(0, 0, 0, 0);

		// init timer
		this.timer = new Timer((int) (INIT * FACTOR), null);

		// init graph
		this.setNewGraphState(DEFAULT_EDGE_TYPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getAlgorithmComboModel()
	 */
	@Override
	public ComboBoxModel<String> getAlgorithmComboModel() {
		return this.algoComboModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getBackButtonModel()
	 */
	@Override
	public ButtonModel getBackButtonModel() {
		return this.backButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getBeginningButtonModel()
	 */
	@Override
	public ButtonModel getBeginningButtonModel() {
		return this.beginningButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getCalculationState()
	 */
	@Override
	public CalculationState getCalculationState() {
		return this.calcState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getDelaySpinnerModel()
	 */
	@Override
	public SpinnerModel getDelaySpinnerModel() {
		return this.delaySpinnerModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getDeleteEdgeButtonModel()
	 */
	@Override
	public ButtonModel getDeleteEdgeButtonModel() {
		return this.deleteEdgeButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getDeleteVertexButtonModel()
	 */
	@Override
	public ButtonModel getDeleteVertexButtonModel() {
		return this.deleteVertexButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getEdgePropertiesButtonModel()
	 */
	@Override
	public ButtonModel getEdgePropertiesButtonModel() {
		return this.edgePropertiesButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getEndButtonModel()
	 */
	@Override
	public ButtonModel getEndButtonModel() {
		return this.endButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getEndVertexButtonModel()
	 */
	@Override
	public ButtonModel getEndVertexButtonModel() {
		return this.endVertexButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getFileMenuModel()
	 */
	@Override
	public ButtonModel getFileMenuModel() {
		return this.fileMenuModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getForwardButtonModel()
	 */
	@Override
	public ButtonModel getForwardButtonModel() {
		return this.forwardButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getGraph()
	 */
	@Override
	public IEditGraphObservable getGraph() {
		return this.graph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getGraphFile()
	 */
	@Override
	public File getGraphFile() {
		return this.graphFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getGraphPropertiesButtonModel()
	 */
	@Override
	public ButtonModel getGraphPropertiesButtonModel() {
		return this.graphPropertiesButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getHelpMenuModel()
	 */
	@Override
	public ButtonModel getHelpMenuModel() {
		return this.helpMenuModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getNewCalcButtonModel()
	 */
	@Override
	public ButtonModel getNewCalcButtonModel() {
		return this.newCalcButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getNewDirGraphButtonModel()
	 */
	@Override
	public ButtonModel getNewDirGraphButtonModel() {
		return this.newDirGraphButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getNewUndirGraphButtonModel()
	 */
	@Override
	public ButtonModel getNewUndirGraphButtonModel() {
		return this.newUndirGraphButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getNewVertexButtonModel()
	 */
	@Override
	public ButtonModel getNewVertexButtonModel() {
		return this.newVertexButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getOpenGraphButtonModel()
	 */
	@Override
	public ButtonModel getOpenGraphButtonModel() {
		return this.openGraphButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getPauseButtonModel()
	 */
	@Override
	public ButtonModel getPauseButtonModel() {
		return this.pauseButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getPlayButtonModel()
	 */
	@Override
	public ButtonModel getPlayButtonModel() {
		return this.playButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getPlayerState()
	 */
	@Override
	public PlayerState getPlayerState() {
		return this.playerState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getProgressBarModel()
	 */
	@Override
	public BoundedRangeModel getProgressBarModel() {
		return this.progressBarModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getSaveGraphAsButtonModel()
	 */
	@Override
	public ButtonModel getSaveGraphAsButtonModel() {
		return this.saveGraphAsButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getSaveGraphButtonModel()
	 */
	@Override
	public ButtonModel getSaveGraphButtonModel() {
		return this.saveGraphButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getStartVertexButtonModel()
	 */
	@Override
	public ButtonModel getStartVertexButtonModel() {
		return this.startVertexButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getStepIterator()
	 */
	@Override
	public IGravisListIterator<String> getStepIterator() {
		return this.stepIterator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getStopButtonModel()
	 */
	@Override
	public ButtonModel getStopButtonModel() {
		return this.stopButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getTimer()
	 */
	@Override
	public Timer getTimer() {
		return this.timer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getToggleComboModel()
	 */
	@Override
	public ToggleComboModel getToggleComboModel() {
		return this.toggleComboModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.gui.model.IAppModel#getVertexPropertiesButtonModel()
	 */
	@Override
	public ButtonModel getVertexPropertiesButtonModel() {
		return this.vertexPropertiesButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#hasGraphFile()
	 */
	@Override
	public boolean hasGraphFile() {
		return this.graphFile != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#hasStepIterator()
	 */
	@Override
	public boolean hasStepIterator() {
		return this.stepIterator != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#isGraphUnsaved()
	 */
	@Override
	public boolean isGraphUnsaved() {
		return this.graphUnsaved;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#isPaused()
	 */
	@Override
	public boolean isPaused() {
		return this.playerState == PAUSED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#isPlaying()
	 */
	@Override
	public boolean isPlaying() {
		return this.playerState == PLAYING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#isStopped()
	 */
	@Override
	public boolean isStopped() {
		return this.playerState == STOPPED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observable#notifyObservers()
	 */
	@Override
	public void notifyObservers() {
		this.setChanged();
		super.notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#notifyObservers(boolean,
	 * boolean)
	 */
	@Override
	public void notifyObservers(boolean graphChanged, boolean protocolCleared) {
		this.notifyObservers(graphChanged, protocolCleared, "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#notifyObservers(boolean,
	 * boolean, java.lang.String)
	 */
	@Override
	public void notifyObservers(boolean graphChanged, boolean protocolCleared,
			String protocolMessage) {

		this.notifyObservers(this.createMainWindowModel());
		this.notifyObservers(this.createToolBarModel());
		this.notifyObservers(new VisualizationViewModel(this.graph,
				graphChanged));
		this.notifyObservers(this.createStepModel());
		this.notifyObservers(new ProtocolModel(protocolMessage, protocolCleared));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observable#notifyObservers(java.lang.Object)
	 */
	@Override
	public void notifyObservers(Object arg) {
		this.setChanged();
		super.notifyObservers(arg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.gui.model.IAppModel#setCalcDoneState(IGravisListIterator
	 * <String>)
	 */
	@Override
	public void setCalcDoneState(final IGravisListIterator<String> stepIterator) {
		// TODO Exception if null

		this.calcState = CALCULATED;
		this.stepIterator = stepIterator;

		this.setMenuAndToolbarEnabled(true);
		this.setPopupsEnabled(true);
		this.setStepPanelEnabled(true);
		this.setEditMode(Mode.PICKING);
	}

	/*
	 * visualEdited does not change the calcState. An empty graph is not
	 * calculable. If calcState is CALCULATED or EDITED_CALCULABLE, then a new
	 * calculation is needed (EDITED_CALCULABLE).
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setEditGraphState(boolean)
	 */
	@Override
	public void setEditGraphState(boolean visualEditied) {
		this.graphUnsaved = true;
		this.calcState = visualEditied ? this.calcState
				: (this.graph.isEmpty() ? NOT_CALCULABLE
						: (this.calcState == CALCULATED
								|| this.calcState == EDITED_CALCULABLE ? EDITED_CALCULABLE
								: CALCULABLE));

		this.setMenuAndToolbarEnabled(true);
		this.setPopupsEnabled(true);

		if (!visualEditied) {
			this.setStepPanelEnabled(false);
		} else {
			this.updateStepPanelModels();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setEditMode(edu.uci.ics.jung.
	 * visualization.control.ModalGraphMouse.Mode)
	 */
	@Override
	public void setEditMode(final Mode mode) {
		this.toggleComboModel.setSelectedItem(mode);
		this.setMenuAndToolbarEnabled(true);
		this.setPopupsEnabled(true);

		if (mode == Mode.EDITING) {
			this.setStepPanelEnabled(true);
		} else {
			this.updateStepPanelModels();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setEndAnimationState()
	 */
	@Override
	public void setEndAnimationState() {
		this.playerState = STOPPED;
		this.setMenuAndToolbarEnabled(true);
		this.setPopupsEnabled(true);
		this.updateStepPanelModels();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.gui.model.IAppModel#setNewGraphState(edu.uci.ics.jung
	 * .graph.util.EdgeType)
	 */
	@Override
	public void setNewGraphState(EdgeType edgeType) throws CoreException {
		if (this.graph == null) {
			this.graph = GraphFactory.createEditGraphObservable(edgeType);
		} else {
			this.graph = GraphFactory.createEditGraphObservable(edgeType,
					this.graph.getEditGraphEventListeners());
		}

		this.calcState = NOT_CALCULABLE;
		this.graphUnsaved = false;
		this.graphFile = null;

		this.initAlgorithmComboModel(this.core.getAlgorithmNames(edgeType));
		this.setMenuAndToolbarEnabled(true);
		this.setPopupsEnabled(true);
		this.setStepPanelEnabled(false);
		this.setEditMode(Mode.EDITING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setNoAlgoSelectedState()
	 */
	@Override
	public void setNoAlgoSelectedState() {
		this.calcState = this.graph.isEmpty() ? NOT_CALCULABLE : CALCULABLE;
		this.setMenuAndToolbarEnabled(true);
		this.setPopupsEnabled(true);
		this.setStepPanelEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setOpenGraphState(IGravisGraph,
	 * File)
	 */
	@Override
	public void setOpenGraphState(IGravisGraph graph, File file)
			throws CoreException {
		// TODO Exception if null

		this.graph = GraphFactory.createEditGraphObservable(graph,
				this.graph.getEditGraphEventListeners());

		this.calcState = this.graph.isEmpty() ? NOT_CALCULABLE : CALCULABLE;
		this.graphUnsaved = false;
		this.graphFile = file;

		this.initAlgorithmComboModel(this.core.getAlgorithmNames(this.graph
				.getEdgeType()));
		this.setMenuAndToolbarEnabled(true);
		this.setPopupsEnabled(true);
		this.setStepPanelEnabled(false);
		this.setEditMode(Mode.PICKING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setPausedState()
	 */
	@Override
	public void setPausedState() {
		this.playerState = PAUSED;

		this.setMenuAndToolbarEnabled(false);
		this.setPopupsEnabled(false);
		this.updateStepPanelModels();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setPlayingState()
	 */
	@Override
	public void setPlayingState() {
		this.playerState = PLAYING;

		this.setMenuAndToolbarEnabled(false);
		this.setPopupsEnabled(false);
		this.updateStepPanelModels();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setSaveGraphState()
	 */
	@Override
	public void setSaveGraphState() {
		if (this.hasGraphFile()) {
			this.graphUnsaved = false;

			this.setMenuAndToolbarEnabled(true);
			this.setStepPanelEnabled(true);
			this.updateStepPanelModels();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setSaveGraphState(java.io.File)
	 */
	@Override
	public void setSaveGraphState(File graphFile) {
		// TODO Exception if null

		this.graphFile = graphFile;
		this.setSaveGraphState();
	}

	@Override
	public void setStepPanelEnabled(final boolean enabled) {
		if (this.hasStepIterator()) {
			this.stepIterator.first();

			if (enabled) {
				// enabled and step iterator
				this.progressBarModel.setMaximum(this.stepIterator.size());
			} else {
				// disabled and step iterator
				this.stepIterator = null;
				this.progressBarModel.setMaximum(0);
			}
		} else {
			// disabled and no step iterator
			this.progressBarModel.setMaximum(0);
		}

		this.progressBarModel.setMinimum(0);
		this.progressBarModel.setValue(0);
		this.playButtonModel.setEnabled(enabled && this.hasStepIterator());
		this.pauseButtonModel.setEnabled(false);
		this.stopButtonModel.setEnabled(false);

		this.updateStepButtonModels(false, false,
				enabled && this.hasStepIterator(),
				enabled && this.hasStepIterator());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setStoppedState()
	 */
	@Override
	public void setStoppedState() {
		this.playerState = STOPPED;
		this.setMenuAndToolbarEnabled(true);
		this.setPopupsEnabled(true);
		this.setStepPanelEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#updateStepPanelModels()
	 */
	@Override
	public void updateStepPanelModels() {
		if (this.hasStepIterator()) {
			this.updateStepButtonModels(this.stepIterator.hasPrevious(),
					this.stepIterator.hasPrevious(),
					this.stepIterator.hasNext(), this.stepIterator.hasNext());

			this.playButtonModel.setEnabled(this.stepIterator.hasNext()
					&& !this.isPlaying());
			this.pauseButtonModel.setEnabled(this.stepIterator.hasNext()
					&& this.isPlaying());
			this.stopButtonModel.setEnabled(this.stepIterator.hasNext()
					&& !this.isStopped());
		}
	}

	/**
	 * @return MainWindowModel
	 */
	private MainWindowModel createMainWindowModel() {
		return new MainWindowModel(
				this.hasGraphFile() ? this.graphFile.getAbsolutePath() : "",
				this.hasGraphFile(), this.graphUnsaved);
	}

	/**
	 * 
	 * @return StepModel
	 */
	private StepModel createStepModel() {
		boolean calculated = this.calcState == CALCULATED;

		return new StepModel(this.progressBarModel.getValue(),
				this.progressBarModel.getMaximum(), calculated
						&& !this.isPlaying(), calculated);
	}

	/**
	 * 
	 * @return ToolBarModel
	 */
	private ToolBarModel createToolBarModel() {
		return new ToolBarModel(this.calcState != NOT_CALCULABLE
				&& this.playerState == STOPPED,
				this.calcState == EDITED_CALCULABLE
						&& this.playerState == STOPPED,
				this.playerState == STOPPED);
	}

	/**
	 * Removes old values and initializes algorithm combo model with new values.
	 * 
	 * @param algoNames
	 */
	private void initAlgorithmComboModel(final String[] algoNames) {
		this.algoComboModel.removeAllElements();
		this.algoComboModel.addElement(DEFAULT_ALGO_ENTRY);

		for (String algoName : algoNames) {
			this.algoComboModel.addElement(algoName);
		}

		this.algoComboModel.setSelectedItem(DEFAULT_ALGO_ENTRY);
	}

	/**
	 * The save graph button model is only enabled when: <br />
	 * there is no existing graphFile or the graph is unsaved. <br />
	 * The newCalcButtonModel is only enabled when edited calculation is
	 * possible.
	 * 
	 * @param enabled
	 */
	private void setMenuAndToolbarEnabled(final boolean enabled) {
		this.fileMenuModel.setEnabled(enabled && this.isStopped());
		this.helpMenuModel.setEnabled(enabled && this.isStopped());

		this.newDirGraphButtonModel.setEnabled(enabled && this.isStopped());
		this.newUndirGraphButtonModel.setEnabled(enabled && this.isStopped());
		this.openGraphButtonModel.setEnabled(enabled && this.isStopped());
		this.saveGraphButtonModel.setEnabled(enabled && this.isStopped()
				&& (!this.hasGraphFile() || this.graphUnsaved));
		this.saveGraphAsButtonModel.setEnabled(enabled && this.isStopped());
		this.graphPropertiesButtonModel
				.setEnabled(enabled && this.isStopped());

		this.toggleComboModel.setToggleModelsEnabled(enabled
				&& this.isStopped());
		this.newCalcButtonModel.setEnabled(enabled && this.isStopped()
				&& this.calcState == EDITED_CALCULABLE);
	}

	/**
	 * Popups are enabed only when there is not editing mode selected.
	 * 
	 * @param enabled
	 */
	private void setPopupsEnabled(final boolean enabled) {
		boolean ok = Mode.EDITING == this.toggleComboModel.getMode() && enabled
				&& this.isStopped();

		this.edgePropertiesButtonModel.setEnabled(ok);
		this.deleteEdgeButtonModel.setEnabled(ok);

		this.newVertexButtonModel.setEnabled(ok);
		this.startVertexButtonModel.setEnabled(ok);
		this.endVertexButtonModel.setEnabled(ok);
		this.vertexPropertiesButtonModel.setEnabled(ok);
		this.deleteVertexButtonModel.setEnabled(ok);
	}

	/**
	 * 
	 * @param beginning
	 * @param back
	 * @param forward
	 * @param end
	 */
	private void updateStepButtonModels(boolean beginning, boolean back,
			boolean forward, boolean end) {

		this.beginningButtonModel.setEnabled(beginning && !this.isPlaying());
		this.backButtonModel.setEnabled(back && !this.isPlaying());
		this.forwardButtonModel.setEnabled(forward && !this.isPlaying());
		this.endButtonModel.setEnabled(end && !this.isPlaying());
	}

}
