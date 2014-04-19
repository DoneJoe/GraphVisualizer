package ch.bfh.ti.gravis.gui.visualization.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JMenuItem;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class DeleteEdgeMenuItem extends JMenuItem implements
		IGraphItemMenuListener {

	private static final long serialVersionUID = -8344732316212412105L;
	
	private final static String TITLE = "Kante löschen";
	private final static String DELETE_EDGE_LABEL = "Kante %s löschen";

	private IEdge edge = null;

	/**
	 * @param vViewer
	 */
	protected DeleteEdgeMenuItem(final VisualizationViewer<IVertex, IEdge> vViewer) {
		super(TITLE);

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteEdgeMenuItem.this.deleteEdge(vViewer);
			}
		});
	}

	/**
	 * 
	 * @param vViewer
	 */
	private void deleteEdge(final VisualizationViewer<IVertex, IEdge> vViewer) {
		vViewer.getPickedEdgeState().pick(this.edge, false);
		vViewer.getGraphLayout().getGraph().removeEdge(this.edge);
		vViewer.repaint();
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
			this.setText(String.format(DELETE_EDGE_LABEL, this.edge.getName()));
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
	public void setGraphItemLocation(Point2D point) {
		// does nothing
	}
}
