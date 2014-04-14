package ch.bfh.bti7301.hs2013.gravis.core.graph;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IEditGraphObservable extends IGravisGraph {

	/**
	 * 
	 * @param listener
	 */
	public abstract void addEditGraphEventListener(IEditGraphEventListener listener);
	
	/**
	 * 
	 * @return IEditGraphEventListener[]
	 */
	public abstract IEditGraphEventListener[] getEditGraphEventListeners();
}
