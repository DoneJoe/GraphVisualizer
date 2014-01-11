package ch.bfh.bti7301.hs2013.gravis.gui;

import javax.swing.JPanel;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import ch.bfh.bti7301.hs2013.gravis.gui.controller.IStepController;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IStepController.EventSource;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;

import javax.swing.ImageIcon;
import javax.swing.BoxLayout;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class StepPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1543453026902655850L;

	/**
	 * Create the panel.
	 * @param model 
	 * @param stepController 
	 */
	public StepPanel(IStepController stepController, IGuiModel model) {
		// TODO set mnemonics
		
		this.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel = new JPanel();
		this.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		JProgressBar progressBar = new JProgressBar();
		panel.add(progressBar);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		
		JButton btnBeginning = new JButton();
		btnBeginning.setIcon(new ImageIcon("D:\\Daten\\Documents\\Programmierung\\Java\\Eclipse_BFH_Project_1\\GraphVisualizer\\ProjectGraphVisualizer\\src\\main\\resources\\META-INF\\images\\Rewind24.gif"));
		panel_1.add(btnBeginning);
		
		JButton btnBack = new JButton();
		btnBack.setIcon(new ImageIcon("D:\\Daten\\Documents\\Programmierung\\Java\\Eclipse_BFH_Project_1\\GraphVisualizer\\ProjectGraphVisualizer\\src\\main\\resources\\META-INF\\images\\StepBack24.gif"));
		panel_1.add(btnBack);
		
		JButton btnForward = new JButton();
		btnForward.setIcon(new ImageIcon("D:\\Daten\\Documents\\Programmierung\\Java\\Eclipse_BFH_Project_1\\GraphVisualizer\\ProjectGraphVisualizer\\src\\main\\resources\\META-INF\\images\\StepForward24.gif"));
		panel_1.add(btnForward);
		
		JButton btnEnd = new JButton();
		btnEnd.setIcon(new ImageIcon("D:\\Daten\\Documents\\Programmierung\\Java\\Eclipse_BFH_Project_1\\GraphVisualizer\\ProjectGraphVisualizer\\src\\main\\resources\\META-INF\\images\\FastForward24.gif"));
		panel_1.add(btnEnd);

		// add listeners
		btnBeginning.setActionCommand(EventSource.BEGINNING.toString());
		btnBeginning.addActionListener(stepController);
		btnBack.setActionCommand(EventSource.BACK.toString());
		btnBack.addActionListener(stepController);
		btnForward.setActionCommand(EventSource.FORWARD.toString());
		btnForward.addActionListener(stepController);
		btnEnd.setActionCommand(EventSource.END.toString());
		btnEnd.addActionListener(stepController);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
