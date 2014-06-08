package ch.bfh.ti.gravis.core.util;

import java.util.Iterator;
import java.util.Objects;

import ch.bfh.ti.gravis.core.step.IStep;

/**
 * This implementation of a bidirectinal immutable iterator iterates over a
 * collection of step comments (String) and executes (or unexecutes) the steps.
 * It decorates a given {@link IGravisListIterator}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class StepIterator implements IGravisListIterator<String> {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "StepIterator.%s(): %s == %s";

	private static final String EXCEPTION_MSG = "remove: unsupported operation!";

	private IGravisListIterator<IStep> iterator;

	private IStep currentCommand;

	/**
	 * 
	 * @param listIterator
	 * 
	 */
	public StepIterator(IGravisListIterator<IStep> listIterator) {
		this.iterator = Objects
				.requireNonNull(listIterator, String.format(NULL_POINTER_MSG,
						"StepIterator", "listIterator", listIterator));
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
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.iterator.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#previous()
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
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#hasPrevious()
	 */
	@Override
	public boolean hasPrevious() {
		return this.iterator.hasPrevious();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#size()
	 */
	@Override
	public int size() {
		return this.iterator.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#nextIndex()
	 */
	@Override
	public int nextIndex() {
		return this.iterator.nextIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#previousIndex()
	 */
	@Override
	public int previousIndex() {
		return this.iterator.previousIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#first()
	 */
	@Override
	public String first() {
		StringBuilder stepString = new StringBuilder();

		while (this.iterator.hasPrevious()) {
			stepString.insert(0, this.previous());
		}

		return stepString.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#last()
	 */
	@Override
	public String last() {
		StringBuilder stepString = new StringBuilder();

		while (this.iterator.hasNext()) {
			stepString.append(this.next());
		}

		return stepString.toString();
	}

}
