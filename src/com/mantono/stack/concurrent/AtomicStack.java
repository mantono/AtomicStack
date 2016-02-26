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
	private final Semaphore permissionToModifyStack = new Semaphore(1);
	private T[] stackVector = (T[]) new Object[16];
	private int head = -1;

	@Override
	public boolean empty()
	{
		return head == -1;
	}

	@Override
	public T peek() throws EmptyStackException
	{
		if(empty())
			throw new EmptyStackException();
		return stackVector[head];
	}

	@Override
	public T pop() throws EmptyStackException
	{
		try
		{
			permissionToModifyStack.acquire();
			if(empty())
				throw new EmptyStackException();
			return stackVector[head--];
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			permissionToModifyStack.release();
		}
		return null;
	}

	@Override
	public T push(T item)
	{
		try
		{
			permissionToModifyStack.acquire();
			if(hasReachedCapacity())
				expand();
			return stackVector[++head] = item;
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			permissionToModifyStack.release();
		}
		return null;
	}

	private void expand()
	{
		final int newSize = stackVector.length*2;
		final T[] newArray = (T[]) new Object[newSize];
		for(int i = 0; i < head; i++)
			newArray[i] = stackVector[i];
		stackVector = newArray;
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
		//TODO Implement me!
		return -1;
	}
	
	@Override
	public String toString()
	{
		final StringBuilder output = new StringBuilder();
		output.append("[");
		for(T element : stackVector)
			output.append(element.toString() + ", "); //NullPointerException - How? Why?
		output.delete(output.length()-2, output.length()-1);
		output.append("]");
		return output.toString();
	}
}
