package com.ivonneroberts.todo.entity;

/**
 * This class represents a single todo which has an id, a message
 * and a completed flag
 * @author ivonne
 *
 */
public class Todo {

	private int id;
	private String message;

	public void setId(int id) {
		this.id = id;
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

}
