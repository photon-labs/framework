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
	public void testUpdateProject() throws PhrescoException {
		ProjectInfo projectInfo = getProjectInfo("tech-php", "tech-php" , "Sample-Php-1" , "Sample-Php-2", "PHR_PHP");
		ProjectInfo update = projectManager.update(projectInfo, serviceManager);
		System.out.println(update.getDescription());
	}	
}
