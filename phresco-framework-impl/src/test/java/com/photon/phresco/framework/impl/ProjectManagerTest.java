package com.photon.phresco.framework.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.commons.model.TechnologyInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectManager;

/**
 * This Test case has one test which validates 
 * 		1. Create Project (ProjectManager.create())
 * 		2. Discover the created projects (ProjectManager.discover())
 * 		3. Finally tearDown() validates deletion of created projects
 * @author suresh_ma
 *
 */
public class ProjectManagerTest extends BaseTest {

	String customerId = "photon";
	private static ProjectManager projectManager = null;
		
	@Before
	public void setUp() throws PhrescoException {
		if (projectManager == null) {
			projectManager = PhrescoFrameworkFactory.getProjectManager();
		}
	}
	
	@After
	public void tearDown() throws PhrescoException {
//		boolean delete = projectManager.delete(getProjectInfo());
//		Assert.assertTrue(delete);
	}
	
	@Test
	public void testCreateProject() throws PhrescoException {
		ProjectInfo projectInfo = getProjectInfo();
		System.out.println("serviceManager :::: " + serviceManager.getCustomers());
		projectManager.create(projectInfo, serviceManager);
		List<ProjectInfo> appList = projectManager.discover(customerId);
		Assert.assertEquals(3, appList.size());
	}
	
//	@Test
	public void testUpdateProject() throws PhrescoException {
		ProjectInfo projectInfo = getProjectInfo();
		ProjectInfo update = projectManager.update(projectInfo, serviceManager);
		System.out.println(update.getDescription());
	}
	
	public ProjectInfo getProjectInfo() {
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setNoOfApps(3);
		projectInfo.setVersion("1.0");
		projectInfo.setDescription("Phresco Test Project");
		projectInfo.setProjectCode("PHR_sampleproject");
		List<String> customerIds = new ArrayList<String>();
		customerIds.add(customerId);
		projectInfo.setCustomerIds(customerIds);
		List<ApplicationInfo> appInfos = new ArrayList<ApplicationInfo>();
		appInfos.add(getAppInfo("Test1", "tech-php"));
//		appInfos.add(getAppInfo("Test2", "tech-php"));
//		appInfos.add(getAppInfo("Test3", "tech-php"));
		projectInfo.setAppInfos(appInfos);
		return projectInfo;
	}

	private ApplicationInfo getAppInfo(String dirName, String techId) {
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
