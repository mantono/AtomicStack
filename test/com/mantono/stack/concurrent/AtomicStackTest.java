package com.mantono.stack.concurrent;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

import com.mantono.stack.Stack;

public class AtomicStackTest
{

	@Test
	public void testEmptyOnCreation()
	{
		assertTrue(new AtomicStack<Integer>().empty());
	}

	@Test
	public void testEmptyAfterRemoval() throws InterruptedException
	{
		final Stack<Integer> stack = new AtomicStack<Integer>();
		stack.push(1);
		stack.pop();
		assertTrue(stack.empty());
	}

	@Test
	public void testPeek() throws InterruptedException
	{
		final Stack<Integer> stack = new AtomicStack<Integer>();
		stack.push(1);
		assertEquals(new Integer(1), stack.peek());
		assertFalse(stack.empty());
	}

	@Test(expected = EmptyStackException.class)
	public void testExceptionAtPeekOnEmptyStack()
	{
		new AtomicStack<Float>().peek();
	}

	@Test(expected = EmptyStackException.class)
	public void testExceptionAtPopOnEmptyStack() throws InterruptedException
	{
		new AtomicStack<Float>().pop();
	}

	@Test
	public void testPop() throws InterruptedException
	{
		final Stack<Integer> stack = new AtomicStack<Integer>();
		stack.push(1);
		assertEquals(new Integer(1), stack.pop());
		assertTrue(stack.empty());
	}

	@Test
	public void testPush() throws InterruptedException
	{
		final Stack<Integer> stack = new AtomicStack<Integer>();
		assertEquals(new Integer(1), stack.push(1));
		assertFalse(stack.empty());
		assertEquals(new Integer(1), stack.peek());
	}

	@Test
	public void testSeveralThreadsPushing() throws InterruptedException
	{
		final Stack<Integer> stack = new AtomicStack<Integer>();
		Runnable r1 = new Runnable()
		{
			@Override
			public void run()
			{
				for(int i = 0; i < 5000; i++)
					try
					{
						stack.push(0);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
			}
		};

		Runnable r2 = new Runnable()
		{
			@Override
			public void run()
			{
				for(int i = 0; i < 5000; i++)
					try
					{
						stack.push(1);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
			}
		};

		new Thread(r1).start();
		new Thread(r2).start();
		
		Thread.sleep(10);
		
		int zeroes, ones;
		zeroes = ones = 0;
		for(int i = 0; i < 10_000; i++)
		{
			int n = stack.pop();
			if(n == 0)
				zeroes++;
			else
				ones++;
		}

		assertEquals(5000, zeroes);
		assertEquals(5000, ones);
	}

	@Test
	public void testPushToTheLimit() throws InterruptedException
	{
		final Stack<Integer> stack = new AtomicStack<Integer>();
		for(int i = 0; i < 20; i++)
			stack.push(i);
		for(int i = 19; i >= 0; i--)
			assertEquals(new Integer(i), stack.pop());
	}

	@Test
	public void testHashCodeForEqualStacks() throws InterruptedException
	{
		final AtomicStack<Integer> stack1 = new AtomicStack<Integer>();
		final AtomicStack<Integer> stack2 = new AtomicStack<Integer>();

		stack1.push(1);
		stack2.push(1);

		stack1.push(2);
		stack2.push(2);

		stack1.push(3);
		stack2.push(3);

		assertEquals(stack1.hashCode(), stack2.hashCode());
	}

	@Test
	public void testHashCodeForDifferentStacks() throws InterruptedException
	{
		final AtomicStack<Integer> stack1 = new AtomicStack<Integer>();
		final AtomicStack<Integer> stack2 = new AtomicStack<Integer>();

		stack1.push(1);
		stack2.push(1);

		stack1.push(2);
		stack2.push(3);

		stack1.push(3);
		stack2.push(2);

		assertFalse(stack1.hashCode() == stack2.hashCode());
	}

	@Test
	public void testEqualsPositive() throws InterruptedException
	{
		final AtomicStack<Integer> stack1 = new AtomicStack<Integer>();
		final AtomicStack<Integer> stack2 = new AtomicStack<Integer>();

		stack1.push(1);
		stack2.push(1);

		stack1.push(2);
		stack2.push(2);

		stack1.push(3);
		stack2.push(3);

		assertTrue(stack1.equals(stack2));
	}

	@Test
	public void testEqualsNegative() throws InterruptedException
	{
		final AtomicStack<Integer> stack1 = new AtomicStack<Integer>();
		final AtomicStack<Integer> stack2 = new AtomicStack<Integer>();

		stack1.push(1);
		stack2.push(1);

		stack1.push(3);
		stack2.push(7);

		assertFalse(stack1.equals(stack2));
	}

	@Test
	public void testToString() throws InterruptedException
	{
		final AtomicStack<Integer> stack = new AtomicStack<Integer>();

		stack.push(1);
		stack.push(2);
		stack.push(3);

		assertEquals("[3, 2, 1]", stack.toString());
	}
}
