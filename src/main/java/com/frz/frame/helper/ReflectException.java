package com.frz.frame.helper;

/**
 * 反射过程异常
 * @author GongTengPangYi
 *
 */
public class ReflectException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6435031881728482256L;

	public ReflectException() {
		super();
	}

	public ReflectException(String message, Throwable cause, boolean enableSuppression,
							boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReflectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectException(String message) {
		super(message);
	}

	public ReflectException(Throwable cause) {
		super(cause);
	}

}
