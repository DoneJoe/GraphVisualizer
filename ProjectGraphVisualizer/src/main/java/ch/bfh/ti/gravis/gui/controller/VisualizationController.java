package ch.bfh.ti.gravis.gui.controller;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.ti.gravis.gui.model.IAppModel;

/**
 * This class represents a controller in the MVC-pattern. The model and the view is updated
 * after an edit graph event is occured.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class VisualizationController implements IEditGraphEventListener {

	private final IAppModel model;

	private final ErrorHandler errHandler;

	/**
	 * 
	 * @param model
	 * @param messageDialogAdapter
	 */
	protected VisualizationController(IAppModel model,
			MessageDialogAdapter messageDialogAdapter) {
		
		this.model = model;
		this.errHandler = new ErrorHandler(messageDialogAdapter);
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
		} catch (Throwable ex) {
			this.errHandler.handleAppErrorExit(ex);
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
			
			// show the graph description
			this.model.getGraphDocument().remove(0,
					this.model.getGraphDocument().getLength());
			this.model.getGraphDocument().insertString(0,
					source.getDescription(), SimpleAttributeSet.EMPTY);
		} catch (Throwable ex) {
			this.errHandler.handleAppErrorExit(ex);
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
