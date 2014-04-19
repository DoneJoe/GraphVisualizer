package ch.bfh.ti.gravis.gui.model;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IStepViewModel {

	/**
	 * 
	 * @return stepValue
	 */
	public abstract int getStepValue();
	
	/**
	 * 
	 * @return stepMaximum
	 */
	public abstract int getStepMaximum();
}
