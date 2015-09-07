package com.ivonneroberts.todo.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.ivonneroberts.todo.entity.Todo;

public class TodoEndpointTest {

	private static final LocalDatastoreServiceTestConfig LOCAL_DATASTORE_SERVICE_TEST_CONFIG = new LocalDatastoreServiceTestConfig()
			.setAutoIdAllocationPolicy(LocalDatastoreService.AutoIdAllocationPolicy.SEQUENTIAL);
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			LOCAL_DATASTORE_SERVICE_TEST_CONFIG);
    Closeable session;
    
	private final User user = new User("example@example.com", "gmail.com");


    @Before
    public void setUp() {
		helper.setUp();
        session = ObjectifyService.begin();
    }

    @After
    public void tearDown() {
        session.close();
		helper.tearDown();
    }

	@Test
	public void testTodoService()
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		assertTrue(apiTodoService != null);
	}
	
	@Test
	public void testAddTodo() throws OAuthRequestException
	{		
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todo = apiTodoService.create("My First Task", user);
		List<Todo> allTodos = apiTodoService.getTodos(user);
		
		assertNotNull(allTodos);
		Todo todoResult = allTodos.get(0);
		assertEquals(todo.getId(), todoResult.getId());
	}
	
	@Test
	public void testCompleteTodo() throws OAuthRequestException, NotFoundException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todo = apiTodoService.create("My First Task", user);

		apiTodoService.update(todo.getId(), user);
		assertTrue(todo.getCompleted());
	}
	
	@Test
	public void testGetAllTodos() throws OAuthRequestException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		apiTodoService.create("My First Task", user);
		apiTodoService.create("My Second Task", user);
		
		List<Todo> allTodos = apiTodoService.getTodos(user);
		assertEquals(2, allTodos.size());
	}
	
	@Test
	public void testDeleteTodo() throws NotFoundException, OAuthRequestException, IOException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoFirst = apiTodoService.create("My First Task", user);
		Todo todoSecond = apiTodoService.create("My Second Task", user);
		apiTodoService.delete(todoFirst.getId(), user);
		
		List<Todo> allTodos = apiTodoService.getTodos(user);
		assertEquals(1, allTodos.size());
		Todo todoResult = allTodos.get(0);
		assertEquals(todoSecond.getId(), todoResult.getId());
	}
	
	
	/*
	 * handle sorting alpha desc ascd, custom sequencing
	 */

}
