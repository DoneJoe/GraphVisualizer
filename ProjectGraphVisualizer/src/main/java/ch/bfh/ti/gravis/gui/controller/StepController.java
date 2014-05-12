package ch.bfh.ti.gravis.gui.controller;

import static ch.bfh.ti.gravis.gui.controller.IStepController.EventSource.*;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import static ch.bfh.ti.gravis.gui.model.IAppModel.CalculationState.*;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class StepController implements IStepController {

	private static final double FACTOR = 1000.0;

	private final IAppModel model;

	private MessageDialogAdapter messageDialogAdapter;

	/**
	 * @param model
	 */
	protected StepController(IAppModel model) {
		this.model = model;

		// add timer listener
		this.model.getTimer().setActionCommand(TIMER_EVENT.toString());
		this.model.getTimer().addActionListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		try {
			if (e.getActionCommand().equals(BEGINNING.toString())) {
				this.handleBeginningEvent();
			} else if (e.getActionCommand().equals(BACK.toString())) {
				this.handleBackEvent();
			} else if (e.getActionCommand().equals(FORWARD.toString())) {
				this.handleForwardEvent();
			} else if (e.getActionCommand().equals(END.toString())) {
				this.handleEndEvent();
			} else if (e.getActionCommand().equals(PLAY.toString())) {
				this.handlePlayEvent();
			} else if (e.getActionCommand().equals(PAUSE.toString())) {
				this.handlePauseEvent();
			} else if (e.getActionCommand().equals(STOP.toString())) {
				this.handleStopEvent();
			} else if (e.getActionCommand().equals(TIMER_EVENT.toString())) {
				this.handleTimerEvent();
			}
		} catch (Exception ex) {
			// TODO Exception handling
			ex.printStackTrace();
			if (this.messageDialogAdapter != null) {
				this.messageDialogAdapter.showMessageDialog("err", "err",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.gui.controller.IStepController#setMessageDialogAdapter
	 * (ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter)
	 */
	@Override
	public void setMessageDialogAdapter(
			MessageDialogAdapter messageDialogAdapter) {

		this.messageDialogAdapter = messageDialogAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent
	 * )
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		try {
			if (e.getSource() instanceof JSpinner && !this.model.isPlaying()
					&& this.model.getCalculationState() == CALCULATED) {

				Object value = ((JSpinner) e.getSource()).getValue();

				if (value instanceof Double) {
					int delay = (int) (((Double) value) * FACTOR);

					this.model.getTimer().setInitialDelay(delay);
					this.model.getTimer().setDelay(delay);
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @throws BadLocationException
	 */
	private void handleBackEvent() throws BadLocationException {
		if (!this.model.isPlaying() && this.model.hasStepIterator()
				&& this.model.getStepIterator().hasPrevious()
				&& this.model.getCalculationState() == CALCULATED) {

			Document doc = this.model.getProtocolDocument();
			String stepMessage = this.model.getStepIterator().previous();

			// updates model
			this.model.updateStepPanelModels();
			this.model.getProgressBarModel().setValue(
					this.model.getProgressBarModel().getValue() - 1);
			doc.remove(doc.getLength() - stepMessage.length(),
					stepMessage.length());

			// updates view
			this.model.notifyObservers(false);
		}
	}

	/**
	 * 
	 * @throws BadLocationException
	 */
	private void handleBeginningEvent() throws BadLocationException {
		if (!this.model.isPlaying() && this.model.hasStepIterator()
				&& this.model.getStepIterator().hasPrevious()
				&& this.model.getCalculationState() == CALCULATED) {

			Document doc = this.model.getProtocolDocument();
			String stepMessage = this.model.getStepIterator().first();

			// updates model
			this.model.updateStepPanelModels();
			this.model.getProgressBarModel().setValue(0);
			doc.remove(doc.getLength() - stepMessage.length(),
					stepMessage.length());

			// updates view
			this.model.notifyObservers(false);
		}
	}

	/**
	 * 
	 * @throws BadLocationException
	 */
	private void handleEndEvent() throws BadLocationException {
		if (!this.model.isPlaying() && this.model.hasStepIterator()
				&& this.model.getStepIterator().hasNext()
				&& this.model.getCalculationState() == CALCULATED) {

			Document doc = this.model.getProtocolDocument();
			String stepMessage = this.model.getStepIterator().last();

			// updates model
			this.model.updateStepPanelModels();
			this.model.getProgressBarModel().setValue(
					this.model.getStepIterator().size());
			doc.insertString(doc.getLength(), stepMessage,
					SimpleAttributeSet.EMPTY);

			// updates view
			this.model.notifyObservers(false);
		}
	}

	/**
	 * 
	 * @return forward step ok
	 * @throws BadLocationException
	 */
	private boolean handleForwardEvent() throws BadLocationException {
		if (this.model.hasStepIterator()
				&& this.model.getStepIterator().hasNext()
				&& this.model.getCalculationState() == CALCULATED) {

			Document doc = this.model.getProtocolDocument();
			String stepMessage = this.model.getStepIterator().next();

			// updates model
			this.model.updateStepPanelModels();
			this.model.getProgressBarModel().setValue(
					this.model.getProgressBarModel().getValue() + 1);
			doc.insertString(doc.getLength(), stepMessage,
					SimpleAttributeSet.EMPTY);

			// updates view
			this.model.notifyObservers(false);

			return true;
		}

		return false;
	}

	/**
	 * 
	 * @throws BadLocationException
	 */
	private void handlePauseEvent() throws BadLocationException {
		if (this.model.isPlaying()
				&& this.model.getCalculationState() == CALCULATED) {

			// updates model
			this.model.setPausedState();

			// updates view
			this.model.notifyObservers(false);
		}
	}

	/**
	 * 
	 * @throws BadLocationException
	 */
	private void handlePlayEvent() throws BadLocationException {
		if (!this.model.isPlaying()
				&& this.model.getCalculationState() == CALCULATED) {

			// updates model
			this.model.setPlayingState();

			// updates view
			this.model.notifyObservers(false);

			// starts timer
			if (!this.model.getTimer().isRunning()) {
				this.model.getTimer().start();
			}
		}
	}

	/**
	 * 
	 * @throws BadLocationException
	 */
	private void handleStopEvent() throws BadLocationException {
		if (!this.model.isStopped()
				&& this.model.getCalculationState() == CALCULATED) {

			// stops timer
			if (this.model.getTimer().isRunning()) {
				this.model.getTimer().stop();
			}

			// updates model
			this.model.setStoppedState();

			// updates view
			this.model.notifyObservers(false);
		}
	}

	/**
	 * 
	 * @throws BadLocationException
	 */
	private void handleTimerEvent() throws BadLocationException {
		// checks if last step is done
		if (this.model.isPlaying() && this.handleForwardEvent()
				&& !this.model.getStepIterator().hasNext()) {

			// stops timer
			if (this.model.getTimer().isRunning()) {
				this.model.getTimer().stop();
			}

			// updates model
			this.model.setEndAnimationState();

			// updates view
			this.model.notifyObservers(false);
		}
	}

}
