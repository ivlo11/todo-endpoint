package com.ivonneroberts.todo.service;

import java.util.List;
import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;

import static com.ivonneroberts.todo.OfyService.ofy;

import com.ivonneroberts.todo.entity.Todo;

@Api(name="todo", version="v1", description="An api to manage basic todo")
public class TodoServiceApi {

	private static final Logger log = Logger.getLogger(TodoServiceApi.class.getName());

	public Todo add(@Named("message") String todoMessage) {
		Todo todo = new Todo(todoMessage);
		ofy().save().entity(todo).now();
		log.info("Saved todo: " + todo.getId());
		return todo;
	}

	@ApiMethod(path = "todos")
	public List<Todo> getAllTodos() {
		log.info("Getting all todos... ");
		return ofy()
		          .load()
		          .type(Todo.class)
		          .order("-__key__")
		          .list();
	}

	@ApiMethod(path = "complete/{id}")
	public Todo setTodoCompleted(@Named("id") Long id) throws NotFoundException {
		Todo todo = _getTodoEntity(id);
		todo.setCompleted(Boolean.TRUE);
		ofy().save().entity(todo).now();
		log.info("Completed todo: " + todo.getId());
		return todo;
	}

	@ApiMethod(path = "delete/{id}")
	public void deleteTodo(@Named("id") Long id) throws NotFoundException{
		Todo todo = _getTodoEntity(id);
		ofy().delete().entity(todo).now();
	}
	
	private Todo _getTodoEntity(Long id) throws NotFoundException {
		Todo todo = ofy().load().type(Todo.class).id(id).now();
		if (todo == null)
		{
			log.severe("Todo " + id + " does not exist!");
			throw new NotFoundException("Todo does not exist");
		}
		return todo;
	}

}
