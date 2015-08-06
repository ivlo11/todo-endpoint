package com.ivonneroberts.todo.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class TodoServiceApiTest {

	@Test
	public void testTodoService()
	{
		TodoServiceApi apiTodoService = new TodoServiceApi();
		assertTrue(apiTodoService != null);
	}
}
