package com.photon.phresco.framework.rest.api.util;

public interface MavenServiceConstants {
	
	
	
	public String APP_ID = "appId";
	public String PROJECT_ID = "projectId";
	public String CUSTOMER_ID = "customerId";
	public String SELECTED_FILES ="selectedFiles";
	public String UNIQUE_KEY ="uniquekey";
	public String TEST_AGAINST ="testAgainst";
	public String TEST_NAME ="testName";
	public String  RESULT_JSON= "resultJson";
	public String  MINIFY_FILE_NAMES= "minifyFileNames";
	public String  MINIFY_ALL= "minifyAll";
	
	
	
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
	
	public String BUILDPROJECT="BUILD";
	public String DEPLOYPROJECT="DEPLOY";
	public String UNIT_TEST="UNIT_TEST";
	public String CODE_VALIDATE="CODE_VALIDATE";
	public String RUN_AGAINST_SOURCE="RUN_AGAINST_SOURCE";
	public String RESTART_SERVER="RESTART_SERVER";
	public String STOP_SERVER="STOP_SERVER";
	public String PERFORMANCE_TEST="PERFORMANCE_TEST";
	public String LOAD_TEST="LOAD_TEST";
	public String MINIFY="MINIFY";
	

}
