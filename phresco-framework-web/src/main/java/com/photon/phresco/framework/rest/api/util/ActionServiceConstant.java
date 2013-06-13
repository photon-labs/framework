package com.photon.phresco.framework.rest.api.util;

public interface ActionServiceConstant {
	
	
	
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
	public String  PROJECT_MODULE= "projectModule";
	public String  REPORT_DATA_TYPE= "reportDataType";
	public String  FROM_PAGE= "fromPage";
	public String REQ_PDF_NAME = "pdfName";
	
	
	
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
	public String SUCCESS ="SUCCESS";
	
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
	public String START_HUB="START_HUB";
	public String START_NODE="START_NODE";
	public String FUNCTIONAL_TEST="FUNCTIONAL_TEST";
	public String STOP_HUB="STOP_HUB";
	public String STOP_NODE="STOP_NODE";
	public String CHECK_HUB_STATUS="CHECK_HUB_STATUS";
	public String CHECK_NODE_STATUS="CHECK_NODE_STATUS";
	public String SHOW_STARTED_HUB_LOG="SHOW_STARTED_HUB_LOG";
	public String SHOW_STARTED_NODE_LOG="SHOW_STARTED_NODE_LOG";
	public String SITE_REPORT="SITE_REPORT";
	

}
