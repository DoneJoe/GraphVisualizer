package ch.bfh.bti7301.hs2013.gravis.gui.model;

import ch.bfh.bti7301.hs2013.gravis.core.CoreException;
import ch.bfh.bti7301.hs2013.gravis.core.ICore;


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
	 */
	public static IAppModel createAppModel(ICore core) throws CoreException {
		return new AppModel(core);
	}

}
