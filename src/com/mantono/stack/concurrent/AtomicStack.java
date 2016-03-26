package com.mantono.stack.concurrent;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.concurrent.Semaphore;

import com.mantono.stack.Stack;

/**
 * This class is a thread safe implementation of the {@link Stack} data
 * structure.
 * 
 * @author Anton Österberg
 * 
 * @param <T>
 */
public class AtomicStack<T> implements Stack<T>
{
	private static final long serialVersionUID = -8663234127316351574L;
	private static final int DEFAULT_CAPACITY = 16;
	private final Semaphore permissionToModifyStack = new Semaphore(1);
	@SuppressWarnings("unchecked")
	private T[] stackVector = (T[]) new Object[DEFAULT_CAPACITY];
	private int head = -1;

	@Override
	public boolean empty()
	{
		return head == -1;
	}

	@Override
	public T peek()
	{
		if(empty())
			throw new EmptyStackException();
		return stackVector[head];
	}

	@Override
	public T pop() throws InterruptedException
	{
		try
		{
			permissionToModifyStack.acquire();
			if(empty())
				throw new EmptyStackException();
			final T element = stackVector[head];
			stackVector[head++] = null;
			return element;
		}
		finally
		{
			permissionToModifyStack.release();
		}
	}

	@Override
	public T push(T item) throws InterruptedException
	{
		try
		{
			permissionToModifyStack.acquire();
			if(hasReachedCapacity())
				expand();
			return stackVector[++head] = item;
		}
		finally
		{
			permissionToModifyStack.release();
		}
	}

	private void expand()
	{
		stackVector = Arrays.copyOf(stackVector, stackVector.length*2);
	}

	private boolean hasReachedCapacity()
	{
		return stackVector.length == head + 1;
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(object == null)
			return false;
		if(!(object instanceof AtomicStack<?>))
			return false;
		
		final AtomicStack<?> otherStack = (AtomicStack<?>) object;
		
		return Arrays.deepEquals(this.stackVector, otherStack.stackVector);
	}
	
	@Override
	public int hashCode()
	{
		int hashCode = 1;
		for(int i = 0; i <= head; i++)
		{
			final T element = stackVector[i];
			if(element != null)
			{
				hashCode *= 17;
				hashCode += element.hashCode();
			}
		}
		return hashCode;
	}

	
	@Override
	public String toString()
	{
		final StringBuilder output = new StringBuilder();
		output.append("[");
		for(int i = head; i >= 0; i--)
		{
			final T element = stackVector[i];
			if(element != null)
				output.append(element.toString() + ", ");
		}
		output.delete(output.length()-2, output.length());
		output.append("]");
		return output.toString();
	}
}
