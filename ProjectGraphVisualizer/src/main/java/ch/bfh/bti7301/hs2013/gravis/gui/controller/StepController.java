package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import static ch.bfh.bti7301.hs2013.gravis.gui.controller.IStepController.EventSource.*;

import java.awt.event.ActionEvent;
import java.util.Observable;

import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.core.util.IGravisListIterator;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class StepController extends Observable implements IStepController {

	private ICore core;
	
	private IGuiModel model;
	
	/**
	 * @param core
	 * @param model
	 */
	protected StepController(ICore core, IGuiModel model) {
		this.core = core;
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
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

	/**
	 * 
	 */
	private void handleEndEvent() {
		IGravisListIterator<String> stepIterator = this.model.getStepIterator();
		
		if (stepIterator != null) {
			stepIterator.last();
			
			this.setChanged();
			this.notifyObservers(this.model.getGraph());
		}
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 */
	private void handleForwardEvent() {
		IGravisListIterator<String> stepIterator = this.model.getStepIterator();
		
		if (stepIterator != null && stepIterator.hasNext()) {
			stepIterator.next();
			
			this.setChanged();
			this.notifyObservers(this.model.getGraph());
		}
		
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	private void handleBackEvent() {
		IGravisListIterator<String> stepIterator = this.model.getStepIterator();
		
		if (stepIterator != null && stepIterator.hasPrevious()) {
			stepIterator.previous();
			
			this.setChanged();
			this.notifyObservers(this.model.getGraph());
		}
		
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	private void handleBeginningEvent() {
		IGravisListIterator<String> stepIterator = this.model.getStepIterator();
		
		if (stepIterator != null) {
			stepIterator.first();
			
			this.setChanged();
			this.notifyObservers(this.model.getGraph());
		}
		
		// TODO Auto-generated method stub
		
	}

}
