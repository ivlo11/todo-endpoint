package com.ivonneroberts.todo.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TodoTest {
	
	@Test
	public void testTodo()
	{
		Todo todo = new Todo("My First Task");
		Long lZero = new Long(0);
		todo.setSequence(lZero);
		assertEquals(null, todo.getId());
		assertEquals(lZero, todo.getSequence());
		assertEquals("My First Task", todo.getMessage());
		assertEquals(false, todo.getCompleted());

		todo.setCompleted(true);
		assertEquals(true, todo.getCompleted());
	}

}
