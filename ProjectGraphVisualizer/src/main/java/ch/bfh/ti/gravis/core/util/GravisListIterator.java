package ch.bfh.ti.gravis.core.util;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * A generic implementation of a bidirectinal immutable iterator. The elements
 * are backed in a given {@code List}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GravisListIterator<E> implements IGravisListIterator<E> {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "GravisListIterator<E>.%s(): %s == %s";

	private static final String EXCEPTION_MSG = "remove(): unsupported operation!";

	private final ListIterator<E> listIterator;

	private int size;

	/**
	 * Creates an iterator iterating over all list elements.The elements are
	 * backed in the given {@code List}.
	 * 
	 * @param list
	 */
	public GravisListIterator(List<E> list) {
		Objects.requireNonNull(list, String.format(NULL_POINTER_MSG,
				"GravisListIterator", "list", list));
		this.listIterator = list.listIterator();
		this.size = list.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.traversing.ITraverserCollection #isEmpty
	 * ()
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.listIterator.hasNext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public E next() {
		return this.listIterator.next();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		// this iterator is immutable
		throw new UnsupportedOperationException(EXCEPTION_MSG);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.traversing.ITraverserCollection #before
	 * ()
	 */
	@Override
	public E previous() {
		return this.listIterator.previous();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.traversing.ITraverserCollection
	 * #hasBefore ()
	 */
	@Override
	public boolean hasPrevious() {
		return this.listIterator.hasPrevious();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.traversing.ITraverserCollection #size()
	 */
	@Override
	public int size() {
		return this.size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#nextIndex()
	 */
	@Override
	public int nextIndex() {
		return this.listIterator.nextIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#previousIndex()
	 */
	@Override
	public int previousIndex() {
		return this.listIterator.previousIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#first()
	 */
	@Override
	public E first() {
		E element = null;

		while (this.listIterator.hasPrevious()) {
			element = this.listIterator.previous();
		}

		return element;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.IGravisListIterator#last()
	 */
	@Override
	public E last() {
		E element = null;

		while (this.listIterator.hasNext()) {
			element = this.listIterator.next();
		}

		return element;
	}

}
