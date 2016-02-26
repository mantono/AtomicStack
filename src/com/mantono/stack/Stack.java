package com.mantono.stack;

import java.io.Serializable;
import java.util.EmptyStackException;

/**
 * An interface for data structures implementing stack functionality, something
 * that is missing in the Java Utility libraries.
 * 
 * @author Anton Österberg
 * 
 * @param <T> the type of the elements that are handled by the stack.
 */
public interface Stack<T> extends Serializable
{
	/**
	 * Check is the is empty.
	 * 
	 * @return true if the stack does not contain any elements, else false.
	 */
	boolean empty();

	/**
	 * Looks at the element at the top of the stack, but does not remove it.
	 * 
	 * @return the top most element on the stack.
	 * @throws EmptyStackException
	 *             if the stack does not contains any elements.
	 */
	T peek() throws EmptyStackException;

	/**
	 * Returns and removes the element at the top of the stack.
	 * 
	 * @return the top most element on the stack.
	 * @throws EmptyStackException
	 *             if the stack does not contains any elements.
	 */
	T pop() throws EmptyStackException;

	/**
	 * Pushes an element onto the top of the stack.
	 * 
	 * @param item
	 *            the element that will be added to the stack.
	 * @return the same element (<code>item</code>).
	 */
	T push(T item);
}
