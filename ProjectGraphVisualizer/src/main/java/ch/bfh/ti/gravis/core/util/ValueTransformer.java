package ch.bfh.ti.gravis.core.util;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;

import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;

/**
 * This utility class contains static methods for transforming values from one
 * data type to other data types.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public final class ValueTransformer {

	/**
	 * Rounds a double value to a double value with two fractional parts.
	 * 
	 * @param value
	 * @return rounded double value
	 */
	public static double round2Decimals(double value) {
		return Math.rint(value * 100.0) / 100.0;
	}

	/**
	 * Converts a list of IRestrictedGraphItem to an array of
	 * IRestrictedGraphItem.
	 * 
	 * @param itemList
	 * @return array of IRestrictedGraphItem
	 */
	public static IRestrictedGraphItem[] toArray(
			Collection<? extends IRestrictedGraphItem> itemList) {

		return itemList == null ? new IRestrictedGraphItem[0] : itemList
				.toArray(new IRestrictedGraphItem[itemList.size()]);
	}

	/**
	 * Converts a stringValue to a boolean value.
	 * 
	 * @param stringValue
	 * @return boolean value
	 */
	public static boolean toBoolean(String stringValue) {
		try {
			return Boolean.parseBoolean(stringValue);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Converts a color to a string representation.
	 * 
	 * @param color
	 * @return string representation of color
	 */
	public static String colorToString(final Color color) {
		if (color == null) {
			return GravisConstants.LIGHT_BLUE;
		}

		if (color.equals(GravisColor.GREEN)) {
			return GravisConstants.GREEN;
		} else if (color.equals(GravisColor.YELLOW)) {
			return GravisConstants.YELLOW;
		} else if (color.equals(GravisColor.BLUE)) {
			return GravisConstants.BLUE;
		} else if (color.equals(GravisColor.BLACK)) {
			return GravisConstants.BLACK;
		} else if (color.equals(GravisColor.WHITE)) {
			return GravisConstants.WHITE;
		} else if (color.equals(GravisColor.RED)) {
			return GravisConstants.RED;
		} else if (color.equals(GravisColor.LIGHT_RED)) {
			return GravisConstants.LIGHT_RED;
		} else if (color.equals(GravisColor.LIGHT_YELLOW)) {
			return GravisConstants.LIGHT_YELLOW;
		} else if (color.equals(GravisColor.LIGHT_GREEN)) {
			return GravisConstants.LIGHT_GREEN;
		} else if (color.equals(GravisColor.LIGHT_BLUE)) {
			return GravisConstants.LIGHT_BLUE;
		} else if (color.equals(GravisColor.LESS_BLUE)) {
			return GravisConstants.LESS_BLUE;
		} else if (color.equals(GravisColor.LESS_LIGHT_BLUE)) {
			return GravisConstants.LESS_LIGHT_BLUE;
		} else {
			return GravisConstants.LIGHT_BLUE;
		}
	}

	/**
	 * Converts a stringValue to a double value.
	 * 
	 * @param stringValue
	 * @return double value
	 */
	public static double toDouble(String stringValue) {
		try {
			return Double.parseDouble(stringValue);
		} catch (Exception e) {
			return Double.NaN;
		}
	}

	/**
	 * Converts two string values to a Point2D object.
	 * 
	 * @param xValue
	 * @param yValue
	 * @return an instance of Point2D
	 */
	public static Point2D toPoint(String xValue, String yValue) {
		try {
			Point point = new Point();
			// double values are rounded to int
			point.setLocation(Double.parseDouble(xValue),
					Double.parseDouble(yValue));
			return point;
		} catch (Exception e) {
			return new Point(GravisConstants.V_LOC_X_DEFAULT,
					GravisConstants.V_LOC_Y_DEFAULT);
		}
	}

	/**
	 * Converts a stringValue to a color object.
	 * 
	 * @param stringValue
	 * @return an instance of Color
	 */
	public static Color toColor(final String stringValue) {
		if (stringValue == null) {
			return GravisColor.LIGHT_BLUE;
		}

		switch (stringValue) {
		case GravisConstants.GREEN:
			return GravisColor.GREEN;
		case GravisConstants.YELLOW:
			return GravisColor.YELLOW;
		case GravisConstants.BLUE:
			return GravisColor.BLUE;
		case GravisConstants.BLACK:
			return GravisColor.BLACK;
		case GravisConstants.WHITE:
			return GravisColor.WHITE;
		case GravisConstants.RED:
			return GravisColor.RED;
		case GravisConstants.LIGHT_BLUE:
			return GravisColor.LIGHT_BLUE;
		case GravisConstants.LESS_BLUE:
			return GravisColor.LESS_BLUE;
		case GravisConstants.LESS_LIGHT_BLUE:
			return GravisColor.LESS_LIGHT_BLUE;
		case GravisConstants.LIGHT_GREEN:
			return GravisColor.LIGHT_GREEN;
		case GravisConstants.LIGHT_RED:
			return GravisColor.LIGHT_RED;
		case GravisConstants.LIGHT_YELLOW:
			return GravisColor.LIGHT_YELLOW;
		default:
			return GravisColor.LIGHT_BLUE;
		}
	}

	/**
	 * Hides the default constructor.
	 */
	private ValueTransformer() {
	}
}
