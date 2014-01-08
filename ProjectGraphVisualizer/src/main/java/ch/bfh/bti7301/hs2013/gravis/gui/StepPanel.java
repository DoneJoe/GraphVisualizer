package ch.bfh.bti7301.hs2013.gravis.gui;

import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import ch.bfh.bti7301.hs2013.gravis.gui.controller.IStepController;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class StepPanel extends JPanel {

	private static final long serialVersionUID = 1543453026902655850L;

	/**
	 * Create the panel.
	 * @param model 
	 * @param stepController 
	 */
	public StepPanel(IStepController stepController, IGuiModel model) {
		this.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel = new JPanel();
		this.add(panel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		JProgressBar progressBar = new JProgressBar();
		panel.add(progressBar);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		
		JButton btnNewButton = new JButton("New button");
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel_1.add(btnNewButton_1);

	}

}
