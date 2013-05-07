package com.photon.phresco.framework.rest.api.util;

import javax.xml.bind.annotation.XmlRootElement;


@SuppressWarnings("restriction")
@XmlRootElement
public class MavenResponse {

	String status;
	String log;
	String service_exception;
	String uniquekey;
	
	
	public String getUniquekey() {
		return uniquekey;
	}
	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getService_exception() {
		return service_exception;
	}
	public void setService_exception(String service_exception) {
		this.service_exception = service_exception;
	}
	
	
}
