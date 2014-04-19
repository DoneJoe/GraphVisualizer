package ch.bfh.ti.gravis.core;

import java.io.File;

import ch.bfh.ti.gravis.core.graph.GravisGraphIOException;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;

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
	 * @throws GravisGraphIOException
	 */
	public abstract IGravisGraph loadGraph(File source) throws GravisGraphIOException;

	/**
	 * 
	 * @param graph
	 * @param file
	 * @throws GravisGraphIOException
	 */
	public abstract void saveGraph(IGravisGraph graph, File file)
			throws GravisGraphIOException;

	/**
	 * 
	 * @param edgeType
	 * @return String[]
	 * @throws CoreException
	 */
	public abstract String[] getAlgorithmNames(EdgeType edgeType) throws CoreException;

	/**
	 * 
	 * @param graph
	 * @param algorithmName
	 * @return IGravisListIterator<String>
	 * @throws CoreException
	 */
	public abstract IGravisListIterator<String> calculateSteps(
			IGravisGraph graph, String algorithmName) throws CoreException;

	/**
	 * 
	 * @param algoName
	 * @return String
	 * @throws CoreException
	 */
	public abstract String getAlgorithmDescription(String algoName) throws CoreException;


}