package ch.bfh.ti.gravis.core;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import ch.bfh.ti.gravis.core.algorithm.AlgorithmException;
import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class CoreTest {

	private static ICore core;
	private static IGravisGraph graph;
	private static VertexFactory vertexFactory;
	private static File file;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		core = CoreFactory.createCore();
		vertexFactory = new VertexFactory();
		file = new File("junit_graphs/JUnit_Dir_Sample_Graph.graphml");
	}

	/**
	 * @throws AlgorithmException
	 * @throws CoreException
	 */
	@Test
	public final void testCalculateStepsEmptyGraph() throws CoreException,
			AlgorithmException {

		graph = GraphFactory.createDirectedGravisGraph();
		assertTrue(core.calculateSteps(graph, "Algorithmus von Dijkstra")
				.isEmpty());
		assertTrue(core.calculateSteps(graph, "Tiefensuche (DFS)").isEmpty());
		assertTrue(core.calculateSteps(graph, "Breitensuche (BFS)").isEmpty());
		assertTrue(core.calculateSteps(
				GraphFactory.createGravisGraph(EdgeType.UNDIRECTED),
				"Algorithmus von Kruskal").isEmpty());
	}

	/**
	 * @throws AlgorithmException
	 * @throws CoreException
	 */
	@Test
	public final void testCalculateStepsOneElement() throws CoreException,
			AlgorithmException {
		graph = GraphFactory.createGravisGraph(EdgeType.UNDIRECTED);
		graph.addVertex(vertexFactory.create());

		assertEquals(2, core.calculateSteps(graph, "Algorithmus von Dijkstra")
				.size());
		assertEquals(2, core.calculateSteps(graph, "Tiefensuche (DFS)").size());
		assertEquals(3, core.calculateSteps(graph, "Breitensuche (BFS)").size());
		assertEquals(1, core.calculateSteps(graph, "Algorithmus von Kruskal")
				.size());
	}

	@Test
	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws GraphIOException
	 * @throws CoreException
	 * @throws AlgorithmException
	 */
	public final void testStepIterator() throws FileNotFoundException,
			GraphIOException, CoreException, AlgorithmException {
		graph = core.loadGraph(file);
		IGravisListIterator<String> iterator = core.calculateSteps(graph,
				"Algorithmus von Dijkstra");
		
		assertFalse(iterator.isEmpty());
		assertEquals(16, iterator.size());
		assertEquals(-1, iterator.previousIndex());
		assertEquals(0, iterator.nextIndex());
		assertTrue(iterator.hasNext());
		assertFalse(iterator.hasPrevious());
		iterator.next();
		assertEquals(0, iterator.previousIndex());
		assertEquals(1, iterator.nextIndex());
		assertTrue(iterator.hasNext());
		assertTrue(iterator.hasPrevious());
		iterator.last();
		assertEquals(15, iterator.previousIndex());
		assertEquals(16, iterator.nextIndex());
		assertFalse(iterator.hasNext());
		assertTrue(iterator.hasPrevious());
		iterator.first();
		assertEquals(-1, iterator.previousIndex());
		assertEquals(0, iterator.nextIndex());
		assertTrue(iterator.hasNext());
		assertFalse(iterator.hasPrevious());
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.Core#getAlgorithmNames(edu.uci.ics.jung.graph.util.EdgeType)}
	 * .
	 */
	@Test
	public final void testGetAlgorithmNames() {
		String[] algoNames = core.getAlgorithmNames(EdgeType.DIRECTED);		
		assertEquals(3, algoNames.length);
		assertEquals("Algorithmus von Dijkstra", algoNames[0]);
		assertEquals("Breitensuche (BFS)", algoNames[1]);
		assertEquals("Tiefensuche (DFS)", algoNames[2]);
		
		algoNames = core.getAlgorithmNames(EdgeType.UNDIRECTED);		
		assertEquals(4, algoNames.length);
		assertEquals("Algorithmus von Dijkstra", algoNames[0]);
		assertEquals("Algorithmus von Kruskal", algoNames[1]);
		assertEquals("Breitensuche (BFS)", algoNames[2]);
		assertEquals("Tiefensuche (DFS)", algoNames[3]);
		
		assertEquals(0, core.getAlgorithmNames(null).length);
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.Core#getAlgorithmDescription(java.lang.String)}
	 * .
	 */
	@Test
	public final void testGetAlgorithmDescription() {
		assertEquals("", core.getAlgorithmDescription(null));
		assertEquals("", core.getAlgorithmDescription("a"));
		assertFalse(core.getAlgorithmDescription("Algorithmus von Dijkstra").isEmpty());
	}

}
