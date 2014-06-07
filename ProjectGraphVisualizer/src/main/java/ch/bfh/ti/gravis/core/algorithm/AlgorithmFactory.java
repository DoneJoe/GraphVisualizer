package ch.bfh.ti.gravis.core.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * This class creates instances of graph algorithms. Concrete graph algorithm
 * classes implement the IAlgorithm interface.
 * 
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

		// Creates an instance of each available algorithm
		IAlgorithm algorithm = new DFSRecursive();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new BreadthFirstSearch();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new DijkstraDistance();
		this.algorithmMap.put(algorithm.getName(), algorithm);
		algorithm = new KruskalMSF();
		this.algorithmMap.put(algorithm.getName(), algorithm);

		for (String algoName : this.algorithmMap.keySet()) {
			if (this.algorithmMap.get(algoName).hasEdgeType(EdgeType.DIRECTED)) {
				this.directedAlgoNames.add(algoName);
			}

			if (this.algorithmMap.get(algoName)
					.hasEdgeType(EdgeType.UNDIRECTED)) {
				this.undirectedAlgoNames.add(algoName);
			}
		}

	}

	/**
	 * Creates an algorithm instance associated with the given algorithmName.
	 * 
	 * @param algorithmName
	 * @return instance of IAlgorithm or null, if the algorithmName is unknown
	 */
	public IAlgorithm createAlgorithm(final String algorithmName) {
		return this.algorithmMap.get(algorithmName == null ? "" : algorithmName.trim());
	}

	/**
	 * Returns a String array of algorithm names with the given edgetype.
	 * 
	 * @param edgetype
	 * @return algo names with the given edgetype or empty string if edgetype is null
	 */
	public String[] getAlgorithmNames(final EdgeType edgetype) {
		if (edgetype == null) {
			return new String[] {};
		} else if (edgetype == EdgeType.DIRECTED) {
			return this.directedAlgoNames
					.toArray(new String[this.directedAlgoNames.size()]);
		} else {
			return this.undirectedAlgoNames
					.toArray(new String[this.undirectedAlgoNames.size()]);
		}
	}

	/**
	 * Returns an algorithm description associated with the given algorithmName.
	 * 
	 * @param algoName
	 * @return algorithm description or empty string if the algorithmName is unknown
	 */
	public String getAlgorithmDescription(final String algoName) {
		IAlgorithm algo = this.algorithmMap.get(algoName == null ? ""
				: algoName.trim());
		return algo == null ? "" : algo.getDescription();
	}

}
