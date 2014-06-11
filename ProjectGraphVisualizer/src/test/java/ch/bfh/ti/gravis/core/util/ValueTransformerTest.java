package ch.bfh.ti.gravis.core.util;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.Test;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ValueTransformerTest {

	private static final double DELTA = 0.00001;
	
	@Test
	public final void testRound2Decimals() {
		assertEquals(1.25, ValueTransformer.round2Decimals(1.245), DELTA);
		assertEquals(1.24, ValueTransformer.round2Decimals(1.2449), DELTA);
		assertEquals(1.2, ValueTransformer.round2Decimals(1.2), DELTA);
		assertEquals(1.0, ValueTransformer.round2Decimals(1.0), DELTA);
	}
	
	@Test
	public final void testToBoolean() {
		assertTrue(ValueTransformer.toBoolean("true"));
		assertTrue(ValueTransformer.toBoolean("TRue"));
		assertFalse(ValueTransformer.toBoolean("false"));
		assertFalse(ValueTransformer.toBoolean(""));
		assertFalse(ValueTransformer.toBoolean(null));
	}
	
	@Test
	public final void testColorToString() {
		assertEquals(GravisConstants.LIGHT_BLUE,  
				ValueTransformer.colorToString(GravisColor.LIGHT_BLUE));
		assertEquals(GravisConstants.LIGHT_BLUE,  
				ValueTransformer.colorToString(null));
		assertEquals(GravisConstants.LIGHT_BLUE,  
				ValueTransformer.colorToString(Color.RED));
		assertEquals(GravisConstants.LESS_LIGHT_BLUE,  
				ValueTransformer.colorToString(GravisColor.LESS_LIGHT_BLUE));
		assertEquals(GravisConstants.LESS_BLUE,  
				ValueTransformer.colorToString(GravisColor.LESS_BLUE));
	}

	@Test
	public final void testToColor() {
		assertEquals(GravisColor.LIGHT_BLUE,  
				ValueTransformer.toColor(GravisConstants.LIGHT_BLUE));
		assertEquals(GravisColor.LIGHT_BLUE,  
				ValueTransformer.toColor(null));
		assertEquals(GravisColor.LIGHT_BLUE,  
				ValueTransformer.toColor(""));
		assertEquals(GravisColor.LESS_LIGHT_BLUE,  
				ValueTransformer.toColor(GravisConstants.LESS_LIGHT_BLUE));
		assertEquals(GravisColor.LESS_BLUE,  
				ValueTransformer.toColor(GravisConstants.LESS_BLUE));
	}

	@Test
	public final void testToDouble() {
		assertEquals(Double.NaN, ValueTransformer.toDouble(null), DELTA);
		assertEquals(1.0, ValueTransformer.toDouble("1"), DELTA);
		assertEquals(Double.NaN, ValueTransformer.toDouble("a1"), DELTA);
		assertEquals(-2.2455, ValueTransformer.toDouble("-2.2455"), DELTA);
		assertEquals(Double.NaN, ValueTransformer.toDouble(""), DELTA);
		assertEquals(Double.NaN, ValueTransformer.toDouble("1,0"), DELTA);
		assertEquals(Double.NaN, ValueTransformer.toDouble("10'0"), DELTA);
	}
	
	@Test
	public final void testToPoint() {
		Point2D point = ValueTransformer.toPoint("1.0", "2");
		assertEquals(1.0, point.getX(), DELTA);
		assertEquals(2.0, point.getY(), DELTA);
		
		point = ValueTransformer.toPoint("1.499", "1.5");
		assertEquals(1.0, point.getX(), DELTA);
		assertEquals(2.0, point.getY(), DELTA);
		
		assertEquals(new Point(GravisConstants.V_LOC_X_DEFAULT,
				GravisConstants.V_LOC_Y_DEFAULT), ValueTransformer.toPoint(null, "2.0"));
		assertEquals(new Point(GravisConstants.V_LOC_X_DEFAULT,
				GravisConstants.V_LOC_Y_DEFAULT), ValueTransformer.toPoint("2.0", null));
		assertEquals(new Point(GravisConstants.V_LOC_X_DEFAULT,
				GravisConstants.V_LOC_Y_DEFAULT), ValueTransformer.toPoint(null, null));
		assertEquals(new Point(GravisConstants.V_LOC_X_DEFAULT,
				GravisConstants.V_LOC_Y_DEFAULT), ValueTransformer.toPoint("a2", "2.0"));
		assertEquals(new Point(GravisConstants.V_LOC_X_DEFAULT,
				GravisConstants.V_LOC_Y_DEFAULT), ValueTransformer.toPoint("2.0", "a2"));
	}
	
}
