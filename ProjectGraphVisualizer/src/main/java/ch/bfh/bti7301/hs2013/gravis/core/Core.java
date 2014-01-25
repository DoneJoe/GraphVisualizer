package ch.bfh.bti7301.hs2013.gravis.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.algorithm.IAlgorithm;
import ch.bfh.bti7301.hs2013.gravis.core.algorithm.IAlgorithmManager;
import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphManager;
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

	private IGraphManager graphManager;
	private IAlgorithmManager algorithmManager;

	/**
	 * @param graphManager
	 * @param algorithmManager
	 */
	protected Core(IGraphManager graphManager,
			IAlgorithmManager algorithmManager) {
		this.graphManager = graphManager;
		this.algorithmManager = algorithmManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.ICore#importGraph(java.io.File)
	 */
	@Override
	public IGravisGraph importGraph(File source) throws CoreException {
		try {
			return this.graphManager.importGraph(source);
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.ICore#saveGraph(ch.bfh.bti7301.hs2013
	 * .gravis.core.graph.IGravisGraph, java.io.File)
	 */
	@Override
	public void saveGraph(IGravisGraph graph, File file) throws CoreException {
		try {
			this.graphManager.saveGraph(graph, file);
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.ICore#getAlgorithmNames(edu.uci.ics
	 * .jung.graph.util.EdgeType)
	 */
	@Override
	public String[] getAlgorithmNames(EdgeType edgeType) throws CoreException {
		try {
			return this.algorithmManager.getAlgorithmNames(edgeType);
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.ICore#calculateSteps(ch.bfh.bti7301
	 * .hs2013.gravis.core.graph.IGravisGraph, java.lang.String)
	 */
	@Override
	public IGravisListIterator<String> calculateSteps(IGravisGraph graph,
			String algorithmName) throws CoreException {
		try {
			List<IStep> commandList = new ArrayList<>();
			GraphEventListener<IVertex, IEdge> graphEventListener = GraphFactory
					.createGravisGraphEventListener(commandList);
			IObservableGravisGraph observableGraph = GraphFactory
					.createObservableGraph(graph);
			observableGraph.addGraphEventListener(graphEventListener);
			IRestrictedGraph restrictedGraph = GraphFactory
					.createRestrictedGraph(observableGraph);
			IAlgorithm algorithm = this.algorithmManager
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
			IAlgorithm algo = this.algorithmManager.getAlgorithm(algoName);
			return algo == null ? null : algo.getDescription();
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

}
