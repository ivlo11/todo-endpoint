package com.ivonneroberts.todo.entity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents a single todo which has an id, a message
 * and a completed flag
 * @author ivonne
 *
 */
public class Todo {
    private static AtomicInteger nextId = new AtomicInteger();

	private int id;
	private String message;
	private boolean completed;

	public Todo(String message) {
		id = nextId.getAndIncrement();
		setMessage(message);
	}

	public int getId() {
		return id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean getCompleted() {
		return completed;
	}

}
