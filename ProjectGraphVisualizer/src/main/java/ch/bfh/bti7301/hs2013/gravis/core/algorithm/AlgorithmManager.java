package ch.bfh.bti7301.hs2013.gravis.core.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.map.HashedMap;

import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class AlgorithmManager implements IAlgorithmManager {

	private Map<String, IAlgorithm> algorithmMap;
	
	private List<String> directedAlgoNames, undirectedAlgoNames;

	protected AlgorithmManager() {
		this.algorithmMap = new HashedMap<>();
		this.directedAlgoNames = new ArrayList<>();
		this.undirectedAlgoNames = new ArrayList<>();
		
		IAlgorithm algorithm = new AlgorithmDFSRecursive();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new AlgorithmDLSRecursive();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new AlgorithmBreadthFirstSearch();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new AlgorithmDijkstra();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new AlgorithmKruskalMinSpanningForest();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		
		for (String algoName : this.algorithmMap.keySet()) {
			if (this.algorithmMap.get(algoName).hasEdgeType(EdgeType.DIRECTED)) {
				this.directedAlgoNames.add(algoName);
			} else {
				this.undirectedAlgoNames.add(algoName);
			}
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.algorithm.IAlgorithmManager#
	 * getAlgorithmNames(edu.uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public String[] getAlgorithmNames(EdgeType edgetype) {
		if (edgetype == EdgeType.DIRECTED) {
			return this.directedAlgoNames.toArray(new String[this.directedAlgoNames.size()]);
		} else {
			return this.undirectedAlgoNames.toArray(new String[this.undirectedAlgoNames.size()]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.algorithm.IAlgorithmManager#getAlgorithm
	 * (java.lang.String)
	 */
	@Override
	public IAlgorithm getAlgorithm(String algorithmName) {
		return this.algorithmMap.get(algorithmName);
	}

}
