package ch.bfh.bti7301.hs2013.gravis.gui.visualization;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.GuiFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IVisualizationController;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.GraphPropertyDialog;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.popup.EdgeMenu;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.popup.VertexCreateMenu;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.popup.VertexMenu;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.layout.ObservableCachingLayout;

/**
 * A visualization panel.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VisualizationPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 177109739873034494L;

	/**
	 * A field for a titled border.
	 */
	private TitledBorder titledBorder;

	/**
	 * A field for a visualization viewer.
	 */
	private GravisVisualizationViewer viewer;

	private VertexMenu vertexMenu;

	private VertexCreateMenu vertexCreateMenu;

	private EdgeMenu edgeMenu;

	private JButton editGraphBtn;

	public VisualizationPanel(IVisualizationController visualizationController,
			IGuiModel model) {
		super();

		// TODO diese Klasse muss editing events auslösen können
		// TODO modeComboBox zurückgeben
		// TODO Popup-Aktivierung abhängig von Modus

		// viewer
		this.viewer = new GravisVisualizationViewer(
				GuiFactory.createLayout(model.getGraph()));
		
		this.setBorder(BorderFactory.createTitledBorder(model.getGraph()
				.getName()));

		this.vertexMenu = new VertexMenu(this.viewer);
		this.vertexCreateMenu = new VertexCreateMenu(this.viewer);
		this.edgeMenu = new EdgeMenu(this.viewer);

		JPanel controls = new JPanel();
		this.editGraphBtn = new JButton("Graphen bearbeiten...");
		JButton dirGraphBtn = new JButton("Neuer gerichteter Graph");
		JButton undirGraphBtn = new JButton("Neuer ungerichteter Graph");
		JButton shortcutBtn = new JButton("Shortcut Liste");

		EditingModalGraphMouse<IVertex, IEdge> graphMouse = new GravisModalGraphMouse(
				this.viewer.getRenderContext(), new VertexFactory(),
				new EdgeFactory(), this.edgeMenu, this.vertexMenu,
				this.vertexCreateMenu);
		JComboBox<?> modeBox = graphMouse.getModeComboBox();

		graphMouse.setMode(Mode.PICKING);
		this.viewer.setGraphMouse(graphMouse);
		this.viewer.addKeyListener(graphMouse.getModeKeyListener());

		// controls.add(this.editGraphBtn);
		// controls.add(dirGraphBtn);
		// controls.add(undirGraphBtn);
		// controls.add(shortcutBtn);
		controls.add(modeBox);

		this.add(controls, BorderLayout.NORTH);

		GraphZoomScrollPane pane = new GraphZoomScrollPane(viewer);
		this.add(pane, BorderLayout.CENTER);

		// modeBox.setEnabled(false);
		// pane.setEnabled(false);
		// this.viewer.setEnabled(false);

		// dirGraphBtn.setActionCommand(OldIGravisMainListener.EventSource.
		// NEW_DIR_GRAPH.toString());
		// dirGraphBtn.addActionListener(mainWindowListener);
		//
		// undirGraphBtn.setActionCommand(OldIGravisMainListener.EventSource.
		// NEW_UNDIR_GRAPH.toString());
		// undirGraphBtn.addActionListener(mainWindowListener);
		//
		// shortcutBtn.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// JOptionPane.showMessageDialog(VisualizationPanel.this,
		// VisualizationPanel.this.shortCuts);
		// }
		// });

		// JPopupMenu edgeMenu = new MyMouseMenus.EdgeMenu(frame);
		// JPopupMenu vertexMenu = new MyMouseMenus.VertexMenu();
		// myPlugin.setEdgePopup(edgeMenu);
		// myPlugin.setVertexPopup(vertexMenu);
		// gm.remove(gm.getPopupEditingPlugin()); // Removes the existing popup
		// gm.add(myPlugin); // Add our new plugin to the mouse

		// Point2D p = visualizationViewer.getRenderContext()
		// .getMultiLayerTransformer().inverseTransform(new Point2D(2,2));
		// System.out.println(p);

		// modeBox.setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof IGravisGraph) {
			this.setBorder(BorderFactory
					.createTitledBorder(((IGravisGraph) arg).getName()));
		}
		this.viewer.update(o, arg);
	}

}
