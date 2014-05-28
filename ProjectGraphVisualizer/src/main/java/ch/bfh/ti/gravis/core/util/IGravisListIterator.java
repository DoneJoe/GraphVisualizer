package ch.bfh.ti.gravis.core.util;

import java.util.Iterator;

/**
 * This Interface defines a bidirectinal immutable iterator.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGravisListIterator<E> extends Iterable<E>, Iterator<E> {

	/**
	 * Returns true if this iterator contains no elements.
	 * 
	 * @return true if this iterator contains no elements
	 */
	public abstract boolean isEmpty();

	/**
	 * Returns the previous element in the list and moves the cursor position
	 * backwards. This method may be called repeatedly to iterate through the
	 * list backwards, or intermixed with calls to next() to go back and forth.
	 * (Note that alternating calls to next and previous will return the same
	 * element repeatedly.)
	 * 
	 * @return the previous element in the list
	 */
	public abstract E previous();

	/**
	 * Returns true if this list iterator has more elements when traversing the
	 * list in the reverse direction. (In other words, returns true if
	 * previous() would return an element rather than throwing an exception.)
	 * 
	 * @return true if the list iterator has more elements when traversing the
	 *         list in the reverse direction
	 */
	public abstract boolean hasPrevious();

	/**
	 * Returns the number of elements in this iterator.
	 * 
	 * @return number of elements
	 */
	public abstract int size();

	/**
	 * Returns the index of the element that would be returned by a subsequent
	 * call to next(). (Returns list size if the list iterator is at the end of
	 * the list.)
	 * 
	 * @return the index of the element that would be returned by a subsequent
	 *         call to next, or list size if the list iterator is at the end of
	 *         the list
	 */
	public abstract int nextIndex();

	/**
	 * Returns the index of the element that would be returned by a subsequent
	 * call to previous(). (Returns -1 if the list iterator is at the beginning
	 * of the list.)
	 * 
	 * @return the index of the element that would be returned by a subsequent
	 *         call to previous, or -1 if the list iterator is at the beginning
	 *         of the list
	 */
	public abstract int previousIndex();

	/**
	 * Moves the iterator to the first element in the iterator and returns this element.
	 * 
	 * @return the first element in the iterator or null if this iterator is empty
	 */
	public abstract E first();

	/**
	 * Moves the iterator to the last element in the iterator and returns this element.
	 * 
	 * @return the last element in the iterator or null if this iterator is empty
	 */
	public abstract E last();
}
