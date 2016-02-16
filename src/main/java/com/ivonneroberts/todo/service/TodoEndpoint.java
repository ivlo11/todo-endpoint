package com.ivonneroberts.todo.service;

import static com.ivonneroberts.todo.OfyService.ofy;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
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

	//TODO move message to body since it is a post - avoid url encoding...
	@ApiMethod(path = "todo",
			httpMethod = "POST")
	public Todo create(Todo todoInput, User user) throws OAuthRequestException, BadRequestException {
		if (user == null) {
			throw new OAuthRequestException("Must be logged in to add a todo");
		}
		
		if(todoInput.getMessage() == null || todoInput.getMessage().equals("") || todoInput.getMessage().length() > 120)
		{
			throw new BadRequestException("The todo message must be defined and be between 1 and 120 characters");
		}
		
		Todo todo = new Todo(todoInput.getMessage(), user.getUserId());

		Long lSequence = todoInput.getSequence();
		if (lSequence != null) {
			todo.setSequence(lSequence);
		}
		
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
		          .filter("userId ==", user.getUserId())
		          .order("-__key__")
		          .list();
	}

	@ApiMethod(path = "todo/{id}",
			httpMethod = "PUT")
	public Todo update(@Named("id") Long id, Todo todoInput, User user) throws NotFoundException, OAuthRequestException {
		if (user == null) {
			throw new OAuthRequestException("Must be logged in to complete a todo");
		}

		Todo todo = _getTodoEntity(id, user.getUserId());
		String strMessage = todoInput.getMessage();
		if (strMessage != null) {
			todo.setMessage(strMessage);
		}

		Boolean bCompleted = todoInput.getCompleted();
		if (bCompleted != null) {
			todo.setCompleted(bCompleted);
		}
		
		Long lSequence = todoInput.getSequence();
		if (lSequence != null) {
			todo.setSequence(lSequence);
		}
		
		ofy().save().entity(todo).now();
		log.info("Completed todo: " + todo.getId());
		return todo;
	}

	@ApiMethod(path = "todo/{id}")
	public void delete(@Named("id") Long id, User user) throws OAuthRequestException, IOException, NotFoundException{
		if (user == null) {
			throw new OAuthRequestException("Must be logged in to delete a todo");
		}
		
		Todo todo = _getTodoEntity(id, user.getUserId());
		ofy().delete().entity(todo).now();
	}
	
	private Todo _getTodoEntity(Long id, String userId) throws NotFoundException {
		Todo todo = ofy().load().type(Todo.class).id(id).now();		
		if (todo == null || todo.getUserId().equals(userId) == false)
		{
			log.severe("Todo " + id + " does not exist!");
			throw new NotFoundException("Todo does not exist");
		}
		return todo;
	}

}
