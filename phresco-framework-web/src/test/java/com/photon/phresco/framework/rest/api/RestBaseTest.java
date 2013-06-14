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
