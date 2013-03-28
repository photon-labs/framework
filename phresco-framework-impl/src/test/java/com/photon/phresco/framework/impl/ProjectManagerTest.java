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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.model.ProjectInfo;
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
		ProjectInfo projectInfo = getProjectInfo("tech-php", "tech-php" , "php-1" , "php-2", "PHR_Php");
		projectManager.create(projectInfo, serviceManager);
//		List<ProjectInfo> appList = projectManager.discover(customerId);
//		Assert.assertEquals(3, appList.size());
	}
	
//	@Test
//	public void testUpdateProject() throws PhrescoException {
//		ProjectInfo projectInfo = getProjectInfo("tech-php", "tech-php" , "Sample-Php-1" , "Sample-Php-2", "PHR_PHP");
//		ProjectInfo update = projectManager.update(projectInfo, serviceManager);
//		System.out.println(update.getDescription());
//	}	
}
