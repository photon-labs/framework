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


import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.model.ApplicationInfo;
import com.photon.phresco.commons.model.BuildInfo;
import com.photon.phresco.commons.model.ProjectInfo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ApplicationManager;
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
