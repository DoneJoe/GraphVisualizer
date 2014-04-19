package ch.bfh.ti.gravis.core.step;

import static org.junit.Assert.*;
import static ch.bfh.ti.gravis.core.graph.item.ItemState.ACTIVATION;
import static ch.bfh.ti.gravis.core.util.ValueTransformer.toArray;

import java.io.File;
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
import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.step.IStep;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import ch.bfh.ti.gravis.core.step.IStepResult;
import ch.bfh.ti.gravis.core.step.StepBuilder;
import ch.bfh.ti.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class StepBuilderTest {

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
	 * {@link ch.bfh.ti.gravis.core.step.StepBuilder#createStepRecorder(ch.bfh.ti.gravis.core.graph.IRestrictedGraph)}
	 * .
	 */
	@Test
	public void testCreateStepRecorder() {
		// fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.step.StepBuilder#StepBuilder()}.
	 */
	@Test
	public void testStepBuilder() {
		// fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.step.StepBuilder#addStep(ch.bfh.ti.gravis.core.graph.item.IGraphItem[])}
	 * .
	 */
	@Test
	public void testAddStep() {
		// fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.step.StepBuilder#createStepList()}
	 * .
	 */
	@Test
	public void testCreateStepList() {
		try {
			GraphIOManager gm = new GraphIOManager();
			IGravisGraph g = gm.loadGraph(new File(
					GravisConstants.TEMPLATES_DIR
							+ "/DijkstraSampleGraph1.graphml"));
			StepBuilder sb = new StepBuilder();
			IRestrictedGraph rg = GraphFactory.createRestrictedGraph(g, sb);
			IStepRecorder rec = StepBuilder.createStepRecorder(rg);
			Collection<? extends IRestrictedVertex> vColl = rg.getVertices();
			IRestrictedVertex[] vertices = vColl
					.toArray(new IRestrictedVertex[vColl.size()]);
			Collection<? extends IRestrictedEdge> eColl = rg.getEdges();
			IRestrictedEdge[] edges = eColl.toArray(new IRestrictedEdge[eColl
					.size()]);

			// rec.item(startVertex).cmt(cmt).res(0.0).add();
			// rec.item(vertex).cmt(cmt).res(Double.POSITIVE_INFINITY).add();
			// selectedVertex.appendComment(cmt);
			// rec.item(selectedVertex).cmt(cmt).state(ACTIVATION).cmtOk()
			// .res(res).tag().add();

			rec.item(vertices[0]).cmt("cmt1").res(5.0).add();
			rec.save();

			List<IStep> stepList = sb.createStepList();
			IStepResult res = stepList.get(0).execute();

			assertEquals("cmt1", res.getComment());
			assertEquals(5.0, vertices[0].getCurrentResult(), 0.01);
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

}
