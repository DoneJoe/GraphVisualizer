package ch.bfh.ti.gravis.gui.component;

import javax.swing.JPanel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import ch.bfh.ti.gravis.core.util.GravisColor;
import ch.bfh.ti.gravis.gui.controller.IStepController;
import ch.bfh.ti.gravis.gui.controller.IStepController.EventSource;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import ch.bfh.ti.gravis.gui.model.StepModel;
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

	private static final String BORDER_LABEL = "Player";

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

	private static final String DELAY_TOOLTIP = "Zeitintervall festlegen (Sekunden)";
	private static final String PLAY_TOOLTIP = "Animation abspielen";
	private static final String PAUSE_TOOLTIP = "Animation anhalten";
	private static final String STOP_TOOLTIP = "Animation stoppen";

	private static final String SPINNER_FORMAT = "#.## s";

	private JLabel lblProgress;

	private JProgressBar progressBar;

	private JSpinner spinnerDelay;

	/**
	 * Create the panel.
	 * 
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
		FlowLayout playBtnLayout = new FlowLayout(FlowLayout.RIGHT);
		FlowLayout stepBtnLayout = new FlowLayout(FlowLayout.LEFT);

		// sets layouts:

		this.setLayout(new GridLayout(2, 0, 0, 0));
		panelProgress.setLayout(new BoxLayout(panelProgress, BoxLayout.X_AXIS));
		panelStep.setLayout(new GridLayout(0, 2));
		panelPlayButtons.setLayout(playBtnLayout);
		panelStepButtons.setLayout(stepBtnLayout);

		// composes panels:

		this.add(panelProgress);
		this.add(panelStep);
		panelStep.add(panelPlayButtons);
		panelStep.add(panelStepButtons);

		// sets border and background colors:

		this.setBorder(BorderFactory.createTitledBorder(BORDER_LABEL));
		this.setBackground(GravisColor.LIGHT_BLUE);
		panelProgress.setBackground(GravisColor.LIGHT_BLUE);
		panelStep.setBackground(GravisColor.LIGHT_BLUE);
		panelPlayButtons.setBackground(GravisColor.LIGHT_BLUE);
		panelStepButtons.setBackground(GravisColor.LIGHT_BLUE);

		// progress panel:

		this.lblProgress = new JLabel(String.format(PROGRESS_LABEL, 0, 0));
		this.lblProgress.setHorizontalAlignment(SwingConstants.LEFT);
		panelProgress.add(this.lblProgress);

		this.progressBar = new JProgressBar(model.getProgressBarModel());
		this.progressBar.setToolTipText(String.format(PROGRESS_LABEL, 0, 0));
		this.progressBar.setEnabled(false);
		panelProgress.add(this.progressBar);

		// play button panel:

		JLabel lblDelay = new JLabel(DELAY_LABEL);
		panelPlayButtons.add(lblDelay);

		this.spinnerDelay = new JSpinner(model.getDelaySpinnerModel());
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(
				this.spinnerDelay, SPINNER_FORMAT);
		editor.getTextField().setEditable(false);
		this.spinnerDelay.setEditor(editor);
		this.spinnerDelay.setEnabled(false);
		this.spinnerDelay.setToolTipText(DELAY_TOOLTIP);
		panelPlayButtons.add(this.spinnerDelay);

		JButton btnPlay = new JButton();
		btnPlay.setIcon(new ImageIcon(loadImage(PLAY_ICON)));
		btnPlay.setToolTipText(PLAY_TOOLTIP);
		panelPlayButtons.add(btnPlay);

		JButton btnPause = new JButton();
		btnPause.setIcon(new ImageIcon(loadImage(PAUSE_ICON)));
		btnPause.setToolTipText(PAUSE_TOOLTIP);
		panelPlayButtons.add(btnPause);

		JButton btnStop = new JButton();
		btnStop.setIcon(new ImageIcon(loadImage(STOP_ICON)));
		btnStop.setToolTipText(STOP_TOOLTIP);
		panelPlayButtons.add(btnStop);

		// beginning action and button:

		Action beginningAction = this.createStepAction(stepController);
		beginningAction.putValue(Action.ACTION_COMMAND_KEY, EventSource.BEGINNING.toString());
		
		JButton btnBeginning = new JButton();
		btnBeginning.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.CTRL_MASK),
				EventSource.BEGINNING.toString());
		btnBeginning.getActionMap().put(EventSource.BEGINNING.toString(), beginningAction);
		btnBeginning.setIcon(new ImageIcon(loadImage(BEGINNING_ICON)));
		btnBeginning.setToolTipText(BEGINNING_TOOLTIP);
		panelStepButtons.add(btnBeginning);

		// back action and button:
		
		Action backAction = this.createStepAction(stepController);
		backAction.putValue(Action.ACTION_COMMAND_KEY, EventSource.BACK.toString());
		
		JButton btnBack = new JButton();
		btnBack.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.ALT_MASK),
				EventSource.BACK.toString());
		btnBack.getActionMap().put(EventSource.BACK.toString(), backAction);
		btnBack.setIcon(new ImageIcon(loadImage(BACK_ICON)));
		btnBack.setToolTipText(BACK_TOOLTIP);
		panelStepButtons.add(btnBack);

		// forward action and button:
		
		Action forwardAction = this.createStepAction(stepController);
		forwardAction.putValue(Action.ACTION_COMMAND_KEY, EventSource.FORWARD.toString());
		
		JButton btnForward = new JButton();
		btnForward.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.ALT_MASK),
				EventSource.FORWARD.toString());
		btnForward.getActionMap().put(EventSource.FORWARD.toString(), forwardAction);
		btnForward.setIcon(new ImageIcon(loadImage(FORWARD_ICON)));
		btnForward.setToolTipText(FORWARD_TOOLTIP);
		panelStepButtons.add(btnForward);

		// end action and button:
		
		Action endAction = this.createStepAction(stepController);
		endAction.putValue(Action.ACTION_COMMAND_KEY, EventSource.END.toString());
		
		JButton btnEnd = new JButton();
		btnEnd.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.CTRL_MASK),
				EventSource.END.toString());
		btnEnd.getActionMap().put(EventSource.END.toString(), endAction);
		btnEnd.setIcon(new ImageIcon(loadImage(END_ICON)));
		btnEnd.setToolTipText(END_TOOLTIP);
		panelStepButtons.add(btnEnd);

		// sets button models:

		btnPlay.setModel(model.getPlayButtonModel());
		btnPause.setModel(model.getPauseButtonModel());
		btnStop.setModel(model.getStopButtonModel());
		btnBeginning.setModel(model.getBeginningButtonModel());
		btnForward.setModel(model.getForwardButtonModel());
		btnBack.setModel(model.getBackButtonModel());
		btnEnd.setModel(model.getEndButtonModel());

		// adds listeners:

		this.spinnerDelay.addChangeListener(stepController);
		btnPlay.addActionListener(stepController);
		btnPlay.setActionCommand(EventSource.PLAY.toString());
		btnPause.addActionListener(stepController);
		btnPause.setActionCommand(EventSource.PAUSE.toString());
		btnStop.addActionListener(stepController);
		btnStop.setActionCommand(EventSource.STOP.toString());
		btnBeginning.setActionCommand(EventSource.BEGINNING.toString());
		btnBeginning.addActionListener(stepController);		
		btnBack.setActionCommand(EventSource.BACK.toString());
		btnBack.addActionListener(stepController);
		btnForward.setActionCommand(EventSource.FORWARD.toString());
		btnForward.addActionListener(stepController);
		btnEnd.setActionCommand(EventSource.END.toString());
		btnEnd.addActionListener(stepController);

		// disables step component focus:

		this.lblProgress.setFocusable(false);
		this.progressBar.setFocusable(false);
		lblDelay.setFocusable(false);
		this.spinnerDelay.setFocusable(false);
		btnPlay.setFocusable(false);
		btnPause.setFocusable(false);
		btnStop.setFocusable(false);
		btnForward.setFocusable(false);
		btnBack.setFocusable(false);
		btnBeginning.setFocusable(false);
		btnEnd.setFocusable(false);

		panelProgress.setFocusable(false);
		panelStep.setFocusable(false);
		panelPlayButtons.setFocusable(false);
		panelStepButtons.setFocusable(false);
		this.setFocusable(false);
		
		// disable focusable in all child components of spinner
		for (Component comp : this.spinnerDelay.getEditor().getComponents()) {
			comp.setFocusable(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof StepModel) {
			StepModel model = (StepModel) arg;
			String labelText = String.format(PROGRESS_LABEL,
					this.progressBar.getValue(), this.progressBar.getMaximum());

			// updates progressBar and spinner
			this.lblProgress.setText(labelText);
			this.progressBar.setToolTipText(labelText);
			this.progressBar.setEnabled(model.isProgressBarEnabled());
			this.progressBar.setIndeterminate(model.isProgressIndeterminate());
			this.spinnerDelay.setEnabled(model.isSpinnerEnabled());
		}
	}

	/**
	 * @param stepController
	 * @return Action
	 */
	private Action createStepAction(final IStepController stepController) {
		return new AbstractAction() {
			
			private static final long serialVersionUID = 7561990250078559350L;

			@Override
			public void actionPerformed(ActionEvent arg) {
				stepController.actionPerformed(arg);
			}
		};
	}

}
