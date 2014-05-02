package ch.bfh.ti.gravis.gui.model;

import javax.swing.text.BadLocationException;

import ch.bfh.ti.gravis.core.CoreException;
import ch.bfh.ti.gravis.core.ICore;


/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public final class AppModelFactory {

	private AppModelFactory() {
	}
	
	/**
	 * @param core 
	 * @return IAppModel
	 * @throws CoreException 
	 * @throws BadLocationException 
	 */
	public static IAppModel createAppModel(ICore core) throws CoreException, BadLocationException {
		return new AppModel(core);
	}

}
