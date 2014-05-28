package ch.bfh.ti.gravis.core;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.bfh.ti.gravis.core.algorithm.AlgorithmException;
import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class CoreTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.Core#loadGraph(java.io.File)}.
	 */
	@Test
	public final void testLoadGraph() {
		// fail("Not yet implemented");
		// TODO implement testLoadGraph
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.Core#saveGraph(ch.bfh.ti.gravis.core.graph.IGravisGraph, java.io.File)}
	 * .
	 */
	@Test
	public final void testSaveGraph() {
		// fail("Not yet implemented");
		// TODO implement testSaveGraph
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.Core#getAlgorithmNames(edu.uci.ics.jung.graph.util.EdgeType)}
	 * .
	 */
	@Test
	public final void testGetAlgorithmNames() {
		// fail("Not yet implemented");
		// TODO implement testGEtAlgoNames
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.Core#calculateSteps(ch.bfh.ti.gravis.core.graph.IGravisGraph, java.lang.String)}
	 * .
	 * 
	 * @throws AlgorithmException
	 * @throws CoreException
	 */
	@Test
	public final void testCalculateSteps() throws CoreException,
			AlgorithmException {
		ICore core = CoreFactory.createCore();
		IGravisGraph graph = GraphFactory.createDirectedGravisGraph();
		VertexFactory vertexFactory = new VertexFactory();
		// IGravisListIterator<String> iterator = null;

		assertEquals(true,
				core.calculateSteps(graph, "Algorithmus von Dijkstra")
						.isEmpty());
		assertEquals(true, core.calculateSteps(graph, "Tiefensuche (DFS)")
				.isEmpty());
		assertEquals(true, core.calculateSteps(graph, "Breitensuche (BFS)")
				.isEmpty());
		assertEquals(
				true,
				core.calculateSteps(
						GraphFactory.createGravisGraph(EdgeType.UNDIRECTED),
						"Algorithmus von Kruskal").isEmpty());

		graph = GraphFactory.createGravisGraph(EdgeType.UNDIRECTED);
		graph.addVertex(vertexFactory.create());

		assertEquals(2,
				core.calculateSteps(graph, "Algorithmus von Dijkstra").size());
		assertEquals(2, core.calculateSteps(graph, "Tiefensuche (DFS)")
				.size());
		assertEquals(3, core.calculateSteps(graph, "Breitensuche (BFS)")
				.size());
		assertEquals(1, core
				.calculateSteps(graph, "Algorithmus von Kruskal").size());

//		graph = GraphFactory.createDirectedGravisGraph(GraphFactory
//				.createUndirectedSampleGraph());
		
		// TODO implement testCalculateSteps
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.Core#getAlgorithmDescription(java.lang.String)}
	 * .
	 */
	@Test
	public final void testGetAlgorithmDescription() {
		// fail("Not yet implemented");
		// TODO implement testGetAlgorithmDescription
	}

}
