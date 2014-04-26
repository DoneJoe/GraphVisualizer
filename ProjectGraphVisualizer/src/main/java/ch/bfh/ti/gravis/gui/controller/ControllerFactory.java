package ch.bfh.ti.gravis.gui.controller;

import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;
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
	 */
	public static IMenuToolbarController createMenuToolbarController(IAppModel model, ICore core) {
		return new MenuToolbarController(model, core);
	}

	/**
	 * 
	 * @param model
	 * @return IEditGraphEventListener
	 */
	public static IEditGraphEventListener createVisualizationController(
			IAppModel model) {
		return new VisualizationController(model);
	}

	/**
	 * @param model
	 * @return IStepController
	 */
	public static IStepController createStepController(IAppModel model) {
		return new StepController(model);
	}

}
