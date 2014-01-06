package ch.bfh.bti7301.hs2013.gravis.gui;

import javax.swing.JFrame;

import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.PointTransformer;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.GravisVisualizationViewer;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.VisualizationPanel;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public final class GuiFactory {

	private GuiFactory() {
	}

	/**
	 * @param core
	 * @return JFrame
	 */
	public static JFrame createGUI(ICore core) {
		Graph<IVertex, IEdge> graph = GraphFactory.createIGravisGraph();
		GravisVisualizationViewer visualizationViewer = new GravisVisualizationViewer(
				new StaticLayout<>(graph, new PointTransformer()));

		OldMainWindowListener mainWindowListener = new OldMainWindowListener(
				core, visualizationViewer);
		VisualizationPanel visualizationPanel = new VisualizationPanel(
				visualizationViewer, mainWindowListener);
		JFrame mainWindow = new OldGravisMainWindow("Test", visualizationPanel,
				mainWindowListener);
		// new OldGravisMainWindow(title, graphPanel, mainWindowListener);

		mainWindowListener.addObserver(visualizationPanel);
		visualizationPanel.setRootFrame(mainWindow);
		
		return mainWindow;
	}

}
