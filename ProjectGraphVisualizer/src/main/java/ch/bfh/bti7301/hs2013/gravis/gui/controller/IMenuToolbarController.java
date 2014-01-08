package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.awt.event.ActionListener;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IMenuToolbarController extends ActionListener, GravisObservable {

	/**
	 * This enum constants are usefull to distinguish between different kinds of
	 * ui events.
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 * 
	 */
	public enum EventSource {
		NEW_DIR_GRAPH, NEW_UNDIR_GRAPH, OPEN_GRAPH, SAVE_GRAPH, GRAPH_PROPERTIES
	}

}
