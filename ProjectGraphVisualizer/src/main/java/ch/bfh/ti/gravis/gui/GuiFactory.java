package ch.bfh.ti.gravis.gui;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ch.bfh.ti.gravis.core.CoreException;
import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.transformer.PointTransformer;
import ch.bfh.ti.gravis.gui.controller.ControllerFactory;
import ch.bfh.ti.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.ti.gravis.gui.controller.IStepController;
import ch.bfh.ti.gravis.gui.dialog.ConfirmDialogAdapter;
import ch.bfh.ti.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.ti.gravis.gui.dialog.GraphPropertyDialogFactory;
import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.ti.gravis.gui.model.AppModelFactory;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;

import static ch.bfh.ti.gravis.core.util.GravisConstants.*;

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
	public static JFrame createGUI(ICore core) throws CoreException,
			IOException {
		// model
		IAppModel model = AppModelFactory.createAppModel(core);

		// controllers
		IMenuToolbarController menuToolbarController = ControllerFactory
				.createMenuToolbarController(core, model);
		IEditGraphEventListener visualizationController = ControllerFactory
				.createVisualizationController(model);
		IStepController stepController = ControllerFactory
				.createStepController(model);
		
		// add EditGraphEventListener to graph
		model.getGraph().addEditGraphEventListener(visualizationController);

		// view
		JFrame mainWindow = new MainWindow(menuToolbarController,
				visualizationController, stepController, model);

		// set dialog adapters to controllers
		menuToolbarController.setFileChooserAdapter(new FileChooserAdapter(
				mainWindow));
		menuToolbarController.setMessageDialogAdapter(new MessageDialogAdapter(
				mainWindow));
		menuToolbarController.setConfirmDialogAdapter(new ConfirmDialogAdapter(
				mainWindow));
		menuToolbarController
				.setGraphPropertyDialogFactory(new GraphPropertyDialogFactory(
						mainWindow));

		return mainWindow;
	}

	/**
	 * @param graph
	 * @return Layout<IVertex, IEdge>
	 */
	public static Layout<IVertex, IEdge> createLayout(IGravisGraph graph) {
		return new StaticLayout<>(graph, new PointTransformer());
	}
	
	/**
	 * Loads an icon ressource with the given name.
	 * 
	 * @param iconName
	 * @return Image
	 * @throws IOException
	 */
	public static Image loadImage(String iconName) throws IOException {
		return ImageIO.read(GuiFactory.class.getResourceAsStream(
				IMAGES_DIR + iconName));
	}

}
