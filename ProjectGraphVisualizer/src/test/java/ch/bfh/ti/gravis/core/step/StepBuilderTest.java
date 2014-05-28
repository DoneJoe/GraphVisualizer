package ch.bfh.ti.gravis.core.step;

import static org.junit.Assert.*;
import static ch.bfh.ti.gravis.core.graph.item.ItemState.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
import ch.bfh.ti.gravis.core.step.IStep;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import ch.bfh.ti.gravis.core.step.IStepResult;
import ch.bfh.ti.gravis.core.step.StepBuilder;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class StepBuilderTest {

	private static IStepRecorder rec;
	private static StepBuilder sb;
	private static IRestrictedVertex[] rVertices;
	private static IRestrictedEdge[] rEdges;
	private static IVertex[] vertices;
	private static IEdge[] edges;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		GraphIOManager gm = new GraphIOManager();
		IGravisGraph g = gm.loadGraph(new File("junit_graphs/JUnit_Dir_Sample_Graph.graphml"));
		sb = new StepBuilder();
		IRestrictedGraph rg = GraphFactory.createRestrictedGraph(g, sb);
		rec = StepBuilder.createStepRecorder(rg);
		
		Collection<? extends IRestrictedVertex> rvColl = rg.getVertices();
		rVertices = rvColl
				.toArray(new IRestrictedVertex[rvColl.size()]);
		Collection<? extends IRestrictedEdge> reColl = rg.getEdges();
		rEdges = reColl.toArray(new IRestrictedEdge[reColl
				.size()]);
		
		Collection<? extends IVertex> vColl = g.getVertices();
		vertices = vColl.toArray(new IVertex[vColl.size()]);		
		Arrays.sort(vertices, new ItemNameComparator());
		Collection<? extends IEdge> eColl = g.getEdges();
		edges = eColl.toArray(new IEdge[eColl.size()]);
		Arrays.sort(edges, new ItemNameComparator());
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
	 * {@link ch.bfh.ti.gravis.core.step.StepBuilder#createStepList()} .
	 * 
	 * @throws GraphIOException
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testCreateStepList() throws GraphIOException, FileNotFoundException {
		assertEquals("", rVertices[0].getNewComment());
		assertEquals(Double.NaN, rVertices[0].getCurrentResult(), 0.01);
		assertEquals(INITIAL, rVertices[0].getCurrentState());		
		
		rec.item(rVertices[0]).cmt("cmt1").res(0.0).add();
		rec.item(rVertices[1]).cmt("cmt2").res(Double.POSITIVE_INFINITY).add();
		rec.item(rVertices[0]).app("app").state(ACTIVATED).tag().cmtOk().add();
		rec.save();
		rec.item(rEdges[0]).state(VISITED).cmtOk().tag().add();
		rec.item(rVertices[1]).state(VISITED).cmtOk().cmt("visit e1").tag().
			res(10.0).value(rVertices[0]).add();
		rec.save();

		List<IStep> stepList = sb.createStepList();
		IStepResult res1 = stepList.get(0).execute();
		IStepResult res2 = stepList.get(1).execute();

		assertEquals(ACTIVATED.getMessage(vertices[0]) + "cmt1appcmt2", res1.getComment());
		assertEquals("", rVertices[0].getNewComment());
		assertEquals(0.0, rVertices[0].getCurrentResult(), 0.01);
		assertEquals(ACTIVATED, rVertices[0].getCurrentState());

		assertEquals(VISITED.getMessage(edges[0]) + VISITED.getMessage(vertices[1]) +
				"visit e1",	res2.getComment());
		assertEquals(true, edges[0].isCurrentTagged());
		assertEquals(VISITED, edges[0].getCurrentState());
		assertEquals(false, edges[0].isCurrentDashed());
		assertEquals(VISITED.getFillColor(edges[0]), edges[0].getCurrentColor());
		assertEquals(VISITED, vertices[1].getCurrentState());
		assertEquals(10.0, vertices[1].getCurrentResult(), 0.01);

		res2 = stepList.get(1).unExecute();
		assertEquals(VISITED.getMessage(edges[0]) + VISITED.getMessage(vertices[1]) +
				"visit e1", res2.getComment());
	}

	// TODO implement StepBuilderTest

}
