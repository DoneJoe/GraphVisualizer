package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.io.File;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGraphManager {

	/**
	 * Imports a graph from a GraphML-file into the system.
	 * 
	 * @param source
	 * @return IGravisGraph
	 * @throws GraphException
	 */
	public abstract IGravisGraph importGraph(File source) throws GraphException;

	/**
	 * 
	 * @param graph
	 * @param file
	 * @throws GraphException
	 */
	public abstract void saveGraph(IGravisGraph graph, File file) throws GraphException;

}
