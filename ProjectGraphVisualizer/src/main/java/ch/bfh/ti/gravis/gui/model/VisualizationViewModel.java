package ch.bfh.ti.gravis.gui.model;

import ch.bfh.ti.gravis.core.graph.IGravisGraph;

/**
 * This model class is used for updating the visualization panel.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class VisualizationViewModel {

	private final IGravisGraph graph;
	
	private final boolean graphChanged;
	
	/**
	 * @param graph
	 * @param graphChanged
	 */
	public VisualizationViewModel(IGravisGraph graph, boolean graphChanged) {
		this.graph = graph;
		this.graphChanged = graphChanged;
	}

	/**
	 * @return IGravisGraph
	 */
	public IGravisGraph getGraph() {
		return this.graph;
	}

	/**
	 * @return the graphChanged
	 */
	public boolean isGraphChanged() {
		return this.graphChanged;
	}
	
}
