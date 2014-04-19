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
	 * @param core
	 * @param model
	 * @return IMenuToolbarController
	 */
	public static IMenuToolbarController createMenuToolbarController(
			ICore core, IAppModel model) {
		return new MenuToolbarController(core, model);
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
