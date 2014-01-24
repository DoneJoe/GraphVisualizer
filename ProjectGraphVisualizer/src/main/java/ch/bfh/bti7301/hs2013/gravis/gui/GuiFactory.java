package ch.bfh.bti7301.hs2013.gravis.gui;

import java.io.IOException;

import javax.swing.JFrame;

import ch.bfh.bti7301.hs2013.gravis.core.CoreException;
import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.util.transformer.PointTransformer;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.ControllerFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IStepController;
import ch.bfh.bti7301.hs2013.gravis.gui.model.GuiModelFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;

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
	 * @throws CoreException 
	 * @throws IOException 
	 */
	public static JFrame createGUI(ICore core) throws CoreException, IOException {
		// model
		IGuiModel model = GuiModelFactory.createGuiModel(core);

		// controllers
		IMenuToolbarController menuToolbarController = ControllerFactory
				.createMenuToolbarController(core, model);
		IEditingGraphEventListener visualizationController = ControllerFactory
				.createVisualizationController(model);
		IStepController stepController = ControllerFactory
				.createStepController(model);
		model.addEditingGraphEventListener(visualizationController);
		
		// view
		return new MainWindow(menuToolbarController, visualizationController,
				stepController, model);
	}

	/**
	 * @param graph
	 * @return Layout<IVertex, IEdge>
	 */
	public static Layout<IVertex, IEdge> createLayout(IGravisGraph graph) {
		return new StaticLayout<>(graph, new PointTransformer());
	}

}
