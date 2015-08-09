package com.ivonneroberts.todo.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TodoTest {
	
	@Test
	public void testTodo()
	{
		Todo todo = new Todo(1, "My First Task");
		assertEquals(1, todo.getId());
		assertEquals("My First Task", todo.getMessage());
		assertEquals(false, todo.getCompleted());

		todo.setCompleted(true);
		assertEquals(true, todo.getCompleted());
	}

}
