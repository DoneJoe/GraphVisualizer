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
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;

import ch.bfh.ti.gravis.core.CoreException;
import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.IEditGraphObservable;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;
import static ch.bfh.ti.gravis.gui.model.IAppModel.CalculationState.*;
import static ch.bfh.ti.gravis.gui.model.IAppModel.PlayerState.*;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class AppModel extends Observable implements IAppModel {

	// static final fields:

	private static final EdgeType DEFAULT_EDGE_TYPE = EdgeType.UNDIRECTED;

	private final static String ALGO_DONE_MSG = "Die Animation kann jetzt gestartet werden."
			+ LN + LN;

	// double values in seconds
	private static final double INIT = 1.0, MIN = 0.25, MAX = 10.0,
			STEP_SIZE = 0.25;
	private static final double FACTOR = 1000.0;

	// non final fields:

	private IEditGraphObservable graph;

	private boolean graphUnsaved, working;

	private CalculationState calcState;

	private PlayerState playerState;

	// final fields:

	private final ICore core;

	private final ToggleComboGroup toggleComboGroup;

	private final DefaultComboBoxModel<String> algoComboModel;

	private final SpinnerModel delaySpinnerModel;

	private final BoundedRangeModel progressBarModel;

	private final PlainDocument protocolDocument, graphDocument,
			algorithmDocument;

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
	 * @throws BadLocationException
	 */
	protected AppModel(final ICore core) throws CoreException,
			BadLocationException {
		this.core = core;
		this.working = this.graphUnsaved = false;
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

		// create document models:

		this.graphDocument = new PlainDocument();
		this.algorithmDocument = new PlainDocument();
		this.protocolDocument = new PlainDocument();

		// create other component models:

		this.toggleComboGroup = new ToggleComboGroup();
		this.algoComboModel = new DefaultComboBoxModel<>();
		this.delaySpinnerModel = new SpinnerNumberModel(INIT, MIN, MAX,
				STEP_SIZE);
		this.progressBarModel = new DefaultBoundedRangeModel(0, 0, 0, 0);

		// init timer
		this.timer = new Timer((int) (INIT * FACTOR), null);

		// init graph and edit mode
		this.setNewGraphState(DEFAULT_EDGE_TYPE);
		this.setEditMode(Mode.PICKING);
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
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getAlgorithmDocument()
	 */
	@Override
	public Document getAlgorithmDocument() {
		return this.algorithmDocument;
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
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getGraphDocument()
	 */
	@Override
	public Document getGraphDocument() {
		return this.graphDocument;
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
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getProtocolDocument()
	 */
	@Override
	public Document getProtocolDocument() {
		return this.protocolDocument;
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
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#getToggleComboGroup()
	 */
	@Override
	public ToggleComboGroup getToggleComboGroup() {
		return this.toggleComboGroup;
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
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#isWorking()
	 */
	@Override
	public boolean isWorking() {
		return this.working;
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
	 * boolean, java.lang.String)
	 */
	@Override
	public void notifyObservers(boolean graphChanged) {
		this.notifyObservers(this.createMainWindowModel());
		this.notifyObservers(this.createToolBarModel());
		this.notifyObservers(new VisualizationViewModel(this.graph,
				graphChanged));
		this.notifyObservers(this.createStepModel());
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
	 * <String>, String)
	 */
	@Override
	public void setCalcDoneState(
			final IGravisListIterator<String> stepIterator, final String algoName)
			throws BadLocationException, CoreException {
		// TODO Exception if null

		this.calcState = CALCULATED;
		this.stepIterator = stepIterator;

		this.updateMenuToolbarModels();
		this.updatePopupModels();
		this.setStepPanelState(true);
		this.setEditMode(Mode.PICKING);

		this.algorithmDocument.remove(0, this.algorithmDocument.getLength());
		this.protocolDocument.remove(0, this.protocolDocument.getLength());
		this.algorithmDocument.insertString(0, this.core.getAlgorithmDescription(algoName),
				SimpleAttributeSet.EMPTY);
		this.protocolDocument.insertString(0, ALGO_DONE_MSG,
				SimpleAttributeSet.EMPTY);
	}

	/*
	 * visualEdited does not change the calcState. An empty graph is not
	 * calculable. If calcState is CALCULATED or EDITED_CALCULABLE, then a new
	 * calculation is needed (EDITED_CALCULABLE).
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setEditGraphState(boolean)
	 */
	@Override
	public void setEditGraphState(boolean visualEditied)
			throws BadLocationException {
		this.graphUnsaved = true;
		this.calcState = visualEditied ? this.calcState
				: (this.graph.isEmpty() ? NOT_CALCULABLE
						: (this.calcState == CALCULATED
								|| this.calcState == EDITED_CALCULABLE ? EDITED_CALCULABLE
								: CALCULABLE));

		this.saveGraphButtonModel.setEnabled(!this.working && this.isStopped()
				&& (!this.hasGraphFile() || this.graphUnsaved));
		this.newCalcButtonModel.setEnabled(!this.working && this.isStopped()
				&& this.calcState == EDITED_CALCULABLE);

		if (!visualEditied) {
			this.setStepPanelState(false);
		}

		if (this.calcState == EDITED_CALCULABLE && this.protocolDocument.getLength() != 0) {
			this.protocolDocument.remove(0, this.protocolDocument.getLength());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setEditMode(edu.uci.ics.jung.
	 * visualization.control.ModalGraphMouse.Mode)
	 */
	@Override
	public void setEditMode(final Mode newMode) throws BadLocationException {
		if (this.toggleComboGroup.getMode() != newMode) {
			this.toggleComboGroup.getModeComboBox().setSelectedItem(newMode);
		}

		this.updateMenuToolbarModels();
		this.updatePopupModels();

		if (newMode == Mode.EDITING) {
			this.setStepPanelState(true);
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
		this.updateMenuToolbarModels();
		this.updatePopupModels();
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
	public void setNewGraphState(EdgeType edgeType) throws CoreException,
			BadLocationException {
		if (this.graph == null) {
			this.graph = GraphFactory.createUndirectedSampleGraph();
			this.calcState = CALCULABLE;
		} else {
			this.graph = GraphFactory.createEditGraphObservable(edgeType,
					this.graph.getEditGraphEventListeners());
			this.calcState = NOT_CALCULABLE;
		}

		this.graphUnsaved = false;
		this.graphFile = null;

		this.initAlgorithmComboModel(this.core.getAlgorithmNames(edgeType));
		this.updateMenuToolbarModels();
		this.updatePopupModels();
		this.setStepPanelState(false);
		this.setEditMode(Mode.EDITING);

		this.graphDocument.remove(0, this.graphDocument.getLength());
		this.algorithmDocument.remove(0, this.algorithmDocument.getLength());
		this.protocolDocument.remove(0, this.protocolDocument.getLength());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setNoAlgoSelectedState()
	 */
	@Override
	public void setNoAlgoSelectedState() throws BadLocationException {
		this.calcState = this.graph.isEmpty() ? NOT_CALCULABLE : CALCULABLE;
		this.updateMenuToolbarModels();
		this.updatePopupModels();
		this.setStepPanelState(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setOpenGraphState(IGravisGraph,
	 * File)
	 */
	@Override
	public void setOpenGraphState(IGravisGraph graph, File file)
			throws CoreException, BadLocationException {
		// TODO Exception if null

		this.graph = GraphFactory.createEditGraphObservable(graph,
				this.graph.getEditGraphEventListeners());

		this.calcState = this.graph.isEmpty() ? NOT_CALCULABLE : CALCULABLE;
		this.graphUnsaved = false;
		this.graphFile = file;

		this.initAlgorithmComboModel(this.core.getAlgorithmNames(this.graph
				.getEdgeType()));
		this.updateMenuToolbarModels();
		this.updatePopupModels();
		this.setStepPanelState(false);
		this.setEditMode(Mode.PICKING);

		this.graphDocument.remove(0, this.graphDocument.getLength());
		this.algorithmDocument.remove(0, this.algorithmDocument.getLength());
		this.protocolDocument.remove(0, this.protocolDocument.getLength());
		this.graphDocument.insertString(0, this.graph.getDescription(),
				SimpleAttributeSet.EMPTY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setPausedState()
	 */
	@Override
	public void setPausedState() throws BadLocationException {
		this.playerState = PAUSED;

		this.updateMenuToolbarModels();
		this.updatePopupModels();
		this.updateStepPanelModels();
		this.setEditMode(Mode.PICKING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setPlayingState()
	 */
	@Override
	public void setPlayingState() throws BadLocationException {
		this.playerState = PLAYING;

		this.updateMenuToolbarModels();
		this.updatePopupModels();
		this.updateStepPanelModels();
		this.setEditMode(Mode.PICKING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setSaveGraphState()
	 */
	@Override
	public void setSaveGraphState() throws BadLocationException {
		if (this.hasGraphFile()) {
			this.graphUnsaved = false;

			this.updateMenuToolbarModels();
			this.updatePopupModels();
			this.updateStepPanelModels();
			
			this.graphDocument.remove(0, this.graphDocument.getLength());
			this.graphDocument.insertString(0, this.graph.getDescription(),
					SimpleAttributeSet.EMPTY);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setSaveGraphState(java.io.File)
	 */
	@Override
	public void setSaveGraphState(File graphFile) throws BadLocationException {
		// TODO Exception if null

		this.graphFile = graphFile;
		this.setSaveGraphState();
	}

	@Override
	public void setStepPanelState(final boolean enabled)
			throws BadLocationException {
		if (this.hasStepIterator()) {
			String stepMessage = this.stepIterator.first();
			this.protocolDocument.remove(this.protocolDocument.getLength()
					- stepMessage.length(), stepMessage.length());

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
		this.playButtonModel.setEnabled(enabled && this.hasStepIterator()
				&& !this.working);
		this.pauseButtonModel.setEnabled(false);
		this.stopButtonModel.setEnabled(false);

		this.updateStepButtonModels(false, false,
				enabled && this.hasStepIterator() && !this.working, enabled
						&& this.hasStepIterator() && !this.working);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setStoppedState()
	 */
	@Override
	public void setStoppedState() throws BadLocationException {
		this.playerState = STOPPED;
		this.updateMenuToolbarModels();
		this.updatePopupModels();
		this.setStepPanelState(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setWorkingState(boolean)
	 */
	@Override
	public void setWorkingState(boolean enabled) throws BadLocationException {
		this.working = enabled;
		this.updateMenuToolbarModels();
		this.updatePopupModels();
		this.setStepPanelState(!enabled);
		this.setEditMode(Mode.PICKING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#updateStepPanelModels()
	 */
	@Override
	public void updateStepPanelModels() {
		if (this.hasStepIterator()) {
			boolean previous = this.stepIterator.hasPrevious() && !this.working;
			boolean next = this.stepIterator.hasNext() && !this.working;

			this.updateStepButtonModels(previous, previous, next, next);

			this.playButtonModel.setEnabled(this.stepIterator.hasNext()
					&& !this.isPlaying() && !this.working);
			this.pauseButtonModel.setEnabled(this.stepIterator.hasNext()
					&& this.isPlaying() && !this.working);
			// if not stopped -> enable stop button model
			this.stopButtonModel.setEnabled(!this.isStopped() && !this.working);
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

		return new StepModel(calculated && !this.isPlaying() && !this.working,
				calculated, this.working);
	}

	/**
	 * 
	 * @return ToolBarModel
	 */
	private ToolBarModel createToolBarModel() {
		return new ToolBarModel(this.calcState != NOT_CALCULABLE
				&& this.isStopped() && !this.working,
				this.calcState == EDITED_CALCULABLE && this.isStopped()
						&& !this.working);
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
	 */
	private void updateMenuToolbarModels() {
		this.fileMenuModel.setEnabled(!this.working && this.isStopped());
		this.helpMenuModel.setEnabled(!this.working && this.isStopped());

		this.newDirGraphButtonModel.setEnabled(!this.working
				&& this.isStopped());
		this.newUndirGraphButtonModel.setEnabled(!this.working
				&& this.isStopped());
		this.openGraphButtonModel.setEnabled(!this.working && this.isStopped());
		this.saveGraphButtonModel.setEnabled(!this.working && this.isStopped()
				&& (!this.hasGraphFile() || this.graphUnsaved));
		this.saveGraphAsButtonModel.setEnabled(!this.working
				&& this.isStopped());
		this.graphPropertiesButtonModel.setEnabled(!this.working
				&& this.isStopped());

		this.toggleComboGroup.setToggleComboGroupEnabled(!this.working
				&& this.isStopped());
		this.newCalcButtonModel.setEnabled(!this.working && this.isStopped()
				&& this.calcState == EDITED_CALCULABLE);
	}

	/**
	 * Popups are enabed only when there is not editing mode selected.
	 * 
	 */
	private void updatePopupModels() {
		boolean ok = Mode.EDITING == this.toggleComboGroup.getMode()
				&& !this.working && this.isStopped();

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

		this.beginningButtonModel.setEnabled(beginning && !this.isPlaying()
				&& !this.working);
		this.backButtonModel.setEnabled(back && !this.isPlaying()
				&& !this.working);
		this.forwardButtonModel.setEnabled(forward && !this.isPlaying()
				&& !this.working);
		this.endButtonModel.setEnabled(end && !this.isPlaying()
				&& !this.working);
	}

}
