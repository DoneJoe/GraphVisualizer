package ch.bfh.bti7301.hs2013.gravis.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.algorithm.AlgorithmException;
import ch.bfh.bti7301.hs2013.gravis.core.algorithm.AlgorithmFactory;
import ch.bfh.bti7301.hs2013.gravis.core.algorithm.IAlgorithm;
import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraphIOException;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphIOManager;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IObservableGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.step.IStep;
import ch.bfh.bti7301.hs2013.gravis.core.util.IGravisListIterator;
import ch.bfh.bti7301.hs2013.gravis.core.util.StepIterator;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisListIterator;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * This class gives access to all important methods of the core classes (facade
 * pattern).
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class Core implements ICore {

	private final static String UNKNOWN_ALGO = "This is not a valid algorithm name: %s";

	private IGraphIOManager graphIOManager;
	private AlgorithmFactory algorithmFactory;

	/**
	 * @param graphIOManager
	 * @param algorithmFactory
	 */
	protected Core(IGraphIOManager graphManager,
			AlgorithmFactory algorithmFactory) {
		this.graphIOManager = graphManager;
		this.algorithmFactory = algorithmFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.ICore#loadGraph(java.io.File)
	 */
	@Override
	public IGravisGraph loadGraph(File source) throws GravisGraphIOException {
			return this.graphIOManager.loadGraph(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.ICore#saveGraph(ch.bfh.bti7301.hs2013
	 * .gravis.core.graph.IGravisGraph, java.io.File)
	 */
	@Override
	public void saveGraph(IGravisGraph graph, File file) throws GravisGraphIOException {
			this.graphIOManager.saveGraph(graph, file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.ICore#getAlgorithmNames(edu.uci.ics
	 * .jung.graph.util.EdgeType)
	 */
	@Override
	public String[] getAlgorithmNames(EdgeType edgeType) {
			return this.algorithmFactory.getAlgorithmNames(edgeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.ICore#calculateSteps(ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.IGravisGraph, java.lang.String)
	 */
	@Override
	public IGravisListIterator<String> calculateSteps(final IGravisGraph graph,
			final String algorithmName) throws CoreException {
		try {
			List<IStep> commandList = new ArrayList<>();
			GraphEventListener<IVertex, IEdge> graphEventListener = GraphFactory
					.createGraphStepListener(commandList);
			IObservableGravisGraph observableGraph = GraphFactory
					.createObservableGraph(graph);
			observableGraph.addGraphEventListener(graphEventListener);
			IRestrictedGraph restrictedGraph = GraphFactory
					.createRestrictedGraph(observableGraph);
			IAlgorithm algorithm = this.algorithmFactory
					.getAlgorithm(algorithmName);

			if (algorithm != null) {
				algorithm.execute(restrictedGraph);
				// undo for all commands in the list in reverse order
				for (int i = commandList.size() - 1; i >= 0; i--) {
					commandList.get(i).unExecute();
				}
				
				return new StepIterator(new GravisListIterator<IStep>(
						commandList));
			} else {
				throw new CoreException(String.format(UNKNOWN_ALGO,
						algorithmName));
			}
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.ICore#getAlgorithmDescription(java.
	 * lang.String)
	 */
	@Override
	public String getAlgorithmDescription(String algoName) throws CoreException {
			try {
				IAlgorithm algo = this.algorithmFactory.getAlgorithm(algoName);
				return algo == null ? null : algo.getDescription();
			} catch (AlgorithmException e) {
				throw new CoreException(e);
			}
	}

}
