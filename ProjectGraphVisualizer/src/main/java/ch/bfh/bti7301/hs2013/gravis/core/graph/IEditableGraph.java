package ch.bfh.bti7301.hs2013.gravis.core.graph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IEditableGraph extends IGravisGraph {

	/**
	 * 
	 * @param listener
	 */
	public abstract void addEditableGraphEventListener(IEditableGraphEventListener listener);
	
	/**
	 * 
	 * @return IEditableGraphEventListener[]
	 */
	public abstract IEditableGraphEventListener[] getEditableGraphEventListeners();
}
