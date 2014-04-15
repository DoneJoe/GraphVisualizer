package ch.bfh.bti7301.hs2013.gravis.core;

import ch.bfh.bti7301.hs2013.gravis.core.algorithm.AlgorithmException;
import ch.bfh.bti7301.hs2013.gravis.core.algorithm.AlgorithmFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;

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
	 * @throws CoreException 
	 */
	public static ICore createCore() throws CoreException {
		try {
			return new Core(GraphFactory.createGraphManager(), new AlgorithmFactory());
		} catch (AlgorithmException e) {
			throw new CoreException(e);
		}
	}

}
