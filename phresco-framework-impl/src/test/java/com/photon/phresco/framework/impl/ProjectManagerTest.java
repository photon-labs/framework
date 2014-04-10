/**
 * Phresco Framework Implementation
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ArtifactGroupInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectManager;
import com.photon.phresco.framework.model.DeleteProjectInfo;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.impl.ServiceManagerImpl;

/**
 * This Test case has one test which validates 
 * 		1. Create Project (ProjectManager.create())
 * 		2. Discover the created projects (ProjectManager.discover())
 * 		3. Finally tearDown() validates deletion of created projects
 * @author suresh_ma
 *
 */
public class ProjectManagerTest {

	String customerId = "photon";
	private static ProjectManager projectManager = null;
	public static ServiceManager serviceManager = null;
	
	@Before
	public void setUp() throws PhrescoException {
		String serviceURL = "http://172.16.17.117:7070/service-3/rest/api";
		String userName = "phresco";
        String password = "Phresco@123";
		projectManager = PhrescoFrameworkFactory.getProjectManager();
		
		ServiceContext context = new ServiceContext();
		context.put("phresco.service.url", serviceURL);
		context.put("phresco.service.username", userName);
		context.put("phresco.service.password", password);
		if (serviceManager == null) {
			serviceManager = new ServiceManagerImpl(context);
		}
		if (projectManager == null) {
			projectManager = PhrescoFrameworkFactory.getProjectManager();
		}
	}
	
	@Test
	public void getProjectsByCustomerId() throws PhrescoException {
		List<ProjectInfo> appList = projectManager.discover(customerId);
		Assert.assertTrue(appList.size() > 0);
	}
	
	@Test
	public void getProjects() throws PhrescoException {
		List<ProjectInfo> discover = projectManager.discover();
		Assert.assertTrue(discover.size() > 0);
	}
	
	@Test
	public void getProjectById() throws PhrescoException {
		ProjectInfo project = projectManager.getProject("PHR_Test", customerId);
		Assert.assertEquals("PHR_Test", project.getId());
	}
	
	@Test
	public void getprojectByAppId() throws PhrescoException {
		ProjectInfo project = projectManager.getProject("PHR_Test", customerId, "PHR_Test");
		Assert.assertEquals("PHR_Test", project.getAppInfos().get(0).getId());
	}
	
//	@Test
	public void testUpdateWithoutApp() throws PhrescoException {
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setId("PHR_Test");
		projectInfo.setVersion("1.0.0-SNAPSHOT");
		projectInfo.setProjectCode("testPhp");
		List<String> customerIds = new ArrayList<String>();
		customerIds.add(customerId);
		projectInfo.setCustomerIds(customerIds);
		ProjectInfo project = projectManager.updateApplication(projectInfo, serviceManager, "testPhp", "");
		Assert.assertEquals("testPhp", project.getProjectCode());
	}
	
//	@Test
	public void testUpdateProject() throws PhrescoException {
		ProjectInfo projectInfo = new ProjectInfo();
		ApplicationInfo appInfo = new ApplicationInfo();
		appInfo.setAppDirName("testPhp");
		appInfo.setName("testPhp");
		appInfo.setCode("testPhp");
		appInfo.setId("PHR_Test");
		TechnologyInfo techInfo = new TechnologyInfo();
		techInfo.setId("tech-php");
		techInfo.setName("php-raw");
		techInfo.setVersion("5.4.x");
		techInfo.setTechGroupId("PHP");
		techInfo.setAppTypeId("e1af3f5b-7333-487d-98fa-46305b9dd6ee");
		appInfo.setTechInfo(techInfo);
		appInfo.setPilot(false);
		appInfo.setUsed(false);
		appInfo.setVersion("5.4.x");
		appInfo.setPomFile("pom.xml");
		
		List<String> selectedModules = new ArrayList<String>();
		selectedModules.add("d365fb80-6bb1-41e6-a6a6-4f6f1d3b5048");
		selectedModules.add("fea9872d-bc78-4765-ace2-0e18af45d105");
		selectedModules.add("aa5b3b7e-6f5c-4826-8e1d-28af66ff8017");
		appInfo.setSelectedModules(selectedModules);
		
		List<ArtifactGroupInfo> selectedServers = new ArrayList<ArtifactGroupInfo>();
		ArtifactGroupInfo artifactGrpInfo = new ArtifactGroupInfo();
		artifactGrpInfo.setArtifactGroupId("downloads_wamp");
		List<String> artifactInfoIds = new ArrayList<String>();
		String version = "b9b344ed-7cc6-472a-a8d8-bf8f842eac9c";
		artifactInfoIds.add(version);
		artifactGrpInfo.setArtifactInfoIds(artifactInfoIds);
		selectedServers.add(artifactGrpInfo);
		
		appInfo.setSelectedServers(selectedServers);
		
		List<ArtifactGroupInfo> selectedDatabases = new ArrayList<ArtifactGroupInfo>();
		
		ArtifactGroupInfo artifactGrpInfo1 = new ArtifactGroupInfo();
		artifactGrpInfo1.setArtifactGroupId("downloads_mysql");
		List<String> artifactInfoIds1 = new ArrayList<String>();
		String version1 = "26bb9f28-e847-4099-b255-429706ceb7b9";
		artifactInfoIds1.add(version1);
		artifactGrpInfo1.setArtifactInfoIds(artifactInfoIds1);
		selectedDatabases.add(artifactGrpInfo1);
		
		appInfo.setSelectedDatabases(selectedDatabases);
		
		List<String> selectedWebservices = new ArrayList<String>();
		selectedWebservices.add("restjson");
		appInfo.setSelectedWebservices(selectedWebservices);
		
		projectInfo.setAppInfos(Collections.singletonList(appInfo));
		projectInfo.setId("PHR_Test");
		projectInfo.setVersion("1.0.0-SNAPSHOT");
		projectInfo.setProjectCode("testPhp");
		projectInfo.setName("testPhp");
		projectInfo.setIntegrationTest(false);
		projectInfo.setGroupId("com.photon.phresco");
		projectInfo.setVersion("2.0");
		projectInfo.setDescription("Sample Discription for php");
		
		ProjectInfo project = projectManager.updateApplication(projectInfo, serviceManager, "testPhp", "");
		Assert.assertEquals("Sample Discription for php", project.getDescription());
	}
}
