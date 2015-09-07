package com.ivonneroberts.todo.service;

import static org.junit.Assert.*;

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

public class TodoServiceApiTest {

	private static final LocalDatastoreServiceTestConfig LOCAL_DATASTORE_SERVICE_TEST_CONFIG = new LocalDatastoreServiceTestConfig()
			.setAutoIdAllocationPolicy(LocalDatastoreService.AutoIdAllocationPolicy.SEQUENTIAL);
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			LOCAL_DATASTORE_SERVICE_TEST_CONFIG);

    Closeable session;

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

    //TODO: need to clean up into separate test cases/classes
	@Test
	public void testTodoService()
	{
		TodoServiceApi apiTodoService = new TodoServiceApi();
		assertTrue(apiTodoService != null);
		
		Todo todo = apiTodoService.add("My First Task");
		List<Todo> allTodos = apiTodoService.getAllTodos();
		
		assertNotNull(allTodos);
		Todo todoResult = allTodos.get(0);
		assertEquals(todo.getId(), todoResult.getId());
		
		try {
			apiTodoService.setTodoCompleted(todoResult.getId());
			assertTrue(todo.getCompleted());
		} catch (NotFoundException e) {
			assertTrue("Todo should exist and didn't", false);
		}
		
		Todo todo2 = apiTodoService.add("My Second Task");
		allTodos = apiTodoService.getAllTodos();
		assertEquals(2, allTodos.size());

		try {
			apiTodoService.deleteTodo(todoResult.getId(), null);
		} catch (OAuthRequestException e) {
			assertTrue("Only authenticated users can delete todos", true);
		} catch (Exception e) {
			assertTrue("No other exception should not occur", false);
		}
		
		try {
			apiTodoService.deleteTodo(todoResult.getId(), new User("example@example.com", "gmail.com"));
		} catch (Exception e) {
			assertTrue("No exception should not occur", false);
		}
		allTodos = apiTodoService.getAllTodos();
		assertEquals(1, allTodos.size());
		todoResult = allTodos.get(0);
		assertEquals(todo2.getId(), todoResult.getId());
		
		/*
		 * handle sorting alpha desc ascd, custom sequencing
		 */

	}
}
