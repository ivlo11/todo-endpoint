package com.ivonneroberts.todo;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.ivonneroberts.todo.entity.Todo;

public class OfyService {
	/**
	 * Default constructor, never called.
	 */
	private OfyService() {
	}

	static {
		factory().register(Todo.class);
	}

	/**
	 * Returns the Objectify service wrapper.
	 * 
	 * @return The Objectify service wrapper.
	 */
	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	/**
	 * Returns the Objectify factory service.
	 * 
	 * @return The factory service.
	 */
	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}

}
