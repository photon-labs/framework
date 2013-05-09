package com.photon.phresco.framework.rest.api;

import java.util.HashMap;
import java.util.Map;


import com.photon.phresco.service.client.api.ServiceManager;

public class ServiceManagerMap {
	
	public static final Map<String, ServiceManager> CONTEXT_MANAGER_MAP = new HashMap<String, ServiceManager>();

	protected static ResponseInfo responseDataEvaluation(ResponseInfo responseData, Exception e, String msg, Object data ) {
		responseData.setResponse(0);
		responseData.setException(e);
		responseData.setMessage(msg);
		responseData.setData(data);
		
		return responseData;
		
	}
}
