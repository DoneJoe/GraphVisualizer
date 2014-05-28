package ch.bfh.ti.gravis.core.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ValueTransformerTest {

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
	 * {@link ch.bfh.ti.gravis.core.util.ValueTransformer#colorToString(java.awt.Color)}
	 * .
	 */
	@Test
	public final void testTransformColorToString() {
		assertEquals(GravisConstants.LIGHT_BLUE,  
				ValueTransformer.colorToString(GravisColor.LIGHT_BLUE));
		assertEquals(GravisConstants.LIGHT_BLUE,  
				ValueTransformer.colorToString(null));
	}

	/**
	 * Test method for
	 * {@link ch.bfh.ti.gravis.core.util.ValueTransformer#toColor(java.lang.String)}
	 * .
	 */
	@Test
	public final void testTransformToColor() {
		assertEquals(GravisColor.LIGHT_BLUE,  
				ValueTransformer.toColor(GravisConstants.LIGHT_BLUE));
		assertEquals(GravisColor.LIGHT_BLUE,  
				ValueTransformer.toColor(null));
	}

	// TODO implement ValueTransformerTest

}
