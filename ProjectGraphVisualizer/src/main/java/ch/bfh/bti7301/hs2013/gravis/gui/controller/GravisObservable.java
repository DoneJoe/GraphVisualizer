package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.util.Observer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface GravisObservable {
	
	/**
	 * 
	 * @param o
	 */
	public abstract void addObserver(Observer o);
}
