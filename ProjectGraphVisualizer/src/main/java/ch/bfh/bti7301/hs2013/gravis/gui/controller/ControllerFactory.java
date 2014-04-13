package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditableGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel;

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
	 * @return IEditableGraphEventListener
	 */
	public static IEditableGraphEventListener createVisualizationController(
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
