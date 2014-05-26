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

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "AlgorithmFactory.%s(): %s == %s";
	
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
	 * 
	 * @param algorithmName
	 * @return IAlgorithm or null
	 */
	public IAlgorithm createAlgorithm(final String algorithmName) {
		Objects.requireNonNull(algorithmName, String.format(NULL_POINTER_MSG, "createAlgorithm", 
				"algorithmName", algorithmName));
		return this.algorithmMap.get(algorithmName.trim());
	}

	/**
	 * 
	 * @param edgetype
	 * @return algo names with edge type
	 */
	public String[] getAlgorithmNames(final EdgeType edgetype) {
		if (edgetype == null) {
			return new String[] { };
		} else if (edgetype == EdgeType.DIRECTED) {
			return this.directedAlgoNames
					.toArray(new String[this.directedAlgoNames.size()]);
		} else {
			return this.undirectedAlgoNames
					.toArray(new String[this.undirectedAlgoNames.size()]);
		}
	}

	/**
	 * @param algoName
	 * @return algorithm description or empty string
	 */
	public String getAlgorithmDescription(final String algoName) {
		IAlgorithm algo = this.algorithmMap.get(algoName == null ? "" : algoName.trim());			
		return algo == null ? "" : algo.getDescription();
	}

}
