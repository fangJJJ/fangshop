package com.fang.exception;

public class DataCheckedException extends Exception{
	
	public DataCheckedException() {
		super();
	}
	
	/**
	 * @param String
	 */
	public DataCheckedException(String s) {
		super(s);
	}
	
	/**
	 * @param Exception
	 */
	public DataCheckedException(Exception e) {
		super(e);
	}

}
