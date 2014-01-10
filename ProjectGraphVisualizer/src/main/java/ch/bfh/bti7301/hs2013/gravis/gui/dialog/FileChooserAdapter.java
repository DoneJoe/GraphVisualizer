package ch.bfh.bti7301.hs2013.gravis.gui.dialog;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class FileChooserAdapter {

	private final static String FILTER_DESCRIPTION = "*.graphml";
	private final static String FILTER = "graphml";
	
	private final JFileChooser fileChooser;

	private final Component parent;

	/**
	 * 
	 * @param parent
	 */
	public FileChooserAdapter(Component parent) {
		this.parent = parent;
		this.fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				FILTER_DESCRIPTION, FILTER);
		
		this.fileChooser.setAcceptAllFileFilterUsed(false);
		this.fileChooser.addChoosableFileFilter(filter);
		this.fileChooser.setCurrentDirectory(new File(GravisConstants.TEMPLATES_DIR));
	}

	/**
	 * @return int
	 */
	public int showOpenDialog() {
		return this.fileChooser.showOpenDialog(this.parent);
	}

	/**
	 * @return int
	 */
	public int showSaveDialog() {
		return this.fileChooser.showSaveDialog(this.parent);
	}

	/**
	 * @return File
	 */
	public File getSelectedFile() {
		return this.fileChooser.getSelectedFile();
	}

}
