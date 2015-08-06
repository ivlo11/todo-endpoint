package com.ivonneroberts.todo.entity;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TodoTest {
	
	@Test
	public void testTodoService()
	{
		Todo todo = new Todo();
		todo.setId(1);
		asserEquals(1, todo.getId());
	}

}
