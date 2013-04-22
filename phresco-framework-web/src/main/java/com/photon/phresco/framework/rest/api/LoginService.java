package com.photon.phresco.framework.rest.api;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.google.gson.Gson;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Credentials;
import com.photon.phresco.util.Utility;

@Path ("/login")
public class LoginService extends RestBase {
	protected static final Map<String, ServiceManager> CONTEXT_MANAGER_MAP = new HashMap<String, ServiceManager>();
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	 public Response authenticate(Credentials credentials) throws PhrescoException  {
	        User user = null;
	        try {
	        	user = doLogin(credentials);
	        	
	        	if (user == null) {

	        		return null;
	        	}
	        	if (!user.isPhrescoEnabled()) {

	        		return null;
	        	}
	        	List<Customer> customers = user.getCustomers();
//	    		Collections.sort(customers, sortCusNameInAlphaOrder());

	        	//encode the password
//	        	byte[] encodedPwd = Base64.encodeBase64(credentials.getPassword().getBytes());
//	        	String encodedString = new String(encodedPwd);


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
	        } catch (PhrescoWebServiceException e) {
	        	throw new PhrescoException(e);
	        } catch (IOException e) {
	        	throw new PhrescoException(e);
			} catch (ParseException e) {
				throw new PhrescoException(e);
			}
			Gson gson = new Gson();
			String json = gson.toJson(user);
	       return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	    }
	
	private User doLogin(Credentials credentials) {
		ServiceManager serviceManager = null;
        try {
        	serviceManager = getServiceManager(credentials.getUsername(), credentials.getPassword());
        	CONTEXT_MANAGER_MAP.put(credentials.getUsername(), serviceManager);
        } catch (PhrescoWebServiceException ex) {
            throw new PhrescoWebServiceException(ex.getResponse());
        } 
        return serviceManager.getUserInfo();
    }
}
