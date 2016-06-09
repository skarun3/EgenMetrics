package com.egen.main.Exception;

import com.egen.main.entity.Metric;

public class InvalidRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidRequestException(Metric metric) {
		super("Metric '" + metric + "' is invalid.");
	}

}
