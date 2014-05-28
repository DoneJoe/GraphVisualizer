package ch.bfh.ti.gravis.gui;

import java.awt.Image;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;

import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.transformer.PointTransformer;
import ch.bfh.ti.gravis.gui.controller.ControllerFactory;
import ch.bfh.ti.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.ti.gravis.gui.controller.IStepController;
import ch.bfh.ti.gravis.gui.controller.IMenuToolbarController.EventSource;
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

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "GuiFactory.%s(): %s == %s";
	
	private GuiFactory() {
	}

	/**
	 * @param core
	 * @return JFrame
	 * @throws IOException
	 * @throws BadLocationException
	 */
	public static JFrame createGUI(final ICore core) throws IOException, BadLocationException {
		Objects.requireNonNull(core, String.format(
				NULL_POINTER_MSG, "createGUI", "core",
				core));
		
		// model
		IAppModel model = AppModelFactory.createAppModel(core);

		// MenuToolbarController and StepController
		IMenuToolbarController menuToolbarController = ControllerFactory
				.createMenuToolbarController(model, core);
		IStepController stepController = ControllerFactory
				.createStepController(model);

		// view
		JFrame mainWindow = new MainWindow(menuToolbarController,
				stepController, model);

		// set dialog adapters to controllers
		MessageDialogAdapter messageDialogAdapter = new MessageDialogAdapter(
				mainWindow);
		menuToolbarController.setFileChooserAdapter(new FileChooserAdapter(
				mainWindow));
		menuToolbarController.setMessageDialogAdapter(messageDialogAdapter);
		menuToolbarController.setConfirmDialogAdapter(new ConfirmDialogAdapter(
				mainWindow));
		menuToolbarController
				.setGraphPropertyDialogFactory(new GraphPropertyDialogFactory(
						mainWindow));
		stepController.setMessageDialogAdapter(messageDialogAdapter);

		// VisualizationController
		IEditGraphEventListener visualizationController = ControllerFactory
				.createVisualizationController(model, messageDialogAdapter);
		model.getGraph().addEditGraphEventListener(visualizationController);

		// add button model listeners
		model.getNewDirGraphButtonModel().setActionCommand(
				EventSource.NEW_DIR_GRAPH.toString());
		model.getNewDirGraphButtonModel().addActionListener(
				menuToolbarController);
		model.getNewUndirGraphButtonModel().setActionCommand(
				EventSource.NEW_UNDIR_GRAPH.toString());
		model.getNewUndirGraphButtonModel().addActionListener(
				menuToolbarController);
		model.getOpenGraphButtonModel().setActionCommand(
				EventSource.OPEN_GRAPH.toString());
		model.getOpenGraphButtonModel()
				.addActionListener(menuToolbarController);
		model.getSaveGraphButtonModel().setActionCommand(
				EventSource.SAVE_GRAPH.toString());
		model.getSaveGraphButtonModel()
				.addActionListener(menuToolbarController);
		model.getSaveGraphAsButtonModel().setActionCommand(
				EventSource.SAVE_GRAPH_AS.toString());
		model.getSaveGraphAsButtonModel().addActionListener(
				menuToolbarController);
		model.getGraphPropertiesButtonModel().setActionCommand(
				EventSource.GRAPH_PROPERTY.toString());
		model.getGraphPropertiesButtonModel().addActionListener(
				menuToolbarController);

		return mainWindow;
	}

	/**
	 * @param graph
	 * @return Layout<IVertex, IEdge>
	 */
	public static Layout<IVertex, IEdge> createLayout(IGravisGraph graph) {
		Objects.requireNonNull(graph, String.format(
				NULL_POINTER_MSG, "createLayout", "graph",
				graph));
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
		return ImageIO.read(GuiFactory.class.getResourceAsStream(IMAGES_DIR
				+ iconName));
	}

}
