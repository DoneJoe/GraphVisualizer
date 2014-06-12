package ch.bfh.ti.gravis.core.graph;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.ti.gravis.core.step.StepBuilder;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import static ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type.*;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GraphTest {

	private static class EditListener implements IEditGraphEventListener {
		public IGraphItem itemSource = null;
		public IGravisGraph graphSource = null;
		public Type type = null;

		@Override
		public void handleGraphItemsChangedEvent(IGraphItem source, Type type) {
			this.itemSource = source;
			this.type = type;
		}

		@Override
		public void handleGraphPropertiesChangedEvent(IGravisGraph source,
				Type type) {
			this.graphSource = source;
			this.type = type;
		}

		public void reset() {
			this.itemSource = null;
			this.graphSource = null;
			this.type = null;
		}

	}

	private static final double DELTA = 0.0001;

	private static VertexFactory vertexFactory;
	private static EdgeFactory edgeFactory;

	private static IGravisGraph gravisGraph;
	private static IEditGraphObservable graph;
	private static IRestrictedGraph rGraph = null;

	private static EditListener listener;
	private static StepBuilder sb;

	private static IVertex v1, v2, v3;
	private static IEdge e1, e2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sb = new StepBuilder();
		listener = new EditListener();
		gravisGraph = GraphFactory.createDirectedGravisGraph();
		graph = GraphFactory.createEditGraphObservable(gravisGraph, listener);
		vertexFactory = new VertexFactory();
		edgeFactory = new EdgeFactory();

		v1 = vertexFactory.create();
		v2 = vertexFactory.create();
		v3 = vertexFactory.create();

		e1 = edgeFactory.create();
		e2 = edgeFactory.create();
	}

	@Test
	public final void testSetGraphName() {
		try {
			graph.setName(null);
			fail();
		} catch (NullPointerException e) {
		}

		listener.reset();
		graph.setName("my Graph 1  \n");
		assertEquals("my Graph 1", graph.getName());
		assertEquals(VISUAL_EDITED, listener.type);
		assertSame(graph, listener.graphSource);

		listener.reset();
		graph.setName(" \t \n");
		assertEquals("my Graph 1", graph.getName());
		assertNull(listener.type);
		assertNull(listener.graphSource);

		listener.reset();
		graph.setName("my Graph 2\n");
		assertEquals("my Graph 2", graph.getName());
		assertEquals(VISUAL_EDITED, listener.type);
		assertSame(graph, listener.graphSource);

		listener.reset();
		graph.setName("my Graph 2");
		assertEquals("my Graph 2", graph.getName());
		assertNull(listener.type);
		assertNull(listener.graphSource);
	}

	@Test
	public final void testSetDescription() {
		try {
			graph.setDescription(null);
			fail();
		} catch (NullPointerException e) {
		}

		listener.reset();
		graph.setDescription(" \n");
		assertEquals("", graph.getDescription());
		assertEquals(VISUAL_EDITED, listener.type);
		assertSame(graph, listener.graphSource);

		listener.reset();
		graph.setDescription("my descr 1  \n");
		assertEquals("my descr 1", graph.getDescription());
		assertEquals(VISUAL_EDITED, listener.type);
		assertSame(graph, listener.graphSource);

		listener.reset();
		graph.setDescription("my descr 1");
		assertEquals("my descr 1", graph.getDescription());
		assertNull(listener.type);
		assertNull(listener.graphSource);
	}

	@Test
	public final void testSetEdgeTypeEmpty() {
		try {
			graph.setEdgeType(null);
			fail();
		} catch (NullPointerException e) {
		}

		assertEquals(EdgeType.DIRECTED, graph.getEdgeType());

		listener.reset();
		graph.setEdgeType(EdgeType.UNDIRECTED);
		assertEquals(EdgeType.UNDIRECTED, graph.getEdgeType());
		assertEquals(EDITED, listener.type);
		assertSame(graph, listener.graphSource);

		listener.reset();
		graph.setEdgeType(EdgeType.UNDIRECTED);
		assertEquals(EdgeType.UNDIRECTED, graph.getEdgeType());
		assertNull(listener.type);
		assertNull(listener.graphSource);
	}

	@Test
	public final void testSetEdgeType() {
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addVertex(v3));
		assertTrue(graph.addEdge(e1, v1, v2));
		assertTrue(graph.addEdge(e2, v1, v3));

		assertEquals(2, graph.getEdgeCount());
		assertEquals(0, graph.getEdgeCount(EdgeType.UNDIRECTED));
		assertEquals(2, graph.getEdgeCount(EdgeType.DIRECTED));

		graph.setEdgeType(EdgeType.UNDIRECTED);
		assertEquals(2, graph.getEdgeCount());
		assertEquals(2, graph.getEdgeCount(EdgeType.UNDIRECTED));
		assertEquals(0, graph.getEdgeCount(EdgeType.DIRECTED));
		assertEquals(graph.getInEdges(v2).iterator().next(), e1);
		assertEquals(graph.getInEdges(v3).iterator().next(), e2);
	}

	@Test
	public final void testAddVertex() {
		v1.setName("XXX");
		v1.setStart(true);
		v1.setEnd(true);
		v3.setName("XXX");

		assertTrue(graph.isEmpty());
		assertEquals(0, graph.getVertexCount());
		assertEquals("XXX", v1.getName());
		assertNotEquals("XXX", v2.getName());
		assertEquals("XXX", v3.getName());
		assertTrue(v1.isStart());
		assertFalse(v2.isStart());
		assertFalse(v3.isStart());
		assertTrue(v1.isEnd());
		assertFalse(v2.isEnd());
		assertFalse(v3.isEnd());

		listener.reset();
		assertTrue(graph.addVertex(v1));
		assertFalse(graph.isEmpty());
		assertEquals(1, graph.getVertexCount());
		assertEquals(ADDED, listener.type);
		assertSame(v1, listener.itemSource);
		assertEquals("XXX", v1.getName());
		assertNotEquals("XXX", v2.getName());
		assertEquals("XXX", v3.getName());
		assertTrue(v1.isStart());
		assertFalse(v2.isStart());
		assertFalse(v3.isStart());
		assertTrue(v1.isEnd());
		assertFalse(v2.isEnd());
		assertFalse(v3.isEnd());

		listener.reset();
		assertFalse(graph.addVertex(v1));
		assertEquals(1, graph.getVertexCount());
		assertNull(listener.type);
		assertNull(listener.itemSource);
		assertEquals("XXX", v1.getName());
		assertNotEquals("XXX", v2.getName());
		assertEquals("XXX", v3.getName());
		assertTrue(v1.isStart());
		assertFalse(v2.isStart());
		assertFalse(v3.isStart());
		assertTrue(v1.isEnd());
		assertFalse(v2.isEnd());
		assertFalse(v3.isEnd());

		listener.reset();
		v2.setStart(true);
		assertTrue(graph.addVertex(v2));
		assertFalse(graph.isEmpty());
		assertEquals(2, graph.getVertexCount());
		assertEquals(ADDED, listener.type);
		assertSame(v2, listener.itemSource);
		assertEquals("XXX", v1.getName());
		assertNotEquals("XXX", v2.getName());
		assertEquals("XXX", v3.getName());
		assertFalse(v1.isStart());
		assertTrue(v2.isStart());
		assertFalse(v3.isStart());
		assertTrue(v1.isEnd());
		assertFalse(v2.isEnd());
		assertFalse(v3.isEnd());

		listener.reset();
		v3.setEnd(true);
		assertTrue(graph.addVertex(v3));
		assertFalse(graph.isEmpty());
		assertEquals(3, graph.getVertexCount());
		assertEquals(ADDED, listener.type);
		assertSame(v3, listener.itemSource);
		assertEquals("XXX", v1.getName());
		assertNotEquals("XXX", v2.getName());
		assertNotEquals("XXX", v3.getName());
		assertFalse(v1.isStart());
		assertTrue(v2.isStart());
		assertFalse(v3.isStart());
		assertFalse(v1.isEnd());
		assertFalse(v2.isEnd());
		assertTrue(v3.isEnd());
	}

	@Test
	public final void testRemoveVertex() {
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addVertex(v3));

		listener.reset();
		assertTrue(graph.removeVertex(v3));
		assertFalse(graph.isEmpty());
		assertEquals(2, graph.getVertexCount());
		assertEquals(REMOVED, listener.type);
		assertSame(v3, listener.itemSource);

		listener.reset();
		assertFalse(graph.removeVertex(v3));
		assertFalse(graph.isEmpty());
		assertEquals(2, graph.getVertexCount());
		assertNull(listener.type);
		assertNull(listener.itemSource);

		listener.reset();
		assertTrue(graph.removeVertex(v2));
		assertFalse(graph.isEmpty());
		assertEquals(1, graph.getVertexCount());
		assertEquals(REMOVED, listener.type);
		assertSame(v2, listener.itemSource);

		listener.reset();
		assertTrue(graph.removeVertex(v1));
		assertTrue(graph.isEmpty());
		assertEquals(0, graph.getVertexCount());
		assertEquals(REMOVED, listener.type);
		assertSame(v1, listener.itemSource);

		v1.setName("XXX");
		v1.setStart(true);
		v1.setEnd(true);
		v2.setStart(true);
		v3.setName("XXX");
		v3.setEnd(true);

		assertEquals("XXX", v1.getName());
		assertNotEquals("XXX", v2.getName());
		assertEquals("XXX", v3.getName());
		assertTrue(v1.isStart());
		assertTrue(v2.isStart());
		assertFalse(v3.isStart());
		assertTrue(v1.isEnd());
		assertFalse(v2.isEnd());
		assertTrue(v3.isEnd());
	}

	@Test
	public final void testAddEditGraphEventListener() {
		assertEquals(1, graph.getEditGraphEventListeners().length);
		assertEquals(listener, graph.getEditGraphEventListeners()[0]);
		assertFalse(v1.removeEditGraphEventListeners(listener));

		graph.addVertex(v1);
		assertTrue(v1.removeEditGraphEventListeners(listener));

		v1.addEditGraphEventListeners(listener);
		assertTrue(v1.removeEditGraphEventListeners(listener));

		graph.addEditGraphEventListener(listener);
		assertEquals(2, graph.getEditGraphEventListeners().length);
		assertEquals(listener, graph.getEditGraphEventListeners()[1]);
	}

	@Test
	public final void testSetItemName() {
		graph.addVertex(v1);
		graph.addVertex(v3);
		graph.addEdge(e1, v1, v2);

		listener.reset();
		v1.setName("XXX");
		assertEquals(EDITED, listener.type);
		assertSame(v1, listener.itemSource);
		assertEquals("XXX", v1.getName());
		assertNotEquals("XXX", v3.getName());
		assertNotEquals("XXX", e1.getName());

		listener.reset();
		v3.setName("XXX");
		assertEquals(EDITED, listener.type);
		assertSame(v3, listener.itemSource);
		assertEquals("XXX", v1.getName());
		assertNotEquals("XXX", v3.getName());

		listener.reset();
		e1.setName("XXX");
		assertEquals(EDITED, listener.type);
		assertSame(e1, listener.itemSource);
		assertEquals("XXX", v1.getName());
		assertNotEquals("XXX", e1.getName());
	}

	@Test
	public final void testSetVertexStart() {
		graph.addVertex(v1);
		graph.addVertex(v2);

		listener.reset();
		v1.setStart(true);
		assertTrue(v1.isStart());
		assertFalse(v2.isStart());
		assertEquals(START_EDITED, listener.type);
		assertSame(v1, listener.itemSource);

		listener.reset();
		v2.setStart(true);
		assertFalse(v1.isStart());
		assertTrue(v2.isStart());
		assertEquals(START_EDITED, listener.type);
		assertSame(v2, listener.itemSource);
	}

	@Test
	public final void testSetVertexEnd() {
		graph.addVertex(v1);
		graph.addVertex(v2);

		listener.reset();
		v1.setEnd(true);
		assertTrue(v1.isEnd());
		assertFalse(v2.isEnd());
		assertEquals(END_EDITED, listener.type);
		assertSame(v1, listener.itemSource);

		listener.reset();
		v2.setEnd(true);
		assertFalse(v1.isEnd());
		assertTrue(v2.isEnd());
		assertEquals(END_EDITED, listener.type);
		assertSame(v2, listener.itemSource);
	}

	@Test
	public final void testSetEdgeWeight() {
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addEdge(e1, v1, v2);

		listener.reset();
		e1.setWeight(2.0);
		assertEquals(2.0, e1.getWeight(), DELTA);
		assertEquals(EDITED, listener.type);
		assertSame(e1, listener.itemSource);
	}

	@Test
	public final void testVisualEDited() {
		graph.addVertex(v1);

		listener.reset();
		v1.setLocation(new Point(new Point(2, 1)));
		assertEquals(new Point(2, 1), v1.getLocation());
		assertEquals(VISUAL_EDITED, listener.type);
		assertSame(v1, listener.itemSource);

		listener.reset();
		v1.setHeight(3.0);
		assertEquals(3.0, v1.getHeight(), DELTA);
		assertEquals(VISUAL_EDITED, listener.type);
		assertSame(v1, listener.itemSource);

		listener.reset();
		v1.setWidth(4.0);
		assertEquals(4.0, v1.getWidth(), DELTA);
		assertEquals(VISUAL_EDITED, listener.type);
		assertSame(v1, listener.itemSource);
	}

	@Test
	public final void testClear() {
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);
		graph.addEdge(e1, v1, v2);
		graph.addEdge(e2, v1, v3);

		assertFalse(graph.isEmpty());
		assertEquals(3, graph.getVertexCount());
		assertEquals(2, graph.getEdgeCount());

		graph.clear();
		assertTrue(graph.isEmpty());
		assertEquals(0, graph.getVertexCount());
		assertEquals(0, graph.getEdgeCount());
	}

	@Test
	public final void testAddEdge() {
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);

		e1.setName("XXX");
		e2.setName("XXX");

		assertEquals(0, graph.getEdgeCount());
		assertEquals("XXX", e1.getName());
		assertEquals("XXX", e2.getName());

		listener.reset();
		assertTrue(graph.addEdge(e1, v1, v2));
		assertEquals(1, graph.getEdgeCount());
		assertEquals(ADDED, listener.type);
		assertSame(e1, listener.itemSource);
		assertEquals("XXX", e1.getName());
		assertEquals("XXX", e2.getName());

		listener.reset();
		assertFalse(graph.addEdge(e1, v1, v2));
		assertEquals(1, graph.getEdgeCount());
		assertNull(listener.type);
		assertNull(listener.itemSource);
		assertFalse(graph.addEdge(e2, v1, v2));

		listener.reset();
		assertTrue(graph.addEdge(e2, v1, v3, EdgeType.UNDIRECTED));
		assertEquals(2, graph.getEdgeCount());
		assertEquals(0, graph.getEdgeCount(EdgeType.UNDIRECTED));
		assertEquals(2, graph.getEdgeCount(EdgeType.DIRECTED));
		assertEquals(ADDED, listener.type);
		assertSame(e2, listener.itemSource);
		assertEquals("XXX", e1.getName());
		assertNotEquals("XXX", e2.getName());

	}

	@Test
	public final void testRemoveEdge() {
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addVertex(v3));
		assertTrue(graph.addEdge(e1, v1, v2));
		assertTrue(graph.addEdge(e2, v1, v3));

		listener.reset();
		assertTrue(graph.removeEdge(e2));
		assertFalse(graph.isEmpty());
		assertEquals(1, graph.getEdgeCount());
		assertEquals(REMOVED, listener.type);
		assertSame(e2, listener.itemSource);

		listener.reset();
		assertFalse(graph.removeEdge(e2));
		assertFalse(graph.isEmpty());
		assertEquals(1, graph.getEdgeCount());
		assertNull(listener.type);
		assertNull(listener.itemSource);

		listener.reset();
		assertTrue(graph.removeEdge(e1));
		assertFalse(graph.isEmpty());
		assertEquals(0, graph.getEdgeCount());
		assertEquals(REMOVED, listener.type);
		assertSame(e1, listener.itemSource);
	}

	@Test
	public final void testGravisGraph() {
		v1.setName("XXX");
		v1.setStart(true);
		v1.setEnd(true);
		v2.setStart(true);
		v3.setName("XXX");
		v3.setEnd(true);

		Graph<IVertex, IEdge> tempGraph = new SparseGraph<IVertex, IEdge>();
		assertTrue(tempGraph.addVertex(v1));
		assertTrue(tempGraph.addVertex(v2));
		assertTrue(tempGraph.addVertex(v3));
		assertTrue(tempGraph.addEdge(e1, v1, v2, EdgeType.DIRECTED));
		assertTrue(tempGraph.addEdge(e2, v1, v3, EdgeType.DIRECTED));

		assertEquals(3, tempGraph.getVertexCount());
		assertEquals(2, tempGraph.getEdgeCount());
		assertEquals(0, tempGraph.getEdgeCount(EdgeType.UNDIRECTED));
		assertEquals(2, tempGraph.getEdgeCount(EdgeType.DIRECTED));

		gravisGraph = new GravisGraph(tempGraph, EdgeType.UNDIRECTED);

		assertEquals(3, tempGraph.getVertexCount());
		assertEquals(2, tempGraph.getEdgeCount());
		assertEquals(2, tempGraph.getEdgeCount(EdgeType.UNDIRECTED));
		assertEquals(0, tempGraph.getEdgeCount(EdgeType.DIRECTED));

		assertEquals(3, tempGraph.getVertexCount());
		assertEquals(2, tempGraph.getEdgeCount());
		assertEquals(2, tempGraph.getEdgeCount(EdgeType.UNDIRECTED));
		assertEquals(0, tempGraph.getEdgeCount(EdgeType.DIRECTED));

		assertTrue((v1.getName().equals("XXX") && !v3.getName().equals("XXX"))
				|| (!v1.getName().equals("XXX") && v3.getName().equals("XXX")));
		assertTrue((v1.isStart() && !v2.isStart())
				|| (!v1.isStart() && v2.isStart()));
		assertTrue((v1.isEnd() && !v3.isEnd()) || (!v1.isEnd() && v3.isEnd()));
	}

	@Test
	public final void testContainsName() {
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addVertex(v3));
		assertTrue(graph.addEdge(e1, v1, v2));
		assertTrue(graph.addEdge(e2, v1, v3));

		v1.setName("XXX");
		e1.setName("YYY");

		assertFalse(graph.containsEdgeName("XXX"));
		assertTrue(graph.containsEdgeName("YYY"));
		assertTrue(graph.containsVertexName("XXX"));
		assertFalse(graph.containsVertexName("YYY"));
		assertTrue(graph.containsItemName("XXX"));
		assertTrue(graph.containsItemName("YYY"));
	}

	@Test
	public final void testResetItemHelperVars() {
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addVertex(v3));
		assertTrue(graph.addEdge(e1, v1, v2));
		assertTrue(graph.addEdge(e2, v1, v3));

		v1.setDone(true);
		v1.setValue(v1);
		e1.setDone(true);
		e1.setValue(e1);

		graph.resetItemHelperVars();

		assertFalse(v1.isDone());
		assertNull(v1.getValue());
		assertFalse(e1.isDone());
		assertNull(e1.getValue());

		v1.setDone(true);
		v1.setValue(v1);
		e1.setDone(true);
		e1.setValue(e1);
		
		assertTrue(v1.isDone());
		assertTrue(e1.isDone());
		assertEquals(v1, v1.getValue());
		assertEquals(e1, e1.getValue());
		
		rGraph = GraphFactory.createRestrictedGraph(
				gravisGraph, sb);
		rGraph.resetItemDoneVars();
		rGraph.resetItemValueVars();
		
		assertFalse(v1.isDone());
		assertNull(v1.getValue());
		assertFalse(e1.isDone());
		assertNull(e1.getValue());
	}

	@Test
	public final void testForceStartVertex() {
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));

		assertTrue(!v1.isStart() && !v2.isStart());
		graph.forceStartVertex();
		assertTrue((v1.isStart() && !v2.isStart())
				|| (!v1.isStart() && v2.isStart()));
	}

	@Test
	public final void testGetStartVertex() {
		rGraph = GraphFactory.createRestrictedGraph(
				gravisGraph, sb);		
		assertNull(rGraph.getStartVertex());
		
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addVertex(v3));
		assertTrue(graph.addEdge(e1, v1, v2));
		assertTrue(graph.addEdge(e2, v1, v3));
		
		v1.setName("XXX");
		v2.setName("YYY");
		v3.setName("ZZZ");
		
		assertFalse(v1.isStart());
		rGraph = GraphFactory.createRestrictedGraph(
				gravisGraph, sb);	
		IRestrictedVertex start = rGraph.getStartVertex();
		assertTrue(start.isStart());
		assertTrue(v1.isStart());
		assertEquals(v1.getName(), start.getName());
	}
	
	@Test
	public final void testGetSuccPred() {
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addEdge(e1, v1, v2));
		
		v1.setName("XXX");
		v2.setName("YYY");
		e1.setName("ZZZ");
		
		rGraph = GraphFactory.createRestrictedGraph(
				gravisGraph, sb);	
		
		Iterator<? extends IRestrictedVertex> vertices = rGraph.getVertices().iterator();
		IRestrictedVertex rV1 = vertices.next();
		IRestrictedVertex rV2 = vertices.next();
		assertEquals(v2.getName(), rGraph.getSuccessors(rV1).iterator().next().getName());
		assertEquals(v1.getName(), rGraph.getPredecessors(rV2).iterator().next().getName());
	}
	
}
