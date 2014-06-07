package ch.bfh.ti.gravis.core.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A bidirectinal immutable iterator. An iterator for lists that allows the
 * programmer to traverse the list in either direction and obtain the iterator's
 * current position in the list.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGravisListIterator<E> extends Iterable<E>, Iterator<E> {

	/**
	 * Moves the iterator to the first element in the iterator and returns this
	 * element.
	 * 
	 * @return the first element in the iterator or <tt>null</tt> if this iterator is
	 *         empty
	 */
	public abstract E first();

	/**
     * Returns {@code true} if this list iterator has more elements when
     * traversing the list in the reverse direction.  (In other words,
     * returns {@code true} if {@link #previous} would return an element
     * rather than throwing an exception.)
     *
     * @return {@code true} if the list iterator has more elements when
     *         traversing the list in the reverse direction
     */
	public abstract boolean hasPrevious();

	/**
	 * Returns <tt>true</tt> if this iterator contains no elements.
	 * 
	 * @return <tt>true</tt> if this iterator contains no elements
	 */
	public abstract boolean isEmpty();

	/**
	 * Moves the iterator to the last element in the iterator and returns this
	 * element.
	 * 
	 * @return the last element in the iterator or <tt>null</tt> if this iterator is
	 *         empty
	 */
	public abstract E last();

	/**
     * Returns the index of the element that would be returned by a
     * subsequent call to {@link #next}. (Returns list size if the list
     * iterator is at the end of the list.)
     *
     * @return the index of the element that would be returned by a
     *         subsequent call to {@code next}, or list size if the list
     *         iterator is at the end of the list
     */
	public abstract int nextIndex();

	/**
     * Returns the previous element in the list and moves the cursor
     * position backwards.  This method may be called repeatedly to
     * iterate through the list backwards, or intermixed with calls to
     * {@link #next} to go back and forth.  (Note that alternating calls
     * to {@code next} and {@code previous} will return the same
     * element repeatedly.)
     *
     * @return the previous element in the list
     * @throws NoSuchElementException if the iteration has no previous
     *         element
     */
	public abstract E previous();

	/**
     * Returns the index of the element that would be returned by a
     * subsequent call to {@link #previous}. (Returns -1 if the list
     * iterator is at the beginning of the list.)
     *
     * @return the index of the element that would be returned by a
     *         subsequent call to {@code previous}, or -1 if the list
     *         iterator is at the beginning of the list
     */
	public abstract int previousIndex();

	/**
	 * Returns the number of elements in this iterator.
	 * 
	 * @return number of elements
	 */
	public abstract int size();
}
