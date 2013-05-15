package com.photon.phresco.framework.rest.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.photon.phresco.service.client.api.ServiceManager;

public class ServiceManagerMap {

	public static final Map<String, ServiceManager> CONTEXT_MANAGER_MAP = new HashMap<String, ServiceManager>();

	public static ServiceManager getServiceManager(String key) throws IOException {
		ServiceManager serviceManager = CONTEXT_MANAGER_MAP.get(key);
		return serviceManager;
	}

	public static void putServiceManager(String key, ServiceManager serviceManager) {
		CONTEXT_MANAGER_MAP.put(key, serviceManager);
	}
}
