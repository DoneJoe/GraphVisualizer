package ch.bfh.bti7301.hs2013.gravis.gui;

import javax.swing.JToolBar;
import javax.swing.JButton;

import ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class ToolBarPanel extends JToolBar {

	private static final long serialVersionUID = 2109148728694878673L;

	/**
	 * Create the panel.
	 * 
	 * @param model 
	 * @param menuToolbarController 
	 */
	public ToolBarPanel(IMenuToolbarController menuToolbarController, IGuiModel model) {
		
		JButton btnNewButton = new JButton("New button");
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button 2");
		add(btnNewButton_1);

	}

}
