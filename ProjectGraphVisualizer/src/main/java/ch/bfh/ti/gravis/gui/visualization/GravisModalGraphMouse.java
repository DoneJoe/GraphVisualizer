package ch.bfh.ti.gravis.gui.visualization;

import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;

import javax.swing.JComboBox;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.gui.popup.CreateVertexMenu;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.annotations.AnnotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.LabelEditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;

/**
 * This class extends the existing JUNG class {@code EditingModalGraphMouse}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GravisModalGraphMouse extends
		EditingModalGraphMouse<IVertex, IEdge> {

	/**
	 * 
	 * @param rc
	 * @param vertexFactory
	 * @param edgeFactory
	 * @param edgePopup
	 * @param vertexPopup
	 * @param vertexCreatePopup
	 */
	public GravisModalGraphMouse(final RenderContext<IVertex, IEdge> rc,
			final Factory<IVertex> vertexFactory,
			final Factory<IEdge> edgeFactory, final JPopupMenu edgePopup,
			final JPopupMenu vertexPopup,
			final CreateVertexMenu vertexCreatePopup) {

		super(rc, vertexFactory, edgeFactory);

		if (this.popupEditingPlugin instanceof GravisPopupGraphMousePlugin) {
			((GravisPopupGraphMousePlugin) this.popupEditingPlugin)
					.setEdgePopup(edgePopup);
			((GravisPopupGraphMousePlugin) this.popupEditingPlugin)
					.setVertexPopup(vertexPopup);
			((GravisPopupGraphMousePlugin) this.popupEditingPlugin)
					.setVertexCreatePopup(vertexCreatePopup);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.ics.jung.visualization.control.EditingModalGraphMouse#loadPlugins
	 * ()
	 */
	@Override
	protected void loadPlugins() {
		// GravisPickingGraphMousePlugin class used
		this.pickingPlugin = new GravisPickingGraphMousePlugin();
		
		this.animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<IVertex, IEdge>();
		this.translatingPlugin = new TranslatingGraphMousePlugin(
				InputEvent.BUTTON1_MASK);
		this.scalingPlugin = new ScalingGraphMousePlugin(
				new CrossoverScalingControl(), 0, this.in, this.out);
		this.rotatingPlugin = new RotatingGraphMousePlugin();
		this.shearingPlugin = new ShearingGraphMousePlugin();
		
		// GravisEditingGraphMousePlugin class used
		this.editingPlugin = new GravisEditingGraphMousePlugin(
				this.vertexFactory, this.edgeFactory);
		
		this.labelEditingPlugin = new LabelEditingGraphMousePlugin<IVertex, IEdge>();
		this.annotatingPlugin = new AnnotatingGraphMousePlugin<IVertex, IEdge>(
				rc);
		
		// GravisPopupGraphMousePlugin class used
		this.popupEditingPlugin = new GravisPopupGraphMousePlugin(
				this.vertexFactory, this.edgeFactory);
		
		this.add(scalingPlugin);
		this.setMode(Mode.EDITING);
	}

	@Override
	protected void setPickingMode() {
		this.remove(this.translatingPlugin);
		this.remove(this.rotatingPlugin);
		this.remove(this.shearingPlugin);
		this.remove(this.editingPlugin);
		this.remove(this.annotatingPlugin);
		this.add(this.pickingPlugin);
		this.add(this.animatedPickingPlugin);
		this.add(this.popupEditingPlugin);
	}

	@Override
	protected void setTransformingMode() {
		this.remove(this.pickingPlugin);
		this.remove(this.animatedPickingPlugin);
		this.remove(this.editingPlugin);
		this.remove(this.annotatingPlugin);
		this.add(this.translatingPlugin);
		this.add(this.rotatingPlugin);
		this.add(this.shearingPlugin);
		this.add(this.popupEditingPlugin);
	}

	@Override
	protected void setEditingMode() {
		this.remove(this.pickingPlugin);
		this.remove(this.animatedPickingPlugin);
		this.remove(this.translatingPlugin);
		this.remove(this.rotatingPlugin);
		this.remove(this.shearingPlugin);
		this.remove(this.labelEditingPlugin);
		this.remove(this.annotatingPlugin);
		this.add(this.editingPlugin);
		this.add(this.popupEditingPlugin);
	}

	@Override
	protected void setAnnotatingMode() {
		this.remove(this.pickingPlugin);
		this.remove(this.animatedPickingPlugin);
		this.remove(this.translatingPlugin);
		this.remove(this.rotatingPlugin);
		this.remove(this.shearingPlugin);
		this.remove(this.labelEditingPlugin);
		this.remove(this.editingPlugin);
		this.remove(this.popupEditingPlugin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.ics.jung.visualization.control.EditingModalGraphMouse#setMode
	 * (edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode)
	 */
	@Override
	public void setMode(Mode mode) {
		if (this.mode != mode && mode != Mode.ANNOTATING) {
			this.fireItemStateChanged(new ItemEvent(this,
					ItemEvent.ITEM_STATE_CHANGED, this.mode,
					ItemEvent.DESELECTED));
			this.mode = mode;

			if (mode == Mode.TRANSFORMING) {
				this.setTransformingMode();
			} else if (mode == Mode.PICKING) {
				this.setPickingMode();
			} else if (mode == Mode.EDITING) {
				this.setEditingMode();
			}

			if (this.modeBox != null) {
				this.modeBox.setSelectedItem(mode);
			}

			this.fireItemStateChanged(new ItemEvent(this,
					ItemEvent.ITEM_STATE_CHANGED, mode, ItemEvent.SELECTED));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.ics.jung.visualization.control.EditingModalGraphMouse#getModeComboBox
	 * ()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public JComboBox<Mode> getModeComboBox() {
		if (this.modeBox == null) {
			this.modeBox = new JComboBox<>(getModes());
			this.modeBox.addItemListener(this.getModeListener());
		}
		this.modeBox.setSelectedItem(this.mode);

		return this.modeBox;
	}

	/**
	 * @return Mode[]
	 */
	public static Mode[] getModes() {
		return new Mode[] { Mode.PICKING, Mode.EDITING, Mode.TRANSFORMING };
	}

}
