package ch.bfh.ti.gravis.core.algorithm;

import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * An algorithm able to operate on a <code>Graph</code>.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IAlgorithm {

	/**
	 * Returns the algorithm name.
	 * 
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * Returns a description of this graph algorithm, e.g. what this algorithm
	 * is made for, its limits etc.
	 * 
	 * @return the description
	 */
	public abstract String getDescription();

	/**
	 * 
	 * @param edgeType
	 * @return boolean
	 */
	public abstract boolean hasEdgeType(EdgeType edgeType);

	/**
	 * The algorithm is executed with the given restricted graph and step
	 * recorder.
	 * 
	 * @param graph
	 * @param rec
	 * @throws AlgorithmException
	 *             if the algorithm is not able to cumpute a correct result with
	 *             the given graph.
	 */
	public abstract void execute(IRestrictedGraph graph, IStepRecorder rec)
			throws AlgorithmException;

}
