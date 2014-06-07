package ch.bfh.ti.gravis.core.graph.item.vertex;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type;
import ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem;
import ch.bfh.ti.gravis.core.graph.item.ItemState;
import ch.bfh.ti.gravis.core.util.GravisColor;
import ch.bfh.ti.gravis.core.util.GravisConstants;

/**
 * This implementation of the {@link IVertex} interface gives access to mutable
 * vertex methods.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GravisVertex extends AbstractGraphItem implements IVertex {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "AbstractGraphItem.%s(): %s == %s";
	
	// special vertex fields:

	private boolean start, end;

	private double width, height;

	private Point2D location;

	private Color currentDrawColor;

	/**
	 * Default constructor sets start and end to false both by default.
	 */
	protected GravisVertex() {
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
	 * @see ch.bfh.ti.gravis.core.graph.item.vertex.IVertex#
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
	 * ch.bfh.ti.gravis.core.graph.item.vertex.IVertex#getHeight()
	 */
	@Override
	public double getHeight() {
		return this.height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.common.IVertex#getLocation()
	 */
	@Override
	public Point2D getLocation() {
		return this.location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.vertex.IVertex#getWidth()
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
	 * @see ch.bfh.ti.gravis.core.graph.item.vertex.IVertex#
	 * setCurrentDrawColor(java.awt.Color)
	 */
	@Override
	public void setCurrentDrawColor(final Color color) {
		this.currentDrawColor = Objects.requireNonNull(color, String.format(
				NULL_POINTER_MSG, "setCurrentDrawColor", "color",
				color));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#
	 * setCurrentState(ch.bfh.ti.gravis.core.graph.item.ItemState)
	 */
	@Override
	public void setCurrentState(ItemState currentState) {
		super.setCurrentState(currentState);
		this.setCurrentDrawColor(currentState.getDrawColor(this));
	}

	@Override
	public void setEnd(boolean end) {
		if (this.isEnd() != end) {
			this.end = end;
			this.fireGraphItemsChangedEvent(this, Type.END_EDITED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.vertex.IVertex#setHeight
	 * (double)
	 */
	@Override
	public void setHeight(double height) {
		if (Double.compare(this.getHeight(), height) != 0) {
			this.height = height;
			this.fireGraphItemsChangedEvent(this, Type.VISUAL_EDITED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.common.IVertex#setLocation(java.awt.geom
	 * .Point2D)
	 */
	@Override
	public void setLocation(final Point2D location) {
		Objects.requireNonNull(location, String.format(
				NULL_POINTER_MSG, "setLocation", "location",
				location));
		
		if (!location.equals(this.getLocation())) {
			this.location = (Point2D) location.clone();
			this.fireGraphItemsChangedEvent(this, Type.VISUAL_EDITED);
		}
	}

	@Override
	public void setStart(boolean start) {
		if (this.isStart() != start) {
			this.start = start;
			this.fireGraphItemsChangedEvent(this, Type.START_EDITED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.item.vertex.IVertex#setWidth(
	 * double)
	 */
	@Override
	public void setWidth(double width) {
		if (Double.compare(this.getWidth(), width) != 0) {
			this.width = width;
			this.fireGraphItemsChangedEvent(this, Type.VISUAL_EDITED);
		}
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#createItemName()
	 */
	@Override
	protected String createItemName() {
		return VertexFactory.createVertexName();
	}

}
