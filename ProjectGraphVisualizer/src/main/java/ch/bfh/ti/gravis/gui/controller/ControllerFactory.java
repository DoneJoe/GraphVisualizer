package ch.bfh.ti.gravis.gui.controller;

import javax.swing.text.BadLocationException;

import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.ti.gravis.gui.model.IAppModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public final class ControllerFactory {

	private ControllerFactory() {
	}

	/**
	 * @param model
	 * @param core
	 * @return IMenuToolbarController
	 * @throws BadLocationException
	 */
	public static IMenuToolbarController createMenuToolbarController(
			IAppModel model, ICore core) throws BadLocationException {
		return new MenuToolbarController(model, core);
	}

	/**
	 * 
	 * @param model
	 * @param messageDialogAdapter
	 * @return IEditGraphEventListener
	 */
	public static IEditGraphEventListener createVisualizationController(
			IAppModel model, MessageDialogAdapter messageDialogAdapter) {
		return new VisualizationController(model, messageDialogAdapter);
	}

	/**
	 * @param model
	 * @return IStepController
	 */
	public static IStepController createStepController(IAppModel model) {
		return new StepController(model);
	}

}
