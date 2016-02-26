package com.mantono.stack.concurrent;

import static org.junit.Assert.*;

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
	public void testEmptyAfterRemoval()
	{
		final Stack<Integer> stack = new AtomicStack<Integer>();
		stack.push(1);
		stack.pop();
		assertTrue(stack.empty());
	}

	@Test
	public void testPeek()
	{
		final Stack<Integer> stack = new AtomicStack<Integer>();
		stack.push(1);
		assertEquals(new Integer(1), stack.peek());
		assertFalse(stack.empty());
	}

	@Test
	public void testPop()
	{
		final Stack<Integer> stack = new AtomicStack<Integer>();
		stack.push(1);
		assertEquals(new Integer(1), stack.pop());
		assertTrue(stack.empty());
	}

	@Test
	public void testPush()
	{
		final Stack<Integer> stack = new AtomicStack<Integer>();
		assertEquals(new Integer(1), stack.push(1));
		assertFalse(stack.empty());
		assertEquals(new Integer(1), stack.peek());
	}

	@Test
	public void testHashCode()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testEquals()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testClone()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testToString()
	{
		fail("Not yet implemented");
	}
}
