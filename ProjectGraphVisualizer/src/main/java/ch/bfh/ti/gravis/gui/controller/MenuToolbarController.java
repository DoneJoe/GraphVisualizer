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
import javax.swing.SwingWorker;

import ch.bfh.ti.gravis.core.CoreException;
import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.graph.GravisGraphIOException;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import ch.bfh.ti.gravis.gui.dialog.ConfirmDialogAdapter;
import ch.bfh.ti.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.ti.gravis.gui.dialog.GraphPropertyDialogFactory;
import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.ti.gravis.gui.model.IAppModel;
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

	private final static String OPEN_GRAPH_MSG = "Ein Graph wird geöffnet...";
	private final static String OPEN_GRAPH_DONE_MSG = "erledigt!%s"
			+ "Datei: %s%sName: %s%sBeschreibung: %s%s%s";
	private final static String SAVE_GRAPH_MSG = "Der aktuelle Graph wurde gespeichert:%s"
			+ "Datei: %s%sName: %s%sBeschreibung: %s%s%s";
	private final static String SELECT_ALGO_MSG = "Folgender Algorithmus wurde ausgewählt:%s"
			+ "Name: %s%sBeschreibung: %s%s%s"
			+ "Die Animationsschritte werden jetzt berechnet ... ";
	private final static String ALGO_DONE_MSG = "die Berechnung der Animationsschritte wurde abgeschlossen.%s%s";

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
	 * @param algoName
	 * @throws CoreException
	 */
	private void handleAlgorithmEvent(final String algoName)
			throws CoreException {

		if (this.model.isStopped()
				&& this.model.getCalculationState() != NOT_CALCULABLE) {

			// ignores title entry
			if (algoName.equals(IAppModel.DEFAULT_ALGO_ENTRY.toString())) {
				// update model
				this.model.setNoAlgoSelectedState();

				// update view
				this.model.notifyObservers(false, false);
			} else {
				SwingWorker<IGravisListIterator<String>, Void> worker = this
						.createCalcSwingWorker(algoName);

				// sets step panel to initial state and disables GUI
				this.model.setWorkingState(true);

				// update view with algorithm selection message
				this.model.notifyObservers(false, true,
						String.format(SELECT_ALGO_MSG, LN, algoName, LN,
								this.core.getAlgorithmDescription(algoName),
								LN, LN));

				worker.execute();
			}
		}
	}

	/**
	 * @param algoName
	 * @return SwingWorker<IGravisListIterator<String>, Void>
	 */
	private SwingWorker<IGravisListIterator<String>, Void> createCalcSwingWorker(
			final String algoName) {

		return new SwingWorker<IGravisListIterator<String>, Void>() {

			@Override
			protected IGravisListIterator<String> doInBackground()
					throws Exception {
				// calculates steps
				return MenuToolbarController.this.core.calculateSteps(
						MenuToolbarController.this.model.getGraph(), algoName);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.SwingWorker#done()
			 */
			@Override
			protected void done() {
				// enables GUI
				MenuToolbarController.this.model.setWorkingState(false);

				try {
					// update model
					MenuToolbarController.this.model.setCalcDoneState(this
							.get());

					// update view
					MenuToolbarController.this.model.notifyObservers(false,
							false, String.format(ALGO_DONE_MSG, LN, LN));
				} catch (Exception e) {
					// TODO: handle exception -> exits app
					e.printStackTrace();
				}
			}
		};
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
					SwingWorker<IGravisGraph, Void> worker = this
							.createOpenGraphSwingWorker(file);

					// disables GUI
					this.model.setWorkingState(true);

					// update view
					this.model.notifyObservers(false, true, OPEN_GRAPH_MSG);

					worker.execute();
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
	 * @param file
	 * @return SwingWorker<IGravisGraph, Void>
	 */
	private SwingWorker<IGravisGraph, Void> createOpenGraphSwingWorker(
			final File file) {
		return new SwingWorker<IGravisGraph, Void>() {

			@Override
			protected IGravisGraph doInBackground() throws Exception {
				return MenuToolbarController.this.core.loadGraph(file);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.SwingWorker#done()
			 */
			@Override
			protected void done() {
				// enables GUI
				MenuToolbarController.this.model.setWorkingState(false);

				try {
					// update model
					MenuToolbarController.this.model.setOpenGraphState(
							this.get(), file);

					// update view
					MenuToolbarController.this.model.notifyObservers(true,
							false, String.format(OPEN_GRAPH_DONE_MSG, LN, file
									.getAbsolutePath(), LN,
									MenuToolbarController.this.model.getGraph()
											.getName(), LN,
									MenuToolbarController.this.model.getGraph()
											.getDescription(), LN, LN));
				} catch (Exception e) {
					// TODO: handle exception -> exits app
					e.printStackTrace();
				}
			}
		};
	}

	/**
	 * The graph is saved in the selected file if: <br />
	 * - the selected file is a new file <br />
	 * - the selected file exists and "overwrite" is selected in the confirm
	 * dialog
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
						|| (file.exists() && this.confirmDialogAdapter
								.showConfirmDialog(OVERWRITE_MSG,
										OVERWRITE_TITLE,
										JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)) {

					// sets step panel to initial state
					this.model.setStepPanelState(true);

					// save graph
					this.core.saveGraph(this.model.getGraph(), file);

					// update model
					this.model.setSaveGraphState(file);

					// update view
					this.model.notifyObservers(false, true, String.format(
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
	 * If the graphFile is not null, the graph is saved with the existing graphFile.
	 * 
	 * @return save result
	 * @throws GravisGraphIOException
	 */
	private int handleSaveGraphEvent() throws GravisGraphIOException {

		if (this.model.isStopped()) {
			if (this.model.hasGraphFile()) {
				// sets step panel to initial state
				this.model.setStepPanelState(true);

				// save graph
				this.core.saveGraph(this.model.getGraph(),
						this.model.getGraphFile());

				// update model
				this.model.setSaveGraphState();

				// update view
				this.model.notifyObservers(false, true, String.format(
						SAVE_GRAPH_MSG, LN, this.model.getGraphFile()
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
