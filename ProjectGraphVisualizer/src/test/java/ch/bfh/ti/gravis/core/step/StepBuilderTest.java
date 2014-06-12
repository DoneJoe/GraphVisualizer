package ch.bfh.ti.gravis.core.step;

import static org.junit.Assert.*;
import static ch.bfh.ti.gravis.core.graph.item.ItemState.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.GraphIOManager;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.graph.comparator.ItemNameComparator;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import ch.bfh.ti.gravis.core.step.StepBuilder;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class StepBuilderTest {

	private static final double DELTA = 0.0001;

	private static IStepRecorder rec;
	private static StepBuilder sb;
	private static IRestrictedVertex[] rVertices;
	private static IRestrictedEdge[] rEdges;
	private static IVertex[] vertices;
	private static IEdge[] edges;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		GraphIOManager gm = new GraphIOManager();
		IGravisGraph g = gm.loadGraph(new File(
				"junit_graphs/JUnit_Dir_Sample_Graph.graphml"));
		sb = new StepBuilder();
		IRestrictedGraph rg = GraphFactory.createRestrictedGraph(g, sb);
		rec = StepBuilder.createStepRecorder(rg);

		Collection<? extends IRestrictedVertex> rvColl = rg.getVertices();
		rVertices = rvColl.toArray(new IRestrictedVertex[rvColl.size()]);
		Collection<? extends IRestrictedEdge> reColl = rg.getEdges();
		rEdges = reColl.toArray(new IRestrictedEdge[reColl.size()]);

		Collection<? extends IVertex> vColl = g.getVertices();
		vertices = vColl.toArray(new IVertex[vColl.size()]);
		Arrays.sort(vertices, new ItemNameComparator());
		Collection<? extends IEdge> eColl = g.getEdges();
		edges = eColl.toArray(new IEdge[eColl.size()]);
		Arrays.sort(edges, new ItemNameComparator());

		rec.item(rVertices[0]).cmt("cmt1").res(0.0).add();
		rec.item(rVertices[1]).cmt("cmt2").state(SOLVED)
				.res(Double.POSITIVE_INFINITY).notVisib().tag().add();
		rec.item(rVertices[0]).app("app").state(ACTIVATED).tag().cmtOk().add();
		rec.save();
		rec.item(rEdges[0]).state(VISITED).cmtOk().tag().dash().add();
		rec.item(rVertices[1]).state(DISCARDED).cmtOk().cmt("visit e1").tag()
				.res(10.0).value(rVertices[1]).visib().notTag().dash().add();
		rec.save();
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.step.StepBuilder#createStepIterator()} .
	 * 
	 * @throws GraphIOException
	 * @throws FileNotFoundException
	 */
	@Test
	public void testCreateStepList() throws GraphIOException,
			FileNotFoundException {
		IGravisListIterator<String> stepList = sb.createStepIterator();
		String res1 = stepList.next();

		assertEquals(ACTIVATED.getMessage(vertices[0]) + "cmt1appcmt2", res1);
		assertEquals("", rVertices[0].getNewComment());
		assertEquals(0.0, rVertices[0].getCurrentResult(), DELTA);
		assertEquals(ACTIVATED, rVertices[0].getCurrentState());
		assertTrue(vertices[0].isCurrentTagged());
		assertFalse(vertices[0].isCurrentDashed());
		assertTrue(vertices[0].isCurrentVisible());

		assertEquals(Double.POSITIVE_INFINITY, rVertices[1].getCurrentResult(),
				DELTA);
		assertEquals(SOLVED, rVertices[1].getCurrentState());
		assertTrue(vertices[1].isCurrentTagged());
		assertFalse(vertices[1].isCurrentDashed());
		assertFalse(vertices[1].isCurrentVisible());

		String res2 = stepList.next();

		assertEquals(
				VISITED.getMessage(edges[0]) + DISCARDED.getMessage(vertices[1])
						+ "visit e1", res2);
		assertEquals(VISITED, edges[0].getCurrentState());
		assertEquals(VISITED.getFillColor(edges[0]), edges[0].getCurrentColor());
		assertEquals(Double.NaN, rEdges[0].getCurrentResult(), DELTA);
		assertTrue(edges[0].isCurrentTagged());
		assertTrue(edges[0].isCurrentDashed());
		assertTrue(edges[0].isCurrentVisible());
		
		assertEquals(DISCARDED, vertices[1].getCurrentState());
		assertEquals(10.0, vertices[1].getCurrentResult(), DELTA);
		assertFalse(vertices[1].isCurrentTagged());
		assertTrue(vertices[1].isCurrentDashed());
		assertTrue(vertices[1].isCurrentVisible());

		res1 = stepList.previous();
		
		assertEquals(
				VISITED.getMessage(edges[0]) + DISCARDED.getMessage(vertices[1])
						+ "visit e1", res1);		assertEquals(0.0, rVertices[0].getCurrentResult(), DELTA);
		assertEquals(ACTIVATED, rVertices[0].getCurrentState());
		assertTrue(vertices[0].isCurrentTagged());
		assertFalse(vertices[0].isCurrentDashed());
		assertTrue(vertices[0].isCurrentVisible());

		assertEquals(Double.POSITIVE_INFINITY, rVertices[1].getCurrentResult(),
				DELTA);
		assertEquals(SOLVED, rVertices[1].getCurrentState());
		assertTrue(vertices[1].isCurrentTagged());
		assertFalse(vertices[1].isCurrentDashed());
		assertFalse(vertices[1].isCurrentVisible());
		
		res2 = stepList.previous();

		assertEquals(ACTIVATED.getMessage(vertices[0]) + "cmt1appcmt2", res2);
		assertEquals(Double.NaN, rVertices[0].getCurrentResult(), DELTA);
		assertEquals(INITIAL, rVertices[0].getCurrentState());
		assertFalse(vertices[0].isCurrentTagged());
		assertFalse(vertices[0].isCurrentDashed());
		assertTrue(vertices[0].isCurrentVisible());
	}

}
