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
	
	@Test(expected=EmptyStackException.class)
	public void testExceptionAtPeekOnEmptyStack()
	{
		new AtomicStack<Float>().peek();
	}
	
	@Test(expected=EmptyStackException.class)
	public void testExceptionAtPopOnEmptyStack()
	{
		new AtomicStack<Float>().pop();
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
	public void testEqualsPositive()
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
	public void testEqualsNegative()
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
	public void testClone()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testToString()
	{
		final AtomicStack<Integer> stack = new AtomicStack<Integer>();
		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		
		assertEquals("[3, 2, 1]", stack.toString());
	}
}
