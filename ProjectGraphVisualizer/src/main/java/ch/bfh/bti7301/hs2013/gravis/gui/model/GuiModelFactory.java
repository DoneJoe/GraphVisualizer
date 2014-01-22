package ch.bfh.bti7301.hs2013.gravis.gui.model;

import ch.bfh.bti7301.hs2013.gravis.core.CoreException;
import ch.bfh.bti7301.hs2013.gravis.core.ICore;


/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public final class GuiModelFactory {

	private GuiModelFactory() {
	}
	
	/**
	 * @param core 
	 * @return IGuiModel
	 * @throws CoreException 
	 */
	public static IGuiModel createGuiModel(ICore core) throws CoreException {
		return new GuiModel(core);
	}

}
