package ch.bfh.ti.gravis.core;

import ch.bfh.ti.gravis.core.algorithm.AlgorithmFactory;
import ch.bfh.ti.gravis.core.graph.GraphIOManager;

/**
 * This static factory class creates instances of type ICore.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public final class CoreFactory {

	/**
	 * A main (no-)constructor.
	 */
	private CoreFactory() {
	}

	/**
	 * Creates an instance of type ICore. ICore is the interface to all important
	 * core services.
	 * 
	 * @return an instance of type ICore
	 */
	public static ICore createCore() {
			return new Core(new GraphIOManager(), new AlgorithmFactory());
	}

}
