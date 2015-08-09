package com.ivonneroberts.todo.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

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
		
		apiTodoService.setTodoCompleted(0);
		assertTrue(todo.getCompleted());
		
		Todo todo2 = apiTodoService.add("My Second Task");
		allTodos = apiTodoService.getAllTodos();
		assertEquals(2, allTodos.size());
		assertSame(todo2, allTodos.get(1));

		apiTodoService.deleteTodo(0);
		allTodos = apiTodoService.getAllTodos();
		assertSame(todo2, allTodos.get(0));
		
		/*
		 * handle sorting alpha desc ascd, custom sequencing
		 */

	}
}
