package ch.bfh.ti.gravis.gui.dialog;

import java.awt.Component;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class AbstractDialogAdapter {

	protected final Component parent;
	
	/**
	 * 
	 * @param parent
	 */
	protected AbstractDialogAdapter(Component parent) {
		this.parent = parent;
	}

}
