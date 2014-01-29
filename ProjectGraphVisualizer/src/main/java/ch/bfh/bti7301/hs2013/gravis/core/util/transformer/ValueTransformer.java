package ch.bfh.bti7301.hs2013.gravis.core.util.transformer;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisColor;

/**
 * A utility class, therefore serving with static method only.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * @author Roland Bruggmann (brugr9@bfh.ch)
 * 
 */
public final class ValueTransformer {

	/**
	 * A main (no-)constructor.
	 */
	private ValueTransformer() {
	}

	/**
	 * @param stringValue
	 * @return a transformed double value
	 */
	public static double transformDouble(String stringValue) {
		try {
			return Double.parseDouble(stringValue);
		} catch (Exception e) {
			return Double.NaN;
		}
	}

	/**
	 * @param stringValue
	 * @return boolean
	 */
	public static boolean transformBoolean(String stringValue) {
		try {
			return Boolean.parseBoolean(stringValue);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param stringValue
	 * @return Color
	 */
	public static Color transformStringToColor(String stringValue) {
		switch (stringValue) {
		case GravisConstants.GREEN:
			return GravisColor.GREEN;
		case GravisConstants.YELLOW:
			return GravisColor.YELLOW;
		case GravisConstants.BLUE:
			return GravisColor.BLUE;
		case GravisConstants.GRAY:
			return GravisColor.GRAY;
		case GravisConstants.ORANGE:
			return GravisColor.ORANGE;
		case GravisConstants.BLACK:
			return GravisColor.BLACK;
		case GravisConstants.WHITE:
			return GravisColor.WHITE;
		case GravisConstants.ANTIQUE:
			return GravisColor.ANTIQUE;
		default:
			return GravisColor.RED;
		}
	}
	
	/**
	 * @param color
	 * @return Color
	 */
	public static String transformColorToString(Color color) {
		if (color.equals(GravisColor.GREEN)) {
			return GravisConstants.GREEN;
		} else if (color.equals(GravisColor.YELLOW)) {
			return GravisConstants.YELLOW;
		} else if (color.equals(GravisColor.BLUE)) {
			return GravisConstants.BLUE;
		} else if (color.equals(GravisColor.GRAY)) {
			return GravisConstants.GRAY;
		} else if (color.equals(GravisColor.ORANGE)) {
			return GravisConstants.ORANGE;
		} else if (color.equals(GravisColor.BLACK)) {
			return GravisConstants.BLACK;
		} else if (color.equals(GravisColor.WHITE)) {
			return GravisConstants.WHITE;
		} else if (color.equals(GravisColor.ANTIQUE)) {
			return GravisConstants.ANTIQUE;
		} else {
			return GravisConstants.RED;
		}
	}

	/**
	 * @param xValue
	 * @param yValue
	 * @return Point2D
	 */
	public static Point2D transformLocation(String xValue, String yValue) {
		try {
			return new Point(Integer.parseInt(xValue), Integer.parseInt(yValue));
		} catch (Exception e) {
			return new Point();
		}
	}

	/**
	 * @param value
	 * @return double
	 */
	public static double round2Decimals(double value) {
		return Math.rint(value * 100.0) / 100.0;
	}

	/**
	 * @param itemList
	 * @return IRestrictedGraphItem[]
	 */
	public static IRestrictedGraphItem[] toArray(List<IRestrictedGraphItem> itemList) {
		return itemList.toArray(new IRestrictedGraphItem[itemList.size()]);
	}

	/**
	 * Transforms a double value to a string-representation: 
	 * If the double value is a whole number, the value is first converted to a int.
	 * If the double value has fractional parts, the value is rounded with round2Decimals().
	 * 
	 * @param value
	 * @return String
	 */
	public static String transformNumberToString(double value) {
		return Double.compare(round2Decimals(value), (int) Math.round(value)) == 0 ? 
				String.valueOf((int) Math.round(value)) : String.valueOf(round2Decimals(value));
	}
}
