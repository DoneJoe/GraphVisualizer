package ch.bfh.bti7301.hs2013.gravis.core.graph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IEditingGraph extends IGravisGraph {

	/**
	 * 
	 * @param listener
	 */
	public abstract void setEditingGraphEventListener(IEditingGraphEventListener listener);
	
	/**
	 * 
	 * @return IEditingGraphEventListener
	 */
	public abstract IEditingGraphEventListener getEditingGraphEventListener();
}
