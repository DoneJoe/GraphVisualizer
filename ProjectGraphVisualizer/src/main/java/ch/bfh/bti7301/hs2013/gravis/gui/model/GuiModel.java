package ch.bfh.bti7301.hs2013.gravis.gui.model;

import javax.swing.DefaultComboBoxModel;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GuiModel implements IGuiModel {

	private IGravisGraph graph;
	
	private boolean graphChanged;

	private IGravisListIterator<String> stepIterator = null;
	
	protected GuiModel() {
		// creates an empty graph
		this.graph = GraphFactory.createGravisGraph();
		this.graphChanged = false;
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
		this.graph = GraphFactory.createGravisGraph(edgeType);
		this.graphChanged = false;

		// TODO disable step panel
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
		this.graph = graph;
		this.graphChanged = false;

		// TODO reset algo-dropdown
		// TODO disable step panel
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getToolBarModel(java
	 * .lang.String[])
	 */
	@Override
	public IToolBarModel getToolBarModel(String[] algoNames) {
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		comboBoxModel.addElement(DEFAULT_ALGO_ENTRY);

		for (String name : algoNames) {
			comboBoxModel.addElement(name);
		}

		return new ToolBarModel(comboBoxModel, !this.graph.isEmpty(),
				this.graphChanged);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#setStepIterator(ch.bfh
	 * .bti7301.hs2013.gravis.core.util.IGravisListIterator)
	 */
	@Override
	public void setStepIterator(IGravisListIterator<String> stepIterator) {
		this.stepIterator = stepIterator;
		
		// TODO enable step panel
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel#getStepIterator()
	 */
	@Override
	public IGravisListIterator<String> getStepIterator() {
		return this.stepIterator;
	}

}
