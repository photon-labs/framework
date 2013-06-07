package com.photon.phresco.framework.rest.api;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.photon.phresco.service.client.api.ServiceManager;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestBaseTest extends RestBase {
	
	protected ServiceManager serviceManager = null;
	
	public RestBaseTest() {
		String userName = "admin";
		String password = "manage";
		serviceManager = getServiceManager(userName, password);
	}
	
	protected void loginFramework() {
		
	}
	
	protected List<String> getCustomer() {
		return Arrays.asList("photon");
	}
	
	protected Client createClient() {
		ClientConfig cfg = new DefaultClientConfig();
		cfg.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cfg);
		return client;
	}
}
