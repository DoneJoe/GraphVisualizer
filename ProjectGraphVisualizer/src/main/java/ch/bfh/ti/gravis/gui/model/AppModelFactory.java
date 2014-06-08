package ch.bfh.ti.gravis.gui.model;

import java.util.Objects;

import javax.swing.text.BadLocationException;

import ch.bfh.ti.gravis.core.ICore;


/**
 * This static factory class creates instances of model classes (MVC-pattern).
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public final class AppModelFactory {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "AppModelFactory.%s(): %s == %s";
	
	private AppModelFactory() {
	}
	
	/**
	 * @param core 
	 * @return IAppModel
	 * @throws BadLocationException 
	 * @throws NullPointerException
	 *             if core is null
	 */
	public static IAppModel createAppModel(ICore core) throws BadLocationException {
		Objects.requireNonNull(core, String.format(
				NULL_POINTER_MSG, "createAppModel", "core",
				core));
		return new AppModel(core);
	}

}
