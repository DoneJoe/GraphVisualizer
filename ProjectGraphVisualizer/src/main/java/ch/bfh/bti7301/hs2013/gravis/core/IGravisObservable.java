package ch.bfh.bti7301.hs2013.gravis.core;

import java.util.Observer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IGravisObservable {
	
	/**
	 * 
	 * @param o
	 */
	public abstract void addObserver(Observer o);
}
