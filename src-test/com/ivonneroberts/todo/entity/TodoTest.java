package com.ivonneroberts.todo.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TodoTest {
	
	@Test
	public void testTodoService()
	{
		Todo todo = new Todo();
		todo.setId(1);
		assertEquals(1, todo.getId());
		
		todo.setMessage("My First Task");
		assertEquals("My First Task", todo.getMessage());

	}

}
