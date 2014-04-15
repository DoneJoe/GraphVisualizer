package ch.bfh.bti7301.hs2013.gravis.core.algorithm;

import java.util.HashSet;
import java.util.Set;

import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * Abstract class with basic functionality.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
abstract class AbstractAlgorithm implements IAlgorithm {

	/**
	 * A filed for the name.
	 */
	private final String name;

	/**
	 * A filed for the description.
	 */
	private final String description;

	/**
	 * A set for the edgeTypes.
	 */
	private final Set<EdgeType> edgeTypes;

	/**
	 * 
	 * @param name
	 * @param description
	 */
	protected AbstractAlgorithm(String name, String description) {
		this.name = name;
		this.description = description;
		this.edgeTypes = new HashSet<>();
	}

	/**
	 * 
	 * @param edgeType
	 */
	protected void addEdgeType(EdgeType edgeType) {
		this.edgeTypes.add(edgeType);
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
	 * ch.bfh.bti7301.hs2013.gravis.core.algorithm.IAlgorithm#hasEdgeType(edu
	 * .uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public final boolean hasEdgeType(EdgeType edgeType) {
		// TODO Verhalten bei edgeType == null?
		return this.edgeTypes.contains(edgeType);
	}

}
