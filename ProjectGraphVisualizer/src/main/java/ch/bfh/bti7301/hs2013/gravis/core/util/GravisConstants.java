package ch.bfh.bti7301.hs2013.gravis.core.util;

import java.awt.Color;


/**
 * This class defines static fields for all important constants in the application.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public final class GravisConstants {

	public static final String TEMPLATES_DIR = "graph-templates";
	public static final String IMAGES_DIR = "/META-INF/images/";
	
	// string representation of the available colors in graphML format:
	
	public static final String GREEN = "green";
	public static final String YELLOW = "yellow";
	public static final String BLUE = "blue";
	public static final String GRAY = "gray";
	public static final String ORANGE = "orange";
	public static final String BLACK = "black";
	public static final String RED = "red";
	public static final String WHITE = "white";
	public static final String ANTIQUE = "antique";
	
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
	
	// some graph item field default values::
	
	public final static int V_LOC_X_DEFAULT = 50;
	public final static int V_LOC_Y_DEFAULT = 50;
	
	public final static double V_WIDTH_DEFAULT = 60.0;
	public final static double V_HEIGHT_DEFAULT = 40.0;
	public final static double E_WEIGHT_DEFAULT = 1.0;
	
	public final static Color E_COLOR_DEFAULT = GravisColor.BLACK;
	public final static Color V_DRAW_COLOR_DEFAULT = GravisColor.BLACK;
	public final static Color V_FILL_COLOR_DEFAULT = GravisColor.ANTIQUE;
		
	// item state colors:
	// TODO define colors
	
	public final static Color V_FILL_INITIAL_COLOR = GravisColor.ANTIQUE;
	public final static Color V_FILL_ACTIVATION_COLOR = GravisColor.BLUE;
	public final static Color V_FILL_VISIT_COLOR = GravisColor.YELLOW;
	public final static Color V_FILL_SOLUTION_COLOR = GravisColor.GREEN;
	public final static Color V_FILL_ELIMINATION_COLOR = GravisColor.RED;
	
	public final static Color V_DRAW_INITIAL_COLOR = GravisColor.BLACK;
	public final static Color V_DRAW_ACTIVATION_COLOR = GravisColor.BLACK;
	public final static Color V_DRAW_VISIT_COLOR = GravisColor.BLACK;
	public final static Color V_DRAW_SOLUTION_COLOR = GravisColor.BLACK;
	public final static Color V_DRAW_ELIMINATION_COLOR = GravisColor.BLACK;
	
	public final static Color E_INITIAL_COLOR = GravisColor.BLACK;
	public final static Color E_ACTIVATION_COLOR = GravisColor.BLACK;
	public final static Color E_VISIT_COLOR = GravisColor.BLACK;
	public final static Color E_SOLUTION_COLOR = GravisColor.BLACK;
	public final static Color E_ELIMINATION_COLOR = GravisColor.BLACK;
	
	// some visualisation default values:
	
	public final static float V_START_DASH = 10.0f;
	public final static float V_END_DASH = 5.0f;
	
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
