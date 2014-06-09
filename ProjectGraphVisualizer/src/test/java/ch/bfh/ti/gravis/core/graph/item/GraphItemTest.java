package ch.bfh.ti.gravis.core.graph.item;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.bfh.ti.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;

import static ch.bfh.ti.gravis.core.graph.item.ItemState.*;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GraphItemTest {

	private static final double DELTA = 0.0001;
	
	private static VertexFactory vertexFactory;
	private static EdgeFactory edgeFactory;

	private static IVertex vertex;
	private static IEdge edge;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vertexFactory = new VertexFactory();
		edgeFactory = new EdgeFactory();
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
		vertex = vertexFactory.create();
		edge = edgeFactory.create();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewComment(java.lang.String)}
	 * .
	 */
	@Test
	public final void testSetNewComment() {
		assertEquals("", vertex.getNewComment());
		assertEquals("", edge.getNewComment());

		vertex.setNewComment("cmt1");
		edge.setNewComment("cmt2");

		assertEquals("cmt1", vertex.getNewComment());
		assertEquals("cmt2", edge.getNewComment());

		vertex.setStateCommentEnabled(true);
		edge.setStateCommentEnabled(true);

		assertEquals(INITIAL.getMessage(vertex) + "cmt1",
				vertex.getNewComment());
		assertEquals(INITIAL.getMessage(edge) + "cmt2", edge.getNewComment());
		
		vertex.resetNewVariables();
		edge.resetNewVariables();
		
		assertEquals("", vertex.getNewComment());
		assertEquals("", edge.getNewComment());
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewDashed(java.lang.Boolean)}
	 * .
	 */
	@Test
	public final void testSetNewDashed() {
		assertEquals(false, vertex.isNewDashed());
		assertEquals(false, edge.isNewDashed());

		vertex.setNewDashed(true);
		edge.setNewDashed(true);
		
		assertEquals(true, vertex.isNewDashed());
		assertEquals(true, edge.isNewDashed());
		
		vertex.resetNewVariables();
		edge.resetNewVariables();
		
		assertEquals(false, vertex.isNewDashed());
		assertEquals(false, edge.isNewDashed());
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewResult(double)}
	 * .
	 */
	@Test
	public final void testSetNewResult() {
		assertEquals(Double.NaN, vertex.getNewResult(), DELTA);
		assertEquals(Double.NaN, edge.getNewResult(), DELTA);

		vertex.setNewResult(2.0);
		edge.setNewResult(2.0);
		
		assertEquals(2.0, vertex.getNewResult(), DELTA);
		assertEquals(2.0, edge.getNewResult(), DELTA);
		
		vertex.resetNewVariables();
		edge.resetNewVariables();
		
		assertEquals(Double.NaN, vertex.getNewResult(), DELTA);
		assertEquals(Double.NaN, edge.getNewResult(), DELTA);
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewState(ch.bfh.ti.gravis.core.graph.item.ItemState)}
	 * .
	 */
	@Test
	public final void testSetNewState() {
		assertEquals(INITIAL, vertex.getNewState());
		assertEquals(INITIAL, edge.getNewState());

		vertex.setNewState(VISITED);
		edge.setNewState(VISITED);
		
		assertEquals(VISITED, vertex.getNewState());
		assertEquals(VISITED, edge.getNewState());
		
		vertex.resetNewVariables();
		edge.resetNewVariables();
		
		assertEquals(INITIAL, vertex.getNewState());
		assertEquals(INITIAL, edge.getNewState());
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewTagged(java.lang.Boolean)}
	 * .
	 */
	@Test
	public final void testSetNewTagged() {
		assertEquals(false, vertex.isNewTagged());
		assertEquals(false, edge.isNewTagged());

		vertex.setNewTagged(true);
		edge.setNewTagged(true);
		
		assertEquals(true, vertex.isNewTagged());
		assertEquals(true, edge.isNewTagged());
		
		vertex.resetNewVariables();
		edge.resetNewVariables();
		
		assertEquals(false, vertex.isNewTagged());
		assertEquals(false, edge.isNewTagged());
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewVisible(java.lang.Boolean)}
	 * .
	 */
	@Test
	public final void testSetNewVisible() {
		assertEquals(true, vertex.isNewVisible());
		assertEquals(true, edge.isNewVisible());

		vertex.setNewVisible(false);
		edge.setNewVisible(false);
		
		assertEquals(false, vertex.isNewVisible());
		assertEquals(false, edge.isNewVisible());
		
		vertex.resetNewVariables();
		edge.resetNewVariables();
		
		assertEquals(true, vertex.isNewVisible());
		assertEquals(true, edge.isNewVisible());
	}

}
