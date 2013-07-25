package com.photon.phresco.framework.rest.api;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseInfo<T> {
	private String message;
	private T data;
	private Exception exception;
	private String status;
	private String responseCode;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
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
	@Override
	public String toString() {
		return "ResponseData [data=" + data + ", exception=" + exception + 
				", status=" + status + ", errorCode=" + responseCode +"]";
	}

}
