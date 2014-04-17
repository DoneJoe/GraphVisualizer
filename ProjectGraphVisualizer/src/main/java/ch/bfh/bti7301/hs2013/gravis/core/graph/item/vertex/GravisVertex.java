package ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Objects;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.AbstractGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.ItemState;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisColor;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;

/**
 * A vertex.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GravisVertex extends AbstractGraphItem implements IVertex {

	// constants:

	private final static char FIRST_CHAR = 'A';

	private final static char LAST_CHAR = 'Z';

	// static counters:

	private static int intCounter = 0;

	private static char charCounter = FIRST_CHAR;

	/**
	 * @return default name
	 */
	protected static String createNewDefaultVertexName() {
		String newChar = String.valueOf(charCounter);
		String newName = intCounter == 0 ? newChar : newChar
				+ String.valueOf(intCounter);

		if (LAST_CHAR - charCounter == 0) {
			charCounter = FIRST_CHAR;
			intCounter++;
		} else {
			charCounter++;
		}

		return newName;
	}

	// special vertex fields:

	private String vertexName;

	private boolean start, end;

	private double width, height;

	private Point2D location;

	private Color currentDrawColor;

	/**
	 * Main constructor setting start and end to false both by default.
	 */
	protected GravisVertex() {
		this.setName(createNewDefaultVertexName());
		this.setStart(false);
		this.setEnd(false);
		this.setWidth(GravisConstants.V_WIDTH_DEFAULT);
		this.setHeight(GravisConstants.V_HEIGHT_DEFAULT);
		this.setLocation(new Point(GravisConstants.V_LOC_X_DEFAULT,
				GravisConstants.V_LOC_Y_DEFAULT));
		this.setCurrentDrawColor(GravisConstants.V_DRAW_COLOR_DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex#
	 * getCurrentDrawColor()
	 */
	@Override
	public Color getCurrentDrawColor() {
		return this.isCurrentVisible() ? this.currentDrawColor
				: GravisColor.WHITE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex#getHeight()
	 */
	@Override
	public double getHeight() {
		return this.height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.common.IVertex#getLocation()
	 */
	@Override
	public Point2D getLocation() {
		return this.location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#getName
	 * ()
	 */
	@Override
	public String getName() {
		return this.vertexName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex#getWidth()
	 */
	@Override
	public double getWidth() {
		return this.width;
	}

	@Override
	public boolean isEnd() {
		return this.end;
	}

	@Override
	public boolean isStart() {
		return this.start;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex#
	 * setCurrentDrawColor(java.awt.Color)
	 */
	@Override
	public void setCurrentDrawColor(Color color) {
		// TODO Exception handling bei null values
		this.currentDrawColor = Objects.requireNonNull(color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.AbstractGraphItem#
	 * setCurrentState(ch.bfh.bti7301.hs2013.gravis.core.graph.item.ItemState)
	 */
	@Override
	public void setCurrentState(ItemState currentState) {
		super.setCurrentState(currentState);
		this.setCurrentDrawColor(currentState.getDrawColor(this));
	}

	@Override
	public void setEnd(boolean end) {
		boolean equal = this.end == end;
		this.end = end;

		if (!equal) {
			this.fireGraphItemsChangedEvent(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex#setHeight
	 * (double)
	 */
	@Override
	public void setHeight(double height) {
		this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.common.IVertex#setLocation(java.awt.geom
	 * .Point2D)
	 */
	@Override
	public void setLocation(Point2D location) {
		// TODO Exception handling bei null values
		Objects.requireNonNull(location);
		this.location = (Point2D) location.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#setName(java.
	 * lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Exception handling bei null values
		Objects.requireNonNull(name);
		boolean equal = this.getName() == null ? false : this.getName().equals(
				name.trim());
		this.vertexName = name.trim();

		if (!equal) {
			this.fireGraphItemsChangedEvent(this);
		}
	}

	@Override
	public void setStart(boolean start) {
		boolean equal = this.start == start;
		this.start = start;

		if (!equal) {
			this.fireGraphItemsChangedEvent(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex#setWidth(
	 * double)
	 */
	@Override
	public void setWidth(double width) {
		this.width = width;
	}

}
