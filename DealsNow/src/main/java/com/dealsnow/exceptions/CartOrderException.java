package com.dealsnow.exceptions;

public class CartOrderException extends RuntimeException {
	public CartOrderException() {}
	public CartOrderException(String m) {
		super(m);
	}
}
