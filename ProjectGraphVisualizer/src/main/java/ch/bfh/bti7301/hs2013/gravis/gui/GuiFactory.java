package ch.bfh.bti7301.hs2013.gravis.gui;

import javax.swing.JFrame;

import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.gui.model.GuiModelFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;

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
	 */
	public static JFrame createGUI(ICore core) {
		IGuiModel model = GuiModelFactory.createGuiModel();
		MainWindow mainWindow = new MainWindow();
		
		return mainWindow;
	}

}
