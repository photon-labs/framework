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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public static final Map<String, ServiceManager> CONTEXT_MANAGER_MAP = new HashMap<String, ServiceManager>();
		
	
	@Before
	public void setUp() throws PhrescoException {
//		String serviceURL = "http://localhost:3030/service/rest/api";
		String serviceURL = "http://172.16.18.178:80/service/rest/api";
		String userName = "admin";
        String password = "manage";
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
	public void testCreateProject() throws PhrescoException {
		ProjectInfo projectInfo = getProjectInfo();
		ProjectInfo create = projectManager.create(projectInfo, serviceManager);
		Assert.assertEquals("testPhp", create.getAppInfos().get(0).getAppDirName());
	}
	
	public ProjectInfo getProjectInfo() {
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setId("test");
		projectInfo.setVersion("1.0");
		projectInfo.setProjectCode("testPhp");
		List<String> customerIds = new ArrayList<String>();
		customerIds.add(customerId);
		projectInfo.setCustomerIds(customerIds);
		List<ApplicationInfo> appInfos = new ArrayList<ApplicationInfo>();
		appInfos.add(getAppInfo());
		projectInfo.setAppInfos(appInfos);
		projectInfo.setNoOfApps(appInfos.size());
		projectInfo.setPreBuilt(false);
		projectInfo.setMultiModule(false);
		return projectInfo;
	}

	protected ApplicationInfo getAppInfo() {
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setId("PHR_Test");
		applicationInfo.setCode("testPhp");
		applicationInfo.setName("testPhp");
		List<String> customerIds = new ArrayList<String>();
		customerIds.add(customerId);
		applicationInfo.setCustomerIds(customerIds);
		TechnologyInfo techInfo = new TechnologyInfo();
		techInfo.setId("tech-php");
		techInfo.setName("PHP");
		techInfo.setVersion("5.4.x");
		techInfo.setAppTypeId("app-layer");
		applicationInfo.setTechInfo(techInfo);
		applicationInfo.setAppDirName("testPhp");
		return applicationInfo;
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
		ProjectInfo project = projectManager.getProject("test", customerId);
		Assert.assertEquals("test", project.getId());
	}
	
	@Test
	public void getprojectByAppId() throws PhrescoException {
		ProjectInfo project = projectManager.getProject("test", customerId, "PHR_Test");
		Assert.assertEquals("PHR_Test", project.getAppInfos().get(0).getId());
	}
	
//	@Test
	public void testUpdateWithoutApp() throws PhrescoException {
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setId("test");
		projectInfo.setVersion("1.0");
		projectInfo.setProjectCode("testPhp");
		List<String> customerIds = new ArrayList<String>();
		customerIds.add(customerId);
		projectInfo.setCustomerIds(customerIds);
		ProjectInfo project = projectManager.updateApplication(projectInfo, serviceManager, "testPhp", "");
		Assert.assertEquals("testPhp", project.getProjectCode());
	}
	
	@Test
	public void testUpdateProject() throws PhrescoException {
		ProjectInfo projectInfo = getProjectInfo();
		projectInfo.getAppInfos().get(0).setAppDirName("testPhp");
		List<String> selectedModules = new ArrayList<String>();
		selectedModules.add("mod_weather_tech_php1.0");
		selectedModules.add("mod_commenting_system._tech_php1.0");
		selectedModules.add("mod_reportgenerator_tech_php1.0");
		projectInfo.getAppInfos().get(0).setSelectedModules(selectedModules);
		
		List<ArtifactGroupInfo> selectedServers = new ArrayList<ArtifactGroupInfo>();
		ArtifactGroupInfo artifactGrpInfo = new ArtifactGroupInfo();
		artifactGrpInfo.setArtifactGroupId("downloads_wamp");
		List<String> artifactInfoIds = new ArrayList<String>();
		String version = "b9b344ed-7cc6-472a-a8d8-bf8f842eac9c";
		artifactInfoIds.add(version);
		artifactGrpInfo.setArtifactInfoIds(artifactInfoIds);
		selectedServers.add(artifactGrpInfo);
		
		projectInfo.getAppInfos().get(0).setSelectedServers(selectedServers);
		
		List<ArtifactGroupInfo> selectedDatabases = new ArrayList<ArtifactGroupInfo>();
		
		ArtifactGroupInfo artifactGrpInfo1 = new ArtifactGroupInfo();
		artifactGrpInfo1.setArtifactGroupId("downloads_mysql");
		List<String> artifactInfoIds1 = new ArrayList<String>();
		String version1 = "26bb9f28-e847-4099-b255-429706ceb7b9";
		artifactInfoIds1.add(version1);
		artifactGrpInfo1.setArtifactInfoIds(artifactInfoIds1);
		selectedDatabases.add(artifactGrpInfo1);
		
		projectInfo.getAppInfos().get(0).setSelectedDatabases(selectedDatabases);
		
		List<String> selectedWebservices = new ArrayList<String>();
		selectedWebservices.add("restjson");
		projectInfo.getAppInfos().get(0).setSelectedWebservices(selectedWebservices);
		
		projectInfo.setVersion("2.0");
		projectInfo.setDescription("Sample Discription for php");
		ProjectInfo project = projectManager.updateApplication(projectInfo, serviceManager, "testPhp", "");
		Assert.assertEquals("Sample Discription for php", project.getDescription());
	}
	
	@Test
	public void deleteProject() throws PhrescoException {
		List<String> appDirNames = new ArrayList<String>();
		appDirNames.add("testPhp");
		DeleteProjectInfo deleteProjectInfo = new DeleteProjectInfo();
		deleteProjectInfo.setAppDirNames(appDirNames);
		boolean delete = projectManager.delete(deleteProjectInfo);
//		Assert.assertTrue(delete);
	}
	
}
