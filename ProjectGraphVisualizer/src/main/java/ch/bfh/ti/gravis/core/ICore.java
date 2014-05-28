package ch.bfh.ti.gravis.core;

import java.io.File;
import java.io.FileNotFoundException;

import ch.bfh.ti.gravis.core.algorithm.AlgorithmException;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * This interface gives access to all important core services. It is a facade to
 * different other core classes and interfaces (facade pattern).
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface ICore {

	/**
	 * Loads a graph from the source file (graphml-format).
	 * 
	 * @param source
	 *            the graph file to import
	 * @return IGravisGraph
	 * @throws GraphIOException
	 * @throws FileNotFoundException
	 */
	public abstract IGravisGraph loadGraph(File source)
			throws GraphIOException, FileNotFoundException;

	/**
	 * Saves the graph in a file (graphml-format).
	 * 
	 * @param graph
	 * @param file
	 * @throws GraphIOException
	 * @throws FileNotFoundException
	 */
	public abstract void saveGraph(IGravisGraph graph, File file)
			throws GraphIOException, FileNotFoundException;

	/**
	 * Returns a String array of algorithm names with the given edgetype.
	 * 
	 * @param edgeType
	 * @return string array
	 */
	public abstract String[] getAlgorithmNames(EdgeType edgeType);

	/**
	 * Calculates the animation steps for a given graph and algorithmName.
	 * 
	 * @param graph
	 * @param algorithmName
	 * @return a list iterator over the steps in proper sequence
	 * @throws CoreException
	 *             for an invalid algorithmName
	 * @throws AlgorithmException
	 *             if the algorithm is not able to calculate a correct result
	 *             with the given graph
	 */
	public abstract IGravisListIterator<String> calculateSteps(
			IGravisGraph graph, String algorithmName) throws CoreException,
			AlgorithmException;

	/**
	 * Returns an algorithm description associated with the given algorithmName.
	 * 
	 * @param algoName
	 * @return algorithm description or empty string, if the algorithmName is
	 *         unknown
	 */
	public abstract String getAlgorithmDescription(String algoName);

}
