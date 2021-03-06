package com.ivonneroberts.todo.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.api.server.spi.response.BadRequestException;
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

	private static final String MY_FIRST_TASK_MESSAGE = "My First Task";
	private static final String MY_SECOND_TASK_MESSAGE = "My Second Task";

	private static final LocalDatastoreServiceTestConfig LOCAL_DATASTORE_SERVICE_TEST_CONFIG = new LocalDatastoreServiceTestConfig()
			.setAutoIdAllocationPolicy(LocalDatastoreService.AutoIdAllocationPolicy.SEQUENTIAL);
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			LOCAL_DATASTORE_SERVICE_TEST_CONFIG);
    
	private Closeable session;
    
	private final User user1 = new User("example@example.com", "gmail.com", "user1");
	private final User user2 = new User("example@example.com", "gmail.com", "user2");


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
	public void testAddTodo() throws OAuthRequestException, BadRequestException
	{		
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoInput = new Todo();
		todoInput.setMessage(MY_FIRST_TASK_MESSAGE);
		Todo todo = apiTodoService.create(todoInput, user1);
		apiTodoService.create(todoInput, user2);
		List<Todo> allTodos = apiTodoService.getTodos(user1);
		
		assertNotNull(allTodos);
		Todo todoResult = allTodos.get(0);
		assertEquals(todo.getId(), todoResult.getId());
	}
	
	@Test
	public void testTooLongMessage()
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoInput = new Todo();
		todoInput.setMessage("Really long string that is over the usual 120 characters that would make apps like twitter barf and so on... something... else...");
		
		try {
			apiTodoService.create(todoInput, user1);
		} catch (BadRequestException e) {
			assertTrue("The message string is too long", true);
		} catch (Exception e) {
			assertTrue("There should be no other exception", false);
		}
	}
	
	@Test
	public void testUpdateTodo() throws OAuthRequestException, NotFoundException, BadRequestException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoInput = new Todo();
		todoInput.setMessage(MY_FIRST_TASK_MESSAGE);
		Todo todo = apiTodoService.create(todoInput, user1);
		
		//
		// Test partial update
		//
		todoInput = new Todo();
		todoInput.setCompleted(Boolean.TRUE);
		
		Todo todoUpdate = apiTodoService.update(todo.getId(), todoInput, user1);
		
		assertTrue(todoUpdate.getCompleted());
		assertEquals(MY_FIRST_TASK_MESSAGE, todoUpdate.getMessage());
		assertEquals(todo.getSequence(), todoUpdate.getSequence());
		
		//
		// Test full update
		//
		String strRenamedMessage = "Renamed First Task";
		Long lSequence = new Long(5);

		todoInput.setMessage(strRenamedMessage);
		todoInput.setSequence(lSequence);
		todoInput.setCompleted(Boolean.FALSE);

		todoUpdate = apiTodoService.update(todo.getId(), todoInput, user1);

		assertFalse(todoUpdate.getCompleted());
		assertEquals(strRenamedMessage, todoUpdate.getMessage());
		assertEquals(lSequence, todoUpdate.getSequence());
		
	}
	
	@Test
	public void testGetAllTodos() throws OAuthRequestException, BadRequestException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoInput = new Todo();
		todoInput.setMessage(MY_FIRST_TASK_MESSAGE);
		apiTodoService.create(todoInput, user1);
		todoInput.setMessage(MY_SECOND_TASK_MESSAGE);
		apiTodoService.create(todoInput, user1);
		
		List<Todo> allTodos = apiTodoService.getTodos(user1);
		assertEquals(2, allTodos.size());
	}
	
	@Test
	public void testDeleteTodo() throws NotFoundException, OAuthRequestException, IOException, BadRequestException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoInput = new Todo();
		todoInput.setMessage(MY_FIRST_TASK_MESSAGE);
		Todo todoFirst = apiTodoService.create(todoInput, user1);
		todoInput.setMessage(MY_SECOND_TASK_MESSAGE);
		Todo todoSecond = apiTodoService.create(todoInput, user1);
		apiTodoService.delete(todoFirst.getId(), user1);
		
		List<Todo> allTodos = apiTodoService.getTodos(user1);
		assertEquals(1, allTodos.size());
		Todo todoResult = allTodos.get(0);
		assertEquals(todoSecond.getId(), todoResult.getId());
	}
	
	
	/*
	 * handle sorting alpha desc ascd, custom sequencing
	 */

}
