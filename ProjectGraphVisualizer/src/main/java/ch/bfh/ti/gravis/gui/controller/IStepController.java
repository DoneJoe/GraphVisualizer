package ch.bfh.ti.gravis.gui.controller;

import java.awt.event.ActionListener;

import javax.swing.event.ChangeListener;

import ch.bfh.ti.gravis.core.IGravisObservable;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IStepController extends ActionListener, ChangeListener,  IGravisObservable {

	/**
	 * This enum constants are usefull to distinguish between different kinds of
	 * ui events.
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 * 
	 */
	public static enum EventSource {
		PLAY, PAUSE, STOP, BEGINNING, BACK, FORWARD, END
	}

}
