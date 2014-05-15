package ch.bfh.ti.gravis.core.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class AlgorithmFactory {

	private Map<String, IAlgorithm> algorithmMap;
	
	private List<String> directedAlgoNames, undirectedAlgoNames;

	public AlgorithmFactory() {
		this.algorithmMap = new TreeMap<>();
		this.directedAlgoNames = new ArrayList<>();
		this.undirectedAlgoNames = new ArrayList<>();
		
		// Create an instance of each available algorithm
		IAlgorithm algorithm = new DFSRecursive();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new PostorderRecursive();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new BreadthFirstSearch();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new DijkstraDistance();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new KruskalMinSpanningForest();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		
		for (String algoName : this.algorithmMap.keySet()) {
			if (this.algorithmMap.get(algoName).hasEdgeType(EdgeType.DIRECTED)) {
				this.directedAlgoNames.add(algoName);
			} 
			
			if (this.algorithmMap.get(algoName).hasEdgeType(EdgeType.UNDIRECTED)) {
				this.undirectedAlgoNames.add(algoName);
			}
		}
		
	}

	/**
	 * 
	 * @param algorithmName
	 * @return IAlgorithm
	 */
	public IAlgorithm createAlgorithm(String algorithmName) {
		// TODO Exception handling
		Objects.requireNonNull(algorithmName);
		
		return this.algorithmMap.get(algorithmName.trim());
	}

	/**
	 * 
	 * @param edgetype
	 * @return String[]
	 */
	public String[] getAlgorithmNames(EdgeType edgetype) {
		if (edgetype == EdgeType.DIRECTED) {
			return this.directedAlgoNames.toArray(new String[this.directedAlgoNames.size()]);
		} else {
			return this.undirectedAlgoNames.toArray(new String[this.undirectedAlgoNames.size()]);
		}
	}

}
