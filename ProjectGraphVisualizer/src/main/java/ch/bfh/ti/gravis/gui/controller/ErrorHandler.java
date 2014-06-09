package ch.bfh.ti.gravis.gui.controller;

import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;

/**
 * This class supports the error and exception handling of controller and dialog classes.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ErrorHandler {

	private final static String APP_ERR_TITLE = "Fehler";
	private final static String APP_ERR_MSG = "In der Applikation ist ein Fehler aufgetreten! "
			+ "Die Applikation wird jetzt beendet.";

	private MessageDialogAdapter messageDialogAdapter;

	public ErrorHandler() {
		this(new MessageDialogAdapter());
	}

	/**
	 * @param messageDialogAdapter
	 */
	public ErrorHandler(MessageDialogAdapter messageDialogAdapter) {
		this.messageDialogAdapter = messageDialogAdapter == null ? new MessageDialogAdapter()
				: messageDialogAdapter;
	}

	/**
	 * @param owner
	 */
	public ErrorHandler(Component owner) {
		this(new MessageDialogAdapter(owner));
	}

	/**
	 * Sets a new message dialog adapter for this error handler.
	 * 
	 * @param messageDialogAdapter
	 */
	public void setMessageDialogAdapter(
			MessageDialogAdapter messageDialogAdapter) {

		this.messageDialogAdapter = messageDialogAdapter == null ? this.messageDialogAdapter
				: messageDialogAdapter;
	}

	/**
	 * 
	 * @param ex
	 */
	public void handleAppErrorExit(Throwable ex) {
		this.handleAppErrorExit(ex, APP_ERR_MSG);
	}

	/**
	 * 
	 * @param ex
	 * @param errMsg
	 */
	public void handleAppErrorExit(Throwable ex, String errMsg) {
		this.showErrorMessage(ex, errMsg, APP_ERR_TITLE);

		// abnormal termination of application
		System.exit(1);
	}

	/**
	 * 
	 * @param errMsg
	 * @param errTitle
	 */
	public void showErrorMessage(String errMsg, String errTitle) {
		this.messageDialogAdapter.showMessageDialog(errMsg, errTitle,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @param e
	 * @param errMsg
	 * @param errTitle
	 */
	public void showErrorMessage(Throwable e, String errMsg, String errTitle) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		this.messageDialogAdapter.showErrorMessageDialog(errMsg, sw.toString(),
				errTitle);
	}

	/**
	 * 
	 * @param e
	 * @param errMsg
	 */
	public void showErrorMessage(Throwable e, String errMsg) {
		this.showErrorMessage(e, errMsg, APP_ERR_TITLE);
	}
}
