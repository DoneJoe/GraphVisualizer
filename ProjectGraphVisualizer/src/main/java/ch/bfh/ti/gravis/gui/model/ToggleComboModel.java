package ch.bfh.ti.gravis.gui.model;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;

import ch.bfh.ti.gravis.gui.visualization.GravisModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ToggleComboModel {

	private final static Mode DEFAULT_MODE = Mode.EDITING;
	
	private final JToggleButton.ToggleButtonModel pickingToggleModel,
			editingToggleModel, transformingToggleModel;

	private final ComboBoxModel<Mode> editModeComboModel;
	
	private final ButtonGroup btnGroupEditMode;
	
	private boolean locked;

	protected ToggleComboModel() {
		this.pickingToggleModel = new JToggleButton.ToggleButtonModel();
		this.editingToggleModel = new JToggleButton.ToggleButtonModel();
		this.transformingToggleModel = new JToggleButton.ToggleButtonModel();		
		this.editModeComboModel = new DefaultComboBoxModel<>(GravisModalGraphMouse.getModes());		
		this.btnGroupEditMode = new ButtonGroup();
		this.locked = false;
		
		// add listeners:
		
		this.pickingToggleModel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				ToggleComboModel.this.setSelectedComboItem(e, Mode.PICKING);
			}
		});
		
		this.editingToggleModel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				ToggleComboModel.this.setSelectedComboItem(e, Mode.EDITING);
			}
		});
		
		this.transformingToggleModel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				ToggleComboModel.this.setSelectedComboItem(e, Mode.TRANSFORMING);
			}
		});		
	}

	/**
	 * Adds button to button group, sets the button model to button
	 * 
	 * @param button
	 * @param mode
	 */
	public void add(AbstractButton button, Mode mode) {
		
		// TODO NullPointer Exception-handling
		
		if (mode == Mode.PICKING) {
			button.setModel(this.getPickingToggleModel());
		} else if (mode == Mode.EDITING) {
			button.setModel(this.getEditingToggleModel());
		} else if (mode == Mode.TRANSFORMING) {
			button.setModel(this.getTransformingToggleModel());
		}

		this.btnGroupEditMode.add(button);
		button.setSelected(mode == this.getDefaultMode());
	}

	/**
	 * @return default mode
	 */
	public Mode getDefaultMode() {
		return DEFAULT_MODE;
	}
	
	/**
	 * @return ButtonModel
	 */
	public ButtonModel getEditingToggleModel() {
		return this.editingToggleModel;
	}

	/**
	 * @return ComboBoxModel
	 */
	public ComboBoxModel<Mode> getModeModel() {
		return this.editModeComboModel;
	}

	/**
	 * @return ButtonModel
	 */
	public ButtonModel getPickingToggleModel() {
		return this.pickingToggleModel;
	}

	/**
	 * @param mode
	 * @return ButtonModel
	 */
	public ButtonModel getToggleModel(Mode mode) {
		if (mode == Mode.EDITING) {
			return this.getEditingToggleModel();
		} else if (mode == Mode.TRANSFORMING) {
			return this.getTransformingToggleModel();
		}
		
		return this.getPickingToggleModel();
	}

	/**
	 * @return ButtonModel
	 */
	public ButtonModel getTransformingToggleModel() {
		return this.transformingToggleModel;
	}

	/**
	 * @param mode
	 */
	public void setSelectedItem(Mode mode) {
		
		// TODO NullPointer Exception-handling
		
		if (!this.locked) {
			this.locked = true;
			
			if (mode == Mode.PICKING) {
				this.getPickingToggleModel().setSelected(true);
			} else if (mode == Mode.EDITING) {
				this.getEditingToggleModel().setSelected(true);
			} else if (mode == Mode.TRANSFORMING) {
				this.getTransformingToggleModel().setSelected(true);;
				}
			
			this.locked = false;
		}
	}

	/**
	 * 
	 * @param event
	 * @param mode
	 */
	private void setSelectedComboItem(ItemEvent event, Mode mode) {
		if (!this.locked && event.getStateChange() == ItemEvent.SELECTED) {
			this.locked = true;
			this.editModeComboModel.setSelectedItem(mode);
			this.locked = false;
		}
	}

}
