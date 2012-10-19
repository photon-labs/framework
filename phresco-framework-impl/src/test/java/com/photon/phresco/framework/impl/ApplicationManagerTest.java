package com.photon.phresco.framework.impl;


import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ActionType;
import com.photon.phresco.framework.api.ApplicationManager;
import com.photon.phresco.framework.model.BuildInfo;
import com.photon.phresco.util.Utility;

public class ApplicationManagerTest extends BaseTest{

	private static ApplicationManager applicationManager;
	private ProjectInfo projectInfo;
	@Before
	public void setUp() throws Exception {
		applicationManager = PhrescoFrameworkFactory.getApplicationManager();
		projectInfo = getProjectInfo("tech-php", "tech-php" , "Sample-Php-1" , "Sample-Php-2", "PHR_PHP");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void PerformActionTest() throws PhrescoException {
		ProjectInfo projectInfo = getProjectInfo("tech-php", "tech-php" , "Sample-Php-1" , "Sample-Php-2", "PHR_PHP");
//		applicationManager.performAction(projectInfo, ActionType.BUILD);
	}
	
	@Test
	public void getBuildInfoTest() throws PhrescoException {
		String projectHome = Utility.getProjectHome() + File.separator + projectInfo.getProjectCode();
		File buildInfoFile = new File(projectHome + "\\do_not_checkin\\build\\build.info");
		List<BuildInfo> buildInfos = applicationManager.getBuildInfos(buildInfoFile);
		for (BuildInfo buildInfo : buildInfos) {
			if(buildInfo != null) {
				Assert.assertTrue(true);
			} else {
				Assert.fail();
			}
		}
	}
	
	@Test
	public void getApplicationInfoTest() throws PhrescoException {
		String projectId = projectInfo.getId();
		ApplicationInfo applicationInfo = applicationManager.getApplicationInfo("photon", projectId, "PHR_Test");
		System.out.println("ApplicationInfo : " + applicationInfo.getAppDirName());
	}
	
//	@Test
	public void deleteTest() throws PhrescoException {
		String projectId = projectInfo.getId();
		ApplicationInfo applicationInfo = applicationManager.getApplicationInfo("photon", projectId, "PHR_Test");
		System.out.println(applicationInfo.getAppDirName());
//		applicationManager.delete(applicationInfo);
	}
}
