package com.photon.phresco.framework.rest.api;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseInfo<T> {
	private String message;
	private T data;
	private Exception exception;
	private Integer response;
	String status;
	String errorCode;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public Integer getResponse() {
		return response;
	}
	public void setResponse(Integer response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "ResponseData [data=" + data + ", exception=" + exception + ", response="
				+ response + ", status=" + status + ", errorCode=" + errorCode +"]";
	}

}
