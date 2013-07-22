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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.exception.PhrescoWebServiceException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;

/**
 * The Class RestBase.
 *
 * @param <T> the generic type
 */
public class RestBase<T> {
	
	/** The Constant CONTEXT_MANAGER_MAP. */
	public static final Map<String, ServiceManager> CONTEXT_MANAGER_MAP = new HashMap<String, ServiceManager>();
	
	/**
	 * Gets the service manager.
	 *
	 * @param userId the user id
	 * @param password the password
	 * @return the service manager
	 */
	protected ServiceManager getServiceManager(String userId, String password) {
		ServiceManager serviceManager = null;
		try {
			ServiceContext context = new ServiceContext();
			FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
			context.put("phresco.service.url", configuration.getServerPath());
			context.put("phresco.service.username", userId);
			context.put("phresco.service.password", password);
			context.put("phresco.service.api.key", configuration.apiKey());
			if (serviceManager == null) {
				serviceManager = new ServiceManagerImpl(context);
				CONTEXT_MANAGER_MAP.put(userId, serviceManager);
			}
		} catch (PhrescoWebServiceException ex) {
			throw new PhrescoWebServiceException(ex.getResponse());
		} catch (PhrescoException e) {
			throw new PhrescoWebServiceException(e);
		}

		return serviceManager;
	}

	/**
	 * Response data evaluation.
	 *
	 * @param responseData the response data
	 * @param e the e
	 * @param msg the msg
	 * @param data the data
	 * @return the response info
	 */
	protected ResponseInfo<T> responseDataEvaluation(ResponseInfo<T> responseData, Exception e, String msg, T data ) {
		responseData.setException(e);
		responseData.setMessage(msg);
		responseData.setData(data);
		
		return responseData;
	}
	
	protected ResponseInfo<T> responseDataEvaluation(ResponseInfo<T> responseData, Exception e, T data, String status, String responseCode ) {
		responseData.setException(e);
		responseData.setData(data);
		responseData.setStatus(status);
		responseData.setErrorCode(responseCode);
		
		return responseData;
	}
}
