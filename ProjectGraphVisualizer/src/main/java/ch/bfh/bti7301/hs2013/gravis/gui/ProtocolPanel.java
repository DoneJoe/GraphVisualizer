package ch.bfh.bti7301.hs2013.gravis.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class ProtocolPanel extends JScrollPane {

	private static final long serialVersionUID = 4198819175596919745L;

	/**
	 * Create the panel.
	 */
	public ProtocolPanel() {
		
		JFormattedTextField frmtdtxtfldTest = new JFormattedTextField();
		frmtdtxtfldTest.setText("Test");
		setViewportView(frmtdtxtfldTest);

	}

}
