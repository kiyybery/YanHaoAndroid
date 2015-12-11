/**
 * 
 */
package com.yanhao.main.yanhaoandroid.http;

/**
 * 
 */
public class InvalidFormatException extends Exception {

	/**
	 * 
	 */
	public InvalidFormatException() {
	}

	/**
	 * @param detailMessage
	 */
	public InvalidFormatException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * @param throwable
	 */
	public InvalidFormatException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param detailMessage
	 * @param throwable
	 */
	public InvalidFormatException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
