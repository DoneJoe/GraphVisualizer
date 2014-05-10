package ch.bfh.ti.gravis.gui.verifier;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
abstract class AbstractVerifier extends InputVerifier {

	private String lastGood;

	/**
	 * @param lastGood 
	 * 
	 */
	protected AbstractVerifier(String lastGood) {
		super();
		
		this.lastGood = lastGood;
	}

	@Override
	public boolean shouldYieldFocus(final JComponent input) {
		if (input instanceof JTextComponent) {
			JTextComponent textField = (JTextComponent) input;
	
			if (this.verify(input)) {
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
