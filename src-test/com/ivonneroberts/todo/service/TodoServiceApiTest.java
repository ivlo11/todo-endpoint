package com.ivonneroberts.todo.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.api.server.spi.response.NotFoundException;
import com.ivonneroberts.todo.entity.Todo;

public class TodoServiceApiTest {

	@Test
	public void testTodoService()
	{
		TodoServiceApi apiTodoService = new TodoServiceApi();
		assertTrue(apiTodoService != null);
		
		Todo todo = apiTodoService.add("My First Task");
		List<Todo> allTodos = apiTodoService.getAllTodos();
		
		assertNotNull(allTodos);
		assertSame(todo, allTodos.get(0));
		
		try {
			apiTodoService.setTodoCompleted(0);
			assertTrue(todo.getCompleted());
		} catch (NotFoundException e) {
			assertTrue("Todo should exist and didn't", false);
		}
		
		Todo todo2 = apiTodoService.add("My Second Task");
		allTodos = apiTodoService.getAllTodos();
		assertEquals(2, allTodos.size());
		assertSame(todo2, allTodos.get(1));

		try {
			apiTodoService.deleteTodo(0);
		} catch (NotFoundException e) {
			assertTrue("Todo should exist and didn't", false);
		}
		allTodos = apiTodoService.getAllTodos();
		assertSame(todo2, allTodos.get(0));
		
		/*
		 * handle sorting alpha desc ascd, custom sequencing
		 */

	}
}
