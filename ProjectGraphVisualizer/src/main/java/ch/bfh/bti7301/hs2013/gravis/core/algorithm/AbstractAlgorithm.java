/**
 * 
 */
package ch.bfh.bti7301.hs2013.gravis.core.algorithm;

import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * Abstract algorithm, does nothing on execute().
 * 
 * @author Roland Bruggmann (brugr9@bfh.ch)
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
abstract class AbstractAlgorithm implements IAlgorithm {

	/**
	 * A filed for the name.
	 */
	private String name;

	/**
	 * A filed for the description.
	 */
	private String description;

	/**
	 * A filed for the id.
	 */
	private int id;

	/**
	 * A field for the graphtypes.
	 */
	private EdgeType[] graphtypes;

	/**
	 * 
	 */
	protected AbstractAlgorithm() {
		this.name = "";
		this.description = "";
		this.id = -1;
		this.graphtypes = new EdgeType[] {};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.algorithm.IAlgorithm#getName()
	 */
	@Override
	public final String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.algorithm.IAlgorithm#getDescription()
	 */
	@Override
	public final String getDescription() {
		return this.description;
	}

	@Override
	public final EdgeType[] getGraphTypes() {
		return this.graphtypes;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the name to set
	 */
	protected final void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the description to set
	 */
	protected final void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id to set
	 */
	protected final void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the graphtypes.
	 * 
	 * @param graphtypes
	 *            the graphtypes to set
	 */
	protected final void setGraphTypes(EdgeType[] annotations) {
		this.graphtypes = annotations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.processing.algorithms.IGraphAlgorithm
	 * #execute(edu.uci.ics.jung.graph.Graph)
	 */
	@Override
	public void execute(IRestrictedGraph graph) throws AlgorithmException {
		// Does nothing on execute.
	}

}
