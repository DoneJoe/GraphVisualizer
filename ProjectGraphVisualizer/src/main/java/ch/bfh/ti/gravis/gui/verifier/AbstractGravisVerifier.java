package ch.bfh.ti.gravis.gui.verifier;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
abstract class AbstractGravisVerifier extends InputVerifier {

	private String lastGood;

	/**
	 * @param lastGood 
	 * 
	 */
	protected AbstractGravisVerifier(String lastGood) {
		super();
		
		this.lastGood = lastGood;
	}

	@Override
	public boolean shouldYieldFocus(final JComponent input) {
		if (input instanceof JTextComponent) {
			JTextComponent textField = (JTextComponent) input;
			boolean inputOK = this.verify(input);
	
			if (inputOK) {
				this.lastGood = textField.getText();
				return true;
			} else {
				textField.setText(this.lastGood);
				return false;
			}
		}
	
		return false;
	}


}
