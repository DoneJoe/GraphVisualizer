package ch.bfh.ti.gravis.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ch.bfh.ti.gravis.core.CoreException;
import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.graph.GravisGraphIOException;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import ch.bfh.ti.gravis.gui.dialog.ConfirmDialogAdapter;
import ch.bfh.ti.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.ti.gravis.gui.dialog.GraphPropertyDialogFactory;
import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import ch.bfh.ti.gravis.gui.model.ProtocolModel;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import static ch.bfh.ti.gravis.gui.controller.IMenuToolbarController.EventSource.*;
import static ch.bfh.ti.gravis.gui.model.IAppModel.CalculationState.NOT_CALCULABLE;
import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;

;

/**
 * This class controls all MenuBar and ToolBar events.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class MenuToolbarController extends WindowAdapter implements
		IMenuToolbarController {

	// dialog messages:

	private final static String APP_ERR_TITLE = "Fehler";
	private final static String APP_ERR_MSG = "In der Applikation ist ein Fehler aufgetreten:%s%s%s";
	private final static String FILE_ERR_TITLE = "Öffnen";
	private final static String FILE_ERR_MSG = "Datei nicht gefunden: %s%s";
	private final static String SAVE_CONFIRM_TITLE = "Graph speichern";
	private final static String SAVE_CONFIRM_MSG = "Änderungen speichern?";
	private final static String SAVE_CONFIRM_FILE_MSG = "Änderungen speichern?%s%s%s%s%s";
	private final static String OVERWRITE_TITLE = "Graph speichern";
	private final static String OVERWRITE_MSG = "Vorhandene Datei überschreiben?";

	// protocol messages:

	private final static String OPEN_GRAPH_MSG = "Ein Graph wurde geöffnet:%s"
			+ "Datei: %s%sName: %s%sBeschreibung: %s%s%s";
	private final static String SAVE_GRAPH_MSG = "Der aktuelle Graph wurde gespeichert:%s"
			+ "Datei: %s%sName: %s%sBeschreibung: %s%s%s";
	private final static String SELECT_ALGO_MSG = "Folgender Algorithmus wurde ausgewählt:%s"
			+ "Name: %s%sBeschreibung: %s%s%s"
			+ "Die Animationsschritte werden jetzt berechnet...%s";
	private final static String ALGO_DONE_MSG = 
			"Die Berechnung der Animationsschritte wurde abgeschlossen.%s%s";

	// final fields:

	private final IAppModel model;

	private final ICore core;

	// null pointer fields:

	private FileChooserAdapter fileChooserAdapter = null;

	private ConfirmDialogAdapter confirmDialogAdapter = null;

	private MessageDialogAdapter messageDialogAdapter = null;

	private GraphPropertyDialogFactory graphPropertyDialogFactory = null;

	/**
	 * @param model
	 * @param core
	 */
	protected MenuToolbarController(IAppModel model, ICore core) {
		this.model = model;
		this.core = core;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		try {
			// choose the appropriate event handler
			if (e.getActionCommand().equals(NEW_DIR_GRAPH.toString())) {
				this.handleNewGraphEvent(EdgeType.DIRECTED);
			} else if (e.getActionCommand().equals(NEW_UNDIR_GRAPH.toString())) {
				this.handleNewGraphEvent(EdgeType.UNDIRECTED);
			} else if (e.getActionCommand().equals(OPEN_GRAPH.toString())) {
				this.handleOpenGraphEvent();
			} else if (e.getActionCommand().equals(SAVE_GRAPH.toString())) {
				this.handleSaveGraphEvent();
			} else if (e.getActionCommand().equals(SAVE_GRAPH_AS.toString())) {
				this.handleSaveGraphAsEvent();
			} else if (e.getActionCommand().equals(GRAPH_PROPERTY.toString())) {
				this.handleGraphPropertyEvent();
			} else if (e.getActionCommand().equals(EXIT.toString())) {
				this.handleExitEvent();
			} else if (e.getActionCommand().equals(NEW_CALC.toString())) {
				Object item = this.model.getAlgorithmComboModel()
						.getSelectedItem();

				if (item instanceof String) {
					this.handleAlgorithmEvent((String) item);
				}
			}
		} catch (Exception ex) {
			if (this.messageDialogAdapter != null) {

				// TODO stackTrace angeben

				this.messageDialogAdapter.showMessageDialog(
						String.format(APP_ERR_MSG, ex.getMessage(), LN, LN),
						APP_ERR_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(final ItemEvent e) {
		try {
			// choose the appropriate event handler
			if (e.getStateChange() == ItemEvent.SELECTED
					&& e.getSource() instanceof JComboBox<?>) {

				JComboBox<?> combo = (JComboBox<?>) e.getSource();

				if (combo.getActionCommand().equals(MODE.toString())
						&& e.getItem() instanceof Mode) {

					this.handleModeEvent(((Mode) e.getItem()));
				} else if (combo.getActionCommand()
						.equals(ALGORITHM.toString())
						&& e.getItem() instanceof String) {

					this.handleAlgorithmEvent(((String) e.getItem()).trim());
				}
			}
		} catch (Exception ex) {

			// TODO stackTrace angeben ex.getStackTrace() ist array!

			if (this.messageDialogAdapter != null) {
				this.messageDialogAdapter.showMessageDialog(
						String.format(APP_ERR_MSG, LN, LN, ex.getMessage()),
						APP_ERR_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.controller.IMenuToolbarController#
	 * setConfirmDialogAdapter
	 * (ch.bfh.ti.gravis.gui.dialog.ConfirmDialogAdapter)
	 */
	@Override
	public void setConfirmDialogAdapter(
			ConfirmDialogAdapter confirmDialogAdapter) {
		this.confirmDialogAdapter = confirmDialogAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.controller.IMenuToolbarController#
	 * setFileChooserAdapter (ch.bfh.ti.gravis.gui.dialog.FileChooserAdapter)
	 */
	@Override
	public void setFileChooserAdapter(FileChooserAdapter fileChooserAdapter) {
		this.fileChooserAdapter = fileChooserAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.controller.IMenuToolbarController#
	 * setGraphPropertyDialogFactory
	 * (ch.bfh.ti.gravis.gui.dialog.GraphPropertyDialogFactory)
	 */
	@Override
	public void setGraphPropertyDialogFactory(
			GraphPropertyDialogFactory graphPropertyDialogFactory) {
		this.graphPropertyDialogFactory = graphPropertyDialogFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.gui.controller.IMenuToolbarController#
	 * setMessageDialogAdapter
	 * (ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter)
	 */
	@Override
	public void setMessageDialogAdapter(
			MessageDialogAdapter messageDialogAdapter) {
		this.messageDialogAdapter = messageDialogAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(final WindowEvent e) {
		try {
			this.handleExitEvent();
		} catch (CoreException | GravisGraphIOException ex) {
			// TODO stackTrace angeben

			if (this.messageDialogAdapter != null) {
				this.messageDialogAdapter.showMessageDialog(
						String.format(APP_ERR_MSG, LN, LN, ex.getMessage()),
						APP_ERR_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Checks if the graph is unsaved. If unsaved, the confirm dialog is called,
	 * if not unsaved, true is returned. True is also returned if the graph has
	 * been saved or "not save" is selected from the confirm dialog.
	 * 
	 * @return boolean
	 * @throws CoreException
	 * @throws GravisGraphIOException
	 */
	private boolean checkSave() throws GravisGraphIOException, CoreException {
		if (this.model.isStopped() && this.model.isGraphUnsaved()
				&& this.confirmDialogAdapter != null) {

			String message = this.model.hasGraphFile() ? String.format(
					SAVE_CONFIRM_FILE_MSG, LN, LN, this.model.getGraphFile()
							.getName(), LN, LN) : SAVE_CONFIRM_MSG;
			int dialogResult = this.confirmDialogAdapter.showConfirmDialog(
					message, SAVE_CONFIRM_TITLE,
					JOptionPane.YES_NO_CANCEL_OPTION);

			if ((dialogResult == JOptionPane.YES_OPTION && this
					.handleSaveGraphEvent() == JFileChooser.APPROVE_OPTION)
					|| dialogResult == JOptionPane.NO_OPTION) {
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param item
	 * @throws CoreException
	 */
	private void handleAlgorithmEvent(final String item) throws CoreException {
		if (this.model.isStopped()
				&& this.model.getCalculationState() != NOT_CALCULABLE) {

			// ignores title entry
			if (item.equals(IAppModel.DEFAULT_ALGO_ENTRY.toString())) {
				// update model
				this.model.setNoAlgoSelectedState();
				
				// update view
				this.model.notifyObservers(false, false);
			} else {
				// algorithm selection message
				this.model.notifyObservers(new ProtocolModel(String.format(
						SELECT_ALGO_MSG, LN, item, LN,
						this.core.getAlgorithmDescription(item), LN, LN, LN)));

				// sets steps to initial state
				this.model.setStepPanelEnabled(true);

				// calculates steps
				IGravisListIterator<String> stepIterator = this.core
						.calculateSteps(this.model.getGraph(), item);

				// update model
				this.model.setCalcDoneState(stepIterator);

				// update view
				this.model.notifyObservers(false, false,
						String.format(ALGO_DONE_MSG, LN, LN));
			}
		}
	}

	/**
	 * 
	 * @throws CoreException
	 * @throws GravisGraphIOException
	 */
	private void handleExitEvent() throws CoreException, GravisGraphIOException {
		// handle unsaved graph
		if (!this.checkSave()) {
			return;
		}

		// exits the application
		System.exit(0);
	}

	private void handleGraphPropertyEvent() {
		if (this.model.isStopped() && this.graphPropertyDialogFactory != null) {
			// create a graph property dialog
			JDialog dialog = this.graphPropertyDialogFactory
					.createGraphPropertyDialog(this.model.getGraph());
			dialog.setVisible(true);
		}
	}

	/**
	 * @param mode
	 * 
	 */
	private void handleModeEvent(final Mode mode) {
		if (this.model.isStopped()) {
			// update model
			this.model.setEditMode(mode);

			// update view
			this.model.notifyObservers(false, false);
		}
	}

	/**
	 * 
	 * @param edgeType
	 * @throws CoreException
	 * @throws GravisGraphIOException
	 */
	private void handleNewGraphEvent(final EdgeType edgeType)
			throws CoreException, GravisGraphIOException {

		if (this.model.isStopped()) {
			// handle unsaved graph
			if (!this.checkSave()) {
				return;
			}

			// update model
			this.model.setNewGraphState(edgeType);

			// update view
			this.model.notifyObservers(true, true);
		}
	}

	/**
	 * An existing file must be selected for open a graph.
	 * 
	 * @throws CoreException
	 * @throws GravisGraphIOException
	 */
	private void handleOpenGraphEvent() throws CoreException,
			GravisGraphIOException {

		if (this.model.isStopped()) {
			// handle unsaved graph
			if (!this.checkSave()) {
				return;
			}

			while (this.fileChooserAdapter.showOpenDialog() == JFileChooser.APPROVE_OPTION) {
				File file = this.fileChooserAdapter.getSelectedFile();

				if (file.exists()) {
					// update model
					this.model.setOpenGraphState(this.core.loadGraph(file),
							file);

					// update view
					this.model.notifyObservers(true, true, String.format(
							OPEN_GRAPH_MSG, LN, file.getAbsolutePath(), LN,
							this.model.getGraph().getName(), LN, this.model
									.getGraph().getDescription(), LN, LN));

					return;
				} else {
					this.messageDialogAdapter.showMessageDialog(
							String.format(FILE_ERR_MSG, LN,
									file.getAbsolutePath()), FILE_ERR_TITLE,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * The graph is saved in the selected file if: <br />
	 * - the selected file is a new file <br />
	 * - the selected file is equal to the graphFile <br />
	 * - the selected file exists, is not equal to the graphFile and "overwrite"
	 * is selected in the confirm dialog
	 * 
	 * @return save result
	 * @throws GravisGraphIOException
	 * 
	 */
	private int handleSaveGraphAsEvent() throws GravisGraphIOException {

		if (this.model.isStopped() && this.fileChooserAdapter != null
				&& this.confirmDialogAdapter != null) {

			while (this.fileChooserAdapter.showSaveDialog() == JFileChooser.APPROVE_OPTION) {
				File file = this.fileChooserAdapter.getSelectedFile();

				if (!file.exists()
						|| file.equals(this.model.getGraphFile())
						|| (file.exists()
								&& !file.equals(this.model.getGraphFile()) && this.confirmDialogAdapter
								.showConfirmDialog(OVERWRITE_MSG,
										OVERWRITE_TITLE,
										JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)) {

					// sets steps to initial state
					this.model.setStepPanelEnabled(true);
					
					// save graph
					this.core.saveGraph(this.model.getGraph(), file);

					// update model
					this.model.setSaveGraphState(file);

					// update view
					this.model.notifyObservers(false, false, String.format(
							SAVE_GRAPH_MSG, LN, file.getAbsolutePath(), LN,
							this.model.getGraph().getName(), LN, this.model
									.getGraph().getDescription(), LN, LN));

					return JFileChooser.APPROVE_OPTION;
				}
			}
		}

		return JFileChooser.CANCEL_OPTION;
	}

	/**
	 * If the graphFile is null, handleSaveGraphAsEvent is called. <br />
	 * If the graphFile is not null, the graph is saved with this graphFile.
	 * 
	 * @return save result
	 * @throws GravisGraphIOException
	 */
	private int handleSaveGraphEvent() throws GravisGraphIOException {

		if (this.model.isStopped()) {
			if (this.model.hasGraphFile()) {
				// save graph
				this.core.saveGraph(this.model.getGraph(),
						this.model.getGraphFile());

				// sets steps to initial state
				this.model.setStepPanelEnabled(true);
				
				// update model
				this.model.setSaveGraphState();

				// update view
				this.model.notifyObservers(false, false, String.format(
						SAVE_GRAPH_MSG, LN, LN, this.model.getGraphFile()
								.getAbsolutePath(), LN, this.model.getGraph()
								.getName(), LN, this.model.getGraph()
								.getDescription(), LN, LN));

				return JFileChooser.APPROVE_OPTION;
			} else {
				return this.handleSaveGraphAsEvent();
			}
		}

		return JFileChooser.CANCEL_OPTION;
	}

}
