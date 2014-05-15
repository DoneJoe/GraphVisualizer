package ch.bfh.ti.gravis.gui.visualization;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JComponent;

import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GravisVertexLabelRenderer extends DefaultVertexLabelRenderer {
	
	private static final long serialVersionUID = -8522153650659157678L;
	
	public GravisVertexLabelRenderer(Color unpickedVertexLabelColor,
			Color pickedVertexLabelColor) {
		
		super(pickedVertexLabelColor);
		this.setForeground(unpickedVertexLabelColor);
	}

	public <V> Component getVertexLabelRendererComponent(JComponent vv,
			Object value, Font font, boolean isSelected, V vertex) {
		
		if (isSelected)
			this.setForeground(this.pickedVertexLabelColor);
		this.setBackground(vv.getBackground());
		
		if (font != null) {
			this.setFont(font);
		} else {
			this.setFont(vv.getFont());
		}
		
		this.setIcon(null);
		this.setBorder(noFocusBorder);
		this.setValue(value);
		return this;
	}
}
