package com.photon.phresco.framework.rest.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseInfo {
	private String message;
	private Object data;
	private Exception exception;
	private Integer response;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
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
