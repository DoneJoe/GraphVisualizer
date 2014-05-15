package ch.bfh.ti.gravis.gui.visualization;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JComponent;

import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GravisEdgeLabelRenderer extends DefaultEdgeLabelRenderer {

	private static final long serialVersionUID = 6570151314248341235L;

	public GravisEdgeLabelRenderer(Color unpickedEdgeLabelColor,
			Color pickedEdgeLabelColor) {
		
		super(pickedEdgeLabelColor);
		this.setForeground(unpickedEdgeLabelColor);
	}

	@Override
	public <E> Component getEdgeLabelRendererComponent(JComponent vv,
			Object value, Font font, boolean isSelected, E edge) {
		
		if (isSelected)
			this.setForeground(this.pickedEdgeLabelColor);
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
