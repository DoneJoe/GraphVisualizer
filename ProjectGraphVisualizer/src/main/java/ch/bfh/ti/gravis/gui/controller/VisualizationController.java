package ch.bfh.ti.gravis.gui.controller;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.ti.gravis.gui.model.IAppModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class VisualizationController implements IEditGraphEventListener {

	private final IAppModel model;

	private final MessageDialogAdapter messageDialogAdapter;

	/**
	 * 
	 * @param model
	 * @param messageDialogAdapter
	 */
	protected VisualizationController(IAppModel model,
			MessageDialogAdapter messageDialogAdapter) {
		this.model = model;
		this.messageDialogAdapter = messageDialogAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IEditGraphEventListener#
	 * handleGraphItemsChangedEvent
	 * (ch.bfh.ti.gravis.core.graph.item.IGraphItem,
	 * ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type)
	 */
	@Override
	public void handleGraphItemsChangedEvent(final IGraphItem source,
			final Type type) {

		try {
			this.updateModelAndView(type);
		} catch (Exception e) {
			
			// TODO Exception Handling -> Message adapter in construktur
			// uebergeben
			this.messageDialogAdapter.showMessageDialog(
					"handleGraphItemsChangedEvent error", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IEditGraphEventListener#
	 * handleGraphPropertiesChangedEvent
	 * (ch.bfh.ti.gravis.core.graph.IGravisGraph,
	 * ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type)
	 */
	@Override
	public void handleGraphPropertiesChangedEvent(final IGravisGraph source,
			final Type type) {

		try {
			this.updateModelAndView(type);

			this.model.getGraphDocument().remove(0,
					this.model.getGraphDocument().getLength());
			this.model.getGraphDocument().insertString(0,
					source.getDescription(), SimpleAttributeSet.EMPTY);
		} catch (BadLocationException e) {
			
			// TODO Exception Handling -> Message adapter in construktur
			// uebergeben
			e.printStackTrace();
		}
	}

	/**
	 * @param type
	 * @throws BadLocationException
	 */
	private void updateModelAndView(final Type type)
			throws BadLocationException {
		if (this.model.isStopped() && !this.model.isWorking()) {
			// update model
			this.model.setEditGraphState(type == Type.VISUAL_EDITED);

			// update view
			this.model.notifyObservers(false);
		}
	}

}
