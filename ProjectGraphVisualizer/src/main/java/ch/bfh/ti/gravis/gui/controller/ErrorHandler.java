package ch.bfh.ti.gravis.gui.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class ErrorHandler {

	private final static String APP_ERR_TITLE = "Fehler";
	private final static String APP_ERR_MSG = "In der Applikation ist ein Fehler aufgetreten! "
			+ "Die Applikation wird jetzt beendet.";

	private MessageDialogAdapter messageDialogAdapter;

	protected ErrorHandler() {
		this(new MessageDialogAdapter());
	}

	/**
	 * @param messageDialogAdapter
	 */
	protected ErrorHandler(MessageDialogAdapter messageDialogAdapter) {
		this.messageDialogAdapter = messageDialogAdapter == null ? new MessageDialogAdapter()
				: messageDialogAdapter;
	}

	/**
	 * 
	 * @param messageDialogAdapter
	 */
	protected void setMessageDialogAdapter(
			MessageDialogAdapter messageDialogAdapter) {

		this.messageDialogAdapter = messageDialogAdapter == null ? this.messageDialogAdapter
				: messageDialogAdapter;
	}

	/**
	 * 
	 * @param ex
	 */
	protected void handleAppErrorExit(Throwable ex) {
		this.handleAppErrorExit(ex, APP_ERR_MSG);
	}

	/**
	 * 
	 * @param ex
	 * @param errMsg
	 */
	protected void handleAppErrorExit(Throwable ex, String errMsg) {
		this.showErrorMessage(ex, errMsg, APP_ERR_TITLE);

		// abnormal termination of application
		System.exit(1);
	}

	/**
	 * 
	 * @param errMsg
	 * @param errTitle
	 */
	protected void showErrorMessage(String errMsg, String errTitle) {
		this.messageDialogAdapter.showMessageDialog(errMsg, errTitle,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @param e
	 * @param errMsg
	 * @param errTitle
	 */
	protected void showErrorMessage(Throwable e, String errMsg, String errTitle) {
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
	protected void showErrorMessage(Throwable e, String errMsg) {
		this.showErrorMessage(e, errMsg, APP_ERR_TITLE);
	}
}
