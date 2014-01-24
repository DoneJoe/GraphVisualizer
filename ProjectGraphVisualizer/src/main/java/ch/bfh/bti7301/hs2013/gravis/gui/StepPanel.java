package ch.bfh.bti7301.hs2013.gravis.gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import static ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants.*;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IStepController;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IStepController.EventSource;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IStepModel;

import javax.swing.ImageIcon;
import javax.swing.BoxLayout;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class StepPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1543453026902655850L;

	private static final String PROGRESS_LABEL = "Schritt %d/%d ";
	private static final String BEGINNING_ICON = "Rewind24.gif";
	private static final String BACK_ICON = "StepBack24.gif";
	private static final String FORWARD_ICON = "StepForward24.gif";
	private static final String END_ICON = "FastForward24.gif";
	private static final String BEGINNING_TOOLTIP = "Zum Anfang";
	private static final String BACK_TOOLTIP = "Einen Schritt zurück";
	private static final String FORWARD_TOOLTIP = "Einen Schritt vorwärts";
	private static final String END_TOOLTIP = "Zum Ende";
	
	private JLabel lblProgress;
	
	private JProgressBar progressBar;
	
	/**
	 * Create the panel.
	 * @param model 
	 * @param stepController 
	 * @throws IOException 
	 */
	public StepPanel(IStepController stepController, IGuiModel model) throws IOException {
		// TODO set mnemonics
		
		this.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panelProgress = new JPanel();
		this.add(panelProgress);
		panelProgress.setLayout(new BoxLayout(panelProgress, BoxLayout.X_AXIS));
		
		this.lblProgress = new JLabel(String.format(PROGRESS_LABEL, 0, 0));
		this.lblProgress.setHorizontalAlignment(SwingConstants.LEFT);
		panelProgress.add(this.lblProgress);
		
		this.progressBar = new JProgressBar();
		this.progressBar.setToolTipText(String.format(PROGRESS_LABEL, 0, 0));
		this.progressBar.setMinimum(0);
		this.progressBar.setMaximum(0);
		this.progressBar.setValue(0);
		panelProgress.add(this.progressBar);
		
		JPanel panelStep = new JPanel();
		this.add(panelStep);
		
		JButton btnBeginning = new JButton();
		btnBeginning.setIcon(new ImageIcon(this.loadImage(BEGINNING_ICON)));
		btnBeginning.setToolTipText(BEGINNING_TOOLTIP);
		btnBeginning.setEnabled(false);
		panelStep.add(btnBeginning);
		
		JButton btnBack = new JButton();
		btnBack.setIcon(new ImageIcon(this.loadImage(BACK_ICON)));
		btnBack.setToolTipText(BACK_TOOLTIP);
		btnBack.setEnabled(false);
		panelStep.add(btnBack);
		
		JButton btnForward = new JButton();
		btnForward.setIcon(new ImageIcon(this.loadImage(FORWARD_ICON)));
		btnForward.setToolTipText(FORWARD_TOOLTIP);
		btnForward.setEnabled(false);
		panelStep.add(btnForward);
		
		JButton btnEnd = new JButton();
		btnEnd.setIcon(new ImageIcon(this.loadImage(END_ICON)));
		btnEnd.setToolTipText(END_TOOLTIP);
		btnEnd.setEnabled(false);
		panelStep.add(btnEnd);

		// add listeners
		btnBeginning.setActionCommand(EventSource.BEGINNING.toString());
		btnBeginning.addActionListener(stepController);
		btnBack.setActionCommand(EventSource.BACK.toString());
		btnBack.addActionListener(stepController);
		btnForward.setActionCommand(EventSource.FORWARD.toString());
		btnForward.addActionListener(stepController);
		btnEnd.setActionCommand(EventSource.END.toString());
		btnEnd.addActionListener(stepController);
		
		// set models
		model.setProgressBarModel(this.progressBar.getModel());
		model.setBeginningButtonModel(btnBeginning.getModel());
		model.setForwardButtonModel(btnForward.getModel());
		model.setBackButtonModel(btnBack.getModel());
		model.setEndButtonModel(btnEnd.getModel());
	}

	/**
	 * Loads an icon ressource with the given name.
	 * 
	 * @param iconName
	 * @return Image
	 * @throws IOException
	 */
	private Image loadImage(String iconName) throws IOException {
		return ImageIO.read(this.getClass().getResourceAsStream(
				IMAGES_DIR + iconName));
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof IStepModel) {
			IStepModel model = (IStepModel) arg;
			String labelText = String.format(PROGRESS_LABEL, model.getStepValue(), 
					model.getStepMaximum());
			
			// update progress labels with current step values
			this.lblProgress.setText(labelText);
			this.progressBar.setToolTipText(labelText);
		}
	}

}
