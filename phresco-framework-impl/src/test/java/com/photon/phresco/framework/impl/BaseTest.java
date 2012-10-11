package com.photon.phresco.framework.impl;

import org.apache.commons.codec.binary.Base64;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;

public class BaseTest {
	public static ServiceManager serviceManager = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws PhrescoException {
		//String serviceURL = "http://172.16.25.196:3030/service/rest/api";
		String serviceURL = "http://localhost:4040/service/rest/api";
		String userName = "phresco";
		byte[] encodeBase64 = Base64.encodeBase64("U83EfU$r".getBytes());
        String password = new String(encodeBase64);
		ServiceContext context = new ServiceContext();
        context.put("phresco.service.url", serviceURL);
        context.put("phresco.service.username", userName);
        context.put("phresco.service.password", password);	
		serviceManager = ServiceClientFactory.getServiceManager(context);
		System.out.println("BaseTest serviceManager : "+serviceManager.getCustomers().size());
	}

	@AfterClass
	public static void tearDownAfterClass() {

	}
	
	
}
