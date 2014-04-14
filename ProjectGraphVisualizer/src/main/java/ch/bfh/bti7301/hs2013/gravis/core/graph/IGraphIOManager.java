package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.io.File;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGraphIOManager {

	/**
	 * Imports a graph from a GraphML-file into the application.
	 * 
	 * @param source
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
	public abstract void saveGraph(IGravisGraph graph, File file) throws GravisGraphIOException;

}
