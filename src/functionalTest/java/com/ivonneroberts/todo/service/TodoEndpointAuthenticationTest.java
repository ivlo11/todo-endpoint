package com.ivonneroberts.todo.service;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.api.server.spi.response.BadRequestException;
import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.ivonneroberts.todo.entity.Todo;

public class TodoEndpointAuthenticationTest {
	
	private static final LocalDatastoreServiceTestConfig LOCAL_DATASTORE_SERVICE_TEST_CONFIG = new LocalDatastoreServiceTestConfig()
			.setAutoIdAllocationPolicy(LocalDatastoreService.AutoIdAllocationPolicy.SEQUENTIAL);
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			LOCAL_DATASTORE_SERVICE_TEST_CONFIG);
	Closeable session;

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
	public void testDeleteWithoutAuth() throws OAuthRequestException, BadRequestException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoInput = new Todo();
		todoInput.setMessage("My First Task");
		Todo todo = apiTodoService.create(todoInput, user1);

		try {
			apiTodoService.delete(todo.getId(), null);
		} catch (OAuthRequestException e) {
			assertTrue("Only authenticated users can delete todos", true);
		} catch (Exception e) {
			assertTrue("No other exception should not occur", false);
		}
	}

	@Test
	public void testAddWithoutAuth() throws OAuthRequestException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();

		try {
			Todo todoInput = new Todo();
			todoInput.setMessage("My First Task");
			apiTodoService.create(todoInput, null);
		} catch (OAuthRequestException e) {
			assertTrue("Only authenticated users can add todos", true);
		} catch (Exception e) {
			assertTrue("No other exception should not occur", false);
		}
	}
	
	@Test
	public void testCompleteWithoutAuth() throws OAuthRequestException, BadRequestException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoInput = new Todo();
		todoInput.setMessage("My First Task");
		Todo todo = apiTodoService.create(todoInput, user1);

		try {
			apiTodoService.update(todo.getId(), todo, null);
		} catch (OAuthRequestException e) {
			assertTrue("Only authenticated users can add todos", true);
		} catch (Exception e) {
			assertTrue("No other exception should not occur", false);
		}
	}
	
	@Test
	public void testReadWithoutAuth() throws OAuthRequestException, BadRequestException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoInput = new Todo();
		todoInput.setMessage("My First Task");
		apiTodoService.create(todoInput, user1);

		try {
			apiTodoService.getTodos(null);
		} catch (OAuthRequestException e) {
			assertTrue("Only authenticated users can add todos", true);
		} catch (Exception e) {
			assertTrue("No other exception should not occur", false);
		}
	}
	
	@Test
	public void testReadUserTasks() throws OAuthRequestException, BadRequestException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoInput = new Todo();
		todoInput.setMessage("My First Task");
		apiTodoService.create(todoInput, user1);
		try {
			apiTodoService.getTodos(user2);
		} catch (NotFoundException e) {
			assertTrue("Users can only read their todos", true);
		} catch (Exception e) {
			assertTrue("No other exception should not occur", false);
		}
	}
	
	@Test
	public void testWriteToUserTasks() throws OAuthRequestException, BadRequestException
	{
		TodoEndpoint apiTodoService = new TodoEndpoint();
		Todo todoInput = new Todo();
		todoInput.setMessage("My First Task");
		Todo todo = apiTodoService.create(todoInput, user1);
		
		try {
			apiTodoService.update(todo.getId(), todo, user2);
		} catch (com.google.api.server.spi.response.NotFoundException e) {
			assertTrue("Users can only write to their todos", true);
		} catch (Exception e) {
			assertTrue("No other exception should not occur", false);
		}
	}

}
