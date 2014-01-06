package ch.bfh.bti7301.hs2013.gravis.core.algorithm;

import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IAlgorithmManager {

	/**
	 * 
	 * @param edgetype
	 * @return
	 */
	public abstract String[] getAlgorithmNames(EdgeType edgetype);

	/**
	 * @param algorithmName
	 * @return IAlgorithm
	 */
	public abstract IAlgorithm getAlgorithm(String algorithmName);

}
