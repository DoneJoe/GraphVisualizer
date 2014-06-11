package ch.bfh.ti.gravis.core.graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.step.StepBuilder;
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
	
	private static IGravisGraph gravisGraph;
	private static IEditGraphObservable graph;
	private static IRestrictedGraph rGraph;
	private static EditListener listener;
	private static StepBuilder sb;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sb = new StepBuilder();
		listener = new EditListener();
		gravisGraph = GraphFactory.createDirectedGravisGraph();
		graph = GraphFactory.createEditGraphObservable(gravisGraph, listener);
		rGraph = GraphFactory.createRestrictedGraph(gravisGraph, sb);
	}

	@Test
	public final void testSetGraphName() {
		try {
			graph.setName(null);
			fail();
		} catch (NullPointerException e) {}
		
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
		} catch (NullPointerException e) {}
		
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
	public final void testSetEdgeType() {
		try {
			graph.setEdgeType(null);
			fail();
		} catch (NullPointerException e) {}
		
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
}
