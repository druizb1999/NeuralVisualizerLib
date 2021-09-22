package com.exceptions;

public class LayerNotFoundException extends Exception {
	
	/**
	 *
	 */
	//Exception that triggers when a layer is not found in the graph
	private static final long serialVersionUID = -2703267581778558754L;

	public LayerNotFoundException(String message) {
		super(message);
	}

}
