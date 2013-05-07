package com.photon.phresco.framework.rest.api.util;

public interface MavenServiceConstants {
	
	
	
	public String APP_ID = "appId";
	public String PROJECT_ID = "projectId";
	public String CUSTOMER_ID = "customerId";
	public String SELECTED_FILES ="selectedFiles";
	public String UNIQUE_KEY ="uniquekey";
	
	public String CONNECT_ACK_CHANNEL="/service/connect";
	public String DISCONNECT_ACK_CHANNEL="/service/disconnect";
	public String ERROR_CHANNEL="/service/error";
	public String DATA_CHANNEL ="/service/phresco";
	
	public String CONNECT_ACK_MSG = "Server received your request and started processing... Pls wait!";
	public String DISCONNECT_ACK_MSG = "Data transfer done hence Disconnecting";
	public String DATA_FORMAT ="hh:mm:ss";
	public String USERNAME ="username";
	
	public String STARTED ="STARTED";
	public String INPROGRESS ="INPROGRESS";
	public String COMPLETED ="COMPLETED";
	public String ERROR ="ERROR";
	

}
