package ch.bfh.ti.gravis.gui.controller;

import java.awt.event.ActionListener;

import ch.bfh.ti.gravis.core.IGravisObservable;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IStepController extends ActionListener, IGravisObservable {

	/**
	 * This enum constants are usefull to distinguish between different kinds of
	 * ui events.
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 * 
	 */
	public static enum EventSource {
		BEGINNING, BACK, FORWARD, END
	}

}
