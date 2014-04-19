package ch.bfh.bti7301.hs2013.gravis.core.util;

import java.util.Iterator;

import ch.bfh.bti7301.hs2013.gravis.core.step.IStep;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class StepIterator implements IGravisListIterator<String> {

	private static final String EXCEPTION_MSG = "remove: unsupported operation!";

	/**
	 * A field for a graph iterator.
	 */
	private IGravisListIterator<IStep> iterator;

	/**
	 * A field for a current command.
	 */
	private IStep currentCommand;

	/**
	 * Main constructor.
	 * 
	 * @param graphIterator
	 * 
	 */
	public StepIterator(IGravisListIterator<IStep> graphIterator) {
		this.iterator = graphIterator;
		this.currentCommand = null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<String> iterator() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public String next() {
		if (this.iterator.hasNext()) {
			this.currentCommand = this.iterator.next();
			return this.currentCommand.execute().getComment();
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException(EXCEPTION_MSG);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.IGravisListIterator#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.iterator.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.IGravisListIterator#previous()
	 */
	@Override
	public String previous() {
		if (this.iterator.hasPrevious()) {
			this.currentCommand = this.iterator.previous();
			return this.currentCommand.unExecute().getComment();
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.IGravisListIterator#hasPrevious()
	 */
	@Override
	public boolean hasPrevious() {
		return this.iterator.hasPrevious();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.IGravisListIterator#size()
	 */
	@Override
	public int size() {
		return this.iterator.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.IGravisListIterator#nextIndex()
	 */
	@Override
	public int nextIndex() {
		return this.iterator.nextIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.IGravisListIterator#previousIndex()
	 */
	@Override
	public int previousIndex() {
		return this.iterator.previousIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.IGravisListIterator#first()
	 */
	@Override
	public String first() {
		String stepString = "";

		while (this.iterator.hasPrevious()) {
			stepString = this.previous();
		}

		return stepString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.IGravisListIterator#last()
	 */
	@Override
	public String last() {
		String stepString = "";

		while (this.iterator.hasNext()) {
			stepString = this.next();
		}

		return stepString;
	}

}
