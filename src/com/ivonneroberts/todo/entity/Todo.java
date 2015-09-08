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
	private Long sequence;
	private String message;
	private Boolean completed;

	public Todo() {
		//
	}
	
	public Todo(String message) {
		setMessage(message);
		setCompleted(Boolean.FALSE);
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

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public Long getSequence() {
		return sequence;
	}
	
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

}
