package com.mantono.stack.concurrent;

import java.util.EmptyStackException;
import java.util.concurrent.Semaphore;

import com.mantono.stack.Stack;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T pop() throws EmptyStackException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T push(T item)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
