package ch.bfh.ti.gravis.core;

import java.io.File;
import java.io.FileNotFoundException;

import ch.bfh.ti.gravis.core.algorithm.AlgorithmException;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * This interface gives access to all important core classes. It is a facade to
 * different other classes and interfaces (facade pattern).
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface ICore {

	/**
	 * Imports a graph.
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
	 * 
	 * @param graph
	 * @param file
	 * @throws GraphIOException
	 * @throws FileNotFoundException 
	 */
	public abstract void saveGraph(IGravisGraph graph, File file)
			throws GraphIOException, FileNotFoundException;

	/**
	 * 
	 * @param edgeType
	 * @return String[]
	 */
	public abstract String[] getAlgorithmNames(EdgeType edgeType);

	/**
	 * 
	 * @param graph
	 * @param algorithmName
	 * @return IGravisListIterator<String>
	 * @throws CoreException
	 * @throws AlgorithmException
	 */
	public abstract IGravisListIterator<String> calculateSteps(
			IGravisGraph graph, String algorithmName) throws CoreException,
			AlgorithmException;

	/**
	 * 
	 * @param algoName
	 * @return String
	 */
	public abstract String getAlgorithmDescription(String algoName);

}
