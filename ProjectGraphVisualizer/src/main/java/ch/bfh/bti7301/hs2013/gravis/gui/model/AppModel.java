package ch.bfh.bti7301.hs2013.gravis.gui.model;

import java.io.File;

import javax.swing.BoundedRangeModel;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;

import ch.bfh.bti7301.hs2013.gravis.core.CoreException;
import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphObservable;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class AppModel implements IAppModel {

	private IEditGraphObservable graph;

	private boolean graphItemsEdited, graphUnsaved, playing, modeToggleChanging,
	modeComboChanging;

	private CalculationState calcState;

	private DefaultComboBoxModel<String> algoComboModel;

	private JToggleButton.ToggleButtonModel pickingToggleModel,
			editingToggleModel, transformingToggleModel;

	private ComboBoxModel<?> editModeComboModel = null;
	// TODO in VisualPanel setzen,in ToolbarPanel abrufen, keine Ruekgabe von
	// VisualPanel

	private ButtonModel deleteEdgeButtonModel = null,
			vertexCreateButtonModel = null, deleteVertexButtonModel = null,
			beginningButtonModel = null, endButtonModel = null,
			backButtonModel = null, forwardButtonModel = null;

	private BoundedRangeModel progressBarModel = null;

	private IGravisListIterator<String> stepIterator = null;

	private File graphFile = null;

	/**
	 * 
	 * @param core
	 * @throws CoreException
	 */
	protected AppModel(final ICore core) throws CoreException {
		// creates an empty undirected graph
		this.graph = GraphFactory.createEditGraphObservable(EdgeType.UNDIRECTED);
		this.graphUnsaved = this.graphItemsEdited = false;
		// TODO set calcState
		this.calcState = CalculationState.NOT_CALCULABLE;
		this.setAlgorithmComboModel(core.getAlgorithmNames(this.graph
				.getEdgeType()));
		
		this.modeToggleChanging = this.modeComboChanging = false;
		this.pickingToggleModel = new JToggleButton.ToggleButtonModel();
		this.editingToggleModel = new JToggleButton.ToggleButtonModel();
		this.transformingToggleModel = new JToggleButton.ToggleButtonModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#createStepModel()
	 */
	@Override
	public IStepModel createStepModel() {
		return new StepModel(this.progressBarModel.getValue(),
				this.progressBarModel.getMaximum());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#createToolBarModel()
	 */
	@Override
	public IToolBarModel createToolBarModel() {
		return new ToolBarModel(
				this.algoComboModel,
				!this.graph.isEmpty(),
				this.graphItemsEdited
						&& this.algoComboModel.getSelectedItem() != DEFAULT_ALGO_ENTRY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getAlgorithmComboModel()
	 */
	@Override
	public ComboBoxModel<String> getAlgorithmComboModel() {
		return this.algoComboModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getBackButtonModel()
	 */
	@Override
	public ButtonModel getBackButtonModel() {
		return this.backButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getBeginningButtonModel
	 * ()
	 */
	@Override
	public ButtonModel getBeginningButtonModel() {
		return this.beginningButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getEditModeComboModel()
	 */
	@Override
	public ComboBoxModel<?> getEditModeComboModel() {
		return this.editModeComboModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getEndButtonModel()
	 */
	@Override
	public ButtonModel getEndButtonModel() {
		return this.endButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getForwardButtonModel()
	 */
	@Override
	public ButtonModel getForwardButtonModel() {
		return this.forwardButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getGraph()
	 */
	@Override
	public IEditGraphObservable getGraph() {
		return this.graph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getProgressBarModel()
	 */
	@Override
	public BoundedRangeModel getProgressBarModel() {
		return this.progressBarModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getStepIterator()
	 */
	@Override
	public IGravisListIterator<String> getStepIterator() {
		return this.stepIterator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#hasGraphChanged()
	 */
	@Override
	public boolean hasGraphChanged() {
		return this.graphItemsEdited;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#isGraphUnsaved()
	 */
	@Override
	public boolean isGraphUnsaved() {
		return this.graphUnsaved;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#resetStepEnabledState()
	 */
	@Override
	public void resetStepEnabledState() {
		if (this.stepIterator != null) {
			this.stepIterator.first();
			this.stepIterator = null;

			this.updateStepButtonModels(false, false, false, false);

			if (this.progressBarModel != null) {
				this.progressBarModel.setMinimum(0);
				this.progressBarModel.setMaximum(0);
				this.progressBarModel.setValue(0);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setAlgorithmComboModel
	 * (java.lang.String[])
	 */
	@Override
	public void setAlgorithmComboModel(String[] algoNames) {
		this.algoComboModel = new DefaultComboBoxModel<>();
		this.algoComboModel.addElement(DEFAULT_ALGO_ENTRY);

		for (String algoName : algoNames) {
			this.algoComboModel.addElement(algoName);
		}

		this.algoComboModel.setSelectedItem(DEFAULT_ALGO_ENTRY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setBackButtonModel(javax
	 * .swing.ButtonModel)
	 */
	@Override
	public void setBackButtonModel(ButtonModel model) {
		this.backButtonModel = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setBeginningButtonModel
	 * (javax.swing.ButtonModel)
	 */
	@Override
	public void setBeginningButtonModel(ButtonModel model) {
		this.beginningButtonModel = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setDeleteEdgeButtonModel
	 * (javax.swing.ButtonModel)
	 */
	@Override
	public void setDeleteEdgeButtonModel(ButtonModel model) {
		this.deleteEdgeButtonModel = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setDeleteVertexButtonModel
	 * (javax.swing.ButtonModel)
	 */
	@Override
	public void setDeleteVertexButtonModel(ButtonModel model) {
		this.deleteVertexButtonModel = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setEditModeComboModel
	 * (javax.swing.ComboBoxModel)
	 */
	@Override
	public void setEditModeComboModel(ComboBoxModel<?> comboModel) {
		this.editModeComboModel = comboModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setEndButtonModel(javax
	 * .swing.ButtonModel)
	 */
	@Override
	public void setEndButtonModel(ButtonModel model) {
		this.endButtonModel = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setForwardButtonModel
	 * (javax.swing.ButtonModel)
	 */
	@Override
	public void setForwardButtonModel(ButtonModel model) {
		this.forwardButtonModel = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setGraphChanged(boolean)
	 */
	@Override
	public void setGraphChanged(boolean graphChanged) {
		this.graphItemsEdited = graphChanged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setGraphUnsaved(boolean)
	 */
	@Override
	public void setGraphUnsaved(boolean graphUnsaved) {
		this.graphUnsaved = graphUnsaved;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setNewGraphState(edu
	 * .uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public void setNewGraphState(EdgeType edgeType) {
		this.graph = GraphFactory.createEditGraphObservable(edgeType,
				this.graph.getEditGraphEventListeners());
		this.graphUnsaved = this.graphItemsEdited = false;
		this.resetStepEnabledState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setOpenGraphState(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph)
	 */
	@Override
	public void setOpenGraphState(IGravisGraph graph) {
		this.graph = GraphFactory.createEditGraphObservable(graph,
				this.graph.getEditGraphEventListeners());
		this.graphUnsaved = this.graphItemsEdited = false;
		this.resetStepEnabledState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setPopupEditMode(edu
	 * .uci. ics.jung.visualization.control.ModalGraphMouse.Mode)
	 */
	@Override
	public void setPopupEditMode(Mode mode) {
		// TODO not playing precond
		if (this.deleteEdgeButtonModel != null) {
			this.deleteEdgeButtonModel.setEnabled(mode == Mode.EDITING);
		}
		if (this.vertexCreateButtonModel != null) {
			this.vertexCreateButtonModel.setEnabled(mode == Mode.EDITING);
		}
		if (this.deleteVertexButtonModel != null) {
			this.deleteVertexButtonModel.setEnabled(mode == Mode.EDITING);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setProgressBarModel(
	 * javax.swing.BoundedRangeModel)
	 */
	@Override
	public void setProgressBarModel(BoundedRangeModel model) {
		this.progressBarModel = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setStepEnabledState(
	 * ch.bfh.bti7301.hs2013.gravis.core.util.IGravisListIterator)
	 */
	@Override
	// TODO rename: setStateStepEnabled
	// TODO methode: resetStepEnabled -> setStateStepEnabled(boolean)
	// TODO prec: not playing
	public void setStepEnabledState(IGravisListIterator<String> stepIterator) {
		if (stepIterator != null) {
			this.stepIterator = stepIterator;
			this.stepIterator.first();

			this.updateStepButtonModels(this.stepIterator.hasPrevious(),
					this.stepIterator.hasPrevious(),
					this.stepIterator.hasNext(), this.stepIterator.hasNext());

			if (this.progressBarModel != null) {
				this.progressBarModel.setMinimum(0);
				this.progressBarModel.setMaximum(this.stepIterator.size());
				this.progressBarModel.setValue(0);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setVertexCreateButtonModel
	 * (javax.swing.ButtonModel)
	 */
	@Override
	public void setVertexCreateButtonModel(ButtonModel model) {
		this.vertexCreateButtonModel = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#updateStepButtonModels
	 * (boolean, boolean, boolean, boolean)
	 */
	@Override
	public void updateStepButtonModels(boolean beginning, boolean back,
			boolean forward, boolean end) {
		// TODO not playing
		if (this.beginningButtonModel != null) {
			this.beginningButtonModel.setEnabled(beginning);
		}
		if (this.backButtonModel != null) {
			this.backButtonModel.setEnabled(back);
		}
		if (this.forwardButtonModel != null) {
			this.forwardButtonModel.setEnabled(forward);
		}
		if (this.endButtonModel != null) {
			this.endButtonModel.setEnabled(end);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getPickingToggleModel()
	 */
	@Override
	public ButtonModel getPickingToggleModel() {
		return this.pickingToggleModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getEditingToggleModel()
	 */
	@Override
	public ButtonModel getEditingToggleModel() {
		return this.editingToggleModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#getTransformingToggleModel
	 * ()
	 */
	@Override
	public ButtonModel getTransformingToggleModel() {
		return this.transformingToggleModel;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#isModeToggleChanging()
	 */
	@Override
	public boolean isModeToggleChanging() {
		return this.modeToggleChanging;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setModeToggleChanging(boolean)
	 */
	@Override
	public void setModeToggleChanging(boolean modeChanging) {
		this.modeToggleChanging = modeChanging;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#isModeComboChanging()
	 */
	@Override
	public boolean isModeComboChanging() {
		return this.modeComboChanging;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel#setModeComboChanging(boolean)
	 */
	@Override
	public void setModeComboChanging(boolean modeChanging) {
		this.modeComboChanging = modeChanging;
	}

	
}
