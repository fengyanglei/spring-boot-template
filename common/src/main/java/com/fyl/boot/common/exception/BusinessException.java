package com.fyl.boot.common.exception;

import lombok.Getter;
import lombok.Setter;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -6729001295655350041L;

	@Getter
	@Setter
	private String discription;
	
	public BusinessException(String message){
		super(message);
		this.discription = message;
	}
	
	public BusinessException(String message, Throwable cause){
		 super(message, cause);  
		 this.discription = message;
	}
	
}
