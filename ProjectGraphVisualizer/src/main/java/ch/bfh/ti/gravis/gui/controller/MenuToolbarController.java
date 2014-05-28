package ch.bfh.ti.gravis.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

import ch.bfh.ti.gravis.core.CoreException;
import ch.bfh.ti.gravis.core.ICore;
import ch.bfh.ti.gravis.core.algorithm.AlgorithmException;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.util.IGravisListIterator;
import ch.bfh.ti.gravis.gui.dialog.ConfirmDialogAdapter;
import ch.bfh.ti.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.ti.gravis.gui.dialog.GraphPropertyDialogFactory;
import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import static ch.bfh.ti.gravis.gui.controller.IMenuToolbarController.EventSource.*;
import static ch.bfh.ti.gravis.gui.model.IAppModel.CalculationState.NOT_CALCULABLE;
import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;

/**
 * This class controls all MenuBar and ToolBar events.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class MenuToolbarController extends WindowAdapter implements
		IMenuToolbarController {

	// dialog messages:

	private final static String OPEN_ERR_TITLE = "Graph öffnen";
	private final static String SAVE_ERR_TITLE = "Graph speichern";
	private final static String OPEN_ERR_MSG = "Beim Öffnen des Graphen ist ein Fehler aufgetreten!";
	private final static String SAVE_ERR_MSG = "Beim Speichern des Graphen ist ein Fehler aufgetreten!";
	private final static String FILE_OPEN_ERR_MSG = "Datei nicht gefunden: %s%s";
	private final static String FILE_SAVE_ERR_MSG = "Der Dateiname ist ungültig: %s%s";

	private final static String SAVE_CONFIRM_TITLE = "Graph speichern";
	private final static String SAVE_CONFIRM_MSG = "Änderungen speichern?";
	private final static String SAVE_CONFIRM_FILE_MSG = "Änderungen speichern?%s%s%s";
	private final static String OVERWRITE_TITLE = "Graph speichern";
	private final static String OVERWRITE_MSG = "Vorhandene Datei überschreiben?";

	private final static String CALC_ERR_MSG = "Bei der Berechnung ist ein interner "
			+ "Fehler aufgetreten! Die Applikation wird jetzt beendet.";
	private final static String ALGO_ERR_MSG = "%s: %s";

	// protocol messages:

	private final static String OPEN_GRAPH_MSG = "Graph wird geöffnet...";
	private final static String ALGO_CALC_MSG = "Animation wird berechnet... ";

	// final fields:

	private final IAppModel model;

	private final ICore core;

	private final ErrorHandler errHandler;

	// null pointer fields:

	private FileChooserAdapter fileChooserAdapter = null;

	private ConfirmDialogAdapter confirmDialogAdapter = null;

	private GraphPropertyDialogFactory graphPropertyDialogFactory = null;

	/**
	 * @param model
	 * @param core
	 */
	protected MenuToolbarController(IAppModel model, ICore core) {
		this.model = model;
		this.core = core;
		this.errHandler = new ErrorHandler();
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
		} catch (Throwable ex) {
			this.errHandler.handleAppErrorExit(ex);
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
		} catch (Throwable ex) {
			this.errHandler.handleAppErrorExit(ex);
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

		this.errHandler.setMessageDialogAdapter(messageDialogAdapter);
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
		} catch (Throwable ex) {
			this.errHandler.handleAppErrorExit(ex);
		}
	}

	/**
	 * Checks if the graph is unsaved. If unsaved, the confirm dialog is called,
	 * if not unsaved, true is returned. True is also returned if the graph has
	 * been saved or "not save" is selected from the confirm dialog.
	 * 
	 * @return boolean
	 * @throws BadLocationException
	 */
	private boolean checkSave() throws BadLocationException {
		if (this.model.isStopped() && this.model.isGraphUnsaved()
				&& this.confirmDialogAdapter != null) {

			String message = this.model.hasGraphFile() ? String.format(
					SAVE_CONFIRM_FILE_MSG, LN, this.model.getGraphFile()
							.getName(), LN) : SAVE_CONFIRM_MSG;
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
	 * @param algoName
	 * @return SwingWorker<IGravisListIterator<String>, Void>
	 */
	private SwingWorker<IGravisListIterator<String>, Void> createCalcSwingWorker(
			final String algoName) {

		return new SwingWorker<IGravisListIterator<String>, Void>() {

			@Override
			protected IGravisListIterator<String> doInBackground()
					throws Exception {

				// calculate steps
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
				try {
					// enable GUI
					MenuToolbarController.this.model.setWorkingState(false);

					// update model
					MenuToolbarController.this.model.setCalcDoneState(
							this.get(), algoName);

					// update view
					MenuToolbarController.this.model.notifyObservers(false);
				} catch (ExecutionException e) {
					try {
						// update view
						MenuToolbarController.this.model.notifyObservers(false);

						if (e.getCause() instanceof AlgorithmException) {
							AlgorithmException aex = (AlgorithmException) e
									.getCause();
							MenuToolbarController.this.errHandler
									.showErrorMessage(
											e,
											String.format(ALGO_ERR_MSG,
													aex.getAlgorithmName(),
													aex.getUserDialogMsg()));
						} else if (e.getCause() instanceof CoreException) {
							MenuToolbarController.this.errHandler
									.handleAppErrorExit(e, CALC_ERR_MSG);
						} else {
							MenuToolbarController.this.errHandler
									.handleAppErrorExit(e);
						}

						// set model to no algo selected state
						MenuToolbarController.this.model
								.getAlgorithmComboModel()
								.setSelectedItem(
										IAppModel.DEFAULT_ALGO_ENTRY.toString());
					} catch (Throwable ex) {
						MenuToolbarController.this.errHandler
								.handleAppErrorExit(e);
					}
				} catch (Throwable e) {
					MenuToolbarController.this.errHandler.handleAppErrorExit(e);
				}
			}
		};
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
				try {
					// enables GUI
					MenuToolbarController.this.model.setWorkingState(false);

					// updates model
					MenuToolbarController.this.model.setOpenGraphState(
							this.get(), file);

					// updates view
					MenuToolbarController.this.model.notifyObservers(true);
				} catch (ExecutionException e) {
					try {
						Document graphDoc = MenuToolbarController.this.model.getGraphDocument();
						// update view
						MenuToolbarController.this.model.notifyObservers(false);

						if (e.getCause() instanceof FileNotFoundException) {
							MenuToolbarController.this.errHandler
									.showErrorMessage(String.format(
											FILE_OPEN_ERR_MSG, LN,
											file.getAbsolutePath()),
											OPEN_ERR_TITLE);
						} else if (e.getCause() instanceof GraphIOException) {
							MenuToolbarController.this.errHandler
									.showErrorMessage(e, OPEN_ERR_MSG,
											OPEN_ERR_TITLE);
						} else {
							MenuToolbarController.this.errHandler
									.handleAppErrorExit(e);
						}

						graphDoc.remove(0, graphDoc.getLength());						
						graphDoc.insertString(0, MenuToolbarController.this.model.getGraph().
								getDescription(),
								SimpleAttributeSet.EMPTY);
						// set model to no algo selected state
						MenuToolbarController.this.model
								.getAlgorithmComboModel()
								.setSelectedItem(
										IAppModel.DEFAULT_ALGO_ENTRY.toString());
					} catch (Throwable ex) {
						MenuToolbarController.this.errHandler
								.handleAppErrorExit(e);
					}
				} catch (Throwable e) {
					MenuToolbarController.this.errHandler.handleAppErrorExit(e);
				}
			}
		};
	}

	/**
	 * 
	 * @param algoName
	 * @throws BadLocationException
	 */
	private void handleAlgorithmEvent(final String algoName)
			throws BadLocationException {

		boolean noAlgo = algoName.equals(IAppModel.DEFAULT_ALGO_ENTRY
				.toString());

		if (this.model.isStopped()
				&& (this.model.getCalculationState() != NOT_CALCULABLE || noAlgo)) {

			Document algoDoc = this.model.getAlgorithmDocument();
			Document protocolDoc = this.model.getProtocolDocument();

			// ignores title entry
			if (noAlgo) {
				// update model
				this.model.setNoAlgoSelectedState();

				// update view
				this.model.notifyObservers(false);
			} else {
				SwingWorker<IGravisListIterator<String>, Void> worker = this
						.createCalcSwingWorker(algoName);

				// sets step panel to initial state and disables GUI
				this.model.setWorkingState(true);

				// clears documents and inserts algo message
				algoDoc.remove(0, algoDoc.getLength());
				protocolDoc.remove(0, protocolDoc.getLength());
				algoDoc.insertString(0,
						this.core.getAlgorithmDescription(algoName),
						SimpleAttributeSet.EMPTY);
				protocolDoc.insertString(0, ALGO_CALC_MSG,
						SimpleAttributeSet.EMPTY);

				// update view
				this.model.notifyObservers(false);

				worker.execute();
			}
		}
	}

	/**
	 * 
	 * @throws BadLocationException
	 */
	private void handleExitEvent() throws BadLocationException {
		// handle unsaved graph
		if (!this.checkSave()) {
			return;
		}

		// exit application
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
	 * @throws BadLocationException
	 * 
	 */
	private void handleModeEvent(final Mode mode) throws BadLocationException {
		if (this.model.isStopped()) {
			// update model
			this.model.setEditMode(mode);

			// update view
			this.model.notifyObservers(false);
		}
	}

	/**
	 * 
	 * @param edgeType
	 * @throws BadLocationException
	 */
	private void handleNewGraphEvent(final EdgeType edgeType)
			throws BadLocationException {

		if (this.model.isStopped()) {
			// handle unsaved graph
			if (!this.checkSave()) {
				return;
			}

			// update model
			this.model.setNewGraphState(edgeType);
			
			// update view
			this.model.notifyObservers(true);			
		}
	}

	/**
	 * An existing file must be selected for opening a graph.
	 * 
	 * @throws BadLocationException
	 */
	private void handleOpenGraphEvent() throws BadLocationException {
		if (this.model.isStopped() && this.fileChooserAdapter != null) {
			// handle unsaved graph
			if (!this.checkSave()) {
				return;
			}

			while (this.fileChooserAdapter.showOpenDialog() == JFileChooser.APPROVE_OPTION) {
				File file = this.fileChooserAdapter.getSelectedFile();

				if (file.exists()) {
					SwingWorker<IGravisGraph, Void> worker = this
							.createOpenGraphSwingWorker(file);
					Document graphDoc = this.model.getGraphDocument();
					Document algoDoc = this.model.getAlgorithmDocument();
					Document protocolDoc = this.model.getProtocolDocument();

					// sets step panel to initial state and disables GUI
					this.model.setWorkingState(true);

					// clears documents and inserts open graph message
					graphDoc.remove(0, graphDoc.getLength());
					algoDoc.remove(0, algoDoc.getLength());
					protocolDoc.remove(0, protocolDoc.getLength());
					graphDoc.insertString(0, OPEN_GRAPH_MSG,
							SimpleAttributeSet.EMPTY);

					// updates view
					this.model.notifyObservers(false);

					worker.execute();
					return;
				} else {
					this.errHandler.showErrorMessage(String.format(FILE_OPEN_ERR_MSG, LN,
							file.getAbsolutePath()), OPEN_ERR_TITLE);
				}
			}
		}
	}

	/**
	 * The graph is saved in the selected file if: <br />
	 * - the selected file is a new file <br />
	 * - the selected file exists and "overwrite" is selected in the confirm
	 * dialog
	 * 
	 * @return save result
	 * @throws BadLocationException
	 * 
	 */
	private int handleSaveGraphAsEvent() throws BadLocationException {
		if (this.model.isStopped() && this.fileChooserAdapter != null
				&& this.confirmDialogAdapter != null) {

			while (this.fileChooserAdapter.showSaveDialog() == JFileChooser.APPROVE_OPTION) {
				File file = this.fileChooserAdapter.getSelectedFile();

				if (!file.exists()
						|| (file.exists() && this.confirmDialogAdapter
								.showConfirmDialog(OVERWRITE_MSG,
										OVERWRITE_TITLE,
										JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)) {

					// set step panel to initial state
					this.model.setStepPanelState(true);

					try {
						// save graph
						this.core.saveGraph(this.model.getGraph(), file);

						// update model
						this.model.setSaveGraphState(file);
					} catch (FileNotFoundException e) {
						this.errHandler.showErrorMessage(
								String.format(FILE_SAVE_ERR_MSG, LN,
										file.getAbsolutePath()), SAVE_ERR_TITLE);
					} catch (GraphIOException e) {
						this.errHandler.showErrorMessage(e, SAVE_ERR_MSG, SAVE_ERR_TITLE);
					}

					// update view
					this.model.notifyObservers(false);

					return JFileChooser.APPROVE_OPTION;
				}
			}
		}

		return JFileChooser.CANCEL_OPTION;
	}

	/**
	 * If the graphFile is null, handleSaveGraphAsEvent is called. <br />
	 * If the graphFile is not null, the graph is saved with the existing
	 * graphFile.
	 * 
	 * @return save result
	 * @throws BadLocationException
	 */
	private int handleSaveGraphEvent() throws BadLocationException {
		if (this.model.isStopped()) {
			if (this.model.hasGraphFile()) {
				// set step panel to initial state
				this.model.setStepPanelState(true);

				try {
					// save graph
					this.core.saveGraph(this.model.getGraph(),
							this.model.getGraphFile());

					// update model
					this.model.setSaveGraphState();
				} catch (FileNotFoundException e) {
					this.errHandler.showErrorMessage(String.format(FILE_SAVE_ERR_MSG, LN,
							this.model.getGraphFile().getAbsolutePath()),
							SAVE_ERR_TITLE);
				} catch (GraphIOException e) {
					this.errHandler.showErrorMessage(e, SAVE_ERR_MSG, SAVE_ERR_TITLE);
				}

				// update view
				this.model.notifyObservers(false);

				return JFileChooser.APPROVE_OPTION;
			} else {
				return this.handleSaveGraphAsEvent();
			}
		}

		return JFileChooser.CANCEL_OPTION;
	}

}
