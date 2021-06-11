package com.ts.flow.exception;
/**
 * @Title: ServiceDomException.java
 * @Description: XML解析相关异常
 * @author wh
 * @date 2021年6月3日  
 * @version V1.0
 */
public class ServiceDomException extends Exception {

	private static final long serialVersionUID = -8615880082635567768L;
	
	public ServiceDomException() {
		super();
	}
	
	public ServiceDomException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ServiceDomException(String message) {
		super(message);
	}
	
	public ServiceDomException(Throwable cause) {
		super(cause);
	}
}

