package ch.bfh.ti.gravis.gui.component;

import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import ch.bfh.ti.gravis.gui.controller.IStepController;
import ch.bfh.ti.gravis.gui.controller.IStepController.EventSource;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import ch.bfh.ti.gravis.gui.model.IStepModel;
import static ch.bfh.ti.gravis.gui.GuiFactory.loadImage;

import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JSpinner;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class StepPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1543453026902655850L;

	private static final String PROGRESS_LABEL = "Schritt %d/%d ";
	private static final String DELAY_LABEL = "Zeitintervall: ";

	private static final String PLAY_ICON = "start-icon_32.png";
	private static final String PAUSE_ICON = "pause-icon_32.png";
	private static final String STOP_ICON = "stop-icon_32.png";
	
	private static final String BEGINNING_ICON = "skip-backward-icon_32.png";
	private static final String BACK_ICON = "backward-icon_32.png";
	private static final String FORWARD_ICON = "forward-icon_32.png";
	private static final String END_ICON = "skip-forward-icon_32.png";
	
	private static final String BEGINNING_TOOLTIP = "Zum Anfang";
	private static final String BACK_TOOLTIP = "Einen Schritt zurück";
	private static final String FORWARD_TOOLTIP = "Einen Schritt vorwärts";
	private static final String END_TOOLTIP = "Zum Ende";
	
	private static final String DELAY_TOOLTIP = "Zeitintervall festlegen";
	private static final String PLAY_TOOLTIP = "Animation abspielen";
	private static final String PAUSE_TOOLTIP = "Animation anhalten";
	private static final String STOP_TOOLTIP = "Animation stoppen";
	
	private JLabel lblProgress;
	
	private JProgressBar progressBar;
	
	/**
	 * Create the panel.
	 * @param model 
	 * @param stepController 
	 * @throws IOException 
	 */
	public StepPanel(final IStepController stepController, final IAppModel model) 
			throws IOException {
		
		// panel and layout declarations:
		
		JPanel panelProgress = new JPanel();
		JPanel panelStep = new JPanel();
		JPanel panelPlayButtons = new JPanel();
		JPanel panelStepButtons = new JPanel();
		FlowLayout playBtnLayout = new FlowLayout();
		FlowLayout stepBtnLayout = new FlowLayout();
		
		// set layouts:
		
		this.setLayout(new GridLayout(2, 0, 0, 0));
		panelProgress.setLayout(new BoxLayout(panelProgress, BoxLayout.X_AXIS));
		panelStep.setLayout(new GridLayout(0, 2));
		playBtnLayout.setAlignment(FlowLayout.RIGHT);
		stepBtnLayout.setAlignment(FlowLayout.LEFT);
		panelPlayButtons.setLayout(playBtnLayout);
		panelStepButtons.setLayout(stepBtnLayout);
		
		// compose panels:
		
		this.add(panelProgress);
		this.add(panelStep);
		panelStep.add(panelPlayButtons);
		panelStep.add(panelStepButtons);
		
		// progress panel:
		
		this.lblProgress = new JLabel(String.format(PROGRESS_LABEL, 0, 0));
		this.lblProgress.setHorizontalAlignment(SwingConstants.LEFT);
		panelProgress.add(this.lblProgress);
		
		this.progressBar = new JProgressBar();
		this.progressBar.setToolTipText(String.format(PROGRESS_LABEL, 0, 0));
		this.progressBar.setMinimum(0);
		this.progressBar.setMaximum(0);
		this.progressBar.setValue(0);
		panelProgress.add(this.progressBar);
		
		// play button panel:
		// TODO disable buttons
		
		JLabel lblDelay = new JLabel(DELAY_LABEL);
		panelPlayButtons.add(lblDelay);
		
		JSpinner spinnerDelay = new JSpinner();
		spinnerDelay.setToolTipText(DELAY_TOOLTIP);
		spinnerDelay.setEnabled(true);
		panelPlayButtons.add(spinnerDelay);
		
		JButton btnPlay = new JButton();
		btnPlay.setIcon(new ImageIcon(loadImage(PLAY_ICON)));
		btnPlay.setToolTipText(PLAY_TOOLTIP);
		btnPlay.setEnabled(true);
		panelPlayButtons.add(btnPlay);
		
		JButton btnPause = new JButton();
		btnPause.setIcon(new ImageIcon(loadImage(PAUSE_ICON)));
		btnPause.setToolTipText(PAUSE_TOOLTIP);
		btnPause.setEnabled(true);
		panelPlayButtons.add(btnPause);
		
		JButton btnStop = new JButton();
		btnStop.setIcon(new ImageIcon(loadImage(STOP_ICON)));
		btnStop.setToolTipText(STOP_TOOLTIP);
		btnStop.setEnabled(true);
		panelPlayButtons.add(btnStop);
		
		// step button panel:
		
		JButton btnBeginning = new JButton();
		btnBeginning.setIcon(new ImageIcon(loadImage(BEGINNING_ICON)));
		btnBeginning.setToolTipText(BEGINNING_TOOLTIP);
		btnBeginning.setEnabled(false);
		panelStepButtons.add(btnBeginning);
		
		JButton btnBack = new JButton();
		btnBack.setIcon(new ImageIcon(loadImage(BACK_ICON)));
		btnBack.setToolTipText(BACK_TOOLTIP);
		btnBack.setEnabled(false);
		panelStepButtons.add(btnBack);
		
		JButton btnForward = new JButton();
		btnForward.setIcon(new ImageIcon(loadImage(FORWARD_ICON)));
		btnForward.setToolTipText(FORWARD_TOOLTIP);
		btnForward.setEnabled(false);
		panelStepButtons.add(btnForward);
		
		JButton btnEnd = new JButton();
		btnEnd.setIcon(new ImageIcon(loadImage(END_ICON)));
		btnEnd.setToolTipText(END_TOOLTIP);
		btnEnd.setEnabled(false);
		panelStepButtons.add(btnEnd);

		// add listeners:
		
		btnBeginning.setActionCommand(EventSource.BEGINNING.toString());
		btnBeginning.addActionListener(stepController);
		btnBack.setActionCommand(EventSource.BACK.toString());
		btnBack.addActionListener(stepController);
		btnForward.setActionCommand(EventSource.FORWARD.toString());
		btnForward.addActionListener(stepController);
		btnEnd.setActionCommand(EventSource.END.toString());
		btnEnd.addActionListener(stepController);
		
		btnPlay.addActionListener(stepController);
		btnPlay.setActionCommand(EventSource.PLAY.toString());
		btnPause.addActionListener(stepController);
		btnPause.setActionCommand(EventSource.PAUSE.toString());
		btnStop.addActionListener(stepController);
		btnStop.setActionCommand(EventSource.STOP.toString());
		spinnerDelay.addChangeListener(stepController);
		
		// set models:
		// TODO models aus AppModel lesen
		
		model.setProgressBarModel(this.progressBar.getModel());
		model.setBeginningButtonModel(btnBeginning.getModel());
		model.setForwardButtonModel(btnForward.getModel());
		model.setBackButtonModel(btnBack.getModel());
		model.setEndButtonModel(btnEnd.getModel());
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
