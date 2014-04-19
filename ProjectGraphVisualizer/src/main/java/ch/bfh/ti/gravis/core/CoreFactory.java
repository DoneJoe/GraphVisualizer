package ch.bfh.ti.gravis.core;

import ch.bfh.ti.gravis.core.algorithm.AlgorithmFactory;
import ch.bfh.ti.gravis.core.graph.GraphIOManager;

/**
 * This factory class creates and composes all necessary objects used in the
 * gravis core package. The construction of objects is centralized in this
 * class. All necessary dependencies are passed through the constructors and
 * methods to the objects (dependency injection). An object speaks only through
 * an interface to other dependant objects.
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
	 * core classes.
	 * 
	 * @return an instance of type ICore
	 */
	public static ICore createCore() {
			return new Core(new GraphIOManager(), new AlgorithmFactory());
	}

}
