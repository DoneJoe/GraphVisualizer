package ch.bfh.bti7301.hs2013.gravis.gui.dialog;

import java.awt.Component;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class AbstractDialogAdapter {

	protected final Component parent;
	
	/**
	 * 
	 * @param parent
	 */
	public AbstractDialogAdapter(Component parent) {
		this.parent = parent;
	}

}