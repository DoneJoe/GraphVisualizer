package ch.bfh.ti.gravis.gui.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.gui.dialog.VertexPropertyDialog;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * A "vertex property" menu item.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class VertexPropertyMenuItem extends JMenuItem implements
		IGraphItemMenuListener {

	private static final long serialVersionUID = 3448304253580836407L;

	private final static String TITLE = "Knoten %s bearbeiten...";

	private IVertex vertex = null;

	private Point2D point = null;

	/**
	 * @param vViewer
	 * @param owner
	 */
	protected VertexPropertyMenuItem(
			final VisualizationViewer<IVertex, IEdge> vViewer, final JFrame owner) {
		super(String.format(TITLE, ""));

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VertexPropertyMenuItem.this.showDialog(vViewer, owner);
			}
		});
	}

	/**
	 * 
	 * @param vViewer 
	 * @param owner
	 */
	private void showDialog(final VisualizationViewer<IVertex, IEdge> vViewer, 
			final JFrame owner) {
		if (this.point != null && this.vertex != null) {
			VertexPropertyDialog dialog = new VertexPropertyDialog(this.vertex,
					owner, vViewer);
			dialog.setLocation((int) this.point.getX() + owner.getX(),
					(int) this.point.getY() + owner.getY());
			dialog.setVisible(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.gui.visualization.GraphItemMenuListener#
	 * setGraphItem
	 * (ch.bfh.ti.gravis.core.graph.item.IGraphItem)
	 */
	@Override
	public void setGraphItem(final IGraphItem item) {
		if (item instanceof IVertex) {
			this.vertex = (IVertex) item;
			this.setText(String.format(TITLE, item.getName()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.gui.visualization.GraphItemMenuListener#
	 * setGraphItemLocation(java.awt.geom.Point2D)
	 */
	@Override
	public void setGraphItemLocation(final Point2D point) {
		if (point != null) {
			this.point = point;
		}
	}

}
