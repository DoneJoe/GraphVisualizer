package ch.bfh.ti.gravis.gui.verifier;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 * A vertex size verifier.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VertexSizeVerifier extends AbstractVerifier {

	private static final double MIN_VALUE = 10.0;

	private static final double MAX_VALUE = 500.0;

	/**
	 * 
	 * @param lastGood
	 */
	public VertexSizeVerifier(String lastGood) {
		super(lastGood);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
	 */
	@Override
	public boolean verify(final JComponent input) {
		if (input instanceof JTextComponent) {
			JTextComponent textField = (JTextComponent) input;
			double value = 0.0;

			try {
				value = Double.parseDouble(textField.getText());
			} catch (Exception e) {
				return false;
			}

			return value >= MIN_VALUE && value <= MAX_VALUE;
		}

		return false;
	}

}
