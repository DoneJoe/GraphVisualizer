package ch.bfh.ti.gravis.gui.verifier;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 * A graph name verifier.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class GraphNameVerifier extends AbstractVerifier {

	private static final int MAX_LENGTH = 55;
	
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
			String name = ((JTextComponent) input).getText().trim();
			
			return !name.trim().isEmpty() && name.length() <= MAX_LENGTH;
		}
		
		return false;
	}

}
