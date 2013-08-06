package com.photon.phresco.framework.rest.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.Assert;
import org.junit.Test;

import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.rest.api.util.ActionFunction;
import com.photon.phresco.framework.rest.api.util.ActionResponse;
import com.photon.phresco.framework.rest.api.util.ActionServiceConstant;
import com.photon.phresco.util.Credentials;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ActionFunctionTest implements ActionServiceConstant  {
	
	ActionFunction actionFunction = new ActionFunction();
	
	boolean server_status=true;
	
	
	public void login() throws PhrescoException {
		
 
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);
 
		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/login");
		
		Credentials lCredentials = new Credentials("demouser","phresco");

		ClientResponse response = webResource.accept(
			        MediaType.APPLICATION_JSON_TYPE).
			        header("Content-Type", "application/json").
			        post(ClientResponse.class, lCredentials);
		
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
		}
		
		GenericType<ResponseInfo<User>> genericType = new GenericType<ResponseInfo<User>>() {};
		ResponseInfo<User> output = response.getEntity(genericType);
		User user = output.getData();
		System.out.println("user " + user);
		System.out.println("Output from Server .... \n");
		System.out.println("response.getType()"+response.getType());
		System.out.println("response.getType()"+response.getStatus());
		System.out.println("output.getMessage()"+output.getMessage());
		
		//fail("login failed");
		assertEquals("Failed during Login", "Login Successfull", output.getMessage());
		
		
	}
	
	
	
	@Test
    public void testBuild() throws PhrescoException {
		
		login();
		
			System.out.println("********************* Running the build test *************************************");
 
			ClientConfig cfg = new DefaultClientConfig();
			cfg.getClasses().add(JacksonJsonProvider.class);
			Client client = Client.create(cfg);
 
			WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/build");
		
		   MultivaluedMap queryParams = new MultivaluedMapImpl();
		   queryParams.add("username", "demouser");
		   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
		   queryParams.add("buildName", "clienttesting");
		   queryParams.add("buildNumber", "555");
		   queryParams.add("customerId", "photon");
		   queryParams.add("environmentName", "Production");
		   queryParams.add("logs", "showErrors");
		   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
		   queryParams.add("resultJson", "");
		   queryParams.add("selectedFileOrFolder", "");
		   queryParams.add("selectedFiles", "");
		   queryParams.add("showSettings", "true");
		   queryParams.add("skipTest", "true");
		   queryParams.add("stFileFunction", "");
		   queryParams.add("targetFolder", "");
 
		

		   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
		
		   if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
					   + response.getStatus());
		   }
		   
		   ActionResponse output = response.getEntity(ActionResponse.class);
 
		   System.out.println("Build Test Output from Server .... \n");
		   System.out.println("response.getType()"+response.getType());
		   System.out.println("response.getStatus()"+response.getStatus());
		   System.out.println("output.getStatus()"+output.getStatus());
		   System.out.println("output.getUniquekey()"+output.getUniquekey());
		   System.out.println("output.getService_exception()"+output.getService_exception());
		   System.out.println("output.getLog()"+output.getLog());
		   
		   assertEquals("build failed",STARTED,output.getStatus());
		   
		   System.out.println("---------Log Start---------------");
		   printLogs(output.getUniquekey());
		
    }
	
	@Test
	public void testDeploy() throws PhrescoException {
		
			System.out.println("********************* Running the Deploy test *************************************");
 
			ClientConfig cfg = new DefaultClientConfig();
			cfg.getClasses().add(JacksonJsonProvider.class);
			Client client = Client.create(cfg);
 
			WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/deploy");
		
			
		   MultivaluedMap queryParams = new MultivaluedMapImpl();
		   queryParams.add("username", "demouser");
		   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
		   queryParams.add("buildNumber", "555");
		   queryParams.add("customerId", "photon");
		   queryParams.add("dataBase", "mysql");
		   queryParams.add("environmentName", "Production");
		   queryParams.add("fetchSql", "");
		   queryParams.add("logs", "showErrors");
		   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
		   queryParams.add("resultJson", "");
		   queryParams.add("stFileFunction", "");
 
		

		   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
		
		   if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
					   + response.getStatus());
		   }
		   
		   ActionResponse output = response.getEntity(ActionResponse.class);
 
		   System.out.println("Deploy Test Output from Server .... \n");
		   System.out.println("response.getType()"+response.getType());
		   System.out.println("response.getStatus()"+response.getStatus());
		   System.out.println("output.getStatus()"+output.getStatus());
		   System.out.println("output.getUniquekey()"+output.getUniquekey());
		   System.out.println("output.getService_exception()"+output.getService_exception());
		   System.out.println("output.getLog()"+output.getLog());
		   
		   assertEquals("Deploy failed",STARTED,output.getStatus());
		   
		   System.out.println("---------Log Start---------------");
		   printLogs(output.getUniquekey());
		
    }
	
	@Test
	public void testProcessbuild() throws PhrescoException {
		
			System.out.println("********************* Running the ProcessBuild test *************************************");
			
			ClientConfig cfg = new DefaultClientConfig();
			cfg.getClasses().add(JacksonJsonProvider.class);
			Client client = Client.create(cfg);
 
			WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/processBuild");
		
			
		   MultivaluedMap queryParams = new MultivaluedMapImpl();
		   queryParams.add("message", "testingTheProcessBuild");
		   queryParams.add("appId", "TestProject");
		   queryParams.add("buildNumber", "555");
		   queryParams.add("customerId", "photon");
		   queryParams.add("url", "https://insight.photoninfotech.com/svn/repos/phresco-svn-projects/ci/3.0.0/CI-Impl/");
		   queryParams.add("username", "admin");
		   queryParams.add("password", "manage");
		   queryParams.add("projectId", "TestProject");
 
		

		   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
		   
		   ActionResponse output = response.getEntity(ActionResponse.class);
		   
		   if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
					   + response.getStatus());
		   }
		   
		   assertEquals("ProcessBuild failed",STARTED,output.getStatus());
		
    }
	
	@Test
	public void testUnitTest() throws PhrescoException {
		
			System.out.println("********************* Running the Unit Test's test *************************************");
 
			ClientConfig cfg = new DefaultClientConfig();
			cfg.getClasses().add(JacksonJsonProvider.class);
			Client client = Client.create(cfg);
 
			WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/runUnitTest");
		
			
		   MultivaluedMap queryParams = new MultivaluedMapImpl();
		   queryParams.add("username", "demouser");
		   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
		   queryParams.add("customerId", "photon");
		   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
		   queryParams.add("resultJson", "");
		   queryParams.add("stFileFunction", "");
		   queryParams.add("testAgainst", "java");
 
		

		   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
		
		   if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
					   + response.getStatus());
		   }
		   
		   ActionResponse output = response.getEntity(ActionResponse.class);
 
		   System.out.println("Unit Test Output from Server .... \n");
		   System.out.println("response.getType()"+response.getType());
		   System.out.println("response.getStatus()"+response.getStatus());
		   System.out.println("output.getStatus()"+output.getStatus());
		   System.out.println("output.getUniquekey()"+output.getUniquekey());
		   System.out.println("output.getService_exception()"+output.getService_exception());
		   System.out.println("output.getLog()"+output.getLog());
		   
		   assertEquals("Unit Test failed",STARTED,output.getStatus());
		   
		   System.out.println("---------Log Start---------------");
		   printLogs(output.getUniquekey());
		
    }
	
	@Test
	public void testcodeValidate() throws PhrescoException {
		
			System.out.println("********************* Running the Code Validate test *************************************");
 
			ClientConfig cfg = new DefaultClientConfig();
			cfg.getClasses().add(JacksonJsonProvider.class);
			Client client = Client.create(cfg);
 
			WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/codeValidate");
		
			
		   MultivaluedMap queryParams = new MultivaluedMapImpl();
		   queryParams.add("username", "demouser");
		   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
		   queryParams.add("customerId", "photon");
		   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
		   queryParams.add("goal", "validate-code");
		   queryParams.add("phase", "validate-code");
		   queryParams.add("resultJson", "");
		   queryParams.add("resultJson", "");
		   queryParams.add("sonar", "src");
		   queryParams.add("sonar", "src");
		   queryParams.add("src", "java");
		   queryParams.add("src", "java");
		   queryParams.add("stFileFunction", "");
		   queryParams.add("stFileFunction", "");
 
		

		   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
		
		   if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
					   + response.getStatus());
		   }
		   
		   ActionResponse output = response.getEntity(ActionResponse.class);
 
		   System.out.println("Code Validate Output from Server .... \n");
		   System.out.println("response.getType()"+response.getType());
		   System.out.println("response.getStatus()"+response.getStatus());
		   System.out.println("output.getStatus()"+output.getStatus());
		   System.out.println("output.getUniquekey()"+output.getUniquekey());
		   System.out.println("output.getService_exception()"+output.getService_exception());
		   System.out.println("output.getLog()"+output.getLog());
		   
		   assertEquals("Code Validation failed",STARTED,output.getStatus());
		   
		   System.out.println("---------Log Start---------------");
		   printLogs(output.getUniquekey());
		
    }
	
	@Test
	public void testrunAgainstSource() throws PhrescoException {
		
			System.out.println("********************* Running the Run Against Source test *************************************");
 
			ClientConfig cfg = new DefaultClientConfig();
			cfg.getClasses().add(JacksonJsonProvider.class);
			Client client = Client.create(cfg);
 
			WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/runAgainstSource");
		
			
		   MultivaluedMap queryParams = new MultivaluedMapImpl();
		   queryParams.add("username", "demouser");
		   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
		   queryParams.add("buildNumber", "555");
		   queryParams.add("customerId", "photon");
		   queryParams.add("dataBase", "mysql");
		   queryParams.add("environmentName", "Production");
		   queryParams.add("fetchSql", "");
		   queryParams.add("logs", "showErrors");
		   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
		   queryParams.add("resultJson", "");
		   queryParams.add("stFileFunction", "");
 
		

		   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
		
		   if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
					   + response.getStatus());
		   }
		   
		   ActionResponse output = response.getEntity(ActionResponse.class);
 
		   System.out.println("Run against source Output from Server .... \n");
		   System.out.println("response.getType()"+response.getType());
		   System.out.println("response.getStatus()"+response.getStatus());
		   System.out.println("output.getStatus()"+output.getStatus());
		   System.out.println("output.getUniquekey()"+output.getUniquekey());
		   System.out.println("output.getService_exception()"+output.getService_exception());
		   System.out.println("output.getLog()"+output.getLog());
		   
		   assertEquals("Run Against Source Test failed",STARTED,output.getStatus());
		   
		   System.out.println("---------Log Start---------------");
		   printRunAgainstSourceLogs(output.getUniquekey());
		
    }
	
	@Test
	public void testRestartServer() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the Restart Run Against Source test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/restartServer");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");

	

	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("Restart Run against source Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("Restart Run Against Source Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printRunAgainstSourceLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testStoptServer() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the Stop Run Against Source test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/stopServer");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");

	

	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("Stop Run against source Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("Stop Run Against Source Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testPerformanceTest() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the Performance test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/performanceTest");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("configurations", "Server");
	   queryParams.add("context", "yyy");
	   queryParams.add("contextPostData", "");
	   queryParams.add("contextType", "GET");
	   queryParams.add("customerId", "photon");
	   queryParams.add("encodingType", "UTF-8");
	   queryParams.add("environmentName", "Production");
	   queryParams.add("loopCount", "4");
	   queryParams.add("name", "yyy");
	   queryParams.add("name", "");
	   queryParams.add("noOfUsers", "4");
	   queryParams.add("objectClass", "com.photon.phresco.framework.model.PerformanceDetails");
	   queryParams.add("objectClass", "com.photon.phresco.framework.model.PerformanceDetails");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   queryParams.add("query", "");
	   queryParams.add("queryType", "Select Statement");
	   queryParams.add("rampUpPeriod", "4");
	   queryParams.add("resultJson", "{\"testAgainst\":\"server\",\"environmentName\":\"Production\",\"configurations\":\"Server\",\"testName\":\"tttt\",\"noOfUsers\":\"4\",\"rampUpPeriod\":\"4\",\"loopCount\":\"4\",\"name\":\"\",\"context\":\"yyy\",\"contextType\":\"GET\",\"encodingType\":\"UTF-8\",\"contextPostData\":\"\",\"objectClass\":\"com.photon.phresco.framework.model.PerformanceDetails\",\"queryType\":\"Select Statement\",\"query\":\"\",\"stFileFunction\":\"contextUrls,dbContextUrls\",\"resultJson\":\"\",\"contextUrls\":[{\"name\":\"yyy\",\"context\":\"yyy\",\"contextType\":\"GET\",\"encodingType\":\"UTF-8\",\"contextPostData\":\"\",\"headers\":[]}],\"dbContextUrls\":[{\"name\":\"\",\"queryType\":\"Select Statement\",\"query\":\"\"}]}");
	   queryParams.add("stFileFunction", "contextUrls,dbContextUrls");
	   queryParams.add("testAgainst", "server");
	   queryParams.add("testName", "tttt");
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("PerformanceTest Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("PerformanceTest Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testLoadTest() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the LoadTest test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/loadTest");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("configurations", "Server");
	   queryParams.add("configurations", "Server");
	   queryParams.add("customerId", "photon");
	   queryParams.add("environmentName", "Production");
	   queryParams.add("environmentName", "Production");
	   queryParams.add("goal", "load-test");
	   queryParams.add("headerKey", "trer");
	   queryParams.add("headerKey", "trer");
	   queryParams.add("headerValue", "ewrw");
	   queryParams.add("headerValue", "ewrw");
	   queryParams.add("loopCount", "2");
	   queryParams.add("loopCount", "2");
	   queryParams.add("noOfUsers", "3");
	   queryParams.add("noOfUsers", "3");
	   queryParams.add("phase", "load-test");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   queryParams.add("rampUpPeriod", "2");
	   queryParams.add("rampUpPeriod", "2");
	   queryParams.add("resultJson", "");
	   queryParams.add("resultJson", "");
	   queryParams.add("showSettings", "");
	   queryParams.add("showSettings", "");
	   queryParams.add("stFileFunction", "");
	   queryParams.add("stFileFunction", "");
	   queryParams.add("testAgainst", "server");
	   queryParams.add("testAgainst", "server");
	   queryParams.add("testName", "tererw");
	   queryParams.add("testName", "tererw"); 	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("LoadTest Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("LoadTest Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testminification() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the minification test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/minification");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("Merge", "Base.js,LoginWidget.js");
	   queryParams.add("Merge_fileLocation", "C:/Documents and Settings/mohamed_as/workspace/projects/test-html5jquerymobilewidget/src/main/webapp/js/framework/");
	   queryParams.add("appId", "c57e9d80-47fb-4137-9078-e90c9d46228b");
	   queryParams.add("customerId", "photon");
	   queryParams.add("fileLocation", "");
	   queryParams.add("getCssFiles1", "");
	   queryParams.add("minifyFileNames", "Merge");
	   queryParams.add("minifyFileNames", "temp");
	   queryParams.add("projectId", "3cabcaa9-134a-4036-ac26-0550a6692612");
	   queryParams.add("temp", "Merge.min.js");
	   queryParams.add("temp_fileLocation", "C:/Documents and Settings/mohamed_as/workspace/projects/test-html5jquerymobilewidget/src/main/webapp/js/framework/");
	   
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("Minification Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("minification Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testCISetup() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the ciSetup test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/ciSetup");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("ciSetup Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("ciSetup Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testCIStart() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the ciStart test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/ciStart");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("ciStart Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("ciStart Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printCIStartLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testCIStop() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the ciStop test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/ciStop");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("ciStop Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("ciStop Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testgenerateReport() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the generateReport test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/generateReport");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("generateReport Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("generateReport Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void teststartHub() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the startHub test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/startHub");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("browserTimeout", "0");
	   queryParams.add("browserTimeout", "0");
	   queryParams.add("capabilityMatcher", "org.openqa.grid.internal.utils.DefaultCapabilityMatcher");
	   queryParams.add("capabilityMatcher", "org.openqa.grid.internal.utils.DefaultCapabilityMatcher");
	   queryParams.add("cleanUpCycle", "5000");
	   queryParams.add("cleanUpCycle", "5000");
	   queryParams.add("customerId", "photon");
	   queryParams.add("goal", "start-hub");
	   queryParams.add("maxSession", "5");
	   queryParams.add("maxSession", "5");
	   queryParams.add("newSessionWaitTimeout", "-1");
	   queryParams.add("newSessionWaitTimeout", "-1");
	   queryParams.add("nodePolling", "5000");
	   queryParams.add("nodePolling", "5000");
	   queryParams.add("phase", "start-hub");
	   queryParams.add("port", "4444");
	   queryParams.add("port", "4444");
	   queryParams.add("prioritizer", "");
	   queryParams.add("prioritizer", "");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   queryParams.add("resultJson", "");
	   queryParams.add("resultJson", "");
	   queryParams.add("servlets", "");
	   queryParams.add("servlets", "");
	   queryParams.add("stFileFunction", "");
	   queryParams.add("stFileFunction", "");
	   queryParams.add("throwOnCapabilityNotPresent", "true");
	   queryParams.add("throwOnCapabilityNotPresent", "true");
	   queryParams.add("timeout", "300000");
	   queryParams.add("timeout", "300000");
	   
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("startHub Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("startHub Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printStartHub(output.getUniquekey());
		
	}
	
	
	@Test
	public void testCheckForHub() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the checkForHub test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/checkForHub");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("checkForHub Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   System.out.println("output.isConnectionAlive()"+output.isConnectionAlive());
	   
	   assertEquals("checkForHub Test failed",SUCCESS,output.getStatus());
	   assertTrue("checkForHub Test failed",output.isConnectionAlive());
		
	}
	
	
	@Test
	public void testStartNode() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the startNode test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/startNode");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("browserMaxInstances", "2");
	   queryParams.add("browserMaxInstances", "2");
	   queryParams.add("browserName", "firefox");
	   queryParams.add("browserName", "firefox");
	   queryParams.add("customerId", "photon");
	   queryParams.add("goal", "start-node");
	   queryParams.add("hubHost", "localhost");
	   queryParams.add("hubHost", "localhost");
	   queryParams.add("hubPort", "4444");
	   queryParams.add("hubPort", "4444");
	   queryParams.add("maxSession", "5");
	   queryParams.add("maxSession", "5");
	   queryParams.add("nodeport", "5555");
	   queryParams.add("nodeport", "5555");
	   queryParams.add("phase", "start-node");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   queryParams.add("proxy", "org.openqa.grid.selenium.proxy.DefaultRemoteProxy");
	   queryParams.add("proxy", "org.openqa.grid.selenium.proxy.DefaultRemoteProxy");
	   queryParams.add("register", "true");
	   queryParams.add("register", "true");
	   queryParams.add("registerCycle", "5000");
	   queryParams.add("registerCycle", "5000");
	   queryParams.add("resultJson", "");
	   queryParams.add("resultJson", "");
	   queryParams.add("seleniumProtocol", "WebDriver");
	   queryParams.add("seleniumProtocol", "WebDriver");
	   queryParams.add("stFileFunction", "");
	   queryParams.add("stFileFunction", "");	   
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("startNode Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("startNode Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printStartNode(output.getUniquekey());
		
	}
	
	@Test
	public void testCheckForNode() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the CheckForNode test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/checkForNode");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("checkForNode Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   System.out.println("output.isConnectionAlive()"+output.isConnectionAlive());
	   
	   assertEquals("checkForNode Test failed",SUCCESS,output.getStatus());
	   assertTrue("checkForNode Test failed",output.isConnectionAlive());
		
	}
	
	@Test
	public void testshowStartedHubLog() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the showStartedHubLog test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/showStartedHubLog");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("showStartedHubLog Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("showStartedHubLog Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testshowStartedNodeLog() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the showStartedNodeLog test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/showStartedNodeLog");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("showStartedNodeLog Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("showStartedNodeLog Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testRunFunctionalTest() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the runFunctionalTest test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/runFunctionalTest");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("environmentName", "Production");
	   queryParams.add("environmentName", "Production");
	   queryParams.add("goal", "functional-test-grid");
	   queryParams.add("phase", "functional-test");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   queryParams.add("resultJson", "");
	   queryParams.add("resultJson", "");
	   queryParams.add("stFileFunction", "");
	   queryParams.add("stFileFunction", "");
	   queryParams.add("testAgainst", "server");
	   queryParams.add("testAgainst", "server");
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("runFunctionalTest Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("runFunctionalTest Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printFunctionalTestLog(output.getUniquekey());
		
	}
	
	
	@Test
	public void testStopNode() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the stopNode test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/stopNode");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("stopNode Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("stopNode Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	@Test
	public void testStopHub() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the stopHub test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/stopHub");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	    	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("stopHub Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   
	   assertEquals("stopHub Test failed",STARTED,output.getStatus());
	   
	   System.out.println("---------Log Start---------------");
	   printLogs(output.getUniquekey());
		
	}
	
	
	@Test
	public void testPrintAsPdf() throws PhrescoException
	{
		

		
		System.out.println("********************* Running the printAsPdf test *************************************");

		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/printAsPdf");
	
		
	   MultivaluedMap queryParams = new MultivaluedMapImpl();
	   queryParams.add("username", "demouser");
	   queryParams.add("appId", "83e1a937-cf66-4231-84c7-f40e9efdf80f");
	   queryParams.add("customerId", "photon");
	   queryParams.add("projectId", "a20be474-0449-435d-8388-f459c96e9567");
	   queryParams.add("fromPage", "functional");
	   queryParams.add("isReportAvailable", "true");
	   queryParams.add("reportDataType", "detail");
	   queryParams.add("sonarUrl", "http://localhost:9000/dashboard");	
	   
	   
	   ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").post(ClientResponse.class );
	
	   if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatus());
	   }
	   
	   ActionResponse output = response.getEntity(ActionResponse.class);

	   System.out.println("printAsPdf Output from Server .... \n");
	   System.out.println("response.getType()"+response.getType());
	   System.out.println("response.getStatus()"+response.getStatus());
	   System.out.println("output.getStatus()"+output.getStatus());
	   System.out.println("output.getUniquekey()"+output.getUniquekey());
	   System.out.println("output.getService_exception()"+output.getService_exception());
	   System.out.println("output.getLog()"+output.getLog());
	   System.out.println("output.isConnectionAlive()"+output.isConnectionAlive());
	   
	   assertEquals("printAsPdf Test failed",SUCCESS,output.getStatus());
		
	}
	
	
		
	public void printLogs(String uniquekey) throws PhrescoException {
		
			ClientConfig cfg = new DefaultClientConfig();
			cfg.getClasses().add(JacksonJsonProvider.class);
			Client client = Client.create(cfg);
 
			WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/readlog");
		
			MultivaluedMap queryParams = new MultivaluedMapImpl();
		    queryParams.add("uniquekey", uniquekey);
 

		    ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").get(ClientResponse.class );
		
		    if (response.getStatus() != 200) {
		    	throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
		    }
 
		    ActionResponse output = response.getEntity(ActionResponse.class);
			
			System.out.println("--> "+output.getLog());
			
			if(output.getLog() != null)
			{
			
			if(output.getLog().contains("BUILD FAILURE"))
			{
				fail("Error occured ");
				throw new PhrescoException("Error occured ");
			}
			
			if(INPROGRESS.equalsIgnoreCase(output.getStatus()))
			{
				printLogs(uniquekey);
			}
			else if(COMPLETED.equalsIgnoreCase(output.getStatus()))
			{
				System.out.println("***** Log finished ***********");
			}
			else if(ERROR.equalsIgnoreCase(output.getStatus()))
			{
				fail("Error occured while retrieving the logs");
				throw new PhrescoException("Error Occured during log retrieval");
			}
			}
				
	}
	
	
	public void printRunAgainstSourceLogs(String uniquekey) throws PhrescoException {
		
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/readlog");
	
		MultivaluedMap queryParams = new MultivaluedMapImpl();
	    queryParams.add("uniquekey", uniquekey);


	    ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").get(ClientResponse.class );
	
	    if (response.getStatus() != 200) {
	    	throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
	    }

	    ActionResponse output = response.getEntity(ActionResponse.class);
		
		System.out.println("--> "+output.getLog());
		
		if(output.getLog() != null)
		{
		
		if(INPROGRESS.equalsIgnoreCase(output.getStatus()))
		{
			if(output.getLog().contains("Starting Coyote HTTP/1.1"))
			{
				removereader(uniquekey);
			}
			else if(output.getLog().contains("BUILD FAILURE"))
			{
				fail("Error occured in Run against source ");
				throw new PhrescoException("Error occured ");
			}
			else
			{
			printRunAgainstSourceLogs(uniquekey);
			}
		}
		else if(COMPLETED.equalsIgnoreCase(output.getStatus()))
		{
			System.out.println("***** Log finished ***********");
		}
		else if(ERROR.equalsIgnoreCase(output.getStatus()))
		{
			fail("Error occured while retrieving the logs");
			throw new PhrescoException("Error Occured during log retrieval");
		}
		}
			
	}
	
	public void printCIStartLogs(String uniquekey) throws PhrescoException {
		
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/readlog");
	
		MultivaluedMap queryParams = new MultivaluedMapImpl();
	    queryParams.add("uniquekey", uniquekey);


	    ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").get(ClientResponse.class );
	
	    if (response.getStatus() != 200) {
	    	throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
	    }

	    ActionResponse output = response.getEntity(ActionResponse.class);
		
		System.out.println("--> "+output.getLog());
		if(output.getLog() != null)
		{
		
		if(INPROGRESS.equalsIgnoreCase(output.getStatus()))
		{
			if(output.getLog().contains("tomcatProcess stopped"))
			{
				removereader(uniquekey);
			}
			else if(output.getLog().contains("BUILD FAILURE"))
			{
				fail("Error occured during CIStart");
				throw new PhrescoException("Error occured during CIStart");
			}
			else
			{
				printCIStartLogs(uniquekey);
			}
		}
		else if(COMPLETED.equalsIgnoreCase(output.getStatus()))
		{
			System.out.println("***** Log finished ***********");
		}
		else if(ERROR.equalsIgnoreCase(output.getStatus()))
		{
			fail("Error occured while retrieving the logs");
			throw new PhrescoException("Error Occured during log retrieval");
		}
		}
			
	}
	
	public void printStartHub(String uniquekey) throws PhrescoException {
		
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/readlog");
	
		MultivaluedMap queryParams = new MultivaluedMapImpl();
	    queryParams.add("uniquekey", uniquekey);


	    ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").get(ClientResponse.class );
	
	    if (response.getStatus() != 200) {
	    	throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
	    }

	    ActionResponse output = response.getEntity(ActionResponse.class);
		
		System.out.println("--> "+output.getLog());
		if(output.getLog() != null)
		{
		
		if(INPROGRESS.equalsIgnoreCase(output.getStatus()))
		{
			if(output.getLog().contains("AbstractConnector:Started"))
			{
				removereader(uniquekey);
			}
			else if(output.getLog().contains("BUILD FAILURE"))
			{
				fail("Error occured during start hub");
				throw new PhrescoException("Error occured during start hub");
			}
			else if(output.getLog().contains("Exception in thread \"main\""))
			{
				fail("Error occured during start hub");
				throw new PhrescoException("Error occured during start hub");
			}
			else
			{
				printStartHub(uniquekey);
			}
		}
		else if(COMPLETED.equalsIgnoreCase(output.getStatus()))
		{
			System.out.println("***** Log finished ***********");
		}
		else if(ERROR.equalsIgnoreCase(output.getStatus()))
		{
			fail("Error occured while retrieving the logs");
			throw new PhrescoException("Error Occured during log retrieval");
		}
		}
			
	}
	
	
	public void printStartNode(String uniquekey) throws PhrescoException {
		
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/readlog");
	
		MultivaluedMap queryParams = new MultivaluedMapImpl();
	    queryParams.add("uniquekey", uniquekey);


	    ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").get(ClientResponse.class );
	
	    if (response.getStatus() != 200) {
	    	throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
	    }

	    ActionResponse output = response.getEntity(ActionResponse.class);
		
		System.out.println("--> "+output.getLog());
		if(output.getLog() != null)
		{
		
		if(INPROGRESS.equalsIgnoreCase(output.getStatus()))
		{
			if(output.getLog().contains("Done: /status"))
			{
				removereader(uniquekey);
			}
			else if(output.getLog().contains("BUILD FAILURE"))
			{
				fail("Error occured during FunctionalTest");
				throw new PhrescoException("Error occured during start Node");
			}
			else if(output.getLog().contains("Exception in thread \"main\""))
			{
				fail("Error occured during start Node");
				throw new PhrescoException("Error occured during start Node");
			}
			else
			{
				printStartNode(uniquekey);
			}
		}
		else if(COMPLETED.equalsIgnoreCase(output.getStatus()))
		{
			System.out.println("***** Log finished ***********");
		}
		else if(ERROR.equalsIgnoreCase(output.getStatus()))
		{
			fail("Error occured while StartNode");
			throw new PhrescoException("Error Occured during StartNode");
		}
		}
			
	}
	
	
	public void printFunctionalTestLog(String uniquekey) throws PhrescoException {
		
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/readlog");
	
		MultivaluedMap queryParams = new MultivaluedMapImpl();
	    queryParams.add("uniquekey", uniquekey);


	    ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").get(ClientResponse.class );
	
	    if (response.getStatus() != 200) {
	    	throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
	    }

	    ActionResponse output = response.getEntity(ActionResponse.class);
		
		System.out.println("--> "+output.getLog());
		
		if(output.getLog() != null)
		{
		
		if(INPROGRESS.equalsIgnoreCase(output.getStatus()))
		{
			if(output.getLog().contains("LAUNCHING FIREFOX"))
			{
				removereader(uniquekey);
			}
			else if(output.getLog().contains("BUILD FAILURE"))
			{
				fail("Error occured during FunctionalTest");
				throw new PhrescoException("Error occured during FunctionalTest");
			}
			else if(output.getLog().contains("Exception in thread \"main\""))
			{
				fail("Error occured during FunctionalTest");
				throw new PhrescoException("Error occured during FunctionalTest");
			}
			else
			{
				printFunctionalTestLog(uniquekey);
			}
		}
		else if(COMPLETED.equalsIgnoreCase(output.getStatus()))
		{
			System.out.println("***** Log finished ***********");
		}
		else if(ERROR.equalsIgnoreCase(output.getStatus()))
		{
			fail("Error occured while FunctionalTest");
			throw new PhrescoException("Error Occured during FunctionalTest");
		}
		}
			
	}
	
	
	public void removereader(String uniquekey) throws PhrescoException {
		
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);

		WebResource webResource = client.resource("http://localhost:2468/framework/rest/api/app/removereader");
	
		MultivaluedMap queryParams = new MultivaluedMapImpl();
	    queryParams.add("uniquekey", uniquekey);


	    ClientResponse response = webResource.queryParams(queryParams).type("application/x-www-form-urlencoded").get(ClientResponse.class );
	
	    if (response.getStatus() != 200) {
	    	throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
	    }

	    ActionResponse output = response.getEntity(ActionResponse.class);
		
		System.out.println("--> "+output.getLog());
		
		if(COMPLETED.equalsIgnoreCase(output.getStatus()))
		{
			System.out.println("***** Log removed successfully ***********");
		}
		else if(ERROR.equalsIgnoreCase(output.getStatus()))
		{
			fail("Error occured while removing the logs");
			throw new PhrescoException("Error Occured during log removal");
		}
			
	}
	
}
