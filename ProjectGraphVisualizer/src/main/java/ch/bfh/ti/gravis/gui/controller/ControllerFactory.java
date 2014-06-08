package ch.bfh.ti.gravis.gui.controller;

import java.util.Objects;

import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.ti.gravis.gui.model.IAppModel;

/**
 * This static factory class creates instances of controller classes (MVC-pattern).
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public final class ControllerFactory {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "ControllerFactory.%s(): %s == %s";

	/**
	 * Hides the dafault constructor.
	 */
	private ControllerFactory() {
	}

	/**
	 * @param model
	 * @param core
	 * @return IMenuToolbarController
	 * @throws NullPointerException
	 *             if core or model is null
	 */
	public static IMenuToolbarController createMenuToolbarController(
			IAppModel model, ICore core) {

		Objects.requireNonNull(core, String.format(NULL_POINTER_MSG,
				"createMenuToolbarController", "core", core));
		Objects.requireNonNull(model, String.format(NULL_POINTER_MSG,
				"createMenuToolbarController", "model", model));
		return new MenuToolbarController(model, core);
	}

	/**
	 * 
	 * @param model
	 * @param messageDialogAdapter
	 * @return IEditGraphEventListener
	 * @throws NullPointerException
	 *             if messageDialogAdapter or model is null
	 */
	public static IEditGraphEventListener createVisualizationController(
			IAppModel model, MessageDialogAdapter messageDialogAdapter) {

		Objects.requireNonNull(messageDialogAdapter, String.format(
				NULL_POINTER_MSG, "createVisualizationController",
				"messageDialogAdapter", messageDialogAdapter));
		Objects.requireNonNull(model, String.format(NULL_POINTER_MSG,
				"createVisualizationController", "model", model));
		return new VisualizationController(model, messageDialogAdapter);
	}

	/**
	 * @param model
	 * @return IStepController
	 * @throws NullPointerException
	 *             if model is null
	 */
	public static IStepController createStepController(IAppModel model) {
		Objects.requireNonNull(model, String.format(NULL_POINTER_MSG,
				"createStepController", "model", model));
		return new StepController(model);
	}

}
