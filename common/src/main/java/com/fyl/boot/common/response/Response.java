package com.fyl.boot.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fyl.boot.common.constant.HttpStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一的返回结果类
 */
@Data
@JsonInclude(Include.NON_NULL)
public class Response<T>  implements Serializable{
	private static final long serialVersionUID = -8761467446316496548L;

	/**
	 * 返回状态
	 */
	Integer code = HttpStatus.OK.value();
	
	/**
	 * 返回消息
	 */
	String msg;
	
	/**
	 * 返回对象
	 */
	T result;

	public Response(){

	}

	public Response(T result) {
		this.result = result;
		this.msg = "请求成功！";
	}

	public Response(T result, String msg) {
		this.result = result;
		this.msg = msg;
	}
	
	public Response(T result, String msg, Integer httpCode) {
		this.result = result;
		this.msg = msg;
		this.code = httpCode;
	}

	public Response error() {
		this.msg = "请求异常！";
		this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
		return this;
	}
}
