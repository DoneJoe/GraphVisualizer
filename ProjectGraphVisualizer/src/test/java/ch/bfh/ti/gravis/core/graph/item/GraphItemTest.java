package ch.bfh.ti.gravis.core.graph.item;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.bfh.ti.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.ti.gravis.core.util.GravisColor;
import ch.bfh.ti.gravis.core.util.GravisConstants;
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
	private static IRestrictedVertex rV;
	private static IRestrictedEdge rE;

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
	@Before
	public void setUp() throws Exception {
		vertex = vertexFactory.create();
		edge = edgeFactory.create();
		rV = VertexFactory.createRestrictedVertex(vertex);
		rE = EdgeFactory.createRestrictedEdge(edge);
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewComment(java.lang.String)}
	 * .
	 */
	@Test
	public final void testSetNewComment() {
		assertEquals("", rV.getNewComment());
		assertEquals("", rE.getNewComment());
		assertEquals(false, rV.isStateCommentEnabled());
		assertEquals(false, rE.isStateCommentEnabled());

		rV.setNewComment("cmt1");
		rE.setNewComment("cmt2");

		assertEquals("cmt1", rV.getNewComment());
		assertEquals("cmt2", rE.getNewComment());

		rV.setStateCommentEnabled(true);
		rE.setStateCommentEnabled(true);

		assertEquals(INITIAL.getMessage(vertex) + "cmt1",
				rV.getNewComment());
		assertEquals(INITIAL.getMessage(edge) + "cmt2", rE.getNewComment());
		assertEquals(true, rV.isStateCommentEnabled());
		assertEquals(true, rE.isStateCommentEnabled());

		rV.appendComment("cmt12");
		rE.appendComment("cmt22");

		assertEquals(INITIAL.getMessage(vertex) + "cmt1cmt12",
				rV.getNewComment());
		assertEquals(INITIAL.getMessage(edge) + "cmt2cmt22",
				rE.getNewComment());

		vertex.resetNewVariables();
		edge.resetNewVariables();

		assertEquals("", rV.getNewComment());
		assertEquals("", rE.getNewComment());
		assertEquals(false, rV.isStateCommentEnabled());
		assertEquals(false, rE.isStateCommentEnabled());
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewDashed(java.lang.Boolean)}
	 * .
	 */
	@Test
	public final void testSetNewDashed() {
		assertEquals(false, rV.isNewDashed());
		assertEquals(false, rE.isNewDashed());

		rV.setNewDashed(null);
		rE.setNewDashed(null);

		assertEquals(false, rV.isNewDashed());
		assertEquals(false, rE.isNewDashed());

		rV.setNewDashed(true);
		rE.setNewDashed(true);

		assertEquals(true, rV.isNewDashed());
		assertEquals(true, rE.isNewDashed());

		vertex.resetNewVariables();
		edge.resetNewVariables();

		assertEquals(false, rV.isNewDashed());
		assertEquals(false, rE.isNewDashed());
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewResult(double)}
	 * .
	 */
	@Test
	public final void testSetNewResult() {
		assertEquals(Double.NaN, rV.getNewResult(), DELTA);
		assertEquals(Double.NaN, rE.getNewResult(), DELTA);

		rV.setNewResult(2.0);
		rE.setNewResult(2.0);

		assertEquals(2.0, rV.getNewResult(), DELTA);
		assertEquals(2.0, rE.getNewResult(), DELTA);

		vertex.resetNewVariables();
		edge.resetNewVariables();

		assertEquals(Double.NaN, rV.getNewResult(), DELTA);
		assertEquals(Double.NaN, rE.getNewResult(), DELTA);
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewState(ch.bfh.ti.gravis.core.graph.item.ItemState)}
	 * .
	 */
	@Test
	public final void testSetNewState() {
		assertEquals(INITIAL, rV.getNewState());
		assertEquals(INITIAL, rE.getNewState());

		rV.setNewState(null);
		rE.setNewState(null);

		assertEquals(INITIAL, rV.getNewState());
		assertEquals(INITIAL, rE.getNewState());

		rV.setNewState(VISITED);
		rE.setNewState(VISITED);

		assertEquals(VISITED, rV.getNewState());
		assertEquals(VISITED, rE.getNewState());

		vertex.resetNewVariables();
		edge.resetNewVariables();

		assertEquals(INITIAL, rV.getNewState());
		assertEquals(INITIAL, rE.getNewState());
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewTagged(java.lang.Boolean)}
	 * .
	 */
	@Test
	public final void testSetNewTagged() {
		assertEquals(false, rV.isNewTagged());
		assertEquals(false, rE.isNewTagged());

		rV.setNewTagged(null);
		rE.setNewTagged(null);

		assertEquals(false, rV.isNewTagged());
		assertEquals(false, rE.isNewTagged());

		rV.setNewTagged(true);
		rE.setNewTagged(true);

		assertEquals(true, rV.isNewTagged());
		assertEquals(true, rE.isNewTagged());

		vertex.resetNewVariables();
		edge.resetNewVariables();

		assertEquals(false, rV.isNewTagged());
		assertEquals(false, rE.isNewTagged());
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#setNewVisible(java.lang.Boolean)}
	 * .
	 */
	@Test
	public final void testSetNewVisible() {
		assertEquals(true, rV.isNewVisible());
		assertEquals(true, rE.isNewVisible());

		rV.setNewVisible(null);
		rE.setNewVisible(null);

		assertEquals(true, rV.isNewVisible());
		assertEquals(true, rE.isNewVisible());

		rV.setNewVisible(false);
		rE.setNewVisible(false);

		assertEquals(false, rV.isNewVisible());
		assertEquals(false, rE.isNewVisible());

		vertex.resetNewVariables();
		edge.resetNewVariables();

		assertEquals(true, rV.isNewVisible());
		assertEquals(true, rE.isNewVisible());
	}

	@Test
	public final void testSetCurrentColor() {
		assertEquals(GravisConstants.V_FILL_COLOR_DEFAULT,
				vertex.getCurrentColor());
		assertEquals(GravisConstants.E_COLOR_DEFAULT, edge.getCurrentColor());

		vertex.setCurrentColor(GravisColor.LIGHT_GREEN);
		edge.setCurrentColor(GravisColor.GREEN);

		assertEquals(GravisColor.LIGHT_GREEN, vertex.getCurrentColor());
		assertEquals(GravisColor.GREEN, edge.getCurrentColor());

		vertex.setCurrentVisible(false);
		edge.setCurrentVisible(false);

		assertEquals(GravisColor.WHITE, vertex.getCurrentColor());
		assertEquals(GravisColor.WHITE, edge.getCurrentColor());

		vertex.setCurrentVisible(true);
		edge.setCurrentVisible(true);

		assertEquals(GravisColor.LIGHT_GREEN, vertex.getCurrentColor());
		assertEquals(GravisColor.GREEN, edge.getCurrentColor());
		
		try {
			vertex.setCurrentColor(null);
			fail();
		} catch (NullPointerException e) {}
		try {
			edge.setCurrentColor(null);
			fail();
		} catch (NullPointerException e) {}
	}

	@Test
	public final void testSetDone() {
		assertEquals(false, rV.isDone());
		assertEquals(false, rE.isDone());

		rV.setDone(true);
		rE.setDone(true);

		assertEquals(true, rV.isDone());
		assertEquals(true, rE.isDone());

		rV.resetHelperVariables();
		rE.resetHelperVariables();

		assertEquals(false, rV.isDone());
		assertEquals(false, rE.isDone());
	}

	@Test
	public final void testSetValue() {
		assertNull(rV.getValue());
		assertNull(rE.getValue());

		rV.setValue(rV);
		rE.setValue(rE);

		assertEquals(rV, rV.getValue());
		assertEquals(rE, rE.getValue());

		rV.resetHelperVariables();
		rE.resetHelperVariables();

		assertNull(rV.getValue());
		assertNull(rE.getValue());
	}

	@Test
	public final void testSetCurrentResult() {
		assertEquals(Double.NaN, rV.getCurrentResult(), DELTA);
		assertEquals(Double.NaN, rE.getCurrentResult(), DELTA);

		vertex.setCurrentResult(4.0);
		edge.setCurrentResult(5.0);

		assertEquals(4.0, rV.getNewResult(), DELTA);
		assertEquals(5.0, rE.getNewResult(), DELTA);
		assertEquals(4.0, rV.getCurrentResult(), DELTA);
		assertEquals(5.0, rE.getCurrentResult(), DELTA);

		vertex.resetNewVariables();
		edge.resetNewVariables();

		assertEquals(4.0, rV.getNewResult(), DELTA);
		assertEquals(5.0, rE.getNewResult(), DELTA);
		assertEquals(4.0, rV.getCurrentResult(), DELTA);
		assertEquals(5.0, rE.getCurrentResult(), DELTA);
	}

	@Test
	public final void testSetCurrentState() {
		assertEquals(INITIAL, rV.getCurrentState());
		assertEquals(INITIAL, rE.getCurrentState());
		assertTrue(rV.isInitial());
		assertTrue(rE.isInitial());

		vertex.setCurrentState(SOLVED);
		edge.setCurrentState(VISITED);

		assertEquals(SOLVED, rV.getNewState());
		assertEquals(VISITED, rE.getNewState());
		assertEquals(SOLVED, rV.getCurrentState());
		assertEquals(VISITED, rE.getCurrentState());
		assertEquals(GravisConstants.V_FILL_SOLVED_COLOR, vertex.getCurrentColor());
		assertEquals(GravisConstants.V_DRAW_SOLVED_COLOR, vertex.getCurrentDrawColor());
		assertEquals(GravisConstants.E_VISITED_COLOR, edge.getCurrentColor());
		assertTrue(rV.isSolved());
		assertTrue(rE.isVisited());

		vertex.resetNewVariables();
		edge.resetNewVariables();

		assertEquals(SOLVED, rV.getNewState());
		assertEquals(VISITED, rE.getNewState());
		assertEquals(SOLVED, rV.getCurrentState());
		assertEquals(VISITED, rE.getCurrentState());
		
		vertex.setCurrentState(ACTIVATED);
		edge.setCurrentState(DISCARDED);
		
		assertTrue(rV.isActivated());
		assertTrue(rE.isDiscarded());

		try {
			vertex.setCurrentState(null);
			fail();
		} catch (NullPointerException e) {}
		try {
			edge.setCurrentState(null);
			fail();
		} catch (NullPointerException e) {}
	}
	
	@Test
	public final void testSetCurrentDashed() {
		assertEquals(false, vertex.isCurrentDashed());
		assertEquals(false, edge.isCurrentDashed());

		vertex.setCurrentDashed(true);
		edge.setCurrentDashed(true);

		assertEquals(true, vertex.isNewDashed());
		assertEquals(true, edge.isNewDashed());
		assertEquals(true, vertex.isCurrentDashed());
		assertEquals(true, edge.isCurrentDashed());

		vertex.resetNewVariables();
		edge.resetNewVariables();

		assertEquals(true, vertex.isNewDashed());
		assertEquals(true, edge.isNewDashed());
		assertEquals(true, vertex.isCurrentDashed());
		assertEquals(true, edge.isCurrentDashed());
	}
	
	@Test
	public final void testSetCurrentTagged() {
		assertEquals(false, vertex.isCurrentTagged());
		assertEquals(false, edge.isCurrentTagged());

		vertex.setCurrentTagged(true);
		edge.setCurrentTagged(true);

		assertEquals(true, vertex.isNewTagged());
		assertEquals(true, edge.isNewTagged());
		assertEquals(true, vertex.isCurrentTagged());
		assertEquals(true, edge.isCurrentTagged());

		vertex.resetNewVariables();
		edge.resetNewVariables();

		assertEquals(true, vertex.isNewTagged());
		assertEquals(true, edge.isNewTagged());
		assertEquals(true, vertex.isCurrentTagged());
		assertEquals(true, edge.isCurrentTagged());
	}
	
	@Test
	public final void testSetCurrentVisible() {
		assertEquals(true, vertex.isCurrentVisible());
		assertEquals(true, edge.isCurrentVisible());
		
		vertex.setCurrentVisible(false);
		edge.setCurrentVisible(false);
		
		assertEquals(false, vertex.isNewVisible());
		assertEquals(false, edge.isNewVisible());
		assertEquals(false, vertex.isCurrentVisible());
		assertEquals(false, edge.isCurrentVisible());
		
		vertex.resetNewVariables();
		edge.resetNewVariables();
		
		assertEquals(false, vertex.isNewVisible());
		assertEquals(false, edge.isNewVisible());
		assertEquals(false, vertex.isCurrentVisible());
		assertEquals(false, edge.isCurrentVisible());
	}
	
	@Test
	public final void testSetName() {
		vertex.setName("V1");
		edge.setName("e1");
		
		assertEquals("V1", vertex.getName());
		assertEquals("e1", edge.getName());
		
		try {
			vertex.setName(null);
			fail();
		} catch (NullPointerException e) {}
		try {
			edge.setName(null);
			fail();
		} catch (NullPointerException e) {}
		
		vertex.setName("");
		edge.setName("");
		
		assertEquals("V1", vertex.getName());
		assertEquals("e1", edge.getName());
	}

	@Test
	public final void testSetWeight() {
		assertEquals(GravisConstants.E_WEIGHT_DEFAULT, edge.getWeight(), DELTA);
		edge.setWeight(5.0);
		assertEquals(5.0, edge.getWeight(), DELTA);
	}
	
	@Test
	public final void testSetStart() {
		assertFalse(vertex.isStart());
		vertex.setStart(true);
		assertTrue(vertex.isStart());
	}
	
	@Test
	public final void testSetEnd() {
		assertFalse(vertex.isEnd());
		vertex.setEnd(true);
		assertTrue(vertex.isEnd());
	}
	
	@Test
	public final void testSetHeight() {
		assertEquals(GravisConstants.V_HEIGHT_DEFAULT, vertex.getHeight(), DELTA);
		vertex.setHeight(6.0);
		assertEquals(6.0, vertex.getHeight(), DELTA);
	}
	
	@Test
	public final void testSetWidth() {
		assertEquals(GravisConstants.V_WIDTH_DEFAULT, vertex.getWidth(), DELTA);
		vertex.setWidth(7.0);
		assertEquals(7.0, vertex.getWidth(), DELTA);
	}
	
	@Test
	public final void testSetLocation() {
		assertEquals(new Point(GravisConstants.V_LOC_X_DEFAULT,
				GravisConstants.V_LOC_Y_DEFAULT), vertex.getLocation());
		vertex.setLocation(new Point(2, 2));
		assertEquals(new Point(2, 2), vertex.getLocation());
		
		try {
			vertex.setLocation(null);
			fail();
		} catch (NullPointerException e) {}
	}
	
}
