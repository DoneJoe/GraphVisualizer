package ch.bfh.bti7301.hs2013.gravis.gui.model;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public final class GuiModelFactory {

	private GuiModelFactory() {
	}
	
	/**
	 * @return IGuiModel
	 */
	public static IGuiModel createGuiModel() {
		return new GuiModel();
	}

}
