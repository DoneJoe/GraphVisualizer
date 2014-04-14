package ch.bfh.bti7301.hs2013.gravis.core.algorithm;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public final class AlgorithmFactory {

	private AlgorithmFactory() {
	}

	/**
	 * @return IAlgorithmManager
	 * @throws AlgorithmException 
	 */
	public static IAlgorithmManager createAlgorithmManager() throws AlgorithmException {
		return new AlgorithmManager();
	}
	
}
