package ch.bfh.bti7301.hs2013.gravis.gui.verifier;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class GraphNameVerifier extends AbstractGravisVerifier {

	/**
	 * @param lastGood 
	 */
	public GraphNameVerifier(String lastGood) {
		super(lastGood);
	}

	/* (non-Javadoc)
	 * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
	 */
	@Override
	public boolean verify(final JComponent input) {
		if (input instanceof JTextComponent) {
			JTextComponent textField = (JTextComponent) input;
			
			return !textField.getText().trim().isEmpty();
		}
		
		return false;
	}

}
