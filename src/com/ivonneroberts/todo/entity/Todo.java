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

	private long id;
	private long sequence;
	private String message;
	private boolean completed;

	public Todo(String message) {
		id = nextId.getAndIncrement();
		setSequence(id);
		setMessage(message);
	}

	public long getId() {
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

	public long getSequence() {
		return sequence;
	}
	
	private void setSequence(long sequence) {
		this.sequence = sequence;
	}

}
