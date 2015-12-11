/**
 * 
 */
package com.yanhao.main.yanhaoandroid.http;

/**
 * @author rax
 *
 */
public class MatchResponseFailedException extends Exception {

	/**
	 * 
	 */
	public MatchResponseFailedException() {
	}

	/**
	 * @param detailMessage
	 */
	public MatchResponseFailedException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * @param throwable
	 */
	public MatchResponseFailedException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param detailMessage
	 * @param throwable
	 */
	public MatchResponseFailedException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
