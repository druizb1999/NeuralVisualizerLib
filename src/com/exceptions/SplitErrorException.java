package com.exceptions;

public class SplitErrorException extends Exception {

	/**
	 * 
	 */
	//Exception that arises when the graph can't be split
	private static final long serialVersionUID = -1028232111074907970L;

	public SplitErrorException(String message) {
		super(message);
	}

}
