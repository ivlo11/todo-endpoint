package com.ivonneroberts.todo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * This class represents a single todo which has an id, a message
 * and a completed flag
 * @author ivonne
 *
 */
@Entity
public class Todo {

    @Id private Long id;
	private long sequence;
	private String message;
	private boolean completed;

	public Todo() {
		
	}
	
	public Todo(String message) {
		setMessage(message);
	}

	public Long getId() {
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
	
	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

}
