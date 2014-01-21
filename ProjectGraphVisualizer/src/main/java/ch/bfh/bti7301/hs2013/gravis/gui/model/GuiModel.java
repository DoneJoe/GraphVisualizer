package ch.bfh.bti7301.hs2013.gravis.gui.model;

import javax.swing.BoundedRangeModel;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GuiModel implements IGuiModel {

	private IEditingGraph graph;

	private boolean graphChanged;

	private DefaultComboBoxModel<String> algoComboModel;
	
	private ComboBoxModel<?> editModeComboModel = null;

	private IGravisListIterator<String> stepIterator = null;

	private ButtonModel deleteEdgeButtonModel = null,
			vertexCreateButtonModel = null, deleteVertexButtonModel = null,
			beginningButtonModel = null, endButtonModel = null,
			backButtonModel = null, forwardButtonModel = null;

	private BoundedRangeModel progressBarModel = null;

	protected GuiModel() {
		// creates an empty graph
		this.graph = GraphFactory.createEditingGraph();
		this.graphChanged = false;
		this.algoComboModel = new DefaultComboBoxModel<>();
		this.algoComboModel.addElement(DEFAULT_ALGO_ENTRY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getGraph()
	 */
	@Override
	public IGravisGraph getGraph() {
		return this.graph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setNewGraphState(edu
	 * .uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public void setNewGraphState(EdgeType edgeType) {
			this.graph = GraphFactory.createEditingGraph(edgeType,
					this.graph.getEditingGraphEventListener());
			this.graphChanged = false;
			this.resetStepEnabledState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setOpenGraphState(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph)
	 */
	@Override
	public void setOpenGraphState(IGravisGraph graph) {
		this.graph = GraphFactory.createEditingGraph(graph, 
				this.graph.getEditingGraphEventListener());
		this.graphChanged = false;
		this.resetStepEnabledState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#hasGraphChanged()
	 */
	@Override
	public boolean hasGraphChanged() {
		return this.graphChanged;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setGraphChanged(boolean)
	 */
	@Override
	public void setGraphChanged(boolean graphChanged) {
		this.graphChanged = graphChanged;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getStepIterator()
	 */
	@Override
	public IGravisListIterator<String> getStepIterator() {
		return this.stepIterator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setVisualizationController
	 * (ch.bfh.bti7301.hs2013.gravis.gui.controller.IEditingGraphEventListener)
	 */
	@Override
	public void setEditingGraphEventListener(
			IEditingGraphEventListener editingGraphEventListener) {
		this.graph.setEditingGraphEventListener(editingGraphEventListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setDeleteEdgeButtonModel
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setPopupEditMode(edu
	 * .uci. ics.jung.visualization.control.ModalGraphMouse.Mode)
	 */
	@Override
	public void setPopupEditMode(Mode mode) {
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setVertexCreateButtonModel
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setDeleteVertexButtonModel
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setStepEnabledState(
	 * ch.bfh.bti7301.hs2013.gravis.core.util.IGravisListIterator)
	 */
	@Override
	public void setStepEnabledState(IGravisListIterator<String> stepIterator) {
		this.stepIterator = stepIterator;
		this.stepIterator.first();
		
		if (this.beginningButtonModel != null) {
			this.beginningButtonModel.setEnabled(false);
		}
		if (this.forwardButtonModel != null) {
			this.forwardButtonModel.setEnabled(true);
		}
		if (this.backButtonModel != null) {
			this.backButtonModel.setEnabled(true);
		}
		if (this.endButtonModel != null) {
			this.endButtonModel.setEnabled(true);
		}
		if (this.progressBarModel != null) {
			this.progressBarModel.setMaximum(this.stepIterator.size());
			this.progressBarModel.setValue(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#resetStepEnabledState()
	 */
	@Override
	public void resetStepEnabledState() {
		this.stepIterator = null;
		
		if (this.beginningButtonModel != null) {
			this.beginningButtonModel.setEnabled(false);
		}
		if (this.forwardButtonModel != null) {
			this.forwardButtonModel.setEnabled(false);
		}
		if (this.backButtonModel != null) {
			this.backButtonModel.setEnabled(false);
		}
		if (this.endButtonModel != null) {
			this.endButtonModel.setEnabled(false);
		}
		if (this.progressBarModel != null) {
			this.progressBarModel.setMaximum(0);
			this.progressBarModel.setValue(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#createToolBarModel()
	 */
	@Override
	public IToolBarModel createToolBarModel() {
		return new ToolBarModel(this.algoComboModel, !this.graph.isEmpty(),
				this.graphChanged);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getAlgorithmComboModel()
	 */
	@Override
	public ComboBoxModel<String> getAlgorithmComboModel() {
		return this.algoComboModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setAlgorithmComboModel
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setEditModeComboModel
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getEditModeComboModel()
	 */
	@Override
	public ComboBoxModel<?> getEditModeComboModel() {
		return this.editModeComboModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setBeginningButtonModel
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setEndButtonModel(javax
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setBackButtonModel(javax
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setForwardButtonModel
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setProgressBarModel(
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
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getBeginningButtonModel
	 * ()
	 */
	@Override
	public ButtonModel getBeginningButtonModel() {
		return this.beginningButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getEndButtonModel()
	 */
	@Override
	public ButtonModel getEndButtonModel() {
		return this.endButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getBackButtonModel()
	 */
	@Override
	public ButtonModel getBackButtonModel() {
		return this.backButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getForwardButtonModel()
	 */
	@Override
	public ButtonModel getForwardButtonModel() {
		return this.forwardButtonModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getProgressBarModel()
	 */
	@Override
	public BoundedRangeModel getProgressBarModel() {
		return this.progressBarModel;
	}


}
