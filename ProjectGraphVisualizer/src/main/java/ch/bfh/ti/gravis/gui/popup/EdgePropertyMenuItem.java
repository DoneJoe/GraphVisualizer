package ch.bfh.ti.gravis.gui.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.gui.dialog.EdgePropertyDialog;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class EdgePropertyMenuItem extends JMenuItem implements IGraphItemMenuListener {

	private static final long serialVersionUID = -1894264493446725645L;

	private final static String TITLE = "Kante %s bearbeiten...";

	private IEdge edge = null;

	private Point2D point = null;

	/**
	 * @param vViewer
	 * @param owner
	 * 
	 */
	protected EdgePropertyMenuItem(
			final VisualizationViewer<IVertex, IEdge> vViewer,
			final JFrame owner) {		
		super(String.format(TITLE, ""));

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EdgePropertyMenuItem.this.showDialog(vViewer, owner);
			}
		});
	}

	/**
	 * 
	 * @param vViewer
	 * @param owner
	 */
	protected void showDialog(
			final VisualizationViewer<IVertex, IEdge> vViewer,
			final JFrame owner) {
		if (this.point != null && this.edge != null) {
			EdgePropertyDialog dialog = new EdgePropertyDialog(this.edge,
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
	 * setGraphItemAndView
	 * (ch.bfh.ti.gravis.core.graph.item.IGraphItem)
	 */
	@Override
	public void setGraphItemAndView(final IGraphItem item) {
		if (item instanceof IEdge) {
			this.edge = (IEdge) item;
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
