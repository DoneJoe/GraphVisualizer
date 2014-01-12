package ch.bfh.bti7301.hs2013.gravis.gui.visualization.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JCheckBoxMenuItem;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class EndVertexCheckBox extends JCheckBoxMenuItem implements
		IGraphItemMenuListener {

	private static final long serialVersionUID = 6641658478963193492L;

	private final static String TITLE = "Endknoten";

	private IVertex vertex = null;

	/**
	 * 
	 * @param vViewer
	 */
	protected EndVertexCheckBox(final VisualizationViewer<IVertex, IEdge> vViewer) {
		super(TITLE);

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EndVertexCheckBox.this.setValue(vViewer);
			}
		});
	}

	/**
	 * 
	 * @param vViewer
	 */
	private void setValue(VisualizationViewer<IVertex, IEdge> vViewer) {
		if (this.vertex != null) {
			this.vertex.setEnd(this.isSelected());
			vViewer.repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.visualization.GraphItemMenuListener
	 * #setGraphItemAndView
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem)
	 */
	@Override
	public void setGraphItemAndView(IGraphItem item) {
		if (item instanceof IVertex) {
			this.vertex = (IVertex) item;
			this.setSelected(this.vertex.isEnd());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.visualization.GraphItemMenuListener
	 * #setGraphItemLocation(java.awt.geom.Point2D)
	 */
	@Override
	public void setGraphItemLocation(Point2D point) {
		// does nothing
	}

}
