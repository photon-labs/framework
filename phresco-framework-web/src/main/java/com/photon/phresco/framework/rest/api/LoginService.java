package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Credentials;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path ("/login")
public class LoginService extends RestBase {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	 public Response authenticate(Credentials credentials) {
	        User user = null;
	    	ResponseInfo responseData = new ResponseInfo();
	        try {
	        	user = doLogin(credentials);
	        	if (user == null) {
	        		responseData.setMessage("Login failed");
	        		return Response.ok(responseData).header("Access-Control-Allow-Origin", "*").build();
	        	}
	        	if (!user.isPhrescoEnabled()) {
	        		responseData.setMessage("Login failed");
	        		return Response.ok(responseData).header("Access-Control-Allow-Origin", "*").build();
	        	}
	        	
	        	List<Customer> customers = user.getCustomers();
	    		Collections.sort(customers, sortCusNameInAlphaOrder());

	        	File tempPath = new File(Utility.getPhrescoTemp() + File.separator + "user.json");
	        	String userId = user.getId();
	        	JSONObject userjson = new JSONObject();
	        	JSONParser parser = new JSONParser();
	        	String customerId = "photon";
	        	if (tempPath.exists()) {
	        		FileReader reader = new FileReader(tempPath);
	        		userjson = (JSONObject)parser.parse(reader);
	        		customerId = (String) userjson.get(userId);
	        		reader.close();
	        	} 
	        	userjson.put(userId, customerId);
	        	
	        	List<String> customerList = new ArrayList<String>();
	        	for (Customer c : customers) {
					customerList.add(c.getId());
				}
	        	
	        	if ((StringUtils.isEmpty(customerId) || "photon".equals(customerId)) && customerList.contains("photon")) {
	        		customerId = "photon";
	        	}
	        	
	        	FileWriter  writer = new FileWriter(tempPath);
	        	writer.write(userjson.toString());
	        	writer.close();
	        	
			responseData.setData(user);
			responseData.setMessage("Login Successfull");
			
			return Response.ok(responseData).header("Access-Control-Allow-Origin", "*").build();
			
	        } catch (PhrescoWebServiceException e) {
	        	ServiceManagerMap.responseDataEvalution(responseData, e, "Login failed", null);
	        	return Response.status(Status.EXPECTATION_FAILED).entity(responseData).header("Access-Control-Allow-Origin", "*").build();
	        } catch (IOException e) {
	        	ServiceManagerMap.responseDataEvalution(responseData, e, "", null);
	        	return Response.status(Status.INTERNAL_SERVER_ERROR).entity(responseData).header("Access-Control-Allow-Origin", "*").build();
			} catch (ParseException e) {
				ServiceManagerMap.responseDataEvalution(responseData, e, "", null);
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(responseData).header("Access-Control-Allow-Origin", "*").build();
			}
	    }

	private User doLogin(Credentials credentials) {
		ServiceManager serviceManager = null;
        try {
        	serviceManager = getServiceManager(credentials.getUsername(), credentials.getPassword());
        } catch (PhrescoWebServiceException ex) {
            throw new PhrescoWebServiceException(ex.getResponse());
        } 
        return serviceManager.getUserInfo();
    }
	
	  private Comparator sortCusNameInAlphaOrder() {
			return new Comparator(){
			    public int compare(Object firstObject, Object secondObject) {
			    	Customer customerName1 = (Customer) firstObject;
			    	Customer customerName2 = (Customer) secondObject;
			       return customerName1.getName().compareToIgnoreCase(customerName2.getName());
			    }
			};
		}
}
