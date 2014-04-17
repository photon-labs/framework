package com.photon.phresco.framework.rest.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.rest.api.ResponseInfo;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class SecurityInterceptor implements ContainerRequestFilter {
	
	@Context
	HttpServletRequest httpServletRequest;

	@Context
	HttpServletResponse httpServletResponse;
	
	public ContainerRequest filter(ContainerRequest request) {
		if(httpServletRequest.getRequestURI().endsWith("rest/api/login")) {
			return request;
		}
		User user = (User) httpServletRequest.getSession().getAttribute("user");
		if(user == null) {
			ResponseInfo<String> responseInfo = new ResponseInfo<String>();
			responseInfo.setMessage("Session Expired");
			responseInfo.setResponseCode("PHR000019");
			ResponseBuilder builder = Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON_TYPE).entity(responseInfo);
	        httpServletResponse.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
	        throw new WebApplicationException(builder.build());
		}
		return request;
	}
}