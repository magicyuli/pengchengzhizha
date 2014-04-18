package com.pengchengzhizha.exception;

public class IllegalInputException extends Exception {
	private static final long serialVersionUID = 1681338838305713966L;

	public IllegalInputException() {
		super();
	}

	public IllegalInputException(String string) {
		super(string);
	}
	
	public IllegalInputException(String string, Throwable t) {
		super(string, t);
	}
	
	public IllegalInputException(Throwable t) {
		super(t);
	}
	
}
