package com.ivonneroberts.todo.service;

import static com.ivonneroberts.todo.OfyService.ofy;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.ivonneroberts.todo.Constants;
import com.ivonneroberts.todo.entity.Todo;

@Api(name="todo", 
	version="v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID},
	description="An api to manage basic todo")
public class TodoEndpoint {

	private static final Logger log = Logger.getLogger(TodoEndpoint.class.getName());

	@ApiMethod(path = "todo/{message}",
			httpMethod = "POST")
	public Todo create(@Named("message") String todoMessage, User user) throws OAuthRequestException {
		if (user == null) {
			throw new OAuthRequestException("Must be logged in to add a todo");
		}
		
		Todo todo = new Todo(todoMessage);
		ofy().save().entity(todo).now();
		log.info("Saved todo: " + todo.getId());
		return todo;
	}

	@ApiMethod(path = "todos")
	public List<Todo> getTodos(User user) throws OAuthRequestException {
		log.info("Getting all todos... ");
		if (user == null) {
			throw new OAuthRequestException("Must be logged in to see todos");
		}
		
		return ofy()
		          .load()
		          .type(Todo.class)
		          .order("-__key__")
		          .list();
	}

	@ApiMethod(path = "todo/{id}",
			httpMethod = "PUT")
	public Todo update(@Named("id") Long id, Todo todoInput, User user) throws NotFoundException, OAuthRequestException {
		if (user == null) {
			throw new OAuthRequestException("Must be logged in to complete a todo");
		}

		Todo todo = _getTodoEntity(id);
		String strMessage = todoInput.getMessage();
		if (strMessage != null) {
			todo.setMessage(strMessage);
		}
			
		todo.setCompleted(todoInput.getCompleted()); //TODO: convert to wrapper to allow null
		todo.setSequence(todoInput.getSequence()); //TODO: convert to wrapper to allow null
		
		ofy().save().entity(todo).now();
		log.info("Completed todo: " + todo.getId());
		return todo;
	}

	@ApiMethod(path = "todo/{id}")
	public void delete(@Named("id") Long id, User user) throws OAuthRequestException, IOException, NotFoundException{
		if (user == null) {
			throw new OAuthRequestException("Must be logged in to delete a todo");
		}
		
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
