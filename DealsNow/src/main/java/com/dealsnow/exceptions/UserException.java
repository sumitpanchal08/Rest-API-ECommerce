package com.dealsnow.exceptions;

public class UserException extends RuntimeException{
	public UserException(){}
	public UserException(String m) {
		super(m);
	}
}
