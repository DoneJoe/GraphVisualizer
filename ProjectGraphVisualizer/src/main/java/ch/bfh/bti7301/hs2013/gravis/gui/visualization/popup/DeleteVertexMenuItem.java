package ch.bfh.bti7301.hs2013.gravis.gui.visualization.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JMenuItem;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class DeleteVertexMenuItem extends JMenuItem implements
		IGraphItemMenuListener {

	private static final long serialVersionUID = -4699986300655692795L;
	
	private final static String TITLE = "Knoten löschen";
	private final static String DELETE_VERTEX_LABEL = "Knoten %s löschen";

	private IVertex vertex;

	/**
	 * 
	 * @param vViewer
	 */
	protected DeleteVertexMenuItem(final VisualizationViewer<IVertex, IEdge> vViewer) {
		super(TITLE);

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteVertexMenuItem.this.deleteVertex(vViewer);
			}
		});
	}

	/**
	 * 
	 * @param vViewer
	 */
	private void deleteVertex(VisualizationViewer<IVertex, IEdge> vViewer) {
		if (this.vertex != null) {
			vViewer.getPickedVertexState().pick(this.vertex, false);
			vViewer.getGraphLayout().getGraph().removeVertex(this.vertex);
			vViewer.repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.visualization.GraphItemMenuListener#
	 * setGraphItemAndView
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem)
	 */
	@Override
	public void setGraphItemAndView(IGraphItem item) {
		if (item instanceof IVertex) {
			this.vertex = (IVertex) item;
			this.setText(String.format(DELETE_VERTEX_LABEL, this.vertex.getId()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.gui.visualization.GraphItemMenuListener#
	 * setGraphItemLocation(java.awt.geom.Point2D)
	 */
	@Override
	public void setGraphItemLocation(Point2D point) {
		// does nothing
	}

}