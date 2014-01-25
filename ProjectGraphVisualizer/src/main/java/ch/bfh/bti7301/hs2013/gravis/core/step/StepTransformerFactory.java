package ch.bfh.bti7301.hs2013.gravis.core.step;

import org.apache.commons.collections15.Transformer;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public final class StepTransformerFactory {

	private StepTransformerFactory() {
	}

	/**
	 * 
	 * @return an instance of Transformer<IGraphItem, IStep>
	 */
	public static Transformer<IGraphItem, IStep> createStepTransformer() {
		return new StepTransformer();
	}

}
