/**
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;

public class BaseTest {
	public static ServiceManager serviceManager = null;
	String customerId = "photon";
	
	@BeforeClass
	public static void setUpBeforeClass() throws PhrescoException {
		//String serviceURL = "http://172.16.25.196:3030/service/rest/api";
		String serviceURL = "http://172.16.25.44:8081/service/rest/api";
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
	
	public ProjectInfo getProjectInfo(String techId1, String techId2 ,String name1 , String name2, String projectCode) {
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setNoOfApps(2);
		projectInfo.setVersion("1.0");
		projectInfo.setProjectCode(projectCode);
		List<String> customerIds = new ArrayList<String>();
		customerIds.add(customerId);
		projectInfo.setCustomerIds(customerIds);
		List<ApplicationInfo> appInfos = new ArrayList<ApplicationInfo>();
		appInfos.add(getAppInfo(name1, techId1));
		appInfos.add(getAppInfo(name2, techId2));
		projectInfo.setAppInfos(appInfos);
		return projectInfo;
	}

	protected ApplicationInfo getAppInfo(String dirName, String techId) {
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setId("PHR_Test");
		List<String> customerIds = new ArrayList<String>();
		customerIds.add(customerId);
		applicationInfo.setCustomerIds(customerIds);
		List<String> selectedModules = new ArrayList<String>();
		selectedModules.add("mod_weather_tech_php1.0");
		selectedModules.add("mod_commenting_system._tech_php1.0");
		selectedModules.add("mod_reportgenerator_tech_php1.0");
		applicationInfo.setSelectedModules(selectedModules);
		List<String> selectedWebservices = new ArrayList<String>();
		selectedWebservices.add("restjson");
		applicationInfo.setSelectedWebservices(selectedWebservices);
		TechnologyInfo techInfo = new TechnologyInfo();
		techInfo.setVersion(techId);
		applicationInfo.setTechInfo(techInfo);
		applicationInfo.setAppDirName(dirName);
		return applicationInfo;
	}
	
}
