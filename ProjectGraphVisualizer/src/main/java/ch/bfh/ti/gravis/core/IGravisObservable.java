package ch.bfh.ti.gravis.core;

import java.util.Observer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IGravisObservable {
	
	/**
	 * 
	 * @param observer
	 */
	public abstract void addObserver(Observer observer);
}
