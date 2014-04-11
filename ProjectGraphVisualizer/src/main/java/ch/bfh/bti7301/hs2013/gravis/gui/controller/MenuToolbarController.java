package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ch.bfh.bti7301.hs2013.gravis.core.CoreException;
import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.ConfirmDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.GraphPropertyDialogFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import static ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController.EventSource.*;

/**
 * This class controls all MenuBar and ToolBar events.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class MenuToolbarController extends Observable implements
		IMenuToolbarController {

	private final static String LN = System.lineSeparator();
	private final static String FILE_ERR_TITLE = "Öffnen";
	private final static String FILE_ERR_MSG = "Datei nicht gefunden: %s";
	private final static String EXIT_TITLE = "Beenden";
	private final static String SAVE_DIALOG_TITLE = "Graph";
	private final static String EXIT_MSG = "Programm wirklich beenden?";
	private final static String SAVE_DIALOG_MSG = "Änderungen speichern?";
	private final static String APP_ERR_TITLE = "Fehler";
	private final static String APP_ERR_MSG = "In der Applikation ist ein Fehler aufgetreten: %s";
	private final static String OPEN_GRAPH_MSG = "%sFolgender Graph wurde geöffnet:%s"
			+ "Datei: %s%sName: %s%sBeschreibung: %s%s";
	private final static String SAVE_GRAPH_MSG = "%sFolgender Graph wurde gespeichert:%s"
			+ "Datei: %s%sName: %s%sBeschreibung: %s%s";
	private final static String SELECT_ALGO_MSG = "%sFolgender Algorithmus wurde ausgewählt:%s"
			+ "Name: %s%sBeschreibung: %s%s"
			+ "Der Algorithmus wurde ausgeführt und die einzelnen Schritte vorgemerkt.%s";

	private final ICore core;

	private final IGuiModel model;

	private FileChooserAdapter fileChooserAdapter = null;

	private ConfirmDialogAdapter confirmDialogAdapter = null;

	private MessageDialogAdapter messageDialogAdapter = null;

	private GraphPropertyDialogFactory graphPropertyDialogFactory = null;

	/**
	 * @param core
	 * @param model
	 */
	protected MenuToolbarController(ICore core, IGuiModel model) {
		this.core = core;
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// choose the applicable event handler
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
				this.messageDialogAdapter.showMessageDialog(
						String.format(APP_ERR_MSG, ex.getMessage()),
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
	public void itemStateChanged(ItemEvent e) {
		try {
			// choose the applicable event handler
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
			if (this.messageDialogAdapter != null) {
				this.messageDialogAdapter.showMessageDialog(
						String.format(APP_ERR_MSG, ex.getMessage()),
						APP_ERR_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController#
	 * setConfirmDialogAdapter
	 * (ch.bfh.bti7301.hs2013.gravis.gui.dialog.ConfirmDialogAdapter)
	 */
	@Override
	public void setConfirmDialogAdapter(
			ConfirmDialogAdapter confirmDialogAdapter) {
		this.confirmDialogAdapter = confirmDialogAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController#
	 * setFileChooserAdapter
	 * (ch.bfh.bti7301.hs2013.gravis.gui.dialog.FileChooserAdapter)
	 */
	@Override
	public void setFileChooserAdapter(FileChooserAdapter fileChooserAdapter) {
		this.fileChooserAdapter = fileChooserAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController#
	 * setGraphPropertyDialogFactory
	 * (ch.bfh.bti7301.hs2013.gravis.gui.dialog.GraphPropertyDialogFactory)
	 */
	@Override
	public void setGraphPropertyDialogFactory(
			GraphPropertyDialogFactory graphPropertyDialogFactory) {
		this.graphPropertyDialogFactory = graphPropertyDialogFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController#
	 * setMessageDialogAdapter
	 * (ch.bfh.bti7301.hs2013.gravis.gui.dialog.MessageDialogAdapter)
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
	 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// no action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		// no action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		try {
			this.handleExitEvent();
		} catch (CoreException ex) {
			if (this.messageDialogAdapter != null) {
				this.messageDialogAdapter.showMessageDialog(
						String.format(APP_ERR_MSG, ex.getMessage()),
						APP_ERR_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent
	 * )
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		// no action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent
	 * )
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		// no action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		// no action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		// no action
	}

	/**
	 * @param item
	 * @throws CoreException
	 * 
	 */
	private void handleAlgorithmEvent(String item) throws CoreException {
		// ignore title entry
		if (item.equals(IGuiModel.DEFAULT_ALGO_ENTRY)) {
			this.model.resetStepEnabledState();
		} else {	
			// update model
			if (this.model.getStepIterator() != null) {
				this.model.getStepIterator().first();
			}
			this.model.setStepEnabledState(this.core.calculateSteps(
					this.model.getGraph(), item));
			this.model.setGraphChanged(false);

			// update view
			this.setChanged();
			this.notifyObservers(this.model.createToolBarModel());
			this.setChanged();
			this.notifyObservers();
			this.setChanged();
			this.notifyObservers(this.model.createStepModel());
			this.setChanged();
			this.notifyObservers(String.format(SELECT_ALGO_MSG, LN, LN, item,
					LN, this.core.getAlgorithmDescription(item), LN, LN));
		}
	}

	/**
	 * 
	 * @throws CoreException
	 */
	private void handleExitEvent() throws CoreException {
		if (this.confirmDialogAdapter != null) {
			// handle unsaved graph
			if (this.model.isGraphUnsaved()) {
				int dialogResult = this.confirmDialogAdapter.showConfirmDialog(
						SAVE_DIALOG_MSG, SAVE_DIALOG_TITLE,
						JOptionPane.YES_NO_CANCEL_OPTION);

				if (dialogResult == JOptionPane.YES_OPTION
						&& this.handleSaveGraphAsEvent() == JFileChooser.APPROVE_OPTION) {
					System.exit(0);
				} else if (dialogResult == JOptionPane.NO_OPTION) {
					System.exit(0);
				}
			} else {
				int dialogResult = this.confirmDialogAdapter.showConfirmDialog(
						EXIT_MSG, EXIT_TITLE, JOptionPane.YES_NO_OPTION);

				if (dialogResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		}
	}

	private void handleGraphPropertyEvent() {
		// create a graph property dialog
		if (this.graphPropertyDialogFactory != null) {
			JDialog dialog = this.graphPropertyDialogFactory
					.createGraphPropertyDialog(this.model.getGraph());
			dialog.setVisible(true);
		}
	}

	/**
	 * @param mode
	 * 
	 */
	private void handleModeEvent(Mode mode) {
		this.model.setPopupEditMode(mode);
		if (this.model.getStepIterator() != null && mode == Mode.EDITING) {
			// update model
			this.model.setStepEnabledState(this.model.getStepIterator());
			
			// update view
			this.setChanged();
			this.notifyObservers();
			this.setChanged();
			this.notifyObservers(this.model.createStepModel());
		}
	}

	/**
	 * 
	 * @param edgeType
	 * @throws CoreException
	 */
	private void handleNewGraphEvent(EdgeType edgeType) throws CoreException {
		// handle unsaved graph
		if (this.model.isGraphUnsaved() && this.confirmDialogAdapter != null) {
			int dialogResult = this.confirmDialogAdapter.showConfirmDialog(
					SAVE_DIALOG_MSG, SAVE_DIALOG_TITLE,
					JOptionPane.YES_NO_CANCEL_OPTION);
			
			if (dialogResult == JOptionPane.YES_OPTION && 
					this.handleSaveGraphAsEvent() != JFileChooser.APPROVE_OPTION) {
					return;
			} else if (dialogResult == JOptionPane.CANCEL_OPTION || 
					dialogResult == JOptionPane.CLOSED_OPTION) {
				return;
			}
		}

		// update model
		this.model.setNewGraphState(edgeType);
		this.model.setAlgorithmComboModel(this.core
				.getAlgorithmNames(this.model.getGraph().getEdgeType()));

		// update view
		this.setChanged();
		this.notifyObservers(this.model.createToolBarModel());
		this.setChanged();
		this.notifyObservers(this.model.getGraph());
		this.setChanged();
		this.notifyObservers(this.model.createStepModel());
	}

	/**
	 * 
	 * @throws CoreException
	 */
	private void handleOpenGraphEvent() throws CoreException {
		if (this.fileChooserAdapter != null
				&& this.messageDialogAdapter != null) {
			// handle unsaved graph
			if (this.model.isGraphUnsaved() && this.confirmDialogAdapter != null) {
				int dialogResult = this.confirmDialogAdapter.showConfirmDialog(
						SAVE_DIALOG_MSG, SAVE_DIALOG_TITLE,
						JOptionPane.YES_NO_CANCEL_OPTION);

				if (dialogResult == JOptionPane.YES_OPTION) {
					this.handleSaveGraphAsEvent();
				} else if (dialogResult == JOptionPane.CANCEL_OPTION ||
						dialogResult == JOptionPane.CLOSED_OPTION) {
					return;
				}
			}

			this.openGraph();
		}
	}

	/**
	 * 
	 * @throws CoreException
	 * @return int
	 * 
	 */
	private int handleSaveGraphAsEvent() throws CoreException {
		int saveResult = Integer.MAX_VALUE;

		if (this.fileChooserAdapter != null) {
			saveResult = this.fileChooserAdapter.showSaveDialog();

			if (saveResult == JFileChooser.APPROVE_OPTION) {
				// save graph and model update
				if (this.model.getStepIterator() != null) {
					this.model
							.setStepEnabledState(this.model.getStepIterator());
				}
				File file = this.fileChooserAdapter.getSelectedFile();
				this.core.saveGraph(this.model.getGraph(), file);
				this.model.setGraphUnsaved(false);

				// update view
				this.setChanged();
				this.notifyObservers();
				this.setChanged();
				this.notifyObservers(String.format(SAVE_GRAPH_MSG, LN, LN, file
						.getAbsolutePath(), LN,
						this.model.getGraph().getName(), LN, this.model
								.getGraph().getDescription(), LN));
			}
		}

		return saveResult;
	}

	/**
	 * 
	 * @throws CoreException
	 */
	private void handleSaveGraphEvent()  throws CoreException {
		// TODO Auto-generated method stub
		this.messageDialogAdapter.showMessageDialog("handleSaveGraphEvent", "", 
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Try to open the file selected from FileChooser.
	 * 
	 * @throws CoreException
	 */
	private void openGraph() throws CoreException {
		while (this.fileChooserAdapter.showOpenDialog() == JFileChooser.APPROVE_OPTION) {
			File file = this.fileChooserAdapter.getSelectedFile();

			if (file.exists()) {
				// update model
				this.model.setOpenGraphState(this.core.importGraph(file));
				this.model.setAlgorithmComboModel(this.core
						.getAlgorithmNames(this.model.getGraph()
								.getEdgeType()));

				// update view
				this.setChanged();
				this.notifyObservers(this.model.createToolBarModel());
				this.setChanged();
				this.notifyObservers(this.model.getGraph());
				this.setChanged();
				this.notifyObservers(this.model.createStepModel());
				this.setChanged();
				this.notifyObservers(String.format(OPEN_GRAPH_MSG, LN, LN,
						file.getAbsolutePath(), LN, this.model.getGraph()
								.getName(), LN, this.model.getGraph()
								.getDescription(), LN));

				break;
			} else {
				this.messageDialogAdapter.showMessageDialog(
						String.format(FILE_ERR_MSG, file.getName()),
						FILE_ERR_TITLE, JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
