package com.photon.phresco.framework.rest.api;


import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;

public class RestBase {

	protected ServiceManager getServiceManager(String userName, String password) {
		return getServiceMgr(userName, password);
	}

	private ServiceManager getServiceMgr(String userName, String password) {
		ServiceManager serviceManager;
		try {
			ServiceContext context = new ServiceContext();
			FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
			context.put("phresco.service.url", configuration.getServerPath());
			context.put("phresco.service.username", userName);
			context.put("phresco.service.password", password);
			context.put("phresco.service.api.key", configuration.apiKey());
			serviceManager = ServiceManagerMap.CONTEXT_MANAGER_MAP.get(userName);
			if (serviceManager == null) {
				serviceManager = new ServiceManagerImpl(context);
				ServiceManagerMap.CONTEXT_MANAGER_MAP.put(userName, serviceManager);
			}
		} catch (PhrescoWebServiceException ex) {
			throw new PhrescoWebServiceException(ex.getResponse());
		} catch (PhrescoException e) {
			throw new PhrescoWebServiceException(e);
		}
		return serviceManager;
	}

}
