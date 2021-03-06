package ch.bfh.ti.gravis.gui.controller;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowListener;

import ch.bfh.ti.gravis.gui.dialog.ConfirmDialogAdapter;
import ch.bfh.ti.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.ti.gravis.gui.dialog.GraphPropertyDialogFactory;
import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;

/**
 * This interface represents a controller in the MVC-pattern. The listeners in
 * this interface handle menubar and toolbar events.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IMenuToolbarController extends ActionListener, WindowListener,
		ItemListener {

	/**
	 * This enum constants are usefull to distinguish between different kinds of
	 * ui events.
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 * 
	 */
	public static enum EventSource {
		NEW_DIR_GRAPH, NEW_UNDIR_GRAPH, OPEN_GRAPH, SAVE_GRAPH, SAVE_GRAPH_AS, GRAPH_PROPERTY, EXIT, NEW_CALC, MODE, ALGORITHM
	}

	/**
	 * @param confirmDialogAdapter
	 */
	public abstract void setConfirmDialogAdapter(
			ConfirmDialogAdapter confirmDialogAdapter);

	/**
	 * @param fileChooserAdapter
	 */
	public abstract void setFileChooserAdapter(
			FileChooserAdapter fileChooserAdapter);

	/**
	 * @param graphPropertyDialogFactory
	 */
	public abstract void setGraphPropertyDialogFactory(
			GraphPropertyDialogFactory graphPropertyDialogFactory);

	/**
	 * @param messageDialogAdapter
	 */
	public abstract void setMessageDialogAdapter(
			MessageDialogAdapter messageDialogAdapter);

}
