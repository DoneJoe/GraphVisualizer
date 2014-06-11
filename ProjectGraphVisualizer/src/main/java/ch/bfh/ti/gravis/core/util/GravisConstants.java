package ch.bfh.ti.gravis.core.util;

import java.awt.Color;

/**
 * Static fields for all important constants in the application.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public final class GravisConstants {

	public static final String LN = System.lineSeparator();
	public static final String SAMPLES_DIR = "sample_graphs";
	public static final String IMAGES_DIR = "/META-INF/images/";

	// string representation of the available colors in graphML format:

	public static final String GREEN = "green";
	public static final String YELLOW = "yellow";
	public static final String BLUE = "blue";
	public static final String BLACK = "black";
	public static final String RED = "red";
	public static final String WHITE = "white";
	public static final String LIGHT_GREEN = "lightGreen";
	public static final String LIGHT_YELLOW = "lightYellow";
	public static final String LIGHT_BLUE = "lightBlue";
	public static final String LESS_BLUE = "lessBlue";
	public static final String LESS_LIGHT_BLUE = "lessLightBlue";
	public static final String LIGHT_RED = "lightRed";

	// key ids in graphML format:

	public final static String G_DESCRIPTION = "description";
	public final static String E_WEIGHT = "weight";
	public final static String E_COLOR = "edgeColor";

	public final static String V_COLOR = "vertexColor";
	public final static String V_LOC_X = "vertexLocation.x";
	public final static String V_LOC_Y = "vertexLocation.y";
	public final static String V_START = "startVertex";
	public final static String V_END = "endVertex";
	public final static String V_WIDTH = "vertexWidth";
	public final static String V_HEIGHT = "vertexHeight";

	// item state colors:

	public final static Color V_FILL_INITIAL_COLOR = GravisColor.LIGHT_BLUE;
	public final static Color V_FILL_ACTIVATED_COLOR = GravisColor.LESS_LIGHT_BLUE;
	public final static Color V_FILL_VISITED_COLOR = GravisColor.LIGHT_YELLOW;
	public final static Color V_FILL_SOLVED_COLOR = GravisColor.LIGHT_GREEN;
	public final static Color V_FILL_DISCARDED_COLOR = GravisColor.LIGHT_RED;

	public final static Color V_DRAW_INITIAL_COLOR = GravisColor.BLUE;
	public final static Color V_DRAW_ACTIVATED_COLOR = GravisColor.BLUE;
	public final static Color V_DRAW_VISITED_COLOR = GravisColor.YELLOW;
	public final static Color V_DRAW_SOLVED_COLOR = GravisColor.GREEN;
	public final static Color V_DRAW_DISCARDED_COLOR = GravisColor.RED;

	public final static Color E_INITIAL_COLOR = GravisColor.BLUE;
	public final static Color E_ACTIVATED_COLOR = GravisColor.BLUE;
	public final static Color E_VISITED_COLOR = GravisColor.YELLOW;
	public final static Color E_SOLVED_COLOR = GravisColor.GREEN;
	public final static Color E_DISCARDED_COLOR = GravisColor.RED;

	// some graph item field default values:

	public final static int V_LOC_X_DEFAULT = 50;
	public final static int V_LOC_Y_DEFAULT = 50;

	public final static double V_WIDTH_DEFAULT = 40.0;
	public final static double V_HEIGHT_DEFAULT = 40.0;
	public final static double E_WEIGHT_DEFAULT = 1.0;

	public final static Color E_COLOR_DEFAULT = E_INITIAL_COLOR;
	public final static Color V_DRAW_COLOR_DEFAULT = V_DRAW_INITIAL_COLOR;
	public final static Color V_FILL_COLOR_DEFAULT = V_FILL_INITIAL_COLOR;
	public final static Color V_PICKED_COLOR = GravisColor.LESS_BLUE;
	public final static Color E_PICKED_COLOR = GravisColor.LESS_BLUE;

	// some other visualisation default values:

	public final static float V_START_DASH = 8.0f;
	public final static float V_END_DASH = 1.5f;

	public final static float STROKE_WIDTH_DEFAULT = 1.0f;
	public final static float V_TAGGED_STROKE = 1.5f;
	public final static float E_TAGGED_STROKE = 1.5f;
	public final static float MITER_LIMIT_DEFAULT = 10.0f;
	public final static float DASH_DEFAULT = 5.0f;
	public final static float DASH_PHASE_DEFAULT = 0.0f;

	/**
	 * Hide the constructor.
	 */
	private GravisConstants() {
	}

}
