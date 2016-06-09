package com.egen.main.entity;

public class Status {

	private int statusCode;
	private String statusMessage;

	public Status() {
	}

	public Status(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public int getCode() {
		return statusCode;
	}

	public void setCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return statusMessage;
	}

	public void setMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
}
