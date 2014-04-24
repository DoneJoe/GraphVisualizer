package ch.bfh.ti.gravis.gui.model;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class MainWindowModel {

	private final String filePath;
	
	private final boolean graphFile, unsaved;

	/**
	 * 
	 * @param filePath
	 * @param graphFile
	 * @param unsaved
	 */
	public MainWindowModel(String filePath, boolean graphFile, boolean unsaved) {
		this.filePath = filePath;
		this.graphFile = graphFile;
		this.unsaved = unsaved;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * @return the graphFile
	 */
	public boolean hasGraphFile() {
		return this.graphFile;
	}

	/**
	 * @return is unsaved
	 */
	public boolean isUnsaved() {
		return this.unsaved;
	}
	
	
}
