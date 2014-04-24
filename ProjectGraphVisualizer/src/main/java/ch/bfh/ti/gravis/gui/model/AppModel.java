package ch.bfh.ti.gravis.gui.model;

import java.io.File;
import java.util.Observable;

import javax.swing.BoundedRangeModel;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import ch.bfh.ti.gravis.core.CoreException;
import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.GravisGraphIOException;
import ch.bfh.ti.gravis.core.graph.IEditGraphObservable;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import static ch.bfh.ti.gravis.gui.model.IAppModel.CalculationState.*;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class AppModel extends Observable implements IAppModel {

	private static final String DEFAULT_ALGO_ENTRY = "Algorithmus wählen:";

	private static final EdgeType DEFAULT_E_TYPE = EdgeType.UNDIRECTED;

	private final ICore core;

	private IEditGraphObservable graph;

	private boolean graphUnsaved, playing;

	private CalculationState calcState;

	private final ToggleComboModel toggleComboModel;

	private final DefaultComboBoxModel<String> algoComboModel;

	private final SpinnerModel delaySpinnerModel;

	private final BoundedRangeModel progressBarModel;

	private final ButtonModel deleteEdgeButtonModel, edgePropertiesButtonModel,
			newVertexButtonModel, deleteVertexButtonModel,
			startVertexButtonModel, endVertexButtonModel,
			vertexPropertiesButtonModel, playButtonModel, pauseButtonModel,
			stopButtonModel, beginningButtonModel, endButtonModel,
			backButtonModel, forwardButtonModel, newDirGraphButtonModel,
			newUndirGraphButtonModel, openGraphButtonModel,
			saveGraphButtonModel, saveGraphAsButtonModel,
			graphPropertiesButtonModel, fileMenuModel, helpMenuModel,
			newCalcButtonModel;

	private IGravisListIterator<String> stepIterator = null;

	private File graphFile = null;

	/**
	 * 
	 * @param core
	 * @throws CoreException
	 */
	protected AppModel(final ICore core) throws CoreException {
		this.core = core;
		this.graphUnsaved = this.playing = false;
		this.calcState = NOT_CALCULABLE;

		// create button models:

		this.deleteEdgeButtonModel = new DefaultButtonModel();
		this.edgePropertiesButtonModel = new DefaultButtonModel();
		this.newVertexButtonModel = new DefaultButtonModel();
		this.deleteVertexButtonModel = new DefaultButtonModel();
		this.startVertexButtonModel = new DefaultButtonModel();
		this.endVertexButtonModel = new DefaultButtonModel();
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

		// create other component models:

		this.toggleComboModel = new ToggleComboModel();
		this.algoComboModel = new DefaultComboBoxModel<>();
		this.delaySpinnerModel = new SpinnerNumberModel();
		this.progressBarModel = new DefaultBoundedRangeModel(0, 0, 0, 0);

		// TODO init SpinnerModel (int, editor) , progressBarModel -> anpassung
		// disableStepPanel

		this.setNewGraphState(DEFAULT_E_TYPE);
	}

	/**
	 * 
	 * @return StepModel
	 */
	private StepModel createStepModel() {
		boolean enabled = this.calcState == CALCULATED;

		return new StepModel(this.progressBarModel.getValue(),
				this.progressBarModel.getMaximum(), enabled, enabled);
	}

	/**
	 * 
	 * @return ToolBarModel
	 */
	private ToolBarModel createToolBarModel() {
		return new ToolBarModel(this.calcState != NOT_CALCULABLE
				&& !this.playing, this.calcState == EDITED_CALCULABLE
				&& !this.playing, !this.playing);
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
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#isGraphUnsaved()
	 */
	@Override
	public boolean isGraphUnsaved() {
		return this.graphUnsaved;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#isPlaying()
	 */
	@Override
	public boolean isPlaying() {
		return this.playing;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setEditGraphState(boolean)
	 */
	@Override
	public void setEditGraphState(boolean visualEditied) {
		if (!this.playing) {
			this.graphUnsaved = true;
			this.calcState = visualEditied ? this.calcState :
					(this.graph.isEmpty() ? NOT_CALCULABLE
					: (this.calcState == CALCULATED ? EDITED_CALCULABLE
							: CALCULABLE));

			this.setMenuAndToolbarEnabled(true);
			this.setPopupsEnabled(true);
			
			if (!visualEditied) {
				this.setStepPanelEnabled(false);
			}
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
		if (!this.playing) {
			if (this.stepIterator != null && mode == Mode.EDITING) {
				this.stepIterator.first();
			}

			this.toggleComboModel.setSelectedItem(mode);
			this.setPopupsEnabled(true);
		}
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
		if (!this.playing) {
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
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setOpenGraphState(java.io.File)
	 */
	@Override
	public void setOpenGraphState(File graphFile)
			throws GravisGraphIOException, CoreException {
		// TODO Exception if null

		if (!this.playing) {
			this.graph = GraphFactory.createEditGraphObservable(
					this.core.loadGraph(graphFile),
					this.graph.getEditGraphEventListeners());
			this.calcState = this.graph.isEmpty() ? NOT_CALCULABLE : CALCULABLE;
			this.graphUnsaved = false;
			this.graphFile = graphFile;

			this.initAlgorithmComboModel(this.core.getAlgorithmNames(this.graph
					.getEdgeType()));
			this.setMenuAndToolbarEnabled(true);
			this.setPopupsEnabled(true);
			this.setStepPanelEnabled(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setPausedState()
	 */
	@Override
	public void setPausedState() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setPlayingState()
	 */
	@Override
	public void setPlayingState() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setSaveGraphState()
	 */
	@Override
	public void setSaveGraphState() throws GravisGraphIOException {
		if (!this.playing && this.graphFile != null) {
			this.graphUnsaved = false;

			this.setMenuAndToolbarEnabled(true);
			this.setStepPanelEnabled(true);

			this.core.saveGraph(this.graph, this.graphFile);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setSaveGraphState(java.io.File)
	 */
	@Override
	public void setSaveGraphState(File graphFile) throws GravisGraphIOException {
		// TODO Exception if null

		if (!this.playing) {
			this.graphFile = graphFile;
			this.setSaveGraphState();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.gui.model.IAppModel#setStepEnabledState(ch.bfh.ti.gravis
	 * .core.util.IGravisListIterator)
	 */
	@Override
	public void setStepEnabledState(IGravisListIterator<String> stepIterator) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#setStoppedState()
	 */
	@Override
	public void setStoppedState() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#updateStepButtonModels(boolean,
	 * boolean, boolean, boolean)
	 */
	@Override
	public void updateStepButtonModels(boolean beginning, boolean back,
			boolean forward, boolean end) {

		if (!this.playing) {
			this.beginningButtonModel.setEnabled(beginning);
			this.backButtonModel.setEnabled(back);
			this.forwardButtonModel.setEnabled(forward);
			this.endButtonModel.setEnabled(end);
		}
	}

	/**
	 * Initializes algorithm combo model with new values and removes old values.
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
	 * All buttons models are only enabled when playing is false. <br />
	 * The save graph button model is only enabled when: <br />
	 * there is no existing graphFile or the graph is unsaved. <br />
	 * The newCalcButtonModel is only enabled when edited calculation is
	 * possible.
	 * 
	 * @param enabled
	 */
	private void setMenuAndToolbarEnabled(final boolean enabled) {
		this.fileMenuModel.setEnabled(enabled && !this.playing);
		this.helpMenuModel.setEnabled(enabled && !this.playing);

		this.newDirGraphButtonModel.setEnabled(enabled && !this.playing);
		this.newUndirGraphButtonModel.setEnabled(enabled && !this.playing);
		this.openGraphButtonModel.setEnabled(enabled && !this.playing);
		this.saveGraphButtonModel.setEnabled(enabled && !this.playing
				&& (this.graphFile == null || this.graphUnsaved));
		this.saveGraphAsButtonModel.setEnabled(enabled && !this.playing);
		this.graphPropertiesButtonModel.setEnabled(enabled && !this.playing);

		this.toggleComboModel.setToggleModelsEnabled(enabled && !this.playing);
		this.newCalcButtonModel.setEnabled(enabled && !this.playing
				&& this.calcState == EDITED_CALCULABLE);
	}

	/**
	 * Popups are enabed only when there is not editing mode selected and not
	 * playing.
	 * 
	 * @param enabled
	 */
	private void setPopupsEnabled(final boolean enabled) {
		boolean ok = Mode.EDITING == this.toggleComboModel.getMode() && enabled
				&& !this.playing;

		this.edgePropertiesButtonModel.setEnabled(ok);
		this.deleteEdgeButtonModel.setEnabled(ok);

		this.newVertexButtonModel.setEnabled(ok);
		this.startVertexButtonModel.setEnabled(ok);
		this.endVertexButtonModel.setEnabled(ok);
		this.vertexPropertiesButtonModel.setEnabled(ok);
		this.deleteVertexButtonModel.setEnabled(ok);
	}

	/**
	 * Precondition: not playing and (disabled or step iterator is not null) <br />
	 * 
	 * Two states can be set by parameter "enabled": <br />
	 * true: enables step panel to initial state and sets the step iterator to
	 * first element <br />
	 * false: disables step panel (all button models) and clears step iterator
	 * 
	 * @param enabled
	 */
	private void setStepPanelEnabled(final boolean enabled) {
		if (!this.playing && (!enabled || this.stepIterator != null)) {
			if (this.stepIterator != null) {
				this.stepIterator.first();

				if (!enabled) {
					this.stepIterator = null;
				}
			}

			this.progressBarModel.setMinimum(0);
			this.progressBarModel.setMaximum(0);
			this.progressBarModel.setValue(0);
			this.playButtonModel.setEnabled(enabled);
			this.pauseButtonModel.setEnabled(false);
			this.stopButtonModel.setEnabled(false);

			this.updateStepButtonModels(enabled, enabled, false, false);
		}
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

	/**
	 * @return MainWindowModel
	 */
	private MainWindowModel createMainWindowModel() {
		return new MainWindowModel(this.hasGraphFile() ? this.graphFile.getAbsolutePath() : "", 
				this.hasGraphFile(), this.graphUnsaved);
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.gui.model.IAppModel#hasGraphFile()
	 */
	@Override
	public boolean hasGraphFile() {
		return this.graphFile != null;
	}

}
