package com.ivonneroberts.todo.service;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.ivonneroberts.todo.entity.Todo;

public class TodoServiceApiAuthenticationTest {
	
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
		TodoServiceApi apiTodoService = new TodoServiceApi();
		assertTrue(apiTodoService != null);
	}
	
	@Test
	public void testDeleteWithoutAuth() throws OAuthRequestException
	{
		TodoServiceApi apiTodoService = new TodoServiceApi();
		Todo todo = apiTodoService.create("My First Task", user);

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
		TodoServiceApi apiTodoService = new TodoServiceApi();

		try {
			apiTodoService.create("My First Task", null);
		} catch (OAuthRequestException e) {
			assertTrue("Only authenticated users can add todos", true);
		} catch (Exception e) {
			assertTrue("No other exception should not occur", false);
		}
	}
	
	@Test
	public void testCompleteWithoutAuth() throws OAuthRequestException
	{
		TodoServiceApi apiTodoService = new TodoServiceApi();
		Todo todo = apiTodoService.create("My First Task", user);

		try {
			apiTodoService.update(todo.getId(), null);
		} catch (OAuthRequestException e) {
			assertTrue("Only authenticated users can add todos", true);
		} catch (Exception e) {
			assertTrue("No other exception should not occur", false);
		}
	}
	
	@Test
	public void testReadWithoutAuth() throws OAuthRequestException
	{
		TodoServiceApi apiTodoService = new TodoServiceApi();
		apiTodoService.create("My First Task", user);

		try {
			apiTodoService.getTodos(null);
		} catch (OAuthRequestException e) {
			assertTrue("Only authenticated users can add todos", true);
		} catch (Exception e) {
			assertTrue("No other exception should not occur", false);
		}
	}
}
