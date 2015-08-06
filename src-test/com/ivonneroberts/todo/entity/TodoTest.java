package com.ivonneroberts.todo.entity;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TodoTest {
	
	@Test
	public void testTodoService()
	{
		Todo todo = new Todo();
		assertTrue(todo != null);
	}

}
