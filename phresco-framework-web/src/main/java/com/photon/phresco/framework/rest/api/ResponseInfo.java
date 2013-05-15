package com.photon.phresco.framework.rest.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseInfo<T> {
	private String message;
	private T data;
	private Exception exception;
	private Integer response;
	
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
		return "ResponseData [message=" + message + ", data=" + data + ", exception=" + exception + ", response="
				+ response + "]";
	}

}
