package ch.bfh.ti.gravis.gui.controller;

import static ch.bfh.ti.gravis.gui.controller.IStepController.EventSource.*;

import java.awt.event.ActionEvent;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;

import ch.bfh.ti.gravis.gui.model.IAppModel;
import ch.bfh.ti.gravis.gui.model.ProtocolModel;
import ch.bfh.ti.gravis.gui.model.VisualizationViewModel;
import static ch.bfh.ti.gravis.gui.model.IAppModel.CalculationState.*;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class StepController implements IStepController {

	private static final double FACTOR = 1000.0;
	
	private final IAppModel model;

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
		}
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
		if (e.getSource() instanceof JSpinner && !this.model.isPlaying() &&
				this.model.getCalculationState() == CALCULATED) {
			
			Object value = ((JSpinner) e.getSource()).getValue();
			
			if (value instanceof Double) {
				int delay = (int) (((Double) value) * FACTOR); 
				
				this.model.getTimer().setInitialDelay(delay);
				this.model.getTimer().setDelay(delay);
			}
		}

	}

	private void handleBackEvent() {
		if (!this.model.isPlaying() && this.model.hasStepIterator()
				&& this.model.getStepIterator().hasPrevious() &&
				this.model.getCalculationState() == CALCULATED) {

			String stepMessage = this.model.getStepIterator().previous();

			// update model
			this.model.updateStepPanelModels();
			this.model.getProgressBarModel().setValue(
					this.model.getProgressBarModel().getValue() - 1);

			// update view
			this.model.notifyObservers(false, false, stepMessage);
		}
	}

	private void handleBeginningEvent() {
		if (!this.model.isPlaying() && this.model.hasStepIterator()
				&& this.model.getStepIterator().hasPrevious() &&
				this.model.getCalculationState() == CALCULATED) {

			String stepMessage = this.model.getStepIterator().first();

			// update model
			this.model.updateStepPanelModels();
			this.model.getProgressBarModel().setValue(0);

			// update view
			this.model.notifyObservers(false, false, stepMessage);
		}
	}

	private void handleEndEvent() {
		if (!this.model.isPlaying() && this.model.hasStepIterator()
				&& this.model.getStepIterator().hasNext() &&
				this.model.getCalculationState() == CALCULATED) {

			String stepMessage = this.model.getStepIterator().last();

			// update model
			this.model.updateStepPanelModels();
			this.model.getProgressBarModel().setValue(
					this.model.getStepIterator().size());

			// update view
			this.model.notifyObservers(false, false, stepMessage);
		}
	}

	private void handleForwardEvent() {
		if (!this.model.isPlaying() && this.model.hasStepIterator()
				&& this.model.getStepIterator().hasNext()
				&& this.model.getCalculationState() == CALCULATED) {

			String stepMessage = this.model.getStepIterator().next();

			// update model
			this.model.updateStepPanelModels();
			this.model.getProgressBarModel().setValue(
					this.model.getProgressBarModel().getValue() + 1);

			// update view
			this.model.notifyObservers(false, false, stepMessage);
		}
	}

	private void handlePauseEvent() {
		if (this.model.isPlaying()
				&& this.model.getCalculationState() == CALCULATED) {

			// update model
			this.model.setPausedState();

			// update view
			this.model.notifyObservers(false, false);
		}
	}

	private void handlePlayEvent() {
		if (!this.model.isPlaying()
				&& this.model.getCalculationState() == CALCULATED) {

			// update model
			this.model.setPlayingState();

			// update view
			this.model.notifyObservers(false, false);

			// start timer
			if (!this.model.getTimer().isRunning()) {
				this.model.getTimer().start();
			}
		}
	}

	private void handleStopEvent() {
		if (!this.model.isStopped()
				&& this.model.getCalculationState() == CALCULATED) {

			// stop timer
			if (this.model.getTimer().isRunning()) {
				this.model.getTimer().stop();
			}

			// update model
			this.model.setStoppedState();

			// update view
			this.model.notifyObservers(false, false);
		}
	}

	private void handleTimerEvent() {
		if (this.model.isPlaying() && this.model.hasStepIterator()
				&& this.model.getStepIterator().hasNext()
				&& this.model.getCalculationState() == CALCULATED) {

			String stepMessage = this.model.getStepIterator().next();

			// update model
			this.model.updateStepPanelModels();
			this.model.getProgressBarModel().setValue(
					this.model.getProgressBarModel().getValue() + 1);

			// update view
			this.model.notifyObservers(new VisualizationViewModel(this.model
					.getGraph(), false));
			this.model.notifyObservers(new ProtocolModel(stepMessage));

			// check, if last step is done
			if (!this.model.getStepIterator().hasNext()) {
				// stop timer
				if (this.model.getTimer().isRunning()) {
					this.model.getTimer().stop();
				}

				// update model
				this.model.setEndAnimationState();

				// update view
				this.model.notifyObservers(false, false);
			}
		}
	}

}
