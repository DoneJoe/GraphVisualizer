package ch.bfh.ti.gravis.gui.controller;

import static ch.bfh.ti.gravis.gui.controller.IStepController.EventSource.*;

import java.awt.event.ActionEvent;
import java.util.Observable;

import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import ch.bfh.ti.gravis.gui.model.IAppModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class StepController extends Observable implements IStepController {

	private final IAppModel model;

	/**
	 * @param model
	 */
	protected StepController(IAppModel model) {
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equals(BEGINNING.toString())) {
			this.handleBeginningEvent();
		} else if (e.getActionCommand().equals(BACK.toString())) {
			this.handleBackEvent();
		} else if (e.getActionCommand().equals(FORWARD.toString())) {
			this.handleForwardEvent();
		} else if (e.getActionCommand().equals(END.toString())) {
			this.handleEndEvent();
		}
	}

	private void handleEndEvent() {
		IGravisListIterator<String> stepIterator = this.model.getStepIterator();

		if (stepIterator != null && stepIterator.hasNext()) {
			// update model
			String stepMessage = stepIterator.last();
			this.model.updateStepButtonModels(stepIterator.hasPrevious(),
					stepIterator.hasPrevious(), stepIterator.hasNext(),
					stepIterator.hasNext());
			// TODO nicht auf null pruefen
			if (this.model.getProgressBarModel() != null) {
				this.model.getProgressBarModel().setValue(stepIterator.size());
			}
			
			this.updateView(stepMessage);
		}
	}

	private void handleForwardEvent() {
		IGravisListIterator<String> stepIterator = this.model.getStepIterator();

		if (stepIterator != null && stepIterator.hasNext()) {
			// update model
			String stepMessage = stepIterator.next();
			this.model.updateStepButtonModels(stepIterator.hasPrevious(),
					stepIterator.hasPrevious(), stepIterator.hasNext(),
					stepIterator.hasNext());
			if (this.model.getProgressBarModel() != null) {
				this.model.getProgressBarModel().setValue(
						this.model.getProgressBarModel().getValue() + 1);
			}
			
			this.updateView(stepMessage);
		}
	}

	private void handleBackEvent() {
		IGravisListIterator<String> stepIterator = this.model.getStepIterator();

		if (stepIterator != null && stepIterator.hasPrevious()) {
			// update model
			String stepMessage = stepIterator.previous();
			this.model.updateStepButtonModels(stepIterator.hasPrevious(),
					stepIterator.hasPrevious(), stepIterator.hasNext(),
					stepIterator.hasNext());
			if (this.model.getProgressBarModel() != null) {
				this.model.getProgressBarModel().setValue(
						this.model.getProgressBarModel().getValue() - 1);
			}

			this.updateView(stepMessage);
		}
	}

	private void handleBeginningEvent() {
		IGravisListIterator<String> stepIterator = this.model.getStepIterator();

		if (stepIterator != null && stepIterator.hasPrevious()) {
			// update model
			String stepMessage = stepIterator.first();
			this.model.updateStepButtonModels(stepIterator.hasPrevious(),
					stepIterator.hasPrevious(), stepIterator.hasNext(),
					stepIterator.hasNext());
			if (this.model.getProgressBarModel() != null) {
				this.model.getProgressBarModel().setValue(0);
			}

			this.updateView(stepMessage);
		}
	}

	/**
	 * Updates all observers.
	 * 
	 * @param stepMessage
	 */
	private void updateView(String stepMessage) {
		this.setChanged();
		this.notifyObservers();
		this.setChanged();
		this.notifyObservers(this.model.createStepModel());
		this.setChanged();
		this.notifyObservers(stepMessage);
	}
	
}
