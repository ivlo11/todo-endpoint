package com.ivonneroberts.todo.service;

import java.util.ArrayList;
import java.util.List;

import com.ivonneroberts.todo.entity.Todo;

public class TodoServiceApi {
	List<Todo> lstTodos = new ArrayList<Todo>();

	public Todo add(String todoMessage) {
		Todo todo = new Todo(todoMessage);

		lstTodos.add(todo);
		return todo;
	}

	public List<Todo> getAllTodos() {
		return lstTodos;
	}

	public Todo setTodoCompleted(int i) {
		for(Todo todo : lstTodos)
		{
			if (todo.getId() == i)
			{
				todo.setCompleted(true);
				return todo;
			}
		}
		return null;
	}

	public void deleteTodo(int i) {
		for(Todo todo : lstTodos)
		{
			if (todo.getId() == i)
			{
				lstTodos.remove(todo);
			}
		}
	}

}
