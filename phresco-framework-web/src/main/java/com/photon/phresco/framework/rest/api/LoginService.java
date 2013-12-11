/**
 * Framework Web Archive
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.LockUtil;
import com.photon.phresco.commons.ResponseCodes;
import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.commons.model.UserPermissions;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Credentials;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * The Class LoginService.
 */
@Path("/login")
public class LoginService extends RestBase implements FrameworkConstants, ResponseCodes{
	
	String status;
	String errorCode;
	String successCode;
	
	/**
	 * Authenticate User for login.
	 *
	 * @param credentials the credentials
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticate(Credentials credentials) {
		User user = null;
		ResponseInfo<User> responseData = new ResponseInfo<User>();
		try {
			user = doLogin(credentials);
			if (user == null) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR110001;
				ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, null, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOuptut).header(
						"Access-Control-Allow-Origin", "*").build();
			}
			if (!user.isPhrescoEnabled()) {
				status = RESPONSE_STATUS_FAILURE;
				errorCode = PHR110002;
				ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, null, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOuptut).header(
						"Access-Control-Allow-Origin", "*").build();
			}

			List<Customer> customers = user.getCustomers();
			//Collections.sort(customers, sortCusNameInAlphaOrder());

			File tempPath = new File(Utility.getPhrescoTemp() + File.separator + "user.json");
			String userId = user.getId();
			JSONObject userjson = new JSONObject();
			JSONParser parser = new JSONParser();
			String customerId = "photon";
			if (tempPath.exists()) {
				FileReader reader = new FileReader(tempPath);
				userjson = (JSONObject) parser.parse(reader);
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

			FileWriter writer = new FileWriter(tempPath);
			writer.write(userjson.toString());
			writer.close();

			ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(credentials.getUsername());
			UserPermissions userPermissions = FrameworkUtil.getUserPermissions(serviceManager, user);
			user.setPermissions(userPermissions);
			
			status = RESPONSE_STATUS_SUCCESS;
			successCode = PHR100001;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, null, user, status, successCode);
			return Response.ok(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoWebServiceException e) {
			if (e.getResponse().getStatus() == 204) {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR110003;
				ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin",
						"*").build();
			} else {
				status = RESPONSE_STATUS_ERROR;
				errorCode = PHR110004;
				ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
				return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin",
						"*").build();
			}
		} catch (IOException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR110005;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();
		} catch (ParseException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR110006;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();
		} catch (PhrescoException e) {
			status = RESPONSE_STATUS_ERROR;
			errorCode = PHR110007;
			ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, e, null, status, errorCode);
			return Response.status(Status.OK).entity(finalOuptut).header(
					"Access-Control-Allow-Origin", "*").build();
		}
	}

	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response clearLock() {
		ResponseInfo<User> responseData = new ResponseInfo<User>();
		LockUtil.LOCK_DETAILS.clear();
		ResponseInfo<User> finalOuptut = responseDataEvaluation(responseData, null, null, status, successCode);
		return Response.status(Status.OK).entity(finalOuptut).header("Access-Control-Allow-Origin", "*").build();
	}
	/**
	 * Do login.
	 *
	 * @param credentials the credentials
	 * @return the user
	 */
	private User doLogin(Credentials credentials) {
		ServiceManager serviceManager = null;
		try {
			serviceManager = getServiceManager(credentials.getUsername(), credentials.getPassword());
		} catch (PhrescoWebServiceException ex) {
			throw new PhrescoWebServiceException(ex.getResponse());
		}
		return serviceManager.getUserInfo();
	}

	/**
	 * Sort customer name in alphabetic order.
	 *
	 * @return the comparator
	 */
	private Comparator sortCusNameInAlphaOrder() {
		return new Comparator() {
			public int compare(Object firstObject, Object secondObject) {
				Customer customerName1 = (Customer) firstObject;
				Customer customerName2 = (Customer) secondObject;
				return customerName1.getName().compareToIgnoreCase(customerName2.getName());
			}
		};
	}
}
