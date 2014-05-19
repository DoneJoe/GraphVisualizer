package ch.bfh.ti.gravis.gui.model;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;

import ch.bfh.ti.gravis.gui.visualization.GravisModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ToggleComboGroup {

	private final JToggleButton pickingToggleButton, editingToggleButton,
			transformingToggleButton;

	private final JComboBox<?> editModeComboBox;

	protected ToggleComboGroup() {
		// create elements:

		this.pickingToggleButton = new JToggleButton();
		this.editingToggleButton = new JToggleButton();
		this.transformingToggleButton = new JToggleButton();
		this.editModeComboBox = new JComboBox<>(
				GravisModalGraphMouse.getModes());
		ButtonGroup btnGroupEditMode = new ButtonGroup();

		// fill button group:

		btnGroupEditMode.add(this.pickingToggleButton);
		btnGroupEditMode.add(this.editingToggleButton);
		btnGroupEditMode.add(this.transformingToggleButton);

		// add listeners:

		this.pickingToggleButton.addItemListener(this
				.createToggleListener(Mode.PICKING));
		this.editingToggleButton.addItemListener(this
				.createToggleListener(Mode.EDITING));
		this.transformingToggleButton.addItemListener(this
				.createToggleListener(Mode.TRANSFORMING));
		this.editModeComboBox.addItemListener(this.createModeComboListener());

		// select picking item:
		
		this.pickingToggleButton.setSelected(true);
	}

	/**
	 * Returns the selected item in the mode combo box.
	 * 
	 * @return Mode
	 */
	public Mode getMode() {
		return (Mode) this.editModeComboBox.getSelectedItem();
	}

	/**
	 * @return JComboBox<?>
	 */
	public JComboBox<?> getModeComboBox() {
		return this.editModeComboBox;
	}

	/**
	 * @param mode
	 * @return JToggleButton
	 */
	public JToggleButton getModeToggleButton(final Mode mode) {
		if (mode == null) {
			return this.pickingToggleButton;
		}
		
		switch (mode) {
		case EDITING:
			return this.editingToggleButton;
		case TRANSFORMING:
			return this.transformingToggleButton;
		default:
			return this.pickingToggleButton;
		}
	}

	/**
	 * @param enabled
	 */
	public void setToggleComboGroupEnabled(boolean enabled) {
		this.pickingToggleButton.setEnabled(enabled);
		this.editingToggleButton.setEnabled(enabled);
		this.transformingToggleButton.setEnabled(enabled);
		this.editModeComboBox.setEnabled(enabled);
	}

	/**
	 * @return ItemListener
	 */
	private ItemListener createModeComboListener() {
		return new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				ToggleComboGroup.this.setSelectedToggleItem(e);
			}
		};
	}

	/**
	 * @return ItemListener
	 */
	private ItemListener createToggleListener(final Mode mode) {
		return new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				ToggleComboGroup.this.setSelectedComboItem(e, mode);
			}
		};
	}

	/**
	 * 
	 * @param event
	 * @param newMode
	 */
	private void setSelectedComboItem(ItemEvent event, Mode newMode) {
		if (event.getStateChange() == ItemEvent.SELECTED
				&& this.getMode() != newMode) {

			this.editModeComboBox.setSelectedItem(newMode);
		}
	}

	/**
	 * @param e
	 */
	@SuppressWarnings("incomplete-switch")
	private void setSelectedToggleItem(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED
				&& e.getItem() instanceof Mode) {

			Mode mode = (Mode) e.getItem();

			switch (mode) {
			case PICKING:
				if (!this.pickingToggleButton.isSelected()) {
					this.pickingToggleButton.setSelected(true);
				}
				break;
			case EDITING:
				if (!this.editingToggleButton.isSelected()) {
					this.editingToggleButton.setSelected(true);
				}
				break;
			case TRANSFORMING:
				if (!this.transformingToggleButton.isSelected()) {
					this.transformingToggleButton.setSelected(true);
				}
			}
		}
	}

}
