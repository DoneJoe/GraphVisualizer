package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;

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
			ICore core, IGuiModel model) {
		return new MenuToolbarController(core, model);
	}

	/**
	 * @param core
	 * @param model
	 * @return IVisualizationController
	 */
	public static IVisualizationController createVisualizationController(
			ICore core, IGuiModel model) {
		return new VisualizationController(core, model);
	}

	/**
	 * @param core
	 * @param model
	 * @return IStepController
	 */
	public static IStepController createStepController(ICore core,
			IGuiModel model) {
		return new StepController(core, model);
	}

}
