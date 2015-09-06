package com.ivonneroberts.todo.service;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.ivonneroberts.todo.entity.Todo;

@Api(name="todo", version="v1", description="An api to manage basic todo")
public class TodoServiceApi {
	List<Todo> lstTodos = new ArrayList<Todo>();

	public Todo add(@Named("message") String todoMessage) {
		Todo todo = new Todo(todoMessage);

		lstTodos.add(todo);
		return todo;
	}

	@ApiMethod(path = "todos")
	public List<Todo> getAllTodos() {
		return lstTodos;
	}

	@ApiMethod(path = "complete")
	public Todo setTodoCompleted(@Named("id") int id) throws NotFoundException {
		for(Todo todo : lstTodos)
		{
			if (todo.getId() == id)
			{
				todo.setCompleted(true);
				return todo;
			}
		}
		throw new NotFoundException("Todo does not exist");
	}

	@ApiMethod(path = "delete")
	public void deleteTodo(@Named("id") int id) throws NotFoundException {
		for(Todo todo : lstTodos)
		{
			if (todo.getId() == id)
			{
				lstTodos.remove(todo);
				return;
			}
		}
		throw new NotFoundException("Todo does not exist");
	}

}
