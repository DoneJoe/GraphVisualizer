package ch.bfh.ti.gravis.core;

import java.io.File;
import java.io.FileNotFoundException;

import ch.bfh.ti.gravis.core.algorithm.AlgorithmException;
import ch.bfh.ti.gravis.core.algorithm.AlgorithmFactory;
import ch.bfh.ti.gravis.core.algorithm.IAlgorithm;
import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.GraphIOManager;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.step.StepBuilder;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * This is an implementation of the ICore interface. This class gives access to
 * all important core services (facade pattern).
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class Core implements ICore {

	private final static String UNKNOWN_ALGO = "Invalid algorithm name: %s";

	private GraphIOManager graphIOManager;
	private AlgorithmFactory algorithmFactory;

	/**
	 * @param graphIOManager
	 * @param algorithmFactory
	 */
	protected Core(GraphIOManager graphIOManager,
			AlgorithmFactory algorithmFactory) {
		this.graphIOManager = graphIOManager;
		this.algorithmFactory = algorithmFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.ICore#loadGraph(java.io.File)
	 */
	@Override
	public IGravisGraph loadGraph(File source) throws GraphIOException,
			FileNotFoundException {
		return this.graphIOManager.loadGraph(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.ICore#saveGraph(ch.bfh.bti7301.hs2013
	 * .gravis.core.graph.IGravisGraph, java.io.File)
	 */
	@Override
	public void saveGraph(IGravisGraph graph, File file)
			throws GraphIOException, FileNotFoundException {
		this.graphIOManager.saveGraph(graph, file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.ICore#getAlgorithmNames(edu.uci.ics
	 * .jung.graph.util.EdgeType)
	 */
	@Override
	public String[] getAlgorithmNames(EdgeType edgeType) {
		return this.algorithmFactory.getAlgorithmNames(edgeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.ICore#calculateSteps(ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.IGravisGraph, java.lang.String)
	 */
	@Override
	public IGravisListIterator<String> calculateSteps(final IGravisGraph graph,
			final String algorithmName) throws CoreException,
			AlgorithmException {

		StepBuilder stepBuilder = new StepBuilder();
		IRestrictedGraph restrictedGraph = GraphFactory.createRestrictedGraph(
				graph, stepBuilder);
		IAlgorithm algorithm = this.algorithmFactory
				.createAlgorithm(algorithmName);

		if (algorithm == null) {
			// invalid algorithmName
			throw new CoreException(String.format(UNKNOWN_ALGO, algorithmName));
		} else {
			restrictedGraph.resetItemHelperVars();
			// execute the algorithm
			algorithm.execute(restrictedGraph,
					StepBuilder.createStepRecorder(restrictedGraph));
			restrictedGraph.resetItemHelperVars();
			
			return stepBuilder.createStepIterator();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.ICore#getAlgorithmDescription(java.
	 * lang.String)
	 */
	@Override
	public String getAlgorithmDescription(String algoName) {
		return this.algorithmFactory.getAlgorithmDescription(algoName);
	}

}
